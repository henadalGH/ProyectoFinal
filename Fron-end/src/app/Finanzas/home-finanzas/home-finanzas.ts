import { Component } from '@angular/core';
import { HeaderFinanzas } from "../header-finanzas/header-finanzas";
import { HeaderAdmin } from "../../Administrados/header-admin/header-admin";

@Component({
  selector: 'app-home-finanzas',
  imports: [HeaderFinanzas, HeaderAdmin],
  templateUrl: './home-finanzas.html',
  styleUrl: './home-finanzas.css',
})
export class HomeFinanzas {

}
