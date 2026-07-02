import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { EmpleadoService } from '../../../Servicio/empleado-service';
import { HeaderAdmin } from '../../Adminstrador/header-admin/header-admin';
import { Header } from '../../../header/header';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-ver-empleado',
  templateUrl: './ver-empleado.html',
  styleUrl: './ver-empleado.css',
  imports: [HeaderAdmin, RouterLink, Header, FormsModule],
})
export class VerEmpleado implements OnInit {

  id!: number;
  empleado: any;

  editarEmail = false;
  editarContacto = false;
  editarCargo = false;

  constructor(
    private route: ActivatedRoute,
    private empleadoService: EmpleadoService
  ) {}

  ngOnInit(): void {
    this.id = Number(this.route.snapshot.paramMap.get('id'));
    this.cargarEmpleado();
  }

  cargarEmpleado(): void {
    this.empleadoService.verEmpleado(this.id)
      .subscribe({
        next: (data) => {
          this.empleado = data;
        },
        error: (error) => {
          console.error(error);
        }
      });
  }

  modificarCargo(idEmpleado: number): void {

    if (!confirm('¿Está seguro de modificar el cargo?')) return;

    this.empleadoService.modificarEmpleado(
      idEmpleado,
      this.empleado.cargo,
      undefined,
      undefined
    )
    .subscribe({
      next: (data) => {
        this.empleado = data;
        this.editarCargo = false;
      },
      error: (error) => {
        console.error(error);
        alert('Error al modificar el cargo');
      }
    });
  }

  modificarEmail(idEmpleado: number): void {

    if (!confirm('¿Está seguro de modificar el email?')) return;

    this.empleadoService.modificarEmpleado(
      idEmpleado,
      undefined,
      this.empleado.email,
      undefined
    )
    .subscribe({
      next: (data) => {
        this.empleado = data;
        this.editarEmail = false;
      },
      error: (error) => {
        console.error(error);
        alert('Error al modificar el email');
      }
    });
  }

  modificarContacto(idEmpleado: number): void {

    if (!confirm('¿Está seguro de modificar el contacto?')) return;

    this.empleadoService.modificarEmpleado(
      idEmpleado,
      undefined,
      undefined,
      this.empleado.contacto
    )
    .subscribe({
      next: (data) => {
        this.empleado = data;
        this.editarContacto = false;
      },
      error: (error) => {
        console.error(error);
        alert('Error al modificar el contacto');
      }
    });
  }

  cancelarEdicion(): void {
    this.editarCargo = false;
    this.editarEmail = false;
    this.editarContacto = false;

    this.cargarEmpleado();
  }
}