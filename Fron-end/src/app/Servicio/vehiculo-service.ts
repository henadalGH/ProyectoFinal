import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class VehiculoService {


  constructor(private http: HttpClient){}


  obtenerVehiculoCliente(idCliente: number)
  {
    return this.http.get<any []>(`http://localhost:8080/vehiculo/buscar/cliente/${idCliente}`);
  }
  
}
