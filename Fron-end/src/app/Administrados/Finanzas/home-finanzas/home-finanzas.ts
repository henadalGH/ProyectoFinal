import { Component, OnInit } from '@angular/core'; 
import { HeaderAdmin } from "../../Adminstrador/header-admin/header-admin";
import { Header } from "../../../header/header";
import { CommonModule, formatDate } from '@angular/common'; 
import { MovimientosService } from '../../../Servicio/movimientos-service';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';


@Component({
  selector: 'app-home-finanzas',
  imports: [HeaderAdmin, Header,CommonModule],
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

  // Gestión de sueldos
  yaPagado: boolean = false;
  cargando: boolean = false;
  mensajeSueldos: string = '';
  private readonly adminId = 1; // Simulación de ID de administrador

  constructor(
    private movimientosService: MovimientosService,
    private router: Router
  ){}

  ngOnInit(): void {
    this.cargarDatosDashboard();
    this.ultmosmovimientos();
    this.consultarEstadoSueldos();
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

  consultarEstadoSueldos(): void {
    this.cargando = true;
    this.mensajeSueldos = 'Consultando estado de sueldos...';

    this.movimientosService.sueldosYaPagados().subscribe({
      next: (res) => {
        this.yaPagado = res.total === 1.0;
        this.mensajeSueldos = this.yaPagado
          ? 'Los sueldos del mes ya fueron pagados.'
          : 'Los sueldos del mes aún no se pagaron.';
      },
      error: (err) => {
        console.error('Error al consultar sueldos pagados', err);
        this.yaPagado = false;
        this.mensajeSueldos = 'No se pudo consultar el estado de sueldos. Intente más tarde.';
      },
      complete: () => {
        this.cargando = false;
      }
    });
  }

  ejecutarPagoSueldos(): void {
    if (this.yaPagado || this.cargando) {
      return;
    }

    const confirmar = window.confirm('¿Desea confirmar el pago de los sueldos del mes?');
    if (!confirmar) {
      return;
    }

    this.cargando = true;
    this.mensajeSueldos = 'Procesando pago de sueldos...';

    this.movimientosService.pagarSueldos(this.adminId).subscribe({
      next: (res) => {
        if (res.total === 1.0) {
          this.yaPagado = true;
          this.mensajeSueldos = res.mensage || 'Sueldos pagados correctamente.';
          Swal.fire({
            icon: 'success',
            title: 'Pago realizado',
            text: this.mensajeSueldos,
            confirmButtonColor: '#2563eb'
          });
        } else {
          this.yaPagado = false;
          this.mensajeSueldos = res.mensage || 'El pago no se registró correctamente.';
          Swal.fire({
            icon: 'warning',
            title: 'Pago incompleto',
            text: this.mensajeSueldos,
            confirmButtonColor: '#2563eb'
          });
        }
        this.consultarEstadoSueldos();
      },
      error: (err) => {
        console.error('Error al pagar sueldos', err);
        this.mensajeSueldos = 'Error al procesar el pago de sueldos. Intente de nuevo.';
        Swal.fire({
          icon: 'error',
          title: 'Error',
          text: this.mensajeSueldos,
          confirmButtonColor: '#2563eb'
        });
      },
      complete: () => {
        this.cargando = false;
      }
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