import { Component, OnInit } from '@angular/core';
import { OrdenTrabajoService } from '../../../../Servicio/orden-trabajo-service';
import { Header } from "../../../../header/header";
import { HeaderAdmin } from "../../header-admin/header-admin";
import { Router } from '@angular/router';
import { Location } from '@angular/common';

@Component({
  selector: 'app-para-facturar',
  imports: [HeaderAdmin, Header],
  templateUrl: './para-facturar.html',
  styleUrl: './para-facturar.css',
})
export class ParaFacturar implements OnInit{

  constructor(private ordenService: OrdenTrabajoService,
    private router: Router,
    private location: Location
  ){}
    
    
    
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


      crearFactura(idOrden: number){
        this.router.navigate(['/crearFactura', idOrden]);
      }

        volverAtras(){
          this.location.back();
        }

}
