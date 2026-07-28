import { Component, OnInit } from '@angular/core';
import { HeaderEmpleado } from "../header-empleado/header-empleado";
import { CommonModule } from '@angular/common';
import { AuthService } from '../../AuthServicio/auth-service';
import { OrdenTrabajoService } from '../../Servicio/orden-trabajo-service';
import { Router } from '@angular/router';
import { Header } from "../../header/header";
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-home-empleado',
  standalone: true,
  imports: [CommonModule, Header, FormsModule],
  templateUrl: './home-empleado.html',
  styleUrl: './home-empleado.css',
})
export class HomeEmpleado implements OnInit{

  constructor(private authService: AuthService,
    private ordenService: OrdenTrabajoService,
    private router: Router
  ){}
  
  fecha: string = '';
  filtroFechaDesde: string = '';
  filtroFechaHasta: string = '';
  filtroEstado: string = '';
  filtroPatente: string = '';
  ordenesCache: any[]= [];
  ordenesFiltradas: any[]= [];
  motivo: string = '';
  
  ngOnInit(): void {
    this.fecha = new Date().toLocaleDateString('sv-SE', {
      timeZone: 'America/Argentina/Buenos_Aires'
    });

    this.filtroFechaDesde = this.fecha;
    this.filtroFechaHasta = this.fecha;
    this.cargarOrdenes();
  }

  private cargarOrdenes(): void {
    const idEmpleado = this.authService.getEntityId();

    if (!idEmpleado) {
      return;
    }

    // Cargar todas las órdenes del empleado una sola vez y filtrar en memoria.
    this.ordenService.obtenrHistorialOrdenes(idEmpleado).subscribe(
      (respuesta: any[]) => {
        this.ordenesCache = respuesta || [];
        this.eliminarDuplicados();
        this.ordenesFiltradas = [...this.ordenesCache];
        this.aplicarFiltros();
      },
      (error) => {
        console.error('Error al cargar órdenes del empleado:', error);
      }
    );
  }

  private eliminarDuplicados(): void {
    this.ordenesCache = this.ordenesCache.filter((orden, index, array) => {
      const id = orden.idOrden ?? orden.id;
      return array.findIndex(item => (item.idOrden ?? item.id) === id) === index;
    });
  }

  aplicarFiltros(): void {
    this.ordenesFiltradas = this.ordenesCache.filter(orden => {
      const fechaOrden = this.obtenerFechaOrden(orden);
      const estadoOrden = this.normalizarEstado(orden.estado);
      const patente = this.obtenerPatente(orden).toLowerCase();

      const desde = this.filtroFechaDesde || '';
      const hasta = this.filtroFechaHasta || '';
      const coincideFecha =
        (!desde && !hasta) ||
        (desde && hasta && fechaOrden >= desde && fechaOrden <= hasta) ||
        (desde && !hasta && fechaOrden >= desde) ||
        (!desde && hasta && fechaOrden <= hasta);

      const coincideEstado = !this.filtroEstado || estadoOrden.includes(this.filtroEstado.toUpperCase());
      const coincidePatente = !this.filtroPatente || patente.includes(this.filtroPatente.toLowerCase());

      return coincideFecha && coincideEstado && coincidePatente;
    });
  }

  limpiarFiltros(): void {
    this.filtroFechaDesde = this.fecha;
    this.filtroFechaHasta = this.fecha;
    this.filtroEstado = '';
    this.filtroPatente = '';
    this.aplicarFiltros();
  }

  private obtenerFechaOrden(orden: any): string {
    return (orden.fecha || orden.fechaProgramada || orden.fechaOrden || orden.fechaAsignacion || '').toString().slice(0, 10);
  }

  private obtenerPatente(orden: any): string {
    return (orden.patente || '').toString();
  }

  private normalizarEstado(estado: string | undefined): string {
    const valor = (estado || '').toUpperCase();
    if (valor === 'PARA_FACTURAR') {
      return 'TERMINADA';
    }
    return valor;
  }

  verOrden(idOrden: number){
    this.router.navigate(['detalleOrden', idOrden]);
  }

  cancelarOrden(idOrden: number) {

    if (confirm("¿Está seguro de cancelar la orden?")) {

      this.ordenService.cancelarOrden(idOrden, "Cliente no asistió al turno").subscribe({
        next: () => {
          alert("Orden cancelada correctamente");
          this.cargarOrdenes();
        },
        error: () => {
          alert("Ocurrió un error al cancelar la orden");
        }
      });

    }

  }

}
