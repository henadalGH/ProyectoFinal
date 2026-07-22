import { Component, OnInit } from '@angular/core';
import { Headercliente } from "../headercliente/headercliente";
import { AuthService } from '../../AuthServicio/auth-service';
import { VehiculoService } from '../../Servicio/vehiculo-service';
import { Router, RouterLink } from '@angular/router';
import { TurnosService } from '../../Servicio/turnos-service';
import { Header } from "../../header/header";
import { FacturaServicio } from '../../Servicio/factura-servicio';

@Component({
  selector: 'app-home-cliente',
  imports: [RouterLink, Headercliente, Header],
  templateUrl: './home-cliente.html',
  styleUrl: './home-cliente.css',
})
export class HomeCliente implements OnInit{

  constructor(private authService: AuthService,
    private vehiculoService: VehiculoService,
    private router: Router,
    private turnoService: TurnosService,
    private facturaService: FacturaServicio
  ){}


  ngOnInit(): void {
    const clienteId = this.authService.getEntityId();
    console.log(clienteId);
    if(clienteId){
      this.facturaService.contarFacturaPendientesPorCiente(clienteId).subscribe(
        (repuesta: any) =>
        {
          this.contarFac = repuesta;
        }
      )
    }

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
              console.log(this.turnosPen.length)
            }});
      }

      if(clienteId){
        const hoy = new Date().toISOString().split('T')[0];
        this.turnoService.obtenerTurnosFuturosPorCliente(hoy, clienteId).subscribe({
          next: (datos) =>{
              this.turFuturos = datos;
            }});
          }
  }
    turnosPen:  any[]=[];
    vehiculo: any[]=[];
    turFuturos: any[]=[];
    contarFac: any;

    verVehiculo( id: number){
      this.router.navigate(['/detalleVehiculo', id]);
    }

    logout(): void {
      this.authService.logout();
    }
}
