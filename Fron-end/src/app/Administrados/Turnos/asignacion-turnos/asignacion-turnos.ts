import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HeaderAdmin } from "../../Adminstrador/header-admin/header-admin";

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


  ngOnInit(): void {
  
  }

  abrirModal(turno: any){
    this.turnoSeleccionado = turno;
    this.mostrarModal = true;
  }

  cerrarModal(){
    this.mostrarModal = false;
    this.turnoSeleccionado = null;
  }

  // 👇 SOLO PARA QUE NO ROMPA
  asignarTurno(){
  }
}