import { Routes } from '@angular/router';
import { Principal } from './principal/principal';
import { HomeEmpleado } from './Empleados/home-empleado/home-empleado';
import { Header } from './header/header';
import { RecuperarContrasenia } from './recuperar-contrasenia/recuperar-contrasenia';
import { GestionCliente } from './Administrados/Clientes/gestion-cliente/gestion-cliente';
import { GestionarVehiculo } from './Administrados/Clientes/gestionar-vehiculo/gestionar-vehiculo';
import { RegistrarClintes } from './Administrados/Clientes/registrar-clintes/registrar-clintes';
import { RegistarVehiculo } from './Administrados/Clientes/registar-vehiculo/registar-vehiculo';
import { HomeCliente } from './Cliente/home-cliente/home-cliente';
import { MisVehiculos } from './Cliente/mis-vehiculos/mis-vehiculos';
import { HeaderEmpleado } from './Empleados/header-empleado/header-empleado';
import { GestionarEmpleados } from './Administrados/Empleados/gestionar-empleados/gestionar-empleados';
import { RegistrarEmpleado } from './Administrados/Empleados/registrar-empleado/registrar-empleado';
import { VerEmpleado } from './Administrados/Empleados/ver-empleado/ver-empleado';
import { VerCliente } from './Administrados/Clientes/ver-cliente/ver-cliente';
import { Login } from './ComponentesPublico/login/login';
import { AuthGuard } from './AuthServicio/auth-guard';
import { Registro } from './ComponentesPublico/registro/registro';
import { InicioPresupuestos } from './Administrados/Presupuestos/inicio-presupuestos/inicio-presupuestos';
import { AsignacionTurnos } from './Administrados/Turnos/asignacion-turnos/asignacion-turnos';
import { HomeAdmin } from './Administrados/Adminstrador/home-admin/home-admin';
import { HeaderAdmin } from './Administrados/Adminstrador/header-admin/header-admin';
import { DetalleVehiculo } from './Cliente/Vehiculo/detalle-vehiculo/detalle-vehiculo';
import { MiHistorial } from './Cliente/Vehiculo/mi-historial/mi-historial';
import { MisTurnos } from './Cliente/Turnos/mis-turnos/mis-turnos';
import { RegistrarFinanzas } from './Administrados/Finanzas/registrar-finanzas/registrar-finanzas';
import { HomeFinanzas } from './Administrados/Finanzas/home-finanzas/home-finanzas';

export const routes: Routes = [ 
    // 🔓 RUTAS PÚBLICAS
    { path: 'login', component: Login },
    { path: 'inicio', component: Principal },
    {path:'registro', component: Registro},
    { path: 'principal', component: Principal },
    { path: 'contra', component: RecuperarContrasenia },
    { path: '', redirectTo: '/inicio', pathMatch: 'full' },

    // 🔐 RUTAS PROTEGIDAS - ADMIN
    { path: 'homeAdmin', component: HomeAdmin, canActivate: [AuthGuard], data: { role: ['ROLE_ADMIN'] } },
    { path: 'headerAdmin', component: HeaderAdmin, canActivate: [AuthGuard], data: { role: ['ROLE_ADMIN'] } },
    { path: 'gestionCliente', component: GestionCliente, canActivate: [AuthGuard], data: { role: ['ROLE_ADMIN'] } },
    { path: 'gestionarVehiculo', component: GestionarVehiculo, canActivate: [AuthGuard], data: { role: ['ROLE_ADMIN'] } },
    { path: 'registrarCliente', component: RegistrarClintes},
    { path: 'registrarVehiculo', component: RegistarVehiculo, canActivate: [AuthGuard], data: { role: ['ROLE_ADMIN', 'ROLE_CLIENTE'] } },
    { path: 'verCliente/:id', component: VerCliente, canActivate: [AuthGuard], data: { role: ['ROLE_ADMIN'] } },
    { path: 'gestionEmpleado', component: GestionarEmpleados, canActivate: [AuthGuard], data: { role: ['ROLE_ADMIN'] } },
    { path: 'registrarEmpleado', component: RegistrarEmpleado, canActivate: [AuthGuard], data: { role: ['ROLE_ADMIN'] } },
    { path: 'verEmpleado/:id', component: VerEmpleado, canActivate: [AuthGuard], data: { role: ['ROLE_ADMIN'] } },
    { path:'turnosAsignacion', component: AsignacionTurnos, canActivate: [AuthGuard], data: {role: ['ROLE_ADMIN']}},
    {path:'inicioPresupuesto' , component: InicioPresupuestos},

    // 🔐 RUTAS PROTEGIDAS  - CLIENTE
    { path: 'homeCliente', component: HomeCliente, canActivate: [AuthGuard], data: { role: ['ROLE_CLIENTE'] } },
    { path: 'misVehiculos', component: MisVehiculos, canActivate: [AuthGuard], data: { role: ['ROLE_CLIENTE'] } },
    {path: 'miHistorial/:id', component: MiHistorial, canActivate: [AuthGuard], data: {role: ['ROLE_CLIENTE']}},
    {path: "detalleVehiculo/:id", component: DetalleVehiculo},
    {path: "misTurnos", component: MisTurnos},
    

    // 🔐 RUTAS PROTEGIDAS - EMPLEADO
    { path: 'homeEmpleado', component: HomeEmpleado, canActivate: [AuthGuard], data: { role: ['ROLE_EMPLEADO'] } },
    { path: 'inicioSocio', component: HomeEmpleado, canActivate: [AuthGuard], data: { role: ['ROLE_EMPLEADO'] } },
    { path: 'headerEmpleado', component: HeaderEmpleado, canActivate: [AuthGuard], data: { role: ['ROLE_EMPLEADO'] } },

    

    // Ruta general
    { path: 'homeFinanzas', component: HomeFinanzas},
    {path: 'registraMovimiento', component: RegistrarFinanzas}
];
