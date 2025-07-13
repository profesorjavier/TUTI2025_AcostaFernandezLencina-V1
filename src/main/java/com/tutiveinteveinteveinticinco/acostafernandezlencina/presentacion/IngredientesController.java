package com.tutiveinteveinteveinticinco.acostafernandezlencina.presentacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.tutiveinteveinteveinticinco.acostafernandezlencina.accesodatos.IIngredienteRepo;
import com.tutiveinteveinteveinticinco.acostafernandezlencina.entidades.Ingrediente;
import com.tutiveinteveinteveinticinco.acostafernandezlencina.servicios.RecetaService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/ingredientes")
public class IngredientesController {

    @Autowired
    private IIngredienteRepo ingredienteRepo;
    
    @Autowired
    private RecetaService recetaService;

    // Listar todos los ingredientes (activos)
    @GetMapping
    public String listar(
            @RequestParam(required = false) String nombre,
            Model model) {
        
        List<Ingrediente> ingredientes = nombre != null ?
            ingredienteRepo.findByNombreContainingIgnoreCase(nombre) :
            ingredienteRepo.findByActivoTrue();
            
        model.addAttribute("ingredientes", ingredientes);
        return "ingredientes/listado";
    }

 // Formulario creación/edición (compartido)
    @GetMapping({"/nuevo", "/editar/{id}"})
    public String mostrarFormulario(
            @PathVariable(required = false) Long id,
            Model model) {
        
        Ingrediente ingrediente = id != null ?
            ingredienteRepo.findById(id).orElse(new Ingrediente()) :
            new Ingrediente();
            
        model.addAttribute("ingrediente", ingrediente);
        return "ingredientes/formulario";
    }

    // Guardar nuevo ingrediente
    @PostMapping("/guardar")
    public String guardar(
            @ModelAttribute("ingrediente") @Valid Ingrediente ingrediente,
            BindingResult result,
            Model model) {
        
        if (result.hasErrors()) {
            return "ingredientes/formulario";
        }

        boolean esEdicion = (ingrediente.getId() != null);
        double caloriasOriginales = 0;

        if (esEdicion) {
            Ingrediente original = ingredienteRepo.findById(ingrediente.getId())
                .orElseThrow(() -> new IllegalArgumentException("Ingrediente no encontrado"));
            caloriasOriginales = original.getCaloriasPorUnidad();
        }

        ingrediente.setActivo(true);
        ingredienteRepo.save(ingrediente);

        // Si cambiaron las calorías, actualizar recetas relacionadas
        if (esEdicion && caloriasOriginales != ingrediente.getCaloriasPorUnidad()) {
            recetaService.recalcularCaloriasPorIngrediente(ingrediente.getId());
        }

        return "redirect:/ingredientes";
    }

   

    // Eliminar (borrado lógico)
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        Ingrediente ingrediente = ingredienteRepo.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        
        ingrediente.setActivo(false);
        ingredienteRepo.save(ingrediente);
        return "redirect:/ingredientes";
    }
}