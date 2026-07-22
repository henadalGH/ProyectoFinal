import { Component, OnInit } from '@angular/core';
import { Headercliente } from "../../headercliente/headercliente";
import { VehiculoService } from '../../../Servicio/vehiculo-service';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ServiciosService } from '../../../Servicio/servicios-service';
import { FormsModule } from '@angular/forms';
import { TurnosService } from '../../../Servicio/turnos-service';
import { AuthService } from '../../../AuthServicio/auth-service';
import { HeaderAdmin } from "../../../Administrados/Adminstrador/header-admin/header-admin";
import { Header } from "../../../header/header";
import { Location } from '@angular/common';

@Component({
  selector: 'app-detalle-vehiculo',
  imports: [Headercliente, FormsModule, HeaderAdmin, Header],
  standalone: true,
  templateUrl: './detalle-vehiculo.html',
  styleUrl: './detalle-vehiculo.css',
})
export class DetalleVehiculo implements OnInit{

  
  constructor(private vehiculoServicio: VehiculoService,
    private route: ActivatedRoute,
    private router: Router,
    private servicioServise: ServiciosService,
    private turnoService: TurnosService,
    private authService: AuthService, 
    private location: Location
  ){}

  mostrarModal: boolean = false;

  vehiculo!: any;
  id!: number;
  servicio: any[] = []; 
  rol!: any;
  
  volverAtras(){
    this.location.back();
  }
  ngOnInit(): void {
    this.id = Number (this.route.snapshot.paramMap.get('id'));

    this.vehiculoServicio.obtenerVehiculoPorId(this.id).subscribe(
      datos => {
        this.vehiculo = datos;
      });

    this.obtenerServicios();
    
    this.rol = this.authService.getRol();
  }


  verMiHistorial(id: number){
    this.router.navigate(['/miHistorial', id]);
  }

  ajustarAltura(textarea: HTMLTextAreaElement): void {
  textarea.style.height = 'auto';
  textarea.style.height = textarea.scrollHeight + 'px';
}


  //Seccion de servisios


  obtenerServicios(){
    this.servicioServise.obtenerServicios().subscribe(
      (repuesta: any) => {
        this.servicio = repuesta;
      }
    )
  }

  idServicio: number = 0;
  descripcion: string = '';

  solicitarTurno(){

    this.turnoService.solicitarTurno(this.descripcion, this.id, this.idServicio).subscribe({      
      next: () => {
        this.cerrarModal();
      }});
  }

  abrirModal(id: number){

    this.mostrarModal = true;
  }
    
  cerrarModal(){
    this.mostrarModal = false;
  }
}
