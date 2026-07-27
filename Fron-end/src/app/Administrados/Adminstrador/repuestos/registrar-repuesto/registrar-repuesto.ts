import { Component } from '@angular/core';
import { Header } from "../../../../header/header";
import { HeaderAdmin } from "../../header-admin/header-admin";
import { RepuestoService } from '../../../../Servicio/repuesto-service';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { Location } from '@angular/common';

@Component({
  selector: 'app-registrar-repuesto',
  imports: [Header, HeaderAdmin, FormsModule],
  templateUrl: './registrar-repuesto.html',
  styleUrl: './registrar-repuesto.css',
})
export class RegistrarRepuesto {

  constructor(private repuestoServicio: RepuestoService,
    private router: Router,
    private location: Location
  ){}

  //Variables
  nombre: string = '';
  marca: string = '';
  codigo: string = '';
  stock: number = 0;
  stockMin: number = 0;
  precioCompra: number = 0;
  categoria: string = '';


  crearRepuesto(){

    const body = {
      nombre: this.nombre,
      marca: this.marca,
      codigo: this.codigo,
      stock: this.stock,
      stockMin: this.stockMin,
      precioCompra: this.precioCompra,
      categoria: this.categoria
    }

    this.repuestoServicio.crearRepuesto(body).subscribe({
      next:any => {
        this.router.navigate(['/repuesto'])
      }});
  }

  volverAtras(){
    this.location.back();
  }

}
