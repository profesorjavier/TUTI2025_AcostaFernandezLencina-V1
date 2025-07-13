package com.tutiveinteveinteveinticinco.acostafernandezlencina.servicios;

import com.tutiveinteveinteveinticinco.acostafernandezlencina.accesodatos.IPreparacionRepo;
import com.tutiveinteveinteveinticinco.acostafernandezlencina.entidades.Preparacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PreparacionServiceImpl implements PreparacionService {

    @Autowired
    private IPreparacionRepo preparacionRepo;
    
    
    

    @Override
    public void guardar(Preparacion preparacion) throws Exception {
        if (preparacion.getFecha().isAfter(LocalDate.now())) {
            throw new Exception("La fecha no puede ser futura.");
        }

        boolean yaExiste = preparacionRepo
                .findByFechaAndRecetaAndActivoTrue(preparacion.getFecha(), preparacion.getReceta())
                .isPresent();

        if (yaExiste) {
            throw new Exception("Ya existe una preparación de esa receta en esa fecha.");
        }

        preparacionRepo.save(preparacion);
    }

    @Override
    public void eliminar(Long id) {
        Preparacion p = preparacionRepo.findById(id).orElseThrow();
        p.setActivo(false);
        preparacionRepo.save(p);
    }

    @Override
    public List listarActivas() {
        return preparacionRepo.findByActivoTrue();
    }

    @Override
    public List<Preparacion> buscarPorRangoFechas(LocalDate desde, LocalDate hasta) {
        return preparacionRepo.findByFechaBetweenAndActivoTrue(desde, hasta);
    }

    @Override
    public Preparacion obtenerPorId(Long id) {
        return preparacionRepo.findById(id).orElse(null);
    }

	@Override
	public List<Preparacion> listarActivasConReceta() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Preparacion> buscarPorRangoFechasConReceta(LocalDate desde, LocalDate hasta) {
		// TODO Auto-generated method stub
		return null;
	}
}
