package com.tutiveinteveinteveinticinco.acostafernandezlencina.dtos;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO para representar una Familia en la aplicación.
 * Se usa principalmente para transferir datos entre capas o en APIs REST.
 */
public class FamiliaDTO {

    // Número identificador único de la familia
    private Integer nroFamilia;

    // Nombre de la familia (obligatorio)
    @NotBlank(message = "El nombre de la familia no puede estar vacío")
    private String nombreFamilia;

    // Fecha de alta, por defecto se asigna a la fecha actual
    private LocalDate fechaAlta = LocalDate.now();

    // Estado activo/inactivo de la familia
    private Boolean activo = true;

    // Lista de integrantes de la familia
    private List<IntegranteDTO> integrantes = new ArrayList<>();

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

    public List<IntegranteDTO> getIntegrantes() {
        return integrantes;
    }

    public void setIntegrantes(List<IntegranteDTO> integrantes) {
        this.integrantes = integrantes;
    }
}