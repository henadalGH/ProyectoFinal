import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { AuthService } from '../../AuthServicio/auth-service';

@Component({
  selector: 'app-header-empleado',
  imports: [RouterLink],
  templateUrl: './header-empleado.html',
  styleUrl: './header-empleado.css',
})
export class HeaderEmpleado {

  constructor(private authService: AuthService) {}

  logout(): void {
    this.authService.logout();
  }
}
