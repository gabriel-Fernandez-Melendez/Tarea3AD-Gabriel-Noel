package com.Gabriel.Noel.tarea3AD2024base.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Gabriel.Noel.tarea3AD2024base.modelo.Estancia;
import com.Gabriel.Noel.tarea3AD2024base.modelo.ParadaSellada;
import com.Gabriel.Noel.tarea3AD2024base.repositorios.EstanciaRepository;

@Service
public class EstanciaService {

	@Autowired
	private EstanciaRepository estanciaRepository;
	
	public Estancia guardarEstancia(Estancia miEstancia)
	{
		return estanciaRepository.save(miEstancia);
	}

	
	public List<Map<String, Object>> obtenerEstanciasFiltradas(Long idParada, LocalDate fechaInicio, LocalDate fechaFin) {
	    List<Object[]> resultados = estanciaRepository.filtrarEstancias(idParada, fechaInicio, fechaFin);
	    List<Map<String, Object>> estancias = new ArrayList<>();

	    for (Object[] fila : resultados) {
	        Map<String, Object> estancia = new HashMap<>();
	        estancia.put("nombre", fila[0]);
	        estancia.put("nacionalidad", fila[1]);
	        estancia.put("seEstancio", fila[2]);
	        estancia.put("esVIP", fila[3]);
	        estancia.put("fechaParada", fila[4]);
	        estancias.add(estancia);
	    }

	    return estancias;
	}

	
}
