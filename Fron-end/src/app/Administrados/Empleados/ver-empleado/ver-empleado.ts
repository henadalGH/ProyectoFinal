import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { EmpleadoService } from '../../../Servicio/empleado-service';
import { HeaderAdmin } from "../../Clientes/header-admin/header-admin";

@Component({
  selector: 'app-ver-empleado',
  templateUrl: './ver-empleado.html',
  styleUrl: './ver-empleado.css',
  imports: [HeaderAdmin, RouterLink],
})
export class VerEmpleado implements OnInit {

  id!: number;
  empleado: any;

  constructor(
    private route: ActivatedRoute,
    private empleadoService: EmpleadoService
  ) {}

  ngOnInit(): void {
    this.id = Number(this.route.snapshot.paramMap.get('id'));

    this.empleadoService.verEmpleado(this.id)
      .subscribe(data => {
        this.empleado = data;
      });
  }
}
