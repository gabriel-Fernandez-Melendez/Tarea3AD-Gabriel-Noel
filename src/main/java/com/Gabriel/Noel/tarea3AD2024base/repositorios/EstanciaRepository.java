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

	
	// comprobar bien
//	@Query("SELECT e.vip FROM Estancia e WHERE e.peregrino.id = :peregrinoId AND e.parada.id = :paradaId")
//	boolean esVIPEnParada(@Param("peregrinoId") Long peregrinoId, @Param("paradaId") Long paradaId);
	
	
//	// FUNCIONAL QUE NO DEVUELVE NULL SI NO, EL TABLEVIEW ROMPE POR NO PODER TRABAJAR CON NULOS
//	@Query("SELECT CASE WHEN COUNT(e) > 0 THEN e.vip ELSE false END FROM Estancia e WHERE e.peregrino.id = :peregrinoId AND e.parada.id = :paradaId")
//	Boolean esVIPEnParada(@Param("peregrinoId") Long peregrinoId, @Param("paradaId") Long paradaId);
//	
//
//	
//	// COMPROBAR BIEN 
//	@Query("SELECT COUNT(e) > 0 FROM Estancia e WHERE e.peregrino.id = :peregrinoId AND e.parada.id = :paradaId")
//	boolean existsByPeregrinoIdAndParadaId(@Param("peregrinoId") Long peregrinoId, @Param("paradaId") Long paradaId);


	// Buscar estancias por peregrino
    List<Estancia> findByPeregrinoId(Long idPeregrino);

    // Buscar estancias por parada y fechas
    List<Estancia> findByParadaIdAndFechaBetween(Long idParada, LocalDate fechaInicio, LocalDate fechaFin);
    
    
    @Query("SELECT p.nombre, p.nacionalidad, " +
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
