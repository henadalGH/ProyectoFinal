import { Component, OnInit } from '@angular/core';

import { HeaderAdmin } from "../header-admin/header-admin";
import { Header } from "../../../header/header";

import { ClienteServicio } from '../../../Servicio/cliente-servicio';
import { TurnosService } from '../../../Servicio/turnos-service';
import { FacturaServicio } from '../../../Servicio/factura-servicio';
import { MovimientosService } from '../../../Servicio/movimientos-service';
import { OrdenTrabajoService } from '../../../Servicio/orden-trabajo-service';


@Component({
  selector: 'app-home-admin',
  imports: [
    HeaderAdmin,
    Header
  ],
  templateUrl: './home-admin.html',
  styleUrl: './home-admin.css',
})
export class HomeAdmin implements OnInit {


  // ==========================
  // VARIABLES
  // ==========================

  totalClientes: number = 0;

  pendiente: any[] = [];

  contaTrunos!: any;
  contarOrdenFac!: any;
  contarTurnosConf!: any;

  ultimas: any[] = [];

  ingreso: any = null;



  constructor(
    private clienteService: ClienteServicio,
    private turnoService: TurnosService,
    private facturaService: FacturaServicio,
    private movimientoService: MovimientosService,
    private ordenServicio: OrdenTrabajoService
  ) {}



  ngOnInit(): void {

    this.obtenerTotalCliente();

    this.obtenerTurnosDelDia();

    this.contarTurnosPen();

    this.contarTurnosConfirmados();

    this.contarOrdenParaFacturar();

    this.obtenerUltimasFacturas();

    this.ingresosPorDia();

  }



  // ==========================
  // CLIENTES
  // ==========================

  obtenerTotalCliente(){

    this.clienteService.obtenerCantidaCliente()
      .subscribe({
        next: (data) => {
          this.totalClientes = data;
        },
        error: (err) => {
          console.error(err);
        }
      });

  }



  // ==========================
  // TURNOS
  // ==========================

  obtenerTurnosDelDia(){

    const fecha = this.obtenerFechaActual();

    this.turnoService.obtenerPorFecha(fecha)
      .subscribe({
        next: (respuesta) => {
          this.pendiente = respuesta;
        },
        error: (err) => {
          console.error(err);
        }
      });

  }



  contarTurnosPen(){

    this.turnoService.contarTurnoPendientes()
      .subscribe(
        (respuesta: any) => {
          this.contaTrunos = respuesta;
        }
      );

  }



  contarTurnosConfirmados(){

    this.turnoService.contarTurnoConfirmados()
      .subscribe(
        (respuesta: any) => {
          this.contarTurnosConf = respuesta;
        }
      );

  }



  // ==========================
  // ORDENES
  // ==========================

  contarOrdenParaFacturar(){

    this.ordenServicio.contarOrdenesParaFacturar()
      .subscribe(
        (respuesta: any) => {
          this.contarOrdenFac = respuesta;
        }
      );

  }



  // ==========================
  // FACTURAS
  // ==========================

  obtenerUltimasFacturas(){

    this.facturaService.obtenerUltimasFactura()
      .subscribe(
        (respuesta: any) => {
          this.ultimas = respuesta;
        }
      );

  }



  formatearFactura(numero: number | string): string {

    const num = Number(numero);

    const parteSucursal = '0001';

    const parteNumero = num
      .toString()
      .padStart(8, '0');

    return `${parteSucursal}-${parteNumero}`;

  }



  // ==========================
  // INGRESOS
  // ==========================

  ingresosPorDia(){

    const fecha = this.obtenerFechaActual();


    this.movimientoService.obtenerTotalIngresos(fecha, fecha)
      .subscribe(
        (respuesta: any) => {

          this.ingreso = respuesta;

        }
      );

  }



  // ==========================
  // FECHA ARGENTINA
  // ==========================

  obtenerFechaActual(): string {

    const fecha = new Date();

    const año = fecha.getFullYear();
    const mes = String(fecha.getMonth() + 1).padStart(2, '0');
    const dia = String(fecha.getDate()).padStart(2, '0');


    return `${año}-${mes}-${dia}`;

  }


}