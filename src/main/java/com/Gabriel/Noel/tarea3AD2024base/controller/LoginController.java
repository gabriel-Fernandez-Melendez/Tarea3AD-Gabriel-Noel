package com.Gabriel.Noel.tarea3AD2024base.controller;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.Gabriel.Noel.tarea3AD2024base.config.StageManager;
import com.Gabriel.Noel.tarea3AD2024base.services.UserService;
import com.Gabriel.Noel.tarea3AD2024base.view.FxmlView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * @author Ram Alapure
 * @since 05-04-2017
 */

@Controller
public class LoginController implements Initializable{

	@FXML
    private Button btnLogin;

    @FXML
    private PasswordField contraseña;

    @FXML
    private TextField nombreUsuario;

    @FXML
    private Label lblLogin;
    
    @FXML
    private Button botonNuevoPeregrino;
    
    @FXML
    private Label contraseñaOlvidada;
    
    @Autowired
    private UserService userService;
    
    
    @Lazy
    @Autowired
    private StageManager stageManager;
        
	@FXML
    private void login(ActionEvent event) throws IOException{
    	if(userService.authenticate(getUsername(), getPassword())){
    		    		
    		stageManager.switchScene(FxmlView.USER);
    		
    	}else{
    		lblLogin.setText("Inicio Fallido.");
    	}
    }
		
	@FXML
	private void redirigirRecuperarContraseña()
	{
		try
		{
			stageManager.switchScene(FxmlView.RECUPERAR_CONTRASEÑA);
		}
		
		catch(Exception e)
		{
			System.out.println("Error en el metodo redirigirRecuperarContraseña()");
		}
	}
	
	// Añadir cuando Gabi haga la pantalla de crear nuevo peregrino
//	@FXML
//	private void nuevoPeregrino()
//	{
//		try
//		{
//			stageManager.switchScene(FxmlView.);
//		}
//		
//		catch(Exception e)
//		{
//			System.out.println("Error en el metodo nuevoPeregrino");
//		}
//	}
	
	
	public String getPassword() {
		return contraseña.getText();
	}

	public String getUsername() {
		return contraseña.getText();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

}
