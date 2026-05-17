import { Location } from '@angular/common';
import { Component } from '@angular/core';
import { Headercliente } from "../../headercliente/headercliente";

@Component({
  selector: 'app-mi-historial',
  imports: [Headercliente],
  templateUrl: './mi-historial.html',
  styleUrl: './mi-historial.css',
})
export class MiHistorial {

  constructor(private locacion: Location){ }

  volver(){
    this.locacion.back();
  }

}
