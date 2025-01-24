package com.Gabriel.Noel.tarea3AD2024base.repositorios;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Gabriel.Noel.tarea3AD2024base.modelo.ParadaSellada;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Peregrino;


@Repository
public interface ParadasSelladasRepository extends JpaRepository<ParadaSellada, Long> {

	 // Buscar paradas selladas por peregrino
    List<ParadaSellada> findByPeregrinoId(Long idPeregrino);

    // Buscar paradas selladas por parada
    List<ParadaSellada> findByParadaId(Long idParada);
    
    // Método para buscar sellados entre un rango de fechas
    List<ParadaSellada> findByFechaParadaBetween(LocalDate fechaInicio, LocalDate fechaFin);
	
    
    // Filtrar paradas selladas por fecha
    //List<Peregrino> findPeregrinosByParadaAndFechaBetween(Long idParada, LocalDate fechaInicio, LocalDate fechaFin);
	
}
