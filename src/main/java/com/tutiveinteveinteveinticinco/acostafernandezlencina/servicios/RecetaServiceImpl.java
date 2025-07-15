package com.tutiveinteveinteveinticinco.acostafernandezlencina.servicios;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.tutiveinteveinteveinticinco.acostafernandezlencina.accesodatos.IIngredienteRecetaRepo;
import com.tutiveinteveinteveinticinco.acostafernandezlencina.accesodatos.IIngredienteRepo;
import com.tutiveinteveinteveinticinco.acostafernandezlencina.accesodatos.IRecetaRepo;
import com.tutiveinteveinteveinticinco.acostafernandezlencina.dtos.IngredienteDTO;
import com.tutiveinteveinteveinticinco.acostafernandezlencina.dtos.IngredienteRecetaDTO;
import com.tutiveinteveinteveinticinco.acostafernandezlencina.dtos.RecetaDTO;
import com.tutiveinteveinteveinticinco.acostafernandezlencina.entidades.Ingrediente;
import com.tutiveinteveinteveinticinco.acostafernandezlencina.entidades.IngredienteReceta;
import com.tutiveinteveinteveinticinco.acostafernandezlencina.entidades.Receta;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
@Transactional

public class RecetaServiceImpl implements RecetaService {

    @Autowired
    private IRecetaRepo recetaRepo;

    @Autowired
    private IIngredienteRepo ingredienteRepo;
    
    @Autowired
    private IIngredienteRecetaRepo ingredienteRecetaRepo;
    
    @Override
    @Transactional
    public Receta guardarReceta(Receta receta) {
        // Validar nombre único solo para nuevas recetas
        if (receta.getId() == null && recetaRepo.existsByNombreAndActivoTrue(receta.getNombre())) {
            throw new DataIntegrityViolationException("Ya existe una receta activa con este nombre");
        }

        // Si es una edición, cargar la receta existente
        if (receta.getId() != null) {
            Receta recetaExistente = recetaRepo.findById(receta.getId())
                .orElseThrow(() -> new EntityNotFoundException("Receta no encontrada"));
            
            // Actualizar solo campos editables
            recetaExistente.setDescripcion(receta.getDescripcion());
            
            // Mantener el estado activo
            recetaExistente.setActivo(receta.isActivo());
            
            // Asignar relación bidireccional para ingredientes
            if (receta.getIngredientes() != null) {
                for (IngredienteReceta ir : receta.getIngredientes()) {
                    if (ir.isActivo()) {
                        ir.setReceta(recetaExistente);
                    }
                }
                recetaExistente.setIngredientes(receta.getIngredientes());
            }
            
            return recetaRepo.save(recetaExistente);
        } else {
            // Es una nueva receta - establecer como activa por defecto
            receta.setActivo(true);
            
            // Asignar relación bidireccional
            if (receta.getIngredientes() != null) {
                for (IngredienteReceta ir : receta.getIngredientes()) {
                    if (ir.isActivo()) {
                        ir.setReceta(receta);
                    }
                }
            }
            
            return recetaRepo.save(receta);
        }
    }

    @Override
    public void eliminar(Long id) {
        Receta receta = recetaRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Receta no encontrada"));
        receta.setActivo(false);
        recetaRepo.save(receta);
    }

    @Override
    public Receta obtenerPorId(Long id) {
        return recetaRepo.findByIdAndActivoTrue(id)
            .orElseThrow(() -> new RuntimeException("Receta no encontrada o inactiva"));
    }

    @Override
    public List<Receta> listarActivas() {
        return recetaRepo.findByActivoTrue();
    }

    @Override
    public List<Receta> buscarPorNombre(String nombre) {
        return recetaRepo.findByActivoTrueAndNombreContainingIgnoreCase(nombre);
    }

    @Override
    public List<Receta> buscarPorFiltros(String nombre, Integer caloriasMin, Integer caloriasMax, boolean activo) {
        if (nombre == null) nombre = "";
        if (caloriasMin == null) caloriasMin = 0;
        if (caloriasMax == null) caloriasMax = Integer.MAX_VALUE;

        return recetaRepo.findByNombreContainingIgnoreCaseAndCaloriasTotalesBetweenAndActivo(
            nombre, caloriasMin, caloriasMax, activo);
    }


