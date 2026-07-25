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

  // --- Datos del Cliente / Vehículo ---
  nombreCliente: string = '';
  telefonoCliente: string = '';
  emailCliente: string = '';
  marcaVehiculo: string = '';
  modeloVehiculo: string = '';
  patenteVehiculo: string = '';

  // --- Opciones de cálculo ---
  aplicaIva: boolean = true;
  descuento: number = 0;
  tipoDescuento: 'monto' | 'porcentaje' = 'porcentaje';

  // --- Totales ---
  subtotal: number = 0;
  montoDescuento: number = 0;
  subtotalConDescuento: number = 0;
  iva: number = 0;
  total: number = 0;

  ngOnInit(): void {
    const idOrden = Number(this.aRouter.snapshot.paramMap.get('id'));

    if (idOrden) {
      this.ordenService.obtenerOrdenPorId(idOrden).subscribe(
        (respuesta: any) => {
          this.datos = respuesta;

          // Carga de datos del cliente y vehículo si vienen dentro de respuesta
          if (respuesta) {
            this.nombreCliente = respuesta.nombreCliente || respuesta.cliente?.nombre || '';
            this.telefonoCliente = respuesta.telefonoCliente || respuesta.cliente?.telefono || '';
            this.emailCliente = respuesta.emailCliente || respuesta.cliente?.email || '';
            this.marcaVehiculo = respuesta.marcaVehiculo || respuesta.vehiculo?.marca || '';
            this.modeloVehiculo = respuesta.modeloVehiculo || respuesta.vehiculo?.modelo || '';
            this.patenteVehiculo = respuesta.patenteVehiculo || respuesta.vehiculo?.patente || '';
          }
        }
      );

      this.ordenService.obtenerDetalleOrden(idOrden).subscribe(
        (respuesta: any) => {
          this.detalleOrden = respuesta.map((detalle: any) => ({
            cantidad: detalle.cantidad,
            descripcion: detalle.trabajoRealizado,
            precioUnitario: detalle.precioUnitario,
            importe: 0,
            trabajoRealizado: detalle.trabajoRealizado
          }));
          console.log("detalleOrden:", this.detalleOrden);
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
    // 1. Acumulado bruto
    this.subtotal = 0;
    this.detalleOrden.forEach(detalle => {
      detalle.importe = (detalle.cantidad || 0) * (detalle.precioUnitario || 0);
      this.subtotal += detalle.importe;
    });

    // 2. Descuento
    if (this.tipoDescuento === 'porcentaje') {
      const porcentaje = Math.min(Math.max(this.descuento || 0, 0), 100);
      this.montoDescuento = (this.subtotal * porcentaje) / 100;
    } else {
      this.montoDescuento = Math.min(this.descuento || 0, this.subtotal);
    }

    this.subtotalConDescuento = Math.max(0, this.subtotal - this.montoDescuento);

    // 3. IVA
    this.iva = this.aplicaIva ? this.subtotalConDescuento * 0.21 : 0;

    // 4. Total
    this.total = this.subtotalConDescuento + this.iva;
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

    const tipoDescuentoEnum = this.tipoDescuento === 'porcentaje' ? 'PORCENTAJE' : 'MONTO';

    const factura = {
      tipoFactura: this.tipoFactura,
      idAdmin,
      idVehiculo,
      idOrden,
      aplicaIva: this.aplicaIva,
      descuento: this.descuento,
      tipoDescuento: tipoDescuentoEnum,
      detallePresupuesto: detalles
    };

    console.log("Factura enviada al back:", factura);

    this.facturaService.crearFactura(factura).subscribe({
      next: (resp) => {
        alert('Factura creada correctamente');
        console.log(resp);
        this.router.navigate(['/paraFacturar'])
      },
      error: (err) => {
        console.error('Error al crear factura');
        console.error(err);
      }
    });
  }

  
}