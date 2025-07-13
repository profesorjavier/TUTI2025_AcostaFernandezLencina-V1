package com.tutiveinteveinteveinticinco.acostafernandezlencina.presentacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tutiveinteveinteveinticinco.acostafernandezlencina.entidades.IngredienteReceta;
import com.tutiveinteveinteveinticinco.acostafernandezlencina.entidades.Receta;
import com.tutiveinteveinteveinticinco.acostafernandezlencina.servicios.IngredienteService;
import com.tutiveinteveinteveinticinco.acostafernandezlencina.servicios.RecetaService;
import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/recetas")
public class RecetasController {

    @Autowired
    private RecetaService recetaService;
    
    @Autowired
    private IngredienteService ingredienteService;

    @GetMapping
    public String listar(Model model,
            @RequestParam(name = "nombre", required = false) String nombre,
            @RequestParam(name = "caloriasMin", required = false) Integer caloriasMin,
            @RequestParam(name = "caloriasMax", required = false) Integer caloriasMax) {
        
        // Valores por defecto para los filtros
        String nombreBusqueda = (nombre != null) ? nombre : "";
        int minCal = (caloriasMin != null) ? caloriasMin : 0;
        int maxCal = (caloriasMax != null) ? caloriasMax : Integer.MAX_VALUE;
        
        /*List<Receta> recetas = recetaService.buscarPorFiltros(nombreBusqueda, minCal, maxCal);
        model.addAttribute("recetas", recetas);*/
        List<Receta> activas = recetaService.buscarPorFiltros(nombreBusqueda, minCal, maxCal,true);
        List<Receta> inactivas = recetaService.buscarPorFiltros(nombreBusqueda, minCal, maxCal,false);
        model.addAttribute("recetas", activas);
        model.addAttribute("recetasInactivas", inactivas);
        return "recetas/listado";
    }

    @PostMapping("/reactivar/{id}")
    public String reactivarReceta(@PathVariable Long id) {
        recetaService.reactivar(id);
        return "redirect:/recetas";
    }
    @GetMapping("/nueva")
    public String nuevaReceta(Model model) {
        Receta receta = new Receta();
        model.addAttribute("receta", receta);
        model.addAttribute("ingredientesDisponibles", ingredienteService.listarActivos());
        return "recetas/formulario";
    }

    @PostMapping("/guardar")
    public String guardarReceta(
            @ModelAttribute("receta") @Valid Receta receta,
            BindingResult result,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "recetas/formulario";
        }

        try {
            // Verificar si es una edición (tiene ID)
            if (receta.getId() != null) {
                Receta existente = recetaService.obtenerRecetaPorId(receta.getId());
                // Solo actualizar campos editables
                existente.setDescripcion(receta.getDescripcion());
                recetaService.guardarReceta(existente);
            } else {
                // Es una nueva receta
                recetaService.guardarReceta(receta);
            }
            redirectAttributes.addFlashAttribute("success", "Receta guardada correctamente");
            return "redirect:/recetas/editar/" + receta.getId();
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("error", "Error: Ya existe una receta con ese nombre");
            return "redirect:/recetas/editar/" + (receta.getId() != null ? receta.getId() : "");
        }
    }

    @PostMapping("/{recetaId}/actualizarCantidad")
    @ResponseBody
    public ResponseEntity<?> actualizarCantidad(
            @PathVariable Long recetaId,
            @RequestParam Long ingRecetaId,
            @RequestParam double nuevaCantidad) {
        try {
            recetaService.actualizarCantidadIngrediente(recetaId, ingRecetaId, nuevaCantidad);
            return ResponseEntity.ok().build(); // HTTP 200 OK sin redirección
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error al actualizar cantidad");
        }
    }


    @GetMapping("/editar/{id}")
    public String editarReceta(@PathVariable Long id, Model model) {
        Receta receta = recetaService.obtenerPorId(id);
        if (receta == null) {
            return "redirect:/recetas";
        }
        
        model.addAttribute("receta", receta);
        model.addAttribute("ingredientesDisponibles", ingredienteService.listarActivos());
        return "recetas/formulario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarReceta(@PathVariable Long id) {
        recetaService.eliminar(id);
        return "redirect:/recetas";
    }

    @GetMapping("/{recetaId}/ingredientes")
    public String gestionarIngredientes(@PathVariable Long recetaId, Model model) {
        Receta receta = recetaService.obtenerPorId(recetaId);
        if (receta == null || !receta.isActivo()) {
            return "redirect:/recetas";
        }
        
        model.addAttribute("receta", receta);
        model.addAttribute("ingredientesDisponibles", ingredienteService.listarActivos());
        model.addAttribute("nuevoIngrediente", new IngredienteReceta());
        
        return "recetas/ingredientes";
    }

    @PostMapping("/{recetaId}/agregarIngrediente")
    public String agregarIngrediente(
            @PathVariable Long recetaId,
            @RequestParam Long ingredienteId,
            @RequestParam double cantidad,
            RedirectAttributes redirectAttributes) {
        
        try {
            recetaService.agregarIngredienteAReceta(recetaId, ingredienteId, cantidad);
            redirectAttributes.addFlashAttribute("success", "Ingrediente agregado correctamente");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        
        return "redirect:/recetas/editar/" + recetaId;
    }
    
    @PostMapping("/{recetaId}/ingredientes")
    public String agregarIngrediente(
            @PathVariable Long recetaId,
            @ModelAttribute("nuevoIngrediente") @Valid IngredienteReceta ingredienteReceta,
            BindingResult result, Model model) {
        
        if (result.hasErrors()) {
            Receta receta = recetaService.obtenerPorId(recetaId);
            model.addAttribute("receta", receta);
            model.addAttribute("ingredientesDisponibles", ingredienteService.listarActivos());
            return "recetas/ingredientes";
        }
        
        recetaService.agregarIngredienteAReceta(
            recetaId,
            ingredienteReceta.getIngrediente().getId(),
            ingredienteReceta.getCantidad()
        );
        
        return "redirect:/recetas/" + recetaId + "/ingredientes";
    }

    @GetMapping("/{recetaId}/eliminarIngrediente/{ingredienteRecetaId}")
    public String eliminarIngrediente(
            @PathVariable Long recetaId,
            @PathVariable Long ingredienteRecetaId) {
        
        recetaService.eliminarIngredienteDeReceta(recetaId, ingredienteRecetaId);
        
        // Redirigir directamente a la edición de la receta
        return "redirect:/recetas/editar/" + recetaId;
    }
    
}