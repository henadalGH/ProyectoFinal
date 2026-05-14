import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class TurnosService {

  constructor(private http: HttpClient){}


  private urlComun = 'http://localhost:8080/turno';


   obtenerPorEstado(estado: string): Observable<any> {
    return this.http.get(`${this.urlComun}/obtenerEstado`, {
      params: {
        estado: estado
      }
    });
  }


  asignarFecha(id: number, fecha: string){

    return this.http.post(`${this.urlComun}/asignarFecha/${id}`, {fecha});
      
  }
  
}
