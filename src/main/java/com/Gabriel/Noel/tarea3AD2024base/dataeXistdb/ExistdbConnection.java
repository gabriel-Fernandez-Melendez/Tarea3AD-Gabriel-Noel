package com.Gabriel.Noel.tarea3AD2024base.dataeXistdb;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.CollectionManagementService;
import org.xmldb.api.modules.XMLResource;

import com.Gabriel.Noel.tarea3AD2024base.modelo.Carnet;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;

import java.io.File;
import java.io.StringWriter;

import org.exist.xmldb.DatabaseInstanceManager;
import org.exist.xmldb.EXistResource;

public class ExistdbConnection 
{

	
	// URI colección
	// Recordar Crear una coleccion dentro de la BD que se llame Paradas
	// cambiar puerto mirarlo
	String URI = "xmldb:exist://localhost:8080/exist/xmlrpc/db/Paradas";
	String User = "admin";
	String Pass = "";
	
	// Instancia unica Singleton
	 private static ExistdbConnection instancia;
	
	// Objeto Collection para
	private Collection coleccionParadas;
	
	
	   private ExistdbConnection() {
	        // Inicializamos la conexión a la colección "Paradas"
	        coleccionParadas = conectarBD(URI);
	    }
	

	   public static ExistdbConnection crearInstancia() {
	        if (instancia == null) {
	            instancia = new ExistdbConnection();
	        }
	        return instancia;
	    }
		
	// Conectar a la coleccion a traves del URI y devuelve un objeto de tipo Collection
	private Collection conectarBD(String URI)
	{	
		//Coleccion de Paradas
		Collection miColeccionParada = null;
		
		try
		{
				Class miClase = Class.forName("org.exist.xmldb.DatabaseImpl");
				Database database = (Database) miClase.newInstance();
			    database.setProperty("create-database", "true");
			    DatabaseManager.registerDatabase(database);
			    
			    //Conectar con la coleccion de existDB
			    miColeccionParada = DatabaseManager.getCollection(URI, User, Pass);
			    
			    
			    System.out.println("Conexion correcta");
			    
		}
		catch(Exception e)
		{
			System.out.println("Error al conectar a la BD existDB: "+e.getMessage());
		}
		
		return miColeccionParada;				
	}
	

	// Realizar un metodo Sigleton para coger la instancia y usarla para conectar a la BD ExistDB
	
	
	
	// Metodo para Crear una subColection que serán las nuevas paradas que se creen
	public void crearSubColectionParadas(String nombreParada)
	{
		
		Collection subParada = conectarBD(URI);
		
		try
		{
	
			CollectionManagementService mgtService = (CollectionManagementService) subParada.getService("CollectionManagementService", "1.0");
			
			
			// Creamos la subColleccion con el nombre de la nueva parada que registremos
			mgtService.createCollection(nombreParada);
			
			// Prueba por pantalla
			System.out.println("Coleccion creada: "+ nombreParada);
						
		}
		
		catch(Exception e)
		{
			System.out.println("Error al crear la Sub Colleccion de las paradas: "+ e.getMessage());
		}
	
	}
	
	
	// Metodo que convierte un objeto (en ete caso carnet) a XML con JAXB https://www.arquitecturajava.com/introduccion-java-jaxb/
	// Metodo que se va a usar en esta clase no en ninguna mas
	 private String convertirCarnetAXml(Carnet carnet)
	    {
	    	String miCarnetXML = null;
	    	
	    	try 
	    	{
	    	
	        JAXBContext contexto = JAXBContext.newInstance(Carnet.class);
	        
	        Marshaller marshaller = contexto.createMarshaller();
	        
	        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

	        // Crear un contenedor para el texto del carnet
	        StringWriter escritor = new StringWriter();
	        
	        // Se convierte el objeto Carnet en formato XML y lo metemos dentro del contenedor
	        marshaller.marshal(carnet, escritor);
	        
	        
	        miCarnetXML = escritor.toString();
	        
	        System.out.println("Carnet convertido a XML de forma correcta");
	        
	    	}
	    	
	    	catch(Exception e)
	    	{
	    		System.out.println("Error al convertir el carnet en XML: "+ e.getMessage());
	    	}

	    	
	    	// devuelve el carnet en formato XML
			return miCarnetXML;        
	    }
	 
	 
	 // Metodo solo para esta clase, no para ninguna mas
	 private void guardarCarnetEnSubColectionParada(String nombreParada, String nombreFichero, String contenidoXml)
	{
		 
		 Collection subParada;
		 
		 try
		 {
			 // Ruta para la subParada ejemplo xmldb:exist://localhost:8080/exist/xmlrpc/db/Paradas/Gijon
			 // Uri: xmldb:exist://localhost:8080/exist/xmlrpc/db/Paradas
			 // Y le damos / nombreParada 
			 String rutaSubParada = URI + "/" + nombreParada;
			 
			 
			 // Conectarse a la nueva URI /Paradas/nombreParada
			 subParada = conectarBD(rutaSubParada);
			 
			 System.out.println();
			 
			 Resource colec_aux=subParada.createResource(nombreFichero, "XMLResource");
			 
			 // Crear el recurso XML que contendrán estas subParadas
			 // Decimos primero el nombre del fichero y por ultimo el tipo que es
			 XMLResource nuevoXML = (XMLResource)colec_aux;
			 
			 // Creamos el contenido dandole por parametro el XML que hemos creado
			 nuevoXML.setContent(contenidoXml);
			 
			 // Guardamos el recurso en la subcoleccion
			 subParada.storeResource(nuevoXML);
			 
			 System.out.println("Carnet guardado en: "+rutaSubParada+ " de forma correcta");
			 
		 }
		 
		 catch(Exception e)
		 {
			 System.out.println("Error al guardar el carnet en la SubColeccion de Parada: "+ e.getMessage());
			 e.printStackTrace();
		 }
		 
	}

	 
	 // Metodo inyectar el carnet a existDB en la SubColeccion de Paradas
	 public void inyectarCarnet(String nombreParada, Carnet miCarnet)
	 {
		 try
		 {
			  
		 // Generar el xml a partir del objeto carnet
		 String miXMLCarnet = convertirCarnetAXml(miCarnet);
		 
		 // Dar nombre al xml
		 String nombreFicheroXML = "Carnet_"+miCarnet.getId()+".xml";
		 
		 
		 guardarCarnetEnSubColectionParada(nombreParada, nombreFicheroXML, miXMLCarnet);
		 
		 System.out.println("Carnet creado de forma exitosa");
		 
		 }
		 
		 catch(Exception e)
		 {
			 System.out.println("Error al crear el carnet y almacenarlo en ExistDB: "+ e.getMessage());
		 }
		 
		 
	 }

	    
	    
	    
		

	
}

