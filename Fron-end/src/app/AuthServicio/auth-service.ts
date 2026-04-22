import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject, catchError, Observable, tap, throwError } from 'rxjs';
import { LoginService } from './login-service';
import { jwtDecode } from 'jwt-decode';

// Interfaz para el token decodificado
interface DecodedToken {
  sub?: string | number;
  role?: string;
  clienteId?: number;
  empleadoId?: number;
  adminId?: number;
  exp?: number;
  iat?: number;
}

@Injectable({
  providedIn: 'root',
})
export class AuthService {

  private loggedIn: BehaviorSubject<boolean> =
    new BehaviorSubject<boolean>(this.hasToken());

  constructor(
    private router: Router,
    private loginServicio: LoginService
  ) {}

  // ========================
  // 🔐 LOGIN
  // ========================
  login(email: string, password: string): Observable<any> {
    return this.loginServicio.login(email, password).pipe(
      tap((resp: any) => {
        if (resp?.jwt) {
          localStorage.setItem('token', resp.jwt);
          localStorage.setItem('email', email);
          this.loggedIn.next(true);
        } else {
          throw new Error(resp?.error || 'Usuario o contraseña incorrectos');
        }
      }),
      catchError((err: any) => {
        let msg = 'Usuario o contraseña incorrectos';

        if (err.status === 0) {
          msg = 'No se pudo conectar al servidor';
        } else if (err.status === 401) {
          msg = err.error?.error || err.message || msg;
        }

        return throwError(() => new Error(msg));
      })
    );
  }

  // ========================
  // 🚪 LOGOUT
  // ========================
  logout(): void {
    localStorage.clear();
    this.loggedIn.next(false);
    this.router.navigate(['/inicio']);
  }

  // ========================
  // ✅ HELPERS
  // ========================
  isLogged(): boolean {
    return this.hasToken();
  }

  getLoggedInObservable(): Observable<boolean> {
    return this.loggedIn.asObservable();
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  // ========================
  // 📋 TOKEN DECODIFICADO
  // ========================
  getDecodedToken(): DecodedToken | null {
    const token = this.getToken();
    if (!token) return null;

    try {
      return jwtDecode<DecodedToken>(token);
    } catch (error) {
      console.error('Error decodificando token:', error);
      return null;
    }
  }

  // ========================
  // 🎭 ROL DESDE JWT
  // ========================
  getRol(): string | null {
    const decoded = this.getDecodedToken();
    if (!decoded?.role) return null;

    let role = decoded.role.toString().toUpperCase();

    if (!role.startsWith('ROLE_')) {
      role = `ROLE_${role}`;
    }

    return role;
  }

  // ========================
  // 🆔 ID DE ENTIDAD DESDE JWT
  // ========================
  getEntityId(): number | null {
    const decoded = this.getDecodedToken();
    if (!decoded) return null;

    // Prioridad: clienteId > empleadoId > adminId > sub
    const entityId = decoded.clienteId || decoded.empleadoId || decoded.adminId || decoded.sub;

    if (entityId == null) return null;

    const parsedId = typeof entityId === 'number' ? entityId : parseInt(entityId.toString(), 10);

    return Number.isNaN(parsedId) ? null : parsedId;
  }

  // ========================
  // ⏰ TOKEN EXPIRADO
  // ========================
  isTokenExpired(): boolean {
    const decoded = this.getDecodedToken();
    if (!decoded?.exp) return true; // Si no hay exp, considerarlo expirado

    const currentTime = Math.floor(Date.now() / 1000); // Tiempo actual en segundos
    return decoded.exp < currentTime;
  }

  // ========================
  // 📧 EMAIL DESDE LOCALSTORAGE
  // ========================
  getEmail(): string | null {
    return localStorage.getItem('email');
  }

  private hasToken(): boolean {
    return !!this.getToken();
  }

  // ========================
  // 🧪 DEBUG TOKEN
  // ========================
  debugToken(): void {
    const token = this.getToken();
    const decoded = this.getDecodedToken();

    if (!token) {
      console.warn('❌ No hay token');
      return;
    }

    console.log('📋 Token Decodificado:', decoded);
    console.log('👤 Rol:', this.getRol());
    console.log('🆔 Entity ID:', this.getEntityId());
    console.log('⏰ Token Expirado:', this.isTokenExpired());
    console.log('📧 Email:', this.getEmail());
  }

  // ========================
  // 🔁 REDIRECCIÓN POR ROL
  // ========================
  redirectByRole(): void {
    const rol = this.getRol();
    console.log('🔄 Redirigiendo por rol:', rol);

    switch (rol) {
      case 'ROLE_ADMIN':

        this.router.navigate(['/homeAdmin']);
        break;

      case 'ROLE_EMPLEADO':
        console.log('👔 Redirigiendo a empleado');
        break;

      case 'ROLE_CLIENTE':
        this.router.navigate(['/homeCliente']);
        break;

      default:
        this.router.navigate(['/inicio']);
    }
  }
}

