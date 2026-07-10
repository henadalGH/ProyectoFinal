import { Component, OnInit } from '@angular/core';
import { Headercliente } from "../headercliente/headercliente";
import { Router, RouterLink } from "@angular/router";
import { VehiculoService } from '../../Servicio/vehiculo-service';
import { AuthService } from '../../AuthServicio/auth-service';
import { Location } from '@angular/common';
import { ServiciosService } from '../../Servicio/servicios-service';
import { TurnosService } from '../../Servicio/turnos-service';
import { FormsModule } from '@angular/forms';
import { Header } from "../../header/header";

@Component({
  selector: 'app-mis-vehiculos',
  standalone: true,
  imports: [Headercliente, RouterLink, FormsModule, Header],
  templateUrl: './mis-vehiculos.html',
  styleUrl: './mis-vehiculos.css',
})
export class MisVehiculos implements OnInit {

  constructor(
    private vehiculoService: VehiculoService,
    private authService: AuthService,
    private router: Router,
    private locacion: Location,
    private turnoService: TurnosService
  ){}

  vehiculo: any[] = [];
  servicios: any[] = [];

  vehiculoIdSeleccionado: number | null = null;

  descripcion: string = '';
  idServicio: number | null = null;

  ngOnInit(): void {
    const clienteId = this.authService.getEntityId();

    if (clienteId) {
      this.vehiculoService.obtenerVehiculoCliente(clienteId).subscribe({
        next: (data) => {
          this.vehiculo = data;
        },
        error: (err) => console.error(err)
      });
    }
  }

  
  volverAtras(){
    this.locacion.back();
  }

  verVehiculo(id: number){
    this.router.navigate(['/detalleVehiculo', id]);
  }

  

  // 🔹 Crear turno
  crearTurno(){

    if (!this.descripcion || !this.vehiculoIdSeleccionado || !this.idServicio) {
      console.log("Faltan datos");
      return;
    }

    this.turnoService.solicitarTurno(
      this.descripcion,
      this.vehiculoIdSeleccionado,
      this.idServicio
    ).subscribe({
      next: () => {
        console.log("Turno creado correctamente");
        this.router.navigate(['/misVehiculos']);
      },
      error: (err) => {
        console.error("Error al crear turno", err);
      }
    });
  }
}