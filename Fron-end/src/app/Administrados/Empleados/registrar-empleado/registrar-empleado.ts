import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { EmpleadoService } from '../../../Servicio/empleado-service';
import { Router, RouterLink } from '@angular/router';
import { HeaderAdmin } from '../../Adminstrador/header-admin/header-admin';
import { Header } from '../../../header/header';

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
  confirmarPassword: string = '';
  contacto: string = '';
  rol: string = 'EMPLEADO';
  dni: string = '';
  fechaNacimiento: string = '';
  cargo: string = '';

  fechaMaxima!: string;

  mostrarPassword = false;
  mostrarConfirmPassword = false;

  constructor(
    private empleadoService: EmpleadoService,
    private router: Router
  ) {}

  ngOnInit(): void {
    // Establece el límite máximo en el calendario para impedir seleccionar menores de 18
    const hoy = new Date();
    hoy.setFullYear(hoy.getFullYear() - 18);
    this.fechaMaxima = hoy.toISOString().split('T')[0];
  }

  get formularioValido(): boolean {
    return (
      this.nombre.trim().length > 0 &&
      this.apellido.trim().length > 0 &&
      this.email.trim().length > 0 &&
      this.validarEmail(this.email) &&
      this.password.trim().length > 0 &&
      this.validarPassword(this.password) &&
      this.confirmarPassword.trim().length > 0 &&
      this.password === this.confirmarPassword &&
      this.contacto.trim().length > 0 &&
      this.dni.trim().length > 0 &&
      this.fechaNacimiento.trim().length > 0 &&
      this.cargo.trim().length > 0
    );
  }

  registrarEmpleado(): void {
    if (!this.formularioValido) {
      alert('Complete todos los campos correctamente antes de registrar.');
      return;
    }

    if (!this.validarMayorDeEdad(this.fechaNacimiento)) {
      return;
    }

    const fechaFormateada = this.formatearFecha(this.fechaNacimiento);
    const dniSinPuntos = this.dni.replace(/\./g, '');

    this.empleadoService
      .crearEmpleado(
        this.nombre,
        this.apellido,
        this.email,
        this.password,
        this.contacto,
        dniSinPuntos,
        fechaFormateada,
        this.rol,
        this.cargo
      )
      .subscribe({
        next: () => {
          this.router.navigate(['/gestionEmpleado']);
        },
        error: (err) => {
          console.error('Error al registrar empleado:', err);
          alert('Ocurrió un error al registrar el empleado.');
        },
      });
  }

  formatearDniVista(event: Event): void {
    const input = event.target as HTMLInputElement;
    const valor = input.value.replace(/[^\d]/g, '').slice(0, 8);

    // Formatea agregando puntos según la longitud real
    if (valor.length > 5) {
      this.dni = `${valor.slice(0, -6)}.${valor.slice(-6, -3)}.${valor.slice(-3)}`;
    } else if (valor.length > 2) {
      this.dni = `${valor.slice(0, -3)}.${valor.slice(-3)}`;
    } else {
      this.dni = valor;
    }
  }

  private validarEmail(email: string): boolean {
    const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return regex.test(email.trim());
  }

  private validarPassword(password: string): boolean {
    const regex = /^(?=.*[a-z])(?=.*\d)[a-z\d]{7,}$/;
    return regex.test(password.trim());
  }

  private validarMayorDeEdad(fecha: string): boolean {
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

  formatearFecha(fecha: string): string {
    if (!fecha) return '';
    const partes = fecha.split('-');
    if (partes.length !== 3) return fecha;

    const [anio, mes, dia] = partes;
    return `${dia}-${mes}-${anio}`;
  }
}