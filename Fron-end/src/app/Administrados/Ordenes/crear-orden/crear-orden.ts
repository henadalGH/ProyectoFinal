import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { HeaderAdmin } from '../../Adminstrador/header-admin/header-admin';
import { Header } from '../../../header/header';

import { OrdenTrabajoService } from '../../../Servicio/orden-trabajo-service';
import { EmpleadoService } from '../../../Servicio/empleado-service';

@Component({
  selector: 'app-crear-orden',
  imports: [HeaderAdmin, Header, FormsModule],
  templateUrl: './crear-orden.html',
  styleUrl: './crear-orden.css',
})
export class CrearOrden implements OnInit {

  constructor(
    private ordeServicio: OrdenTrabajoService,
    private empleadoServicio: EmpleadoService
  ) {}

  ordenes: any[] = [];
  empleados: any[] = [];

  modal = false;

  idOrden: number | null = null;
  idEmpleado: number | null = null;

  ngOnInit(): void {
    this.obtenerOrdenesPendientes();
  }

  obtenerOrdenesPendientes(): void {
    this.ordeServicio.obtenerOrdenPorEstado('ASIGNADA').subscribe(
      (respuesta: any[]) => {
        this.ordenes = respuesta;
        console.log(this.ordenes)
      }
    );
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

    console.log('Id Orden:', this.idOrden);
    console.log('Id Empleado:', this.idEmpleado);

    // Acá va el servicio para reasignar la orden.
  }

}