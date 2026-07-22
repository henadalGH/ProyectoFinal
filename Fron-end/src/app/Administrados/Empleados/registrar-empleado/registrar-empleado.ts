import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { EmpleadoService } from '../../../Servicio/empleado-service';
import { Router, RouterLink } from '@angular/router';
import { HeaderAdmin } from '../../Adminstrador/header-admin/header-admin';
import { Header } from "../../../header/header";

@Component({
  selector: 'app-registrar-empleado',
  standalone: true,
  imports: [FormsModule, RouterLink, HeaderAdmin, Header],
  templateUrl: './registrar-empleado.html',
  styleUrl: './registrar-empleado.css',
})
export class RegistrarEmpleado implements OnInit {

  nombre: string = '';
  apellido: string = '';
  email: string = '';
  password: string = '';
  contacto: string = '';
  rol: string = 'EMPLEADO';
  dni: string = '';
  fechaNacimiento: string = '';
  cargo: string = '';

  fechaMaxima!: string;

  editarEmail = false;
  editarContacto = false;
  editarCargo = false;


  constructor(
    private empleadoService: EmpleadoService,
    private router: Router
  ) {}


  ngOnInit() {

    const hoy = new Date();

    // resta 18 años
    hoy.setFullYear(hoy.getFullYear() - 18);

    this.fechaMaxima = hoy.toISOString().split('T')[0];
  }


  registrarEmpleado() {


    if (!this.validarEdad()) {
      alert("El empleado debe ser mayor de 18 años");
      return;
    }


    const fechaFormateada = this.formatearFecha(this.fechaNacimiento);


    this.empleadoService.crearEmpleado(
      this.nombre,
      this.apellido,
      this.email,
      this.password,
      this.contacto,
      this.dni,
      fechaFormateada,
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



  validarEdad(): boolean {

    if (!this.fechaNacimiento) {
      return false;
    }


    const nacimiento = new Date(this.fechaNacimiento);
    const hoy = new Date();


    let edad = hoy.getFullYear() - nacimiento.getFullYear();


    const diferenciaMes = hoy.getMonth() - nacimiento.getMonth();


    if (
      diferenciaMes < 0 ||
      (diferenciaMes === 0 && hoy.getDate() < nacimiento.getDate())
    ) {
      edad--;
    }


    return edad >= 18;
  }



  formatearFecha(fecha: string): string {

    if (!fecha) return '';

    const partes = fecha.split('-');

    if (partes.length !== 3) return fecha;

    const [anio, mes, dia] = partes;

    return `${dia}-${mes}-${anio}`;
  }

}