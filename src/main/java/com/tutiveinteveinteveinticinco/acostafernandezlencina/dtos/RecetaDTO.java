package com.tutiveinteveinteveinticinco.acostafernandezlencina.dtos;

import lombok.Data;
import java.util.List;

@Data
public class RecetaDTO {
	private Long id;
    private String nombre;
    private String descripcion;
    private List<IngredienteRecetaDTO> ingredientes;
    
    
    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public List<IngredienteRecetaDTO> getIngredientes() {
		return ingredientes;
	}
	public void setIngredientes(List<IngredienteRecetaDTO> ingredientes) {
		this.ingredientes = ingredientes;
	}
	public void setCaloriasTotales(double d) {
		// TODO Auto-generated method stub
		
	}

}