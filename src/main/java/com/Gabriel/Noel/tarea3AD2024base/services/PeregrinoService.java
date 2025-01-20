package com.Gabriel.Noel.tarea3AD2024base.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Gabriel.Noel.tarea3AD2024base.modelo.Peregrino;
import com.Gabriel.Noel.tarea3AD2024base.repositorios.PeregrinoRepository;

@Service
public class PeregrinoService {

	//declaramos un  objeto de tipo repository para operar sobre el 
	@Autowired
	private PeregrinoRepository peregrinoBD;
	
	//metodos de recuperacion de datos haciendo uso del repositorio del peregrino
	public Peregrino GuardarPeregrino(Peregrino p) {
		return peregrinoBD.save(p);
	}
	//posible error en el import por hacer uso de util y no del import de list hibernate(este ye el metodo qu)
	public List<Peregrino> ListaDePeregrinos(){
		return peregrinoBD.findAll();
	}
	
	
}
