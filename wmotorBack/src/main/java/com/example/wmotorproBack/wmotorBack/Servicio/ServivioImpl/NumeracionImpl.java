package com.example.wmotorproBack.wmotorBack.Servicio.ServivioImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.wmotorproBack.wmotorBack.Modelo.Entity.NumeracionEntity;
import com.example.wmotorproBack.wmotorBack.Repository.NumeracionReposistory;
import com.example.wmotorproBack.wmotorBack.Servicio.NumeracionService;

@Service
public class NumeracionImpl implements NumeracionService{


    @Autowired
    private NumeracionReposistory numeracionReposistory;
    
    @Override
    public Long generarNumero() {

        NumeracionEntity numero = numeracionReposistory.findById("PRESUPUESTO").orElseThrow();

        Long nuevoNumero = numero.getValor() + 1;

         numero.setValor(nuevoNumero);

        numeracionReposistory.save(numero);

        return nuevoNumero;

    }

    @Override
    public Long generarNumeroOrden() {
        
         NumeracionEntity numero = numeracionReposistory.findById("ORDEN").orElseThrow();

        Long nuevoNumero = numero.getValor() + 1;

         numero.setValor(nuevoNumero);

        numeracionReposistory.save(numero);

        return nuevoNumero;

    }

    

}
