package com.Gabriel.Noel.tarea3AD2024base.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Gabriel.Noel.tarea3AD2024base.modelo.Parada;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Peregrino;
import com.Gabriel.Noel.tarea3AD2024base.repositorios.ParadasRepository;
import com.Gabriel.Noel.tarea3AD2024base.repositorios.ParadasSelladasRepository;

@Service
public class ParadaService {

	@Autowired
	private ParadasRepository paradasRepository;

	
	@Autowired
	private ParadasSelladasRepository paradasSelladasRepository;

	// Metodo Crea una parada
	public Parada insertarParada(Parada parada) {
		// Validaciones antes de retornar la nueva parada
		try {
			if (parada.getNombre() == null || parada.getNombre().isEmpty()) {
				System.out.println("El nombre de parada no puede estar vacio");
			}

			if (paradasRepository.findByNombreIgnoreCase(parada.getNombre()) != null) {
				System.out.println("El nombre de la parada ya existe");
			}

		} catch (Exception e) {
			System.out.println("Error en el metodo crearParada");
		}

		return paradasRepository.save(parada);
	}

	// Metodo que obtiene y lista todas las paradas
	public List<Parada> obtenerParadas() {
		return paradasRepository.findAll();
	}

	// Metodo que devuelve la parada por id del responsable de su credencial
	public Parada obtenerParadaPorIdResponsable(Long idUsuario) {
		return paradasRepository.findById(idUsuario);
	}
	
	// Metodo que devuelve la parada por ID
//	public Parada obtenerParadaId(Long idParada)
//	{
//		return paradasRepository.findByIdParada(idParada);
//	}

	// Metodo para obtener la parada por id del peregrino
//	public Parada obtenerParadaPorIdPeregrino(Long idPeregrino)
//	{
//		return paradasRepository.findByIdPeregrino(idPeregrino);
//	}
	
	
//	public List<Peregrino> obtenerPeregrinosPorFecha (Long idParada, LocalDate fechaInicio, LocalDate fechaFin)
//	{
//		try
//		{
//			if (fechaInicio == null || fechaFin == null)
//			{
//				System.out.println("Los campos de fecha no pueden estar vacios");
//			}
//			
//			if (fechaFin.isBefore(fechaInicio))
//			{
//				System.out.println("La fecha de inicio es posterior a la fecha de fin");
//			}
//		}
//		catch(Exception e)
//		{
//			System.out.println("Error en el metodo obtenerPeregrinosPorFecha");
//		}
//		
//		return paradasSelladasRepository.findPeregrinosByParadaAndFechaBetween(idParada, fechaInicio, fechaFin);
//	}



}
