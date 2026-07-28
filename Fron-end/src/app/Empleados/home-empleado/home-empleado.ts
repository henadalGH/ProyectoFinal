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
  ordenesSinTerminar: any[]= [];
  motivo: string = '';
  
  ngOnInit(): void {
    this.fecha = new Date().toLocaleDateString('sv-SE', {
  timeZone: 'America/Argentina/Buenos_Aires'
});

    const idEmpleado = this.authService.getEntityId();

    if(idEmpleado){

        this.ordenService.obtenerOrdenPorfechaEmpleado(idEmpleado, this.fecha).subscribe(
          (repuesta: any) => {
            this.ordenes = repuesta;
            console.log(this.ordenes)
          }
        )
    }

    if(idEmpleado){
      const estado: string = 'ASIGNADA';

      this.ordenService.obtenerOrdenSinTerminar(idEmpleado, estado).subscribe(
        (repuesta: any) => {
          this.ordenesSinTerminar = repuesta;
          console.log(this.ordenesSinTerminar)
        }
      )
    }
  }


  verOrden(idOrden: number){
    this.router.navigate(['detalleOrden', idOrden]);
  }

  cancelarOrden(idOrden: number) {

  if (confirm("¿Está seguro de cancelar la orden?")) {

    this.ordenService.cancelarOrden(idOrden, "Cliente no asistió al turno").subscribe({
      next: () => {
        alert("Orden cancelada correctamente");
      },
      error: () => {
        alert("Ocurrió un error al cancelar la orden");
      }
    });

  }

}


}
