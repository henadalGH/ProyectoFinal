import { Component, OnInit } from '@angular/core';
import { HeaderAdmin } from "../header-admin/header-admin";
import { Header } from "../../../header/header";
import { RepuestoService } from '../../../Servicio/repuesto-service';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-repuestos',
  imports: [HeaderAdmin, Header, FormsModule, RouterLink, CommonModule],
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

  mostarModal: boolean = false;
  mostarModalAjuste: boolean = false;
  
  marca: string = '';
  nombre: string = '';
  porcentaje: number = 0;
  nombreMarca: any[]=[];
  porcentajeVenta: number = 0; 
  tipoDescuento: string = '';

  abrirAjuste(){
    this.mostarModalAjuste = true;
  }

  cerrarAjuste(){
    this.mostarModalAjuste = false;
  }

  modificaPrecioVenta(){
    this.repuetoService.modificarGananciaRepuesto(this.porcentajeVenta, this.tipoDescuento).subscribe(
      { next: any =>{
          this.cerrarAjuste();
          this.obtenerRepuesto();
          
        }});
  }


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

  abrirModal(){
    this.mostarModal = true;
    this.obtenerNombreMarcaRepuesto();
  }

  cerrarModal(){
    this.mostarModal = false;
  }


  aumentarPrecioRepuesto(){

    this.repuetoService.modificarPrecioBase(this.porcentaje, this.marca, this.nombre).subscribe(
      {
        next: any =>{
          this.cerrarModal();
          this.obtenerRepuesto();
          
        }
      }
    )
  }

  obtenerNombreMarcaRepuesto(){
    this.repuetoService.obtenerNombreMarcasRepuesto().subscribe(
      (repuesta: any) => {
        this.nombreMarca = repuesta;
      }
    )
  }


}