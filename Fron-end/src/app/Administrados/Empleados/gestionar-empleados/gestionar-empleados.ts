import { Component, OnInit } from '@angular/core';
import { EmpleadoService } from '../../../Servicio/empleado-service';
import { HeaderAdmin } from "../../Clientes/header-admin/header-admin";
import { RouterLink } from "@angular/router";

@Component({
  selector: 'app-gestionar-empleados',
  imports: [HeaderAdmin, RouterLink],
  templateUrl: './gestionar-empleados.html',
  styleUrl: './gestionar-empleados.css',
})
export class GestionarEmpleados implements OnInit{
  
  constructor(
    private empleadosService: EmpleadoService
  ){}
  
  ngOnInit(): void {
    this.obtenerEmpleado();
  }

  empleados: any[] = [];

  obtenerEmpleado(){
      return this.empleadosService.obtenerEmpleados().subscribe(
        (repuesta: any) => {this.empleados = repuesta});
  }

}
