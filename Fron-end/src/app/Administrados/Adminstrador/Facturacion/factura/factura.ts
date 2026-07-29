import { Component, OnInit } from '@angular/core';
import { HeaderAdmin } from "../../header-admin/header-admin";
import { OrdenTrabajoService } from '../../../../Servicio/orden-trabajo-service';
import { Route, Router, RouterLink } from "@angular/router";
import { FacturaServicio } from '../../../../Servicio/factura-servicio';
import { Header } from "../../../../header/header";
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-factura',
  standalone: true,
  imports: [HeaderAdmin, Header, RouterLink, CommonModule, FormsModule],
  templateUrl: './factura.html',
  styleUrl: './factura.css',
})
export class Factura implements OnInit{

  constructor(private ordenService: OrdenTrabajoService
    ,private facturaService: FacturaServicio,
    private route: Router
  ){}

  facturas: any[]= [];
  facEmitida!: any;
  facaturaPendientes: any []=[];
  facturaCobradas!: any;
  facturaImpagas!: any;
  total$Pendiente: any;

  searchCliente: string = '';
  filterEstado: string = '';
  fechaDesde: string = '';
  fechaHasta: string = '';

  get facturasFiltradas(): any[] {
    return this.facturas.filter((fac) => {
      const nombreCompleto = `${fac.nombreCliente ?? ''} ${fac.apellidoCliente ?? ''}`.toLowerCase();
      const buscaNombre = this.searchCliente.trim().toLowerCase();
      const coincideNombre = !buscaNombre || nombreCompleto.includes(buscaNombre);

      const estado = fac.estadoPresupuesto ?? '';
      let coincideEstado = true;
      if (this.filterEstado === 'impaga') {
        coincideEstado = estado === 'ENVIADO';
      } else if (this.filterEstado === 'pagada') {
        coincideEstado = estado === 'PAGADO';
      } else if (this.filterEstado === 'sin-enviar') {
        coincideEstado = estado === 'PENDIENTE';
      }

      const fechaRegistro = fac.fechaRegistro ? new Date(fac.fechaRegistro) : null;
      let coincideDesde = true;
      let coincideHasta = true;
      if (this.fechaDesde && fechaRegistro) {
        coincideDesde = fechaRegistro >= new Date(this.fechaDesde);
      }
      if (this.fechaHasta && fechaRegistro) {
        const fechaFin = new Date(this.fechaHasta);
        fechaFin.setHours(23, 59, 59, 999);
        coincideHasta = fechaRegistro <= fechaFin;
      }

      return coincideNombre && coincideEstado && coincideDesde && coincideHasta;
    });
  }

  ngOnInit(): void {
   this.obtenerOrdenParaFacturar();
   this.obtenerFacturas();
   this.contarFacturasEmitidas();
   this.contarFacturasCobradas();
   this.contarFacturasImpagas();
   this.totalACobrar()
  }

  factura: any[] = [];
  
  obtenerOrdenParaFacturar(){

    const estado = 'PARA_FACTURAR';

    this.ordenService.obtenerOrdenPorEstado(estado).subscribe(
      (repuesta: any) =>{
        this.factura = repuesta;
        console.log(this.factura.length);
      });
    }


    
    obtenerFacturas(){
      this.facturaService.obtenerFacturas().subscribe(
        (repuesta: any) => {
          this.facturas = repuesta;
        });
    }

    verFactura(id: number){
      this.route.navigate(['/verFactura', id]);

    }

    contarFacturasEmitidas(){
      this.facturaService.contarFacturasEmitida().subscribe(
        
          (repuesta: any) => {
            this.facEmitida = repuesta;
            console.log("Facturas emitidad " + this.facEmitida)
          });
    }

    contarFacturasCobradas(){
      this.facturaService.contarFacturasCobradas().subscribe(
        (repuesta: any) =>{
          this.facturaCobradas = repuesta;
          console.log("Factura cobrada: " + this.facturaCobradas)
        }
      )
    }

    
    contarFacturasImpagas(){
      this.facturaService.contarFacturasImpagas().subscribe(
        (repuesta: any) =>{
          this.facturaImpagas = repuesta;
          console.log("Factura impagas: " + this.facturaImpagas)
        }
      )
    }

    totalACobrar(){
      this.facturaService.obternerTotalFacturaImpagas().subscribe(
        (repuesta: any) =>{
          this.total$Pendiente = repuesta;
          console.log("Total Factura a cobrar: " + this.total$Pendiente)
        }
      )
    }

}
