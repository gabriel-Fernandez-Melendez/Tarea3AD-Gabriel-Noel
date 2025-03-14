package com.Gabriel.Noel.tarea3AD2024base.controller;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.Gabriel.Noel.tarea3AD2024base.config.StageManager;
import com.Gabriel.Noel.tarea3AD2024base.dataeXistdb.ExistdbConnection;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Credenciales;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Parada;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Usuarios;
import com.Gabriel.Noel.tarea3AD2024base.services.CredencialesService;
import com.Gabriel.Noel.tarea3AD2024base.services.ParadaService;
import com.Gabriel.Noel.tarea3AD2024base.view.FxmlView;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

@Controller
public class NuevaParadaController implements Initializable {
	
	private ExistdbConnection EXBD ; //inicializar al usar

	@FXML
	private TextField nombre_login;

	@FXML
	private PasswordField contraseña_login;

	@FXML
	private MenuItem menusalir;

	@FXML
	private TextField nombre_responsable;

	@FXML
	private TextField nombre_parada;

	@FXML
	private TextField correo_usuario;

	@FXML
	private TextField nombre_region;
	
	@FXML
	private Button botonBackupCarnet;

	@FXML
	private Button boton_guardar;

	@FXML
	private Button limpiarcampos;

	@FXML
	private Button botonCerrarSesion;

	@FXML
	private Button botonServicio;

	@FXML
	private Label mostrar_contraseña;

	@FXML
	private ImageView imagen;

	@Autowired
	private CredencialesService credenciales_service;

	@Autowired
	private ParadaService parada_service;

	@Lazy
	@Autowired
	private StageManager stageManager;

	@PersistenceContext
	private EntityManager entityManager;

	// metodo que tiene que ver con la insercion de datos
	// OJO no tiene por que ser null cambiar si hace falta
	// este metodo ya lo habia creado en el nuevo peregrino y puede ser reutilizado
	private Credenciales GuardarNuevasCredenciales() {
		Credenciales cred = new Credenciales();
		ArrayList<Credenciales> credenciales = (ArrayList<Credenciales>) credenciales_service.ListaDeCredenciales();
		for (Credenciales c : credenciales) {
			// no meter de momento el comprobar contraseña por que mientras tengan nombres
			// distintos no hace falta que las contraseñas sean diferentes
			if (nombre_login.getText().equalsIgnoreCase(c.getNombreUsuario())) {
				mostrarAlerta("nombre de usuario invalido",
						"el nombre de usuario que ha introducido ya esta cojido, escoja uno diferente",
						AlertType.ERROR);
				// val esta a false ya
			} else {

				cred.setNombreUsuario(nombre_login.getText());
				cred.setContraseñaUsuario(contraseña_login.getText());
				cred.setTipo(Usuarios.Responsable_Parada);
				cred.setCorreo_usuario(correo_usuario.getText());
				cred = credenciales_service.GuardarCredenciales(cred);
			}
		}
		return cred;
	}

	private void GuardarParada(Credenciales cred) {
		Parada p = new Parada();
		p.setNombre(nombre_parada.getText());
		p.setRegion(nombre_region.getText().charAt(0)); // asi solo pillo el primer caracter que introdice (lo
														// controlare en el futuro)
		p.setResponsable(nombre_responsable.getText());
		p.setCredenciales(cred);
		parada_service.guardarParada(p);
		
		// Aqui iria el metodo para crear la subcoleccion de las paradas
		// Y crearia la subColeccion con el nombre de la parada
		EXBD=new ExistdbConnection("xmldb:exist://localhost:8080/exist/xmlrpc/db/Paradas");
		EXBD.crearSubColectionParadas(p.getNombre());

	}

