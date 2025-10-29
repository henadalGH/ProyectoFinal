package com.example.wMotorPro.WMotorPro.modelo.dto;


public class GuardarUsuario {
    private String nombre;
    private String apellido;
    private String email;
    private String contrasenia;
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }
    public void setContacto(String contacto) {
        this.contacto = contacto;
    }
    public String getNombre() {
        return nombre;
    }
    public String getApellido() {
        return apellido;
    }
    public String getEmail() {
        return email;
    }
    public String getContrasenia() {
        return contrasenia;
    }
    public String getContacto() {
        return contacto;
    }
    private String contacto;
}
