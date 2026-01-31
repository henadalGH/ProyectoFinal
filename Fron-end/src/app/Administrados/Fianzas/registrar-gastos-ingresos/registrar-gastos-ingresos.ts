import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MovimientoService } from '../../../Servicio/movimiento-service';
import { Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-registrar-gastos-ingresos',
  imports: [FormsModule, RouterLink],
  templateUrl: './registrar-gastos-ingresos.html',
  styleUrl: './registrar-gastos-ingresos.css',
})
export class RegistrarGastosIngresos {

  constructor(
    private movimientoService: MovimientoService,
    private router: Router
  ) {}

  categoria: string = '';
  tipo: string = '';
  concepto: string = '';
  importe: number = 0;


  registrasGastos() {
    this.movimientoService
      .registrarIngresosGasto(this.categoria, this.tipo, this.concepto, this.importe)
      .subscribe(() => {
        this.router.navigate(['/homeMovimientos']);
      });
  }
}
