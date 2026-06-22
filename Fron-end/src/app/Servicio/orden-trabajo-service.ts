import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class OrdenTrabajoService {

  constructor(private http: HttpClient){}


  private urlOrden = 'http://localhost:8080/orden';
  private urlDetalle = 'http://localhost:8080/detalleOrden'  

  asignarOrden(idTurno: number, idEmpleado: number, prioridad: string) {

  return this.http.post(
    `${this.urlOrden}/asignacion/${idTurno}/${idEmpleado}/${prioridad}`,{}
  );}


  obtenerOrdenPorfechaEmpleado(idEmpleado: number, fecha: string){
      return this.http.get<any []>(`${this.urlOrden}/obtenerOrdenesEmpledo/${idEmpleado}`,
        {
        params: {
          fecha: fecha
        }});
    }

  obtenerOrdenPorId(idOrden: number){
    return this.http.get<any []>(`${this.urlOrden}/${idOrden}`);
  }

  agregarDetalleOrden(idOrden: number, detalles: any): Observable<any>{
    return this.http.post(`${this.urlDetalle}/agregarDetalle/${idOrden}`, detalles);
  }

  obtenerDetalleOrden(idOrden: number){
    
    return this.http.get<any []>(`${this.urlDetalle}/obtenerDetalle/${idOrden}`);
  }

    actualizarEstadoOrden(idOrden: number, estado: string) {
    return this.http.put(
      `${this.urlOrden}/${idOrden}/estado`,
      `"${estado}"`,
      { headers: { 'Content-Type': 'application/json' } }
    );
  }



  obtenerOrdenPorEstado(estado: string){
    return this.http.get<any[]>(`${this.urlOrden}/estado/${estado}`);
  }
}