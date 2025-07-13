package com.tutiveinteveinteveinticinco.acostafernandezlencina.dtos;

import lombok.Data;

@Data
public class IngredienteDTO {
	private Long id;
    private String nombre;
    private String unidadBase;
    private double caloriasPorUnidad;
    
    public double getCaloriasPorUnidad() {
        return caloriasPorUnidad;
    }

    public void setCaloriasPorUnidad(double caloriasPorUnidad) {
        this.caloriasPorUnidad = caloriasPorUnidad;
    }
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
	public String getUnidadBase() {
		return unidadBase;
	}
	public void setUnidadBase(String unidadBase) {
		this.unidadBase = unidadBase;
	}
	
}