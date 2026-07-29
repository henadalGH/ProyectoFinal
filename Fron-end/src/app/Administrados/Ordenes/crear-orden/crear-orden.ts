import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { HeaderAdmin } from '../../Adminstrador/header-admin/header-admin';
import { Header } from '../../../header/header';

import { OrdenTrabajoService } from '../../../Servicio/orden-trabajo-service';
import { EmpleadoService } from '../../../Servicio/empleado-service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-crear-orden',
  imports: [HeaderAdmin, Header, FormsModule, CommonModule],
  templateUrl: './crear-orden.html',
  styleUrl: './crear-orden.css',
})
export class CrearOrden implements OnInit {

  constructor(
    private ordeServicio: OrdenTrabajoService,
    private empleadoServicio: EmpleadoService
  ) {}

  ordenes: any[] = [];
  ordenesFiltradas: any[] = [];
  empleados: any[] = [];

  modal = false;

  idOrden: number | null = null;
  idEmpleado: number | null = null;

  empleadoFiltro = '';
  fechaDesde: string | null = null;
  fechaHasta: string | null = null;

  ngOnInit(): void {
    this.obtenerOrdenesPendientes();
  }

  obtenerOrdenesPendientes(): void {
    this.ordeServicio.obtenerOrdenPorEstado('ASIGNADA').subscribe(
      (respuesta: any[]) => {
        this.ordenes = respuesta;
        console.log('Ordenes obtenidas:', this.ordenes);
        this.ordenesFiltradas = [...respuesta];
      }
    );
  }

  aplicarFiltros(): void {
    const textoEmpleado = this.empleadoFiltro.trim().toLowerCase();
    const fechaDesde = this.fechaDesde ? new Date(this.fechaDesde) : null;
    const fechaHasta = this.fechaHasta ? new Date(this.fechaHasta) : null;

    this.ordenesFiltradas = this.ordenes.filter((ord) => {
      const nombreEmpleado = (ord.nombreEmpleado || '').toLowerCase();
      const cumpleEmpleado = !textoEmpleado || nombreEmpleado.includes(textoEmpleado);

      if (!cumpleEmpleado) {
        return false;
      }

      if (!ord.fechaEmicion) {
        return true;
      }

      const fechaOrden = new Date(ord.fechaEmicion);

      if (fechaDesde && fechaOrden < fechaDesde) {
        return false;
      }

      if (fechaHasta) {
        const fechaHastaFinal = new Date(this.fechaHasta + 'T23:59:59');
        if (fechaOrden > fechaHastaFinal) {
          return false;
        }
      }

      return true;
    });
  }

  limpiarFiltros(): void {
    this.empleadoFiltro = '';
    this.fechaDesde = null;
    this.fechaHasta = null;
    this.ordenesFiltradas = [...this.ordenes];
  }

  obtenerEmpleados(): void {
    this.empleadoServicio.obtenerEmpleadoMEcanico().subscribe(
      (respuesta: any[]) => {
        this.empleados = respuesta;
      }
    );
  }

  abrirModal(idOrden: number): void {
    this.idOrden = idOrden;
    this.idEmpleado = null;
    this.modal = true;

    this.obtenerEmpleados();
  }

  cerrarModal(): void {
    this.modal = false;
    this.idOrden = null;
    this.idEmpleado = null;
  }

  reasignarOrden(): void {
    if (this.idOrden == null || this.idEmpleado == null) {
      return;
    }

    this.ordeServicio.reasiganrOrden(this.idOrden, this.idEmpleado).subscribe({
      next: () => {
        this.cerrarModal();
        this.obtenerOrdenesPendientes();
      },
      error: (error) => {
        console.error('Error al reasignar orden:', error);
      }
    });
  }

}