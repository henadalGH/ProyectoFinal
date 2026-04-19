import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { Vehiculoservice } from './vehiculoservice';
import { Location } from '@angular/common';
import { AuthService } from '../../../AuthServicio/auth-service';

@Component({
  selector: 'app-registar-vehiculo',
  imports: [ FormsModule],
  standalone: true,
  templateUrl: './registar-vehiculo.html',
  styleUrl: './registar-vehiculo.css',
})
export class RegistarVehiculo {

  marca: String = "";
  modelo: String = "";
  patente: String = "";
  anio: String = "";
  kilometreje: number = 0;


  constructor(private vehiculoService: Vehiculoservice,
    private router: Router,
    private locacion: Location,
    private authService: AuthService
  ){}

  volverAtras(){
    this.locacion.back();
  }

  agregarVehiculo() {
    this.vehiculoService.agregarVehiculo(this.marca, this.modelo, this.patente,this.kilometreje, this.anio).subscribe(
        (responce) => {
            console.log("Los datos fueron ingresados");
            const rol = this.authService.getRol(); // ejemplo

            if (rol === 'ADMIN') {
              this.router.navigate(['/admin']);
            } else if (rol === 'CLIENTE') {
              this.router.navigate(['/home']);
            } else {
              this.locacion.back();
  }
        }
      )
  }

}
