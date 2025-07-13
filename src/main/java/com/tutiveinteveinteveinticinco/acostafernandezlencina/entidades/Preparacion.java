package com.tutiveinteveinteveinticinco.acostafernandezlencina.entidades;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Preparacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate fecha;

    @ManyToOne
    @JoinColumn(name = "receta_id", nullable = false)
    private Receta receta;

    @Column(nullable = false)
    private int cantidadRaciones;

    @Column(nullable = false)
    private boolean activo = true;

    // === CONSTRUCTORES ===

    public Preparacion() {}

    public Preparacion(LocalDate fecha, Receta receta, int cantidadRaciones) {
        this.fecha = fecha;
        this.receta = receta;
        this.cantidadRaciones = cantidadRaciones;
        this.activo = true;
    }

    // === GETTERS Y SETTERS ===

    public Long getId() {
        return id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Receta getReceta() {
        return receta;
    }

    public void setReceta(Receta receta) {
        this.receta = receta;
    }

    public int getCantidadRaciones() {
        return cantidadRaciones;
    }

    public void setCantidadRaciones(int cantidadRaciones) {
        this.cantidadRaciones = cantidadRaciones;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    // Calorías por ración (suma total / cantidad)
    public double getCaloriasPorRacion() {
        return receta.getIngredientes().stream()
            .mapToDouble(IngredienteReceta::getCaloriasPorPorcion)
            .sum() / Math.max(1, cantidadRaciones);
    }
}
