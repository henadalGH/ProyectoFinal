import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class MovimientosService {

  constructor(private http: HttpClient){}

  private urlMovimiento = "http://localhost:8080/movimiento";

  registrarMovimiento(tipoMovimiento: string,categoria: string, concepto: string, importe: number, idAdmin: number ){

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

  obtenerMovimientoPorMes(mes :number, anio: number){

    const params = new HttpParams()
    .set('mes', mes)
    .set('anio', anio)


    return this.http.get<any []>(`${this.urlMovimiento}/mes`, {params})
  }

  


  
}
