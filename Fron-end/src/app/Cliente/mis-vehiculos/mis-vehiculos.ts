import { Component, OnInit } from '@angular/core';
import { Headercliente } from "../headercliente/headercliente";
import { Router, RouterLink } from "@angular/router";
import { VehiculoService } from '../../Servicio/vehiculo-service';
import { AuthService } from '../../AuthServicio/auth-service';
import { Location } from '@angular/common';
import { ServiciosService } from '../../Servicio/servicios-service';
import { TurnosService } from '../../Servicio/turnos-service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-mis-vehiculos',
  standalone: true,
  imports: [Headercliente, RouterLink, FormsModule],
  templateUrl: './mis-vehiculos.html',
  styleUrl: './mis-vehiculos.css',
})
export class MisVehiculos implements OnInit {

  constructor(
    private vehiculoService: VehiculoService,
    private authService: AuthService,
    private router: Router,
    private locacion: Location,
    private servicioService: ServiciosService,
    private turnoService: TurnosService
  ){}

  vehiculo: any[] = [];
  servicios: any[] = [];

  mostrarModal: boolean = false;
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

  // 🔹 Navegación
  verMiHistorial(id: number){
    this.router.navigate(['/miHistorial', id]);
  }

  verDetalles(id:number){
    this.router.navigate(['/verVehiculo', id])
  }

  volverAtras(){
    this.locacion.back();
  }

  // 🔹 Modal
  abrirModel(id: number){
    this.vehiculoIdSeleccionado = id;
    this.mostrarModal = true;

    this.servicioService.obtenerServicios().subscribe({
      next: (datos: any) => {
        this.servicios = datos;
      },
      error: (err) => console.error(err)
    });
  }

  cerrarModel(){
    this.mostrarModal = false;

    // limpiar datos
    this.descripcion = '';
    this.idServicio = null;
    this.vehiculoIdSeleccionado = null;
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
        this.cerrarModel();
        this.router.navigate(['/misVehiculos']);
      },
      error: (err) => {
        console.error("Error al crear turno", err);
      }
    });
  }
}