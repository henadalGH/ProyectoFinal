import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HeaderAdmin } from "../../Administrados/Adminstrador/header-admin/header-admin";
import { HeaderEmpleado } from "../header-empleado/header-empleado";
import { OrdenTrabajoService } from '../../Servicio/orden-trabajo-service';

@Component({
  selector: 'app-detalle-orden',
  imports: [HeaderEmpleado],
  templateUrl: './detalle-orden.html',
  styleUrl: './detalle-orden.css',
})
export class DetalleOrden implements OnInit{

  constructor(private aRoute: ActivatedRoute,
    private ordenService: OrdenTrabajoService
  ){}

  idOrden: number = 0;
  orden: any[] = [];

  ngOnInit(): void {
    
    this.idOrden = Number (this.aRoute.snapshot.paramMap.get('id'))

    if (this.idOrden) {
       this.ordenService.obtenerOrdenPorId(this.idOrden).subscribe(
        (repuesta: any) => {
          this.orden = repuesta;
        }

       )

    };
  }

}
