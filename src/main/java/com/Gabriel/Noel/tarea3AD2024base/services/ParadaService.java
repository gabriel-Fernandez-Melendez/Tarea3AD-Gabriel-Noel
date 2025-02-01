package com.Gabriel.Noel.tarea3AD2024base.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Gabriel.Noel.tarea3AD2024base.modelo.Carnet;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Credenciales;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Parada;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Peregrino;
import com.Gabriel.Noel.tarea3AD2024base.repositorios.CarnetRepository;
import com.Gabriel.Noel.tarea3AD2024base.repositorios.ParadasRepository;
import com.Gabriel.Noel.tarea3AD2024base.repositorios.PeregrinoRepository;

@Service
public class ParadaService {

	@Autowired
	private PeregrinoRepository peregrinoBD;
	
	@Autowired
	private CarnetRepository carnetBD;
	
	@Autowired
	private ParadasRepository paradasRepository;
	

	
	
	/**
	 * Obtiene la lista de todos los peregrinos registrados en la BD
	 * @return
	 */
	public List<Peregrino> listaDePeregrino()
	{
		return peregrinoBD.findAll();
	}
	
	
	/**
	 * Guarda un Carnet en la BD
	 * @param miCarnet
	 * @return
	 */
	public Carnet guardarCarnet(Carnet miCarnet)
	{
		return carnetBD.save(miCarnet);
	}
	
	
	/**
	 * Obtiene una lista de todas las paradas de la BD
	 * @return
	 */
	public List<Parada> ListaDeParadas() {
		return paradasRepository.findAll();
	}
	
	
	
	/**
	 * Obtiene una parada en base a unas credenciales de un usuario
	 * en este caso para un (Responsable de Parada)
	 * @param miCredencial
	 * @return
	 */
	public Parada buscarParadaPorCredenciales(Credenciales miCredencial)
	{
		return paradasRepository.findByCredenciales(miCredencial);
	}
	
	
	
	
}
