package com.Gabriel.Noel.tarea3AD2024base.database;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Clase que gestiona la conexión con la base de datos ObjectDB.
 */
public class ObjectDBConnection {

    //EntityManager para manejar la conexión a ObjectDB
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("objectdb:db/OBDB_Peregrinos.odb");

    
    /**
     * Obtiene una instancia de `EntityManager` para operar en la base de datos.
     * 
     * @return `EntityManager` conectado a ObjectDB.
     */
    public static EntityManager getEntityManager() 
    {
        return emf.createEntityManager();
    }

    /**
     * Cierra la conexión con la base de datos ObjectDB.
     */
    public static void cerrarConexion() 
    {
        if (emf != null && emf.isOpen()) 
        {
            emf.close();
            System.out.println("Conexión con ObjectDB cerrada correctamente.");
        }
    }
}
