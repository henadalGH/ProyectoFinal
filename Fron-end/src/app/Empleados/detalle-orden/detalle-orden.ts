import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HeaderEmpleado } from "../header-empleado/header-empleado";
import { OrdenTrabajoService } from '../../Servicio/orden-trabajo-service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-detalle-orden',
  imports: [HeaderEmpleado, FormsModule],
  templateUrl: './detalle-orden.html',
  styleUrl: './detalle-orden.css',
})
export class DetalleOrden implements OnInit{

  constructor(private aRoute: ActivatedRoute,
    private ordenService: OrdenTrabajoService
  ){}

  idOrden: number = 0;
  orden!: any;
  filas: any[] = [];
  private contadorId = 1;

  //variables del detalle de la orden
  tipoItem: string = '';
  cantidad: number = 0;
  codigo: string = '';
  trabajo: string = '';


  ngOnInit(): void {
    
    this.idOrden = Number (this.aRoute.snapshot.paramMap.get('id'))

    if (this.idOrden) {
       this.ordenService.obtenerOrdenPorId(this.idOrden).subscribe(
        (repuesta: any) => {
          this.orden = repuesta;
        })};

        this.agregarFila();
  }

  agregarFila(): void {

    this.filas.push({
      id: this.contadorId++,
      cantidad: 0,
      codigo: '',
      trabajo: ''
    });

  }
  eliminarFila(id: number): void {

    this.filas = this.filas.filter(
      fila => fila.id !== id
    );

  }


}
