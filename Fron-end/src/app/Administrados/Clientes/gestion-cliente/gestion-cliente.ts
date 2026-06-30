import { Component, OnInit } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { ClienteServicio } from '../../../Servicio/cliente-servicio';
import { HeaderAdmin } from '../../Adminstrador/header-admin/header-admin';
import { Header } from "../../../header/header";

@Component({
  selector: 'app-gestion-cliente',
  imports: [
    RouterLink,
    FormsModule,
    HeaderAdmin,
    Header
  ],
  templateUrl: './gestion-cliente.html',
  styleUrl: './gestion-cliente.css',
})
export class GestionCliente implements OnInit {

  constructor(
    private clienteServicio: ClienteServicio,
    private router: Router
  ) {}

  clientes: any[] = [];
  busqueda: string = '';

  ngOnInit(): void {
    this.obtenerCliente();
  }

  obtenerCliente() {
    this.clienteServicio.obtenerTodosLosClientes().subscribe(
      (respuesta: any) => {
        this.clientes = respuesta;
      }
    );
  }

  get clientesFiltrados() {
    if (!this.busqueda.trim()) {
      return this.clientes;
    }

    const texto = this.busqueda.toLowerCase();

    return this.clientes.filter(cli =>
      cli.usuario.nombre?.toLowerCase().includes(texto) ||
      cli.usuario.apellido?.toLowerCase().includes(texto) ||
      cli.direccion?.toLowerCase().includes(texto)
    );
  }

  verCliente(id: number) {
    this.router.navigate(['/verCliente', id]);
  }
}