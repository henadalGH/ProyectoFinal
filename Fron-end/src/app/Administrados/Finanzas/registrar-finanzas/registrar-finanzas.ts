import { Component } from '@angular/core';
import { Header } from "../../../header/header";
import { HeaderAdmin } from "../../Adminstrador/header-admin/header-admin";
import { FormsModule } from '@angular/forms';
import { MovimientosService } from '../../../Servicio/movimientos-service';
import { Router } from '@angular/router';
import { AuthService } from '../../../AuthServicio/auth-service';
import { Location } from '@angular/common';

@Component({
  selector: 'app-registrar-finanzas',
  imports: [Header, HeaderAdmin, FormsModule],
  templateUrl: './registrar-finanzas.html',
  styleUrl: './registrar-finanzas.css',
})
export class RegistrarFinanzas {

  constructor(
    private movimientoService: MovimientosService,
    private router: Router,
    private authService: AuthService,
    private location: Location
  ) {}

  movimiento: string = '';
  categoria: string = '';
  concepto: string = '';
  importe: number | null = null;
  importeVista: string = '';

  // Variables para mostrar errores
  errorMovimiento = false;
  errorCategoria = false;
  errorConcepto = false;
  errorImporte = false;

  volverAtras() {
    this.location.back();
  }

  validarFormulario(): boolean {

    // Limpiar errores anteriores
    this.errorMovimiento = false;
    this.errorCategoria = false;
    this.errorConcepto = false;
    this.errorImporte = false;

    let valido = true;

    if (!this.movimiento || this.movimiento.trim() === '') {
      this.errorMovimiento = true;
      valido = false;
    }

    if (!this.categoria || this.categoria.trim() === '') {
      this.errorCategoria = true;
      valido = false;
    }

    if (!this.concepto || this.concepto.trim() === '') {
      this.errorConcepto = true;
      valido = false;
    }

    if (this.importe == null || this.importe <= 0) {
      this.errorImporte = true;
      valido = false;
    }

    return valido;
  }

  registrarMovimiento() {

    if (!this.validarFormulario()) {
      return;
    }

    const idAdmin = Number(this.authService.getEntityId());

    this.movimientoService
      .registrarMovimiento(
        this.movimiento,
        this.categoria,
        this.concepto,
        this.importe!,
        idAdmin
      )
      .subscribe({
        next: () => {
          this.router.navigate(['/homeFinanzas']);
        },
        error: (error) => {
          console.error('Error al registrar movimiento', error);
        }
      });
  }

  formatearImporte(event: any) {

  let valor = event.target.value;

  valor = valor.replace(/[^\d,]/g, '');

  const partes = valor.split(',');

  let entero = partes[0];
  let decimal = partes[1] ?? '';

  decimal = decimal.substring(0, 2);

  entero = entero.replace(/\./g, '');

  this.importe = Number(
    entero + '.' + decimal
  );

  entero = entero.replace(/\B(?=(\d{3})+(?!\d))/g, '.');

  this.importeVista = decimal
    ? `${entero},${decimal}`
    : entero;
}
}