import { Component, OnInit } from '@angular/core';
import { HeaderAdmin } from "../../header-admin/header-admin";
import { ActivatedRoute, Router } from '@angular/router';
import { Location } from '@angular/common';
import { FacturaServicio } from '../../../../Servicio/factura-servicio';
import { Header } from "../../../../header/header";
import { AuthService } from '../../../../AuthServicio/auth-service';
import { Headercliente } from "../../../../Cliente/headercliente/headercliente";

@Component({
  selector: 'app-ver-factura',
  imports: [HeaderAdmin, Header, Headercliente],
  templateUrl: './ver-factura.html',
  styleUrl: './ver-factura.css',
})
export class VerFactura implements OnInit{
  
  constructor(
    private aRouter: ActivatedRoute,
    private location: Location,
    private facturaService: FacturaServicio,
    private router: Router,
    private authService: AuthService
  ){ }


  factura: any = null;
  numeroFacturaFormateado = '';
  rol!: any;
  
  ngOnInit(): void {
    
    const idFactura = Number (this.aRouter.snapshot.paramMap.get('id'))
    this.rol = this.authService.getRol();
  
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


  enviarFactura(idFactura: number){

    const estado = 'ENVIADO';

    this.facturaService.actualizarEstadoFactura(idFactura, estado).subscribe(
      {
        next: () => {
          this.router.navigate(['/factura']);
        }});
    }

    volverAtras(){
      this.location.back();
    }

  
}
