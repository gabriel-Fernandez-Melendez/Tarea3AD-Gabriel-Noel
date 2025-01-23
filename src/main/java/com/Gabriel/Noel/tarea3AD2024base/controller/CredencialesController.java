package com.Gabriel.Noel.tarea3AD2024base.controller;

import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

@Controller
public class CredencialesController implements Initializable {

	// Variable Global estatica para el nombre de usuario (NOEL)
	private static String nombreUsuarioAutenticado;
	
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

	// no puedo usar static por que no llega correctamente la inyeccion de
	// dependencias
	@FXML
	public void prueba() {
		System.out.println("prueba");
		Credenciales cred = new Credenciales();
		cred.setNombreUsuario("rtyhnetd");
		cred.setContraseñaUsuario("pero q");
		cred.setTipo(Usuarios.Peregrino);
		credenciales_service.GuardarCredenciales(cred);
		System.out.println("termino");
	}

	// este meotod es el que nos permite contrastar con la las credenciales que hay
	// en la tabla de la basde datos y posteriormente lo envia al menu propicio para
	// ello
	@FXML
	public void ValidarCredenciales() {
		System.out.println("entro al metodo de autenticacion");
		boolean val = false;
		ArrayList<Credenciales> cred_lista = (ArrayList<Credenciales>) credenciales_service.ListaDeCredenciales();
		for (Credenciales c : cred_lista) {
			if (nombreUsuario.getText().contentEquals(c.getNombreUsuario())
					&& contraseña.getText().contentEquals(c.getContraseñaUsuario())) {
				System.out.println("el valido!");
				
				nombreUsuarioAutenticado = c.getNombreUsuario();
				
				mostrarAlerta("Bienvenido", "Bienvenido: "+c.getTipo().getTipoDeUsuario(), AlertType.INFORMATION);
				val = true;
				
			} else {
				System.out.println("no son credenciales validas");
			}
			
		}
		//esta alerta se muestra fuera del for para que no se ejecute muchas veces y un  if para que no se ejecute igualemente siempre al terminar el bucle
		if(!val) {
		mostrarAlerta("Las credenciales son invalidas", "Las credenciales introducidas no son validas, intentelo de nuevo", AlertType.ERROR);	
		}
		
		// fuera del for tiene que ir la llamada la metodo que conexte con las
		// interfaces pasando como argumento el tipo de usuario que es
	}

	private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
		Alert miAlerta = new Alert(tipo);
		miAlerta.setTitle(titulo);
		miAlerta.setContentText(mensaje);
		miAlerta.showAndWait();
	}
	
	// METODO PRUEBA PREGUNTAR A GABRI (NOEL)
	public static String getNombreUsuario()
	{
		return nombreUsuarioAutenticado;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

}
