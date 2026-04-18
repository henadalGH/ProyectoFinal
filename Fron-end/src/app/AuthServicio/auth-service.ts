import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject, catchError, Observable, tap, throwError } from 'rxjs';
import { LoginService } from './login-service';
import { jwtDecode } from 'jwt-decode';

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
  // 🎭 ROL DESDE JWT
  // ========================
  getRol(): string | null {

    const token = this.getToken();
    if (!token) return null;

    try {
      const decoded: any = jwtDecode(token);

      let role =
        decoded.authorities?.[0] ||
        decoded.roles?.[0] ||
        decoded.role ||
        decoded.rol ||
        null;

      if (!role) return null;

      // 🔥 Normalizamos (case-insensitive)
      role = role.toString().toUpperCase();

      if (!role.startsWith('ROLE_')) {
        role = `ROLE_${role}`;
      }

      return role;

    } catch (e) {
      console.error('Error leyendo rol del token', e);
      return null;
    }
  }

  // ========================
  // 🆔 ID DESDE JWT (FIX CLAVE)
  // ========================
  getUserId(): number | null {

    const token = this.getToken();
    if (!token) return null;

    try {
      const decoded: any = jwtDecode(token);

      const rol = this.getRol();

      let rawId: any = null;

      if (rol === 'ROLE_EMPLEADO') {
        rawId = decoded.socioId;
      } else if (rol === 'ROLE_CLIENTE') {
        rawId = decoded.jugadorId;
      } else if (rol === 'ROLE_ADMIN') {
        rawId = decoded.adminId;
      } else {
        // Fallback a sub o id general
        rawId = decoded.sub || decoded.id;
      }

      if (!rawId) return null;

      const parsedId =
        typeof rawId === 'number' ? rawId : parseInt(rawId, 10);

      return Number.isNaN(parsedId) ? null : parsedId;

    } catch (e) {
      console.error('Error leyendo ID del token', e);
      return null;
    }
  }

  private hasToken(): boolean {
    return !!this.getToken();
  }

  // ========================
  // 🧪 DEBUG TOKEN
  // ========================
  debugToken(): void {

    const token = this.getToken();

    if (!token) {
      console.warn('❌ No hay token');
      return;
    }

    try {
      const decoded: any = jwtDecode(token);

      console.log('📋 Token Decodificado:', decoded);
      console.log('👤 Rol:', this.getRol());
      console.log('🆔 User ID:', this.getUserId());
      console.log('📧 Email:', localStorage.getItem('email'));

    } catch (e) {
      console.error('❌ Error decodificando token:', e);
    }
  }

  // ========================
  // 🔁 REDIRECCIÓN POR ROL
  // ========================
  redirectByRole(): void {

    const rol = this.getRol();
    console.log('🔄 Redirigiendo por rol:', rol);

    switch (rol) {

      case 'ROLE_ADMIN':
        console.log('👑 Redirigiendo a admin');
        this.router.navigate(['/homeAdmin']);
        break;

      case 'ROLE_EMPLEADO':
        console.log('👔 Redirigiendo a empleado');
        this.router.navigate(['/inicioSocio']);
        break;

      case 'ROLE_CLIENTE':
        console.log('👤 Redirigiendo a cliente');
        this.router.navigate(['/homeCliente']);
        break;

      default:
        console.log('❓ Rol desconocido, redirigiendo a inicio:', rol);
        this.router.navigate(['/inicio']);
    }
  }
  
}

