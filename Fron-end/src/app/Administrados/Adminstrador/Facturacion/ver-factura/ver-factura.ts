import { Component, OnInit } from '@angular/core';
import { HeaderAdmin } from "../../header-admin/header-admin";
import { ActivatedRoute, Router } from '@angular/router';
import { Location } from '@angular/common';
import { FacturaServicio } from '../../../../Servicio/factura-servicio';

@Component({
  selector: 'app-ver-factura',
  imports: [HeaderAdmin],
  templateUrl: './ver-factura.html',
  styleUrl: './ver-factura.css',
})
export class VerFactura implements OnInit{
  
  constructor(
    private aRouter: ActivatedRoute,
    private location: Location,
    private facturaService: FacturaServicio,
  ){ }


  factura: any = null;
  numeroFacturaFormateado = '';
  
  ngOnInit(): void {
    
    const idFactura = Number (this.aRouter.snapshot.paramMap.get('id'))
  
    if(idFactura){
      this.facturaService.obtenerFacturaPorid(idFactura).subscribe(
        (repuesta: any) => {
          this.factura = repuesta;
          this.numeroFacturaFormateado ='0001-' +
          String(this.factura.numeroPresupuesto).padStart(8, '0');
        }
      )
    }
  }

  
}
