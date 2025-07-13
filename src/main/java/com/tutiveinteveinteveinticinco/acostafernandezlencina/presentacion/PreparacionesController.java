package com.tutiveinteveinteveinticinco.acostafernandezlencina.presentacion;

import com.tutiveinteveinteveinticinco.acostafernandezlencina.dtos.PreparacionDTO;
import com.tutiveinteveinteveinticinco.acostafernandezlencina.entidades.Preparacion;
import com.tutiveinteveinteveinticinco.acostafernandezlencina.servicios.PreparacionService;
import com.tutiveinteveinteveinticinco.acostafernandezlencina.servicios.RecetaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/preparaciones")
public class PreparacionesController {

    private final PreparacionService preparacionService;
    private final RecetaService recetaService;

    public PreparacionesController(PreparacionService preparacionService, RecetaService recetaService) {
        this.preparacionService = preparacionService;
        this.recetaService = recetaService;
    }
    
    @GetMapping({"", "/"})
    public String mostrarListado(Model modelo) {
        List<PreparacionDTO> preparaciones = preparacionService.listarActivas();
        if(preparaciones == null) {
            preparaciones = new ArrayList<>();
        }
        modelo.addAttribute("preparaciones", preparaciones);
        return "preparaciones/listado";
    }

    @GetMapping("/listar")
    public String listar(Model modelo) {
        List<PreparacionDTO> preparaciones = preparacionService.listarActivas(); // o el método que uses
        if(preparaciones == null) {
            preparaciones = new ArrayList<>(); // Inicializa una lista vacía si es null
        }
        modelo.addAttribute("preparaciones", preparaciones);
        return "preparaciones/listado";
    }

    @GetMapping("/nueva")
    public String mostrarFormularioNueva(Model model) {
        model.addAttribute("preparacion", new Preparacion());
        model.addAttribute("recetas", recetaService.listarActivas());
        return "preparaciones/formulario";
    }

    @PostMapping("/guardar")
    public String guardarPreparacion(@ModelAttribute("preparacion") Preparacion preparacion,
                                   BindingResult result,
                                   Model model,
                                   RedirectAttributes redirectAttributes) {
        
        if (result.hasErrors()) {
            model.addAttribute("recetas", recetaService.listarActivas());
            return "preparaciones/formulario";
        }
        
        try {
            preparacionService.guardar(preparacion);
            redirectAttributes.addFlashAttribute("success", "Preparación guardada exitosamente");
            return "redirect:/preparaciones";
            
        } catch (Exception e) {
            model.addAttribute("error", "Error al guardar: " + e.getMessage());
            model.addAttribute("recetas", recetaService.listarActivas());
            return "preparaciones/formulario";
        }
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Preparacion preparacion = preparacionService.obtenerPorId(id);
        
        if (preparacion == null) {
            return "redirect:/preparaciones";
        }
        
        model.addAttribute("preparacion", preparacion);
        model.addAttribute("recetas", recetaService.listarActivas());
        return "preparaciones/formulario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarPreparacion(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            preparacionService.eliminar(id);
            redirectAttributes.addFlashAttribute("success", "Preparación eliminada exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar: " + e.getMessage());
        }
        return "redirect:/preparaciones";
    }
}