package com.Gabriel.Noel.tarea3AD2024base.dataeXistdb;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.CollectionManagementService;
import org.xmldb.api.modules.XMLResource;
import java.io.File;

import org.exist.xmldb.DatabaseInstanceManager;
import org.exist.xmldb.EXistResource;

public class ExistdbConnection {
	
	// Meter el main
	
	// URI colección
	// Recordar Crear una coleccion dentro de la BD que se llame Paradas
	String URI = "xmldb:exist://localhost:8080/exist/xmlrpc/db/Paradas";
	
	//Coleccion de Paradas
	Collection miColeccionParada = null;
	
	//Resource que es lo que contendra la coleccion de las paradas
	XMLResource misParadas = null;
	
	

}

