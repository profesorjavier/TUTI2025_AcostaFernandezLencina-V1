package com.tutiveinteveinteveinticinco.acostafernandezlencina.accesodatos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tutiveinteveinteveinticinco.acostafernandezlencina.entidades.Receta;

public interface IRecetaRepo extends JpaRepository<Receta, Long> {
	
	
	// Solo falta agregar el método para calcular calorías totales
	@Query("SELECT r FROM Receta r WHERE r.activo = true AND LOWER(r.nombre) LIKE LOWER(concat('%', :nombre,'%')) AND (SELECT SUM(ir.caloriasPorPorcion) FROM r.ingredientes ir) BETWEEN :min AND :max")
	List<Receta> findByActivoTrueAndNombreContainingIgnoreCaseAndCaloriasTotalesBetween1(
	    @Param("nombre") String nombre, 
	    @Param("min") int caloriasMin, 
	    @Param("max") int caloriasMax);

    // Listar recetas activas (no eliminadas lógicamente)
    List<Receta> findByActivoTrue();

    // Buscar una receta por nombre exacto
    Optional<Receta> findByNombre(String nombre);

    // Buscar recetas activas que tengan calorías dentro de un rango
    List<Receta> findByActivoTrueAndIngredientes_CaloriasPorPorcionBetween(int desde, int hasta);
    // Buscar recetas activas por nombre que contengan cierto texto
    List<Receta> findByActivoTrueAndNombreContainingIgnoreCase(String nombreParcial);
 // En IRecetaRepo.java
    List<Receta> findByActivoTrueAndNombreContainingIgnoreCaseAndCaloriasTotalesBetween(
    	    String nombre, int caloriasMin, int caloriasMax);

	boolean existsByNombreAndActivoTrue(String nombre);

	Optional<Receta> findByIdAndActivoTrue(Long id);

	boolean existsByNombreAndActivoTrueAndIdNot(String nombre, Long idExcluir);

	List<Receta> findByNombreContainingIgnoreCaseAndCaloriasTotalesBetweenAndActivo(String nombre, Integer caloriasMin,
			Integer caloriasMax, boolean activo);
	
	
    }
