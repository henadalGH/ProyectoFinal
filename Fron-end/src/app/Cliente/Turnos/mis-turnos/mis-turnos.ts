import { Component, OnInit } from '@angular/core';
import { Headercliente } from "../../headercliente/headercliente";
import { TurnosService } from '../../../Administrados/ServiciosAdmin/turnos-service';


@Component({
  selector: 'app-mis-turnos',
  imports: [Headercliente],
  templateUrl: './mis-turnos.html',
  styleUrl: './mis-turnos.css',
})
export class MisTurnos implements OnInit{
  
  constructor(private turnoService: TurnosService){}

  ngOnInit(): void {
    
  }

  pendientes: any[]= [];

  obtenerTurnosPrendientePorVehiculo(idVehiculo: number){

    this.turnoService.obtenerPorIdVehiculo(idVehiculo).subscribe(
        (repuesta) =>{
          this.pendientes = repuesta;
        }
    )

  }

}
