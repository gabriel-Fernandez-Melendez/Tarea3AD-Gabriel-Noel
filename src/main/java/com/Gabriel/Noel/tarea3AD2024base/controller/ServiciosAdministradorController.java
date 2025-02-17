package com.Gabriel.Noel.tarea3AD2024base.controller;

import java.io.Serializable;

import org.springframework.stereotype.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import com.Gabriel.Noel.tarea3AD2024base.config.StageManager;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Parada;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Servicio;
import com.Gabriel.Noel.tarea3AD2024base.services.ParadaService;
import com.Gabriel.Noel.tarea3AD2024base.view.FxmlView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;



@Controller
public class ServiciosAdministradorController implements Serializable
{
	// BOTONES
	@FXML
	private Button btnLogout;
	
	@FXML
	private Button botonAsignarServicio;
	
	@FXML
	private Button botonCrearServicio;
	
	@FXML
	private Button botonVolver;
	
	
	// TABLAS
	@FXML
	private TableView<Parada> tablaParadas;
	
	@FXML
	private TableView<Servicio> tablaServicios;
	
	// COLUMNAS SERVICIOS	
	@FXML
	private TableColumn<Servicio,Long> idServicio;
	
	@FXML
	private TableColumn<Servicio,String> nombreServicio;
	
	@FXML
	private TableColumn<Servicio,Double> precioServicio;
	
	
	// COLUMNAS PARADA
	@FXML
	private TableColumn<Parada,Long> idParada;
	
	@FXML
	private TableColumn<Parada,String> nombreParada;
	
	// TEXTFIELDS
	@FXML
	private TextField textServicio;
	
	@FXML
	private TextField textPrecio;
	
	// Para poder recoger todas las paradas
	@Autowired
	private ParadaService parada_service;
	
	
	// Depende con que BD se quiera cerrar la sesion 
	@Lazy
	@Autowired
	private StageManager stageManager;
	
	
	@FXML
	public void initialize()
	{
		cargarColumnasParadas();
		// cargarColumnasServicios();
		
	}
	
	
	@FXML
	private void crearServicio()
	{
		String nombre = textServicio.getText();
		Double precio = 0.0;
		
		try {
	        precio = Double.parseDouble(textPrecio.getText());
	    } catch (NumberFormatException e) {
	        mostrarAlerta("Error", "Introduzca un valor numérico válido para el precio (ejemplo: 10.5)", Alert.AlertType.ERROR);
	        return;
	    }

	    // Validar que el nombre no esté vacío
	    if (nombre == null || nombre.isEmpty()) {
	        mostrarAlerta("Error", "El nombre del servicio no puede estar vacío.", Alert.AlertType.ERROR);
	        return;
	    }

	    // Ejemplo pata comprobar si el servicio existe (Preguntar como hacer con DB4O)
	    //	    if (servicioExiste(nombre)) {
	    //	        mostrarAlerta("Error", "Ya existe un servicio con este nombre.", Alert.AlertType.ERROR);
	    //	        return;
	    //	    }
	    
	    
		// Creo el nuevo objeto
		Servicio miNuevoServicio = new Servicio();
		
		miNuevoServicio.setNombre(nombre);
		miNuevoServicio.setPrecio(precio);
		
		
		/**
		 * Aqui añadir el objeto SERVICIO a la BD de DB4O a traves de algun metodo en el service
		 */
		
		// Mostrar alerta para indicar al usuario
		mostrarAlerta("Exito","Ha guardado el servicio correctamente", AlertType.CONFIRMATION);
		
		// Limpiar campos después de la creación
	    textServicio.clear();
	    textPrecio.clear();
		
		// Recargamos la tabla para ver los cambios en tiempo real
		cargarColumnasServicios();
	}
	

	
	/**
	 * Metodo para cerrar sesion y volver al Login.
	 */
	@FXML
	private void volverALogin() 
	{
		try 
		{
			stageManager.switchScene(FxmlView.LOGIN);
		}

		catch (Exception e) 
		{
			System.out.println("Error en el metodo volverALogin");
		}
	}	
	
	
	@FXML
	private void cargarColumnasParadas()
	{
		try 
		{
		tablaParadas.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		idParada.setCellValueFactory(new PropertyValueFactory<>("id"));
		nombreParada.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		
		
		// Cargamos las paradas
		List<Parada> misParadas = parada_service.ListaDeParadas();
		
		
		// Convertimos la lista a un Observable
		ObservableList<Parada> miObservable = FXCollections.observableArrayList(misParadas);
		
		// Cargamos las paradas a la TableView
		tablaParadas.setItems(miObservable);
		}
		
		catch(Exception e)
		{
			System.out.println("Error en el metodo cargarColumnasParadas()");
		}
		
	}
	
	
	@FXML
	private void cargarColumnasServicios()
	{
		try
		{
		idServicio.setCellValueFactory(new PropertyValueFactory<>("id"));
		nombreServicio.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		precioServicio.setCellValueFactory(new PropertyValueFactory<>("precio"));
		
		
		// Cargamos los servicios
		/**
		 * Aqui recoger la lista de los servicios por una clase Service
		 * ejemplo List<Servicio> misServicios = servicio_service.ListaDeServicios();
		 * 
		 * Así recojo todos los servicios
		 */
		List<Servicio> misServicios = null;
		
		
		// Convertimos la lista a un Observable
		ObservableList<Servicio> miObservable = FXCollections.observableArrayList(misServicios);
		
		// Por ultimo cargar a la tabla
		tablaServicios.setItems(miObservable);
		}
		
		catch(Exception e)
		{
			System.out.println("Error en el metodo cargarColumnasServicios()");
		}				
	}
	
	
	@FXML
	private void asignarServicio()
	{
		try 
		{
		
			 // Obtener el servicio seleccionado
	        Servicio miServicio = tablaServicios.getSelectionModel().getSelectedItem();
	        
	        // Obtener las paradas seleccionadas (múltiples)
	        List<Parada> paradasSeleccionadas = tablaParadas.getSelectionModel().getSelectedItems();

	        // Verificar que ambos elementos han sido seleccionados
	        if (paradasSeleccionadas.isEmpty() || miServicio == null) {
	            mostrarAlerta("Error", "Seleccione un Servicio y una o varias Paradas", Alert.AlertType.ERROR);
	            return;
	        }
	        
	        // Hacer un For para evitar que paradas
	        

	      
	        for (Parada misParadas : paradasSeleccionadas)
	        {
	        	
	        	/**
				 * Recorrer la lista de las paradas y guarda el servicio a aquel que no lo tiene
				 * se evita duplicados y se asigna correctamente el servicio
				 * ejemplo: 
				 * 
				 * 	if (!misParadas.getservicio().contains(miServicio)) {
				 * 		misParadas.getservicio().add(miServicio)
				 * 	}
				 * 
				 * 	parada_service.save(misParadas)
				 */	
	        	
	        }
		
	        mostrarAlerta("Éxito", "El servicio ha sido asignado correctamente a las paradas seleccionadas.", Alert.AlertType.INFORMATION);
		
		
		}
		catch(Exception e)
		{
			System.out.println("Error en el metodo asignarServicio");
		}
		
	}
	
	/**
	 * Metodo para mostrar las alertas en la parte de la vista
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
	
	
	/**
	 * Cambia la vista para volver a la ventana del Responsable de Parada
	 */
	@FXML
	private void volverAtras() {
		stageManager.switchScene(FxmlView.NuevaParada);
	}
}
