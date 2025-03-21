 package com.Gabriel.Noel.tarea3AD2024base.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Gabriel.Noel.tarea3AD2024base.modelo.Carnet;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Credenciales;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Peregrino;
import com.Gabriel.Noel.tarea3AD2024base.repositorios.jpa.CarnetRepository;

@Service
public class CarnetService {

	//declaramos un  objeto de tipo repository para operar sobre el 

	@Autowired
	private CarnetRepository carnetBD;
	
	@Transactional
	public Carnet GuardarCarnet(Carnet c) {
		return carnetBD.save(c);
	}
	
	//metodo que permite buscar en funcion de un  id el carnet de ese peregrino(noel este metodo puede devolver nulo, si lo es pon lo como condicional para entrar en un do while)
	@Transactional
	public Carnet BuscarPorId(long id) {
		Carnet carnet = carnetBD.findById(id).orElse(null);
		if (carnet == null) {
			System.out.println("no se encontro ningun peregrino con ese id , intentelo de nuevo");
		}
		return carnet;
	}
	public List<Carnet> ListaDeCarnets() {
		return carnetBD.findAll();
	}
	
}