    @Override
    public void agregarIngrediente(Long recetaId, Long ingredienteId, double cantidad) {
        Receta receta = recetaRepo.findById(recetaId).orElseThrow();
        Ingrediente ingrediente = ingredienteRepo.findById(ingredienteId).orElseThrow();
        
        // Verificar si el ingrediente ya existe en la receta
        boolean ingredienteExistente = receta.getIngredientes().stream()
            .anyMatch(ir -> ir.getIngrediente().getId().equals(ingredienteId));
        
        if (!ingredienteExistente) {
            IngredienteReceta ingredienteReceta = new IngredienteReceta();
            ingredienteReceta.setIngrediente(ingrediente);
            ingredienteReceta.setCantidad(cantidad);
            ingredienteReceta.setReceta(receta);
            receta.getIngredientes().add(ingredienteReceta);
            recetaRepo.save(receta);
        }
    }

    @Override
    public void eliminarIngrediente(Long recetaId, Long ingredienteRecetaId) {
        Receta receta = recetaRepo.findById(recetaId).orElseThrow();
        receta.getIngredientes().removeIf(ir -> ir.getId().equals(ingredienteRecetaId));
        recetaRepo.save(receta);
    }

    @Override
    public double calcularCaloriasTotales(Long recetaId) {
        return obtenerPorId(recetaId).getIngredientes().stream()
            .filter(IngredienteReceta::isActivo)
            .mapToDouble(IngredienteReceta::getCaloriasPorPorcion)
            .sum();
    }

    @Override
    public boolean existeRecetaConNombre(String nombre, Long idExcluir) {
        if (idExcluir == null) {
            return recetaRepo.existsByNombreAndActivoTrue(nombre);
        }
        return recetaRepo.existsByNombreAndActivoTrueAndIdNot(nombre, idExcluir);
    }

    @Override
    public RecetaDTO convertirADTO(Receta receta) {
        RecetaDTO dto = new RecetaDTO();
        dto.setId(receta.getId());
        dto.setNombre(receta.getNombre());
        dto.setDescripcion(receta.getDescripcion());
        dto.setCaloriasTotales(calcularCaloriasTotales(receta.getId()));
        
        List<IngredienteRecetaDTO> ingredientesDTO = receta.getIngredientes().stream()
            .filter(IngredienteReceta::isActivo)
            .map(this::convertirIngredienteRecetaADTO)
            .collect(Collectors.toList());
        
        dto.setIngredientes(ingredientesDTO);
        return dto;
    }

    private IngredienteRecetaDTO convertirIngredienteRecetaADTO(IngredienteReceta ir) {
        IngredienteRecetaDTO dto = new IngredienteRecetaDTO();
        dto.setId(ir.getId());
        dto.setCantidad(ir.getCantidad());
        dto.setCaloriasPorPorcion(ir.getCaloriasPorPorcion());
        
        IngredienteDTO ingredienteDTO = new IngredienteDTO();
        ingredienteDTO.setId(ir.getIngrediente().getId());
        ingredienteDTO.setNombre(ir.getIngrediente().getNombre());
        ingredienteDTO.setUnidadBase(ir.getIngrediente().getUnidadBase());
        
        dto.setIngrediente(ingredienteDTO);
        return dto;
    }

	@Override
	public List<Receta> buscarPorRangoCalorias(int desde, int hasta) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean existeRecetaConNombre(String nombre) {
		// TODO Auto-generated method stub
		return false;
	}

