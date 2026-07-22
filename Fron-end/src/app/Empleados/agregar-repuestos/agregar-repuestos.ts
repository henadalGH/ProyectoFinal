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

  obtenerRepuestos() {

    this.repuestoServicio.obtenerRepuestosOrden().subscribe({

      next: (respuesta: any[]) => {

        this.repuestos = respuesta.map(repuesto => ({
          ...repuesto,
          seleccionado: false,
          cantidadSeleccionada: 1
        }));

        this.repuestosFiltrados = [...this.repuestos];

      },

      error: (error) => {
        console.error(error);
      }

    });

  }

  buscarRepuesto(): void {

    const texto = this.buscar.toLowerCase().trim();

    if (!texto) {
      this.repuestosFiltrados = [...this.repuestos];
      return;
    }

    this.repuestosFiltrados = this.repuestos.filter((repuesto: any) =>
      repuesto.nombreRepuesto.toLowerCase().includes(texto) ||
      repuesto.codigo.toLowerCase().includes(texto) ||
      repuesto.marcaRepuesto.toLowerCase().includes(texto)
    );

  }

  seleccionarRepuesto(event: any, repuesto: any): void {

    if (event.target.checked) {

      repuesto.seleccionado = true;

      if (!this.seleccionados.some(r => r.id === repuesto.id)) {
        this.seleccionados.push(repuesto);
      }

    } else {

      repuesto.seleccionado = false;

      this.seleccionados = this.seleccionados.filter(
        r => r.id !== repuesto.id
      );

    }

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