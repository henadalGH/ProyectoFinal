import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class TurnosService {

  private urlComun = 'http://localhost:8080/turno/crear';
  private urlAsignacion = 'http://localhost:8080/turno/pendientesAsignacion'
  private url = 'http://localhost:8080/turno';

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

  crearTurnoCasual(
  descripcion: string,
  idServicio: number,
  idVehiculo: number | null,
  fecha: string,
  turnoClienteCasualDTO?: {
    nombreCliente: string,
    contactoCliente: string,
    email: string,
    marcaVehiculo: string,
    modeloVehiculo: string,
    patenteVehiculo: string
  }
): Observable<any> {

  const body = {
    descripcion,
    idServicio,
    idVehiculo,
    fecha: {
      fechas: fecha
    },
    turnoClienteCasualDTO
  };

  return this.http.post<any>(this.urlComun, body);
}

  obtenerTurnosAsignacion(){

    return this.http.get<any>(this.urlAsignacion);
  }


  obtenerTurnosPendientePorCliente(id: number){

    return this.http.get<any []>(`${this.url}/obtenerTurnoCliente/${id}`)

  }

  actualizarEstadoTurno(idTurno: number, estado:string){
    return this.http.put<any>(`${this.url}/cambiarEstado/${idTurno}`, {estado});
  }

  obtenerPorEstado(estado: string): Observable<any> {
    return this.http.get(`${this.url}/obtenerEstado`, {
      params: {
        estado: estado
      }
    });
  }


  obtenerTurnoPorId(idTurnno: number){
    return this.http.get<any>(`${this.url}/${idTurnno}`);
  }

  obtenerPorFecha(fecha: string): Observable<any[]> {

    const params = new HttpParams()
      .set('fecha', fecha);

    return this.http.get<any[]>(
      `${this.url}/obtenerPorFecha`,
      { params }
    );
  }
  
}