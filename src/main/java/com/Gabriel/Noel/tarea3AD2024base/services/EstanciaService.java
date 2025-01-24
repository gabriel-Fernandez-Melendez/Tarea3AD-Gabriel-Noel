package com.Gabriel.Noel.tarea3AD2024base.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Gabriel.Noel.tarea3AD2024base.modelo.Estancia;
import com.Gabriel.Noel.tarea3AD2024base.repositorios.EstanciaRepository;

@Service
public class EstanciaService {

	@Autowired
	private EstanciaRepository estanciaRepository;
	
	public Estancia guardarEstancia(Estancia miEstancia)
	{
		return estanciaRepository.save(miEstancia);
	}
	
	public boolean verificarEstancia(Long peregrinoId, Long paradaId) {
	    return estanciaRepository.existsByPeregrinoIdAndParadaId(peregrinoId, paradaId);
	}

	
	public boolean esVIPEnParada(Long peregrinoId, Long paradaId) {
	    Boolean esVIP = estanciaRepository.esVIPEnParada(peregrinoId, paradaId);
	    return esVIP != null && esVIP;
	}

}
