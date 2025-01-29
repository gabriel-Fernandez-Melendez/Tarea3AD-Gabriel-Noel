package com.Gabriel.Noel.tarea3AD2024base.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Gabriel.Noel.tarea3AD2024base.modelo.Credenciales;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Parada;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Peregrino;


@Repository
public interface PeregrinoRepository extends JpaRepository<Peregrino, Long> {

 //de momento  no hay que declarar ningun metodo ya que estamos utilizando los implementados por jpa
	Peregrino findByCredenciales(Credenciales id);
	
}