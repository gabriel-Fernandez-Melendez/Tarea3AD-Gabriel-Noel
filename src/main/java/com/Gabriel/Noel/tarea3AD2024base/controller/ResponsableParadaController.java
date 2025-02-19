package com.Gabriel.Noel.tarea3AD2024base.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.Gabriel.Noel.tarea3AD2024base.config.StageManager;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Carnet;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Credenciales;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Estancia;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Parada;
import com.Gabriel.Noel.tarea3AD2024base.modelo.ParadaSellada;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Peregrino;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Servicio;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Usuarios;
import com.Gabriel.Noel.tarea3AD2024base.services.CarnetService;
import com.Gabriel.Noel.tarea3AD2024base.services.CredencialesService;
import com.Gabriel.Noel.tarea3AD2024base.services.EstanciaService;
import com.Gabriel.Noel.tarea3AD2024base.services.ParadaSelladaService;
import com.Gabriel.Noel.tarea3AD2024base.services.ParadaService;
import com.Gabriel.Noel.tarea3AD2024base.services.PeregrinoService;
import com.Gabriel.Noel.tarea3AD2024base.view.FxmlView;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Controlador de la vista del responsable de la parada. Permite visualizar los
 * peregrinos que han sellado su carnet, gestionar su hospedaje y verificar su
 * estado en la parada.
 */
@Controller
public class ResponsableParadaController {

	private static Parada paradaActual;

	private String nombreParada;

	@FXML
	private Button btnLogout;
	@FXML
	private MenuItem menusalir;
	@FXML
	private TableView<Peregrino> tablaPeregrinos;

	@FXML
	private TableColumn<Peregrino, Long> colPeregrinoID;

	@FXML
	private TableColumn<Peregrino, String> colNombre;

	@FXML
	private TableColumn<Peregrino, String> colNacionalidad;

	@FXML
	private TableColumn<Peregrino, Long> colIdCarnet;

	@FXML
	private TableColumn<Peregrino, Long> colIdCredenciales;

	@FXML
	private Button irAFiltrarEstancias;

	@FXML
	private CheckBox checkEsVIP;

	@FXML
	private CheckBox checkHospedaje;

	@FXML
	private Button botonSellarCarnet;

	@FXML
	private Label labelParadaResponsable;

	@Autowired
	private PeregrinoService peregrinoService;

	@Autowired
	private CarnetService carnetService;

	@Autowired
	private EstanciaService estanciaService;

	@Autowired
	private ParadaSelladaService paradaSelladaService;

	@Autowired
	private ParadaService paradaService;

	@Lazy
	@Autowired
	private StageManager stageManager;

	// Atributos de las tablas y radiobuttons en relacion a la entrega de DB4O
	@FXML
	private TableView<Servicio> tabla_servicios;
	@FXML
	private TableView<Servicio> servicios_contratados;
	@FXML
	private TableColumn<Servicio,Long> id_servicio;
	@FXML
	private TableColumn<Servicio,String> nombre_servicio;
	@FXML
	private TableColumn<Servicio,Double> precio_servicio;
	@FXML
	private TableColumn<Servicio,Long> id_serviciocontratado;
	@FXML
	private TableColumn<Servicio,String> nombre_serviciocontratado;
	@FXML
	private TableColumn<Servicio,Double> precio_contratados;
	@FXML
	private Button añadir;
	@FXML
	private Button remover;
	@FXML
	private RadioButton tarjeta;
	@FXML
	private RadioButton efectivo;
	@FXML
	private RadioButton bizum;
	
	
	
	
	//metodos de la nuevo implementacion(revisar si la asignacion del char funciona correctamente)
	private void GrupoPagos() {
		ToggleGroup grupo = new ToggleGroup();
		tarjeta.setToggleGroup(grupo);
		efectivo.setToggleGroup(grupo);
		bizum.setToggleGroup(grupo);
		tarjeta.setText("T");
		efectivo.setText("E");
		bizum.setText("B");
	}
	/**
	 * Método de inicialización del controlador. Carga los datos de la tabla de
	 * peregrinos y asigna la parada actual para ver el nombre de la parada
	 */
	@FXML
	public void initialize() {
		cargarColumnas();
		cargarPeregrinos();
		inicializarParadaActual();
		//nuevos metodos al momento de inicializar la ventana
		GrupoPagos();
	}

