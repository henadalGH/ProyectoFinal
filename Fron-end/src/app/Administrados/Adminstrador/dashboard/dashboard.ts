import { Component, OnInit } from '@angular/core';
import { HeaderAdmin } from "../header-admin/header-admin";
import { Header } from "../../../header/header";
import { BalanceServicio } from '../../../Servicio/balance-servicio';
import { Chart } from 'chart.js/auto';


@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [HeaderAdmin, Header],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css',
})
export class Dashboard implements OnInit{
  
  constructor(private balanceService: BalanceServicio){}
  
  ngOnInit(): void {
    this.obtenerBalance();
  }

  balances: any[] = [];

  obtenerBalance(){

      const anio = 2026;
      this.balanceService.obtenerBalance(anio).subscribe(
        (repuesta: any) =>{
          this.balances = repuesta;
          console.log(this.balances)
          this.crearGrafico();
        });
  }



crearGrafico() {
  // Filtrar solo los meses con datos (excluyendo los que están en cero)
  const balancesFiltrados = this.balances.filter((item: any) => 
    item.totalIngresos > 0 || item.totalGastos > 0 || item.balance !== 0
  );

  // Preparar labels y datos desde el backend
  const labels = balancesFiltrados.map((item: any) => item.nombreMes);
  const ingresos = balancesFiltrados.map((item: any) => item.totalIngresos);
  const gastos = balancesFiltrados.map((item: any) => item.totalGastos);
  const balance = balancesFiltrados.map((item: any) => item.balance);

  const data = {
    labels: labels,
    datasets: [
      {
        label: 'Ingresos',
        data: ingresos,
        type: 'bar' as const,
        backgroundColor: 'rgba(75, 192, 75, 0.6)',
        borderColor: 'rgb(75, 192, 75)',
        borderWidth: 1,
        yAxisID: 'y',
        order: 2
      },
      {
        label: 'Gastos',
        data: gastos,
        type: 'bar' as const,
        backgroundColor: 'rgba(255, 99, 132, 0.6)',
        borderColor: 'rgb(255, 99, 132)',
        borderWidth: 1,
        yAxisID: 'y',
        order: 2
      },
      {
        label: 'BALANCE NETO',
        data: balance,
        type: 'line' as const,
        borderColor: 'rgb(54, 162, 235)',
        backgroundColor: 'rgba(54, 162, 235, 0.1)',
        borderWidth: 3,
        pointRadius: 6,
        pointBackgroundColor: 'rgb(54, 162, 235)',
        pointBorderColor: '#fff',
        pointBorderWidth: 2,
        yAxisID: 'y1',
        order: 1,
        tension: 0.4
      }
    ]
  };

  const config = {
    type: 'bar' as const,
    data: data,
    options: {
      responsive: true,
      maintainAspectRatio: true,
      interaction: {
        mode: 'index' as const,
        intersect: false
      },
      plugins: {
        legend: {
          display: true,
          position: 'top' as const
        }
      },
      scales: {
        y: {
          type: 'linear' as const,
          display: true,
          position: 'left' as const,
          beginAtZero: true,
          title: {
            display: true,
            text: 'Monto (USD)'
          }
        },
        y1: {
          type: 'linear' as const,
          display: true,
          position: 'right' as const,
          beginAtZero: true,
          title: {
            display: true,
            text: 'Balance Neto (USD)'
          },
          grid: {
            drawOnChartArea: false
          }
        }
      }
    }
  };

  new Chart('miGrafico', config);

}
      


  

}
