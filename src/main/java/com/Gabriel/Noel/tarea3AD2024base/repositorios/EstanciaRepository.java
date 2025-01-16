package com.Gabriel.Noel.tarea3AD2024base.repositorios;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Gabriel.Noel.tarea3AD2024base.modelo.Estancia;


@Repository
public interface EstanciaRepository extends JpaRepository<Estancia, Long> {

	// Buscar estancias por peregrino
    List<Estancia> findByPeregrinoId(Long idPeregrino);

    // Buscar estancias por parada y fechas
    List<Estancia> findByParadaIdAndFechaBetween(Long idParada, LocalDate fechaInicio, LocalDate fechaFin);
}
