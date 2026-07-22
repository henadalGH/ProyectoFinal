import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { EmpleadoService } from '../../../Servicio/empleado-service';
import { HeaderAdmin } from '../../Adminstrador/header-admin/header-admin';
import { Header } from '../../../header/header';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-ver-empleado',
  templateUrl: './ver-empleado.html',
  styleUrl: './ver-empleado.css',
  imports: [HeaderAdmin, RouterLink, Header, FormsModule],
})
export class VerEmpleado implements OnInit {

  id!: number;
  empleado: any;

  editarNombre = false;
  editarApellido = false;
  editarEmail = false;
  editarContacto = false;
  editarDni = false;
  editarFechaNacimiento = false;
  editarFechaIngreso = false;
  editarCargo = false;
  editarSueldo = false;

  fechaMaxima!: string;
  fechaNacimientoOriginal!: string;

  aumentoSueldo: number = 0;


  constructor(
    private route: ActivatedRoute,
    private empleadoService: EmpleadoService
  ) {}


  ngOnInit(): void {

    this.id = Number(this.route.snapshot.paramMap.get('id'));


    const hoy = new Date();

    hoy.setFullYear(hoy.getFullYear() - 18);

    this.fechaMaxima = hoy.toISOString().split('T')[0];


    this.cargarEmpleado();
  }



  cargarEmpleado(): void {

    this.empleadoService.verEmpleado(this.id).subscribe({

      next: (data: any) => {

        this.empleado = data;


        const sueldoGuardado = localStorage.getItem(
          'sueldoEmpleado_' + this.empleado.id
        );


        if (sueldoGuardado) {

          this.empleado.sueldo = Number(sueldoGuardado);

        }

      },


      error: (error) => {

        console.error(error);

      }

    });

  }



  iniciarEdicionFechaNacimiento(): void {

    this.fechaNacimientoOriginal = this.empleado.fechaNacimiento;

    this.editarFechaNacimiento = true;

  }



  modificarFechaNacimiento(idEmpleado: number): void {


    if (!this.validarEdad()) {


      alert('El empleado debe ser mayor de 18 años');


      this.empleado.fechaNacimiento = this.fechaNacimientoOriginal;

      this.editarFechaNacimiento = false;


      return;

    }



    if (!confirm('¿Guardar cambios de la fecha de nacimiento?')) {


      this.empleado.fechaNacimiento = this.fechaNacimientoOriginal;

      this.editarFechaNacimiento = false;


      return;

    }



    this.empleadoService.modificarEmpleado(

      idEmpleado,
      undefined,
      undefined,
      undefined,
      undefined,
      undefined,
      undefined,
      this.empleado.fechaNacimiento

    ).subscribe({

      next: () => {

        this.editarFechaNacimiento = false;

        this.cargarEmpleado();

      },


      error: (error) => {

        console.error(error);

        alert('Error al modificar la fecha de nacimiento');

      }

    });

  }




  validarEdad(): boolean {


    if (!this.empleado?.fechaNacimiento) {

      return false;

    }



    const nacimiento = new Date(this.empleado.fechaNacimiento);



    if (isNaN(nacimiento.getTime())) {

      return false;

    }



    const hoy = new Date();



    let edad = hoy.getFullYear() - nacimiento.getFullYear();



    const diferenciaMes =
      hoy.getMonth() - nacimiento.getMonth();



    if (
      diferenciaMes < 0 ||
      (diferenciaMes === 0 &&
      hoy.getDate() < nacimiento.getDate())
    ) {

      edad--;

    }



    return edad >= 18;

  }





  aumentarSueldo(): void {


    if (this.aumentoSueldo <= 0) {

      alert('Ingrese un monto válido.');

      return;

    }


    if (!confirm('¿Está seguro de aumentar el sueldo?')) {

      return;

    }


    this.empleado.sueldo += this.aumentoSueldo;


    localStorage.setItem(
      'sueldoEmpleado_' + this.empleado.id,
      this.empleado.sueldo.toString()
    );


    this.aumentoSueldo = 0;


    alert('Sueldo actualizado correctamente.');

  }





  restablecerSueldo(): void {


    if (!confirm('¿Desea restablecer el sueldo original?')) {

      return;

    }


    localStorage.removeItem(
      'sueldoEmpleado_' + this.empleado.id
    );


    this.cargarEmpleado();

  }




  modificarNombre(idEmpleado:number):void{

    if(!confirm('¿Guardar cambios del nombre?')) return;


    this.empleadoService.modificarEmpleado(
      idEmpleado,
      undefined,
      undefined,
      undefined,
      this.empleado.nombre

    ).subscribe({

      next:()=>{

        this.editarNombre=false;

        this.cargarEmpleado();

      }

    });

  }




  modificarApellido(idEmpleado:number):void{

    if(!confirm('¿Guardar cambios del apellido?')) return;


    this.empleadoService.modificarEmpleado(
      idEmpleado,
      undefined,
      undefined,
      undefined,
      undefined,
      this.empleado.apellido

    ).subscribe({

      next:()=>{

        this.editarApellido=false;

        this.cargarEmpleado();

      }

    });

  }





  modificarEmail(idEmpleado:number):void{


    if(!confirm('¿Guardar cambios del email?')) return;


    this.empleadoService.modificarEmpleado(
      idEmpleado,
      undefined,
      this.empleado.email

    ).subscribe({

      next:()=>{

        this.editarEmail=false;

        this.cargarEmpleado();

      }

    });

  }





  modificarContacto(idEmpleado:number):void{


    if(!confirm('¿Guardar cambios del contacto?')) return;


    this.empleadoService.modificarEmpleado(
      idEmpleado,
      undefined,
      undefined,
      this.empleado.contacto

    ).subscribe({

      next:()=>{

        this.editarContacto=false;

        this.cargarEmpleado();

      }

    });

  }





  modificarDni(idEmpleado:number):void{


    if(!confirm('¿Guardar cambios del DNI?')) return;


    this.empleadoService.modificarEmpleado(
      idEmpleado,
      undefined,
      undefined,
      undefined,
      undefined,
      undefined,
      this.empleado.dni

    ).subscribe({

      next:()=>{

        this.editarDni=false;

        this.cargarEmpleado();

      }

    });

  }





  modificarFechaIngreso(idEmpleado:number):void{


    if(!confirm('¿Guardar cambios de la fecha de ingreso?')) return;


    this.empleadoService.modificarEmpleado(
      idEmpleado,
      undefined,
      undefined,
      undefined,
      undefined,
      undefined,
      undefined,
      undefined,
      this.empleado.fechaIngreso

    ).subscribe({

      next:()=>{

        this.editarFechaIngreso=false;

        this.cargarEmpleado();

      }

    });

  }





  modificarCargo(idEmpleado:number):void{


    if(!confirm('¿Guardar cambios del cargo?')) return;


    this.empleadoService.modificarEmpleado(
      idEmpleado,
      this.empleado.cargo

    ).subscribe({

      next:()=>{

        this.editarCargo=false;

        this.cargarEmpleado();

      }

    });

  }





  cancelarEdicion():void{


    this.editarNombre=false;
    this.editarApellido=false;
    this.editarEmail=false;
    this.editarContacto=false;
    this.editarDni=false;
    this.editarFechaNacimiento=false;
    this.editarFechaIngreso=false;
    this.editarCargo=false;


    this.cargarEmpleado();

  }

}