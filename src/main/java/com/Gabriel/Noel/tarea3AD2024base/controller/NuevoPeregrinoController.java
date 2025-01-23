package com.Gabriel.Noel.tarea3AD2024base.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.Gabriel.Noel.tarea3AD2024base.modelo.Credenciales;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Parada;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Peregrino;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Usuarios;
import com.Gabriel.Noel.tarea3AD2024base.services.CredencialesService;
import com.Gabriel.Noel.tarea3AD2024base.services.PeregrinoService;

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
	private ChoiceBox<Parada> parada;

	@Autowired
	private CredencialesService credenciales_service;

	@Autowired
	private PeregrinoService peregrino_service;

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
	
	private boolean GuardarPeregrino() {
		boolean val =false;
		ArrayList<Peregrino> peregrinos =(ArrayList<Peregrino>) peregrino_service.ListaDePeregrinos();
		for(Peregrino p: peregrinos) {
			// NO HACE FALTA POR QUE LOS NOMBRES DEL PEREGRINO SE PUEDEN REPETIR 
			if(nombre_peregrino.getText().equals(p.getNombre())) {
			
			}
		}
		return true;
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

	}

}
