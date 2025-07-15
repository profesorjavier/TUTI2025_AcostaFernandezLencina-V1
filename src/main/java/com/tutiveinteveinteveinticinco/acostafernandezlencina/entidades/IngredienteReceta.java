package com.tutiveinteveinteveinticinco.acostafernandezlencina.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "ingrediente_receta")
@Data // Lombok genera automáticamente getters, setters, etc.
public class IngredienteReceta {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Ingrediente ingrediente;

    @Column(nullable = false)
    private double cantidad;

    @Column(name = "calorias_por_porcion", nullable = false)
    private double caloriasPorPorcion = 0;

    @ManyToOne
    @JoinColumn(name = "receta_id", nullable = false) 
    private Receta receta;

    @Column(nullable = false)
    private boolean activo = true;
    
    
    
    public IngredienteReceta() {
        // Constructor vacío necesario para JPA
    }

    public IngredienteReceta(Ingrediente ingrediente, double cantidad) {
        this.ingrediente = ingrediente;
        this.cantidad = cantidad;
        calcularCalorias();
        }
    
    @PrePersist
    @PreUpdate
    public void calcularCalorias() {
        if (this.ingrediente != null) {
            // Usar caloriasPorUnidad del ingrediente asociado
            this.caloriasPorPorcion = (int) Math.round(
                this.ingrediente.getCaloriasPorUnidad() * this.cantidad
            );
        } else {
            this.caloriasPorPorcion = 0; // Manejo de caso nulo
        }
    }
    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Ingrediente getIngrediente() {
		return ingrediente;
	}

	public void setIngrediente(Ingrediente ingrediente) {
	    this.ingrediente = ingrediente;
	    calcularCalorias(); // Recalcular al cambiar ingrediente
	}

	public double getCantidad() {
		return cantidad;
	}

	public void setCantidad(double cantidad) {
	    this.cantidad = cantidad;
	    calcularCalorias(); // Recalcular al cambiar cantidad
	}

	public double getCaloriasPorPorcion() {
		return caloriasPorPorcion;
	}

	public void setCaloriasPorPorcion(double d) {
		this.caloriasPorPorcion = d;
	}

	public Receta getReceta() {
		return receta;
	}

	public void setReceta(Receta receta) {
		this.receta = receta;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	
}