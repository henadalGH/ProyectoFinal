import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class RegistroClienteServicio {

  constructor(private http: HttpClient){}

  private urlApiRegs= 'http://localhost:8080/registro/nuevo'
  private urlLocalidad = 'http://localhost:8080/localidad'

  crearCliente(nombre: string, apellido: string, email: string,
  password:string, contacto: string, direccion:string,rol: string ,idLocalidad: number ): Observable<any>{
    
  const body = {
    nombre, apellido, email, password, contacto, direccion, rol, idLocalidad
  }
    return this.http.post<any>(this.urlApiRegs,body);
  }

  obtenerLocalidad(){
    return this.http.get<any[]>(`${this.urlLocalidad}/obtener`);
  }


  
}
