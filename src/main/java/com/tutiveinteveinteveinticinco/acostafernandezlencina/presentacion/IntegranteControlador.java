package com.tutiveinteveinteveinticinco.acostafernandezlencina.presentacion;

import com.tutiveinteveinteveinticinco.acostafernandezlencina.dtos.IntegranteDTO;
import com.tutiveinteveinteveinticinco.acostafernandezlencina.servicios.IntegranteService;
import com.tutiveinteveinteveinticinco.acostafernandezlencina.servicios.FamiliaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/familias/{nroFamilia}/integrantes")
public class IntegranteControlador {

    @Autowired
    private IntegranteService integranteService;

    @Autowired
    private FamiliaService familiaService;

    // Formulario nuevo integrante
    @GetMapping("/nuevo")
    public String nuevoIntegrante(@PathVariable Integer nroFamilia, Model modelo) {
        var familia = familiaService.buscarDtoPorNroFamilia(nroFamilia);
        modelo.addAttribute("integrante", new IntegranteDTO());
        modelo.addAttribute("familiaId", familia.getNroFamilia());
        modelo.addAttribute("nombreFamilia", familia.getNombreFamilia());
        modelo.addAttribute("modo", "crear");
        return "familias/integrante_form";
    }

    // Guardar nuevo integrante
    @PostMapping("/guardar")
    public String guardarIntegrante(@PathVariable Integer nroFamilia,
                                  @ModelAttribute("integrante") IntegranteDTO dto,
                                  Model modelo) {
        try {
            integranteService.agregarIntegrante(nroFamilia, dto);
            return "redirect:/familias/ver/" + nroFamilia;
        } catch (IllegalArgumentException e) {
            var familia = familiaService.buscarDtoPorNroFamilia(nroFamilia);
            modelo.addAttribute("error", e.getMessage());
            modelo.addAttribute("familiaId", familia.getNroFamilia());
            modelo.addAttribute("nombreFamilia", familia.getNombreFamilia());
            modelo.addAttribute("modo", "crear");
            return "familias/integrante_form";
        }
    }

    @GetMapping("/editar/{dni}")
    public String editarIntegrante(@PathVariable Integer nroFamilia,
                                 @PathVariable Integer dni,
                                 Model modelo) {
        try {
            var dto = integranteService.buscarIntegranteDTOporDni(nroFamilia, dni);
            var familia = familiaService.buscarDtoPorNroFamilia(nroFamilia);
            
            // Debug: Verificar la fecha recibida
            System.out.println("Fecha de nacimiento obtenida: " + dto.getFechaNacimiento());
            
            modelo.addAttribute("integrante", dto);
            modelo.addAttribute("familiaId", familia.getNroFamilia());
            modelo.addAttribute("nombreFamilia", familia.getNombreFamilia());
            modelo.addAttribute("integranteDni", dni);
            modelo.addAttribute("modo", "editar");
            
            return "familias/integrante_form";
        } catch (Exception e) {
            modelo.addAttribute("error", "Error al cargar integrante: " + e.getMessage());
            return "redirect:/familias/ver/" + nroFamilia;
        }
    }

    // Actualizar integrante
    @PostMapping("/actualizar/{dni}")
    public String actualizarIntegrante(@PathVariable Integer nroFamilia,
                                      @PathVariable Integer dni,
                                      @ModelAttribute("integrante") IntegranteDTO dto) {
        integranteService.actualizarIntegrantePorDni(nroFamilia, dni, dto);
        return "redirect:/familias/ver/" + nroFamilia;
    }

    // Eliminar integrante
    @GetMapping("/eliminar/{dni}")
    public String eliminarIntegrante(@PathVariable Integer nroFamilia,
                                    @PathVariable Integer dni) {
        integranteService.eliminarIntegrantePorDni(nroFamilia, dni);
        return "redirect:/familias/ver/" + nroFamilia;
    }
}