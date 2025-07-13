package com.tutiveinteveinteveinticinco.acostafernandezlencina.servicios;

import com.tutiveinteveinteveinticinco.acostafernandezlencina.entidades.Ingrediente;
import java.util.List;

public interface IngredienteService {
    List<Ingrediente> listarActivos();
    Ingrediente obtenerPorId(Long id);
    // Agregar otros métodos según necesites
}