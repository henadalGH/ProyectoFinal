import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class MovimientoService {
  
  constructor(private http: HttpClient){}


  private urlRegistroMov = 'http://localhost:8080/movimiento/registro'
  private urlMovimiento = 'http://localhost:8080/movimiento';

  registrarIngresosGasto(categoria: string, tipo: string, concepto: string, importe: number): Observable<any>
  {
    const params = new HttpParams().set('movimientosEnum', tipo);
    const body = {
      categoria, concepto, importe
    }
      return this.http.post( this.urlRegistroMov, body ,{params: params});
  }

  
  

  obtenerPorFecha(fecha: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.urlMovimiento}/${fecha}`);
  }
}
