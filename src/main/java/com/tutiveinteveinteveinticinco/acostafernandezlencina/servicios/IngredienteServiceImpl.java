package com.tutiveinteveinteveinticinco.acostafernandezlencina.servicios;

import com.tutiveinteveinteveinticinco.acostafernandezlencina.accesodatos.IIngredienteRepo;
import com.tutiveinteveinteveinticinco.acostafernandezlencina.entidades.Ingrediente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class IngredienteServiceImpl implements IngredienteService {

    @Autowired
    private IIngredienteRepo ingredienteRepo;

    @Override
    public List<Ingrediente> listarActivos() {
        return ingredienteRepo.findByActivoTrue();
    }

    @Override
    public Ingrediente obtenerPorId(Long id) {
        return ingredienteRepo.findById(id).orElse(null);
    }
}