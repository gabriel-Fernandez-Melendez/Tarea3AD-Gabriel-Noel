package com.Gabriel.Noel.tarea3AD2024base.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Gabriel.Noel.tarea3AD2024base.modelo.ParadaSellada;
import com.Gabriel.Noel.tarea3AD2024base.repositorios.ParadasSelladasRepository;

@Service
public class ParadaSelladaService {

	@Autowired
	private ParadasSelladasRepository paradaSelladaRepository;
	
	/**
	 * Filtrado de las paradas selladas en base al id de la parada, y el rango de fechas
	 * @param paradaId
	 * @param fechaInicio
	 * @param fechaFin
	 * @return
	 */
	public List<ParadaSellada> filtrarPorParadaYFechas(Long paradaId, LocalDate fechaInicio, LocalDate fechaFin) 
	{
	    return paradaSelladaRepository.findByParadaAndFechas(paradaId, fechaInicio, fechaFin);
	}


	/**
	 * Obtiene una lista de todas las paradas selladas de la BD
	 * @return
	 */
	public List<ParadaSellada> obtenerTodasParadasSelladas() {
	    return paradaSelladaRepository.findAll();
	}
    
	
	/**
	 * Guarda un nuevo registro de parada sellada si no existe previamente para el mismo peregrino
	 * en la misma parada y fecha
	 * @param miParadaSellada
	 * @return
	 */
	 public ParadaSellada guardarParadaSellada(ParadaSellada miParadaSellada) 
	 {
		 	// Se recogen los datos de las paradas selladas (idPeregrino,idParada y Fecha)
	        Long peregrinoId = miParadaSellada.getPeregrino().getId();
	        Long paradaId = miParadaSellada.getParada().getId();
	        LocalDate fecha = miParadaSellada.getFechaParada();

	        // Validamos si ya existe un sellado para el pregrino en la misma parada y fecha
	        // si es el caso, devuelve null y no guarda el sellado para ese peregrino.
	        if (paradaSelladaRepository.existeSelladoEnFecha(peregrinoId, paradaId, fecha)) 
	        {
	            System.out.println("Ya existe un sellado para este peregrino en esta parada y fecha.");
	            return null; // No guardamos duplicados
	        }

	        System.out.println("No existe sellado previo. Procedemos a guardar.");
	        return paradaSelladaRepository.save(miParadaSellada);
	    }
}
