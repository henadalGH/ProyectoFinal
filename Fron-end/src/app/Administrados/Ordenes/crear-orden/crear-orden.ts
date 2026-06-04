import { Component, OnInit } from '@angular/core';
import { HeaderAdmin } from "../../Adminstrador/header-admin/header-admin";
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../../../AuthServicio/auth-service';
import { TurnosService } from '../../../Servicio/turnos-service';

@Component({
  selector: 'app-crear-orden',
  imports: [HeaderAdmin],
  templateUrl: './crear-orden.html',
  styleUrl: './crear-orden.css',
})
export class CrearOrden implements OnInit{
  
  constructor(private router: Router,
    private auth: AuthService,
    private actRouter: ActivatedRoute,
    private turnoServicio: TurnosService
  ){}
  
  
  //Definiciones de variablear
  id!: number;
  turno!: any;


  ngOnInit(): void {
    
    this.id = Number (this.actRouter.snapshot.paramMap.get('id'));

    this.turnoServicio.obtenerTurnoPorId(this.id).subscribe(
      (repuesta : any) => {
        this.turno  = repuesta;
      }
    )
  }


  

}
