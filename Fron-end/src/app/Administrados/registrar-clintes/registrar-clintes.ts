import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { ClienteService } from '../gestion-cliente/cliente-service';
import { FormsModule } from '@angular/forms';
import { RegistroClienteServicio } from '../../servicio/registro-cliente-servicio';


@Component({
  selector: 'app-registrar-clintes',
  imports: [RouterLink, FormsModule],
  standalone: true,
  templateUrl: './registrar-clintes.html',
  styleUrl: './registrar-clintes.css',
})
export class RegistrarClintes {

  nombre: string='';
  apellido: string='';
  email: string ='';
  password: string='';
  contacto: string = '';
  direccion: string = '';
  rol: string = 'CLIENTE';

  constructor(private servicoRegistro: RegistroClienteServicio, private router: Router){}

  registrarCliente() {
    this.servicoRegistro.crearCliente(this.nombre, this.apellido, this.email, this.password,
      this.contacto,this.direccion, this.rol
    ).subscribe(
      (Response)=> {
        this.router.navigate(['/gestionCliente'])
      }

    )
  }
}
