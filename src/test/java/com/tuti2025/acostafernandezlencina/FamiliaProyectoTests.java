package com.tuti2025.acostafernandezlencina;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutiveinteveinteveinticinco.acostafernandezlencina.dtos.FamiliaDTO;
import com.tutiveinteveinteveinticinco.acostafernandezlencina.dtos.IntegranteDTO;
import com.tutiveinteveinteveinticinco.acostafernandezlencina.servicios.FamiliaService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class FamiliaProyectoTests {

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
        familiaDTO.setIntegrantes(new ArrayList<>());

        IntegranteDTO integrante = new IntegranteDTO();
        integrante.setDni(12335678);
        integrante.setNombre("Juan");
        integrante.setApellido("Pérez");
        integrante.setFechaNacimiento(LocalDate.of(1990, 1, 1));
        integrante.setOcupacion("Ingeniero");

        familiaDTO.getIntegrantes().add(integrante);
    }

    @Test
    void testGuardarFamiliaNueva() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/familias/guardar")
                        .param("nuevoDni", "12345678")
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
        familiaService.guardarDesdeDto(familiaDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/familias/listar"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("familiasActivas"));
    }

    @Test
    void testVerFamilia() throws Exception {
        familiaService.guardarDesdeDto(familiaDTO);
        Integer nro = familiaService.listarTodosDtos().get(0).getNroFamilia();

        mockMvc.perform(MockMvcRequestBuilders.get("/familias/ver/" + nro))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("familia"));
    }

    @Test
    void testNuevoIntegranteVista() throws Exception {
        familiaService.guardarDesdeDto(familiaDTO);
        Integer nro = familiaService.listarTodosDtos().get(0).getNroFamilia();

        mockMvc.perform(MockMvcRequestBuilders.get("/familias/" + nro + "/nuevoIntegrante"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("integrante"));
    }

    @Test
    void testEliminarFamilia() throws Exception {
        familiaService.guardarDesdeDto(familiaDTO);
        Long id = (long) familiaService.listarTodosDtos().get(0).getNroFamilia();

        mockMvc.perform(MockMvcRequestBuilders.get("/familias/eliminar/" + id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/familias/listar"));
    }
}
