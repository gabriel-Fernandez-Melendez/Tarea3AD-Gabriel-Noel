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
	
	
	// Guardar la pàrada sellada (Objeto completo)
	public ParadaSellada guardarParadaSellada(ParadaSellada miParadaSellada)
	{
		return paradaSelladaRepository.save(miParadaSellada);
	}

//	 // Método para filtrar sellados por rango de fechas
//	public List<ParadaSellada> filtrarPorParadaYFechas(Long paradaId, LocalDate fechaInicio, LocalDate fechaFin) {
//	    return paradaSelladaRepository.findByParadaAndFechas(paradaId, fechaInicio, fechaFin);
//	}
	
	public List<ParadaSellada> filtrarPorParadaYFechas(Long paradaId, LocalDate fechaInicio, LocalDate fechaFin) {
	    String fechaInicioStr = fechaInicio.toString();
	    String fechaFinStr = fechaFin.toString();
	    
	    System.out.println("=== FILTRANDO PARADAS SELLADAS ===");
	    System.out.println("ID Parada: " + paradaId);
	    System.out.println("Fecha Inicio (String): " + fechaInicioStr);
	    System.out.println("Fecha Fin (String): " + fechaFinStr);

	    return paradaSelladaRepository.findByParadaAndFechas(paradaId, fechaInicio, fechaFin);
	}


	// Obtener todas las paradas selladas
	public List<ParadaSellada> obtenerTodasParadasSelladas() {
	    return paradaSelladaRepository.findAll();
	}
    
    
}
