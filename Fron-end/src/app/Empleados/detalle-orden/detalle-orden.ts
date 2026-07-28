import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HeaderEmpleado } from '../header-empleado/header-empleado';
import { OrdenTrabajoService } from '../../Servicio/orden-trabajo-service';
import { FormsModule } from '@angular/forms';
import { Header } from "../../header/header";
import { Location } from '@angular/common';

@Component({
  selector: 'app-detalle-orden',
  imports: [FormsModule, Header],
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
  ordenCargada = false;
  detalleCargado = false;
  pendingState: any = null;

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
          this.ordenCargada = true;
          this.intentarIntegrarState();
        },
        error: (err) => console.error('Error al obtener la orden:', err)
      });

      // Cargar los detalles ya guardados previamente en la base de datos
      this.ordenService.obtenerDetalleOrden(this.idOrden).subscribe({
        next: (respuesta: any) => {
          this.detalleOrden = (Array.isArray(respuesta) ? respuesta : []).map((item: any) => ({
            ...item,
            esPersistido: true
          }));
          this.detalleCargado = true;
          this.intentarIntegrarState();
        },
        error: (err) => console.error('Error al obtener el detalle:', err)
      });
    }

    this.pendingState = history.state ?? {};
    this.intentarIntegrarState();
  }

  // Agrega fila manual (por defecto para Mano de Obra)
  agregarFila(): void {
    this.filas.push({
      id: this.contadorId++,
      cantidad: 1,
      codigo: '',
      trabajoRealizado: '',
      tipoItem: 'MANO_OBRA',
      esRepuestoCat: false,
      esServicioCat: false,
      esPersistido: false
    });
  }

  private intentarIntegrarState(): void {
    if (!this.ordenCargada || !this.detalleCargado || !this.pendingState) {
      return;
    }

    if (!this.soloLectura && this.orden?.nombreServicio) {
      this.agregarServicioOrdenInicial(this.orden.nombreServicio);
    }

    if (Array.isArray(this.pendingState.repuestos)) {
      this.pendingState.repuestos.forEach((repuesto: any) => {
        const trabajo = `${repuesto.nombre} ${repuesto.marca || ''}`.trim();
        if (!trabajo || this.itemYaExiste(trabajo)) {
          return;
        }

        this.filas.push({
          id: this.contadorId++,
          cantidad: repuesto.cantidad || 1,
          codigo: repuesto.codigo || '',
          trabajoRealizado: trabajo,
          tipoItem: 'REPUESTO',
          esRepuestoCat: true,
          esPersistido: false
        });
      });
    }

    if (Array.isArray(this.pendingState.servicios)) {
      this.pendingState.servicios.forEach((servicio: any) => {
        const trabajo = (servicio.nombre || servicio.nombreServicio || '').trim();
        if (!trabajo || this.itemYaExiste(trabajo)) {
          return;
        }

        this.filas.push({
          id: this.contadorId++,
          cantidad: 1,
          codigo: '',
          trabajoRealizado: trabajo,
          tipoItem: 'SERVICIO',
          esServicioCat: true,
          esPersistido: false
        });
      });
    }

    history.replaceState({ ...history.state, repuestos: null, servicios: null }, '');
    this.pendingState = null;
  }

  private itemYaExiste(trabajoRealizado: string): boolean {
    const primera = trabajoRealizado.trim().toLowerCase();
    if (!primera) {
      return false;
    }
    return this.detalleOrden.some((item: any) =>
      String(item.trabajoRealizado || '').trim().toLowerCase() === primera
    ) || this.filas.some((item: any) =>
      String(item.trabajoRealizado || '').trim().toLowerCase() === primera
    );
  }

  private agregarServicioOrdenInicial(nombreServicio: string): void {
    const trabajo = String(nombreServicio || '').trim();
    if (!trabajo || this.itemYaExiste(trabajo)) {
      return;
    }

    this.detalleOrden.unshift({
      id: this.contadorId++,
      cantidad: 1,
      codigo: '',
      trabajoRealizado: trabajo,
      tipoItem: 'SERVICIO',
      esServicioCat: true,
      esPersistido: true
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

  // Navega hacia la pantalla de selección de servicios
  irASeleccionarServicios(): void {
    this.router.navigate(['/agregarServicios'], {
      state: {
        idOrden: this.idOrden,
        fromDetalle: true
      }
    });
  }

  // Elimina filas temporales nuevas; no borra ítems persistidos del backend
  eliminarFila(id: number): void {
    // 1. Si está en las filas temporales (no guardadas)
    const indexFilas = this.filas.findIndex(f => f.id === id);
    if (indexFilas !== -1) {
      // Usamos .filter() para romper la referencia y forzar a Angular a refrescar el DOM
      this.filas = this.filas.filter(f => f.id !== id);
      return;
    }

    // 2. Si viene de los detalles traídos de la base de datos, no eliminamos nada
    const item = this.detalleOrden.find(detalle => detalle.id === id);
    if (item && item.esPersistido) {
      console.warn('No se puede eliminar un ítem persistido del backend.');
      return;
    }

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
            respuesta => {
              this.detalleOrden = (Array.isArray(respuesta) ? respuesta : []).map((item: any) => ({
                ...item,
                esPersistido: true
              }));
            }
          );
        },
        error: (err) => {
          console.error('Error al guardar el detalle:', err);
        }
      });
  }

  estadoTerminado: string = 'PARA_FACTURAR';

  get tieneDetallesSinGuardar(): boolean {
    return this.filas.length > 0;
  }

  get puedeTerminarOrden(): boolean {
    return !this.botonDeshabilitado && !this.tieneDetallesSinGuardar;
  }

  terminarOrden(): void {
    if (this.tieneDetallesSinGuardar) {
      console.warn('Debe guardar los detalles antes de finalizar la orden.');
      return;
    }

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