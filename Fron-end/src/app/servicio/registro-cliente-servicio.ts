import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class RegistroClienteServicio {
  
  constructor(private http: HttpClient){}

  private urlApi= "http://localhost:8080/registro/nuevo"

  //metodo que crea un cliente
  crearCliente(nombre: string, apellido: string, email: string, password: string, 
    contacto: string, direccion: string, rol: string): Observable<any>
    {
      const params = new HttpParams().set('rolesEnum', rol);

      const body = { 
        nombre, 
        apellido, 
        email, 
        password, 
        contacto, 
        direccion 
      };

      return this.http.post<any>(this.urlApi, body, {params: params});
    }
  
}
