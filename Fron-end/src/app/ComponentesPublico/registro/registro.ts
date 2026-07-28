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
  idLocalidad!: any;

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
      this.password === this.confirmarPassword &&
      this.contacto.trim().length > 0 &&
      this.direccion.trim().length > 0 &&
      this.provinciaSeleccionada.trim().length > 0 &&
      this.idLocalidad != null
    );
  }

  crearUsuario() {
    if (!this.formularioValido) {
      alert('Complete todos los campos correctamente antes de registrarse.');
      return;
    }

    if (!this.validarPassword(this.password)) {
      alert('La contraseña debe tener al menos 7 caracteres, incluir letras y números, y solo letras minúsculas.');
      return;
    }

    if (this.password !== this.confirmarPassword) {
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
      .subscribe(() => {
        this.router.navigate(['/inicio']);
      });
  }

  volverAtras() {
    this.location.back();
  }

  obtenerLocalidad() {
    this.registroCliente.obtenerLocalidad().subscribe((respuesta: any) => {
      this.localidad = respuesta;

      this.provincias = [...new Set(this.localidad.map((l: any) => l.nombreProvincia))];
    });
  }

  filtrarLocalidades() {
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