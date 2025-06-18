package com.tutiveinteveinteveinticinco.acostafernandezlencina.accesodatos;

import com.tutiveinteveinteveinticinco.acostafernandezlencina.entidades.Familia;
import com.tutiveinteveinteveinticinco.acostafernandezlencina.entidades.Integrante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IntegranteRepo extends JpaRepository<Integrante, Long> {

    // Busca un integrante por DNI dentro de una familia
    Optional<Integrante> findByDniAndFamilia(Integer dni, Familia familia);

    // Verifica si ya existe un integrante con ese DNI en una familia
    boolean existsByDniAndFamilia(Integer dni, Familia familia);

    // Obtiene todos los integrantes activos de una familia
    List<Integrante> findByFamiliaAndActivoTrue(Familia familia);

    // Obtiene todos los integrantes inactivos de una familia
    List<Integrante> findByFamiliaAndActivoFalse(Familia familia);

    // Lista todos los integrantes de una familia (activos + inactivos)
    List<Integrante> findByFamilia(Familia familia);
}
