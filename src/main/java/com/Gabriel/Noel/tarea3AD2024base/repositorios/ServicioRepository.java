package com.Gabriel.Noel.tarea3AD2024base.repositorios;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.Gabriel.Noel.tarea3AD2024base.modelo.Servicio;
import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;

@Repository
public class ServicioRepository {

	ObjectContainer db = Db4oEmbedded.openFile("DB4O_Peregrinos.db4o");
	
	
	// Metodo para recoger todos los servicios
	public List<Servicio> recogerServicios()
	{
		 List<Servicio> misServicios = new ArrayList<>();

	        try 
	        {
	            List<Servicio> resultados = db.query(Servicio.class);
	            for(Servicio ser : resultados)
	            {
	            	misServicios.add(ser);
	            }
	        }
	        
	        catch(Exception e)
	        {
	        	System.out.println("Error"+ e.getMessage());
	        }
	        
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
	
	
	public void asignarParadasAServicio(Long servicioId, List<Long> nuevasParadas) {
	    try {
	        System.out.println("Buscando servicio con ID: " + servicioId);
	        
	        // Buscar el servicio por ID en la base de datos
	        List<Servicio> servicios = db.query(Servicio.class);
	        
	        for (Servicio servicio : servicios) 
	        {
	            if (servicio.getId().equals(servicioId)) 
	            {
	                
	                // Filtrar paradas nuevas para evitar duplicados
	                List<Long> nuevasParadasUnicas = new ArrayList<>();
	                
	                for (Long idParada : nuevasParadas) 
	                {
	                    if (!servicio.getIdParada().contains(idParada)) 
	                    {
	                        nuevasParadasUnicas.add(idParada);
	                    }
	                }

	                if (nuevasParadasUnicas.isEmpty()) 
	                {
	                    System.out.println("No hay nuevas paradas para agregar.");
	                    return;
	                }

	                // Agregar las nuevas paradas
	                servicio.getIdParada().addAll(nuevasParadasUnicas);
	                System.out.println("Paradas agregadas al servicio ID " + servicio.getId() + ": " + servicio.getIdParada());

	                // Guardar en DB4O
	                db.store(servicio);
	                db.commit();
	                System.out.println("Servicio actualizado y guardado en la BD.");

	                break;
	            }
	        }
	    } catch (Exception e) {
	        System.out.println("Error al asignar paradas al servicio: " + e.getMessage());
	    }
	}

	
	
	// Método para cerrar la conexión a DB4O cuando se cierra la sesión
    public void cerrarConexion() {
        if (db != null) {
            db.close();
            System.out.println("Conexión a DB4O cerrada correctamente.");
        }
	
    }
}
