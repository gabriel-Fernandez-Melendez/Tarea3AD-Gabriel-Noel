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
	

	
	
	// Metodo para devolver todos los peregrinos
	public List<Peregrino> listaDePeregrino()
	{
		return peregrinoBD.findAll();
	}
	
	// Metodo para guardar el Carnet
	public Carnet guardarCarnet(Carnet miCarnet)
	{
		return carnetBD.save(miCarnet);
	}
	
	// Metodo para obtener parada por id de responsable
	public Parada obtenerParadaPorResponsable(Long idUsuario) 
	{
        return paradasRepository.findByIdUsuario(idUsuario);
    }
	
	// Metodo que retorna todas las paradas
	public List<Parada> ListaDeParadas() {
		return paradasRepository.findAll();
	}
	
	// Encuentra el objeto parada por el id de las credenciales del usuario
	public Parada buscarParadaPorCredenciales(Credenciales miCredencial)
	{
		return paradasRepository.findByCredenciales(miCredencial);
	}
	
	
	
	
}
