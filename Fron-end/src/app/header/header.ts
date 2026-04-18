import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { AuthService } from '../AuthServicio/auth-service';

@Component({
  selector: 'app-header',
  imports: [RouterLink],
  templateUrl: './header.html',
  styleUrl: './header.css',
})
export class Header {

  constructor(private authService: AuthService) {}

  logout(): void {
    this.authService.logout();
  }
}
