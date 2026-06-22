import { Component, OnInit } from '@angular/core';
import { HeaderAdmin } from "../../header-admin/header-admin";
import { ActivatedRoute } from '@angular/router';
import { OrdenTrabajoService } from '../../../../Servicio/orden-trabajo-service';

@Component({
  selector: 'app-detalle-factura',
  standalone: true,
  imports: [HeaderAdmin],
  templateUrl: './detalle-factura.html',
  styleUrl: './detalle-factura.css',
})
export class DetalleFactura implements OnInit{

  constructor(
    private aRouter: ActivatedRoute,
    private ordenService: OrdenTrabajoService
  ){}


  datos: any = null;
  detalleOrden: any[]=[];

  ngOnInit(): void {
    
    const idOrden = Number (this.aRouter.snapshot.paramMap.get('id'));

    if(idOrden){
      this.ordenService.obtenerOrdenPorId(idOrden).subscribe(
        (repusta: any) => {
          this.datos = repusta;
        });
    }

    if(idOrden){

      this.ordenService.obtenerDetalleOrden(idOrden).subscribe(
      
        (repuesta:any) =>{
          this.detalleOrden = repuesta;
        }
      
    )

    }
  }

}
