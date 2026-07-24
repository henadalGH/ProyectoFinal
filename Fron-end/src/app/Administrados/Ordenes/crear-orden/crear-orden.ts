import { Component, OnInit } from '@angular/core';
import { HeaderAdmin } from "../../Adminstrador/header-admin/header-admin";
import { ActivatedRoute, Router } from '@angular/router';
import { OrdenTrabajoService } from '../../../Servicio/orden-trabajo-service';

@Component({
  selector: 'app-crear-orden',
  imports: [HeaderAdmin],
  templateUrl: './crear-orden.html',
  styleUrl: './crear-orden.css',
})
export class CrearOrden implements OnInit{
  
  constructor(private router: Router,
    private actRouter: ActivatedRoute,
    private ordeServicio: OrdenTrabajoService
  ){}
 
  //Variables
  ordenes: any[]= [];

  ngOnInit(): void {
    this.obtenerOrdenesPEndientes();
  }

  obtenerOrdenesPEndientes(){
    const estado = 'ASIGNADA'

    this.ordeServicio.obtenerOrdenPorEstado(estado).subscribe(
      (repuesta: any) =>{
        this.ordenes = repuesta;
        console.log(repuesta);
      });
  }



  

}
