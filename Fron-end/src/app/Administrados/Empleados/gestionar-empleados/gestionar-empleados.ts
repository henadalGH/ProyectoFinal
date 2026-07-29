import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { EmpleadoService } from '../../../Servicio/empleado-service';
import { Router, RouterLink } from "@angular/router";
import { HeaderAdmin } from '../../Adminstrador/header-admin/header-admin';
import { Header } from "../../../header/header";

@Component({
  selector: 'app-gestionar-empleados',
  imports: [HeaderAdmin, RouterLink, Header, FormsModule],
  templateUrl: './gestionar-empleados.html',
  styleUrl: './gestionar-empleados.css',
})
export class GestionarEmpleados implements OnInit {
  empleados: any[] = [];
  error: boolean = false; // Nueva variable de estado
  searchName: string = '';
  filterCargo: string = '';
  filterActivo: string = '';

  constructor(
    private empleadosService: EmpleadoService,
    private router: Router
  ) {}

  get empleadosFiltrados(): any[] {
    return this.empleados.filter((empl) => {
      const nombreCompleto = `${empl.usuario?.nombre ?? ''} ${empl.usuario?.apellido ?? ''}`.toLowerCase();
      const buscaNombre = this.searchName.trim().toLowerCase();
      const coincideNombre = !buscaNombre || nombreCompleto.includes(buscaNombre);
      const cargoEmpleado = (empl.cargo?.cargo ?? '').toLowerCase();
      const coincideCargo = !this.filterCargo || cargoEmpleado === this.filterCargo.toLowerCase();
      const coincideActivo = !this.filterActivo ||
        (this.filterActivo === 'si' && empl.activo === true) ||
        (this.filterActivo === 'no' && empl.activo === false);
      return coincideNombre && coincideCargo && coincideActivo;
    });
  }

  ngOnInit(): void {
    this.obtenerEmpleado();
  }

  obtenerEmpleado() {
    this.empleadosService.obtenerEmpleados().subscribe({
      next: (respuesta: any) => {
        this.empleados = respuesta;
        this.error = false;
        this.searchName = '';
        this.filterCargo = '';
        this.filterActivo = '';
      },
      error: (err) => {
        console.error('Error al cargar empleados', err);
        this.error = true; // Activamos el estado de error
      }
    });
  }

  verEmpleado(id: number) {
    this.router.navigate(['/verEmpleado', id]);
  }

  desactivarEmpleado(idEmpleado: number) {

  if (!confirm("¿Está seguro de que desea dar de baja a este empleado?")) {
    return;
  }

  this.empleadosService.activaDesactivarEmpleado(idEmpleado, false).subscribe({
    next: () => {
      this.obtenerEmpleado();
      alert("El empleado fue desactivado correctamente");
    },
    error: (err) => {
      console.error("Error al desactivar el empleado", err);
      this.error = true;
    }
  });
}

activarEmpleado(idEmpleado: number) {

  if (!confirm("¿Está seguro de que desea darle de alta a este empleado?")) {
    return;
  }

  this.empleadosService.activaDesactivarEmpleado(idEmpleado, true).subscribe({
    next: () => {
      this.obtenerEmpleado();
      alert("El empleado fue activado correctamente");
    },
    error: (err) => {
      console.error("Error al activar el empleado", err);
      this.error = true;
    }
  });
}
}
