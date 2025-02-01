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
	 * Obtiene una lista de estancias filtradas por (Parada) y (Rango de fechas)
	 * Los resultados que son devueltos en una lista de Mapas.
	 * 
	 * Cada mapa es una fila con Clave-Valor
	 * 
	 * @param idParada
	 * @param fechaInicio
	 * @param fechaFin
	 * @return
	 */
	public List<Map<String, Object>> obtenerEstanciasFiltradas(Long idParada, LocalDate fechaInicio, LocalDate fechaFin) 
	{
		// Se ejecuta la consulta en el repositorio y se obtienen los resultados en forma de lista de objetos.
	    List<Object[]> resultados = estanciaRepository.filtrarEstancias(idParada, fechaInicio, fechaFin);
	    
	    // Se crea una lista donde se almacenarán los mapas con los datos estructurados.
	    List<Map<String, Object>> estancias = new ArrayList<>();

	    /**
         * Cada fila de resultados es un array de objetos (Object[])
         * donde cada posición corresponde a un campo de la consulta SQL
         * (idPeregrino,nombre,nacionalidad,seEstancio,esVIP,fechaParada)
         * 
         * Se mapea cada fila a un Map<String, Object> en donde:
         * La clave (String) es el nombre del dato
         * El valor (Object) es el dato correspondiente (puede ser String, Long, Boolean...)
         */
	    for (Object[] fila : resultados) 
	    {
	        Map<String, Object> estancia = new HashMap<>();
	        estancia.put("idPeregrino", fila[0]); // Ahora incluye el ID del peregrino
	        estancia.put("nombre", fila[1]);
	        estancia.put("nacionalidad", fila[2]);
	        estancia.put("seEstancio", fila[3]); // Se maneja con true/False
	        estancia.put("esVIP", fila[4]);	// Se maneja con True/False
	        estancia.put("fechaParada", fila[5]);
	        
	        // Se añade el mapa con los datos a la lista
	        estancias.add(estancia);
	    }

	    return estancias;
	}
	
}
