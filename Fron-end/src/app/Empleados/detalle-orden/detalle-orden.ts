import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HeaderEmpleado } from '../header-empleado/header-empleado';
import { OrdenTrabajoService } from '../../Servicio/orden-trabajo-service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-detalle-orden',
  imports: [HeaderEmpleado, FormsModule],
  templateUrl: './detalle-orden.html',
  styleUrl: './detalle-orden.css',
})
export class DetalleOrden implements OnInit {

  constructor(
    private aRoute: ActivatedRoute,
    private ordenService: OrdenTrabajoService,
    private router: Router
  ) {}

  idOrden: number = 0;
  orden: any = null;
  filas: any[] = [];
  contadorId: number = 1;
  detalleOrden: any[]=[];
  botonDeshabilitado = false;

  ngOnInit(): void {

    this.idOrden = Number(
      this.aRoute.snapshot.paramMap.get('id')
    );

    if (this.idOrden) {

      this.ordenService
        .obtenerOrdenPorId(this.idOrden)
        .subscribe({
          next: (respuesta: any) => {
            this.orden = respuesta;
            this.botonDeshabilitado = this.orden.estado === 'PARA_FACTURAR';
            console.log(this.orden.estado);
            console.log(this.botonDeshabilitado);
          },
          error: (err) => {
            console.error(err);
          }
        });

    }


    if(this.idOrden){

      this.ordenService.obtenerDetalleOrden(this.idOrden).subscribe(
      
        (repuesta:any) =>{
          this.detalleOrden = repuesta;
        }
      
    )

    }

    this.agregarFila();


  }

  agregarFila(): void {

    this.filas.push({
      id: this.contadorId++,
      cantidad: 0,
      codigo: '',
      trabajoRealizado: '',
      tipoItem: ''
    });

  }

  eliminarFila(id: number): void {

    this.filas = this.filas.filter(
      fila => fila.id !== id
    );

  }

  guardarDetalle(): void {

    const detalles = this.filas.map(fila => ({
      cantidad: fila.cantidad,
      codigo: fila.codigo,
      trabajoRealizado: fila.trabajoRealizado,
      tipoItem: fila.tipoItem
    }));

    console.log(detalles);

    this.ordenService
      .agregarDetalleOrden(this.idOrden, detalles)
      .subscribe({
        next: (resp) => {
          console.log('Detalles guardados');
          console.log(resp);
        },
        error: (err) => {
          console.error(err);
        }
      });

  }

  estadoTerminado : string = 'PARA_FACTURAR';
  terminarOrden(){

    this.ordenService.actualizarEstadoOrden(this.idOrden, this.estadoTerminado).subscribe(
      
        (next: any) => {
            this.router.navigate(['/homeEmpleado'])
        }
      
    )

  }

  
}