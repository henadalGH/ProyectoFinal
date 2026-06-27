import { Component } from '@angular/core';
import { Header } from "../../../header/header";
import { HeaderAdmin } from "../../Adminstrador/header-admin/header-admin";

@Component({
  selector: 'app-registrar-finanzas',
  imports: [Header, HeaderAdmin],
  templateUrl: './registrar-finanzas.html',
  styleUrl: './registrar-finanzas.css',
})
export class RegistrarFinanzas {

}
