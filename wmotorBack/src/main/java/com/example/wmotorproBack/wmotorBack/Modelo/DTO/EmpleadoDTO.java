package com.example.wmotorproBack.wmotorBack.Modelo.DTO;

import java.time.LocalDate;

import com.example.wmotorproBack.wmotorBack.Modelo.Enums.CargosEnum;

public class EmpleadoDTO {

    
    private String nombre;
    private String apellido;
    private String email;
    private String contacto;
    private String dni;
    private LocalDate fechaIngreso;
    private LocalDate fechaNacimiento;
    private CargosEnum cargo;
    private Double sueldo;
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getApellido() {
        return apellido;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getContacto() {
        return contacto;
    }
    public void setContacto(String contacto) {
        this.contacto = contacto;
    }
    public String getDni() {
        return dni;
    }
    public void setDni(String dni) {
        this.dni = dni;
    }
    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }
    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }
    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }
    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
    public CargosEnum getCargo() {
        return cargo;
    }
    public void setCargo(CargosEnum cargo) {
        this.cargo = cargo;
    }
    public Double getSueldo() {
        return sueldo;
    }
    public void setSueldo(Double sueldo) {
        this.sueldo = sueldo;
    }

    
    

}
