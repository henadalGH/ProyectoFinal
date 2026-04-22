import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class TurnosService {

  constructor(private http: HttpClient){}

  private urlComun =' ';

 
  solicitarTurno(idVehiculo: number, descripcion: string, servicio: number, idCliente: number){

  }
  
}
