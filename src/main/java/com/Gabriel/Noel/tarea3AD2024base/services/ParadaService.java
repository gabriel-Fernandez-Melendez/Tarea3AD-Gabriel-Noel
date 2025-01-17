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
	
	
	// Metodo Crea una parada
	public Parada crearParada(Parada parada) throws Exception
	{
		//Validaciones antes de retornar la nueva parada
		
		if(parada.getNombre() == null || parada.getNombre().isEmpty())
		{
			throw new Exception("El nombre de parada no puede estar vacio");
		}
		
		if(paradasRepository.findByNombre(parada.getNombre()) != null)
		{
			throw new Exception("El nombre de la parada ya existe");
		}
		
		return paradasRepository.save(parada);
	}
	
	// Metodo que obtiene y lista todas las paradas
	public List<Parada> listaParadas()
	{
		return paradasRepository.findAll();
	}
	
	// Metodo que devuelve la parada por id del responsable de su credencial
	public Parada obtenerParadaPorResponsable(Long idUsuario)
	{
		return paradasRepository.findByIdUsuario(idUsuario);
	}
	
	// Metodo para obtener la parada por el nombre
	public Parada obtenerParadaPorNombre (String nombre)
	{
		return paradasRepository.findByNombre(nombre);
	}
	
	
	
}
