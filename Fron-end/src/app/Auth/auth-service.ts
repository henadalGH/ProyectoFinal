import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject, tap } from 'rxjs';
import { LoginServide } from '../login/login-servicio';

@Injectable({
  providedIn: 'root',
})
export class AuthService {

  private loggedIn: BehaviorSubject<string> = new BehaviorSubject<string>('');
  
  constructor(private router: Router,
    private loginServi: LoginServide
  ){}


  get isLooggedIn(){
    return this.loggedIn.asObservable();
  }
  
  login(email: string, passwod: string) {
    return this.loginServi.login(email, passwod).pipe(
      tap((response: any) => {
        if(response && response.success){
          this.loggedIn.next(email);
          localStorage.setItem('email', email);
          this.router.navigate(['/homeAdmin']);
        }
        else {
          alert('usuario o contraseña incorrecto')
        }
      }
    )); 
  }
  
}
