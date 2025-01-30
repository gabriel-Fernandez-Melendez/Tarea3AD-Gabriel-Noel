package com.Gabriel.Noel.tarea3AD2024base.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.Gabriel.Noel.tarea3AD2024base.config.StageManager;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Carnet;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Credenciales;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Parada;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Peregrino;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Usuarios;
import com.Gabriel.Noel.tarea3AD2024base.services.CarnetService;
import com.Gabriel.Noel.tarea3AD2024base.services.CredencialesService;
import com.Gabriel.Noel.tarea3AD2024base.services.ParadaService;
import com.Gabriel.Noel.tarea3AD2024base.services.PeregrinoService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

@Controller
public class NuevoPeregrinoController implements Initializable {

	@FXML
	private PasswordField Contraseña;

	@FXML
	private TextField Nombre_login;

	@FXML
	private ImageView imagen;

	@FXML
	private Label mostrar_contraseña;

	@FXML
	private TextField nombre_peregrino;
	
	@FXML
	private TextField correo_peregrino;

	@FXML
	private ComboBox<String> pais;

	@FXML
	private ComboBox<Parada> parada;

	@FXML
	private Button guardar;

	@FXML
	private Button limpiar;

	@Autowired
	private CredencialesService credenciales_service;

	@Autowired
	private PeregrinoService peregrino_service;

	@Autowired
	private ParadaService parada_service;

	@Autowired
	private CarnetService carnet_service;
	@Lazy
	@Autowired
	private StageManager stageManager;

	// sin este campo erl introducir el carnet en el peregrino da problemas
	@PersistenceContext
	private EntityManager entityManager;

	@FXML
	public void AyudaJavaFX() {
		try {
			System.out.println("entro a la funcion de ayuda pero no cargo la ventana");
			// Crear un WebView para mostrar la ayuda
			WebView webView = new WebView();

			// Cargar el archivo HTML desde los recursos
			String url = getClass().getResource("/ayuda/Ayuda.html").toExternalForm();
			webView.getEngine().load(url);

			// Crear un nuevo Stage para la ventana de ayuda
			Stage helpStage = new Stage();
			helpStage.setTitle("Ayuda");

			// Crear una Scene con el WebView
			Scene helpScene = new Scene(webView, 600, 600);

			// Configurar la ventana
			helpStage.setScene(helpScene);

			// Bloquea la ventana principal mientras se muestra la ayuda
			helpStage.initModality(Modality.APPLICATION_MODAL);
			helpStage.setResizable(true);

			// Mostrar la ventana de ayuda
			helpStage.show();

		} catch (NullPointerException e) {
			// Manejar el caso en que el archivo de ayuda no se encuentra
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Archivo de Ayuda no encontrado");
			alert.setContentText("Por favor, verifica que el archivo 'help.html' esté en la ruta '/ayuda/help.html'.");
			alert.showAndWait();
			// esta linea en caso de que necesitemos detectar el origen del fallo atravez de
			// consola
			e.printStackTrace();
		}
	}

	@FXML
	private void ImagenPrecionada() {
		// imagen.setImage(img);
		System.out.println("entra al metodo de precionado");
		Image img = new Image(getClass().getResourceAsStream("/images/eye.png"));
		imagen.setImage(img);
		System.out.println("cargo");
		mostrar_contraseña.setText("Tu contraseña es: " + Contraseña.getText());
		mostrar_contraseña.setVisible(true);
	}

	@FXML
	private void Imagensoltada() {
		Image img = new Image(getClass().getResourceAsStream("/images/hide.png"));
		imagen.setImage(img);
		System.out.println("entra al metodo de soltado");
		mostrar_contraseña.setVisible(false);
	}

	// TENGO QUE METER EL METOD FXML RELATIVO AL BOTON QUE GUARDA CASA COSA
	@FXML
	private void GuardarNuevoPeregrino() {
		Credenciales cred = GuardarNuevasCredenciales();
		Carnet c = GuardarCarnet(parada.getValue());
		Carnet carnet_aux = carnet_service.BuscarPorId(c.getId()); // esta detached asi que tengo que buscarlo en																// funcion del retorno que me da la funcion
		GuardarPeregrino(cred, carnet_aux);
		mostrarAlerta("Peregrino añadido", "Puede entrar con sus credenciales de peregrino", AlertType.INFORMATION);
	}

