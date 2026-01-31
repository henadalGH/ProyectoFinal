import { Component, OnInit } from '@angular/core';
import { HeaderAdmin } from "../../Clientes/header-admin/header-admin";
import { RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { MovimientoService } from '../../../Servicio/movimiento-service';

@Component({
  selector: 'app-home-movimientos',
  imports: [HeaderAdmin, RouterLink, FormsModule],
  templateUrl: './home-movimientos.html',
  styleUrl: './home-movimientos.css',
})
export class HomeMovimientos implements OnInit{
  
  constructor(
    private movimientoService: MovimientoService
  ){ }

  movimiento: any[] = [];
  fechaHoy : string = '';
  
  
  ngOnInit(): void {
    this.obtenerPorFecha();
    const dia = new Date().toLocaleDateString('sv-SE');
    this.fechaHoy = dia;

  }
  obtenerPorFecha() {
  const fecha = new Date().toLocaleDateString('sv-SE');

  this.movimientoService.obtenerPorFecha(fecha).subscribe(
    (res: any[])=> {
      this.movimiento = res;
    });
  }



}
