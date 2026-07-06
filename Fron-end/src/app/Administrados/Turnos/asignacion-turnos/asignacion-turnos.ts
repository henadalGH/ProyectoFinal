import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HeaderAdmin } from "../../Adminstrador/header-admin/header-admin";
import { TurnosService } from '../../ServiciosAdmin/turnos-service';
import { Header } from "../../../header/header";
import { Router } from '@angular/router';

@Component({
  selector: 'app-asignacion-turnos',
  standalone: true,
  imports: [CommonModule, FormsModule, HeaderAdmin, Header],
  templateUrl: './asignacion-turnos.html',
  styleUrls: ['./asignacion-turnos.css'],
})
export class AsignacionTurnos implements OnInit {

  constructor(private turnoService: TurnosService,
    private router: Router
  ){}

  obtenerAsignacion: any[] = [];

  mostrarModal: boolean = false;
  turnoSeleccionado: number | null = null;

  fechaMinima: string = '';
  fechaAsignada: string = '';

  ngOnInit(): void {
    this.asignarTurno();

  }

 
  abrirModal(id: number){
    this.turnoSeleccionado = id;
    this.mostrarModal = true;
  }

  cerrarModal(){
    this.mostrarModal = false;
    this.turnoSeleccionado = null;
    this.fechaAsignada = '';
  }

  asignarTurno(){
    this.turnoService.obtenerPorEstado('PENDIENTE_ASIGNACION')
      .subscribe((respuesta: any) => {
        this.obtenerAsignacion = respuesta;
      });
  }

  
  asignarFechaTurno(){

    if (!this.turnoSeleccionado) {
      alert('No hay turno seleccionado');
      return;
    }

    if (!this.fechaAsignada) {
      alert('Seleccioná una fecha');
      return;
    }

    const hoy = new Date().toISOString().split('T')[0];

    if (this.fechaAsignada < hoy) {
      alert('No podés elegir una fecha pasada');
      return;
    }
    

    this.turnoService.asignarFecha(this.turnoSeleccionado, this.fechaAsignada)
      .subscribe({
        next: () => {
          alert('Fecha asignada correctamente');
          this.cerrarModal();
          this.asignarTurno();
        },
        error: (err) => {
          console.error(err);
          alert('Error al asignar la fecha');
          console.log()
        }
      });
  }


  idTurnoCasual(){
    this.router.navigate(['/turnoCasual']);
  }
}