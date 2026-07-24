import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HeaderEmpleado } from '../header-empleado/header-empleado';
import { OrdenTrabajoService } from '../../Servicio/orden-trabajo-service';
import { FormsModule } from '@angular/forms';
import { Header } from "../../header/header";
import { Location } from '@angular/common';

@Component({
  selector: 'app-detalle-orden',
  imports: [HeaderEmpleado, FormsModule, Header],
  templateUrl: './detalle-orden.html',
  styleUrl: './detalle-orden.css',
})
export class DetalleOrden implements OnInit {

  constructor(
    private aRoute: ActivatedRoute,
    private ordenService: OrdenTrabajoService,
    private router: Router,
    private location: Location
  ) {}

  idOrden: number = 0;
  orden: any = null;
  filas: any[] = [];
  contadorId: number = 1;
  detalleOrden: any[] = [];
  botonDeshabilitado = false;
  soloLectura: boolean = false;

  ngOnInit(): void {
    // 1. Obtener ID de la orden desde los parámetros de la URL
    this.idOrden = Number(this.aRoute.snapshot.paramMap.get('id'));

    if (this.idOrden) {
      // Cargar datos principales de la orden
      this.ordenService.obtenerOrdenPorId(this.idOrden).subscribe({
        next: (respuesta: any) => {
          this.orden = respuesta;
          this.botonDeshabilitado = this.orden.estado === 'PARA_FACTURAR';
          this.soloLectura = this.orden.estado === 'CANCELADA';
        },
        error: (err) => console.error('Error al obtener la orden:', err)
      });

      // Cargar los detalles ya guardados previamente en base de datos
      this.ordenService.obtenerDetalleOrden(this.idOrden).subscribe({
        next: (respuesta: any) => {
          this.detalleOrden = respuesta;
        },
        error: (err) => console.error('Error al obtener el detalle:', err)
      });
    }

    // 2. Recibir e integrar repuestos seleccionados desde la pantalla "Agregar Repuestos"
    const state = history.state ?? {};
    if (state.repuestos && Array.isArray(state.repuestos)) {
      state.repuestos.forEach((repuesto: any) => {
        this.filas.push({
          id: this.contadorId++,
          cantidad: repuesto.cantidad || 1,
          codigo: repuesto.codigo || '',
          trabajoRealizado: `${repuesto.nombre} ${repuesto.marca || ''}`.trim(),
          tipoItem: 'REPUESTO',
          esRepuestoCat: true // Bandera para bloquear la edición de tipoItem en el template
        });
      });
    }
  }

  // Agrega fila manual (por defecto para Mano de Obra o Servicio)
  agregarFila(): void {
    this.filas.push({
      id: this.contadorId++,
      cantidad: 1,
      codigo: '',
      trabajoRealizado: '',
      tipoItem: 'MANO_OBRA',
      esRepuestoCat: false
    });
  }

  // Navega hacia la pantalla de selección de repuestos
  irASeleccionarRepuestos(): void {
    this.router.navigate(['/agregarRepuestos'], {
      state: {
        idOrden: this.idOrden
      }
    });
  }

  // Elimina filas tanto temporales como traídas de la base de datos
  eliminarFila(id: number): void {
    const indexFilas = this.filas.findIndex(f => f.id === id);
    if (indexFilas !== -1) {
      this.filas.splice(indexFilas, 1);
      return;
    }

    this.detalleOrden = this.detalleOrden.filter(detalle => detalle.id !== id);
  }

  // Guarda la unificación de ítems previos y nuevos
  guardarDetalle(): void {
    const todosLosDetalles = [...this.detalleOrden, ...this.filas].map(item => ({
      cantidad: item.cantidad,
      codigo: item.codigo,
      trabajoRealizado: item.trabajoRealizado,
      tipoItem: item.tipoItem
    }));

    this.ordenService
      .agregarDetalleOrden(this.idOrden, todosLosDetalles)
      .subscribe({
        next: (resp) => {
          console.log('Detalles guardados exitosamente:', resp);
          this.filas = []; // Limpiamos las filas dinámicas temporales
          
          // Recargamos el detalle desde la BD para mantener todo sincronizado
          this.ordenService.obtenerDetalleOrden(this.idOrden).subscribe(
            respuesta => this.detalleOrden = respuesta
          );
        },
        error: (err) => {
          console.error('Error al guardar el detalle:', err);
        }
      });
  }

  estadoTerminado: string = 'PARA_FACTURAR';

  terminarOrden(): void {
    this.ordenService.actualizarEstadoOrden(this.idOrden, this.estadoTerminado).subscribe({
      next: () => {
        this.router.navigate(['/homeEmpleado']);
      },
      error: (err) => console.error('Error al finalizar la orden:', err)
    });
  }

  volverAtras(): void {
    this.location.back();
  }
}