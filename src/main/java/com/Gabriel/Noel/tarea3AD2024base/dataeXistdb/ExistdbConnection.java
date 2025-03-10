package com.Gabriel.Noel.tarea3AD2024base.dataeXistdb;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.CollectionManagementService;
import org.xmldb.api.modules.XMLResource;

import com.Gabriel.Noel.tarea3AD2024base.modelo.Carnet;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Estancia;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Parada;
import com.Gabriel.Noel.tarea3AD2024base.modelo.ParadaSellada;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Peregrino;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;

import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.exist.xmldb.EXistResource;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Element;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;


public class ExistdbConnection 
{

	
	// URI colección
	// Recordar Crear una coleccion dentro de la BD que se llame Paradas
	// cambiar puerto mirarlo
	String URI = "xmldb:exist://localhost:8080/exist/xmlrpc/db/Paradas";
	String User = "admin";
	String Pass = "";
	
	private Collection miColeccion;
	
	
	   public ExistdbConnection(String url) {
	        // Inicializamos la conexión a la colección "Paradas"
	       miColeccion = conectarBD(url);
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
	        
	    	}
	    	
	    	catch(Exception e)
	    	{
	    		System.out.println("Error al convertir el carnet en XML: "+ e.getMessage());
	    	}

	    	
	    	// devuelve el carnet en formato XML
			return miCarnetXML;        
	    }
	 
	 
	 // Metodo solo para esta clase, no para ninguna mas
	 private void guardarCarnetEnSubColectionParada_Original(String nombreParada, String nombreFichero, String contenidoXml)
	{
		 
		 
		 Collection subParada;
		 
		 try
		 {
			 // Ruta para la subParada ejemplo xmldb:exist://localhost:8080/exist/xmlrpc/db/Paradas/Gijon
			 // Uri: xmldb:exist://localhost:8080/exist/xmlrpc/db/Paradas
			 // Y le damos / nombreParada 
			 String rutaSubParada = URI + "/" + nombreParada+"/";
			 System.out.println(contenidoXml);
			 
			 // Conectarse a la nueva URI /Paradas/nombreParada (Se conecta correctamente)
			 subParada = conectarBD(rutaSubParada);			 
			 
			 Resource colec_aux=subParada.createResource(nombreFichero, "XMLResource");
			 
			 // Crear el recurso XML que contendrán estas subParadas
			 // Decimos primero el nombre del fichero y por ultimo el tipo que es
			 //XMLResource nuevoXML = (XMLResource)colec_aux; (puede que se tenga que encapsular aqui?)
			 
			 // Creamos el contenido dandole por parametro el XML que hemos creado
			 colec_aux.setContent(contenidoXml);
			 
			 // Guardamos el recurso en la subcoleccion
			 subParada.storeResource(colec_aux);		 
		 }
		 
		 catch(Exception e)
		 {
			 //porfa usa e.printStackTrace(); para poder ver toda la ruta del error
			 e.printStackTrace();
		 }
		 
	}
	 

	 public void guardarCarnetEnSubColectionParada(String nombreParada, String nombreFichero, String contenidoXml) {
		    try {
		        // Construir la ruta de la subcolección
		        String rutaSubColeccion = URI + "/" + nombreParada;
		        
		        Collection subColeccion = DatabaseManager.getCollection(rutaSubColeccion, User, Pass);
		        
		        if (subColeccion == null) {
		            System.out.println("No se pudo obtener la subcolección: " + nombreParada);
		            return;
		        }
		        
		        // Crear el recurso XML
		        XMLResource recurso = (XMLResource) subColeccion.createResource(nombreFichero, "XMLResource");
		        		        
		        // Establecer el MIME type para asegurar el procesamiento como XML
		        ((EXistResource) recurso).setMimeType("carnet/xml");
		        
		        // Asignar el contenido como String
		        recurso.setContent(contenidoXml);
		        
		        // Guardar el recurso en la subcolección
		        subColeccion.storeResource(recurso);
		        
		    } catch(Exception e) {
		        System.out.println("Error en guardarCarnetEnSubColeccionParada: " + e.getMessage());
		        e.printStackTrace();
		    }
		}
		
	 
		 // Inyecta el XML del carnet en la subcolección correspondiente a la parada
		    public void inyectarCarnet(String nombreParada, Carnet miCarnet, String xml) 
		    {
		        try 
		        {
		            String nombreFicheroXML = "Carnet_" + miCarnet.getId() + ".xml";
		            guardarCarnetEnSubColectionParada(nombreParada, nombreFicheroXML, xml);
		        } 
		        catch(Exception ex)
		        {
		        	System.out.println("Error al inyectar el carnet:");
		            ex.printStackTrace();
		        }
		    }
		    

		public static String exportarCarnet(Peregrino p,ArrayList<Parada> paradas_colec,ArrayList<ParadaSellada> selladas_colec) // PROBADO Y FUNCIONAL//hace falta poner el nombre de la parada
		{
			 StringWriter writer = new StringWriter();
			try {
				
				//pasar los datos en orden a la funcion desde los argumentos
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				DOMImplementation dom = db.getDOMImplementation();

				// Se crea elemento Padre carnet
				Document documento = dom.createDocument(null, "carnet", null);
				Element carnet = documento.getDocumentElement();

				documento.setXmlVersion("1.0");
				ProcessingInstruction ip = documento.createProcessingInstruction("xml-stylesheet",
						"type=\"text/xml\" href=\"test.xsl\"");

				Element id, fechaexp, expedidoen, peregrino, nombrePeregrino, nacionalidad, hoy, distanciatotal, paradas,
						parada, orden, nombreParada, region, estancias, estancia, idEstancia, fecha, paradaEstancia, vip;
				
				Text idValor, fechaexpValor, expedidoenValor, nombreValorPeregrino, nacionalidadValor, hoyValor,
						distanciatotalValor, ordenValor, nombreValor, regionValor, idEstanciaValor, fechaValor,
						paradaEstanciaValor, vipValor;

				// despues del las instrucciones xml-stylesheet etc etc y despues del elemento padre
				documento.insertBefore(ip, carnet);

				// Creamos el elemento id 
				id = documento.createElement("id");
				carnet.appendChild(id);
				idValor = documento.createTextNode(String.valueOf(p.getId())); //funciona cono el import correcto
				id.appendChild(idValor);

				// elemtn fecha expedicion ** Mirar como hacer la local date ** (Corregido, coge la fecha exp del objeto carnet que tiene el objeto peregrino)
				fechaexp = documento.createElement("fechaexp");
				carnet.appendChild(fechaexp);
				fechaexpValor = documento.createTextNode(String.valueOf(p.getCarnet().getFechaexp())); // cojo con get el objeto carnet del peregrino y cojo el att Fechaexp
				fechaexp.appendChild(fechaexpValor);

				// elemtno de expedido en X parada
				expedidoen = documento.createElement("expedidoen"); 
				carnet.appendChild(expedidoen);
				expedidoenValor = documento
						.createTextNode(String.valueOf(p.getCarnet().getParadaInicial()));
				expedidoen.appendChild(expedidoenValor);

				// creo el nodo peregrino del que colgaran todos los datos del peregrino
				peregrino = documento.createElement("peregrino");

				// elemento con el nombre del peregrino
				nombrePeregrino = documento.createElement("nombre");
				peregrino.appendChild(nombrePeregrino);
				nombreValorPeregrino = documento.createTextNode(String.valueOf(p.getNombre())); // recogemos el nombre del objeto con el metodo getNombre
				nombrePeregrino.appendChild(nombreValorPeregrino);

				// elemento con la nacionalidad del peregrino
				nacionalidad = documento.createElement("nacionalidad");
				peregrino.appendChild(nacionalidad);
				nacionalidadValor = documento.createTextNode(String.valueOf(p.getNacionalidad())); // Recogemos la nacionalidad del peregrino con su getNacionalidad
				nacionalidad.appendChild(nacionalidadValor);

				// Cerramos etiqueta del peregrino 
				carnet.appendChild(peregrino);

				
				// Creamos la etiqueta de hoy
				hoy = documento.createElement("hoy");
				carnet.appendChild(hoy);
				hoyValor = documento.createTextNode(String.valueOf(LocalDate.now())); // Recogemos la fecha de hoy con LocalDate.now()
				hoy.appendChild(hoyValor);

				// Creamos la etiqueta distanciatotal
				distanciatotal = documento.createElement("distanciatotal");
				carnet.appendChild(distanciatotal);
				distanciatotalValor = documento.createTextNode(String.valueOf(p.getCarnet().getDistancia())); // (MODIFICAR EN EL FUTURO) no hay distancia aun
				distanciatotal.appendChild(distanciatotalValor);
				// -- las paradas :D -- //
				paradas = documento.createElement("paradas");
				int num=1;
				for (ParadaSellada par:selladas_colec)  // recorremos con un For llamandoa  mi peregrino.getParadas.size para saber el tamaño de paradas que tiene este peregrino
				{
					if(p.getId()==par.getPeregrino().getId()) {
										parada = documento.createElement("parada"); // Creamos el elemento parada

					// etiqueta orden
					orden = documento.createElement("orden");
					parada.appendChild(orden);
					
					ordenValor = documento.createTextNode(String.valueOf(num)); // revisar que ye el orden ( revisado, es el orden en el que este hombre "peregrino" ha hecho las paradas primera parada tendra 1 y asisucesivo 2,3,4
					orden.appendChild(ordenValor);
					num++;
					// etiqueta nombre de la parada
					nombreParada = documento.createElement("nombre");
					parada.appendChild(nombreParada);
					nombreValor = documento.createTextNode(String.valueOf(par.getParada().getNombre())); // Recogemos el nombre, llamamos al objeto creado de parada 'p' y con .getNombre recogemos el nombre de la primera parada (que recorre el for)
					nombreParada.appendChild(nombreValor);

					// etiqueta region
					region = documento.createElement("region");
					parada.appendChild(region);
					regionValor = documento.createTextNode(String.valueOf(par.getParada().getRegion())); // La igual que el anterior recogemos la region de la parada a traves el .getRegion de la primera parada que recorremos con el for 
					region.appendChild(regionValor);

					paradas.appendChild(parada); // cierro etiqueta parada
					}


				}
				
				
				carnet.appendChild(paradas);

				estancias = documento.createElement("estancias");

				for (Estancia e:p.getEstancias()) // Recorremos con un for each la lista de estancias que tenga el peregrino con . getEstancia() que le damos por parametro a este metodo de exportar carnet
				{
					// Se crea la etiqueta de estancia 
					estancia = documento.createElement("estancia");

					// Creo la etiqueta id 
					idEstancia = documento.createElement("id");
					estancia.appendChild(idEstancia);
					idEstanciaValor = documento.createTextNode(String.valueOf(e.getId())); // recogo el id de la estancia con .getID en la que hago el foreach del peregrino
					idEstancia.appendChild(idEstanciaValor);

					// Creo la etiqueta fecha
					fecha = documento.createElement("fecha");
					estancia.appendChild(fecha);
					fechaValor = documento.createTextNode(String.valueOf(e.getFecha())); // recogo la fecha de la estancia con .getFecha 
					fecha.appendChild(fechaValor);

					// Creo la etiqueta parada
					paradaEstancia = documento.createElement("parada");
					estancia.appendChild(paradaEstancia);
					paradaEstanciaValor = documento.createTextNode(String.valueOf(e.getParada().getNombre())); // Recojo el nombre de la parada a traves del getNombre cogiendo el getParada
					paradaEstancia.appendChild(paradaEstanciaValor);

					if (e.isVip()) // en caso de que sea vip controlado con un boleano 
					{
						vip = documento.createElement("vip");
						estancia.appendChild(vip);
						vipValor = documento.createTextNode("true");
						vip.appendChild(vipValor);

					}
					else // en el caso de que no sea vip 
					{
						vip = documento.createElement("vip");
						estancia.appendChild(vip);
						vipValor = documento.createTextNode("false");
						vip.appendChild(vipValor);
					}

					estancias.appendChild(estancia); // cierro etiqueta estancia

				}

				carnet.appendChild(estancias); // cierro elemento padre

				Source fuente = new DOMSource(documento);

				TransformerFactory tf = TransformerFactory.newInstance();
		        Transformer transformer = tf.newTransformer();
		        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
		        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

		       
		        transformer.transform(new DOMSource(documento), new StreamResult(writer));
		        return writer.toString();
		        
			}

			catch (Exception e) {
				System.out.println("El error: " + e.getMessage());
			}
			return writer.toString();
		}
	    
	    
		
		
		public List<String> listarCarnetsDeParada(String nombreParada) {
		    List<String> listaFicheros = new ArrayList<>();
		    Collection subParada = null;
		    try {
		        String rutaSubParada = URI + "/" + nombreParada;  // "xmldb:exist://.../db/Paradas/Granda"
		        subParada = conectarBD(rutaSubParada);
		        if (subParada != null) {
		            String[] recursos = subParada.listResources();
		            if (recursos != null) {
		                for (String recurso : recursos) {
		                    listaFicheros.add(recurso);
		                }
		            }
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		        System.out.println("Error al listar carnets en la parada " + nombreParada + ": " + e.getMessage());
		    } finally {
		        // Cerrar la colección si se desea
		        if (subParada != null) {
		            try { subParada.close(); } catch (XMLDBException e) { /* ignore */ }
		        }
		    }
		    return listaFicheros;
		}


		public String obtenerContenidoCarnet(String nombreParada, String nombreFichero) {
		    Collection subParada = null;
		    try {
		        String rutaSubParada = URI + "/" + nombreParada;
		        subParada = conectarBD(rutaSubParada);
		        if (subParada != null) {
		            Resource res = subParada.getResource(nombreFichero);
		            if (res != null) {
		                Object rawContent = res.getContent();
		                if (rawContent instanceof String) {
		                    // Directamente un String
		                    return (String) rawContent;
		                } else if (rawContent instanceof byte[]) {
		                    // Es un array de bytes, lo convertimos a String
		                    byte[] bytes = (byte[]) rawContent;
		                    return new String(bytes, StandardCharsets.UTF_8);
		                } else {
		                    System.out.println("Contenido no es String ni byte[], sino: " + rawContent.getClass());
		                }
		            }
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		        System.out.println("No se pudo obtener el contenido XML de " + nombreFichero + ": " + e.getMessage());
		    } finally {
		        if (subParada != null) {
		            try { subParada.close(); } catch (XMLDBException e) { /* ignore */ }
		        }
		    }
		    return null;
		}


		

	
}

