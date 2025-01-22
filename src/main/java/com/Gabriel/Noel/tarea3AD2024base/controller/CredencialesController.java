package com.Gabriel.Noel.tarea3AD2024base.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import com.Gabriel.Noel.tarea3AD2024base.config.StageManager;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Credenciales;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Parada;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Peregrino;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Usuarios;
import com.Gabriel.Noel.tarea3AD2024base.services.CredencialesService;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

@Controller
public class CredencialesController implements Initializable {

	@FXML
	private TextField nombreUsuario;
	@FXML
	private PasswordField contraseña;
	@FXML
	private Button Boton_login;
	@FXML
	private Button boton_Nuevo_Peregrino;
	
	@Autowired
	private CredencialesService credenciales_service;
	@Lazy
	@Autowired
	private StageManager stageManager;
	 
	//no puedo usar static por que  no llega correctamente la inyeccion de dependencias
	@FXML
	public void prueba() {
		System.out.println("prueba");
		Credenciales cred = new Credenciales();
		cred.setNombreUsuario("rtyhnetd");
		cred.setContraseñaUsuario("pero q");
		cred.setTipo(Usuarios.Peregrino	);
		credenciales_service.GuardarCredenciales(cred);
		System.out.println("termino");
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	
}

