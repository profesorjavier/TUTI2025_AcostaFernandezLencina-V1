package com.tutiveinteveinteveinticinco.acostafernandezlencina.entidades;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Ingrediente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String nombre;
    private String descripcion;
    
    private String unidadBase; // gramos, ml, unidades, etc.
    
    @Column(name = "calorias_por_unidad", nullable = false)
    private double caloriasPorUnidad;
    private boolean activo = true;
    
    
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
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getUnidadBase() {
		return unidadBase;
	}
	public void setUnidadBase(String unidadBase) {
		this.unidadBase = unidadBase;
	}
	public boolean isActivo() {
		return activo;
	}
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	public boolean equalsIgnoreCase(Ingrediente ingrediente) {
		// TODO Auto-generated method stub
		return false;
	}

	
}