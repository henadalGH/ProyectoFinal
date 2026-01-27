import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { EmpleadoService } from '../../../Servicio/empleado-service';
import { Route, Router, RouterLink } from '@angular/router';
import { HeaderAdmin } from "../../Clientes/header-admin/header-admin";

@Component({
  selector: 'app-registrar-empleado',
  imports: [FormsModule, RouterLink, HeaderAdmin],
  standalone: true,
  templateUrl: './registrar-empleado.html',
  styleUrl: './registrar-empleado.css',
})
export class RegistrarEmpleado {

  nombre: string = '';
  apellido: string= '';
  email: string = '';
  password: string= '';
  contacto: string = '';
  rol: string = 'EMPLEADO';
  dni: string = '';
  fechaNacimiento: string = '';
  cargo: string= '';

  constructor(private empleadoService: EmpleadoService,
    private router: Router
  ){}

  registrarEmpleado() {
    this.empleadoService.crearEmpleado(this.nombre, this.apellido, this.email, this.password, this.contacto,this.dni, 
      this.fechaNacimiento, this.rol, this.cargo).subscribe(
        (Response) =>{
          this.router.navigate(['/gestionEmpleado']);
        }
      )
}

}
