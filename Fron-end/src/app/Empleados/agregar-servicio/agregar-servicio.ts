import { Component, OnInit } from '@angular/core';
import { ServiciosService } from '../../Servicio/servicios-service';
import { Location } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Header } from "../../header/header";
import { Router } from '@angular/router';

export interface Servicio {
  id: number;
  nombre?: string;
  nombreServicio?: string;
  descripcion?: string;
  precio?: number;
  seleccionado?: boolean;
}

@Component({
  selector: 'app-agregar-servicio',
  imports: [FormsModule, Header],
  templateUrl: './agregar-servicio.html',
  styleUrl: './agregar-servicio.css',
})
export class AgregarServicio implements OnInit {

  servicios: Servicio[] = [];
  serviciosFiltrados: Servicio[] = [];
  seleccionados: Servicio[] = [];
  buscar: string = '';
  idOrden = 0;

  constructor(
    private serviciosServicio: ServiciosService,
    private location: Location,
    private router: Router
  ) {}

  ngOnInit(): void {
    const state = history.state ?? {};
    this.idOrden = Number(state.idOrden ?? 0);

    this.obtenerServicios();
  }

  obtenerServicios(): void {
    this.serviciosServicio.obtenerServicios().subscribe({
      next: (respuesta: any) => {
        const datos = (Array.isArray(respuesta) ? respuesta : []) as Servicio[];
        
        this.servicios = datos.map((servicio: Servicio) => ({
          ...servicio,
          seleccionado: false
        }));

        this.aplicarFiltroYOrden();
      },
      error: (error: any) => {
        console.error('Error al obtener los servicios:', error);
      }
    });
  }

  buscarServicio(): void {
    this.aplicarFiltroYOrden();
  }

  seleccionarServicio(event: Event, servicio: Servicio): void {
    const target = event.target as HTMLInputElement;
    const estaSeleccionado = target.checked;
    servicio.seleccionado = estaSeleccionado;

    if (estaSeleccionado) {
      if (!this.seleccionados.some(s => s.id === servicio.id)) {
        this.seleccionados.push(servicio);
      }
    } else {
      this.seleccionados = this.seleccionados.filter(
        s => s.id !== servicio.id
      );
    }

    this.aplicarFiltroYOrden();
  }

  /**
   * Filtra por búsqueda y ordena la lista priorizando los marcados en la parte superior.
   */
  private aplicarFiltroYOrden(): void {
    const texto = this.buscar.toLowerCase().trim();

    // 1. Filtrar según el texto ingresado
    const resultado = this.servicios.filter((servicio: Servicio) => {
      if (!texto) return true;
      return (
        servicio.nombre?.toLowerCase().includes(texto) ||
        servicio.nombreServicio?.toLowerCase().includes(texto) ||
        servicio.descripcion?.toLowerCase().includes(texto)
      );
    });

    // 2. Ordenar sin mutar el arreglo original (evita loops en la vista)
    this.serviciosFiltrados = [...resultado].sort((a, b) => {
      if (a.seleccionado === b.seleccionado) return 0;
      return a.seleccionado ? -1 : 1;
    });
  }

  agregarServicios(): void {
    const serviciosOrden = this.seleccionados.map(servicio => ({
      id: servicio.id,
      nombre: servicio.nombre || servicio.nombreServicio || '',
      descripcion: servicio.descripcion || '',
      precio: servicio.precio || 0
    }));

    this.router.navigate(
      ['/detalleOrden', this.idOrden],
      {
        state: {
          idOrden: this.idOrden,
          servicios: serviciosOrden
        }
      }
    );
  }

  volver(): void {
    this.location.back();
  }
}