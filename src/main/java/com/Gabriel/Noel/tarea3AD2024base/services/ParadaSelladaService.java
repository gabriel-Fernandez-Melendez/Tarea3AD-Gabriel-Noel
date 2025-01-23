package com.Gabriel.Noel.tarea3AD2024base.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Gabriel.Noel.tarea3AD2024base.modelo.ParadaSellada;
import com.Gabriel.Noel.tarea3AD2024base.repositorios.ParadasSelladasRepository;

@Service
public class ParadaSelladaService {

	@Autowired
	private ParadasSelladasRepository paradaSelladaRepository;
	
	
	// Guardar la pàrada sellada (Objeto completo)
	public ParadaSellada guardarParadaSellada(ParadaSellada miParadaSellada)
	{
		return paradaSelladaRepository.save(miParadaSellada);
	}

	
}
