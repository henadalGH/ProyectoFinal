import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { ClienteService } from '../gestion-cliente/cliente-service';
import { FormsModule } from '@angular/forms';


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
  rol: string = '';

  constructor(private cliente: ClienteService, private router: Router){}


  registrarCliente(): void{
    console.log(this.nombre, this.apellido, this.contacto, this.email, this.password, this.direccion, this.rol)
  
    this.cliente.agregarCliente(this.nombre, this.apellido, this.email,this.password, this.direccion, this.contacto, this.rol)
    .subscribe({
      next: (res)=> {
        console.log('Clientente agregado exitosamente');
        this.router.navigate(['/gestionCliente'])
      }
    })
  
  }


}
