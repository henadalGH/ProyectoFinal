import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { EmpleadoService } from '../../../Servicio/empleado-service';
import { Router, RouterLink } from '@angular/router';
import { HeaderAdmin } from "../../Clientes/header-admin/header-admin";

@Component({
  selector: 'app-registrar-empleado',
  standalone: true,
  imports: [FormsModule, RouterLink, HeaderAdmin],
  templateUrl: './registrar-empleado.html',
  styleUrl: './registrar-empleado.css',
})
export class RegistrarEmpleado {

  nombre: string = '';
  apellido: string = '';
  email: string = '';
  password: string = '';
  contacto: string = '';
  rol: string = 'EMPLEADO';
  dni: string = '';
  fechaNacimiento: string = ''; // viene del input type="date"
  cargo: string = '';

  constructor(
    private empleadoService: EmpleadoService,
    private router: Router
  ) {}

  registrarEmpleado() {

    const fechaFormateada = this.formatearFecha(this.fechaNacimiento);

    this.empleadoService.crearEmpleado(
      this.nombre,
      this.apellido,
      this.email,
      this.password,
      this.contacto,
      this.dni,
      fechaFormateada, // 👈 fecha en formato dd-MM-yyyy
      this.rol,
      this.cargo
    ).subscribe({
      next: () => {
        this.router.navigate(['/gestionEmpleado']);
      },
      error: (err) => {
        console.error('Error al registrar empleado:', err);
      }
    });
  }

  // 🔧 Convierte yyyy-MM-dd → dd-MM-yyyy
  formatearFecha(fecha: string): string {
    if (!fecha) return '';

    const partes = fecha.split('-'); // ["2000", "10", "10"]

    if (partes.length !== 3) return fecha; // fallback por si algo raro pasa

    const [anio, mes, dia] = partes;

    return `${dia}-${mes}-${anio}`;
  }
}