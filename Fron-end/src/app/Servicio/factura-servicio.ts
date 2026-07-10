import { HttpClient, HttpParams } from '@angular/common/http'; // <-- Importamos HttpParams acá
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class FacturaServicio {

  constructor(private http: HttpClient){ }

  private urlFactura = 'http://localhost:8080/presupuesto';

  crearFactura(factura: any): Observable<any> {
    return this.http.post<any>(`${this.urlFactura}/crear`, factura);
  }

  obtenerFacturas(){
    return this.http.get<any[]>(`${this.urlFactura}/todos`);
  }

  obtenerFacturaPorid(idFactura: number){
    return this.http.get<any>(`${this.urlFactura}/${idFactura}`)
  }

  obtenerFacturaPorIdVehiculo(idVehiculo: number){
    return this.http.get<any>(`${this.urlFactura}/${idVehiculo}/vehiculo`);
  }

  actualizarEstadoFactura(idPresupuesto: number, estado: string) {
    return this.http.put(
      `${this.urlFactura}/cambiarEstado/${idPresupuesto}`,
      `"${estado}"`,
      { headers: { 'Content-Type': 'application/json' } }
    );
  }

  obtenerUltimasFactura(){
    return this.http.get<any[]>(`${this.urlFactura}/ultimas`);
  }

  obtenerUltimasFacturaCliente(idCliente: number){
    return this.http.get<any[]>(`${this.urlFactura}/ultimas/cliente/${idCliente}`);
  }

  obtenerFacturaPorIdCliente(idCliente: number){
    return this.http.get<any[]>(`${this.urlFactura}/obtenerPorCliente/${idCliente}`);
  }

  obtenerFacturaPorIdClienteEstado(idCliente: number, estado: string) {
  return this.http.get<any[]>(
    `${this.urlFactura}/obtenerPorCliente/${idCliente}/estado`,
    {
        params: { estado }
      }
    );
  }
  
}