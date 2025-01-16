package com.Gabriel.Noel.tarea3AD2024base.repositorios;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Gabriel.Noel.tarea3AD2024base.modelo.ParadaSellada;


@Repository
public interface ParadasSelladasRepository extends JpaRepository<ParadaSellada, Long> {

	 // Buscar paradas selladas por peregrino
    List<ParadaSellada> findByPeregrinoId(Long idPeregrino);

    // Buscar paradas selladas por parada
    List<ParadaSellada> findByParadaId(Long idParada);
	
	
}
