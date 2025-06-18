package com.tutiveinteveinteveinticinco.acostafernandezlencina.entidades;

import jakarta.persistence.*;

/**
 * Entidad que representa a un integrante de una familia.
 * Hereda de Persona, por lo que incluye datos como nombre, apellido, etc.
 */
@Entity
public class Integrante extends Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Estado del integrante: activo o inactivo
    private Boolean activo = true;

    // Relación con la familia a la que pertenece
    @ManyToOne
    @JoinColumn(name = "familia_id")
    private Familia familia;

    // === Getters y Setters ===


    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Familia getFamilia() {
        return familia;
    }

    public void setFamilia(Familia familia) {
        this.familia = familia;
    }

    /**
     * Este setter está aquí por cuestiones de validación o mapeo,
     * aunque ocupación venga de la clase Persona.
     */
    @Override
    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }
}