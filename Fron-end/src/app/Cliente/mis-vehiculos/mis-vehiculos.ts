import { Component, OnInit } from '@angular/core';
import { Headercliente } from "../headercliente/headercliente";
import { ActivatedRoute, RouterLink } from "@angular/router";
import { VehiculoService } from '../../Servicio/vehiculo-service';
import { AuthService } from '../../AuthServicio/auth-service';

@Component({
  selector: 'app-mis-vehiculos',
  imports: [Headercliente, RouterLink],
  templateUrl: './mis-vehiculos.html',
  styleUrl: './mis-vehiculos.css',
})
export class MisVehiculos implements OnInit{

  constructor(private vehiculoService: VehiculoService,
    private authService: AuthService
  ){}

  vehiculo: any[]= []

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

 
}
