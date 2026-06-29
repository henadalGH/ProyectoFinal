import { Component, OnInit } from '@angular/core';
import { HeaderAdmin } from "../header-admin/header-admin";
import { ClienteServicio } from '../../../Servicio/cliente-servicio';
import { Header } from "../../../header/header";
import { TurnosService } from '../../../Servicio/turnos-service';

@Component({
  selector: 'app-home-admin',
  imports: [HeaderAdmin, Header],
  templateUrl: './home-admin.html',
  styleUrl: './home-admin.css',
})
export class HomeAdmin implements OnInit{
  
  totalClientes: number = 0;
  pendiente: any[]= [];
  
  constructor(private clienteService: ClienteServicio,
    private turnoService: TurnosService
  ){

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

    const fecha = new Date().toISOString().split('T')[0];
console.log(fecha);

this.turnoService.obtenerPorFecha(fecha).subscribe(
  (respuesta: any) => {
    this.pendiente = respuesta;
  }
);
  }
    
    
  }


