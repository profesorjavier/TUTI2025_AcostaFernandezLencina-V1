package com.tutiveinteveinteveinticinco.acostafernandezlencina.servicios;

import com.tutiveinteveinteveinticinco.acostafernandezlencina.dtos.IntegranteDTO;
import com.tutiveinteveinteveinticinco.acostafernandezlencina.entidades.Familia;
import com.tutiveinteveinteveinticinco.acostafernandezlencina.entidades.Integrante;
import com.tutiveinteveinteveinticinco.acostafernandezlencina.accesodatos.FamiliaRepo;
import com.tutiveinteveinteveinticinco.acostafernandezlencina.accesodatos.IntegranteRepo;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IntegranteService {

    @Autowired
    private FamiliaRepo familiaRepo;

    @Autowired
    private IntegranteRepo integranteRepo;

    @Transactional
    public void agregarIntegrante(Integer nroFamilia, IntegranteDTO dto) {
        Familia familia = familiaRepo.findBynroFamilia(nroFamilia)
                .orElseThrow(() -> new IllegalArgumentException("Familia no encontrada"));

        if (integranteRepo.existsByDniAndFamilia(dto.getDni(), familia)) {
            throw new IllegalArgumentException("Ya existe un integrante con ese DNI en esta familia");
        }

        Integrante nuevo = new Integrante();
        nuevo.setDni(dto.getDni());
        nuevo.setApellido(dto.getApellido());
        nuevo.setNombre(dto.getNombre());
        nuevo.setFechaNacimiento(dto.getFechaNacimiento());
        nuevo.setOcupacion(dto.getOcupacion());
        nuevo.setActivo(true);
        nuevo.setFamilia(familia);

        integranteRepo.save(nuevo);
    }

    public IntegranteDTO buscarIntegranteDTOporDni(Integer nroFamilia, Integer dni) {
        Familia familia = familiaRepo.findBynroFamilia(nroFamilia)
                .orElseThrow(() -> new IllegalArgumentException("Familia no encontrada"));

        Integrante integrante = integranteRepo.findByDniAndFamilia(dni, familia)
                .orElseThrow(() -> new IllegalArgumentException("Integrante no encontrado"));

        return mapToDTO(integrante);
    }

    @Transactional
    public void actualizarIntegrantePorDni(Integer nroFamilia, Integer dni, IntegranteDTO dto) {
        Familia familia = familiaRepo.findBynroFamilia(nroFamilia)
                .orElseThrow(() -> new IllegalArgumentException("Familia no encontrada"));

        Integrante existente = integranteRepo.findByDniAndFamilia(dni, familia)
                .orElseThrow(() -> new IllegalArgumentException("Integrante no encontrado"));

        existente.setApellido(dto.getApellido());
        existente.setNombre(dto.getNombre());
        existente.setFechaNacimiento(dto.getFechaNacimiento());
        existente.setOcupacion(dto.getOcupacion());
        existente.setActivo(dto.getActivo() != null ? dto.getActivo() : true);

        integranteRepo.save(existente);
    }

    @Transactional
    public void eliminarIntegrantePorDni(Integer nroFamilia, Integer dni) {
        Familia familia = familiaRepo.findBynroFamilia(nroFamilia)
                .orElseThrow(() -> new IllegalArgumentException("Familia no encontrada"));

        Integrante existente = integranteRepo.findByDniAndFamilia(dni, familia)
                .orElseThrow(() -> new IllegalArgumentException("Integrante no encontrado"));

        existente.setActivo(false);
        integranteRepo.save(existente);
    }

    public List<IntegranteDTO> listarDTOsPorFamilia(Integer nroFamilia) {
        Familia familia = familiaRepo.findBynroFamilia(nroFamilia)
                .orElseThrow(() -> new RuntimeException("Familia no encontrada"));

        return integranteRepo.findByFamilia(familia).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // ========== MAPEO ==========
    private IntegranteDTO mapToDTO(Integrante i) {
        IntegranteDTO dto = new IntegranteDTO();
        dto.setDni(i.getDni());
        dto.setNombre(i.getNombre());
        dto.setApellido(i.getApellido());
        dto.setFechaNacimiento(i.getFechaNacimiento());
        dto.setOcupacion(i.getOcupacion());
        dto.setActivo(i.getActivo());
        return dto;
    }
}
