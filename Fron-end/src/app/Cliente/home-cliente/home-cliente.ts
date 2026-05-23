import { Component, OnInit } from '@angular/core';
import { Headercliente } from "../headercliente/headercliente";
import { AuthService } from '../../AuthServicio/auth-service';
import { MisVehiculos } from "../mis-vehiculos/mis-vehiculos";
import { VehiculoService } from '../../Servicio/vehiculo-service';
import { Router, RouterLink } from '@angular/router';
import { TurnosService } from '../../Servicio/turnos-service';

@Component({
  selector: 'app-home-cliente',
  imports: [RouterLink],
  templateUrl: './home-cliente.html',
  styleUrl: './home-cliente.css',
})
export class HomeCliente implements OnInit{

  constructor(private authService: AuthService,
    private vehiculoService: VehiculoService,
    private router: Router,
    private turnoService: TurnosService
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


      if(clienteId){
        this.turnoService.obtenerTurnosPendientePorCliente(clienteId).subscribe(
          {
            next: (datos) =>{
              this.turnosPen = datos;
            }
          }
        )
      }
  }
  turnosPen:  any[]  = [];
  vehiculo: any[]= [];


  verVehiculo( id: number){
    this.router.navigate(['/detalleVehiculo', id])

  }


  logout(): void {
    this.authService.logout();
  }

}
