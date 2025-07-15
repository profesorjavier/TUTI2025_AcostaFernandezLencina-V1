package com.tutiveinteveinteveinticinco.acostafernandezlencina.accesodatos;

import com.tutiveinteveinteveinticinco.acostafernandezlencina.entidades.Ingrediente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface IIngredienteRepo extends JpaRepository<Ingrediente, Long> {
    List<Ingrediente> findByActivoTrue();
    List<Ingrediente> findByNombreContainingIgnoreCase(String nombre);
}