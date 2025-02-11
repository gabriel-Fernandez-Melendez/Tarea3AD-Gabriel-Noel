package com.Gabriel.Noel.tarea3AD2024base.controller;

import java.io.Serializable;
import java.net.URL;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

@Controller
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
	private Button botonVolverLogin;

	@FXML
	private Button restaurar;

	@FXML
	private Button guardar;
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
		/*
		 * System.out.println(cred.toString());
		 * Nombre_login.setText(cred.getNombreUsuario());
		 * correo.setText(cred.getCorreo_usuario());
		 * contraseña.setText(cred.getContraseñaUsuario()); ObservableList<Parada>
		 * parada_ = FXCollections.observableArrayList(car.getParadaInicial());
		 * ObservableList<String> pais_ =
		 * FXCollections.observableArrayList(per.getNacionalidad());
		 * parada.setItems(parada_); pais.setItems(pais_);
		 */
	}

	@FXML
	private void volverLogin() {
		stageManager.switchScene(FxmlView.LOGIN);
	}
}
