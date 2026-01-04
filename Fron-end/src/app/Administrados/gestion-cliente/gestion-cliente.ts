import { Component, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { ClienteService } from './cliente-service';
import { HeaderAdmin } from "../header-admin/header-admin";

@Component({
  selector: 'app-gestion-cliente',
  imports: [RouterLink, HeaderAdmin], 
  templateUrl: './gestion-cliente.html',
  styleUrl: './gestion-cliente.css',
})
export class GestionCliente implements OnInit{

  constructor( private clienteServicio: ClienteService){ }


  ngOnInit(): void {
    this.obtenerCliente();
  }

  clientes2: any[] = [];

  obtenerCliente()
  {
    this.clienteServicio.obtenerClientes().subscribe((respuesta: any) => {this.clientes2 = respuesta;});
}

}
