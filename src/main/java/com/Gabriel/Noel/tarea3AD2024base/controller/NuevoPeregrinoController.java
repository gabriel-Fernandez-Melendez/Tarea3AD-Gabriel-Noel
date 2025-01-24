package com.Gabriel.Noel.tarea3AD2024base.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.Gabriel.Noel.tarea3AD2024base.modelo.Carnet;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Credenciales;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Parada;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Peregrino;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Usuarios;
import com.Gabriel.Noel.tarea3AD2024base.services.CarnetService;
import com.Gabriel.Noel.tarea3AD2024base.services.CredencialesService;
import com.Gabriel.Noel.tarea3AD2024base.services.ParadaService;
import com.Gabriel.Noel.tarea3AD2024base.services.PeregrinoService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
	private ChoiceBox<String> pais;

	@FXML
	private ComboBox<Parada> parada;

	@Autowired
	private CredencialesService credenciales_service;

	@Autowired
	private PeregrinoService peregrino_service;
	
	@Autowired
	private ParadaService parada_service;

	@Autowired
	private CarnetService carnet_service;
	
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
	
	//TENGO QUE METER EL METOD FXML RELATIVO AL  BOTON QUE GUARDA CASA COSA 
	@FXML
	private void GuardarNuevoPeregrino() {
		
	}

	// aqui iran los metodos que se implementen en los @fxml pero no tengan que
	// llevar la notacion (los relativos a la base de datos)
	public boolean GuardarNuevasCredenciales() {
		boolean val = false;
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
				Credenciales cred = new Credenciales();
				cred.setNombreUsuario(nombre_peregrino.getText());
				cred.setContraseñaUsuario(Contraseña.getText());
				cred.setTipo(Usuarios.Peregrino);
				credenciales_service.GuardarCredenciales(cred);
				val = true;
			}
		}
		return val;
	}
	
	private Peregrino GuardarPeregrino() {
		boolean val =false; // puede que esta variable no haga falta
		Peregrino per = new Peregrino();
		ArrayList<Peregrino> peregrinos =(ArrayList<Peregrino>) peregrino_service.ListaDePeregrinos();
		for(Peregrino p: peregrinos) {
			// hace falta hacer un  if por que tanto el nombre del peregrino como la parada o el pais se pueden repetir  			
			per.setNombre(nombre_peregrino.getText());
			per.setNacionalidad(pais.getValue());
			ArrayList<Parada> paradas =new ArrayList<Parada>();
			paradas.add(parada.getValue());
			
			}
		return per;
	}
	
	private void GuardarCarnet(Peregrino p,Parada par) {
		Carnet c= new Carnet();
		c.setDistancia(0);
		c.setFechaexp(LocalDate.now());
		c.setParadaInicial(par);
		carnet_service.GuardarCarnet(c);
	}

	private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
		Alert miAlerta = new Alert(tipo);
		miAlerta.setTitle(titulo);
		miAlerta.setContentText(mensaje);
		miAlerta.showAndWait();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		mostrar_contraseña.setVisible(false);
		/*
		 * parada =new ChoiceBox<>(); List<Parada> paradas = new ArrayList<Parada>();
		 * paradas = parada_service.ListaDeParadas(); // puede que solo sea posible con
		 * lista gnenerica for(Parada p :paradas) { System.out.println(p.getNombre()); }
		 * parada.setItems(FXCollections.observableArrayList(paradas));
		 */
		 // Imprimir datos para depuración

		List<Parada> paradas = new ArrayList<Parada>();
		
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

	}

}
