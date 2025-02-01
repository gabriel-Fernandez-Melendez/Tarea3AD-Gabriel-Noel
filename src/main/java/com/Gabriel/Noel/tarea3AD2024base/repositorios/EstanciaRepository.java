package com.Gabriel.Noel.tarea3AD2024base.repositorios;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Gabriel.Noel.tarea3AD2024base.modelo.Estancia;
import com.Gabriel.Noel.tarea3AD2024base.modelo.ParadaSellada;

@Repository
public interface EstanciaRepository extends JpaRepository<Estancia, Long> {

    	/**
    	 * Filtra las estancias en una parada dentro de un rango de fecha,
    	 * luego obtiene la informacion del peregrino (ID,Nombre,Nacionalidad,Se Hospeda, SiEsVIP, y la Fecha de Sellado)
    	 * 
    	 * @param idParada donde se filtran las estancias
    	 * @param fechaInicio
    	 * @param fechaFin
    	 * @return devuelve todo, una lista de obetos con la informacion completa del peregrino
    	 */
    	@Query("SELECT p.id, p.nombre, p.nacionalidad, " +
    	       "CASE WHEN e.id IS NOT NULL THEN true ELSE false END AS seEstancio, " +
    	       "CASE WHEN e.vip = true THEN true ELSE false END AS esVIP, " +
    	       "ps.fechaParada " +
    	       "FROM Peregrino p " +
    	       "LEFT JOIN p.paradasSelladas ps ON ps.peregrino.id = p.id " +
    	       "LEFT JOIN Estancia e ON e.peregrino.id = p.id AND e.parada.id = ps.parada.id AND e.fecha = ps.fechaParada " +
    	       "WHERE ps.parada.id = :idParada " +
    	       "AND ps.fechaParada BETWEEN :fechaInicio AND :fechaFin")
    	List<Object[]> filtrarEstancias(@Param("idParada") Long idParada,
    	                                @Param("fechaInicio") LocalDate fechaInicio,
    	                                @Param("fechaFin") LocalDate fechaFin);

    	
}
