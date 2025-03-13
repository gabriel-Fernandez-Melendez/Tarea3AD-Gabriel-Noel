package com.Gabriel.Noel.tarea3AD2024base.repositorios.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Gabriel.Noel.tarea3AD2024base.modelo.Credenciales;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Parada;

@Repository
public interface ParadasRepository extends JpaRepository<Parada, Long> {

	/**
	 * Busca la parada asociada a una credencial
	 * pasandole como parametro el objeto credencial
	 * 
	 * @param miCredencial
	 * @return
	 */
	// PRUEBA PARA SABER SI FUNCIONA Buscar el objeto parada mediante el objeto Credenciales 
	Parada findByCredenciales(Credenciales miCredencial);
}
