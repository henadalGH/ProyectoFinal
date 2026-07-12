import { Component, OnInit } from '@angular/core';
import { HeaderEmpleado } from "../header-empleado/header-empleado";
import { Header } from "../../header/header";
import { OrdenTrabajoService } from '../../Servicio/orden-trabajo-service';
import { Location } from '@angular/common';
import { AuthService } from '../../AuthServicio/auth-service';

@Component({
  selector: 'app-ordenes-proximas',
  imports: [HeaderEmpleado, Header],
  templateUrl: './ordenes-proximas.html',
  styleUrl: './ordenes-proximas.css',
})
export class OrdenesProximas  implements OnInit{

  constructor(private ordeservicio: OrdenTrabajoService,
    private location: Location,
    private authService: AuthService  ){}

    ordenFutura: any[]= [];

  ngOnInit(): void {
    
    const idEmpleado = this.authService.getEntityId();

    if(idEmpleado){
      const hoy = new Date().toISOString().split('T')[0];
      this.ordeservicio.obtenerOrdenTrabajoFuturasPorempleado(idEmpleado, hoy).subscribe(

        (respuesta: any[]) =>{
          this.ordenFutura = respuesta;
        }); }
    
      }

}
