import { Component, OnInit } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { ClienteServicio } from '../../../Servicio/cliente-servicio';
import { HeaderAdmin } from "../header-admin/header-admin";

@Component({
  selector: 'app-gestion-cliente',
  imports: [RouterLink, HeaderAdmin],
  templateUrl: './gestion-cliente.html',
  styleUrl: './gestion-cliente.css',
})
export class GestionCliente implements OnInit{
  
  constructor(private clienteServicio: ClienteServicio,
    private router: Router
  ){}

  ngOnInit(): void {
    this.obtenerCliente();
  }

  clientes: any [] =[];

  obtenerCliente() {
    this.clienteServicio.obtenerTodosLosClientes().subscribe(
      (repuesta: any)=> {this.clientes = repuesta;});
  }

  verCliente(id: number){
    this.router.navigate(['/verCliente', id]);
  }


}
