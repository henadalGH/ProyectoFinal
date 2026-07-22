import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HeaderEmpleado } from '../header-empleado/header-empleado';
import { Header } from '../../header/header';
import { FormsModule } from '@angular/forms';
import { Location } from '@angular/common';

import { OrdenTrabajoService } from '../../Servicio/orden-trabajo-service';
import { RepuestoService } from '../../Servicio/repuesto-service';

import {
  LineaOrdenDetalle,
  RepuestoOrden,
  RepuestoModalItem,
  TipoItem
} from './detalle-orden.interfaces';


@Component({
  selector: 'app-detalle-orden',
  imports: [
    HeaderEmpleado,
    Header,
    FormsModule
  ],
  templateUrl: './detalle-orden.html',
  styleUrl: './detalle-orden.css'
})
export class DetalleOrden implements OnInit {


  constructor(
    private aRoute: ActivatedRoute,
    private ordenService: OrdenTrabajoService,
    private router: Router,
    private location: Location,
    private repuestoServicio: RepuestoService
  ) {}


  idOrden = 0;

  orden:any = null;

  filas:LineaOrdenDetalle[] = [];

  detalleOrden:LineaOrdenDetalle[] = [];

  contadorId = 1;

  botonDeshabilitado = false;

  soloLectura = false;


  repuestosOrden:RepuestoOrden[] = [];



  // Modal (lo dejo por si después lo usás)

  isRepuestoModalOpen = false;

  filaActiva:LineaOrdenDetalle | null = null;

  repuestosModal:RepuestoModalItem[] = [];

  repuestosModalFiltrados:RepuestoModalItem[] = [];

  buscarRepuestoModal = '';



  ngOnInit():void {


    this.idOrden =
      Number(this.aRoute.snapshot.paramMap.get('id'));


    this.recibirRepuestos();


    if(this.idOrden){

      this.cargarOrden();

      this.cargarDetalle();

    }


    this.agregarFila();

  }



  cargarOrden(){

    this.ordenService.obtenerOrdenPorId(this.idOrden)
    .subscribe({

      next:(resp:any)=>{

        this.orden = resp;

        this.botonDeshabilitado =
          resp.estado === 'PARA_FACTURAR';

        this.soloLectura =
          resp.estado === 'CANCELADA';

      },

      error:err=>console.error(err)

    });

  }



  cargarDetalle(){

    this.ordenService.obtenerDetalleOrden(this.idOrden)
    .subscribe({

      next:(resp:any)=>{


        this.detalleOrden =
          resp.map((d:any)=>({

            id:d.id ?? this.contadorId++,

            cantidad:d.cantidad ?? 0,

            codigo:d.codigo ?? '',

            trabajoRealizado:
              d.trabajoRealizado ?? '',

            tipoItem:
              d.tipoItem ?? '',

            selectedRepuestos:
              d.selectedRepuestos ?? []

          }));


        this.actualizarResumenRepuestos();


      },

      error:err=>console.error(err)

    });


  }



  agregarFila(){

    this.filas.push({

      id:this.contadorId++,

      cantidad:0,

      codigo:'',

      trabajoRealizado:'',

      tipoItem:'',

      selectedRepuestos:[]

    });

  }



  eliminarFila(id:number){

    this.filas =
      this.filas.filter(
        fila=>fila.id !== id
      );

    this.detalleOrden =
      this.detalleOrden.filter(
        detalle=>detalle.id !== id
      );

    this.actualizarResumenRepuestos();

  }

  abrirAgregarRepuestos(fila:LineaOrdenDetalle): void {

    if(this.soloLectura){
      return;
    }

    this.router.navigate(
      ['/agregarRepuestos'],
      {
        state:{
          idOrden:this.idOrden,
          idFila:fila.id
        }
      }
    );

  }




  onTipoItemChange(
    fila:LineaOrdenDetalle,
    valor:string
  ){


    fila.tipoItem =
      valor as TipoItem;



    if(valor !== 'REPUESTO'){

      fila.selectedRepuestos=[];

    }

  }





  recibirRepuestos(){


    const state = history.state;


    if(state?.repuestos){


      setTimeout(()=>{


        this.agregarRepuestosAFila(
          state.idFila,
          state.repuestos
        );


      },300);

    }


  }




  agregarRepuestosAFila(
    idFila:number,
    repuestos:RepuestoOrden[]
  ){


    const fila =
      this.filas.find(
        f=>f.id === idFila
      ) ??
      this.detalleOrden.find(
        d=>d.id === idFila
      );


    if(!fila){
      return;
    }


    fila.tipoItem = 'REPUESTO';

    fila.selectedRepuestos =
      [...repuestos];


    fila.codigo =
      repuestos
      .map(r=>r.codigo)
      .filter(Boolean)
      .join(', ');


    fila.cantidad =
      repuestos.reduce(
        (total,r)=>
          total + Number(r.cantidad || 0),
        0
      );


    fila.trabajoRealizado =
      repuestos
      .map(r=>r.nombre)
      .filter(Boolean)
      .join(', ');



    this.actualizarResumenRepuestos();


  }





  actualizarResumenRepuestos(){


    this.repuestosOrden =
      [
        ...this.filas,
        ...this.detalleOrden
      ]
      .flatMap(
        fila=>fila.selectedRepuestos ?? []
      );


  }





  guardarDetalle(){


    const detalles =
      this.filas.map(fila=>({


        cantidad:fila.cantidad,

        codigo:fila.codigo,

        trabajoRealizado:
          fila.trabajoRealizado,

        tipoItem:
          fila.tipoItem,

        repuestos:
          fila.selectedRepuestos


      }));



    console.log(detalles);



    this.ordenService
      .agregarDetalleOrden(
        this.idOrden,
        detalles
      )
      .subscribe({

        next:resp=>{

          console.log(
            'Detalle guardado',
            resp
          );

        },

        error:err=>{

          console.error(err);

        }

      });


  }





  terminarOrden(){


    this.ordenService
    .actualizarEstadoOrden(
      this.idOrden,
      'PARA_FACTURAR'
    )
    .subscribe({

      next:()=>{

        this.router.navigate(
          ['/homeEmpleado']
        );

      },

      error:err=>console.error(err)

    });


  }




  volverAtras(){

    this.location.back();

  }

}