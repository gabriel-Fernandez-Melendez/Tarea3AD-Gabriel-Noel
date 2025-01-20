package com.Gabriel.Noel.tarea3AD2024base.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.Gabriel.Noel.tarea3AD2024base.config.StageManager;
import com.Gabriel.Noel.tarea3AD2024base.modelo.User;

import com.Gabriel.Noel.tarea3AD2024base.services.UserService;
import com.Gabriel.Noel.tarea3AD2024base.view.FxmlView;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

@Controller
public class RecuperarContraseñaController {

    @FXML
    private Label lblLogin;

    @FXML
    private TextField campoNombre;

    @FXML
    private TextField campoApellido;

    @FXML
    private TextField campoCorreo;

    @FXML
    private Button botonRecuperar;

    @FXML
    private Button botonVolverLogin;
    
    @Autowired
    private UserService userService;
    
    @Lazy
    @Autowired
    private StageManager stageManager;
    
    
    // Metodo para que muestre la contraseña al usuario con una alerta
    @FXML
    private void mostrarContraseña()
    {
    	try
    	{
    		String nombre = campoNombre.getText();
    		String apellido = campoApellido.getText();
    		String correo = campoCorreo.getText();
    		
    		// Se verifica que se rellenen todos los campos
    		 if (nombre.isEmpty() || apellido.isEmpty() || correo.isEmpty()) 
    		 {
                 mostrarAlerta("Error","Todos los campos son obligatorios.", Alert.AlertType.ERROR);
                 return;
             }
    		 
    	// Se busca al usuario por correo -- luego mas comprobaciones --
    	User miUsuario = userService.findByEmail(correo);
    		 
    	
    	if (miUsuario.getFirstName().equals(nombre) && miUsuario.getLastName().equals(apellido))
    	{
    		mostrarAlerta("Recuperacion Correcta", "Tu contraseña es: " + miUsuario.getPassword(), Alert.AlertType.INFORMATION);
    	}
    	else
    	{
    		mostrarAlerta("Error", "Los datos proporcionados no coinciden con ningun usuario", Alert.AlertType.ERROR);
    	}
    	}
    		catch(Exception e)
    		{
    			mostrarAlerta("Error", "Ocurrio un problema al recuperar su contraseña", Alert.AlertType.ERROR);
    		}
    }
    
    
    @FXML
    private void volverALogin()
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
    
    	private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
            Alert miAlerta = new Alert(tipo);
            miAlerta.setTitle(titulo);
            miAlerta.setContentText(mensaje);
            miAlerta.showAndWait();
        }
    
}
