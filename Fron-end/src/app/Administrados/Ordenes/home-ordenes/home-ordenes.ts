import { Component, OnInit } from '@angular/core';
import { HeaderAdmin } from "../../Adminstrador/header-admin/header-admin";
import { TurnosService } from '../../../Servicio/turnos-service';
import { RecuperarContrasenia } from '../../../recuperar-contrasenia/recuperar-contrasenia';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home-ordenes',
  imports: [HeaderAdmin],
  templateUrl: './home-ordenes.html',
  styleUrl: './home-ordenes.css',
})
export class HomeOrdenes implements OnInit{


  constructor(private turnoService: TurnosService,
    private router: Router
  ){}

  ngOnInit(): void {
    this.obtenerTurnosComfirmados();
  }

  turnos: any[] = [];
  
  obtenerTurnosComfirmados(){

    const estado: string = 'CONFIRMADO'

    this.turnoService.obtenerPorEstado(estado).subscribe(
      (repuesta: any) => {
        this.turnos = repuesta;
      });
  }


  crearOrden(idturno: number){
    this.router.navigate(['/crearOrden/', idturno]);
  }
}