	 @Override
	    public void agregarIngredienteAReceta(Long recetaId, Long ingredienteId, double cantidad) {
	        Receta receta = recetaRepo.findById(recetaId)
	            .orElseThrow(() -> new RuntimeException("Receta no encontrada"));
	        
	        Ingrediente ingrediente = ingredienteRepo.findById(ingredienteId)
	            .orElseThrow(() -> new RuntimeException("Ingrediente no encontrado"));

	        // Verificar si el ingrediente ya existe y está activo
	        boolean existe = receta.getIngredientes().stream()
	            .anyMatch(ir -> ir.getIngrediente().getId().equals(ingredienteId) && ir.isActivo());
	        
	        if (!existe) {
	            receta.agregarIngrediente(ingrediente, cantidad);
	            recetaRepo.save(receta); // Esto activará el cálculo de calorías
	        } else {
	            throw new RuntimeException("El ingrediente ya existe en esta receta");
	        }
	    }
	 @Override
	 @Transactional
	 public void eliminarIngredienteDeReceta(Long recetaId, Long ingredienteRecetaId) {
	     IngredienteReceta ingredienteReceta = ingredienteRecetaRepo.findById(ingredienteRecetaId)
	         .orElseThrow(() -> new EntityNotFoundException("Relación no encontrada"));
	     
	     if (!ingredienteReceta.getReceta().getId().equals(recetaId)) {
	         throw new IllegalArgumentException("No pertenece a esta receta");
	     }
	     
	        Receta receta = recetaRepo.findById(recetaId)
	                .orElseThrow(() -> new EntityNotFoundException("Receta no encontrada"));
	        
	     // Eliminar la relación de ambos lados
	     ingredienteReceta.getReceta().getIngredientes().remove(ingredienteReceta);
	     ingredienteRecetaRepo.delete(ingredienteReceta);
	     
	     // Recalcular calorías totales
	     receta.calcularCaloriasTotales();
	     recetaRepo.save(receta); // Guardar receta actualizada
	 }

	 @Override
	 public void recalcularCaloriasPorIngrediente(Long ingredienteId) {
	     // Obtener todos los IngredienteReceta que usan este ingrediente
	     List<IngredienteReceta> relaciones = ingredienteRecetaRepo.findByIngredienteId(ingredienteId);

	     for (IngredienteReceta ir : relaciones) {
	         double nuevaCalPorPorcion = ir.getCantidad() * ir.getIngrediente().getCaloriasPorUnidad();
	         ir.setCaloriasPorPorcion(nuevaCalPorPorcion);
	         ingredienteRecetaRepo.save(ir);

	         // Recalcular y guardar la receta asociada
	         Receta receta = ir.getReceta();
	         double total = receta.getIngredientes().stream()
	             .filter(IngredienteReceta::isActivo)
	             .mapToDouble(IngredienteReceta::getCaloriasPorPorcion)
	             .sum();
	         receta.setCaloriasTotales(total);
	         recetaRepo.save(receta);
	     }
	 }
	 
	 
	@Override
	public void agregarIngredienteAReceta(Long recetaId, Long ingredienteId, double cantidad, int calorias) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Receta obtenerRecetaPorId(Long id) {
	    return recetaRepo.findById(id)
	            .orElseThrow(() -> new EntityNotFoundException("Receta no encontrada"));
	}


	@Override
	public void actualizarCantidadIngrediente(Long recetaId, Long ingRecetaId, double nuevaCantidad) {
	    Receta receta = recetaRepo.findById(recetaId).orElseThrow();
	
		IngredienteReceta ingReceta = ingredienteRecetaRepo.findById(ingRecetaId)
	        .orElseThrow(() -> new RuntimeException("Ingrediente no encontrado"));

	    
	    
	    if (!ingReceta.getReceta().getId().equals(recetaId)) {
	        throw new RuntimeException("Ingrediente no pertenece a la receta");
	    }

	    ingReceta.setCantidad(nuevaCantidad);

	    // Si tu entidad calcula calorías por porción, podés actualizarlo también:
	    double caloriasUnitarias = ingReceta.getIngrediente().getCaloriasPorUnidad(); // Por ejemplo
	    ingReceta.setCaloriasPorPorcion(nuevaCantidad * caloriasUnitarias);

	    ingredienteRecetaRepo.save(ingReceta);
	    
	    double total = receta.getIngredientes().stream()
	            .filter(IngredienteReceta::isActivo)
	            .mapToDouble(IngredienteReceta::getCaloriasPorPorcion)
	            .sum();

	        receta.setCaloriasTotales(total);
	        recetaRepo.save(receta);
	}
	public void reactivar(Long id) {
	    Receta receta = recetaRepo.findById(id).orElseThrow();
	    receta.setActivo(true);
	    recetaRepo.save(receta);
	}

	/*@Override
	public void agregarIngredienteAReceta(Long recetaId, Long ingredienteId, double cantidad, int calorias) {
		// TODO Auto-generated method stub
		
	}*/
	
	
}