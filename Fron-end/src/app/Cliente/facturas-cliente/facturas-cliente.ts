import { Component, OnInit } from '@angular/core';
import { Headercliente } from "../headercliente/headercliente";
import { Header } from "../../header/header";
import { FacturaServicio } from '../../Servicio/factura-servicio';
import { AuthService } from '../../AuthServicio/auth-service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-facturas-cliente',
  imports: [Headercliente, Header],
  templateUrl: './facturas-cliente.html',
  styleUrl: './facturas-cliente.css',
})
export class FacturasCliente implements OnInit{

  constructor(private facturaService: FacturaServicio,
    private authService: AuthService,
    private router: Router
  ){}

  rol!: any;
  numeroFacturaFormateado = '';
  ultimas: any[] =[];


  ngOnInit(): void {
    
    const idCliente = this.authService.getEntityId();
    this.rol = this.authService.getRol();

    if(idCliente){
      const estado = 'ENVIADO'
      this.facturaService.obtenerFacturaPorIdClienteEstado(idCliente, estado).subscribe(
        (repuesta: any) => {
          this.facturas = repuesta;
          this.numeroFacturaFormateado ='0001-' +
          String(this.facturas[0].numeroPresupuesto).padStart(8, '0');
        })}

        if(idCliente){
          this.facturaService.obtenerUltimasFacturaCliente(idCliente).subscribe(
            (repuesta: any) =>{
              this.ultimas = repuesta;
              console.log(this.ultimas)
            }
          )
        }
      }

  facturas: any[]= [];

  verFactura(idFactura: number){
    this.router.navigate(['/verFactura', idFactura]);
  }
  
}
