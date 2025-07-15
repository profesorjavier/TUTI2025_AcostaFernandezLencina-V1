package com.tutiveinteveinteveinticinco.acostafernandezlencina.accesodatos;

import com.tutiveinteveinteveinticinco.acostafernandezlencina.entidades.Preparacion;
import com.tutiveinteveinteveinticinco.acostafernandezlencina.entidades.Receta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


import com.tutiveinteveinteveinticinco.acostafernandezlencina.entidades.Preparacion;
import com.tutiveinteveinteveinticinco.acostafernandezlencina.entidades.Receta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IPreparacionRepo extends JpaRepository<Preparacion, Long> {

	@Query("SELECT p FROM Preparacion p JOIN FETCH p.receta WHERE p.activo = true")
    List<Preparacion> findActivasWithReceta();
    
    @Query("SELECT p FROM Preparacion p JOIN FETCH p.receta WHERE p.activo = true AND p.fecha BETWEEN :desde AND :hasta")
    List<Preparacion> findActivasByFechaWithReceta(@Param("desde") LocalDate desde, @Param("hasta") LocalDate hasta);
	
   
    
    
	
	List<Preparacion> findByActivoTrue();

    Optional<Preparacion> findByFechaAndRecetaAndActivoTrue(LocalDate fecha, Receta receta);

    List<Preparacion> findByFechaBetweenAndActivoTrue(LocalDate desde, LocalDate hasta);

    List<Preparacion> findByReceta_IdAndActivoTrue(Long recetaId);


	
	
	
	



}
