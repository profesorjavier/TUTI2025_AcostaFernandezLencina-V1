package com.tutiveinteveinteveinticinco.acostafernandezlencina.servicios;

import com.tutiveinteveinteveinticinco.acostafernandezlencina.dtos.PreparacionDTO;
import com.tutiveinteveinteveinticinco.acostafernandezlencina.entidades.Preparacion;

import java.time.LocalDate;
import java.util.List;

public interface PreparacionService {

    void guardar(Preparacion preparacion) throws Exception;

    void eliminar(Long id);

    List<PreparacionDTO> listarActivas();

    List<Preparacion> buscarPorRangoFechas(LocalDate desde, LocalDate hasta);

    List<Preparacion> listarActivasConReceta();
    List<Preparacion> buscarPorRangoFechasConReceta(LocalDate desde, LocalDate hasta);
    Preparacion obtenerPorId(Long id);
}
