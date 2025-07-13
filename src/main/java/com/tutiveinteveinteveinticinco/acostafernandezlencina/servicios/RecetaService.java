package com.tutiveinteveinteveinticinco.acostafernandezlencina.servicios;

import java.util.List;

import com.tutiveinteveinteveinticinco.acostafernandezlencina.dtos.IngredienteDTO;
import com.tutiveinteveinteveinticinco.acostafernandezlencina.dtos.IngredienteRecetaDTO;
import com.tutiveinteveinteveinticinco.acostafernandezlencina.dtos.RecetaDTO;
import com.tutiveinteveinteveinticinco.acostafernandezlencina.entidades.IngredienteReceta;
import com.tutiveinteveinteveinticinco.acostafernandezlencina.entidades.Receta;

public interface RecetaService {
    void eliminar(Long id);
    Receta obtenerPorId(Long id);
    List<Receta> listarActivas();
    List<Receta> buscarPorNombre(String nombre);
    List<Receta> buscarPorRangoCalorias(int desde, int hasta);
    void agregarIngredienteAReceta(Long recetaId, Long ingredienteId, double cantidad, int calorias);
    void eliminarIngredienteDeReceta(Long recetaId, Long ingredienteRecetaId);
    Receta obtenerRecetaPorId(Long id);
    double calcularCaloriasTotales(Long recetaId);
	RecetaDTO convertirADTO(Receta receta);
	Receta guardarReceta(Receta receta);
    boolean existeRecetaConNombre(String nombre);
	List<Receta> buscarPorFiltros(String nombre, Integer caloriasMin, Integer caloriasMax, boolean activo);
	void agregarIngrediente(Long recetaId, Long ingredienteId, double cantidad);
	void eliminarIngrediente(Long recetaId, Long ingredienteRecetaId);
	void agregarIngredienteAReceta(Long recetaId, Long id, double cantidad);
	boolean existeRecetaConNombre(String nombre, Long idExcluir);
	void actualizarCantidadIngrediente(Long recetaId, Long ingRecetaId, double nuevaCantidad);
	void reactivar(Long id);
	void recalcularCaloriasPorIngrediente(Long ingredienteId);


    }
