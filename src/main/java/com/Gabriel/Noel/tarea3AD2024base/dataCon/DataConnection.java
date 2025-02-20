package com.Gabriel.Noel.tarea3AD2024base.dataCon;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;

/**
 * Clase que maneja la conexión con la base de datos DB4O.
 * Implementa el patrón Singleton para asegurar una única instancia de la conexión.
 * También gestiona el cierre automático de la base de datos al cerrar la aplicación.
 */
public class DataConnection {

    // Instancia única de la conexión
    private static DataConnection INSTANCE = null;

    // Ruta del archivo de la base de datos DB4O
    private static final String PATH = "DB4O_Peregrinos.db4o";

    // Contenedor de objetos de DB4O (base de datos)
    private static ObjectContainer db;

    /**
     * Constructor privado para evitar instanciación externa.
     * Llama a `performConnection()` para abrir la base de datos.
     * Registra un hook para cerrar la base de datos al cerrar la aplicación.
     */
    private DataConnection() 
    {
        performConnection();
        
        // Asegura que la base de datos se cierre correctamente al cerrar la aplicación
        Runtime.getRuntime().addShutdownHook(new Thread(() -> closeConnection()));
    }

    /**
     * Crea la instancia única de DataConnection si aún no existe.
     */
    private static synchronized void createInstance() 
    {
        if (INSTANCE == null) {
            INSTANCE = new DataConnection();
        }
    }

    /**
     * Devuelve la instancia única del contenedor de objetos de DB4O.
     * Si la instancia no existe, la crea.
     *
     * @return
     */
    public static ObjectContainer getInstance() 
    {
        if (INSTANCE == null) 
        {
            createInstance();
        }
        return db;
    }

    /**
     * Abre la conexión con la base de datos DB4O si no está abierta.
     */
    private void performConnection() 
    {
        if (db == null || db.ext().isClosed()) 
        {
            db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), PATH);
            System.out.println("Base de datos DB4O abierta en: " + PATH);
        }
    }

    /**
     * Cierra la conexión con la base de datos DB4O si está abierta.
     */
    public void closeConnection() 
    {
        if (db != null && !db.ext().isClosed()) 
        {
            db.close();
            System.out.println("Base de datos DB4O cerrada correctamente.");
        }
    }
}