	// aqui iran los metodos que se implementen en los @fxml pero no tengan que
	// llevar la notacion (los relativos a la base de datos)
	public Credenciales GuardarNuevasCredenciales() {
		Credenciales cred = new Credenciales();
		ArrayList<Credenciales> credenciales = (ArrayList<Credenciales>) credenciales_service.ListaDeCredenciales();
		for (Credenciales c : credenciales) {
			// no meter de momento el comprobar contraseña por que mientras tengan nombres
			// distintos no hace falta que las contraseñas sean diferentes
			if (Nombre_login.getText().equalsIgnoreCase(c.getNombreUsuario())) {
				mostrarAlerta("nombre de usuario invalido",
						"el nombre de usuario que ha introducido ya esta cojido, escoja uno diferente",
						AlertType.ERROR);
				// val esta a false ya
			} else {

				cred.setNombreUsuario(nombre_peregrino.getText());
				cred.setContraseñaUsuario(Contraseña.getText());
				cred.setCorreo_usuario(correo_peregrino.getText());
				cred.setTipo(Usuarios.Peregrino);
				cred.setCorreo_usuario(correo_peregrino.getText());
				credenciales_service.GuardarCredenciales(cred);
			}
		}
		return cred;
	}

	
	private void GuardarPeregrino(Credenciales c, Carnet car) {
		boolean val = false; // puede que esta variable no haga falta
		Peregrino per = new Peregrino();
		// NO hace falta hacer un if por que tanto el nombre del peregrino como la
		// parada o el pais se pueden repetir
		per.setNombre(nombre_peregrino.getText());
		per.setNacionalidad(pais.getValue());
		per.setCarnet(car);
		per.setCredenciales(c);
		Peregrino peregrino_nuevo = peregrino_service.GuardarPeregrino(per);

	}

	private Carnet GuardarCarnet(Parada par) {
		Carnet c = new Carnet();
		c.setDistancia(0);
		c.setFechaexp(LocalDate.now());
		c.setParadaInicial(par);
		c.setNvips(0);
		Carnet carnet = carnet_service.GuardarCarnet(c);
		return carnet;
	}

	private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
		Alert miAlerta = new Alert(tipo);
		miAlerta.setTitle(titulo);
		miAlerta.setContentText(mensaje);
		miAlerta.showAndWait();
	}

	// metodos para la lectura de los paises
	private static String extraer_datos_pais(String etiqueta, Element elem) {
		NodeList nodo = elem.getElementsByTagName(etiqueta).item(0).getChildNodes();
		Node valorNodo = (Node) nodo.item(0);
		return valorNodo.getNodeValue();
	}

	public static ArrayList<String> SeleccionDePais() {
		// tuve que cambiar el argumente del documento en relacion a la segunda entrega
		InputStream paises_xml = NuevoPeregrinoController.class.getClassLoader().getResourceAsStream("XML/paises.xml");
		ArrayList<String> paises = new ArrayList<>();
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document documento = builder.parse(paises_xml);
			documento.getDocumentElement().normalize();
			NodeList nodo = documento.getElementsByTagName("pais");
			for (int i = 0; i < nodo.getLength(); i++) {
				Node nodo_pais = nodo.item(i);
				if (nodo_pais.getNodeType() == Node.ELEMENT_NODE) {
					Element elemento = (Element) nodo_pais;// Obtenemos los elementos del nodo
					paises.add(extraer_datos_pais("nombre", elemento));
				}
			}
			// borrar para la entrega , es para comprobar que se cargan los paises
			// correctamente
			for (String s : paises) {
				System.out.println(s);
			}
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return paises;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		mostrar_contraseña.setText(" ");
		mostrar_contraseña.setVisible(false);
		// listas para cargar los datos
		List<Parada> paradas = new ArrayList<Parada>();
		List<String> paises = new ArrayList<String>();

		// carga de los datos de las paradas ern la vista
		paradas = parada_service.ListaDeParadas();
		if (paradas != null && !paradas.isEmpty()) {
			for (Parada p : paradas) {
				System.out.println(p.getNombre());
			}
		} else {
			System.out.println("La lista de paradas está vacía o es null.");
		}
		ObservableList<Parada> opciones = FXCollections.observableArrayList(paradas);
		parada.setItems(opciones);
		parada.setValue(paradas.get(0));

		// carga de los datos de los paises en la vista
		paises = SeleccionDePais();
		ObservableList<String> opciones_2 = FXCollections.observableArrayList(paises);
		pais.setItems(opciones_2);
		pais.setValue(paises.get(0));

		// inicializarlabel

	}

}
