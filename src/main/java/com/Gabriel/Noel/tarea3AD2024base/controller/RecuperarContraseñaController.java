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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

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
