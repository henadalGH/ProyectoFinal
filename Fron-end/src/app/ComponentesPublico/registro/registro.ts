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
  contacto: string = '';
  direccion: string = '';
  rol: string = 'CLIENTE';
  mostrarPassword: boolean = false;

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

  crearUsuario() {
  this.registroCliente.crearCliente(
    this.nombre,
    this.apellido,
    this.email,
    this.password,
    this.contacto,
    this.direccion,
    this.rol,
    this.idLocalidad
    
  ).subscribe(() => {
    this.router.navigate(['/inicio']);
  });
}

  volverAtras() {
    this.location.back();
  }

  obtenerLocalidad() {
    this.registroCliente.obtenerLocalidad().subscribe(
      (respuesta: any) => {
        this.localidad = respuesta;

        // Provincias sin repetir
        this.provincias = [...new Set(
          this.localidad.map((l: any) => l.nombreProvincia)
        )];
      }
    );
  }

  filtrarLocalidades() {
    this.localidadesFiltradas = this.localidad.filter(
      (l: any) => l.nombreProvincia === this.provinciaSeleccionada
    );

    // Limpia la selección anterior
    this.idLocalidad = null;
  }

}