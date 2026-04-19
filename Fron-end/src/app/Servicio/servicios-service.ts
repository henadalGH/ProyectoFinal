import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class ServiciosService {


  constructor(private http: HttpClient){}

  private urlServicios = 'http://localhost:8080/servicio/todos';


  obtenerServicios()
  {
    return this.http.get(this.urlServicios);
  }
  
}
