import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { EmpleadoService } from '../../../Servicio/empleado-service';
import { HeaderAdmin } from '../../Adminstrador/header-admin/header-admin';
import { Header } from '../../../header/header';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-ver-empleado',
  standalone: true,
  templateUrl: './ver-empleado.html',
  styleUrls: ['./ver-empleado.css'],
  imports: [HeaderAdmin, RouterLink, Header, FormsModule, CommonModule],
})
export class VerEmpleado implements OnInit {

  id!: number;
  empleado: any;

  // Copia para detectar cambios
  empleadoOriginal: any;

  // Un solo modo edición
  editarTodo = false;

  // Bonificación solo en la vista / localStorage
  bonificacion = 0;
  bonificacionOriginal = 0;


  constructor(
    private route: ActivatedRoute,
    private empleadoService: EmpleadoService
  ) {}


  ngOnInit(): void {

    this.id = Number(this.route.snapshot.paramMap.get('id'));


    const hoy = new Date();

    hoy.setFullYear(hoy.getFullYear() - 18);

    this.fechaMaxima = hoy.toISOString().split('T')[0];


    this.cargarEmpleado();
  }



  cargarEmpleado(): void {

    this.empleadoService.verEmpleado(this.id).subscribe({

      next: (data: any) => {

        this.empleado = data;
        this.empleadoOriginal = structuredClone(data);


        const sueldoGuardado = localStorage.getItem(
          'sueldoEmpleado_' + this.empleado.id
        );

        if (sueldoGuardado !== null) {
          this.empleado.sueldo = Number(sueldoGuardado);
          this.empleadoOriginal.sueldo = Number(sueldoGuardado);
        }

        const bonificacionGuardada = localStorage.getItem(
          'bonificacionEmpleado_' + this.empleado.id
        );

        if (bonificacionGuardada !== null) {
          this.bonificacion = Number(bonificacionGuardada);
          this.bonificacionOriginal = Number(bonificacionGuardada);
        } else {
          this.bonificacion = 0;
          this.bonificacionOriginal = 0;
        }

      },


      error: (error) => {

        console.error(error);

      }

    });

  }

  activarEdicion(): void {
    this.editarTodo = true;
  }

  guardarCambios(): void {
    if (!confirm('¿Guardar cambios?')) {
      return;

    }

    if (!this.validarMayorDeEdad(this.empleado?.fechaNacimiento)) {
      return;

    }

    const cargo =
      this.empleado.cargo !== this.empleadoOriginal.cargo
        ? this.empleado.cargo
        : undefined;

    const email =
      this.empleado.email !== this.empleadoOriginal.email
        ? this.empleado.email
        : undefined;

    const contacto =
      this.empleado.contacto !== this.empleadoOriginal.contacto
        ? this.empleado.contacto
        : undefined;

    const nombre =
      this.empleado.nombre !== this.empleadoOriginal.nombre
        ? this.empleado.nombre
        : undefined;

    const apellido =
      this.empleado.apellido !== this.empleadoOriginal.apellido
        ? this.empleado.apellido
        : undefined;

    const dni =
      this.empleado.dni !== this.empleadoOriginal.dni
        ? this.empleado.dni
        : undefined;

    const fechaNacimiento =
      this.empleado.fechaNacimiento !== this.empleadoOriginal.fechaNacimiento
        ? this.empleado.fechaNacimiento
        : undefined;

    const fechaIngreso =
      this.empleado.fechaIngreso !== this.empleadoOriginal.fechaIngreso
        ? this.empleado.fechaIngreso
        : undefined;

    const cambiosBackend = [
      cargo,
      email,
      contacto,
      nombre,
      apellido,
      dni,
      fechaNacimiento,
      fechaIngreso,
    ].some((value) => value !== undefined);

    this.guardarSueldoLocalStorage();
    this.guardarBonificacionLocalStorage();

    if (!cambiosBackend) {
      this.editarTodo = false;
      this.empleadoOriginal = structuredClone(this.empleado);
      alert('No hay cambios en los campos guardables en el backend.');
      return;

    }

    this.empleadoService.modificarEmpleado(
      this.empleado.id,
      cargo,
      email,
      contacto,
      nombre,
      apellido,
      dni,
      fechaNacimiento,
      fechaIngreso
    ).subscribe({
      next: () => {
        this.editarTodo = false;
        this.cargarEmpleado();
        alert('Empleado actualizado correctamente.');
      },
      error: (err) => {
        console.error(err);
        alert('Error al actualizar el empleado.');
      }

    });

  }

  cancelarEdicion(): void {
    this.editarTodo = false;
    this.empleado = structuredClone(this.empleadoOriginal);
    this.bonificacion = this.bonificacionOriginal;
  }

  private validarMayorDeEdad(fecha: string | null | undefined): boolean {
    if (!fecha) {
      alert('Debe ingresar la fecha de nacimiento.');
      return false;
    }

    const fechaNacimiento = this.parsearFecha(fecha);

    if (!fechaNacimiento) {
      alert('La fecha de nacimiento tiene un formato inválido.');
      return false;
    }

    const hoy = new Date();
    let edad = hoy.getFullYear() - fechaNacimiento.getFullYear();
    const mesDiferencia = hoy.getMonth() - fechaNacimiento.getMonth();
    const diaDiferencia = hoy.getDate() - fechaNacimiento.getDate();

    if (mesDiferencia < 0 || (mesDiferencia === 0 && diaDiferencia < 0)) {
      edad--;
    }

    if (edad < 18) {
      alert('La fecha de nacimiento debe ser de una persona mayor de 18 años.');
      return false;
    }

    return true;
  }

  private parsearFecha(fecha: string): Date | null {
    const fechaLimpia = fecha.trim();

    const formatoIso = /^\d{4}-\d{2}-\d{2}$/;
    const formatoLocal = /^\d{2}-\d{2}-\d{4}$/;

    if (formatoIso.test(fechaLimpia)) {
      const [anio, mes, dia] = fechaLimpia.split('-').map(Number);
      return new Date(anio, mes - 1, dia);
    }

    if (formatoLocal.test(fechaLimpia)) {
      const [dia, mes, anio] = fechaLimpia.split('-').map(Number);
      return new Date(anio, mes - 1, dia);
    }

    return null;
  }

  private guardarSueldoLocalStorage(): void {
    if (this.empleado?.id != null && this.empleado?.sueldo != null) {
      localStorage.setItem(
        'sueldoEmpleado_' + this.empleado.id,
        this.empleado.sueldo.toString()
      );
    }
  }

  private guardarBonificacionLocalStorage(): void {
    if (this.id != null) {
      localStorage.setItem(
        'bonificacionEmpleado_' + this.id,
        this.bonificacion.toString()
      );
      this.bonificacionOriginal = this.bonificacion;
    }
  }
}
