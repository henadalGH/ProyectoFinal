import { Component, OnInit } from '@angular/core';
import { HeaderAdmin } from "../../header-admin/header-admin";
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule, Location } from '@angular/common';
import { FacturaServicio } from '../../../../Servicio/factura-servicio';
import { Header } from "../../../../header/header";
import { AuthService } from '../../../../AuthServicio/auth-service';
import { Headercliente } from "../../../../Cliente/headercliente/headercliente";
import { timeInterval } from 'rxjs';

@Component({
  selector: 'app-ver-factura',
  imports: [HeaderAdmin, Header, Headercliente, CommonModule],
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
  idFactura!: any;
  idAdmin!: any;
  tipoMovimiento: string ='INGRESO';
  concepto: string = 'Se crobó la factura N° ' + this.numeroFacturaFormateado;
  categoria: string = 'COBRO_FACTURA';

  
  
  ngOnInit(): void {
    
    this.idFactura = Number (this.aRouter.snapshot.paramMap.get('id'))
    this.rol = this.authService.getRol();
    this.idAdmin = this.authService.getEntityId();

    if(this.idFactura){
      this.facturaService.obtenerFacturaPorid(this.idFactura).subscribe(
        (repuesta: any) => {
          this.factura = repuesta;
          console.log(this.factura);
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

  pagarFactura(importe: number){

    const body = {
      concepto : this.concepto,
      categoria: this.categoria,
      tipoMovimiento: this.tipoMovimiento,
      importe
    }

    console.log(body);
    this.facturaService.pagarFactura(this.idFactura, this.idAdmin, body).subscribe(
      
    );
  }

  
}
