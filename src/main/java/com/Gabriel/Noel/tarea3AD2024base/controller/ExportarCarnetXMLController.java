package com.Gabriel.Noel.tarea3AD2024base.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import org.springframework.stereotype.Controller;

import com.Gabriel.Noel.tarea3AD2024base.modelo.Carnet;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Credenciales;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Parada;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Peregrino;

import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

@Controller
public class ExportarCarnetXMLController implements Initializable {

	@FXML
	private Button boton;
	
	@FXML
	private TableView<Carnet> tabla_carnet;
	
	@FXML
	private TableColumn<Carnet, Long> id_carnet;
	
	@FXML
	private TableColumn<Carnet, Long> distancia;
	
	@FXML
	private TableColumn<Carnet, LocalDate> fecha;
	
	@FXML
	private TableColumn<Carnet, Long> estancians_vip;
	
	@FXML
	private TableColumn<Carnet, Parada> parada;
	
	@FXML
	private void boton() {
		System.out.println("prueba");
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Credenciales cred= CredencialesController.Credenciales_usuario;
		//prueba de que llegan los datos
		System.out.println(cred.toString());
		//colPeregrinoID.setCellValueFactory(new PropertyValueFactory<>("id"));
		//colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		//colNacionalidad.setCellValueFactory(new PropertyValueFactory<>("nacionalidad"));

		
	}

}
