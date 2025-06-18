package com.tutiveinteveinteveinticinco.acostafernandezlencina.presentacion;

import com.tutiveinteveinteveinticinco.acostafernandezlencina.dtos.IntegranteDTO;
import com.tutiveinteveinteveinticinco.acostafernandezlencina.servicios.IntegranteService;
import com.tutiveinteveinteveinticinco.acostafernandezlencina.servicios.FamiliaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/familias")
public class IntegranteControlador {

    @Autowired
    private IntegranteService integranteService;

    @Autowired
    private FamiliaService familiaService;

    // Formulario para nuevo integrante
    @GetMapping("/{nroFamilia}/nuevoIntegrante")
    public String nuevoIntegrante(@PathVariable Integer nroFamilia, Model modelo) {
        var familia = familiaService.buscarDtoPorNroFamilia(nroFamilia);
        modelo.addAttribute("integrante", new IntegranteDTO());
        modelo.addAttribute("familiaId", familia.getNroFamilia());
        modelo.addAttribute("nombreFamilia", familia.getNombreFamilia());
        modelo.addAttribute("modo", "crear");
        return "familias/integrante_form";
    }

    // Guardar nuevo integrante
    @PostMapping("/{nroFamilia}/guardarIntegrante")
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

    // Formulario de edición
    @GetMapping("/{nroFamilia}/editarIntegrantePorDni/{dni}")
    public String editarIntegrante(@PathVariable Integer nroFamilia,
                                    @PathVariable Integer dni,
                                    Model modelo) {
        var dto = integranteService.buscarIntegranteDTOporDni(nroFamilia, dni);
        var familia = familiaService.buscarDtoPorNroFamilia(nroFamilia);

        modelo.addAttribute("integrante", dto);
        modelo.addAttribute("familiaId", familia.getNroFamilia());
        modelo.addAttribute("nombreFamilia", familia.getNombreFamilia());
        modelo.addAttribute("integranteDni", dni);
        modelo.addAttribute("modo", "editar");
        return "familias/integrante_form";
    }

    // Guardar edición
    @PostMapping("/{nroFamilia}/actualizarIntegrantePorDni/{dni}")
    public String actualizarIntegrante(@PathVariable Integer nroFamilia,
                                       @PathVariable Integer dni,
                                       @ModelAttribute("integrante") IntegranteDTO dto) {
        integranteService.actualizarIntegrantePorDni(nroFamilia, dni, dto);
        return "redirect:/familias/ver/" + nroFamilia;
    }

    // Eliminar (inactivar) integrante
    @GetMapping("/{nroFamilia}/eliminarIntegrantePorDni/{dni}")
    public String eliminarIntegrante(@PathVariable Integer nroFamilia,
                                     @PathVariable Integer dni) {
        integranteService.eliminarIntegrantePorDni(nroFamilia, dni);
        return "redirect:/familias/ver/" + nroFamilia;
    }
}
