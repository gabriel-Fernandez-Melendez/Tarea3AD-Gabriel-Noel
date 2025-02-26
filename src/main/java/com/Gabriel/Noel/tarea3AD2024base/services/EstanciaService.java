package com.Gabriel.Noel.tarea3AD2024base.services;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Gabriel.Noel.tarea3AD2024base.controller.PeregrinoTablaController;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Estancia;
import com.Gabriel.Noel.tarea3AD2024base.repositorios.EstanciaRepository;

@Service
public class EstanciaService {

	@Autowired
	private EstanciaRepository estanciaRepository;
	
	
	/**
	 * Guarda la estancia en la Base de Datos
	 * @param miEstancia
	 * @return
	 */
	public Estancia guardarEstancia(Estancia miEstancia)
	{
		return estanciaRepository.save(miEstancia);
	}

	 /**
     * Obtiene una lista de estancias filtradas por parada y rango de fechas.
     * @param idParada ID de la parada
     * @param fechaInicio Fecha de inicio del filtro
     * @param fechaFin Fecha de fin del filtro
     * @return Lista de PeregrinoTabla con los datos listos para mostrar en la tabla
     */
    public List<PeregrinoTablaController> obtenerEstanciasFiltradas(Long idParada, LocalDate fechaInicio, LocalDate fechaFin) 
    {
        return estanciaRepository.filtrarEstancias(idParada, fechaInicio, fechaFin);
    }

	
}
