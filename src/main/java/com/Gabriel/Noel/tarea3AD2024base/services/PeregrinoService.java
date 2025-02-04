package com.Gabriel.Noel.tarea3AD2024base.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Gabriel.Noel.tarea3AD2024base.modelo.Credenciales;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Peregrino;
import com.Gabriel.Noel.tarea3AD2024base.repositorios.PeregrinoRepository;

@Service
public class PeregrinoService {

	// declaramos un objeto de tipo repository para operar sobre el
	@Autowired
	private PeregrinoRepository peregrinoBD;

	// metodos de recuperacion de datos haciendo uso del repositorio del
	// peregrino(tengo que probar que lo inserta sin id )
	@Transactional
	public Peregrino GuardarPeregrino(Peregrino p) {
		return peregrinoBD.save(p);
	}

	// posible error en el import por hacer uso de util y no del import de list
	// hibernate(este ye el metodo qu)
	public List<Peregrino> ListaDePeregrinos() {
		return peregrinoBD.findAll();
	}

	// metodo que permite buscar a un peregrino por id y informa al usuario en caso
	// de ser null
	public Peregrino BuscarPorId(long id) {
		Peregrino peregrino = peregrinoBD.findById(id).orElse(null);
		if (peregrino == null) {
			System.out.println("no se encontro ningun peregrino con ese id , intentelo de nuevo");
		}
		return peregrino;
	}
	@Transactional
	public Peregrino BuscarPorCredenciales(Credenciales cred) {
		Peregrino peregrino = peregrinoBD.findByCredenciales(cred);
		return peregrino;
	}

	// apartir de aqui implementare mas metodos si llega a ser necesario

}
