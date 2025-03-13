package com.Gabriel.Noel.tarea3AD2024base.repositorios.jpa;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Gabriel.Noel.tarea3AD2024base.modelo.ParadaSellada;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Peregrino;

@Repository
public interface ParadasSelladasRepository extends JpaRepository<ParadaSellada, Long> {

	/**
	 * Busca todas las parada selladas en una parada especifica dentro de un rango
	 * de fechas indicado
	 * 
	 * @param paradaId
	 * @param fechaInicio
	 * @param fechaFin
	 * @return
	 */
	// COMPROBAR BIEN ANTES DE NADA
	@Query("SELECT ps FROM ParadaSellada ps WHERE ps.parada.id = :paradaId AND ps.fechaParada BETWEEN :fechaInicio AND :fechaFin")
	List<ParadaSellada> findByParadaAndFechas(
			@Param("paradaId") Long paradaId,
			@Param("fechaInicio") LocalDate fechaInicio, 
			@Param("fechaFin") LocalDate fechaFin);

	/**
	 * Verifica si un peregrino ha sellado en una parada en una fecha en especifico
	 * (Así evitar que pueda sellarse dos veces el mismo dia en la misma parada)
	 * 
	 * @param peregrinoId
	 * @param paradaId
	 * @param fecha
	 * @return
	 */
	@Query("SELECT COUNT(ps) > 0 FROM ParadaSellada ps WHERE ps.peregrino.id = :peregrinoId AND ps.parada.id = :paradaId AND ps.fechaParada = :fecha")
	Boolean existeSelladoEnFecha(
			@Param("peregrinoId") Long peregrinoId, 
			@Param("paradaId") Long paradaId,
			@Param("fecha") LocalDate fecha);

}
