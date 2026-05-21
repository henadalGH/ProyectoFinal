import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { Vehiculoservice } from './vehiculoservice';
import { Location } from '@angular/common';
import { AuthService } from '../../../AuthServicio/auth-service';

@Component({
  selector: 'app-registar-vehiculo',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './registar-vehiculo.html',
  styleUrl: './registar-vehiculo.css',
})
export class RegistarVehiculo {

  marca: string = "";
  modelo: string = "";
  patente: string = "";
  anio: string = "";
  kilometreje: number = 0;

  constructor(
    private vehiculoService: Vehiculoservice,
    private router: Router,
    private locacion: Location,
    private authService: AuthService
  ) {}

  volverAtras() {
    this.locacion.back();
  }

  agregarVehiculo() {

    // ✅ acá obtenés el ID correctamente
    const clienteId = this.authService.getEntityId();

    if (!clienteId) {
      console.error("❌ No se pudo obtener el ID del usuario");
      return;
    }

    this.vehiculoService.agregarVehiculo(
      this.marca,
      this.modelo,
      this.patente,
      this.kilometreje,
      this.anio,
      clienteId // 👈 ahora sí correcto
    ).subscribe({
      next: () => {
        console.log("✅ Vehículo agregado");

        const rol = this.authService.getRol();

        if (rol === 'ROLE_ADMIN') {
          this.router.navigate(['/homeAdmin']);
        } else if (rol === 'ROLE_CLIENTE') {
          this.router.navigate(['/homeCliente']);
        } else {
          this.locacion.back();
        }
      },
      error: (err) => {
        console.error("❌ Error al agregar vehículo:", err);
      }
    });
  }
}