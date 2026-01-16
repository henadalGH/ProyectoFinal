package com.example.wmotorproBack.wmotorBack.Modelo.DTO;

import java.util.Date;

public class MovimientoDTO {
    private Long id;
    private MovimientoDTO tipoMovimiento;
    private String categoria;
    private String concepto;
    private Double importe;
    public Double getImporte() {
        return importe;
    }
    public void setImporte(Double importe) {
        this.importe = importe;
    }
    private Date fechaRegistro;

    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public MovimientoDTO getTipoMovimiento() {
        return tipoMovimiento;
    }
    public void setTipoMovimiento(MovimientoDTO tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }
    public String getCategoria() {
        return categoria;
    }
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    public String getConcepto() {
        return concepto;
    }
    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }
   
    public Date getFechaRegistro() {
        return fechaRegistro;
    }
    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    


}
