import { Component, OnInit } from '@angular/core';
import { HeaderEmpleado } from "../header-empleado/header-empleado";
import { CommonModule } from '@angular/common';
import { AuthService } from '../../AuthServicio/auth-service';
import { OrdenTrabajoService } from '../../Servicio/orden-trabajo-service';
import { Router } from '@angular/router';
import { Header } from "../../header/header";

@Component({
  selector: 'app-home-empleado',
  standalone: true,
  imports: [HeaderEmpleado, CommonModule, Header],
  templateUrl: './home-empleado.html',
  styleUrl: './home-empleado.css',
})
export class HomeEmpleado implements OnInit{

  constructor(private authService: AuthService,
    private ordenService: OrdenTrabajoService,
    private router: Router
  ){}
  
  fecha: string = '';
  ordenes: any[]= [];
  motivo: string = '';
  
  ngOnInit(): void {
    this.fecha = new Date().toISOString().split('T')[0];

    const idEmpleado = this.authService.getEntityId();

    if(idEmpleado){

        this.ordenService.obtenerOrdenPorfechaEmpleado(idEmpleado, this.fecha).subscribe(
          (repuesta: any) => {
            this.ordenes = repuesta;
          }
        )
    }
  }


  verOrden(idOrden: number){
    this.router.navigate(['detalleOrden', idOrden]);
  }

  CancelarOrden(idOrden: number){

  }

}
