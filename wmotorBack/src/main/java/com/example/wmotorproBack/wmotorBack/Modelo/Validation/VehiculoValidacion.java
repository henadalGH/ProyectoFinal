package com.example.wmotorproBack.wmotorBack.Modelo.Validation;

import org.springframework.stereotype.Component;

import com.example.wmotorproBack.wmotorBack.Modelo.DTO.ResponceDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.VehiculoDTO;

@Component
public class VehiculoValidacion { 

    public ResponceDTO validacionVehiculo(VehiculoDTO vehiculo) {

        ResponceDTO responce = new ResponceDTO();
        responce.setNumOfErrors(0);
        responce.setMensage("");

        // ===== MARCA =====
        if (vehiculo.getMarca() == null || vehiculo.getMarca().trim().length() < 3
                || vehiculo.getMarca().trim().length() > 50) {

            responce.setNumOfErrors(responce.getNumOfErrors() + 1);
            responce.setMensage(responce.getMensage()
                    + "La marca del vehículo no puede ser nula y debe tener entre 3 y 50 caracteres. ");
        }

        // ===== MODELO =====
        if (vehiculo.getModelo() == null || vehiculo.getModelo().trim().length() < 3
                || vehiculo.getModelo().trim().length() > 50) {

            responce.setNumOfErrors(responce.getNumOfErrors() + 1);
            responce.setMensage(responce.getMensage()
                    + "El modelo no puede ser nulo y debe tener entre 3 y 50 caracteres. ");
        }

        // ===== AÑO (int) =====
        int anio = vehiculo.getAnio();
        int anioActual = java.time.Year.now().getValue();

        if (anio < 1900 || anio > anioActual + 1) {

            responce.setNumOfErrors(responce.getNumOfErrors() + 1);
            responce.setMensage(responce.getMensage()
                    + "El año debe ser mayor o igual a 1900 y menor o igual al año actual + 1. ");
        }

        // ===== PATENTE =====
        String patente = vehiculo.getPatente();

        if (patente == null || patente.isBlank()) {

            responce.setNumOfErrors(responce.getNumOfErrors() + 1);
            responce.setMensage(responce.getMensage()
                    + "La patente es obligatoria. ");

        } else {

            patente = patente.toUpperCase().trim();
            patente = patente.replaceAll("[^A-Z0-9]", "");
            vehiculo.setPatente(patente);

            if (anio < 2016) {
                String formatoViejo = "^[A-Z]{3}[0-9]{3}$";

                if (!patente.matches(formatoViejo)) {
                    responce.setNumOfErrors(responce.getNumOfErrors() + 1);
                    responce.setMensage(responce.getMensage()
                            + "Para vehículos anteriores a 2016 la patente debe ser AAA123. ");
                }

            } else {
                String formatoNuevo = "^[A-Z]{2}[0-9]{3}[A-Z]{2}$";

                if (!patente.matches(formatoNuevo)) {
                    responce.setNumOfErrors(responce.getNumOfErrors() + 1);
                    responce.setMensage(responce.getMensage()
                            + "Para vehículos desde 2016 la patente debe ser AA123AA. ");
                }
            }
        }

        // ===== KILOMETRAJE (int) =====
        int km = vehiculo.getKilometraje();

        if (km < 0) {
            responce.setNumOfErrors(responce.getNumOfErrors() + 1);
            responce.setMensage(responce.getMensage()
                    + "El kilometraje no puede ser menor a 0. ");
        }

        return responce;
    }
}
