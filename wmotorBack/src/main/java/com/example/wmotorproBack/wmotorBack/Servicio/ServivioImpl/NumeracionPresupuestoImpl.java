package com.example.wmotorproBack.wmotorBack.Servicio.ServivioImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.wmotorproBack.wmotorBack.Modelo.Entity.NumeroPresupuestoEntity;
import com.example.wmotorproBack.wmotorBack.Repository.NumeracionPresupuestoReposistory;
import com.example.wmotorproBack.wmotorBack.Servicio.NumeracionPresupuestoService;

@Service
public class NumeracionPresupuestoImpl implements NumeracionPresupuestoService{


    @Autowired
    private NumeracionPresupuestoReposistory numeracionPresupuestoReposistory;
    

    @Override
    public Long generarNumero() {

        NumeroPresupuestoEntity numero = numeracionPresupuestoReposistory.findById("PRESUPUESTO").orElseThrow();

        Long nuevoNumero = numero.getValor() + 1;

        numeracionPresupuestoReposistory.save(numero);

        return nuevoNumero;

    }

}
