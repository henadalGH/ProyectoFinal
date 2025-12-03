package com.example.wmotorproBack.wmotorBack.Modelo.DTO;

import java.util.Date;

import com.example.wmotorproBack.wmotorBack.Modelo.Enums.RolesEnum;

public class RegistroDTO {
    
    // Datos comunes del usuario
    private String nombre;
    private String apellido;
    private String email;
    private String password;
    private String contacto;
    private RolesEnum roles;

    // Cliente
    private String direccion;

    // Empleado
    private Date fechaNacimieto;
    private String dni;
    private Date fechaIngreso;

    // GETTERS Y SETTERS
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getContacto() { return contacto; }
    public void setContacto(String contacto) { this.contacto = contacto; }

    public RolesEnum getRoles() { return roles; }
    public void setRoles(RolesEnum roles) { this.roles = roles; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public Date getFechaNacimieto() { return fechaNacimieto; }
    public void setFechaNacimieto(Date fechaNacimieto) { this.fechaNacimieto = fechaNacimieto; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public Date getFechaIngreso() { return fechaIngreso; }
    public void setFechaIngreso(Date fechaIngreso) { this.fechaIngreso = fechaIngreso; }
}
