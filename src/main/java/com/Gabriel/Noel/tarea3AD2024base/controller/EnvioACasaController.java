package com.Gabriel.Noel.tarea3AD2024base.controller;

import java.io.Serializable;

import org.springframework.stereotype.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import com.Gabriel.Noel.tarea3AD2024base.config.StageManager;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Direccion;
import com.Gabriel.Noel.tarea3AD2024base.modelo.EnvioACasa;
import com.Gabriel.Noel.tarea3AD2024base.services.EnvioACasaService;
import com.Gabriel.Noel.tarea3AD2024base.view.FxmlView;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

@Controller
public class EnvioACasaController implements Serializable {
	
	
	//Botones	
	@FXML
	private Button botonLimpiarCampos;
	
	@FXML
	private Button botonGuardarDatos;
	
	
	// TextFile
	@FXML
	private TextField pesoPaquete;

	@FXML
	private TextField largoPaquete;
	
	@FXML
	private TextField anchoPaquete;

	@FXML
	private TextField altoPaquete;
	
	@FXML
	private TextField direccionEnvio;
	
	@FXML
	private TextField localidadEnvio;
	
	// CheckBox
	@FXML
	private CheckBox esUrgente;
	
	
	@Autowired
	private EnvioACasaService envioCasaService;
	
	
	@Lazy
	@Autowired
	private StageManager stageManager;
	
	@FXML
	public void limpiarCampos()
	{
		pesoPaquete.clear();
		largoPaquete.clear();
		anchoPaquete.clear();
		altoPaquete.clear();
		direccionEnvio.clear();
		localidadEnvio.clear();
		esUrgente.setSelected(false);
	}
	
	
	@FXML
	public void guardarDatosEnvio()
	{
		Direccion miDireccion = new Direccion();
		EnvioACasa miEnvio = new EnvioACasa();
		
		try
		{
		String peso = pesoPaquete.getText();
		String largo = largoPaquete.getText();
		String ancho = anchoPaquete.getText();
		String alto = altoPaquete.getText();
		String direccion = direccionEnvio.getText();
		String localidad = localidadEnvio.getText();
		
		boolean urgente = esUrgente.isSelected();
		
		if (!validar(peso)|| !validar(largo) || !validar(ancho) || !validar(alto) ||
				!validar(direccion) || !validar(localidad));
		
		// Creo el objeto Direccion
		miDireccion.setDireccion(direccion);
		miDireccion.setLocalidad(localidad);
		
		
		// Convierto las variables a Double para almacenarlas al objeto miEnvio
		Double pesoDouble = Double.parseDouble(peso);
		Double largoDouble = Double.parseDouble(largo);
		Double anchoDouble = Double.parseDouble(ancho);
		Double altoDouble = Double.parseDouble(alto);
		

		
		// Creo el Objeto Envio
		miEnvio.setPeso(pesoDouble);
		miEnvio.setLargo(largoDouble);
		miEnvio.setAncho(anchoDouble);
		miEnvio.setAlto(altoDouble);
		miEnvio.setEsUrgente(urgente);
		miEnvio.setDireccion(miDireccion);
		
		envioCasaService.registrarEnvio(miEnvio);
		
		mostrarAlerta("Guardado", "Envio registrado y guardado con exito, volveras a la ventana del responsable", AlertType.CONFIRMATION);
		
		volverAtras();

		}
		
		catch(Exception e)
		{
			System.out.println("Error en el metodo GuardarDatosEnvios: "+ e.getMessage());
		}
		
		
	}
	
	
	public boolean validar(String texto)
	{
		boolean valido = false;
		
		if (texto.isEmpty())
		{
			
			mostrarAlerta("Error", "No puedes dejar el campo "+ texto +"vacio", AlertType.WARNING);
			return valido = false;
		}
			
		return valido = true;
		
		
	}
	
	
	private void volverAtras() {
		try {
			stageManager.switchScene(FxmlView.RESPONSABLE);
		}

		catch (Exception e) {
			System.out.println("Error en el metodo volverALogin");
		}
	}
	
	
	/**
	 * Metodo para mostrar las alertas en la parte de la vista
	 * 
	 * @param titulo
	 * @param mensaje
	 * @param tipo
	 */
	private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
		Alert miAlerta = new Alert(tipo);
		miAlerta.setTitle(titulo);
		miAlerta.setContentText(mensaje);
		miAlerta.showAndWait();
	}

}
