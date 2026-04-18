import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router } from '@angular/router';
import { AuthService } from './auth-service';

@Injectable({
  providedIn: 'root',
})
export class AuthGuard implements CanActivate{

  constructor(
    private auth: AuthService,
    private router: Router
  ) {}

  canActivate(route: ActivatedRouteSnapshot): boolean {

    if (!this.auth.isLogged()) {
      this.router.navigate(['/login']);
      return false;
    }

    const allowedRoles = route.data?.['role'];
    const userRol = this.auth.getRol();

    if (allowedRoles && !allowedRoles.includes(userRol)) {
      this.router.navigate(['/inicio']);
      return false;
    }

    return true;
  }
  
}
