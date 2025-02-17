package com.Gabriel.Noel.tarea3AD2024base.repositorios;

import java.util.List;

import com.Gabriel.Noel.tarea3AD2024base.modelo.Servicio;
import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;

public class ServicioRepository {

	ObjectContainer db = Db4oEmbedded.openFile("DB4O_Peregrinos.db4o");
	
	
	// Metodo para recoger todos los servicios
	public List<Servicio> recogerServicios()
	{
		List<Servicio> misServicios = null;
		List<Servicio> resultados = db.query(Servicio.class);
		
		for (Servicio miServicio : resultados)
		{
			misServicios.add(miServicio);
		}
		
		db.close();
		return misServicios;	
	}
	
	
	// Metodo para guardar un servicio
	public void guardarServicio(Servicio miServicio) 
	{
		try
		{
			db.store(miServicio);
			db.commit();
			
		}
		catch(Exception e)
		{
			System.out.println("Error en el metodo guardarServicio()");
		}
	}
	
	
	
}
