import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class TurnosService {

  constructor(private http: HttpClient){}

 
  solicitarTurno(vehiculo: number, descripcion: string, servicio: string){

  }
  
}
