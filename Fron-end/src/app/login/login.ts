import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { LoginServide } from './login-servicio';

@Component({
  selector: 'app-login',
  imports: [FormsModule, RouterLink],
  templateUrl: './login.html',
  standalone: true,
  styleUrl: './login.css',
})
export class Login {

email: String = "";
password: String = "";

constructor(private loginService: LoginServide, private router: Router)
{}

Login(): void {
  console.log(this.email, this.password);
  this.loginService.login(this.email, this.password).subscribe(
    (Response) => {
      this.loginService.setToken(Response.token);

      this.router.navigate(['/homeAdmin']);
    },
    (error)=> {
      console.error('Error de login', error);
      alert('Correo o contraseña incorrecta');
      
    }
  )
}

  


}
