package com.example.wmotorproBack.wmotorBack.Modelo.DTO;

import java.util.Date;

import com.example.wmotorproBack.wmotorBack.Modelo.Enums.CargosEnum;

public class EmpleadoDTO {

    
    private String nombre;
    private String apellodo;
    private String email;
    private String contacto;
    private String dni;
    private Date fechaIngreso;
    private Date fechaNacimiento;
    private CargosEnum cargo;
    private Double sueldo;

    
    public Double getSueldo() {
        return sueldo;
    }
    public void setSueldo(Double sueldo) {
        this.sueldo = sueldo;
    }
    public CargosEnum getCargo() {
        return cargo;
    }
    public void setCargo(CargosEnum cargo) {
        this.cargo = cargo;
    }
    
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getApellodo() {
        return apellodo;
    }
    public void setApellodo(String apellodo) {
        this.apellodo = apellodo;
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
    public Date getFechaIngreso() {
        return fechaIngreso;
    }
    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }
    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }
    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
    
    

}
