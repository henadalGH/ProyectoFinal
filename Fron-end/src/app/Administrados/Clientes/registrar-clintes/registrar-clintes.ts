import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { RegistroClienteServicio } from '../../../Servicio/registro-cliente-servicio';
import { HeaderAdmin } from "../header-admin/header-admin";

@Component({
  selector: 'app-registrar-clintes',
  imports: [RouterLink, FormsModule, HeaderAdmin],
  standalone: true,
  templateUrl: './registrar-clintes.html',
  styleUrl: './registrar-clintes.css',
})
export class RegistrarClintes {


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
    this.password, this.contacto, this.direccion,  this.rol).subscribe(
    (Response)=>{
      this.router.navigate(['/gestionCliente']);
    }
  )
}

}
