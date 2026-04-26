import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class TurnosService {

  private urlComun = 'http://localhost:8080/turno/crear';

  constructor(private http: HttpClient){}

  solicitarTurno(
    descripcion: string, 
    idVehiculo: number, 
    idServicio: number
  ): Observable<any> {

    return this.http.post<any>(this.urlComun, {
      descripcion,
      idVehiculo,
      idServicio
    });
  }
}