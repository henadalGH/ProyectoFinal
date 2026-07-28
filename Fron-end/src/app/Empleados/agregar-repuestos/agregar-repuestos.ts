import { Component, OnInit } from '@angular/core';
import { RepuestoService } from '../../Servicio/repuesto-service';
import { Location } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HeaderEmpleado } from "../header-empleado/header-empleado";
import { Header } from "../../header/header";
import { Router } from '@angular/router';
import { RepuestoOrden } from '../detalle-orden/detalle-orden.interfaces';

@Component({
  selector: 'app-agregar-repuestos',
  imports: [FormsModule, HeaderEmpleado, Header],
  templateUrl: './agregar-repuestos.html',
  styleUrl: './agregar-repuestos.css',
})
export class AgregarRepuestos implements OnInit {

  constructor(
    private repuestoServicio: RepuestoService,
    private location: Location,
    private router: Router
  ) {}

  repuestos: any[] = [];
  repuestosFiltrados: any[] = [];
  seleccionados: any[] = [];
  buscar: string = '';
  idOrden = 0;
  idFila = 0;

  ngOnInit(): void {
    const state = history.state ?? {};

    this.idOrden = Number(state.idOrden ?? 0);
    this.idFila = Number(state.idFila ?? 0);

    this.obtenerRepuestos();
  }

  obtenerRepuestos(): void {
    this.repuestoServicio.obtenerRepuestosOrden().subscribe({
      next: (respuesta: any[]) => {
        this.repuestos = respuesta.map(repuesto => ({
          ...repuesto,
          seleccionado: false,
          cantidadSeleccionada: 1
        }));

        this.aplicarFiltroYOrden();
      },
      error: (error) => {
        console.error('Error al obtener los repuestos:', error);
      }
    });
  }

  buscarRepuesto(): void {
    this.aplicarFiltroYOrden();
  }

  seleccionarRepuesto(event: any, repuesto: any): void {
    const estaSeleccionado = event.target.checked;
    repuesto.seleccionado = estaSeleccionado;

    if (estaSeleccionado) {
      if (!this.seleccionados.some(r => r.id === repuesto.id)) {
        this.seleccionados.push(repuesto);
      }
    } else {
      this.seleccionados = this.seleccionados.filter(
        r => r.id !== repuesto.id
      );
    }

    // Al marcar o desmarcar, volvemos a aplicar el ordenamiento para subir los seleccionados
    this.aplicarFiltroYOrden();
  }

  /**
   * Filtra por búsqueda y ordena la lista priorizando los marcados en la parte superior.
   */
  private aplicarFiltroYOrden(): void {
    const texto = this.buscar.toLowerCase().trim();

    // 1. Filtrar según el texto ingresado
    let resultado = this.repuestos.filter((repuesto: any) => {
      if (!texto) return true;
      return (
        repuesto.nombreRepuesto?.toLowerCase().includes(texto) ||
        repuesto.codigo?.toLowerCase().includes(texto) ||
        repuesto.marcaRepuesto?.toLowerCase().includes(texto)
      );
    });

    // 2. Ordenar: los seleccionados (seleccionado === true) van primero
    this.repuestosFiltrados = resultado.sort((a, b) => {
      if (a.seleccionado === b.seleccionado) return 0;
      return a.seleccionado ? -1 : 1;
    });
  }

  agregarRepuestos(): void {
    const repuestosOrden: RepuestoOrden[] = this.seleccionados.map(repuesto => ({
      id: repuesto.id,
      nombre: repuesto.nombreRepuesto,
      marca: repuesto.marcaRepuesto,
      codigo: repuesto.codigo,
      cantidad: Number(repuesto.cantidadSeleccionada),
      subtotal: Number(repuesto.cantidadSeleccionada) * Number(repuesto.precio ?? 0)
    }));

    this.router.navigate(
      ['/detalleOrden', this.idOrden],
      {
        state: {
          idOrden: this.idOrden,
          idFila: this.idFila,
          repuestos: repuestosOrden
        }
      }
    );
  }

  volver(): void {
    this.location.back();
  }
}