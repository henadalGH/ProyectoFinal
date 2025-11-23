import { Component } from '@angular/core';
import { HeaderFinanzas } from "../header-finanzas/header-finanzas";
import { RouterLink } from "@angular/router";

@Component({
  selector: 'app-presupuesto',
  imports: [HeaderFinanzas, RouterLink],
  templateUrl: './presupuesto.html',
  styleUrl: './presupuesto.css',
})
export class Presupuesto {

}
