package com.Gabriel.Noel.tarea3AD2024base.controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.Gabriel.Noel.tarea3AD2024base.config.StageManager;
import com.Gabriel.Noel.tarea3AD2024base.view.FxmlView;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
/**
 * Esta clase define los metodos que del cotrolador de recuperacion de contraseña (aun  no funcional por falta de conocimientos en el uso correcto des proceso que puede disparar esta accion)
 * @author: Gabriel - Noel
 * @version: 06/03/2025 1.0
 */
@Controller
public class RecuperarContraseñaController implements Initializable{

    @FXML
    private Label lblLogin;

    @FXML
    private TextField campoNombre;
    @FXML
	private MenuItem menusalir;
    @FXML
    private TextField campoApellido;

    @FXML
    private TextField campoCorreo;

    @FXML
    private Button botonRecuperar;
    
    @FXML
	private MenuItem ayuda;

    @FXML
    private Button botonVolverLogin;
    
    @Lazy
    @Autowired
    private StageManager stageManager;
    
    
    // Metodo para que muestre la contraseña al usuario con una alerta
//    @FXML
//    private void mostrarContraseña()
//    {
//    	try
//    	{
//    		String nombre = campoNombre.getText();
//    		String apellido = campoApellido.getText();
//    		String correo = campoCorreo.getText();
//    		
//    		// Se verifica que se rellenen todos los campos
//    		 if (nombre.isEmpty() || apellido.isEmpty() || correo.isEmpty()) 
//    		 {
//                 mostrarAlerta("Error","Todos los campos son obligatorios.", Alert.AlertType.ERROR);
//                 return;
//             }
//    		 
//    	// Se busca al usuario por correo -- luego mas comprobaciones --
//    	//User miUsuario = userService.findByEmail(correo);
//    		 Credenciales miUsuario = null; // CAMBIAR ESTO ESPERAR AL METODO DE GABRIEL
//    		 
//    	
//    	if (miUsuario.getFirstName().equals(nombre) && miUsuario.getLastName().equals(apellido))
//    	{
//    		mostrarAlerta("Recuperacion Correcta", "Tu contraseña es: " + miUsuario.getPassword(), Alert.AlertType.INFORMATION);
//    	}
//    	else
//    	{
//    		mostrarAlerta("Error", "Los datos proporcionados no coinciden con ningun usuario", Alert.AlertType.ERROR);
//    	}
//    	}
//    		catch(Exception e)
//    		{
//    			mostrarAlerta("Error", "Ocurrio un problema al recuperar su contraseña", Alert.AlertType.ERROR);
//    		}
//    }
    
    @FXML
	public void AyudaJavaFX() {
		try {
			System.out.println("entro a la funcion de ayuda pero no cargo la ventana");
			// Crear un WebView para mostrar la ayuda
			WebView webView = new WebView();

			// Cargar el archivo HTML desde los recursos
			String url = getClass().getResource("/ayuda/AyudaRecuperarContraseña.html").toExternalForm();
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
    private void volverAlLogin()
    {
    	try
		{
			stageManager.switchScene(FxmlView.LOGIN);
		}
		
		catch(Exception e)
		{
			System.out.println("Error en el metodo volverALogin");
		}
    }
	/*
	 * private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType
	 * tipo) { Alert miAlerta = new Alert(tipo); miAlerta.setTitle(titulo);
	 * miAlerta.setContentText(mensaje); miAlerta.showAndWait(); }
	 */
	@FXML
	private void Salir() {
		//Boolean salir = false;
		Alert miAlerta = new Alert(AlertType.CONFIRMATION);
		miAlerta.setTitle("Salir");
		miAlerta.setContentText("seguro que quiere salir?");
		Optional<ButtonType> resultado = miAlerta.showAndWait();

		if(resultado.get()==ButtonType.OK) {
			System.exit(0);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
    
}
