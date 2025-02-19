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
	
	
	@Lazy
	@Autowired
	private StageManager stageManager;
	
	
	@FXML
	public void initialize()
	{
		cargarColumnasParadas();
		cargarColumnasServicios();
		
	}
	
	/**
	 * Metodo para crear el servicio cogiendo los dos resultados de nuestros TextField
	 * 1º Obtenemos primero la lista de los servicios que existan en nuestra DB4O
	 * 2º Generamos un nuevo ID en base al tamaño que haya de Servicios sumando +1
	 * 3º Creamos el objeto Servicio
	 * 4º Llamamos al metodo de la Capa del Service pasandole por parametro nuestro objeto que hemos creado
	 */
	@FXML
	private void crearServicio() {

	    // Obtener valores de los campos de texto
	    String nombre = textServicio.getText();
	    Double precio = Double.parseDouble(textPrecio.getText());

	    // Obtener la lista de servicios existentes
	    List<Servicio> listaServicios = servicioService.obtenerTodosLosServicios();
	    
	    for(Servicio s : listaServicios)
	    {
	    	if (s.getNombre().equals(nombre))
	    	{
	    		mostrarAlerta("Error", "No se puede registrar un servicio con el mismo nombre", AlertType.WARNING);
	    		return;
	    	}
	    }

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
	    
	    mostrarAlerta("Exito","Ha guardado el servicio correctamente", AlertType.CONFIRMATION);

	    // Limpiar campos
	    textServicio.clear();
	    textPrecio.clear();

	    // Recargar la tabla para reflejar cambios
	    cargarColumnasServicios();
	}


	

	
	/**
	 * Metodo para cerrar sesion y volver al Login.
	 * y cerrar conexion con DB4O
	 */
	@FXML
	private void volverALogin() 
	{
		try 
		{
//			servicioService.cerrarConexion();
//			mostrarAlerta("Información", "Sesion cerrada con DB4O correctamente", Alert.AlertType.INFORMATION);
			stageManager.switchScene(FxmlView.LOGIN);
		}

		catch (Exception e) 
		{
			System.out.println("Error en el metodo volverALogin");
		}
	}	
	
	/**
	 * Método que carga las columnas de la tabla de paradas con los datos almacenados en la base de datos.
	 * 
	 * Este método configura la tabla para permitir la selección múltiple de filas, 
	 * define las propiedades de las columnas y recupera la lista de paradas desde el servicio correspondiente.
	 * Luego, convierte esta lista en un objeto `ObservableList` para su correcta visualización en la UI.
	 * 
	 * En caso de error durante la carga de datos, se captura la excepción y se muestra un mensaje en la consola.
	 */
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
	
	
	/**
	 * 
	 */
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
	
	

	/**
	 * Asigna una o varias paradas a un servicio seleccionado, evitando duplicados y 
	 * asegurando la persistencia en la base de datos DB4O.
	 * 
	 * Este método permite que un servicio tenga múltiples paradas asignadas y 
	 * evita agregar paradas que ya estaban previamente asignadas. 
	 * Si no se selecciona un servicio o paradas, se muestra una alerta informando al usuario.
	 * 
	 * El proceso sigue los siguientes pasos:
	 * 
	 * Verifica que el usuario haya seleccionado un servicio.
	 * Obtiene las paradas seleccionadas de la tabla.
	 * Filtra las paradas para evitar duplicados en la lista.
	 * Actualiza el servicio con las nuevas paradas.
	 * Guarda los cambios en la base de datos a través del servicio.
	 * Refresca la interfaz gráfica para reflejar los cambios.
	 * Muestra un mensaje de confirmación si la asignación fue exitosa.
	 * 
	 * 
	 * Si todas las paradas seleccionadas ya estaban asignadas, se muestra un mensaje de advertencia.
	 * 
	 * @throws Exception Si ocurre un error durante la asignación de paradas.
	 */
	@FXML
	private void asignarServicio() {
	    try {
	        // Obtener el servicio seleccionado
	        Servicio miServicio = tablaServicios.getSelectionModel().getSelectedItem();
	        
	        // Comprueba de que selecciono un servicio antes de asignarlo
	        if (miServicio == null) 
	        {
	            mostrarAlerta("Error", "Seleccione un servicio antes de asignar paradas.", Alert.AlertType.ERROR);
	            return;
	        }
	        
	        // Debug por pantalla
	        System.out.println("Servicio seleccionado: " + miServicio.getNombre());

	        // Obtener las paradas seleccionadas de la tabla de paradas
	        List<Parada> paradasSeleccionadas = new ArrayList<>(tablaParadas.getSelectionModel().getSelectedItems());
	        
	        // Si no selecciona ninguna parada le saldra una alerta para que escoja una
	        if (paradasSeleccionadas.isEmpty()) 
	        {
	            mostrarAlerta("Error", "Seleccione al menos una parada para asignar.", Alert.AlertType.ERROR);
	            return;
	        }
	        
	        // Debug para que saque por pantalla las paradas seleccionadas
	        System.out.println("Paradas seleccionadas: " + paradasSeleccionadas);

	        
	        
	        //Evitar duplicados en los IDs de paradas
	        List<Long> idParadasActuales = new ArrayList<>(miServicio.getIdParada()); // Creamos una lista con los ID de las paradas asignadas al Servicio
	        
	        List<Long> nuevasParadas = new ArrayList<>(); // Sera para guardar las nuevas paradas que aun no estan asignadas

	        // Solo añadira las paradas que no estan previamente asignadas
	        for (Parada parada : paradasSeleccionadas) 
	        {
	            if (!idParadasActuales.contains(parada.getId())) // Solo agregar si no está ya en la lista
	            { 
	                nuevasParadas.add(parada.getId());
	            }
	        }

	        // Si la alerta salta, es que no hay ningun ID para asignar a lo que ya todas las paradas seleccionadas
	        // Tienen asignado el servicio que se quiere asignar.
	        if (nuevasParadas.isEmpty()) 
	        {
	            mostrarAlerta("Información", "Todas las paradas seleccionadas ya están asignadas a este servicio.", Alert.AlertType.WARNING);
	            return;
	        }

	        //Actualizar el servicio con los nuevos IDs de paradas
	        idParadasActuales.addAll(nuevasParadas); // Agregar las nuevas paradas a la lista
	        miServicio.setIdParada(idParadasActuales); // Asignar la lista actualizada al servicio
	        
	        System.out.println("Lista total de paradas en servicio: " + miServicio.getIdParada());

	        //Guardar cambios en DB4O
	        servicioService.asignarParadasAServicio(miServicio.getId(), idParadasActuales);

	        //Refrescar tabla con la lista actualizada
	        tablaServicios.refresh();
       
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
