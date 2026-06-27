import { Component } from '@angular/core';
import { HeaderAdmin } from "../../Adminstrador/header-admin/header-admin";
import { Header } from "../../../header/header";
import { Router } from '@angular/router';

@Component({
  selector: 'app-home-finanzas',
  imports: [HeaderAdmin, Header],
  templateUrl: './home-finanzas.html',
  styleUrl: './home-finanzas.css',
})
export class HomeFinanzas {

  constructor(private router: Router){}

  irRegistroFinansas(){
    this.router.navigate(['/registraMovimiento'])
  }

}
