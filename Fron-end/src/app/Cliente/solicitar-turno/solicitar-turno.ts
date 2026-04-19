import { Component, OnInit } from '@angular/core';
import { Headercliente } from "../headercliente/headercliente";
import { ServiciosService } from '../../Servicio/servicios-service';

@Component({
  selector: 'app-solicitar-turno',
  imports: [ Headercliente],
  templateUrl: './solicitar-turno.html',
  styleUrl: './solicitar-turno.css',
})
export class SolicitarTurno implements OnInit{


  nombreServi: number = 0;
  descripcion: string = '';
  vehiculo: number = 0;

  constructor(private servicios: ServiciosService){}
  ngOnInit(): void {
    this.obtenerServicios();
  }

  servicio: any[] = [];


  obtenerServicios(){

    this.servicios.obtenerServicios().subscribe({
      next: (repuesta: any) => {
        this.servicio = repuesta;
      }});
  }

  solicutarturno(){}




}



