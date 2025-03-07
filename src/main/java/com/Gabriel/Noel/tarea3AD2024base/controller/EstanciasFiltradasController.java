package com.Gabriel.Noel.tarea3AD2024base.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.Gabriel.Noel.tarea3AD2024base.config.StageManager;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Credenciales;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Parada;
import com.Gabriel.Noel.tarea3AD2024base.services.EstanciaService;
import com.Gabriel.Noel.tarea3AD2024base.services.ParadaService;
import com.Gabriel.Noel.tarea3AD2024base.view.FxmlView;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
/**

 * Esta clase define los metodos que del cotrolador de las vista del filtrado de estancias donde se definen los metodos que capturan los datos de la ventana y si tienen el formato deseado los inserta en la base de datos 
 * y tambien muestra los datos filtrados por las fechas que indique el usuario 
 * @author: Gabriel - Noel
 * @version: 06/03/2025
 */
@Controller
public class EstanciasFiltradasController {

	@FXML
	private MenuItem menusalir;
	@FXML
	private Button btnLogout;
	
	private String nombreParada;
	
	@FXML
	private Label nombreDeLaParada;

	@FXML
	private Button botonFiltrarEstancias;

	@FXML
	private Button botonReseteo;

	@FXML
	private Button botonVolverAtras;

	@FXML
	private TableView<PeregrinoTablaController> tablaPeregrinosFiltrados;

	@FXML
	private TableColumn<PeregrinoTablaController, Long> colPeregrinoID;

	@FXML
	private TableColumn<PeregrinoTablaController, String> colNombre;

	@FXML
	private TableColumn<PeregrinoTablaController, String> colFechaSellado;

	@FXML
	private TableColumn<PeregrinoTablaController, String> colSeEstancio;

	@FXML
	private TableColumn<PeregrinoTablaController, String> colEsVIP;

	@FXML
	private DatePicker fechaFiltradoInicio;

	@FXML
	private DatePicker fechaFiltradoFin;

	@Autowired
	private EstanciaService estanciaService;
	
	@Autowired
	private ParadaService paradaService; 

	@Lazy
	@Autowired
	private StageManager stageManager;
	
	@FXML
	private MenuItem ayuda;
	
	@FXML
	private MenuItem salir;

	private Parada paradaActual;

    /**
     * Método de inicialización del controlador.
     * Carga las columnas de la tabla y obtiene la parada actual desde el controlador de responsables.
     */
	@FXML
	private void initialize() {
		cargarColumnas();
		paradaActual = ResponsableParadaController.getParada();
		inicializarParadaActual();
	}
	
	
	@FXML
	public void AyudaJavaFX() {
		try {
			System.out.println("entro a la funcion de ayuda pero no cargo la ventana");
			// Crear un WebView para mostrar la ayuda
			WebView webView = new WebView();

			// Cargar el archivo HTML desde los recursos
			String url = getClass().getResource("/ayuda/AyudaEstanciasFiltradas.html").toExternalForm();
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
	


    /**
     * Configura las columnas de la tabla con los valores correspondientes.
     */
	private void cargarColumnas() {
		colPeregrinoID.setCellValueFactory(new PropertyValueFactory<>("id"));
		colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		colFechaSellado.setCellValueFactory(new PropertyValueFactory<>("fechaSellado"));
		colSeEstancio.setCellValueFactory(new PropertyValueFactory<>("seEstancio"));
		colEsVIP.setCellValueFactory(new PropertyValueFactory<>("esVIP"));
	}
	
	@FXML
	private void filtrarEstancias() {
	    try {
	        LocalDate fechaInicio = fechaFiltradoInicio.getValue();
	        LocalDate fechaFin = fechaFiltradoFin.getValue();

	        if (fechaInicio == null || fechaFin == null) {
	            mostrarAlerta("Error", "Debes seleccionar fechas válidas y tener una parada asignada.", Alert.AlertType.ERROR);
	            return;
	        }
	        
	        if (fechaInicio.isAfter(fechaFin))
	        {
	        	mostrarAlerta("Error", "La fecha de Inicio no debe ser posterior a la de Fin", Alert.AlertType.ERROR);
	        	return;
	        }

	        // Obtener las estancias directamente como objetos PeregrinoTabla
	        List<PeregrinoTablaController> estancias = estanciaService.obtenerEstanciasFiltradas(paradaActual.getId(), fechaInicio, fechaFin);

	        if (estancias.isEmpty()) 
	        {
	            mostrarAlerta("Información", "No se encontraron sellados en este rango de fechas.", Alert.AlertType.INFORMATION);
	            tablaPeregrinosFiltrados.getItems().clear();
	            return;
	        }

	        // Convertir la lista en un ObservableList para la tabla
	        ObservableList<PeregrinoTablaController> peregrinosTabla = FXCollections.observableArrayList(estancias);

	        // Asignar los valores a la tabla
	        tablaPeregrinosFiltrados.setItems(peregrinosTabla);

	    } catch (Exception e) {
	        mostrarAlerta("Error", "Ha ocurrido un error al filtrar las estancias: " + e.getMessage(), Alert.AlertType.ERROR);
	    }
	}

	/**
	 * Metodo para mostrar alertas en la vista
	 * @param titulo
	 * @param mensaje
	 * @param tipo
	 */
	private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
		Alert alerta = new Alert(tipo);
		alerta.setTitle(titulo);
		alerta.setContentText(mensaje);
		alerta.showAndWait();
	}

	/**
	 * Cambia la vista para ir al login
	 */
	@FXML
	private void volverALogin() {
		stageManager.switchScene(FxmlView.LOGIN);
	}

	/**
	 * Cambia la vista para volver a la ventana del Responsable de Parada
	 */
	@FXML
	private void volverAtras() {
		stageManager.switchScene(FxmlView.RESPONSABLE);
	}

	/**
	 * Metodo asignado al Boton de reseteo para resetear las fechas 
	 */
	@FXML
	private void resetearFechas() {
		fechaFiltradoInicio.setValue(null);
		fechaFiltradoFin.setValue(null);
	}
	
	@FXML
	/**
	 *  Metodo para inicializar la parada actual
	 */
	private void inicializarParadaActual()
	{ 
		try
		{
			// Obtengo la credencial entera a traves del nombre de usuario que se ha logueado
			Credenciales miCredencial = CredencialesController.Credenciales_usuario; //se llama asi  y no con un  getter por que el objetivo es no tener que declarar un  objeto de la clasepara usarlo  o recibir sus datos , de momentyo por no borrar mas codigo lo dejo asi pero  podia recortarse mas codigo un 
			
			
			// Asigno la parada buscada a traves del objeto credenciales
			paradaActual = paradaService.buscarParadaPorCredenciales(miCredencial); // metiendo aqui directamente CredencialesController.Credenciales_usuario
			
			// asigno el valor del nombre de la parada
			nombreParada = paradaActual.getNombre();
		}
		
		catch(Exception e)
		{
			System.out.println("Error en el metodo inicializarParadaActual()");
		}
		
		// Le damos a la label el nombre de la parada
		nombreDeLaParada.setText(nombreParada);
	}
	
	@FXML
	private void Salir() {
		Alert miAlerta = new Alert(AlertType.CONFIRMATION);
		miAlerta.setTitle("Salir");
		miAlerta.setContentText("seguro que quiere salir?");
		Optional<ButtonType> resultado = miAlerta.showAndWait();

		if(resultado.get()==ButtonType.OK) {
			System.exit(0);
		}
	}
}
