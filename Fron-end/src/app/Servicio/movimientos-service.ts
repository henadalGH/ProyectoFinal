import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class MovimientosService {

  constructor(private http: HttpClient){}

  private urlMovimiento = "http://localhost:8080/movimiento";

  registrarMovimiento(tipoMovimiento: string, categoria: string, concepto: string, importe: number, idAdmin: number ){
    const body ={
      tipoMovimiento,
      categoria, 
      concepto,
      importe,
      idAdmin
    }
    return this.http.post(`${this.urlMovimiento}/registro`, body);
  }

  obtenerPorFecha(fecha: string){
    return this.http.get<any []>(`${this.urlMovimiento}/${fecha}`);
  }

  obtenerMovimientoPorMes(mes: number, anio: number){
    const params = new HttpParams()
      .set('mes', mes)
      .set('anio', anio);

    return this.http.get<any []>(`${this.urlMovimiento}/mes`, {params});
  }

  // ==========================================
  //  MÉTODOS NUEVOS PARA MÉTRICAS DEL DASHBOARD
  // ==========================================

  totalIngresos(desde: string, hasta: string): Observable<number> {
    const params = new HttpParams()
      .set('desde', desde)
      .set('hasta', hasta);

    return this.http.get<number>(`${this.urlMovimiento}/total-ingresos`, { params });
  }

  totalEgresos(desde: string, hasta: string): Observable<number> {
    const params = new HttpParams()
      .set('desde', desde)
      .set('hasta', hasta);

    return this.http.get<number>(`${this.urlMovimiento}/total-egresos`, { params });
  }

  balance(desde: string, hasta: string): Observable<number> {
    const params = new HttpParams()
      .set('desde', desde)
      .set('hasta', hasta);

    return this.http.get<number>(`${this.urlMovimiento}/balance`, { params });
  }

  obtenerTotalIngresos(desde: string, hasta: string): Observable<number> {
      const params = new HttpParams()
        .set('desde', desde)
        .set('hasta', hasta);
  
      return this.http.get<number>(`${this.urlMovimiento}/total-ingresos`, { params });
    }

    obtenerUltimoMovimiento(){
      return this.http.get<any []>(`${this.urlMovimiento}/ultimosMovimiento`);
    }
}