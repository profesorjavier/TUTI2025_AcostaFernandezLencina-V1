package com.tutiveinteveinteveinticinco.acostafernandezlencina.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDate;

/**
 * Clase base abstracta para personas (como Integrante).
 * Contiene campos comunes como dni, nombre, apellido, etc.
 */
@MappedSuperclass
public abstract class Persona {

    // Documento único e identificador de la persona
    @Column(nullable = false, unique = true)
    private Integer dni;

    // Nombre del individuo
    @Column(nullable = false)
    private String nombre;

    // Apellido del individuo
    @Column(nullable = false)
    private String apellido;

    // Fecha de nacimiento (usamos LocalDate)
    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    // Ocupación actual (protegida para que sea accesible en subclases)
    @Column(name = "ocupacion", nullable = false)
    protected String ocupacion;

    // === Getters y Setters ===

    public String getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }

    public Integer getDni() {
        return dni;
    }

    public void setDni(Integer dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    // === Métodos auxiliares ===

    /**
     * Devuelve el nombre completo formateado como "Apellido, Nombre".
     * Útil para mostrar en listados o selects.
     */
    public String getNombreCompleto() {
        return this.apellido + ", " + this.nombre;
    }
}