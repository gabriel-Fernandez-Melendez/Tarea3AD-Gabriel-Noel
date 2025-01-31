package com.Gabriel.Noel.tarea3AD2024base.controller;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.Source;
import javax.xml.transform.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;

import com.Gabriel.Noel.tarea3AD2024base.config.StageManager;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Carnet;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Credenciales;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Estancia;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Parada;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Peregrino;
import com.Gabriel.Noel.tarea3AD2024base.services.CarnetService;
import com.Gabriel.Noel.tarea3AD2024base.services.ParadaService;
import com.Gabriel.Noel.tarea3AD2024base.services.PeregrinoService;
import com.Gabriel.Noel.tarea3AD2024base.view.FxmlView;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


@Controller
public class ExportarCarnetXMLController implements Initializable {

	@FXML
	private Button botonFiltrarEstancias;

	// Añadido boton cerrar sesion (Noel)
	@FXML
	private Button botonCerrarSesion;

	@FXML
	private TableView<Carnet> tabla_carnet;

	@FXML
	private TableColumn<Carnet, Long> id_carnet;

	@FXML
	private TableColumn<Carnet, Long> distancia;

	@FXML
	private TableColumn<Carnet, LocalDate> fecha;

	@FXML
	private TableColumn<Carnet, Long> estancians_vip;

	@FXML
	private TableColumn<Carnet, Parada> parada;

	@FXML
	private void boton() {
		System.out.println("prueba");
	}
	@Lazy
	@Autowired
	private CarnetService carnet_service;
	@Lazy
	@Autowired
	private PeregrinoService peregrino_service;
	@Lazy
	@Autowired
	private ParadaService parada_service;

	@Lazy
	@Autowired
	private StageManager stageManager;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// Credenciales cred= CredencialesController.Credenciales_usuario;
		// prueba de que llegan los datos
		// System.out.println(cred.toString());
		// colPeregrinoID.setCellValueFactory(new PropertyValueFactory<>("id"));
		// colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		// colNacionalidad.setCellValueFactory(new
		// PropertyValueFactory<>("nacionalidad"));
		id_carnet.setCellValueFactory(new PropertyValueFactory<>("id"));
		distancia.setCellValueFactory(new PropertyValueFactory<>("Distancia"));
		fecha.setCellValueFactory(new PropertyValueFactory<>("fechaexp"));
		estancians_vip.setCellValueFactory(new PropertyValueFactory<>("Nvips"));
		parada.setCellValueFactory(new PropertyValueFactory<>("ParadaInicial"));
		// saco el dato del objeto estatico de credenciales
		Peregrino per = peregrino_service.BuscarPorCredenciales(CredencialesController.getCredenciales());

		ObservableList<Carnet> lista = FXCollections.observableArrayList(per.getCarnet());
		System.out.println(per.getCarnet().toString() + "cargo ? ");
		tabla_carnet.setItems(lista);

	}
	
	@FXML
	public void ExportarXML() {
		Credenciales c =CredencialesController.Credenciales_usuario;
		Peregrino p=peregrino_service.BuscarPorCredenciales(c);
		ArrayList<Parada> par = (ArrayList<Parada>) parada_service.ListaDeParadas();
		exportarCarnet(p, par);
		mostrarAlerta("Exportado", "tiene el XML en la carpeta de recursos del proyecto", AlertType.INFORMATION);
		
	}
	
	private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
		Alert miAlerta = new Alert(tipo);
		miAlerta.setTitle(titulo);
		miAlerta.setContentText(mensaje);
		miAlerta.showAndWait();
	}
	
	@FXML
	public void Exportar() {
		parada_service.ListaDeParadas();
	}

	public static void exportarCarnet(Peregrino p,ArrayList<Parada> paradas_colec) // PROBADO Y FUNCIONAL//hace falta poner el nombre de la parada
	{
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
		
			for (Parada par:paradas_colec)  // recorremos con un For llamandoa  mi peregrino.getParadas.size para saber el tamaño de paradas que tiene este peregrino
			{
				Parada parada_aux = par;// Creo un objeto parada 'p' y cogemos la primera parada que es con la que recorremos el for con ese ultimo .get(a); *entender bien*
				parada = documento.createElement("parada"); // Creamos el elemento parada

				// etiqueta orden
				orden = documento.createElement("orden");
				parada.appendChild(orden);
				int num=1;
				ordenValor = documento.createTextNode(String.valueOf(num)); // revisar que ye el orden ( revisado, es el orden en el que este hombre "peregrino" ha hecho las paradas primera parada tendra 1 y asisucesivo 2,3,4
				orden.appendChild(ordenValor);
				num++;
				// etiqueta nombre de la parada
				nombreParada = documento.createElement("nombre");
				parada.appendChild(nombreParada);
				nombreValor = documento.createTextNode(String.valueOf(par.getNombre())); // Recogemos el nombre, llamamos al objeto creado de parada 'p' y con .getNombre recogemos el nombre de la primera parada (que recorre el for)
				nombreParada.appendChild(nombreValor);

				// etiqueta region
				region = documento.createElement("region");
				parada.appendChild(region);
				regionValor = documento.createTextNode(String.valueOf(par.getRegion())); // La igual que el anterior recogemos la region de la parada a traves el .getRegion de la primera parada que recorremos con el for 
				region.appendChild(regionValor);

				paradas.appendChild(parada); // cierro etiqueta parada
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

			System.out.println("----- Generando el fichero XML");
			Source fuente = new DOMSource(documento);

			File fichero = new File("src/main/resources/" + p.getNombre() + ".xml");

			Result resultado = new StreamResult(fichero);

			TransformerFactory fabricaTransformador = TransformerFactory.newInstance();

			Transformer transformador = fabricaTransformador.newTransformer();

			transformador.transform(fuente, resultado);

			System.out.println("Carnet exportado correctamente");
		}

		catch (Exception e) {
			System.out.println("El error: " + e.getMessage());
		}
	}

	// Añadida la funcionalidad volver al login (Noel)
	@FXML
	private void cerrarSesion() {
		stageManager.switchScene(FxmlView.LOGIN);
	}

}
