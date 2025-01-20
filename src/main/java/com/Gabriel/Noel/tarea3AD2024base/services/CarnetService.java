package com.Gabriel.Noel.tarea3AD2024base.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Gabriel.Noel.tarea3AD2024base.modelo.Carnet;
import com.Gabriel.Noel.tarea3AD2024base.repositorios.CarnetRepository;

@Service
public class CarnetService {

	//declaramos un  objeto de tipo repository para operar sobre el 

	@Autowired
	private CarnetRepository carnetBD;
	
	//metodos de recuperacion de datos haciendo uso del repositorio del peregrino
	public Carnet GuardarCarnet(Carnet c) {
		return carnetBD.save(c);
	}
	
}
