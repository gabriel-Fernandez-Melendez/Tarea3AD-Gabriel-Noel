package com.Gabriel.Noel.tarea3AD2024base.services;

import org.springframework.stereotype.Service;

import com.Gabriel.Noel.tarea3AD2024base.modelo.ConjuntoContratado;
import com.Gabriel.Noel.tarea3AD2024base.repositorios.ConjuntoContratadosRepository;

@Service // creo que no hace falta notar las clases
public class ConjuntoContradadoService {
	private ConjuntoContratadosRepository servicio_contratado;

	public ConjuntoContradadoService(ConjuntoContratadosRepository servicio_contratado) {
		this.servicio_contratado = servicio_contratado;
	}
	
	public void GuardarConjunto(ConjuntoContratado c) {
		servicio_contratado.guardarServicio(c);
	}
}
