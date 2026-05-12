import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class TurnosService {

  constructor(private http: HttpClient){}


  private urlComun = 'http://localhost:8080/turnos';


  obtenerTurnosAsignacion(){
    return this.http.get<any>(this.urlComun);
  }
  
}
