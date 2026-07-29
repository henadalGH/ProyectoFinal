import { Component, OnInit } from '@angular/core';
import { RegistroClienteServicio } from '../../Servicio/registro-cliente-servicio';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { Location } from '@angular/common';

@Component({
  selector: 'app-registro',
  imports: [FormsModule],
  templateUrl: './registro.html',
  styleUrl: './registro.css',
})
export class Registro implements OnInit {

  nombre: string = '';
  apellido: string = '';
  email: string = '';
  password: string = '';
  confirmarPassword: string = '';
  contacto: string = '';
  direccion: string = '';
  rol: string = 'CLIENTE';

  mostrarPassword: boolean = false;
  mostrarConfirmPassword: boolean = false;

  // Localidades
  localidad: any[] = [];
  localidadesFiltradas: any[] = [];

  // Provincias
  provincias: string[] = [];
  provinciaSeleccionada: string = '';

  // Localidad seleccionada
  idLocalidad: any = null;

  constructor(
    private registroCliente: RegistroClienteServicio,
    private router: Router,
    private location: Location
  ) {}

  ngOnInit(): void {
    this.obtenerLocalidad();
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
      !this.contrasenasNoCoinciden &&
      this.contacto.trim().length > 0 &&
      this.direccion.trim().length > 0 &&
      this.provinciaSeleccionada.trim().length > 0 &&
      this.idLocalidad != null
    );
  }

  // Valida si las contraseñas no coinciden
  get contrasenasNoCoinciden(): boolean {
    return (
      this.password.trim().length > 0 &&
      this.confirmarPassword.trim().length > 0 &&
      this.password !== this.confirmarPassword
    );
  }

  crearUsuario(): void {

    if (!this.formularioValido) {
      alert('Complete todos los campos correctamente antes de registrarse.');
      return;
    }

    if (this.contrasenasNoCoinciden) {
      alert('Las contraseñas no coinciden.');
      return;
    }

    this.registroCliente
      .crearCliente(
        this.nombre,
        this.apellido,
        this.email,
        this.password,
        this.contacto,
        this.direccion,
        this.rol,
        this.idLocalidad
      )
      .subscribe({
        next: () => {
          this.router.navigate(['/inicio']);
        },
        error: (error) => {
          console.error(error);
          alert('Ocurrió un error al registrar el usuario.');
        }
      });
  }

  volverAtras(): void {
    this.location.back();
  }

  obtenerLocalidad(): void {
    this.registroCliente.obtenerLocalidad().subscribe({
      next: (respuesta: any) => {
        this.localidad = respuesta;

        this.provincias = [
          ...new Set(
            this.localidad.map((l: any) => l.nombreProvincia)
          ),
        ] as string[];
      },
      error: (error) => {
        console.error(error);
      }
    });
  }

  filtrarLocalidades(): void {

    this.localidadesFiltradas = this.localidad.filter(
      (l: any) => l.nombreProvincia === this.provinciaSeleccionada
    );

    this.idLocalidad = null;
  }

  private validarEmail(email: string): boolean {
    const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return regex.test(email.trim());
  }

  private validarPassword(password: string): boolean {
    const regex = /^(?=.*[a-z])(?=.*\d)[a-z\d]{7,}$/;
    return regex.test(password.trim());
  }

}