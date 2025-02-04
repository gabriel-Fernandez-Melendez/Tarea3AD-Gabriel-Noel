package com.Gabriel.Noel.tarea3AD2024base.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.Gabriel.Noel.tarea3AD2024base.config.StageManager;
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
	private Button boton_guardar;

	@FXML
	private Button limpiarcampos;

	@FXML
	private Button botonCerrarSesion;

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
		boolean validar_correo = true;
		boolean validar_region = true;
		boolean validar_nombre = true;
		boolean validar_contraseña = true;

		if (nombre_login.getText().contains(" ")) {
			mostrarAlerta("Nombre no valido", "no puede tener espacios blancos el nombre", AlertType.ERROR);
			validar_nombre = false;
		}
		if (contraseña_login.getText().contains(" ")) {
			mostrarAlerta("contraseña no valida", "no puede tener espacios blancos la contraseña", AlertType.ERROR);
			validar_contraseña = false;
		}
		if (!correo_usuario.getText().contains("@")) {
			mostrarAlerta("contraseña no valida", "falta el @", AlertType.ERROR);
			validar_correo = false;
		}
		if (!correo_usuario.getText().contains(".com")) {
			mostrarAlerta("contraseña no valida", "falta el .com", AlertType.ERROR);
			validar_correo = false;
		}
		if (nombre_region.getText().length() > 1) {
			mostrarAlerta("contraseña no valida", "la region solo puede ser un caracter", AlertType.ERROR);
			validar_region = false;
		}
		if (validar_contraseña && validar_correo && validar_nombre && validar_region) {
			Credenciales c = GuardarNuevasCredenciales();
			GuardarParada(c);
			mostrarAlerta("Nueva parada", "A guardado la nueva parada con sus responsable correspondiente",
					AlertType.INFORMATION);
		}

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
	private void Salir() {
		Alert miAlerta = new Alert(AlertType.CONFIRMATION);
		miAlerta.setTitle("Salir");
		miAlerta.setContentText("seguro que quiere salir?");
		Optional<ButtonType> resultado = miAlerta.showAndWait();

		if(resultado.get()==ButtonType.OK) {
			System.exit(0);
		}
	}
	


}
