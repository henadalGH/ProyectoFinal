import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ClienteServicio } from '../../../Servicio/cliente-servicio';
import { HeaderAdmin } from '../../Adminstrador/header-admin/header-admin';
import { Header } from "../../../header/header";
import { VehiculoService } from '../../../Servicio/vehiculo-service';
import { Location } from '@angular/common';

@Component({
  selector: 'app-ver-cliente',
  imports: [HeaderAdmin, Header],
  templateUrl: './ver-cliente.html',
  styleUrl: './ver-cliente.css',
})
export class VerCliente implements OnInit{
  
  constructor(
    private router: ActivatedRoute,
    private clienteServicio: ClienteServicio,
    private vehiculoServicio: VehiculoService,
    private routers: Router,
    private location: Location

  ){}
  
  id!: number;
  cliente: any;
  vehiculo: any[]= [];

  ngOnInit(): void {
    this.id = Number(this.router.snapshot.paramMap.get('id'));

    this.clienteServicio.obtenerClientePorId(this.id).
    subscribe(
      data => {
        this.cliente = data;
      });


    if(this.id){
      this.vehiculoServicio.obtenerVehiculoCliente(this.id).subscribe(
        (repuesta: any) => {
          this.vehiculo = repuesta;
        }
      )
    }
  }

  verDetalleVehiculo(id: number){
    this.routers.navigate(['detalleVehiculo', id])
  }

  volverAtras(){
    this.location.back();
  }
}
