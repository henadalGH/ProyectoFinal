import { Component, OnInit } from '@angular/core';
import { Headercliente } from "../../headercliente/headercliente";
import { VehiculoService } from '../../../Servicio/vehiculo-service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-detalle-vehiculo',
  imports: [Headercliente],
  templateUrl: './detalle-vehiculo.html',
  styleUrl: './detalle-vehiculo.css',
})
export class DetalleVehiculo implements OnInit{

  constructor(private vehiculoServicio: VehiculoService,
    private route: ActivatedRoute
  ){
  }

  vehiculo: any[] = [];
  id!: number   
  
  ngOnInit(): void {
    const id = Number (this.route.snapshot.paramMap.get('id'));

    this.vehiculoServicio.obtenerVehiculoCliente(id).subscribe(
      datos => {
        this.vehiculo = datos;
      }
    )
  }
    
  


}
