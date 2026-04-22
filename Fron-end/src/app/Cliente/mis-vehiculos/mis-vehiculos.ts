import { Component, OnInit } from '@angular/core';
import { Headercliente } from "../headercliente/headercliente";
import { Router, RouterLink } from "@angular/router";
import { VehiculoService } from '../../Servicio/vehiculo-service';
import { AuthService } from '../../AuthServicio/auth-service';
import { Location } from '@angular/common';
import { ServiciosService } from '../../Servicio/servicios-service';

@Component({
  selector: 'app-mis-vehiculos',
  imports: [Headercliente, RouterLink],
  templateUrl: './mis-vehiculos.html',
  styleUrl: './mis-vehiculos.css',
})
export class MisVehiculos implements OnInit{

  constructor(private vehiculoService: VehiculoService,
    private authService: AuthService,
    private router: Router,
    private locacion: Location, 
    private servicio: ServiciosService
  ){}

  vehiculo: any[]= [];
  servicios: any[]= [];
  mostrarModal: boolean = false;
  vehiculoIdSeleccionado: number | null = null;

  ngOnInit(): void {
    
    const id = this.authService.getEntityId();
    console.log(id);

    const clienteId = this.authService.getEntityId();

  if (clienteId) {
    this.vehiculoService.obtenerVehiculoCliente(clienteId).subscribe({
      next: (data) => {
        this.vehiculo = data;
      }
    });
  }

  }

  verMiHistorial(id: number){
    this.router.navigate(['/miHistorial', id]);
  }

  volverAtras(){
    this.locacion.back();
  }

  //Seccion model

  abrirModel(id: number){
    this.vehiculoIdSeleccionado = id;
    this.mostrarModal = true;

    this.servicio.obtenerServicios().subscribe({

      next: (datos : any)=>{
        this.servicios = datos;
      }
    })
  }

  cerrarModel(){
    this.mostrarModal=false;
  }


  

  

  

 
}
