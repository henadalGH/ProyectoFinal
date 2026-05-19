import { Component, OnInit } from '@angular/core';
import { Headercliente } from "../headercliente/headercliente";
import { AuthService } from '../../AuthServicio/auth-service';
import { MisVehiculos } from "../mis-vehiculos/mis-vehiculos";
import { VehiculoService } from '../../Servicio/vehiculo-service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home-cliente',
  imports: [],
  templateUrl: './home-cliente.html',
  styleUrl: './home-cliente.css',
})
export class HomeCliente implements OnInit{

  constructor(private authService: AuthService,
    private vehiculoService: VehiculoService,
    private router: Router
  ){}


  ngOnInit(): void {
    const clienteId = this.authService.getEntityId();

    if (clienteId) {
      this.vehiculoService.obtenerVehiculoCliente(clienteId).subscribe({
        next: (data) => {
          this.vehiculo = data;
        },
        error: (err) => console.error(err)
      });
    }
  }

  vehiculo: any[]= [];


  verVehiculo( id: number){
    this.router.navigate(['/detalleVehiculo', id])

  }


  logout(): void {
    this.authService.logout();
  }
}
