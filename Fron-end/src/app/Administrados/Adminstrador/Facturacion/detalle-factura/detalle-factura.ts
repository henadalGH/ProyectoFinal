import { Component, OnInit } from '@angular/core';
import { HeaderAdmin } from "../../header-admin/header-admin";
import { ActivatedRoute, Router } from '@angular/router';
import { OrdenTrabajoService } from '../../../../Servicio/orden-trabajo-service';
import { Location, CommonModule } from '@angular/common';
import { FacturaServicio } from '../../../../Servicio/factura-servicio';
import { AuthService } from '../../../../AuthServicio/auth-service';
import { FormsModule } from '@angular/forms';
import { Header } from "../../../../header/header";

@Component({
  selector: 'app-detalle-factura',
  standalone: true,
  imports: [CommonModule, HeaderAdmin, FormsModule, Header],
  templateUrl: './detalle-factura.html',
  styleUrls: ['./detalle-factura.css'],
})
export class DetalleFactura implements OnInit {

  constructor(
    private aRouter: ActivatedRoute,
    private ordenService: OrdenTrabajoService,
    private router: Router,
    private location: Location,
    private facturaService: FacturaServicio,
    private authService: AuthService
  ) {}

  datos: any = null;
  detalleOrden: any[] = [];
  contador: number = 0;

  tipoFactura: string = 'A';

  subtotal: number = 0;
  iva: number = 0;
  total: number = 0;

  ngOnInit(): void {

    const idOrden = Number(this.aRouter.snapshot.paramMap.get('id'));

    if (idOrden) {

      this.ordenService.obtenerOrdenPorId(idOrden).subscribe(
        (respuesta: any) => {
          this.datos = respuesta;
        }
      );

      this.ordenService.obtenerDetalleOrden(idOrden).subscribe(
        (respuesta: any) => {

          this.detalleOrden = respuesta.map((detalle: any) => ({
            cantidad: detalle.cantidad,
            descripcion: detalle.trabajoRealizado,
            precioUnitario: 0,
            importe: 0,
            trabajoRealizado: detalle.trabajoRealizado
          }));

          this.calcularTotales();

        }
      );

    }

  }

  agregarFila(): void {

    this.detalleOrden.push({
      idDetalle: this.contador++,
      cantidad: 0,
      descripcion: '',
      precioUnitario: 0,
      importe: 0,
      trabajoRealizado: null
    });

  }

  quitarFila(): void {

    if (this.detalleOrden.length > 0) {
      this.detalleOrden.pop();
      this.calcularTotales();
    }

  }

  calcularTotales(): void {

    this.subtotal = 0;

    this.detalleOrden.forEach(detalle => {

      detalle.importe =
        (detalle.cantidad || 0) *
        (detalle.precioUnitario || 0);

      this.subtotal += detalle.importe;

    });

    this.iva = this.subtotal * 0.21;

    this.total = this.subtotal + this.iva;

  }

  volverAtras(): void {
    this.location.back();
  }

  crearFactura(): void {

    const idAdmin = this.authService.getEntityId();
    const idVehiculo = this.datos?.idVehiculo;
    const idOrden = this.datos?.id;

    const detalles = this.detalleOrden.map(fila => ({
      cantidad: fila.cantidad,
      descripcion: fila.descripcion,
      precioUnitario: fila.precioUnitario
    }));

    const factura = {
      tipoFactura: this.tipoFactura,
      idAdmin,
      idVehiculo,
      idOrden,
      detallePresupuesto: detalles
    };

    console.log(factura);

    this.facturaService.crearFactura(factura).subscribe({

      next: (resp) => {

        alert('Factura creada correctamente');
        console.log(resp);
        this.router.navigate(['/facturas']);

      },

      error: (err) => {

        console.error('Error al crear factura');
        console.error(err);

      }

    });

  }

}