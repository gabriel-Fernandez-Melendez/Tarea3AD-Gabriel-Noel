package com.Gabriel.Noel.tarea3AD2024base.repositorios;

import com.Gabriel.Noel.tarea3AD2024base.modelo.ConjuntoContratado;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Servicio;
import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;

public class ConjuntoContratadosRepository {
	 //habia que que quitar la palabra final del atrubito para que cerrar la conexion en una operacion no lo haga de forma permanente
	private ObjectContainer db = Db4oEmbedded.openFile("DB4O_Peregrinos.db4o");
	
	public void guardarServicio(ConjuntoContratado c) {
        try {
            db.store(c);
            db.commit();
            System.out.println("Servicio guardado en DB4O: " + c); //mensaje solo para el usuario
        } catch (Exception e) {
            System.out.println("Error al guardar el servicio: " + e.getMessage());
        }
    }
	
}
