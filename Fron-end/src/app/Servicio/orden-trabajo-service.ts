import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class OrdenTrabajoService {

  constructor(private http: HttpClient){}


  private urlComun = 'http://localhost:8080/orden';
  
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
}
