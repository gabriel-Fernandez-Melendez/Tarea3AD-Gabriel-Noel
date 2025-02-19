package com.Gabriel.Noel.tarea3AD2024base.controller;

import java.io.Serializable;

import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import com.Gabriel.Noel.tarea3AD2024base.config.StageManager;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Parada;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Servicio;
import com.Gabriel.Noel.tarea3AD2024base.services.ParadaService;
import com.Gabriel.Noel.tarea3AD2024base.services.ServiciosService;
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
import javafx.beans.property.SimpleStringProperty;




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
	
	@FXML
	private TableColumn<Servicio,String> idParadas; // Concatenar los ID de las paradas y no trabajarlo con Long
	
	
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
	
	@Autowired
	private ServiciosService servicioService;
	
	
	// Depende con que BD se quiera cerrar la sesion 
	@Lazy
	@Autowired
	private StageManager stageManager;
	
	
	@FXML
	public void initialize()
	{
		cargarColumnasParadas();
		cargarColumnasServicios();
		
	}
	
	@FXML
	private void crearServicio() {

	    // Obtener valores de los campos de texto
	    String nombre = textServicio.getText();
	    Double precio = Double.parseDouble(textPrecio.getText());

	    // Obtener la lista de servicios existentes
	    List<Servicio> listaServicios = servicioService.obtenerTodosLosServicios();

	    // Generar un nuevo ID (el mayor +1)
	    long idNuevo = listaServicios.size()+1;

	    // Crear servicio con los datos obtenidos
	    Servicio nuevoServicio = new Servicio();
	    nuevoServicio.setId(idNuevo);
	    nuevoServicio.setNombre(nombre);
	    nuevoServicio.setPrecio(precio);
	    nuevoServicio.setIdParada(new ArrayList<>());

	    // Guardar en la base de datos
	    servicioService.crearServicio(nuevoServicio);

	    System.out.println("Servicio guardado: " + nuevoServicio);
	    
	    // Mostrar alerta para indicar al usuario
	    mostrarAlerta("Exito","Ha guardado el servicio correctamente", AlertType.CONFIRMATION);

	    // Limpiar campos
	    textServicio.clear();
	    textPrecio.clear();

	    // Recargar la tabla para reflejar cambios
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
			servicioService.cerrarConexion();
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
		
		idParadas.setCellValueFactory(cellData -> {
			 List<Long> idParadasList = cellData.getValue().getIdParada(); // Obtiene la lista de IDs
	            String ids = (idParadasList != null && !idParadasList.isEmpty()) ? 
	                         idParadasList.toString().replace("[", "").replace("]", "") : "Sin paradas";
	            return new SimpleStringProperty(ids); // Devuelve la lista como String limpio
	        });
		
		List<Servicio> misServicios = servicioService.obtenerTodosLosServicios();
		
		// Imprimir los servicios recuperados
        System.out.println("Servicios recuperados desde DB4O:");
        for (Servicio s : misServicios) 
        {
            System.out.println(s.toString());
        }
	
		// Convertimos la lista a un Observable
		ObservableList<Servicio> miObservable = FXCollections.observableArrayList(misServicios);
		
		// Por ultimo cargar a la tabla
		tablaServicios.setItems(miObservable);
		}
		
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}				
	}
	
	


	
	@FXML
	private void asignarServicio() {
	    try {
	        // 1️⃣ Obtener el servicio seleccionado
	        Servicio miServicio = tablaServicios.getSelectionModel().getSelectedItem();
	        if (miServicio == null) {
	            mostrarAlerta("Error", "Seleccione un servicio antes de asignar paradas.", Alert.AlertType.ERROR);
	            return;
	        }
	        System.out.println("Servicio seleccionado: " + miServicio.getNombre());

	        // 2️⃣ Obtener las paradas seleccionadas
	        List<Parada> paradasSeleccionadas = new ArrayList<>(tablaParadas.getSelectionModel().getSelectedItems());
	        if (paradasSeleccionadas.isEmpty()) {
	            mostrarAlerta("Error", "Seleccione al menos una parada para asignar.", Alert.AlertType.ERROR);
	            return;
	        }
	        System.out.println("Paradas seleccionadas: " + paradasSeleccionadas);

	        // 3️⃣ Evitar duplicados en los IDs de paradas
	        List<Long> idParadasActuales = new ArrayList<>(miServicio.getIdParada()); // Copia segura de la lista actual
	        List<Long> nuevasParadas = new ArrayList<>();

	        for (Parada parada : paradasSeleccionadas) {
	            if (!idParadasActuales.contains(parada.getId())) { // Solo agregar si no está ya en la lista
	                nuevasParadas.add(parada.getId());
	            }
	        }

	        if (nuevasParadas.isEmpty()) {
	            mostrarAlerta("Información", "Todas las paradas seleccionadas ya están asignadas a este servicio.", Alert.AlertType.WARNING);
	            return;
	        }

	        // 4️⃣ Actualizar el servicio con los nuevos IDs de paradas
	        idParadasActuales.addAll(nuevasParadas); // Agregar las nuevas paradas a la lista
	        miServicio.setIdParada(idParadasActuales); // Asignar la lista actualizada al servicio
	        System.out.println("Lista total de paradas en servicio: " + miServicio.getIdParada());

	        // 5️⃣ Guardar cambios en DB4O
	        servicioService.asignarParadasAServicio(miServicio.getId(), idParadasActuales);

	        // Refrescar tabla con la lista actualizada
	        cargarColumnasServicios();

	        // Confirmación al usuario
	        mostrarAlerta("Éxito", "El servicio ha sido asignado correctamente a las paradas seleccionadas.", Alert.AlertType.CONFIRMATION);

	    } catch (Exception e) {
	        System.out.println("Error en el método asignarServicio: " + e.getMessage());
	        e.printStackTrace();
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
