package com.tutiveinteveinteveinticinco.acostafernandezlencina.presentacion;

import com.tutiveinteveinteveinticinco.acostafernandezlencina.dtos.FamiliaDTO;
import com.tutiveinteveinteveinticinco.acostafernandezlencina.servicios.FamiliaService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/familias")
public class FamiliaControlador {

    @Autowired
    private FamiliaService familiaService;

    // === ALTA / MODIFICACIÓN ===

    @GetMapping("/nueva")
    public String nuevaFamilia(Model modelo) {
        modelo.addAttribute("familia", new FamiliaDTO());
        modelo.addAttribute("modo", "crear");
        return "familias/familia_form";
    }

    @GetMapping("/editar/{nroFamilia}")
    public String editarFamilia(@PathVariable Integer nroFamilia, Model modelo) {
        FamiliaDTO dto = familiaService.buscarDtoPorNroFamilia(nroFamilia);
        modelo.addAttribute("familia", dto);
        modelo.addAttribute("modo", "editar");
        return "familias/familia_form";
    }

    @PostMapping("/guardar")
    public String guardarFamilia(@Valid @ModelAttribute("familia") FamiliaDTO dto, BindingResult result, Model modelo) {
        if (result.hasErrors()) {
            modelo.addAttribute("modo", dto.getNroFamilia() == null ? "crear" : "editar");
            return "familias/familia_form";
        }

        familiaService.guardarDesdeDto(dto);
        return "redirect:/familias/listar";
    }

    // === VER DETALLES ===

    @GetMapping("/ver/{nroFamilia}")
    public String verFamilia(@PathVariable Integer nroFamilia, Model modelo) {
        FamiliaDTO dto = familiaService.buscarDtoPorNroFamilia(nroFamilia);
        modelo.addAttribute("familia", dto);
        return "familias/ver";
    }

    // === LISTADOS ===

    @GetMapping("/listar")
    public String listarTodo(@RequestParam(required = false) String busqueda, Model modelo) {
        List<FamiliaDTO> activas;
        List<FamiliaDTO> inactivas;

        if (busqueda != null && !busqueda.isBlank()) {
            activas = familiaService.buscarFamilias(true, busqueda);
            inactivas = familiaService.buscarFamilias(false, busqueda);
        } else {
            activas = familiaService.listarActivas();
            inactivas = familiaService.listarDtosInactivos();
        }

        modelo.addAttribute("familiasActivas", activas);
        modelo.addAttribute("familiasInactivas", inactivas);
        modelo.addAttribute("modo", "normal");
        return "familias/listado";
    }

    @GetMapping("/listadoCompleto")
    public String listadoCompleto(Model modelo) {
        List<FamiliaDTO> familias = familiaService.listarTodosConIntegrantes();
        modelo.addAttribute("familias", familias);
        modelo.addAttribute("modo", "completo");
        return "familias/listado";
    }

    // === ESTADO: INACTIVAR / REACTIVAR ===

    @GetMapping("/inactivar/{nroFamilia}")
    public String inactivarFamilia(@PathVariable Integer nroFamilia) {
        familiaService.inactivarPorNroFamilia(nroFamilia);
        return "redirect:/familias/listar";
    }

    @GetMapping("/reactivar/{nroFamilia}")
    public String reactivarFamilia(@PathVariable Integer nroFamilia) {
        familiaService.reactivarPorNroFamilia(nroFamilia);
        return "redirect:/familias/listar";
    }

    // === RUTA AL MENÚ PRINCIPAL ===

    @GetMapping("")
    public String redirigirAlMenu() {
        return "familias/menu";
    }

    @GetMapping("/menu")
    public String menuPrincipal() {
        return "menu";
    }
}
