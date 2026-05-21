import { Component } from '@angular/core';
import { Router, RouterLink } from "@angular/router";
import { AuthService } from '../../AuthServicio/auth-service';

@Component({
  selector: 'app-headercliente',
  imports: [RouterLink],
  templateUrl: './headercliente.html',
  styleUrl: './headercliente.css',
})
export class Headercliente {

  constructor(private authService: AuthService,
    private router: Router
  ) {}

  logout(): void {
    this.authService.logout();
  }

  verHitorial(id: number){

    this.router.navigate(['/miHistorial'])

  }
}
