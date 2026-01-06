import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class RegistroClienteServicio {

  constructor(private http: HttpClient){}

  private urlApiRegs= 'http://localhost:8080/registro/nuevo'

  crearCliente(nombre: string, apellido: string, email: string, password:string, contacto: string, direccion:string, rol: string): Observable<any>{
    const params = new HttpParams().set('rolesEnum', rol);
  const body = {
    nombre, apellido, email, password, contacto, direccion
  }
    return this.http.post<any>(this.urlApiRegs,body, {params: params});
  }
  
}
