package com.example.wmotorproBack.wmotorBack.Modelo.DTO;

public class ResponceDTO {
    
    private int numOfErrors;
    private String mensage;
    private Double total;

    public int getNumOfErrors() {
        return numOfErrors;
    }
    public void setNumOfErrors(int numOfErrors) {
        this.numOfErrors = numOfErrors;
    }
    public String getMensage() {
        return mensage;
    }
    public void setMensage(String mensage) {
        this.mensage = mensage;
    }
    public Double getTotal() {
        return total;
    }
    public void setTotal(Double total) {
        this.total = total;
    }
}
