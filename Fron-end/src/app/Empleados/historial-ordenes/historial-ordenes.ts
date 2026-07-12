import { Component, OnInit } from '@angular/core';
import { HeaderEmpleado } from "../header-empleado/header-empleado";
import { Header } from "../../header/header";
import { OrdenTrabajoService } from '../../Servicio/orden-trabajo-service';
import { AuthService } from '../../AuthServicio/auth-service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-historial-ordenes',
  imports: [HeaderEmpleado, Header, FormsModule],
  templateUrl: './historial-ordenes.html',
  styleUrl: './historial-ordenes.css',
})
export class HistorialOrdenes implements OnInit{
  
  constructor(private ordenServicio: OrdenTrabajoService,
    private athuService: AuthService
  ){  }

  historial: any[]= [];

  ngOnInit(): void {
  
    const idEmpleado = this.athuService.getEntityId();

    if(idEmpleado){
      this.ordenServicio.obtenrHistorialOrdenes(idEmpleado).subscribe(
        (repuesta: any[]) =>{
          this.historial = repuesta;
        });}
  }

  textoBusqueda = '';

get historialFiltrado() {

  if (!this.textoBusqueda.trim()) {
    return this.historial;
  }

  const texto = this.textoBusqueda.toLowerCase();

  return this.historial.filter(o =>
    o.nombreCliente.toLowerCase().includes(texto) ||
    o.nombreServicio.toLowerCase().includes(texto) ||
    o.patente.toLowerCase().includes(texto) ||
    `${o.marca} ${o.modelo}`.toLowerCase().includes(texto)
  );
}

}
