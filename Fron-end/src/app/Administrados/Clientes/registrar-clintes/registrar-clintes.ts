import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { RegistroClienteServicio } from '../../../Servicio/registro-cliente-servicio';
import { HeaderAdmin } from '../../Adminstrador/header-admin/header-admin';
import { Header } from "../../../header/header";

@Component({
  selector: 'app-registrar-clintes',
  imports: [RouterLink, FormsModule, HeaderAdmin, Header],
  standalone: true,
  templateUrl: './registrar-clintes.html',
  styleUrl: './registrar-clintes.css',
})
export class RegistrarClintes {


    // Localidades
    localidad: any[] = [];
    localidadesFiltradas: any[] = [];

    // Provincias
    provincias: string[] = [];
    provinciaSeleccionada: string = '';

    // Localidad seleccionada
    idLocalidad!: any;


  nombre: string ='';
  apellido: string = '';
  email: string = '';
  password: string = '';
  contacto: string = '';
  direccion: string= '';
  rol: string = 'CLIENTE';
  

  constructor(private registroCliente: RegistroClienteServicio,
    private router: Router
  ){

  }

crearUsuario() {
  this.registroCliente.crearCliente(this.nombre, this.apellido, this.email, 
    this.password, this.contacto, this.direccion,  this.rol, this.idLocalidad).subscribe(
    (Response)=>{
      this.router.navigate(['/gestionCliente']);
    }
  )
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
