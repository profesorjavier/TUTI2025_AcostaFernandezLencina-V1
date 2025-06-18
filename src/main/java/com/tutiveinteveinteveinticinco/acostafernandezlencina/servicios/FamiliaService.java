package com.tutiveinteveinteveinticinco.acostafernandezlencina.servicios;

import com.tutiveinteveinteveinticinco.acostafernandezlencina.dtos.FamiliaDTO;
import com.tutiveinteveinteveinticinco.acostafernandezlencina.entidades.Familia;
import com.tutiveinteveinteveinticinco.acostafernandezlencina.accesodatos.FamiliaRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FamiliaService {

    @Autowired
    private FamiliaRepo familiaRepo;

    public List<FamiliaDTO> listarTodosDtos() {
        return familiaRepo.findAll().stream()
                .map(this::mapToDTOConIntegrantes)
                .collect(Collectors.toList());
    }

    public List<FamiliaDTO> listarActivas() {
        return familiaRepo.findByActivoTrue().stream()
                .map(this::mapToDTOConIntegrantes)
                .collect(Collectors.toList());
    }

    public List<FamiliaDTO> listarDtosInactivos() {
        return familiaRepo.findByActivoFalse().stream()
                .map(this::mapToDTOConIntegrantes)
                .collect(Collectors.toList());
    }

    public List<FamiliaDTO> listarTodosConIntegrantes() {
        return familiaRepo.findAll().stream()
                .map(this::mapToDTOConIntegrantes)
                .collect(Collectors.toList());
    }

    public List<FamiliaDTO> buscarFamilias(boolean activas, String filtro) {
        List<Familia> familias = activas ? familiaRepo.findByActivoTrue() : familiaRepo.findByActivoFalse();
        return familias.stream()
                .filter(f -> f.getNombreFamilia().toLowerCase().contains(filtro.toLowerCase())
                        || String.valueOf(f.getNroFamilia()).equals(filtro))
                .map(this::mapToDTOConIntegrantes)
                .collect(Collectors.toList());
    }

    public FamiliaDTO buscarDtoPorNroFamilia(Integer nroFamilia) {
        return familiaRepo.findBynroFamilia(nroFamilia)
                .map(this::mapToDTOConIntegrantes)
                .orElseThrow(() -> new IllegalArgumentException("Familia no encontrada"));
    }

    @Transactional
    public void guardarDesdeDto(FamiliaDTO dto) {
        validarFamilia(dto);

        Familia familia;
        if (dto.getNroFamilia() != null) {
            familia = familiaRepo.findBynroFamilia(dto.getNroFamilia())
                    .orElseThrow(() -> new RuntimeException("Familia no encontrada"));
            actualizarCamposBasicos(familia, dto);
        } else {
            familia = nuevaFamilia(dto);
        }

        familiaRepo.save(familia);
    }

    @Transactional
    public void inactivarPorNroFamilia(Integer nroFamilia) {
        Familia familia = familiaRepo.findBynroFamilia(nroFamilia)
                .orElseThrow(() -> new RuntimeException("Familia no encontrada"));
        familia.setActivo(false);
        familia.getIntegrantes().forEach(i -> i.setActivo(false));
        familiaRepo.save(familia);
    }

    @Transactional
    public void reactivarPorNroFamilia(Integer nroFamilia) {
        Familia familia = familiaRepo.findBynroFamilia(nroFamilia)
                .orElseThrow(() -> new RuntimeException("Familia no encontrada"));

        System.out.println("Reactivando familia " + familia.getNroFamilia() + ", estado actual: " + familia.getActivo());

        familia.setActivo(Boolean.TRUE);
        if (familia.getIntegrantes() != null) {
            familia.getIntegrantes().forEach(i -> i.setActivo(Boolean.TRUE));
        }

        familiaRepo.save(familia);

        System.out.println("Familia " + familia.getNroFamilia() + " reactivada. Estado: " + familia.getActivo());
    }

    @Transactional
    public void eliminar(Long id) {
        Optional<Familia> familiaOpt = familiaRepo.findById(id);
        familiaOpt.ifPresent(f -> {
            f.setActivo(false);
            familiaRepo.save(f);
        });
    }

    // =========== MÉTODOS PRIVADOS ===========

    private void validarFamilia(FamiliaDTO dto) {
        if (dto.getNombreFamilia() == null || dto.getNombreFamilia().isBlank()) {
            throw new IllegalArgumentException("El nombre de la familia es obligatorio");
        }
    }

    private Familia nuevaFamilia(FamiliaDTO dto) {
        Familia f = new Familia();
        f.setNombreFamilia(dto.getNombreFamilia());
        f.setFechaAlta(dto.getFechaAlta());
        f.setActivo(true);
        return f;
    }

    private void actualizarCamposBasicos(Familia f, FamiliaDTO dto) {
        f.setNombreFamilia(dto.getNombreFamilia());
    }

    private FamiliaDTO mapToDTOConIntegrantes(Familia f) {
        FamiliaDTO dto = new FamiliaDTO();
        dto.setNroFamilia(f.getNroFamilia());
        dto.setNombreFamilia(f.getNombreFamilia());
        dto.setFechaAlta(f.getFechaAlta());
        dto.setActivo(f.getActivo());
        dto.setIntegrantes(
            f.getIntegrantes() != null
                ? f.getIntegrantes().stream()
                    .map(in -> {
                        var i = new com.tutiveinteveinteveinticinco.acostafernandezlencina.dtos.IntegranteDTO();
                        i.setDni(in.getDni());
                        i.setNombre(in.getNombre());
                        i.setApellido(in.getApellido());
                        i.setFechaNacimiento(in.getFechaNacimiento());
                        i.setOcupacion(in.getOcupacion());
                        i.setActivo(in.getActivo());
                        return i;
                    }).collect(Collectors.toList())
                : List.of()
        );
        return dto;
    }
}
