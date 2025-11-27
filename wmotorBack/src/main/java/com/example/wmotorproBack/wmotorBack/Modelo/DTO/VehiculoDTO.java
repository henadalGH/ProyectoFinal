package com.example.wmotorproBack.wmotorBack.Modelo.DTO;

public class VehiculoDTO {

    private String marca;
    private String modelo;
    private String anio;
    private String patente;
    
    public String getMarca() {
        return marca;
    }
    public void setMarca(String marca) {
        this.marca = marca;
    }
    public String getModelo() {
        return modelo;
    }
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
    public String getAnio() {
        return anio;
    }
    public void setAnio(String anio) {
        this.anio = anio;
    }
    public String getPatente() {
        return patente;
    }
    public void setPatente(String patente) {
        this.patente = patente;
    }
    public int getKilometraje() {
        return kilometraje;
    }
    public void setKilometraje(int kilometraje) {
        this.kilometraje = kilometraje;
    }
    public String getNumero_chasis() {
        return numero_chasis;
    }
    public void setNumero_chasis(String numero_chasis) {
        this.numero_chasis = numero_chasis;
    }
    private int kilometraje;
    private String numero_chasis; 
}
