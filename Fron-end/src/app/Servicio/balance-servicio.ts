import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class BalanceServicio {

  private urlMovimiento = "http://localhost:8080/movimiento";

  constructor(private http: HttpClient) {}

  obtenerBalance(anio: number){
    const params = new HttpParams()
      .set('anio', anio);

    return this.http.get( `${this.urlMovimiento}/balance-mensual`, {params});


  }


}
