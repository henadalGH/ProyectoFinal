import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class LoginServide {

  
  constructor(private http: HttpClient) {}
  
  private urlApi= "http://localhost:8080/auth/login";

  login(email: String, password: String): Observable<any>{
    console.log("Enviando:", email, password);
    return this.http.post<any>(this.urlApi, {email: email, password: password});
  }

  setToken(token: string): void {
    localStorage.setItem('token', token);
  }
}
