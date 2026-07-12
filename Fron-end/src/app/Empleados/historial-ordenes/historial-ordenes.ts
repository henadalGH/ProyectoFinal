import { Component } from '@angular/core';
import { HeaderEmpleado } from "../header-empleado/header-empleado";
import { Header } from "../../header/header";

@Component({
  selector: 'app-historial-ordenes',
  imports: [HeaderEmpleado, Header],
  templateUrl: './historial-ordenes.html',
  styleUrl: './historial-ordenes.css',
})
export class HistorialOrdenes {

}
