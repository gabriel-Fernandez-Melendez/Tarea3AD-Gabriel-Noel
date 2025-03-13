package com.Gabriel.Noel.tarea3AD2024base.repositorios.jpa;

import org.springframework.stereotype.Repository;

import com.Gabriel.Noel.tarea3AD2024base.dataCon.DataConnection;
import com.Gabriel.Noel.tarea3AD2024base.modelo.ConjuntoContratado;
import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;

@Repository
public class ConjuntoContratadosRepository {

	private ObjectContainer db;

	public ConjuntoContratadosRepository() {
		this.db = DataConnection.getInstance();
	}

	public void guardarServicio(ConjuntoContratado c) {
		try {
			db.store(c);
			db.commit();
			System.out.println("Servicio guardado en DB4O: " + c);

		} catch (Exception e) {
			System.out.println("Error al guardar el servicio: " + e.getMessage());
		}
	}

}
