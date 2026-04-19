import { Component } from '@angular/core';
import { Headercliente } from "../headercliente/headercliente";
import { RouterLink } from "@angular/router";

@Component({
  selector: 'app-mis-vehiculos',
  imports: [Headercliente, RouterLink],
  templateUrl: './mis-vehiculos.html',
  styleUrl: './mis-vehiculos.css',
})
export class MisVehiculos {

}
