import { Component, OnInit } from '@angular/core';
import { HeaderAdmin } from "../header-admin/header-admin";
import { Header } from "../../../header/header";
import { BalanceServicio } from '../../../Servicio/balance-servicio';
import { Chart } from 'chart.js/auto';
import { TurnosService } from '../../../Servicio/turnos-service';
import ChartDataLabels from 'chartjs-plugin-datalabels';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [HeaderAdmin, Header],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css',
})
export class Dashboard implements OnInit {
  
  constructor(
    private balanceService: BalanceServicio,
    private turnoService: TurnosService
  ) {}
  
  ngOnInit(): void {
    // 1. PRIMERO registrar plugins globales de Chart.js
    Chart.register(ChartDataLabels);

    // 2. LUEGO ejecutar las llamadas a APIs y generación de gráficos
    this.obtenerBalance();
    this.graficoServicioMasSolicitado();
    this.graficoTurnosPorMes();
    this.graficoTurnosEstado();
  }

  balances: any[] = [];
  servicioSolicitados: any[] = [];
  turnosPorMes: any[] = [];
  turnosEstado: any[] = [];
  
  chartInstance: any;       // Gráfico de Balances
  graficoTurnos!: Chart;    // Gráfico de Turnos por Mes
  graficoEstado!: Chart;    // Gráfico de Dona por Estado

  private formatCurrency(value: number): string {
    if (value >= 1e6) {
      return '$' + (value / 1e6).toFixed(1).replace('.0', '') + 'M';
    } else if (value >= 1e3) {
      return '$' + (value / 1e3).toFixed(0) + 'k';
    }
    return '$' + value;
  }

  obtenerBalance() {
    const anio = 2026;
    this.balanceService.obtenerBalance(anio).subscribe({
      next: (respuesta: any) => {
        this.balances = respuesta || [];
        this.crearGrafico();
      },
      error: (err) => console.error('Error cargando balances:', err)
    });
  }

  /* ==========================================================
     1. GRÁFICO DE BALANCES (INGRESO vs GASTOS vs BALANCE NETO)
     ========================================================== */
  crearGrafico() {
    if (this.chartInstance) {
      this.chartInstance.destroy();
    }

    // Si la API devuelve datos, los usamos; de lo contrario mantenemos el array original
    const balancesFiltrados = this.balances.filter((item: any) => 
      item.totalIngresos > 0 || item.totalGastos > 0 || item.balance !== 0
    );

    const datosUsar = balancesFiltrados.length > 0 ? balancesFiltrados : this.balances;

    const labels = datosUsar.map((item: any) => item.nombreMes);
    const ingresos = datosUsar.map((item: any) => item.totalIngresos);
    const gastos = datosUsar.map((item: any) => item.totalGastos);
    const balance = datosUsar.map((item: any) => item.balance);

    const data = {
      labels: labels,
      datasets: [
        {
          label: 'Ingresos',
          data: ingresos,
          type: 'bar' as const,
          backgroundColor: '#10b981',
          borderColor: '#10b981',
          borderWidth: 0,
          borderRadius: 6,
          borderSkipped: false,
          yAxisID: 'y',
          order: 2,
          barPercentage: 0.5,
          categoryPercentage: 0.65
        },
        {
          label: 'Gastos',
          data: gastos,
          type: 'bar' as const,
          backgroundColor: '#f43f5e',
          borderColor: '#f43f5e',
          borderWidth: 0,
          borderRadius: 6,
          borderSkipped: false,
          yAxisID: 'y',
          order: 2,
          barPercentage: 0.5,
          categoryPercentage: 0.65
        },
        {
          label: 'Balance Neto',
          data: balance,
          type: 'line' as const,
          borderColor: '#3b82f6',
          backgroundColor: 'rgba(59, 130, 246, 0.05)',
          fill: true,
          borderWidth: 3,
          pointRadius: 4,
          pointHoverRadius: 7,
          pointBackgroundColor: '#3b82f6',
          pointBorderColor: '#ffffff',
          pointBorderWidth: 2,
          yAxisID: 'y1',
          order: 1,
          tension: 0.35
        }
      ]
    };

    const config = {
      type: 'bar' as const,
      data: data,
      options: {
        responsive: true,
        maintainAspectRatio: false,
        interaction: {
          mode: 'index' as const,
          intersect: false
        },
        plugins: {
          legend: {
            display: true,
            position: 'bottom' as const,
            labels: {
              usePointStyle: true,
              pointStyle: 'circle' as const,
              padding: 20,
              font: {
                family: "'Inter', sans-serif",
                size: 12,
                weight: 'normal' as const
              },
              color: '#64748b'
            }
          },
          datalabels: {
            display: false
          },
          tooltip: {
            backgroundColor: '#0f172a',
            titleFont: { family: "'Inter', sans-serif", size: 13, weight: 'bold' as const },
            bodyFont: { family: "'Inter', sans-serif", size: 12 },
            padding: 12,
            cornerRadius: 8,
            boxPadding: 6
          }
        },
        scales: {
          x: {
            grid: { display: false },
            ticks: {
              color: '#64748b',
              font: { family: "'Inter', sans-serif", size: 11, weight: 'normal' as const }
            }
          },
          y: {
            type: 'linear' as const,
            display: true,
            position: 'left' as const,
            beginAtZero: true,
            grid: { color: '#f1f5f9' },
            ticks: {
              color: '#94a3b8',
              font: { family: "'Inter', sans-serif", size: 11 },
              callback: (value: any) => this.formatCurrency(value)
            },
            border: { dash: [5, 5] }
          },
          y1: {
            type: 'linear' as const,
            display: true,
            position: 'right' as const,
            beginAtZero: true,
            grid: { drawOnChartArea: false },
            ticks: {
              color: '#94a3b8',
              font: { family: "'Inter', sans-serif", size: 11 },
              callback: (value: any) => this.formatCurrency(value)
            }
          }
        }
      }
    };

    this.chartInstance = new Chart('miGrafico', config);
  }

