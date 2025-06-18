package com.tutiveinteveinteveinticinco.acostafernandezlencina.accesodatos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tutiveinteveinteveinticinco.acostafernandezlencina.entidades.Familia;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para gestionar operaciones CRUD sobre la entidad Familia.
 * Extiende JpaRepository, por lo tanto ya tiene métodos como save(), findById(), findAll(), delete(), etc.
 */
@Repository
public interface FamiliaRepo extends JpaRepository<Familia, Long> {

    /**
     * Devuelve una lista con todas las familias activas (activo = true).
     */
    List<Familia> findByActivoTrue();

    /**
     * Devuelve una lista con todas las familias inactivas (activo = false).
     * Útil si querés mostrar las desactivadas para posiblemente reactivarlas.
     */
    List<Familia> findByActivoFalse();

    /**
     * Busca una familia por su número de familia.
     * @param nroFamilia El número de familia a buscar.
     * @return Optional con la familia encontrada o vacío si no existe.
     */
    Optional<Familia> findBynroFamilia(Integer nroFamilia);

    // NOTA: Ya tenemos findAll() por defecto para listar todas las familias.
}