import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { RegistroClienteServicio } from '../../../Servicio/registro-cliente-servicio';
import { HeaderAdmin } from '../../Adminstrador/header-admin/header-admin';
import { Header } from '../../../header/header';

@Component({
  selector: 'app-registrar-clintes',
  imports: [RouterLink, FormsModule, HeaderAdmin, Header],
  standalone: true,
  templateUrl: './registrar-clintes.html',
  styleUrl: './registrar-clintes.css',
})
export class RegistrarClintes implements OnInit {
  // Localidades
  localidad: any[] = [];
  localidadesFiltradas: any[] = [];

  // Provincias
  provincias: string[] = [];
  provinciaSeleccionada: string = '';

  // Localidad seleccionada
  idLocalidad!: any;

  nombre: string = '';
  apellido: string = '';
  email: string = '';
  password: string = '';
  confirmarPassword: string = '';
  contacto: string = '';
  direccion: string = '';
  rol: string = 'CLIENTE';

  showPassword = false;
  showConfirmPassword = false;

  constructor(
    private registroCliente: RegistroClienteServicio,
    private router: Router
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
      alert('Complete todos los campos correctamente antes de registrar.');
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
        this.router.navigate(['/gestionCliente']);
      });
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
