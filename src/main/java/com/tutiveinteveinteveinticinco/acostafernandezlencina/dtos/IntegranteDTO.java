package com.tutiveinteveinteveinticinco.acostafernandezlencina.dtos;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

/**
 * DTO para representar a un integrante de familia.
 * Se usa para transferir datos entre capas o exponer en APIs REST.
 */
public class IntegranteDTO {

    // Documento Nacional de Identidad (obligatorio y positivo)
    @NotNull(message = "El DNI no puede ser nulo")
    @Positive(message = "El DNI debe ser un número positivo")
    private Integer dni;

    // Nombre del integrante (obligatorio)
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    // Apellido del integrante (obligatorio)
    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;

    // Fecha de nacimiento (obligatoria)
    @NotNull(message = "La fecha de nacimiento es obligatoria")
    private LocalDate fechaNacimiento;

    // Ocupación actual (cadena de texto, por simplicidad)
    @NotNull(message = "La ocupación es obligatoria")
    private String ocupacion;
    
    // Estado del integrante: activo o inactivo
    private Boolean activo = true;

    public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	// Lista de integrantes (posiblemente familiares asociados)
    private List<IntegranteDTO> integrantes;


    // === Getters y Setters ===

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

    public String getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }

    public List<IntegranteDTO> getIntegrantes() {
        return integrantes;
    }

    public void setIntegrantes(List<IntegranteDTO> integrantes) {
        this.integrantes = integrantes;
    }
}