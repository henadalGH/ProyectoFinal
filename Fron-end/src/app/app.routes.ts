import { Routes } from '@angular/router';
import { Principal } from './principal/principal';
import { Login } from './login/login';
import { HomeAdministrador } from './Administrados/Clientes/home-administrador/home-administrador';
import { HomeEmpleado } from './Empleados/home-empleado/home-empleado';
import { Header } from './header/header';
import { RecuperarContrasenia } from './recuperar-contrasenia/recuperar-contrasenia';
import { HeaderAdmin } from './Administrados/Clientes/header-admin/header-admin';
import { GestionCliente } from './Administrados/Clientes/gestion-cliente/gestion-cliente';
import { GestionarVehiculo } from './Administrados/Clientes/gestionar-vehiculo/gestionar-vehiculo';
import { RegistrarClintes } from './Administrados/Clientes/registrar-clintes/registrar-clintes';
import { RegistarVehiculo } from './Administrados/Clientes/registar-vehiculo/registar-vehiculo';
import { CrearPresupuesto } from './utilidades/crear-presupuesto/crear-presupuesto';
import { HomeCliente } from './Cliente/home-cliente/home-cliente';
import { SolicitarTurno } from './Cliente/solicitar-turno/solicitar-turno';
import { MisVehiculos } from './Cliente/mis-vehiculos/mis-vehiculos';
import { HeaderEmpleado } from './Empleados/header-empleado/header-empleado';

import { GestionarEmpleados } from './Administrados/Empleados/gestionar-empleados/gestionar-empleados';
import { RegistrarEmpleado } from './Administrados/Empleados/registrar-empleado/registrar-empleado';
import { VerEmpleado } from './Administrados/Empleados/ver-empleado/ver-empleado';
import { VerCliente } from './Administrados/Clientes/ver-cliente/ver-cliente';

export const routes: Routes = [

    {path: "principal", component: Principal},
    {path: "login", component: Login},
    {path: "homeAdmin", component: HomeAdministrador},
    {path: "homeCliente", component: HomeCliente},
    {path: "homeEmpleado", component: HomeEmpleado},
    {path: "header", component: Header},
    {path:'', redirectTo: '/principal', pathMatch: 'full'},
    {path: "contra", component: RecuperarContrasenia},
    {path:"headerAdmin", component: HeaderAdmin},
    {path:"gestionCliente", component: GestionCliente},
    {path: "gestionarVehiculo", component: GestionarVehiculo},
    {path: "registrarCliente", component: RegistrarClintes},
    {path: "registrarVehiculo", component:  RegistarVehiculo},
    {path: "crearPresupuesto", component: CrearPresupuesto},
    {path: "solicitarTurno", component: SolicitarTurno},
    {path: "misVehiculos", component: MisVehiculos},
    {path:"headerEmpleado", component: HeaderEmpleado},
    {path:"gestionEmpleado", component: GestionarEmpleados},
    {path:"registrarEmpleado", component: RegistrarEmpleado}, 
    {path:"verEmpleado/:id", component:VerEmpleado},
    {path:"verCliente/:id", component: VerCliente}
];
