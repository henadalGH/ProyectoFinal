import { Component, OnInit } from '@angular/core';
import { HeaderAdmin } from "../header-admin/header-admin";
import { ClienteServicio } from '../../../Servicio/cliente-servicio';
import { Header } from "../../../header/header";

@Component({
  selector: 'app-home-admin',
  imports: [HeaderAdmin, Header],
  templateUrl: './home-admin.html',
  styleUrl: './home-admin.css',
})
export class HomeAdmin implements OnInit{
  
  totalClientes: number = 0;
  
  constructor(private clienteService: ClienteServicio){

  }
  
  ngOnInit(): void {
    this.obtenerTotalCliente();
  }

  obtenerTotalCliente(){

    this.clienteService.obtenerCantidaCliente().subscribe(
      {
      next: (data) => {
        this.totalClientes = data;
      },
      error: (err) => {
        console.error(err);
      }
    });
  }
    
    
  }


