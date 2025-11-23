import { Routes } from '@angular/router';
import { Principal } from './principal/principal';
import { Login } from './login/login';
import { HomeAdministrador } from './Administrados/home-administrador/home-administrador';
import { HomeEmpleado } from './Empleados/home-empleado/home-empleado';
import { Header } from './header/header';
import { RecuperarContrasenia } from './recuperar-contrasenia/recuperar-contrasenia';
import { HeaderAdmin } from './Administrados/header-admin/header-admin';
import { GestionCliente } from './Administrados/gestion-cliente/gestion-cliente';
import { GestionarVehiculo } from './Administrados/gestionar-vehiculo/gestionar-vehiculo';
import { RegistrarClintes } from './Administrados/registrar-clintes/registrar-clintes';
import { RegistarVehiculo } from './Administrados/registar-vehiculo/registar-vehiculo';
import { HomeFinanzas } from './Finanzas/home-finanzas/home-finanzas';
import { Reportes } from './Finanzas/reportes/reportes';
import { MovimientosFinancieros } from './Finanzas/movimientos-financieros/movimientos-financieros';
import { Presupuesto } from './Finanzas/presupuesto/presupuesto';
import { HeaderFinanzas } from './Finanzas/header-finanzas/header-finanzas';
import { CrearPresupuesto } from './utilidades/crear-presupuesto/crear-presupuesto';
import { HomeCliente } from './Cliente/home-cliente/home-cliente';
import { SolicitarTurno } from './Cliente/solicitar-turno/solicitar-turno';
import { MisVehiculos } from './Cliente/mis-vehiculos/mis-vehiculos';
import { HeaderEmpleado } from './Empleados/header-empleado/header-empleado';

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
    {path: "homeFinanzas", component: HomeFinanzas},
    {path:"reportes", component: Reportes},
    {path: "finanzas", component: MovimientosFinancieros},
    {path: "presupuestos", component: Presupuesto},
    {path: "headerFinanzas", component: HeaderFinanzas},
    {path: "crearPresupuesto", component: CrearPresupuesto},
    {path: "solicitarTurno", component: SolicitarTurno},
    {path: "misVehiculos", component: MisVehiculos},
    {path:"headerEmpleado", component: HeaderEmpleado}
];
