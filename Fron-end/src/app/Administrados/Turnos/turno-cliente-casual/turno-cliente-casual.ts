import { Component, OnInit } from '@angular/core';
import { TurnosService } from '../../../Servicio/turnos-service';
import { ServiciosService } from '../../../Servicio/servicios-service';
import { HeaderAdmin } from "../../Adminstrador/header-admin/header-admin";
import { FormsModule } from '@angular/forms';
import { Header } from "../../../header/header";
import { ClienteServicio } from '../../../Servicio/cliente-servicio';
import { Router } from '@angular/router';
import { Location } from '@angular/common';

@Component({
  selector: 'app-turno-cliente-casual',
  standalone: true,
  imports: [HeaderAdmin, FormsModule, Header],
  templateUrl: './turno-cliente-casual.html',
  styleUrl: './turno-cliente-casual.css',
})
export class TurnoClienteCasual implements OnInit{


  constructor(private turnoService: TurnosService,
    private servicioService: ServiciosService,
    private clienteService: ClienteServicio,
    private router: Router,
    private location: Location
  ){}


  ngOnInit(): void {
    this.obtenerServicios();
  }

  //Definicion de variables

  nombre: string = '';
  contacto: string = '';
  email: string = '';
  marcaVehiculo: string = '';
  modeloVehiculo: string = '';
  patenteVehiculo: string = '';
  descripcion: string = '';
  fecha: string = '';
  idServicio: number = 0;
  servicios: any[] = [];
  cliente: any[] = [];
  idVehiculo: number = 0;
  tipoCliente: '' | 'registrado' | 'casual' = '';


  obtenerServicios(){
    this.servicioService.obtenerServicios().subscribe(
      (repuesta: any) => {
        this.servicios= repuesta;
      }
    )
  }

  obtenerCliente(){
    this.clienteService.obtenerTodosLosClientes().subscribe(
      (repuesta : any) =>{
        this.cliente = repuesta;
      }
    )
  }

      seleccionarTipo(tipo: 'registrado' | 'casual') {
      this.tipoCliente = tipo;

        if(this.tipoCliente == 'registrado'){
        this.obtenerCliente();
      }
    
    }

      crearTurnoCasual() {
      this.turnoService.crearTurnoCasual(
        this.descripcion,
        this.idServicio,
        this.idVehiculo,
        this.fecha,
        {
          nombreCliente: this.nombre,
          contactoCliente: this.contacto,
          email: this.email,
          marcaVehiculo: this.marcaVehiculo,
          modeloVehiculo: this.modeloVehiculo,
          patenteVehiculo: this.patenteVehiculo
        }
      ).subscribe({
        next: (resp) => {
          alert('Turno  creado Correctamente');
        console.log(resp);
        this.router.navigate(['/turnosAsignacion'])
        },
        error: (err) => {

        console.error('Error al crear factura');
        console.error(err);
      }});
    }





  

}
