package com.tutiveinteveinteveinticinco.acostafernandezlencina.entidades;


import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

import com.tutiveinteveinteveinticinco.acostafernandezlencina.entidades.Receta;

@Entity
public class Receta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String nombre;

    @Column(length = 1000, nullable = false)
    private String descripcion;

    private boolean activo = true;
    @Column(nullable = false)
    private double caloriasTotales;

    @OneToMany(mappedBy = "receta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IngredienteReceta> ingredientes = new ArrayList<>();

    //metodos
    
 // Métodos para manejar ingredientes
    public void agregarIngrediente(Ingrediente ingrediente, double cantidad) {
        IngredienteReceta ingredienteReceta = new IngredienteReceta();
        ingredienteReceta.setIngrediente(ingrediente);
        ingredienteReceta.setCantidad(cantidad);
        ingredienteReceta.setReceta(this);
        ingredienteReceta.setActivo(true);
        this.ingredientes.add(ingredienteReceta);
        this.calcularCaloriasTotales(); // Actualizar calorías
    }

    public void eliminarIngrediente(IngredienteReceta ingrediente) {
        ingrediente.setActivo(false);
        this.calcularCaloriasTotales(); // Actualizar calorías
    }

    @PrePersist
    @PreUpdate
    public void calcularCaloriasTotales() {
        this.caloriasTotales = this.ingredientes.stream()
            .filter(IngredienteReceta::isActivo)
            .mapToInt(ir -> (int)(ir.getIngrediente().getCaloriasPorUnidad() * ir.getCantidad()))
            .sum();
    }
    
    public void removerIngrediente(IngredienteReceta ingredienteReceta) {
        ingredientes.remove(ingredienteReceta);
        ingredienteReceta.setReceta(null);
        this.calcularCaloriasTotales(); // Actualizar calorías

    }
    
    // ----- GETTERS Y SETTERS -----

    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getId() {
        return id;
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

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public List<IngredienteReceta> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<IngredienteReceta> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public double getCaloriasTotales() {
     return caloriasTotales;
    }
 
    public void setCaloriasTotales(double total) {
     this.caloriasTotales = total;
    }




	
    
}
