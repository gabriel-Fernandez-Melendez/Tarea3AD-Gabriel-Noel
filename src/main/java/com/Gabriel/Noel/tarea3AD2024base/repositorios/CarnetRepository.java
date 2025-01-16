package com.Gabriel.Noel.tarea3AD2024base.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Gabriel.Noel.tarea3AD2024base.modelo.Carnet;


@Repository
public interface CarnetRepository extends JpaRepository<Carnet, Long> {

	 //de momento  no hay que declarar ningun metodo ya que estamos utilizando los implementados por jpa
}