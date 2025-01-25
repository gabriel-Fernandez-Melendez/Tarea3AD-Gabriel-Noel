package com.Gabriel.Noel.tarea3AD2024base.repositorios;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Gabriel.Noel.tarea3AD2024base.modelo.Estancia;


@Repository
public interface EstanciaRepository extends JpaRepository<Estancia, Long> {

	
	// comprobar bien
//	@Query("SELECT e.vip FROM Estancia e WHERE e.peregrino.id = :peregrinoId AND e.parada.id = :paradaId")
//	boolean esVIPEnParada(@Param("peregrinoId") Long peregrinoId, @Param("paradaId") Long paradaId);
	
	
	// FUNCIONAL QUE NO DEVUELVE NULL SI NO, EL TABLEVIEW ROMPE POR NO PODER TRABAJAR CON NULOS
	@Query("SELECT CASE WHEN COUNT(e) > 0 THEN e.vip ELSE false END FROM Estancia e WHERE e.peregrino.id = :peregrinoId AND e.parada.id = :paradaId")
	Boolean esVIPEnParada(@Param("peregrinoId") Long peregrinoId, @Param("paradaId") Long paradaId);


	
	// COMPROBAR BIEN 
	@Query("SELECT COUNT(e) > 0 FROM Estancia e WHERE e.peregrino.id = :peregrinoId AND e.parada.id = :paradaId")
	boolean existsByPeregrinoIdAndParadaId(@Param("peregrinoId") Long peregrinoId, @Param("paradaId") Long paradaId);

	// Buscar estancias por peregrino
    List<Estancia> findByPeregrinoId(Long idPeregrino);

    // Buscar estancias por parada y fechas
    List<Estancia> findByParadaIdAndFechaBetween(Long idParada, LocalDate fechaInicio, LocalDate fechaFin);
}
