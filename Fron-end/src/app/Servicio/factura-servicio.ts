import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class FacturaServicio {

  constructor(private http: HttpClient){ }

  private urlFactura = 'http://localhost:8080/presupuesto';

  
  crearFactura( factura: any){
  
    return this.http.post(`${this.urlFactura}`, factura);
  }
  
}
