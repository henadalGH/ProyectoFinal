import { Component, OnInit } from '@angular/core';
import { EmpleadoService } from '../../../Servicio/empleado-service';
import { HeaderAdmin } from "../../Clientes/header-admin/header-admin";
import { Router, RouterLink } from "@angular/router";

@Component({
  selector: 'app-gestionar-empleados',
  imports: [HeaderAdmin, RouterLink],
  templateUrl: './gestionar-empleados.html',
  styleUrl: './gestionar-empleados.css',
})
export class GestionarEmpleados implements OnInit{
  constructor(
    private empleadosService: EmpleadoService,
    private router: Router
  ){}
  
  ngOnInit(): void {
    this.obtenerEmpleado();
  }

  empleados: any[] = [];

  obtenerEmpleado(){
      return this.empleadosService.obtenerEmpleados().subscribe(
        (repuesta: any) => {this.empleados = repuesta});
  }

  verEmpleado(id: number) {
    this.router.navigate(['/verEmpleado',id]);
  }

}
