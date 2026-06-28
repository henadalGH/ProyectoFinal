import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { HeaderAdmin } from '../../Adminstrador/header-admin/header-admin';
import { TurnosService } from '../../../Servicio/turnos-service';
import { EmpleadoService } from '../../../Servicio/empleado-service';
import { OrdenTrabajoService } from '../../../Servicio/orden-trabajo-service';
import { Header } from "../../../header/header";

@Component({
  selector: 'app-home-ordenes',
  standalone: true,
  imports: [CommonModule, HeaderAdmin, FormsModule, Header],
  templateUrl: './home-ordenes.html',
  styleUrls: ['./home-ordenes.css']
})
export class HomeOrdenes implements OnInit {

  turnos: any[] = [];
  empleados: any[] = [];

  modal = false;

  idTurno: number | null = null;
  idEmpleado: number | null = null;
  prioridad = '';

  constructor(
    private turnoService: TurnosService,
    private empleadoService: EmpleadoService,
    private ordenService: OrdenTrabajoService
  ) {}

  ngOnInit(): void {
    this.obtenerTurnosComfirmados();
    this.obtenerEpleadoMecanico();
  }

  obtenerTurnosComfirmados(): void {

    this.turnoService.obtenerPorEstado('CONFIRMADO').subscribe({
      next: (resp: any) => {
        this.turnos = resp;
      },
      error: (err) => {
        console.error(err);
      }
    });

  }

  obtenerEpleadoMecanico(): void {

    this.empleadoService.obtenerEmpleadoMEcanico().subscribe({
      next: (resp: any) => {
        this.empleados = resp;
      },
      error: (err) => {
        console.error(err);
      }
    });

  }

  abrilModal(idTurno: number): void {

    this.idTurno = idTurno;
    this.idEmpleado = null;
    this.prioridad = '';

    this.modal = true;

    console.log('Turno seleccionado:', this.idTurno);

  }

  cerrarmodal(): void {

    this.modal = false;

    this.idEmpleado = null;
    this.prioridad = '';

  }

  AsignarOrden(): void {

    console.log('Turno:', this.idTurno);
    console.log('Empleado:', this.idEmpleado);
    console.log('Prioridad:', this.prioridad);

    if (this.idTurno === null) {
      alert('Debe seleccionar un turno');
      return;
    }

    if (this.idEmpleado === null) {
      alert('Debe seleccionar un empleado');
      return;
    }

    if (!this.prioridad) {
      alert('Debe seleccionar una prioridad');
      return;
    }

    this.ordenService
      .asignarOrden(
        this.idTurno,
        this.idEmpleado,
        this.prioridad
      )
      .subscribe({
        next: () => {

          alert('Orden asignada correctamente');

          this.cerrarmodal();
          this.obtenerTurnosComfirmados();

        },
        error: (err) => {

          console.error(err);
          alert('Error al asignar la orden');

        }
      });

  }

}