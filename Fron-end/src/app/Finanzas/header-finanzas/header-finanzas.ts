import { Component } from '@angular/core';
import { RouterLink } from "@angular/router";
import { AuthService } from '../../AuthServicio/auth-service';

@Component({
  selector: 'app-header-finanzas',
  imports: [RouterLink],
  templateUrl: './header-finanzas.html',
  styleUrl: './header-finanzas.css',
})
export class HeaderFinanzas {

  constructor(private authService: AuthService) {}

  logout(): void {
    this.authService.logout();
  }
}
