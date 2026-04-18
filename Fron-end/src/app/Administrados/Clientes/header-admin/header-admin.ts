import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { AuthService } from '../../../AuthServicio/auth-service';

@Component({
  selector: 'app-header-admin',
  imports: [RouterLink],
  templateUrl: './header-admin.html',
  styleUrl: './header-admin.css',
})
export class HeaderAdmin {

  constructor(private authService: AuthService) {}

  logout(): void {
    this.authService.logout();
  }
}
