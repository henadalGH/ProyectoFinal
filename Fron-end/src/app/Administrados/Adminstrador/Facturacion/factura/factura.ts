import { Component, OnInit } from '@angular/core';
import { HeaderAdmin } from "../../header-admin/header-admin";
import { OrdenTrabajoService } from '../../../../Servicio/orden-trabajo-service';
import { RouterLink } from "@angular/router";

@Component({
  selector: 'app-factura',
  standalone: true,
  imports: [HeaderAdmin, RouterLink],
  templateUrl: './factura.html',
  styleUrl: './factura.css',
})
export class Factura implements OnInit{

  constructor(private ordenService: OrdenTrabajoService){}
  
  
  
  ngOnInit(): void {
   this.obtenerOrdenParaFacturar();
  }

  factura: any[] = [];
  
  obtenerOrdenParaFacturar(){

    const estado = 'PARA_FACTURAR';

    this.ordenService.obtenerOrdenPorEstado(estado).subscribe(
      (repuesta: any) =>{
        this.factura = repuesta;
        console.log(this.factura.length);
      });
    }

}
