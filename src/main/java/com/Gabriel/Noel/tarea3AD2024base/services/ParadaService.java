package com.Gabriel.Noel.tarea3AD2024base.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Gabriel.Noel.tarea3AD2024base.modelo.Parada;
import com.Gabriel.Noel.tarea3AD2024base.repositorios.ParadasRepository;

@Service
public class ParadaService {

	@Autowired
	private ParadasRepository paradasRepository;
	
	public Parada crearParada(Parada parada)
	{
		return paradasRepository.save(parada);
	}
	
	// Un metodo para encontrar una parada por ID
	public Parada obtenerParada(Long id)
	{
		Parada miParada = paradasRepository.findByIdUsuario(id);
		return miParada;
		
	}
	
	
	public List<Parada> listaParadas()
	{
		return paradasRepository.findAll();
	}
	
	
	
}
