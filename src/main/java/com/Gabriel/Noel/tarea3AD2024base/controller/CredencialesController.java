package com.Gabriel.Noel.tarea3AD2024base.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import com.Gabriel.Noel.tarea3AD2024base.config.AppJavaConfig;
import com.Gabriel.Noel.tarea3AD2024base.config.StageManager;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Credenciales;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Parada;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Peregrino;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Usuarios;
import com.Gabriel.Noel.tarea3AD2024base.services.CredencialesService;
import com.Gabriel.Noel.tarea3AD2024base.view.FxmlView;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

@Controller
public class CredencialesController implements Initializable {

	// Variable Global estatica para el nombre de usuario (NOELIA)
	public static Credenciales Credenciales_usuario;

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
	
	// Añado una inyeccion para el AppJavaConfig
	@Autowired
	private AppJavaConfig appJavaConfig;

	
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
	public void ValidarCredenciales() 
	{
		System.out.println("entro al metodo de autenticacion");
		boolean val = false;
		
		// verificar si el usuario es administrador validar contra application.properties
		// Se hace antes de que vaya al Switch y primero vea si las credenciales escritas
		// Son las del administrador (NOEL) #FUNCIONAL
		 if (nombreUsuario.getText().equals(appJavaConfig.getAdminUsername()) &&
			        contraseña.getText().equals(appJavaConfig.getAdminPassword())) {
			        
			        System.out.println("Inicio de sesión como ADMIN");
			        mostrarAlerta("Bienvenido", "Bienvenido Administrador", AlertType.INFORMATION);
			        stageManager.switchScene(FxmlView.NuevaParada); // Acceso directo al menú de admin
			        return;
			    }
		
		
		
		
		ArrayList<Credenciales> cred_lista = (ArrayList<Credenciales>) credenciales_service.ListaDeCredenciales();
		
		for (Credenciales c : cred_lista) 
		{
			if (nombreUsuario.getText().contentEquals(c.getNombreUsuario())
					&& contraseña.getText().contentEquals(c.getContraseñaUsuario())) 
			{
				System.out.println("el valido!");
				Credenciales_usuario = c;
				
				mostrarAlerta("Bienvenido", "Bienvenido: " + c.getTipo().getTipoDeUsuario(), AlertType.INFORMATION);
				val = true;
				
				AccesoAlMenu();
				
			} 
			
			else 
			{
				System.out.println("no son credenciales validas");
			}

		}
		// esta alerta se muestra fuera del for para que no se ejecute muchas veces y un
		// if para que no se ejecute igualemente siempre al terminar el bucle
		if (!val) {
			mostrarAlerta("Las credenciales son invalidas",
					"Las credenciales introducidas no son validas, intentelo de nuevo", AlertType.ERROR);
		}

		// fuera del for tiene que ir la llamada la metodo que conexte con las
		// interfaces pasando como argumento el tipo de usuario que es
	}
	
	public static Credenciales getCredenciales()
	{
		return Credenciales_usuario;
	}
	
	private void AccesoAlMenu() {
		Usuarios tipodeusuario =Credenciales_usuario.getTipo();
		
		switch (tipodeusuario)
		{
		case Invitado: 
			//como al inicio del programa le asignamos invitado temporalmente entra aqui!
			stageManager.switchScene(FxmlView.NuevoPeregrino);
			break;
		case Responsable_Parada: 
			stageManager.switchScene(FxmlView.RESPONSABLE);
			break;
		case Peregrino: 
			stageManager.switchScene(FxmlView.ExportarXML);
//			break;
//		case Administrador_General: 
//			stageManager.switchScene(FxmlView.NuevaParada);//esto hay que modificarlo
	break;
		
		default:
			throw new IllegalArgumentException("Unexpected value: " + tipodeusuario);
		}
	}

	private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
		Alert miAlerta = new Alert(tipo);
		miAlerta.setTitle(titulo);
		miAlerta.setContentText(mensaje);
		miAlerta.showAndWait();
	}

	// METODO PRUEBA PREGUNTAR A GABRI (NOEL) hay que eliminar este metodo
	//public static String getNombreUsuario() {
		//return nombreUsuarioAutenticado;
	//}
	
	@FXML
 	private void RecuperarContraseña() {
 		try {
 			System.out.println("lalala prueba");
 			stageManager.switchScene(FxmlView.RECUPERAR_CONTRASEÑA);
 		}

 		catch (Exception e) {
 			System.out.println("Error en el metodo volverALogin");
 		}
 	}
	//movi el metodo para desarrolar con mayor facilidad la interfaz
	@FXML
	public void AyudaJavaFX( ) {
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
		    //esta linea en caso de que necesitemos detectar el origen del fallo atravez de consola
		    e.printStackTrace();
		    }
		}
	
	@FXML
	private void NuevoPeregrino() {
		stageManager.switchScene(FxmlView.NuevoPeregrino);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

}
