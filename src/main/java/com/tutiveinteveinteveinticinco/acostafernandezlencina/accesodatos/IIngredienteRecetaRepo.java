package com.tutiveinteveinteveinticinco.acostafernandezlencina.accesodatos;

import com.tutiveinteveinteveinticinco.acostafernandezlencina.entidades.IngredienteReceta;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IIngredienteRecetaRepo extends JpaRepository<IngredienteReceta, Long> {
	List<IngredienteReceta> findByIngredienteId(Long ingredienteId);

}