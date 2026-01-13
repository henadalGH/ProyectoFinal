package com.example.wmotorproBack.wmotorBack.Modelo.DTO;

import java.util.Date;

public class MovimientosFinDTO {
    
    private Long  id;
    private String tipoMovimiento;
    private String categoria;
    private String concepto;
    private Integer importe;
    private Date fechaRegistro;


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTipoMovimiento() {
        return tipoMovimiento;
    }
    public void setTipoMovimiento(String tipoMovimiento) {
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
    public Integer getImporte() {
        return importe;
    }
    public void setImporte(Integer importe) {
        this.importe = importe;
    }
    public Date getFechaRegistro() {
        return fechaRegistro;
    }
    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    
}