	@FXML
	public void AyudaJavaFX() {
		try {
			System.out.println("entro a la funcion de ayuda pero no cargo la ventana");
			// Crear un WebView para mostrar la ayuda
			WebView webView = new WebView();

			// Cargar el archivo HTML desde los recursos
			String url = getClass().getResource("/ayuda/Ayuda.html").toExternalForm();
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
	 * Cambia la vista a la pantalla de filtrado de estancias.
	 */
	@FXML
	private void redirigirEstanciasFiltradas() {
		try {
			stageManager.switchScene(FxmlView.ESTANCIAS_FILTRADAS);
		}

		catch (Exception e) {
			mostrarAlerta("Error", "No se pudo cambiar de ventana a Estancias Filtradas", Alert.AlertType.ERROR);
			System.out.println("Error en el metodo redirigirEstanciasFiltradas");
		}
	}

	/**
	 * Sella el carnet de un peregrino y, si corresponde, registra su estancia en la
	 * parada.
	 */
	@FXML
	private void sellarCarnet() {
		try {
			Peregrino peregrinoSeleccionado = tablaPeregrinos.getSelectionModel().getSelectedItem(); // <-- GABRIEL

			if (peregrinoSeleccionado == null) {
				mostrarAlerta("Error", "Por favor, selecciona a un peregrino de la lista.", Alert.AlertType.ERROR);
				return;
			}

			boolean seHospeda = checkHospedaje.isSelected();
			boolean esVip = checkEsVIP.isSelected();

			// Si selecciona VIP y no es VIP al mismo tiempo, saltara la alerta
			if (esVip && !seHospeda) {
				mostrarAlerta("Error", "Si selecciona Es VIP, tiene que seleccionar la casilla con el icono de Hotel",
						Alert.AlertType.ERROR);
				return;
			}

			// Validar si ya existe el sellado antes de proceder
			ParadaSellada miParadaSellada = new ParadaSellada();
			miParadaSellada.setPeregrino(peregrinoSeleccionado);
			miParadaSellada.setParada(paradaActual);
			miParadaSellada.setFechaParada(LocalDate.now());

			if (paradaSelladaService.guardarParadaSellada(miParadaSellada) == null) {
				mostrarAlerta("Error", "El peregrino ya ha sellado en esta parada en la misma fecha.",
						Alert.AlertType.ERROR);
				return;
			}

			// Creamos un objeto carnet y lo obtenemos del peregrino que hemos seleccionado
			// de la tabla
			Carnet carnet = peregrinoSeleccionado.getCarnet();

			// Sumamos 5.0Km a su carnet
			carnet.setDistancia(carnet.getDistancia() + 5.0);

			// Si se Hospeda y es VIP sumamos a su carnet en el campo VIP +1
			if (seHospeda && esVip) {
				carnet.setNvips(carnet.getNvips() + 1);
			}

			// Guardamos el carnet Ó Actualizamos
			carnetService.GuardarCarnet(carnet);

			/**
			 * Para el caso de que se Hospede el peregrino Recogemos todos los datos que
			 * almacena la entidad ESTANCIA Cogemos la fecha de hoy Cogemos el campo esVIP
			 * Cogemos la Parada actual del responsable de la parada Cogemos el objeto
			 * peregrino entero seleccionado de la tabla
			 */
			if (seHospeda) {
				Estancia nuevaEstancia = new Estancia();
				nuevaEstancia.setFecha(LocalDate.now());
				nuevaEstancia.setVip(esVip);
				nuevaEstancia.setParada(paradaActual);
				nuevaEstancia.setPeregrino(peregrinoSeleccionado);

				// Guardamos la nueva estancia del peregrino que se ha hospedado
				estanciaService.guardarEstancia(nuevaEstancia);
			}

			mostrarAlerta("Éxito", "Carnet sellado correctamente.", Alert.AlertType.INFORMATION);

		}

		catch (Exception e) {
			mostrarAlerta("Error", "No se pudo sellar el carnet: " + e.getMessage(), Alert.AlertType.ERROR);
		}
	}

	/**
	 * Metodo para cerrar sesion y volver al Login.
	 */
	@FXML
	private void volverALogin() {
		try {
			stageManager.switchScene(FxmlView.LOGIN);
		}

		catch (Exception e) {
			System.out.println("Error en el metodo volverALogin");
		}
	}

	/**
	 * Cargar Columnas del TableView
	 */
	private void cargarColumnas() {
		colPeregrinoID.setCellValueFactory(new PropertyValueFactory<>("id"));
		colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		colNacionalidad.setCellValueFactory(new PropertyValueFactory<>("nacionalidad"));

		colIdCarnet.setCellValueFactory(cellData -> {
			Peregrino peregrino = cellData.getValue();
			return new SimpleObjectProperty<>(peregrino.getCarnet() != null ? peregrino.getCarnet().getId() : null);
		});

		colIdCredenciales.setCellValueFactory(cellData -> {
			Peregrino peregrino = cellData.getValue();
			return new SimpleObjectProperty<>(
					peregrino.getCredenciales() != null ? peregrino.getCredenciales().getId() : null);
		});

	}

	/**
	 * Cargar la lista de los peregrinos
	 */
	private void cargarPeregrinos() {

		try {
			// Obtenermos los peregrinos
			List<Peregrino> listaPeregrinos = peregrinoService.ListaDePeregrinos();

			ObservableList<Peregrino> datos = FXCollections.observableArrayList(listaPeregrinos);

			tablaPeregrinos.setItems(datos);

		}

		catch (Exception e) {
			System.out.println("Error en el metodo cargarPeregrinos()");
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

	//
	public void setParada(Parada parada) {
		ResponsableParadaController.paradaActual = parada;
	}

	public static Parada getParada() {
		return paradaActual;
	}

	/**
	 * Metodo para inicializar la parada actual
	 */
	private void inicializarParadaActual() {
		try {
			// Obtengo la credencial entera a traves del nombre de usuario que se ha
			// logueado
			Credenciales miCredencial = CredencialesController.getCredenciales();

			// Asigno la parada buscada a traves del objeto credenciales
			paradaActual = paradaService.buscarParadaPorCredenciales(miCredencial);

			// asigno el valor del nombre de la parada
			nombreParada = paradaActual.getNombre();
		}

		catch (Exception e) {
			System.out.println("Error en el metodo inicializarParadaActual()");
		}

		// Le damos a la label el nombre de la parada
		labelParadaResponsable.setText(nombreParada);
	}

	@FXML
	private void Salir() {
		Boolean salir = false;
		Alert miAlerta = new Alert(AlertType.CONFIRMATION);
		miAlerta.setTitle("Salir");
		miAlerta.setContentText("seguro que quiere salir?");
		Optional<ButtonType> resultado = miAlerta.showAndWait();

		if (resultado.get() == ButtonType.OK) {
			System.exit(0);
		}
	}

}
