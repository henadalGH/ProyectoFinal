import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class EmpleadoService {

  constructor(private http: HttpClient) {}

  private urlTodos = 'http://localhost:8080/empleado/todos';
  private urlApiRegs = 'http://localhost:8080/registro/nuevo';
  private urlPorid = 'http://localhost:8080/empleado';
  
  obtenerEmpleados() {
    return this.http.get(this.urlTodos);
  }

  crearEmpleado(
    nombre: string,
    apellido: string,
    email: string,
    password: string,
    contacto: string,
    dni: string,
    fechaNacimiento: string,
    rol: string,
    cargo: string
  ): Observable<any> {

    
    const params = new HttpParams()
      .set('rolesEnum', rol.toUpperCase())
      .set('cargo', cargo.toUpperCase());

      
    const body = {
      nombre,
      apellido,
      email,
      password,
      contacto,
      dni,
      fechaNacimiento
    };

    return this.http.post<any>(this.urlApiRegs, body, { params });
  }

  verEmpleado(id: number)
  {
    return this.http.get(`http://localhost:8080/empleado/${id}`);
  }



}
