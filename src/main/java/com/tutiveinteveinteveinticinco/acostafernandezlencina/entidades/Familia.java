package com.tutiveinteveinteveinticinco.acostafernandezlencina.entidades;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad que representa una familia en el sistema.
 * Mapea la tabla 'Familia' en la base de datos.
 */
@Entity
public class Familia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer nroFamilia;

    // Nombre descriptivo de la familia
    private String nombreFamilia;

    // Fecha de alta, por defecto se asigna a la fecha actual
    private LocalDate fechaAlta = LocalDate.now();

    // Estado activo/inactivo
    private Boolean activo = true;

    // Relación uno a muchos con Integrante
    @OneToMany(mappedBy = "familia", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Integrante> integrantes = new ArrayList<>();

    // === Constructores ===

    public Familia() {
        this.integrantes = new ArrayList<>();
    }

    // === Getters y Setters ===

    public Integer getNroFamilia() {
        return nroFamilia;
    }

    public void setNroFamilia(Integer nroFamilia) {
        this.nroFamilia = nroFamilia;
    }

    public String getNombreFamilia() {
        return nombreFamilia;
    }

    public void setNombreFamilia(String nombreFamilia) {
        this.nombreFamilia = nombreFamilia;
    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public List<Integrante> getIntegrantes() {
        return integrantes;
    }

    public void setIntegrantes(List<Integrante> integrantes) {
        this.integrantes = integrantes;
    }
}