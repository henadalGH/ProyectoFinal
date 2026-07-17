import { Component, OnInit } from '@angular/core';
import { HeaderAdmin } from "../../header-admin/header-admin";
import { Header } from "../../../../header/header";
import { RepuestoService } from '../../../../Servicio/repuesto-service';
import { ActivatedRoute } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-ver-repuesto',
  imports: [HeaderAdmin, Header, FormsModule],
  templateUrl: './ver-repuesto.html',
  styleUrl: './ver-repuesto.css',
})
export class VerRepuesto implements OnInit {

  constructor(
    private repuestoServicio: RepuestoService,
    private aRouter: ActivatedRoute
  ) {}

  repuesto!: any;
  cantidad: number = 0;
  idRepuesto!: number;

  ngOnInit(): void {

    this.idRepuesto = Number(this.aRouter.snapshot.paramMap.get('id'));

    if (this.idRepuesto) {
      this.obtenerRepuesto();
    }

  }

  obtenerRepuesto() {

    this.repuestoServicio.obtenerRepuestoPorId(this.idRepuesto)
      .subscribe({
        next: (repuesto: any) => {
          this.repuesto = repuesto;
        },
        error: (error) => {
          console.error(error);
          alert("Error al cargar el repuesto");
        }
      });

  }

  // AUMENTAR STOCK
  aumentarStock() {

    if (!this.cantidad || this.cantidad <= 0) {
      alert("La cantidad debe ser mayor a 0");
      return;
    }

    this.repuestoServicio.aumentarStrok(this.idRepuesto, this.cantidad)
      .subscribe({

        next: () => {

          alert("Stock aumentado correctamente");

          this.obtenerRepuesto();

          this.cantidad = 0;
        },

        error: (error) => {

          console.error(error);
          alert("Error al aumentar el stock");

        }

      });

  }

  activarRepuesto() {

    this.repuestoServicio.activaDesactivarRepuesto(this.idRepuesto, true)
      .subscribe({

        next: () => {
          alert("El repuesto fue activado con éxito");
          this.obtenerRepuesto();
        },

        error: (error) => {

          console.error(error);
          alert("Error al activar el repuesto");

        }

      });

  }

  desactivarRepuesto() {

    this.repuestoServicio.activaDesactivarRepuesto(this.idRepuesto, false)
      .subscribe({

        next: () => {
          alert("El repuesto fue desactivado con éxito");
          this.obtenerRepuesto();
        },

        error: (error) => {

          console.error(error);
          alert("Error al desactivar el repuesto");

        }

      });

  }

  // DISMINUIR STOCK
  disminuirStock() {

    if (!this.cantidad || this.cantidad <= 0) {
      alert("La cantidad debe ser mayor a 0");
      return;
    }

    if (this.cantidad > this.repuesto.stock) {
      alert("No hay suficiente stock disponible");
      return;
    }

    this.repuestoServicio.disminuirStock(this.idRepuesto, this.cantidad)
      .subscribe({

        next: () => {

          alert("Stock disminuido correctamente");

          this.obtenerRepuesto();

          this.cantidad = 0;
        },

        error: (error) => {

          console.error(error);
          alert("Error al disminuir el stock");

        }

      });

  }

}