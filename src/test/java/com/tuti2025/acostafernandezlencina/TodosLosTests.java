// Tests de integración y unitarios para el proyecto TUTI2025 - CORREGIDOS

package com.tuti2025.acostafernandezlencina;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutiveinteveinteveinticinco.acostafernandezlencina.dtos.FamiliaDTO;
import com.tutiveinteveinteveinticinco.acostafernandezlencina.dtos.IntegranteDTO;
import com.tutiveinteveinteveinticinco.acostafernandezlencina.entidades.Familia;
import com.tutiveinteveinteveinticinco.acostafernandezlencina.servicios.FamiliaService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class TodosLosTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FamiliaService familiaService;

    @Autowired
    private ObjectMapper objectMapper;

    private FamiliaDTO familiaDTO;

    @BeforeEach
    void setUp() {
        familiaDTO = new FamiliaDTO();
        familiaDTO.setNombreFamilia("Test Familia");
        familiaDTO.setFechaAlta(LocalDate.now());
    }

    @Test
    void testGuardarFamiliaNueva() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/familias/guardar")
                        .param("nuevoDni", "87654321") // DNI distinto al default
                        .param("nuevoNombre", "Juan")
                        .param("nuevoApellido", "Pérez")
                        .param("nuevoFechaNacimiento", "1990-01-01")
                        .param("nuevoOcupacion", "Ingeniero")
                        .flashAttr("familia", familiaDTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/familias/listar"));
    }

    @Test
    void testListarFamilias() throws Exception {
        familiaDTO.setIntegrantes(new ArrayList<>());
        IntegranteDTO i = new IntegranteDTO();
        i.setDni(11111111);
        i.setNombre("Ana");
        i.setApellido("López");
        i.setFechaNacimiento(LocalDate.of(1991, 2, 2));
        i.setOcupacion("Abogada");
        familiaDTO.getIntegrantes().add(i);
        familiaService.guardarDesdeDto(familiaDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/familias/listar"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("familiasActivas"));
    }

    @Test
    void testVerFamilia() throws Exception {
        familiaDTO.setIntegrantes(new ArrayList<>());
        IntegranteDTO i = new IntegranteDTO();
        i.setDni(22222222);
        i.setNombre("Carlos");
        i.setApellido("Mendez");
        i.setFechaNacimiento(LocalDate.of(1985, 5, 5));
        i.setOcupacion("Carpintero");
        familiaDTO.getIntegrantes().add(i);
        familiaService.guardarDesdeDto(familiaDTO);
        Integer nro = familiaService.listarTodosDtos().get(0).getNroFamilia();

        mockMvc.perform(MockMvcRequestBuilders.get("/familias/ver/" + nro))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("familia"));
    }

    @Test
    void testNuevoIntegranteVista() throws Exception {
        familiaDTO.setIntegrantes(new ArrayList<>());
        IntegranteDTO i = new IntegranteDTO();
        i.setDni(33333333);
        i.setNombre("Luz");
        i.setApellido("Ramírez");
        i.setFechaNacimiento(LocalDate.of(1993, 8, 8));
        i.setOcupacion("Ingeniera");
        familiaDTO.getIntegrantes().add(i);
        familiaService.guardarDesdeDto(familiaDTO);
        Integer nro = familiaService.listarTodosDtos().get(0).getNroFamilia();

        mockMvc.perform(MockMvcRequestBuilders.get("/familias/" + nro + "/nuevoIntegrante"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("integrante"));
    }

    @Test
    void testEliminarFamilia() throws Exception {
        familiaDTO.setIntegrantes(new ArrayList<>());
        IntegranteDTO i = new IntegranteDTO();
        i.setDni(44554444);
        i.setNombre("Raúl");
        i.setApellido("Suárez");
        i.setFechaNacimiento(LocalDate.of(1980, 10, 10));
        i.setOcupacion("Mecánico");
        familiaDTO.getIntegrantes().add(i);
        familiaService.guardarDesdeDto(familiaDTO);
        Long id = (long) familiaService.listarTodosDtos().get(0).getNroFamilia();

        mockMvc.perform(MockMvcRequestBuilders.get("/familias/eliminar/" + id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/familias/listar"));
    }
}
