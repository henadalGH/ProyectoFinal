import { Component } from '@angular/core';
import { Header } from "../../../header/header";
import { HeaderAdmin } from "../../Adminstrador/header-admin/header-admin";
import { FormsModule } from '@angular/forms';
import { MovimientosService } from '../../../Servicio/movimientos-service';
import { Router } from '@angular/router';
import { AuthService } from '../../../AuthServicio/auth-service';

@Component({
  selector: 'app-registrar-finanzas',
  imports: [Header, HeaderAdmin, FormsModule],
  templateUrl: './registrar-finanzas.html',
  styleUrl: './registrar-finanzas.css',
})
export class RegistrarFinanzas {

  constructor(private movimientoService: MovimientosService,
    private router: Router,
    private authService: AuthService
  ){}


  movimiento: string = '';
  categoria: string = '';
  concepto: string = '';
  importe: number = 0;

   

  registrarMovimiento(){

    const idAdmin = Number( this.authService.getEntityId());

    this.movimientoService.registrarMovimiento(this.movimiento, this.categoria, this.concepto, this.importe, idAdmin).subscribe(

      (next: any) => {
        this.router.navigate(['/homeFinanzas']);
      });

  }

}
