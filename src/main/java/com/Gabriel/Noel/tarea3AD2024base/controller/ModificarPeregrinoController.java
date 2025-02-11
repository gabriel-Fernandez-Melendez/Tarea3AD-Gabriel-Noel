package com.Gabriel.Noel.tarea3AD2024base.controller;

import java.io.Serializable;
import java.net.URL;
import java.util.ResourceBundle;

import com.Gabriel.Noel.tarea3AD2024base.modelo.Parada;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

public class ModificarPeregrinoController implements Initializable {

	@FXML
	private TextField Nombre_login;
	
	@FXML
	private TextField correo;
	
	@FXML
	private TextField contraseña;
	
	@FXML
	private TextField nombre_peregrino;
	
	@FXML
	private ComboBox<String> pais;
	
	@FXML
	private ComboBox<Parada> parada;
	
	@FXML
	private MenuItem menusalir;
	
	@FXML
	private Button limpiar;

	@FXML
	private Button restaurar;
	
	@FXML
	private Button guardar;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

}
