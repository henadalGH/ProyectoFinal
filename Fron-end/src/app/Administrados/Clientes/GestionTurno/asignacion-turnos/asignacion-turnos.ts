import { Component, OnInit } from '@angular/core';
import { HeaderAdmin } from "../../header-admin/header-admin";
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms'; // 👈 IMPORTANTE por ngModel

@Component({
  selector: 'app-asignacion-turnos',
  standalone: true,
  imports: [CommonModule, FormsModule, HeaderAdmin],
  templateUrl: './asignacion-turnos.html',
  styleUrls: ['./asignacion-turnos.css'],
})
export class AsignacionTurnos implements OnInit {

  asignacion: any[] = [];

  mostrarModal: boolean = false;
  turnoSeleccionado: any = null;

  // 👇 para los inputs
  fechaSeleccionada: string = '';
  horaSeleccionada: string = '';

  ngOnInit(): void {
    // 👇 datos de prueba (para que veas algo)
    this.asignacion = [
      {
        id: 1,
        nombreCliente: 'Hernan',
        apellidoCliente: 'Nadal',
        contacto: '3425623569',
        nombreServicio: 'Cambio de aceite y filtros',
        marca: 'Ford',
        modelo: 'Fiesta',
        descripcion: 'prueba'
      }
    ];
  }

  abrirModal(turno: any){
    this.turnoSeleccionado = turno;
    this.mostrarModal = true;
  }

  cerrarModal(){
    this.mostrarModal = false;
    this.turnoSeleccionado = null;
    this.fechaSeleccionada = '';
    this.horaSeleccionada = '';
  }

  // 👇 SOLO PARA QUE NO ROMPA
  asignarTurno(){
    if(!this.fechaSeleccionada || !this.horaSeleccionada){
      alert('Seleccioná fecha y hora');
      return;
    }

    console.log('Turno asignado:', {
      turno: this.turnoSeleccionado,
      fecha: this.fechaSeleccionada,
      hora: this.horaSeleccionada
    });

    this.cerrarModal();
  }
}