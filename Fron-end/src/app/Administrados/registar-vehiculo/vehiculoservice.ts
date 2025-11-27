import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class Vehiculoservice {

  constructor(private http: HttpClient)
  {}

  private utlApi = "http://localhost:8080/vehiculo/agregar"

  agregarVehiculo(marca: String, modelo: String, patente: String, kilometraje: number, anio: String){
    console.log("Se recibio lo siguente", modelo, marca, patente, anio, kilometraje);
    return this.http.post<any>(this.utlApi, {marca:  marca, modelo: modelo, patente: patente, anio: anio, kilometraje: kilometraje })
  }

  
}
