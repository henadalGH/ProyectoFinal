import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ClienteService {
  
  constructor(private http: HttpClient){}

    private urlApi= "http://localhost:8080/clientes/todos";
    

    obtenerClientes()
    {
      return this.http.get(this.urlApi);
    }


    agregarCliente(nombre: string, apellido: string, 
      email: string, password: string, contacto: string,
      domicilio: string, rol: string ): Observable<any>{

        return this.http.post<any>(this.urlApi, {})

    }
}
