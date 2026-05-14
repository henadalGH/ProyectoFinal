import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HeaderAdmin } from "../../Adminstrador/header-admin/header-admin";
import { TurnosService } from '../../ServiciosAdmin/turnos-service';

@Component({
  selector: 'app-asignacion-turnos',
  standalone: true,
  imports: [CommonModule, FormsModule, HeaderAdmin],
  templateUrl: './asignacion-turnos.html',
  styleUrls: ['./asignacion-turnos.css'],
})
export class AsignacionTurnos implements OnInit {

  constructor(private turnoService: TurnosService){}

  obtenerAsignacion: any[] = [];

  mostrarModal: boolean = false;
  turnoSeleccionado: number | null = null;

  fechaMinima: string = '';
  fechaAsignacion: string = '';

  ngOnInit(): void {
    this.asignarTurno();

    const hoy = new Date();
    this.fechaMinima = hoy.toISOString().split('T')[0];
  }

 
  abrirModal(id: number){
    this.turnoSeleccionado = id;
    this.mostrarModal = true;
  }

  cerrarModal(){
    this.mostrarModal = false;
    this.turnoSeleccionado = null;
    this.fechaAsignacion = '';
  }

  asignarTurno(){
    this.turnoService.obtenerPorEstado('PENDIENTE_ASIGNACION')
      .subscribe((respuesta: any) => {
        this.obtenerAsignacion = respuesta;
      });
  }

  // ✅ ACÁ USÁS EL ID + VALIDACIÓN
  asignarFechaTurno(){

    if (!this.turnoSeleccionado) {
      alert('No hay turno seleccionado');
      return;
    }

    if (!this.fechaAsignacion) {
      alert('Seleccioná una fecha');
      return;
    }

    const hoy = new Date().toISOString().split('T')[0];

    if (this.fechaAsignacion < hoy) {
      alert('No podés elegir una fecha pasada');
      return;
    }


    this.turnoService.asignarFecha(this.turnoSeleccionado, this.fechaAsignacion)
      .subscribe({
        next: () => {
          alert('Fecha asignada correctamente');
          this.cerrarModal();
          this.asignarTurno(); // recarga la lista
        },
        error: (err) => {
          console.error(err);
          alert('Error al asignar la fecha');
        }
      });
  }
}