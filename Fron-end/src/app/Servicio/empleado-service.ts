import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class EmpleadoService {
  
  constructor(private http: HttpClient){}
private urlTodos = 'http://localhost:8080/empleado/todos';


  obtenerEmpleados()
  {
      return this.http.get( this.urlTodos);
  }

  
}
