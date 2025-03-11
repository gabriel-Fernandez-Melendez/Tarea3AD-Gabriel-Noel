package com.Gabriel.Noel.tarea3AD2024base.controller;

import java.io.Serializable;

import org.springframework.stereotype.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import com.Gabriel.Noel.tarea3AD2024base.config.StageManager;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Credenciales;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Direccion;
import com.Gabriel.Noel.tarea3AD2024base.modelo.EnvioACasa;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Parada;
import com.Gabriel.Noel.tarea3AD2024base.services.EnvioACasaService;
import com.Gabriel.Noel.tarea3AD2024base.services.ParadaService;
import com.Gabriel.Noel.tarea3AD2024base.view.FxmlView;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

@Controller
public class EnvioACasaController implements Serializable {
	
	

	private static final long serialVersionUID = 1L;

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
	private ParadaService paradaService;
	
	
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
		
//		if (!validar(peso)|| !validar(largo) || !validar(ancho) || !validar(alto) || // preguntar si  borrar esto
//				!validar(direccion) || !validar(localidad))
//		{
//			return;
//		}
		
		 // Comprobación de campos vacíos y mostrar qué campo falta
        if (peso.isEmpty()) {
            mostrarAlerta("Error", "El campo 'Peso' no puede estar vacío.", AlertType.ERROR);
            return;
        }
        if (largo.isEmpty()) {
            mostrarAlerta("Error", "El campo 'Largo' no puede estar vacío.", AlertType.ERROR);
            return;
        }
        if (ancho.isEmpty()) {
            mostrarAlerta("Error", "El campo 'Ancho' no puede estar vacío.", AlertType.ERROR);
            return;
        }
        if (alto.isEmpty()) {
            mostrarAlerta("Error", "El campo 'Alto' no puede estar vacío.", AlertType.ERROR);
            return;
        }
        if (direccion.isEmpty()) {
            mostrarAlerta("Error", "El campo 'Dirección' no puede estar vacío.", AlertType.ERROR);
            return;
        }
        if (localidad.isEmpty()) {
            mostrarAlerta("Error", "El campo 'Localidad' no puede estar vacío.", AlertType.ERROR);
            return;
        }
		
		 // Verificar que el peso usa punto (.) como separador decimal y no coma (,)
        if (!peso.matches("\\d+(\\.\\d{1,2})?")) {
            mostrarAlerta("Error", "El peso debe ser un número válido con punto (.) como separador decimal.", AlertType.ERROR);
            return;
        }
		
		// Creo el objeto Direccion
		miDireccion.setDireccion(direccion);
		miDireccion.setLocalidad(localidad);
		
		
		// Convierto las variables a Double para almacenarlas al objeto miEnvio
		Double pesoDouble = Double.parseDouble(peso);
		
		// Verificar que el peso no sea negativo
        if (pesoDouble < 0) {
            mostrarAlerta("Error", "El peso no puede ser un número negativo.", AlertType.ERROR);
            return;
        }
        
        // Verificar que largo, ancho y alto son enteros válidos
        if (!largo.matches("\\d+") || !ancho.matches("\\d+") || !alto.matches("\\d+")) {
            mostrarAlerta("Error", "Las dimensiones deben ser números enteros positivos.", AlertType.ERROR);
            return;
        }
		
		
		int largoInt = Integer.parseInt(largo);
        int anchoInt = Integer.parseInt(ancho);
        int altoInt = Integer.parseInt(alto);
        
     // Verificar que ninguna dimensión sea negativa
        if (largoInt < 0 || anchoInt < 0 || altoInt < 0) {
            mostrarAlerta("Error", "Las dimensiones no pueden ser negativas.", AlertType.ERROR);
            return;
        }
		
        int[] volumen = {largoInt, anchoInt, altoInt};
		
		// Creo el Objeto Envio
		miEnvio.setPeso(pesoDouble);
		miEnvio.setVolumen(volumen);
		miEnvio.setEsUrgente(urgente);
		miEnvio.setDireccion(miDireccion);
		miEnvio.setIdParada(recogerIDParada());
		
		envioCasaService.registrarEnvio(miEnvio);
		
		mostrarAlerta("Guardado", "Envio registrado y guardado con exito, volveras a la ventana del responsable", AlertType.CONFIRMATION);
		
		volverAtras();

		}
		
		catch(Exception e)
		{
			System.out.println("Error en el metodo GuardarDatosEnvios: "+ e.getMessage());
		}
		
		
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
	
	
	private Long recogerIDParada()
    {
    	Long idParada = 0L;
    	
    	try 
    	{
    		Credenciales miCredencial = CredencialesController.getCredenciales();
    		
    		Parada paradaActual = paradaService.buscarParadaPorCredenciales(miCredencial);
    		
    		idParada = paradaActual.getId();
    		  		
    	}
    	
    	catch(Exception e)
    	{
    		System.out.println("Error en el metodo de recogerIDParada"+ e.getMessage());
    	}
    	
    	
    	
    	return idParada;
    }
	

}
