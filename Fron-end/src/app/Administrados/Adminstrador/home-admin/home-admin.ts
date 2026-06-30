import { Component, OnInit } from '@angular/core';
import { HeaderAdmin } from "../header-admin/header-admin";
import { ClienteServicio } from '../../../Servicio/cliente-servicio';
import { Header } from "../../../header/header";
import { TurnosService } from '../../../Servicio/turnos-service';
import { FacturaServicio } from '../../../Servicio/factura-servicio';
import { MovimientosService } from '../../../Servicio/movimientos-service';

@Component({
  selector: 'app-home-admin',
  imports: [HeaderAdmin, Header],
  templateUrl: './home-admin.html',
  styleUrl: './home-admin.css',
})
export class HomeAdmin implements OnInit{
  
  totalClientes: number = 0;
  pendiente: any[]= [];
  
  constructor(private clienteService: ClienteServicio,
    private turnoService: TurnosService,
    private facturaService: FacturaServicio,
    private movimientoService: MovimientosService
  ){

  }
  
  ngOnInit(): void {
    this.obtenerTotalCliente();
    this.obtenerUltimasFacturas();
    this.ingresosPorDia();
  }

  obtenerTotalCliente(){

    this.clienteService.obtenerCantidaCliente().subscribe(
      {
      next: (data) => {
        this.totalClientes = data;
      },
      error: (err) => {
        console.error(err);
      }
    });

    const fecha = new Date().toISOString().split('T')[0];
console.log(fecha);

this.turnoService.obtenerPorFecha(fecha).subscribe(
  (respuesta: any) => {
    this.pendiente = respuesta;
  }
);
  }

  ultimas: any[] =[];
  obtenerUltimasFacturas(){
    this.facturaService.obtenerUltimasFactura().subscribe(
      (repuesta: any) => {
        this.ultimas = repuesta;
      }
    )
  }

  formatearFactura(numero: number | string): string {

    const num = Number(numero);

    const parteSucursal = '0001';

    const parteNumero = num.toString().padStart(8, '0');

    return `${parteSucursal}-${parteNumero}`;
  }

  //ingresos por dia


  ingreso: any = null;

  ingresosPorDia(){

    const fecha = new Date().toISOString().split('T')[0];

    this.movimientoService.obtenerTotalIngresos(fecha , fecha).subscribe(
      (repuesta: any) => {
        this.ingreso = repuesta;
        console.log(this.ingreso)
      }
    )
    
  }

    
    
  }


