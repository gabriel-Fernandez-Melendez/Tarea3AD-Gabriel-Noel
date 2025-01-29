package com.Gabriel.Noel.tarea3AD2024base.controller;

import java.net.URL;
import java.util.ArrayList;
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

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

@Controller
public class NuevaParadaController implements Initializable {

	
	@FXML
	private TextField nombre_login;
	
	@FXML
	private PasswordField contraseña_login;
	
	@FXML
	private TextField nombre_responsable;
	
	@FXML
	private TextField nombre_parada;
	
	@FXML
	private TextField nombre_region;
	
	@FXML
	private Button boton_guardar;
	
	@FXML
	private Button limpiarcampos;
	
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
	
	//metodo que tiene que ver con la insercion de datos 
	//OJO no tiene por que ser null cambiar si hace falta
	//este metodo ya lo habia creado en el nuevo peregrino y puede ser reutilizado
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
				cred=credenciales_service.GuardarCredenciales(cred);
			}
		}
		return cred;
	}
	
	private void GuardarParada(	Credenciales cred) {
		Parada p =new Parada();
		p.setNombre(nombre_parada.getText());
		p.setRegion(nombre_region.getText().charAt(0)); //asi  solo pillo el primer caracter que introdice (lo controlare en el futuro)
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
	
	//metodos para guardar la parada y las credenciales del usuario
	@FXML
	public void GuardarCredencialesResponsableParada() {
		Credenciales c=GuardarNuevasCredenciales();
		if(c==null) {
			mostrarAlerta("Credenciales no disponibles", "estas credenciales pertenecen a otro usuario del sistema", AlertType.ERROR);
		}
		System.out.println("MIS CREDENCIALES SE GUARDARON COMO "+ c.toString());
		GuardarParada(c);
	}
	
	@FXML
	public void LimpiarCampos() {
		
	}
	
	//reutilizo los metodos creados en nuero peregrino para mostrar la contraseña al usuario
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
	

}
