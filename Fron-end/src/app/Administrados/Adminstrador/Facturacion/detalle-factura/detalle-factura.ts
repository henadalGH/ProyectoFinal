import { Component, OnInit } from '@angular/core';
import { HeaderAdmin } from "../../header-admin/header-admin";
import { ActivatedRoute, Router } from '@angular/router';
import { OrdenTrabajoService } from '../../../../Servicio/orden-trabajo-service';
import { Location } from '@angular/common';
import { FacturaServicio } from '../../../../Servicio/factura-servicio';

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
    private ordenService: OrdenTrabajoService,
    private router: Router,
    private location: Location,
    private facturaService: FacturaServicio
  ){}


  datos: any = null;
  detalleOrden: any[]=[];
  contador: number = 0;
  

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

  agregarFila() :void{

    this.detalleOrden.push(
      {
      idDetalle: this.contador++,
      cantidad: 0,
      descripcion: '',
      precioUnitario: 0,
      importe: 0
  
    });
  }

  quitarFila(){
    this.detalleOrden.pop()
  }

  volverAtras(){
    this.location.back();
  }

  
}
