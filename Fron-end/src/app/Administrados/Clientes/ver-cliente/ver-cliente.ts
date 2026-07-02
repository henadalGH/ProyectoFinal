import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ClienteServicio } from '../../../Servicio/cliente-servicio';
import { HeaderAdmin } from '../../Adminstrador/header-admin/header-admin';
import { Header } from "../../../header/header";

@Component({
  selector: 'app-ver-cliente',
  imports: [HeaderAdmin, Header],
  templateUrl: './ver-cliente.html',
  styleUrl: './ver-cliente.css',
})
export class VerCliente implements OnInit{
  
  constructor(
    private router: ActivatedRoute,
    private clienteServicio: ClienteServicio
  ){}
  
  id!: number;
  cliente: any;

  ngOnInit(): void {
    this.id = Number(this.router.snapshot.paramMap.get('id'));

    this.clienteServicio.obtenerClientePorId(this.id).
    subscribe(
      data => {
        this.cliente = data;
      });
  }
}
