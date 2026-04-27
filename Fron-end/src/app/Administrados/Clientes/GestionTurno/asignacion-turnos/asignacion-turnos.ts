import { Component, OnInit } from '@angular/core';
import { HeaderAdmin } from "../../header-admin/header-admin";
import { TurnosService } from '../../../../Servicio/turnos-service';

@Component({
  selector: 'app-asignacion-turnos',
  imports: [HeaderAdmin],
  templateUrl: './asignacion-turnos.html',
  styleUrl: './asignacion-turnos.css',
})
export class AsignacionTurnos implements OnInit{

  constructor(private turnosService: TurnosService){
  }
  ngOnInit(): void {
    this.obtenerTurnosAsignacion();
  }

  asignacion: any[] = [];

  obtenerTurnosAsignacion()
  {
      return this.turnosService.obtenerTurnosAsignacion().subscribe(
        {
          next: (repuesta: any)=>{
            this.asignacion = repuesta;
          }
        }
      )
  }

}