  /* ==========================================================
     2. SERVICIOS MÁS SOLICITADOS
     ========================================================== */
  graficoServicioMasSolicitado(): void {
    this.turnoService.serviciosMasSolicitados().subscribe({
      next: (respuesta: any[]) => {
        this.servicioSolicitados = (respuesta || [])
          .sort((a, b) => b.cantidad - a.cantidad)
          .slice(0, 5);
      },
      error: (error) => console.error('Error cargando servicios:', error)
    });
  }

  /* ==========================================================
     3. GRÁFICO DE TURNOS POR MES
     ========================================================== */
  graficoTurnosPorMes(): void {
    this.turnoService.cantidadTurnosPorMes().subscribe({
      next: (respuesta: any[]) => {
        this.turnosPorMes = respuesta || [];

        const meses = [
          '', 'Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun', 'Jul', 'Ago', 'Sep', 'Oct', 'Nov', 'Dic'
        ];

        const labels = this.turnosPorMes.map(item => meses[item.mes] || item.mes);
        const cantidades = this.turnosPorMes.map(item => item.cantidad);

        if (this.graficoTurnos) {
          this.graficoTurnos.destroy();
        }

        this.graficoTurnos = new Chart('graficoTurnos', {
          type: 'bar',
          data: {
            labels: labels,
            datasets: [{
              label: 'Turnos',
              data: cantidades,
              backgroundColor: '#7B5CFF',
              borderRadius: 6,
              borderSkipped: false,
              barPercentage: 0.5,
              categoryPercentage: 0.7
            }]
          },
          options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
              legend: { display: false },
              datalabels: { display: false },
              tooltip: {
                backgroundColor: '#0f172a',
                padding: 10,
                cornerRadius: 8
              }
            },
            scales: {
              y: {
                beginAtZero: true,
                grid: { color: '#f1f5f9' },
                ticks: {
                  precision: 0,
                  color: '#94a3b8',
                  font: { family: "'Inter', sans-serif", size: 11 }
                },
                border: { dash: [5, 5] }
              },
              x: {
                grid: { display: false },
                ticks: {
                  color: '#64748b',
                  font: { family: "'Inter', sans-serif", size: 11, weight: 'normal' as const }
                }
              }
            }
          }
        });
      },
      error: (error) => console.error('Error cargando turnos por mes:', error)
    });
  }

  /* ==========================================================
     4. GRÁFICO DE DONA (TURNOS POR ESTADO)
     ========================================================== */
  graficoTurnosEstado(): void {
    this.turnoService.turnosPorEstado().subscribe({
      next: (respuesta: any[]) => {
        const respuestaSegura = respuesta || [];
        const estadosPermitidos = ['CONFIRMADO', 'CANCELADO', 'PENDIENTE', 'TERMINADO', 'TERMINADOS', 'FINALIZADO'];
        
        const datosFiltrados = respuestaSegura.filter(e => 
          e.estado && estadosPermitidos.includes(e.estado.toUpperCase())
        );

        const labels = datosFiltrados.map(e => {
          const est = e.estado.toUpperCase();
          if (est === 'CONFIRMADO') return 'Confirmados';
          if (est === 'CANCELADO') return 'Cancelados';
          if (est === 'PENDIENTE') return 'Pendientes';
          if (['TERMINADO', 'TERMINADOS', 'FINALIZADO'].includes(est)) return 'Terminados';
          return e.estado;
        });

        const cantidades = datosFiltrados.map(e => e.cantidad);
        const total = cantidades.reduce((a, b) => a + b, 0);

        if (this.graficoEstado) {
          this.graficoEstado.destroy();
        }

        const centerText = {
          id: 'centerText',
          afterDraw(chart: any) {
            const { ctx } = chart;
            const meta = chart.getDatasetMeta(0);

            if (!meta || !meta.data.length) return;

            const x = meta.data[0].x;
            const y = meta.data[0].y;

            ctx.save();
            ctx.textAlign = 'center';
            ctx.textBaseline = 'middle';

            ctx.fillStyle = '#64748b';
            ctx.font = "500 13px 'Inter', sans-serif";
            ctx.fillText('Total', x, y - 18);

            ctx.fillStyle = '#1e293b';
            ctx.font = "bold 26px 'Inter', sans-serif";
            ctx.fillText(total.toString(), x, y + 6);

            ctx.restore();
          }
        };

        this.graficoEstado = new Chart('graficoEstado', {
          type: 'doughnut',
          data: {
            labels,
            datasets: [{
              data: cantidades,
              backgroundColor: ['#3b82f6', '#ef4444', '#f59e0b', '#10b981'],
              borderWidth: 3,
              borderColor: '#ffffff',
              hoverBorderWidth: 3
            }]
          },
          options: {
            responsive: true,
            maintainAspectRatio: false,
            cutout: '75%',
            plugins: {
              legend: {
                position: 'right' as const,
                labels: {
                  usePointStyle: true,
                  pointStyle: 'circle' as const,
                  padding: 20,
                  font: {
                    family: "'Inter', sans-serif",
                    size: 13,
                    weight: 'normal' as const
                  },
                  color: '#334155',
                  generateLabels(chart) {
                    const dataset = chart.data.datasets[0];
                    return chart.data.labels!.map((label: any, i: number) => ({
                      text: `${label} (${dataset.data[i]})`,
                      fillStyle: (dataset.backgroundColor as any[])[i],
                      strokeStyle: (dataset.backgroundColor as any[])[i],
                      lineWidth: 0,
                      hidden: false,
                      index: i,
                      pointStyle: 'circle'
                    }));
                  }
                }
              },
              datalabels: {
                color: '#ffffff',
                font: {
                  family: "'Inter', sans-serif",
                  weight: 'bold' as const,
                  size: 11
                },
                formatter(value: any, context: any) {
                  const dataArr = context.dataset.data;
                  const totalSum = dataArr.reduce((a: number, b: number) => a + b, 0);
                  const percentage = totalSum > 0 ? (value / totalSum * 100) : 0;
                  return percentage > 10 ? percentage.toFixed(0) + '%' : '';
                }
              },
              tooltip: {
                backgroundColor: '#0f172a',
                cornerRadius: 8,
                padding: 10
              }
            }
          },
          plugins: [centerText]
        });
      },
      error: (error) => console.error('Error cargando turnos por estado:', error)
    });
  }
}