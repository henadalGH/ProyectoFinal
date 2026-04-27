import { Routes } from '@angular/router';
import { Principal } from './principal/principal';
import { HomeAdministrador } from './Administrados/Clientes/home-administrador/home-administrador';
import { HomeEmpleado } from './Empleados/home-empleado/home-empleado';
import { Header } from './header/header';
import { RecuperarContrasenia } from './recuperar-contrasenia/recuperar-contrasenia';
import { HeaderAdmin } from './Administrados/Clientes/header-admin/header-admin';
import { GestionCliente } from './Administrados/Clientes/gestion-cliente/gestion-cliente';
import { GestionarVehiculo } from './Administrados/Clientes/gestionar-vehiculo/gestionar-vehiculo';
import { RegistrarClintes } from './Administrados/Clientes/registrar-clintes/registrar-clintes';
import { RegistarVehiculo } from './Administrados/Clientes/registar-vehiculo/registar-vehiculo';
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
import { GestionarEmpleados } from './Administrados/Empleados/gestionar-empleados/gestionar-empleados';
import { RegistrarEmpleado } from './Administrados/Empleados/registrar-empleado/registrar-empleado';
import { VerEmpleado } from './Administrados/Empleados/ver-empleado/ver-empleado';
import { VerCliente } from './Administrados/Clientes/ver-cliente/ver-cliente';
import { Login } from './ComponentesPublico/login/login';
import { AuthGuard } from './AuthServicio/auth-guard';
import { HistorialVehiculo } from './Cliente/historial-vehiculo/historial-vehiculo';
import { VerMiVehucilo } from './Cliente/ver-mi-vehucilo/ver-mi-vehucilo';
import { AsignacionTurnos } from './Administrados/Clientes/GestionTurno/asignacion-turnos/asignacion-turnos';

export const routes: Routes = [
    // 🔓 RUTAS PÚBLICAS
    { path: 'login', component: Login },
    { path: 'inicio', component: Principal },
    { path: 'principal', component: Principal },
    { path: 'contra', component: RecuperarContrasenia },
    { path: '', redirectTo: '/inicio', pathMatch: 'full' },

    // 🔐 RUTAS PROTEGIDAS - ADMIN
    { path: 'homeAdmin', component: HomeAdministrador, canActivate: [AuthGuard], data: { role: ['ROLE_ADMIN'] } },
    { path: 'headerAdmin', component: HeaderAdmin, canActivate: [AuthGuard], data: { role: ['ROLE_ADMIN'] } },
    { path: 'gestionCliente', component: GestionCliente, canActivate: [AuthGuard], data: { role: ['ROLE_ADMIN'] } },
    { path: 'gestionarVehiculo', component: GestionarVehiculo, canActivate: [AuthGuard], data: { role: ['ROLE_ADMIN'] } },
    { path: 'registrarCliente', component: RegistrarClintes, canActivate: [AuthGuard], data: { role: ['ROLE_ADMIN'] } },
    { path: 'registrarVehiculo', component: RegistarVehiculo, canActivate: [AuthGuard], data: { role: ['ROLE_ADMIN', 'ROLE_CLIENTE'] } },
    { path: 'verCliente/:id', component: VerCliente, canActivate: [AuthGuard], data: { role: ['ROLE_ADMIN'] } },
    { path: 'gestionEmpleado', component: GestionarEmpleados, canActivate: [AuthGuard], data: { role: ['ROLE_ADMIN'] } },
    { path: 'registrarEmpleado', component: RegistrarEmpleado, canActivate: [AuthGuard], data: { role: ['ROLE_ADMIN'] } },
    { path: 'verEmpleado/:id', component: VerEmpleado, canActivate: [AuthGuard], data: { role: ['ROLE_ADMIN'] } },
    { path:'turnosAsignacion', component: AsignacionTurnos, canActivate: [AuthGuard], data: {role: ['ROLE_ADMIN']}},

    // 🔐 RUTAS PROTEGIDAS - CLIENTE
    { path: 'homeCliente', component: HomeCliente, canActivate: [AuthGuard], data: { role: ['ROLE_CLIENTE'] } },
    { path: 'solicitarTurno', component: SolicitarTurno, canActivate: [AuthGuard], data: { role: ['ROLE_CLIENTE'] } },
    { path: 'misVehiculos', component: MisVehiculos, canActivate: [AuthGuard], data: { role: ['ROLE_CLIENTE'] } },
    {path: 'miHistorial/:id', component: HistorialVehiculo, canActivate: [AuthGuard], data: {role: ['ROLE_CLIENTE']}},
    {path: 'verVehiculo/:id', component: VerMiVehucilo,canActivate: [AuthGuard], data: {role:['ROLE_CLIENTE']}},

    // 🔐 RUTAS PROTEGIDAS - EMPLEADO
    { path: 'homeEmpleado', component: HomeEmpleado, canActivate: [AuthGuard], data: { role: ['ROLE_EMPLEADO'] } },
    { path: 'inicioSocio', component: HomeEmpleado, canActivate: [AuthGuard], data: { role: ['ROLE_EMPLEADO'] } },
    { path: 'headerEmpleado', component: HeaderEmpleado, canActivate: [AuthGuard], data: { role: ['ROLE_EMPLEADO'] } },

    // 🔐 RUTAS PROTEGIDAS - FINANZAS
    { path: 'homeFinanzas', component: HomeFinanzas, canActivate: [AuthGuard], data: { role: ['ROLE_ADMIN', 'ROLE_FINANZAS'] } },
    { path: 'reportes', component: Reportes, canActivate: [AuthGuard], data: { role: ['ROLE_ADMIN', 'ROLE_FINANZAS'] } },
    { path: 'finanzas', component: MovimientosFinancieros, canActivate: [AuthGuard], data: { role: ['ROLE_ADMIN', 'ROLE_FINANZAS'] } },
    { path: 'presupuestos', component: Presupuesto, canActivate: [AuthGuard], data: { role: ['ROLE_ADMIN', 'ROLE_FINANZAS'] } },
    { path: 'headerFinanzas', component: HeaderFinanzas, canActivate: [AuthGuard], data: { role: ['ROLE_ADMIN', 'ROLE_FINANZAS'] } },
    { path: 'crearPresupuesto', component: CrearPresupuesto, canActivate: [AuthGuard], data: { role: ['ROLE_ADMIN', 'ROLE_FINANZAS'] } },

    // Ruta general
    { path: 'header', component: Header }
];
