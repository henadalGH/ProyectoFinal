import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../../AuthServicio/auth-service';
import { TurnosService } from '../../../Servicio/turnos-service';
import { Router } from '@angular/router';
import { Headercliente } from "../../headercliente/headercliente";
import { Header } from "../../../header/header";


@Component({
  selector: 'app-mis-turnos',
  imports: [Headercliente, Header],
  templateUrl: './mis-turnos.html',
  styleUrl: './mis-turnos.css',
})
export class MisTurnos implements OnInit{
  
  constructor(private turnoService: TurnosService,
    private authService: AuthService,
    private router: Router
  ){}

  pendiente: any[] = [];
  
  ngOnInit(): void {

    const idCliente = this.authService.getEntityId()

    if(idCliente){
      this.turnoService.obtenerTurnosPendientePorCliente(idCliente).subscribe(
        (respueste: any) => {
          this.pendiente = respueste;
          console.log(this.pendiente.length, idCliente);
        }
      )
    }
    
  }

  logout(): void {
    this.authService.logout();
  }

  pendientes: any[]= [];


  confirmarTurno(idTurno: number){
      const estado = "CONFIRMADO"

      this.turnoService.actualizarEstadoTurno(idTurno, estado).subscribe(
        (next: any) => {
          this.router.navigate(['misTurnos']);

        }
      )
  }


  cancelarTurno(idTurno: number){
    const estado = "CANCELADO"

      this.turnoService.actualizarEstadoTurno(idTurno, estado).subscribe(
        (next: any) => {
          this.router.navigate(['misTurnos']);
        }
      )
  }
}
