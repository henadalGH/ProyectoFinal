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

      // Cargar los detalles ya guardados previamente en la base de datos
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

      // CORRECCIÓN CLAVE: Limpiamos history.state para que no vuelvan a aparecer al hacer F5
      history.replaceState({ ...history.state, repuestos: null }, '');
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

  // Elimina filas tanto temporales como de la vista local
  eliminarFila(id: number): void {
    // 1. Si está en las filas temporales (no guardadas)
    const indexFilas = this.filas.findIndex(f => f.id === id);
    if (indexFilas !== -1) {
      // Usamos .filter() para romper la referencia y forzar a Angular a refrescar el DOM
      this.filas = this.filas.filter(f => f.id !== id);
      return;
    }

    // 2. Si viene de los detalles traídos de la base de datos
    this.detalleOrden = this.detalleOrden.filter(detalle => detalle.id !== id);
  }

  // Guarda ÚNICAMENTE los ítems nuevos
  guardarDetalle(): void {
    // Si no hay filas temporales nuevas, no hacemos la petición
    if (this.filas.length === 0) {
      console.log('No hay ítems nuevos para guardar.');
      return;
    }

    // Mapeamos SOLO las filas temporales nuevas
    const nuevosDetalles = this.filas.map(item => ({
      cantidad: item.cantidad,
      codigo: item.codigo,
      trabajoRealizado: item.trabajoRealizado,
      tipoItem: item.tipoItem
    }));

    this.ordenService
      .agregarDetalleOrden(this.idOrden, nuevosDetalles)
      .subscribe({
        next: (resp) => {
          console.log('Nuevos detalles guardados exitosamente:', resp);
          this.filas = []; // Limpiamos las filas dinámicas temporales

          // Recargamos el detalle completo desde la BD para sincronizar
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

  cancelar(){
    this.router.navigate(['/homeEmpleado']);
  }


  // 1. Quita solo las filas dinámicas NUEVAS (aún no guardadas en BD)
    eliminarFilaTemporal(id: number): void {
      this.filas = this.filas.filter(f => f.id !== id);
    }

    // 2. Quita elementos de la lista local de lo que YA ESTABA guardado
    eliminarDetalleGuardado(id: number): void {
      this.detalleOrden = this.detalleOrden.filter(d => d.id !== id);
    }
}