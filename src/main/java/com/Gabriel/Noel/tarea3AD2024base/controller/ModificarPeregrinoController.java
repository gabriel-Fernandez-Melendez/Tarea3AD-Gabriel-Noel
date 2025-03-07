package com.Gabriel.Noel.tarea3AD2024base.controller;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.Gabriel.Noel.tarea3AD2024base.config.StageManager;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Carnet;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Credenciales;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Parada;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Peregrino;
import com.Gabriel.Noel.tarea3AD2024base.services.CarnetService;
import com.Gabriel.Noel.tarea3AD2024base.services.CredencialesService;
import com.Gabriel.Noel.tarea3AD2024base.services.PeregrinoService;
import com.Gabriel.Noel.tarea3AD2024base.view.FxmlView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Esta clase define los metodos que del cotrolador de Modificacion de los datos del peregrino, en caso de que los datos incluidos sean validos se almacenan los cambios (no se puede cambiar la parada incial ni el pais de origen)
 * @author: Gabriel - Noel
 * @version: 06/03/2025
 */
@Controller
public class ModificarPeregrinoController implements Initializable {

	@FXML
	private TextField nombre;

	@FXML
	private TextField correo;

	@FXML
	private TextField clave;

	@FXML
	private TextField nombre_peregrino;

	@FXML
	private ComboBox<String> pais;

	@FXML
	private ComboBox<Parada> parada;

	@FXML
	private MenuItem menusalir;

	@FXML
	private Button botonVolverLogin;

	@FXML
	private Button restaurar;

	@FXML
	private Button guardar;
	
	@FXML
	private MenuItem ayuda;
	
	@FXML
	private MenuItem exportarPeregrino;
	
	@Autowired
	private PeregrinoService peregrino_service;
	@Autowired
	private CarnetService carnet_service;
	@Autowired
	private CredencialesService credenciales_service;
	@Lazy
	@Autowired
	private StageManager stageManager;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		Peregrino per = peregrino_service.BuscarPorCredenciales(CredencialesController.Credenciales_usuario);
		Carnet car = carnet_service.BuscarPorId(per.getId());
		Credenciales cred =credenciales_service.obtenerCredencialesPorNombreUsuario(CredencialesController.Credenciales_usuario.getNombreUsuario());
		System.out.println(cred.toString());
		System.out.println(per.toString());
		System.out.println(car.toString());
		
		  List<Parada> paradas = new ArrayList<Parada>(); List<String> paises = new
		  ArrayList<String>(); paradas.add(car.getParadaInicial());
		  paises.add(per.getNacionalidad()); System.out.println(cred.toString());
		  nombre.setText(cred.getNombreUsuario());
		  correo.setText(cred.getCorreo_usuario()); clave.setText(cred.getContraseñaUsuario());
		  nombre_peregrino.setText(per.getNombre());
		  ObservableList<Parada> parada_ = FXCollections.observableArrayList(paradas);
		  ObservableList<String> pais_ = FXCollections.observableArrayList(paises);
		  parada.setItems(parada_); 
		  pais.setItems(pais_);
		  pais.setValue(pais_.get(0));
		  parada.setValue(parada_.get(0));
		 System.out.println("funciona");
		 
	}
	
	@FXML
	public void GuardarDatosModificados(){
		Peregrino p =new Peregrino();
		//setear los campos
	}
	
	
	@FXML
	public void AyudaJavaFX() {
		try {
			System.out.println("entro a la funcion de ayuda pero no cargo la ventana");
			// Crear un WebView para mostrar la ayuda
			WebView webView = new WebView();

			// Cargar el archivo HTML desde los recursos
			String url = getClass().getResource("/ayuda/AyudaModificarPeregrino.html").toExternalForm();
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
	
	@FXML
	private void volverExportar() {
		stageManager.switchScene(FxmlView.ExportarXML);
	}
	

	@FXML
	private void volverLogin() {
		stageManager.switchScene(FxmlView.LOGIN);
	}
}
