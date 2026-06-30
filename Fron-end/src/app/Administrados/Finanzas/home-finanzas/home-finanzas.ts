import { Component, OnInit } from '@angular/core'; 
import { HeaderAdmin } from "../../Adminstrador/header-admin/header-admin";
import { Header } from "../../../header/header";
import { formatDate } from '@angular/common'; 
import { MovimientosService } from '../../../Servicio/movimientos-service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home-finanzas',
  imports: [HeaderAdmin, Header],
  templateUrl: './home-finanzas.html',
  styleUrl: './home-finanzas.css',
})
export class HomeFinanzas implements OnInit {

  // Métricas del Día
  cajaDelDia: number = 0;
  egresosDelDia: number = 0;
  balanceDelDia: number = 0;

  // Métricas del Mes
  ingresosMensuales: number = 0;
  egresosMensuales: number = 0;
  balanceMensual: number = 0;

  constructor(
    private movimientosService: MovimientosService,
    private router: Router
  ){}

  ngOnInit(): void {
    this.cargarDatosDashboard();
    this.ultmosmovimientos();
  }

  cargarDatosDashboard() {
    const hoy = new Date();

    // 1. Fechas para el control Diario (Mismo día en 'desde' y 'hasta')
    const fechaHoyStr = formatDate(hoy, 'yyyy-MM-dd', 'en-US');

    // 2. Fechas para el control Mensual Automático
    const primerDiaMes = new Date(hoy.getFullYear(), hoy.getMonth(), 1);
    const ultimoDiaMes = new Date(hoy.getFullYear(), hoy.getMonth() + 1, 0);

    const desdeMesStr = formatDate(primerDiaMes, 'yyyy-MM-dd', 'en-US');
    const hastaMesStr = formatDate(ultimoDiaMes, 'yyyy-MM-dd', 'en-US');


    // ==========================================
    //  LLAMADAS PARA LAS MÉTRICAS DEL DÍA
    // ==========================================

    // Total Ingresos Diarios
    this.movimientosService.totalIngresos(fechaHoyStr, fechaHoyStr).subscribe({
      next: (total) => this.cajaDelDia = total,
      error: (err) => console.error('Error al cargar ingresos diarios', err)
    });

    // Total Egresos Diarios
    this.movimientosService.totalEgresos(fechaHoyStr, fechaHoyStr).subscribe({
      next: (total) => this.egresosDelDia = total,
      error: (err) => console.error('Error al cargar egresos diarios', err)
    });

    // Balance Diario
    this.movimientosService.balance(fechaHoyStr, fechaHoyStr).subscribe({
      next: (total) => this.balanceDelDia = total,
      error: (err) => console.error('Error al cargar balance diario', err)
    });


    // ==========================================
    //  LLAMADAS PARA LAS MÉTRICAS DEL MES
    // ==========================================

    // Total Ingresos Mensuales
    this.movimientosService.totalIngresos(desdeMesStr, hastaMesStr).subscribe({
      next: (total) => this.ingresosMensuales = total,
      error: (err) => console.error('Error al cargar ingresos mensuales', err)
    });

    // Total Egresos Mensuales
    this.movimientosService.totalEgresos(desdeMesStr, hastaMesStr).subscribe({
      next: (total) => this.egresosMensuales = total,
      error: (err) => console.error('Error al cargar egresos mensuales', err)
    });

    // Balance Mensual
    this.movimientosService.balance(desdeMesStr, hastaMesStr).subscribe({
      next: (total) => this.balanceMensual = total,
      error: (err) => console.error('Error al cargar balance mensual', err)
    });
  }

   irRegistroFinansas(){

    this.router.navigate(['/registraMovimiento']);
  }

  ultimoMovi: any[] = [];

  ultmosmovimientos(){
    this.movimientosService.obtenerUltimoMovimiento().subscribe(
      (repuesta: any) => {
        this.ultimoMovi = repuesta;
      }
    )
  }

}