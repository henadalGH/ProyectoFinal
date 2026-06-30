import { Component, OnInit } from '@angular/core';
import { EmpleadoService } from '../../../Servicio/empleado-service';
import { Router, RouterLink } from "@angular/router";
import { HeaderAdmin } from '../../Adminstrador/header-admin/header-admin';
import { Header } from "../../../header/header";

@Component({
  selector: 'app-gestionar-empleados',
  imports: [HeaderAdmin, RouterLink, Header],
  templateUrl: './gestionar-empleados.html',
  styleUrl: './gestionar-empleados.css',
})
export class GestionarEmpleados implements OnInit {
  empleados: any[] = [];
  error: boolean = false; // Nueva variable de estado

  constructor(
    private empleadosService: EmpleadoService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.obtenerEmpleado();
  }

  obtenerEmpleado() {
    this.empleadosService.obtenerEmpleados().subscribe({
      next: (respuesta: any) => {
        this.empleados = respuesta;
        this.error = false;
      },
      error: (err) => {
        console.error('Error al cargar empleados', err);
        this.error = true; // Activamos el estado de error
      }
    });
  }

  verEmpleado(id: number) {
    this.router.navigate(['/verEmpleado', id]);
  }
}
