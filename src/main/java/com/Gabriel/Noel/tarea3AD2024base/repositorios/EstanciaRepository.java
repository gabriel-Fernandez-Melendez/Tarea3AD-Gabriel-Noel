package com.Gabriel.Noel.tarea3AD2024base.repositorios;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Gabriel.Noel.tarea3AD2024base.controller.PeregrinoTablaController;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Estancia;



@Repository
public interface EstanciaRepository extends JpaRepository<Estancia, Long> {



	/**
	 * La query lo que hace es filtrar peregrinos que han sellado su carnet en una parada especifica
	 * en un rango de fechas.
	 * 
	 * Une las tablas ParadasSelladas y Estancia para saber si se hospedó y si fue VIP.
	 * 
	 * Devuelve los datos ecapsulados con la clase PeregrinoTabla
	 * 
	 * @param idParada
	 * @param fechaInicio
	 * @param fechaFin
	 * @return
	 */
	//todo review
	@Query("SELECT new com.Gabriel.Noel.tarea3AD2024base.controller.PeregrinoTablaController( " +
		       "p.id, " +
		       "p.nombre, " +
		       "ps.fechaParada, " +  
		       "CASE WHEN e.id IS NOT NULL THEN 'Sí' ELSE 'No' END, " +
		       "CASE WHEN e.vip = true THEN 'Sí' ELSE 'No' END) " +
		       "FROM Peregrino p " +
		       "LEFT JOIN p.paradasSelladas ps ON ps.peregrino.id = p.id " +
		       "LEFT JOIN Estancia e ON e.peregrino.id = p.id AND e.parada.id = ps.parada.id AND e.fecha = ps.fechaParada " +
		       "WHERE ps.parada.id = :idParada " +
		       "AND ps.fechaParada BETWEEN :fechaInicio AND :fechaFin")
		List<PeregrinoTablaController> filtrarEstancias(@Param("idParada") Long idParada,
		                                      @Param("fechaInicio") LocalDate fechaInicio,
		                                      @Param("fechaFin") LocalDate fechaFin);

}
