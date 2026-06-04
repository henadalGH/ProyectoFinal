import { Component, OnInit } from '@angular/core';
import { HeaderAdmin } from "../../Adminstrador/header-admin/header-admin";
import { TurnosService } from '../../../Servicio/turnos-service';
import { RecuperarContrasenia } from '../../../recuperar-contrasenia/recuperar-contrasenia';
import { Router } from '@angular/router';
import { EmpleadoService } from '../../../Servicio/empleado-service';

@Component({
  selector: 'app-home-ordenes',
  imports: [HeaderAdmin],
  templateUrl: './home-ordenes.html',
  styleUrl: './home-ordenes.css',
})
export class HomeOrdenes implements OnInit{


  constructor(private turnoService: TurnosService,
    private router: Router,
    private empleadoService: EmpleadoService
  ){}

  ngOnInit(): void {
    this.obtenerTurnosComfirmados();
    this.obtenerEpleadoMecanico();
  }

  //Defniciones de variabes
  turnos: any[] = [];
  empleados: any[] = [];
  modal : boolean = false;
  
  obtenerTurnosComfirmados(){

    const estado: string = 'CONFIRMADO'

    this.turnoService.obtenerPorEstado(estado).subscribe(
      (repuesta: any) => {
        this.turnos = repuesta;
      });
  }


  crearOrden(idturno: number){
    this.router.navigate(['/crearOrden/', idturno]);
  }

  obtenerEpleadoMecanico(){
    this.empleadoService.obtenerEmpleadoMEcanico().subscribe
    (
      (repuesta: any) => {
        this.empleados = repuesta;
      }

    )}


  abrilModal(id: number){
    
    
    this.modal = true;
  }

  cerrarmodal(){

    this.modal = false;
  }

  



}
