package com.Gabriel.Noel.tarea3AD2024base.repositorios;


import com.Gabriel.Noel.tarea3AD2024base.database.ObjectDBConnection;
import com.Gabriel.Noel.tarea3AD2024base.modelo.EnvioACasa;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class EnvioACasaRepository {

	/**
	 * Metodo para guardar los envios en la base de datos dandole por parametro
	 * el objeto entero de Envio a Casa
	 * @param envio
	 */
	public void guardarEnvio(EnvioACasa envio) 
	{
        EntityManager em = ObjectDBConnection.getEntityManager();
        
        try 
        {
            em.getTransaction().begin();
            
            em.persist(envio);
            
            em.getTransaction().commit();
            
            System.out.println("Envío registrado en ObjectDB: " + envio);
            
        } 
        
        catch (Exception e) 
        {
            em.getTransaction().rollback();
            System.out.println("Error al guardar el envío: " + e.getMessage());
        } 
        
        // Mirar si no es mejor cerrar en "Cerrar conexion"
        finally 
        {
            em.close();
        }
    }
	
	
	/**
	 * Metodo que retorna todos los envios de la BD
	 * @return
	 */
	public List<EnvioACasa> obtenerTodosLosEnvios() 
	{
        EntityManager em = ObjectDBConnection.getEntityManager();
            
        TypedQuery<EnvioACasa> query = em.createQuery("SELECT e FROM EnvioACasa e", EnvioACasa.class);
        
        List<EnvioACasa> envios = query.getResultList();
        
        em.close();
        
        return envios;
    }
	
	
	/**
	 * Metodo que devuelve los envios de una parada en especifico
	 * @param idParada
	 * @return
	 */
	 public List<EnvioACasa> obtenerEnviosPorParada(Long idParada) 
	 {
	        EntityManager em = ObjectDBConnection.getEntityManager();
	        
	        TypedQuery<EnvioACasa> query = em.createQuery("SELECT e FROM EnvioACasa e WHERE e.idParada = :idParada", EnvioACasa.class);
	        
	        query.setParameter("idParada", idParada);
	        
	        List<EnvioACasa> envios = query.getResultList();
	        
	        em.close();
	        
	        return envios;
	    }
	
	 
	 
//	 public void eliminarEnvio(Long id) 
//	 {
//	        EntityManager em = ObjectDBConnection.getEntityManager();
//	        
//	        try 
//	        {
//	            em.getTransaction().begin();
//	            
//	            EnvioACasa envio = em.find(EnvioACasa.class, id);
//	            
//	            if (envio != null) 
//	            {
//	                em.remove(envio);
//	                System.out.println("Envío eliminado: " + envio);
//	            }
//	            
//	            em.getTransaction().commit();
//	            
//	        } 
//	        
//	        catch (Exception e) 
//	        {
//	            em.getTransaction().rollback();
//	            System.out.println("Error al eliminar el envío: " + e.getMessage());
//	        } 
//	        
//	        finally 
//	        {
//	            em.close();
//	        }
//	    }
}
