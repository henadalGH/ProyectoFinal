import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class OrdenTrabajoService {

  constructor(private http: HttpClient){}


  private urlComun = 'http://localhost:8080/orden';
  private urlDetalle = 'http://localhost:8080/detalleOrden'  

  asignarOrden(idTurno: number, idEmpleado: number, prioridad: string) {

  return this.http.post(
    `${this.urlComun}/asignacion/${idTurno}/${idEmpleado}/${prioridad}`,{}
    
  );

}


  obtenerOrdenPorfechaEmpleado(idEmpleado: number, fecha: string){

    return this.http.get<any []>(`${this.urlComun}/obtenerOrdenesEmpledo/${idEmpleado}`,
      {
      params: {
        fecha: fecha
      }});

  }


  obtenerOrdenPorId(idOrden: number){
    return this.http.get<any []>(`${this.urlComun}/${idOrden}`);
  }

  agregarDetalleOrden(idOrden: number): Observable<any>{

    return this.http.post(`${this.urlDetalle}/agregarDetalle/${idOrden}`, {});
  }
}
