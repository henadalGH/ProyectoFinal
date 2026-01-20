import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { Vehiculoservice } from './vehiculoservice';

@Component({
  selector: 'app-registar-vehiculo',
  imports: [RouterLink, FormsModule],
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
    private router: Router
  ){}

  agregarVehiculo() {
    this.vehiculoService.agregarVehiculo(this.marca, this.modelo, this.patente,this.kilometreje, this.anio).subscribe(
        (responce) => {
            console.log("Los datos fueron ingresados");
            this.router.navigate(['/gestionCliente'])
        }
      )
  }

}
