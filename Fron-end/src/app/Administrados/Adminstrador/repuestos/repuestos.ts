import { Component, OnInit } from '@angular/core';
import { HeaderAdmin } from "../header-admin/header-admin";
import { Header } from "../../../header/header";
import { RepuestoService } from '../../../Servicio/repuesto-service';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-repuestos',
  imports: [HeaderAdmin, Header, FormsModule],
  templateUrl: './repuestos.html',
  styleUrl: './repuestos.css',
})
export class Repuestos implements OnInit{

  constructor(private repuetoService: RepuestoService,
    private router: Router
  ){}

  ngOnInit(): void {
    this.obtenerRepuesto();
  }


  // Variables
  repuestos: any[] = [];

  // copia de todos los repuestos
  repuestosOriginal: any[] = [];


  // filtros
  nombreBuscar: string = '';
  categoriaBuscar: string = '';



  obtenerRepuesto(){

    this.repuetoService.obtenerRepuestos().subscribe(
      (repuesta: any) => {

        this.repuestos = repuesta;

        // guardamos todos los datos originales
        this.repuestosOriginal = repuesta;

      });

  }



  buscarRepuesto(){

    let nombre = this.nombreBuscar.toLowerCase().trim();
    let categoria = this.categoriaBuscar.toLowerCase().trim();


    this.repuestos = this.repuestosOriginal.filter(rep => {


      let coincideNombre = 
        rep.nombre.toLowerCase().includes(nombre);


      let coincideCategoria =
        categoria === '' ||
        categoria === 'todas' ||
        rep.categoria.toLowerCase().includes(categoria);



      return coincideNombre && coincideCategoria;


    });


  }



  limpiarBusqueda(){

    this.nombreBuscar = '';
    this.categoriaBuscar = '';

    this.repuestos = this.repuestosOriginal;

  }

  verRepuesto(idRepuesto: number){

    this.router.navigate(['/verRepuesto', idRepuesto]);
  }


}