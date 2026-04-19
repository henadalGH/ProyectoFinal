import { Component, OnInit } from '@angular/core';
import { Headercliente } from "../headercliente/headercliente";
import { ActivatedRoute, RouterLink } from "@angular/router";
import { VehiculoService } from '../../Servicio/vehiculo-service';

@Component({
  selector: 'app-mis-vehiculos',
  imports: [Headercliente, RouterLink],
  templateUrl: './mis-vehiculos.html',
  styleUrl: './mis-vehiculos.css',
})
export class MisVehiculos implements OnInit{

  constructor(private vehiculoService: VehiculoService,
    private route: ActivatedRoute
  ){}

  

  ngOnInit(): void {
    
    const id = this.route.snapshot.paramMap.get('id');
    console.log(id);
  }

  

}
