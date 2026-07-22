import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class RepuestoService {


  constructor(private http: HttpClient){}

  private urlRepuesto= "http://localhost:8080/repuesto";

  obtenerRepuestos(){
    return this.http.get<any[]>(`${this.urlRepuesto}/obtenerRepuestos`)
  }


  obtenerRepuestoPorId(idRepuesto: number){

    return this.http.get<any>(`${this.urlRepuesto}/obtenerRepuesto/${idRepuesto}`);
  }

  aumentarStrok(idRepuesto: number, cantidad: number){

    return this.http.put(`${this.urlRepuesto}/aumentarStock/${idRepuesto}`, {cantidad});
  }

  disminuirStock(idRepuesto: number, cantidad: number){
    return this.http.put(`${this.urlRepuesto}/disminuirStock/${idRepuesto}`, {cantidad});
  }

  activaDesactivarRepuesto(idRepuesto: number, activo: boolean){
    return this.http.put(`${this.urlRepuesto}/activaDesacticaRepuesto/${idRepuesto}`, activo);
  }

  obtenerStockBajoRepuesto(){
    return this.http.get<any[]>(`${this.urlRepuesto}/obtenerStockBajo`)
  }

  obtenerRepuestosOrden(){
    return this.http.get<any[]>(`${this.urlRepuesto}/obtenerRepuestoOrden`);
  }



}
