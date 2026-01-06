import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ClienteServicio {

  constructor(private http: HttpClient){}

  private urlApiCliente= 'http://localhost:8080/cliente/todos';

  obtenerTodosLosClientes(){
    return this.http.get(this.urlApiCliente); 
  }
  
}
