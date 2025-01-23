package com.Gabriel.Noel.tarea3AD2024base.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Gabriel.Noel.tarea3AD2024base.modelo.Carnet;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Credenciales;


@Repository
public interface CredencialesRepository extends JpaRepository<Credenciales, Long> {

	 //de momento  no hay que declarar ningun metodo ya que estamos utilizando los implementados por jpa
	
	// Prueba para buscar el responsable de parada por nombre de usuario (NOEL)
	Credenciales findByNombreUsuario(String nombreUsuario);
}