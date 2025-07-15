package com.tutiveinteveinteveinticinco.acostafernandezlencina.dtos;

import lombok.Data;

@Data
public class IngredienteRecetaDTO {
	private Long id;
    private double cantidad;
    private double caloriasPorPorcion;
    private IngredienteDTO ingrediente;
    
    
    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public double getCantidad() {
		return cantidad;
	}
	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}
	public double getCaloriasPorPorcion() {
		return caloriasPorPorcion;
	}
	public void setCaloriasPorPorcion(double d) {
		this.caloriasPorPorcion = d;
	}
	public IngredienteDTO getIngrediente() {
		return ingrediente;
	}
	public void setIngrediente(IngredienteDTO ingrediente) {
		this.ingrediente = ingrediente;
	}

}