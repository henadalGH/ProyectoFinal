import { Component } from '@angular/core';
import { HeaderEmpleado } from "../header-empleado/header-empleado";
import { Header } from "../../header/header";

@Component({
  selector: 'app-ordenes-proximas',
  imports: [HeaderEmpleado, Header],
  templateUrl: './ordenes-proximas.html',
  styleUrl: './ordenes-proximas.css',
})
export class OrdenesProximas {

}
