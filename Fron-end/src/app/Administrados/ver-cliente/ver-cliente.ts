import { Component, OnInit } from '@angular/core';
import { ClienteServicio } from '../../Servicio/cliente-servicio';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { HeaderAdmin } from "../header-admin/header-admin";

@Component({
  selector: 'app-ver-cliente',
  imports: [HeaderAdmin, RouterLink],
  templateUrl: './ver-cliente.html',
  styleUrl: './ver-cliente.css',
})
export class VerCliente implements OnInit {

  constructor(
    private clienteServicio: ClienteServicio,
    private router: ActivatedRoute
  ){}
  
  cliente: any;
  
  ngOnInit(): void {
    const id = this.router.snapshot.paramMap.get('id');

    this.clienteServicio.obtenerClientePorId(id!).subscribe(
      {
        next: (data) => this.cliente = data
      }
    )
  }

  

}
