import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../../AuthServicio/auth-service';
import { TurnosService } from '../../../Servicio/turnos-service';
import { Router } from '@angular/router';
import { Headercliente } from "../../headercliente/headercliente";
import { Header } from "../../../header/header";


@Component({
  selector: 'app-mis-turnos',
  imports: [Headercliente, Header],
  templateUrl: './mis-turnos.html',
  styleUrl: './mis-turnos.css',
})
export class MisTurnos implements OnInit {
  
  constructor(
    private turnoService: TurnosService,
    private authService: AuthService,
    private router: Router
  ) {}

  pendiente: any[] = [];
  
  ngOnInit(): void {
    // Cargamos al iniciar el componente
    this.cargarTurnosPendientes();
  }

  // Separamos la lógica para poder reutilizarla
  cargarTurnosPendientes(): void {
    const idCliente = this.authService.getEntityId();

    if (idCliente) {
      this.turnoService.obtenerTurnosPendientePorCliente(idCliente).subscribe(
        (respuesta: any) => {
          this.pendiente = respuesta;
          console.log(this.pendiente.length, idCliente);
        },
        (error) => console.error('Error al cargar turnos', error)
      );
    }
  }

  logout(): void {
    this.authService.logout();
  }

  confirmarTurno(idTurno: number) {
    const estado = "CONFIRMADO";

    this.turnoService.actualizarEstadoTurno(idTurno, estado).subscribe(
      (next: any) => {
        // Volvemos a traer la lista limpia desde el servidor
        this.cargarTurnosPendientes();
      },
      (error) => console.error('Error al actualizar', error)
    );
  }

  cancelarTurno(idTurno: number) {
    const estado = "CANCELADO";

    this.turnoService.actualizarEstadoTurno(idTurno, estado).subscribe(
      (next: any) => {
        // Volvemos a traer la lista limpia desde el servidor
        this.cargarTurnosPendientes();
      },
      (error) => console.error('Error al actualizar', error)
    );
  }
}