package com.Gabriel.Noel.tarea3AD2024base.services;


import org.springframework.stereotype.Service;

import com.Gabriel.Noel.tarea3AD2024base.modelo.ConjuntoContratado;
import com.Gabriel.Noel.tarea3AD2024base.repositorios.jpa.ConjuntoContratadosRepository;
import com.Gabriel.Noel.tarea3AD2024base.repositorios.jpa.ServicioRepository;


@Service
public class ConjuntoContradadoService {
	
	  private ConjuntoContratadosRepository conjunto_rep;
	  
	  public ConjuntoContradadoService(ConjuntoContratadosRepository c ) {
	        this.conjunto_rep = c;
	    }
	  public void GuardarConjunto(ConjuntoContratado c) {
	 conjunto_rep.guardarServicio(c); }
	 
}
