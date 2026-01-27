import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ClienteServicio {

  constructor(private http: HttpClient){}

  private urlApiCliente= 'http://localhost:8080/cliente/todos';
  private urlApiClienteId = 'http://localhost:8080/cliente'

  obtenerTodosLosClientes(){
    return this.http.get(this.urlApiCliente); 
  }


  obtenerClientePorId(id: number){
    return this.http.get(`http://localhost:8080/cliente/${id}`);

  }
  
}
