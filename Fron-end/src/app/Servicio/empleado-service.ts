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
  private urlComun = 'http://localhost:8080/empleado';

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
    fechaNacimieto: string,
    rol: string = "EMPLEADO",
    cargo: string
  ): Observable<any> {

    const body = {
      nombre,
      apellido,
      email,
      password,
      rol,
      contacto,
      dni,
      fechaNacimieto,
      cargo
    };

    return this.http.post<any>(this.urlApiRegs, body);
  }

  verEmpleado(id: number)
  {
    return this.http.get(`http://localhost:8080/empleado/${id}`);
  }

  obtenerEmpleadoMEcanico(){
    return this.http.get<any[]>(`${this.urlComun}/mecanico`);
  }

  modificarEmpleado(
  idEmpleado: number,
  cargo?: string,
  email?: string,
  contacto?: string
) {

  const body: any = {};

  // CARGO
  if (cargo !== undefined && cargo !== null && cargo !== '') {
    body.cargo = cargo;
  }

  // EMAIL
  if (email !== undefined && email !== null && email !== '') {
    body.email = email;
  }

  // CONTACTO
  if (contacto !== undefined && contacto !== null && contacto !== '') {
    body.contacto = contacto;
  }

  return this.http.put(
    `${this.urlComun}/modificaEmpleado/${idEmpleado}`,
    body
  );
}


activaDesactivarEmpleado(idEmpleado: number, activo: boolean){

  return this.http.put(`${this.urlComun}/activaEmpleado/${idEmpleado}`, activo);
}


}
