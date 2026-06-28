import { Component, OnInit } from '@angular/core';
import { HeaderAdmin } from "../../header-admin/header-admin";
import { OrdenTrabajoService } from '../../../../Servicio/orden-trabajo-service';
import { Route, Router, RouterLink } from "@angular/router";
import { FacturaServicio } from '../../../../Servicio/factura-servicio';
import { Header } from "../../../../header/header";

@Component({
  selector: 'app-factura',
  standalone: true,
  imports: [HeaderAdmin, RouterLink, Header],
  templateUrl: './factura.html',
  styleUrl: './factura.css',
})
export class Factura implements OnInit{

  constructor(private ordenService: OrdenTrabajoService
    ,private facturaService: FacturaServicio,
    private route: Router
  ){}

  facturas: any[]= [];
  
  
  
  ngOnInit(): void {
   this.obtenerOrdenParaFacturar();
   this.obtenerFacturas();
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


    obtenerFacturas(){
      this.facturaService.obtenerFacturas().subscribe(
        (repuesta: any) => {
          this.facturas = repuesta;
        });
    }

    verFactura(id: number){
      this.route.navigate(['/verFactura', id]);

    }

}
