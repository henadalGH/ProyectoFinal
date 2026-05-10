import { Component } from '@angular/core';
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
export class Registro {

  nombre: string = '';
  apellido: string = '';
  email: string = '';
  password: string = '';
  contacto: string = '';
  direccion: string = '';
  rol: string = 'CLIENTE';
  mostrarPassword: boolean = false;

  constructor(
    private registroCliente: RegistroClienteServicio,
    private router: Router,
    private locacion: Location
  ) {}

  crearUsuario() {
    this.registroCliente.crearCliente(
      this.nombre, this.apellido, this.email,
      this.password, this.contacto, this.direccion, this.rol
    ).subscribe(() => {
      this.router.navigate(['/inicio']);
    });
  }

  volverAtras() {
    this.locacion.back();
  }
}