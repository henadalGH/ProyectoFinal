import { Component, OnInit } from '@angular/core';
import { ServiciosService } from '../Servicio/servicios-service';
import { RouterLink } from '@angular/router';
import { Location } from '@angular/common';

@Component({
  selector: 'app-servicios-ofresidos',
  imports: [RouterLink],
  templateUrl: './servicios-ofresidos.html',
  styleUrl: './servicios-ofresidos.css',
})
export class ServiciosOfresidos implements OnInit{

  constructor(private servicio: ServiciosService,
    private location: Location
  ){}

  servicios: any[]= [];

  ngOnInit(): void {
    
    this.servicio.obtenerServicios().subscribe(
      (repuesta: any) => {
        this.servicios = repuesta;
      }
    );
  }


  volver(){
    this.location.back();
  }

}
