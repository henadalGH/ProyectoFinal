import { Component } from '@angular/core';
import { AuthService } from '../../../AuthServicio/auth-service';
import { RouterLink } from '@angular/router';

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