	private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
		Alert miAlerta = new Alert(tipo);
		miAlerta.setTitle(titulo);
		miAlerta.setContentText(mensaje);
		miAlerta.showAndWait();
	}

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

	// metodos para guardar la parada y las credenciales del usuario
	@FXML
	public void GuardarCredencialesResponsableParada() {

		if (nombre_login.getText().isEmpty() || nombre_login.getText().contains(" ")) {
			mostrarAlerta("Nombre no valido", "no puede tener espacios blancos el nombre", AlertType.ERROR);
			return;
		}
		
		
		
		if (contraseña_login.getText().contains(" ") || contraseña_login.getText().isEmpty()) {
			mostrarAlerta("contraseña no valida", "no puede tener espacios blancos la contraseña", AlertType.ERROR);
			return;
		}
		if (!correo_usuario.getText().matches(
				"(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")) {
			mostrarAlerta("correo no valida", "falta el @", AlertType.ERROR);
			return;
		}
		if (!nombre_region.getText().matches("^[a-zA-Z]$")) {
			mostrarAlerta("region cago en ros", "la region solo puede ser un caracter", AlertType.ERROR);
			return;
		}
		ArrayList<Parada> paradas = new ArrayList<Parada>();
		paradas =(ArrayList<Parada>) parada_service.ListaDeParadas();
		
		for (Parada miParada : paradas)
		{
			if(miParada.getCredenciales().getNombreUsuario().equals(nombre_login.getText()))
			{
				mostrarAlerta("Nombre no valido", "El nombre ya existe en la BD y está registrado", AlertType.ERROR);
				return;
			}
		}
		
		
		for(Parada p : paradas	) 
		{
			if(p.getNombre().equalsIgnoreCase(nombre_parada.getText())) {
				mostrarAlerta("nombre de parada existente", "tiene que escojer un  nombre que no exista en la parada", AlertType.ERROR);
				return;
			}
		}
		
		Credenciales c = GuardarNuevasCredenciales();
		
		GuardarParada(c);
		
		mostrarAlerta("Nueva parada", "A guardado la nueva parada con sus responsable correspondiente",AlertType.INFORMATION);
	}

	// Metodo para setear todo a null y los campos quedarian vacios (Noel)
	@FXML
	public void LimpiarCampos() {
		nombre_login.setText(null);
		correo_usuario.setText(null);
		contraseña_login.setText(null);
		nombre_responsable.setText(null);
		nombre_parada.setText(null);
		nombre_region.setText(null);
	}

	// reutilizo los metodos creados en nuero peregrino para mostrar la contraseña
	// al usuario
	@FXML
	private void ImagenPrecionada() {
		System.out.println("entra al metodo de precionado");
		Image img = new Image(getClass().getResourceAsStream("/images/eye.png"));
		imagen.setImage(img);
		System.out.println("cargo");
		mostrar_contraseña.setText("Tu contraseña es: " + contraseña_login.getText());
		mostrar_contraseña.setVisible(true);
	}

	@FXML
	private void Imagensoltada() {
		Image img = new Image(getClass().getResourceAsStream("/images/hide.png"));
		imagen.setImage(img);
		System.out.println("entra al metodo de soltado");
		mostrar_contraseña.setVisible(false);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		mostrar_contraseña.setVisible(false);

	}

	// Cerrar sesion y volver al Login (NOEL)
	@FXML
	private void cerrarSesion() {
		stageManager.switchScene(FxmlView.LOGIN);
	}

	@FXML
	private void irAServicio() {
		stageManager.switchScene(FxmlView.ServiciosAdministrador);
	}

	@FXML
	private void Salir() {
		Alert miAlerta = new Alert(AlertType.CONFIRMATION);
		miAlerta.setTitle("Salir");
		miAlerta.setContentText("seguro que quiere salir?");
		Optional<ButtonType> resultado = miAlerta.showAndWait();

		if (resultado.get() == ButtonType.OK) {
			System.exit(0);
		}
	}
	
	
	@FXML
	private void hacerBackup() {
	    // URL para abrir en el navegador
	    String url = "http://localhost:8081/backup";
	    abrirNavegador(url);
	}

	private void abrirNavegador(String url) {
	    try {
	        ProcessBuilder pb;
	        
	        if (System.getProperty("os.name").toLowerCase().contains("win")) {
	            pb = new ProcessBuilder("cmd", "/c", "start", url);
	        } else {
	            pb = new ProcessBuilder("xdg-open", url);
	        }

	        pb.start();
	        mostrarAlerta("Redirigiendo al navegador", "Backup realizado con éxito", AlertType.INFORMATION);
	    } catch (IOException e) {
	        System.err.println("Error al abrir el navegador: " + e.getMessage());
	    }
	}



}
