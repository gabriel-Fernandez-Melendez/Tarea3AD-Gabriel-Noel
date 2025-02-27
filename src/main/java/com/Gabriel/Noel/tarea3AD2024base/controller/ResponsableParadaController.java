package com.Gabriel.Noel.tarea3AD2024base.controller;

import java.time.LocalDate;
import java.util.ArrayList;
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
import com.Gabriel.Noel.tarea3AD2024base.modelo.ConjuntoContratado;
import com.Gabriel.Noel.tarea3AD2024base.services.CarnetService;
import com.Gabriel.Noel.tarea3AD2024base.services.ConjuntoContradadoService;
import com.Gabriel.Noel.tarea3AD2024base.services.EstanciaService;
import com.Gabriel.Noel.tarea3AD2024base.services.ParadaSelladaService;
import com.Gabriel.Noel.tarea3AD2024base.services.ParadaService;
import com.Gabriel.Noel.tarea3AD2024base.services.PeregrinoService;
import com.Gabriel.Noel.tarea3AD2024base.services.ServiciosService;
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
	private Button botonEnvios;

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
	private TableColumn<Servicio, Long> id_servicio;
	@FXML
	private TableColumn<Servicio, String> nombre_servicio;
	@FXML
	private TableColumn<Servicio, Double> precio_servicio;
	@FXML
	private TableColumn<Servicio, Long> id_serviciocontratado;
	@FXML
	private TableColumn<Servicio, String> nombre_serviciocontratado;
	@FXML
	private TableColumn<Servicio, Double> precio_contratados;
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

	@Autowired
	ConjuntoContradadoService ConjuntoContratado;

	@FXML
	private ToggleGroup grupo;

	@Autowired
	private ServiciosService servicioService;

	// metodos de la nuevo implementacion(revisar si la asignacion del char funciona
	// correctamente)
	private void GrupoPagos() {
		grupo = new ToggleGroup();
		tarjeta.setToggleGroup(grupo);
		efectivo.setToggleGroup(grupo);
		bizum.setToggleGroup(grupo);
		tarjeta.setText("T");
		efectivo.setText("E");
		bizum.setText("B");
	}

	private void GuardarConjunto() {
		grupo.getSelectedToggle();
		List<Servicio> servicios = new ArrayList<>(servicios_contratados.getItems());
		ConjuntoContratado c = new ConjuntoContratado();
		// en funcion del tamañalo de la lista creamos el id
		Long id = (long) servicios.size() + 1;
		c.setId(id);
		List<Servicio> servicios_aux = new ArrayList<>();
		Double precio = 0.0;
		for (Servicio s : servicios) {
			servicios_aux.add(s);
			precio += s.getPrecio();
		}
		System.out.println("el precio es" + precio);
		c.setServicios((ArrayList<Servicio>) servicios_aux);
		c.setPrecio_total(precio);
		RadioButton seleccion = (RadioButton) grupo.getSelectedToggle();
		c.setModo_de_pago(seleccion.getText().charAt(0));
		System.out.println("Se ha creado el objeto" + c.toString());
		ConjuntoContratado.GuardarConjunto(c);
	}

	private void CargarServicios() {
		Parada parada_aux = new Parada();

		ArrayList<Servicio> servicios = (ArrayList<Servicio>) servicioService.obtenerTodosLosServicios();

		ArrayList<Servicio> servicios_filtrados = new ArrayList<Servicio>();

		ArrayList<Parada> paradas = (ArrayList<Parada>) paradaService.ListaDeParadas();

		for (Parada p : paradas)

		{

			if (CredencialesController.Credenciales_usuario.getNombreUsuario().matches(p.getResponsable())) {
				parada_aux = p;
				for (Servicio s : servicios)

				{
					if (s.getNombreParadas().contains(parada_aux.getNombre()))

					{
						servicios_filtrados.add(s);
					}

				}

				tabla_servicios.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

				id_servicio.setCellValueFactory(new PropertyValueFactory<>("id"));
				nombre_servicio.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
				precio_servicio.setCellValueFactory(new PropertyValueFactory<>("Precio"));

				ObservableList<Servicio> lista = FXCollections.observableArrayList(servicios_filtrados); // esta es la
																											// lista con
																											// los
																											// campos
																											// filtrados
																											// que es la
																											// que hay
																											// que

				tabla_servicios.setItems(lista);
			}
		}
	}

	// habia que inicializar la llamada de la tabla
	private void Inicializar_tabla() {

		servicios_contratados.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		id_serviciocontratado.setCellValueFactory(new PropertyValueFactory<>("id"));
		nombre_serviciocontratado.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
		precio_contratados.setCellValueFactory(new PropertyValueFactory<>("Precio"));
	}

	@FXML
	private void PasarServicio() {
		System.out.println("entra en el metodo");
		ObservableList<Servicio> s = tabla_servicios.getSelectionModel().getSelectedItems();
		if (s == null) {
			mostrarAlerta("Seleccione un servicio", "no tiene ningun servicio seleccionado", AlertType.WARNING);
		} else {
			servicios_contratados.getItems().addAll(s);
		}
	}

	@FXML
	private void EliminarSeleccion() {
		ObservableList<Servicio> s = servicios_contratados.getSelectionModel().getSelectedItems();
		servicios_contratados.getItems().removeAll(s);
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
		// nuevos metodos al momento de inicializar la ventana
		CargarServicios();
		Inicializar_tabla();
		grupo = new ToggleGroup();
		tarjeta.setToggleGroup(grupo);
		efectivo.setToggleGroup(grupo);
		bizum.setToggleGroup(grupo);
		tarjeta.setText("T");
		efectivo.setText("E");
		bizum.setText("B");
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
		Estancia nuevaEstancia = new Estancia();
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

			// VERIFICAR SI EL PEREGRINO HA CONTRATADO EL SERVICIO ENVIO A CASA
			boolean tieneEnvioACasa = servicios_contratados.getItems().stream()
					.anyMatch(servicio -> servicio.getNombre().equalsIgnoreCase("Envio a Casa"));

			// Validar si ya existe el sellado antes de proceder
			ParadaSellada miParadaSellada = new ParadaSellada();
			miParadaSellada.setPeregrino(peregrinoSeleccionado);
			miParadaSellada.setParada(paradaActual);
			miParadaSellada.setFechaParada(LocalDate.now());

			// Creamos un objeto carnet y lo obtenemos del peregrino que hemos seleccionado
			// de la tabla
			Carnet carnet = peregrinoSeleccionado.getCarnet();

			// Sumamos 5.0Km a su carnet
			carnet.setDistancia(carnet.getDistancia() + 5.0);

			// Si se Hospeda y es VIP sumamos a su carnet en el campo VIP +1
			if (seHospeda && esVip) {
				carnet.setNvips(carnet.getNvips() + 1);
			}

			System.out.println("El servicio es" + tieneEnvioACasa);

			// Guardamos el carnet Ó Actualizamos
			// ELIMINO

			/**
			 * Para el caso de que se Hospede el peregrino Recogemos todos los datos que
			 * almacena la entidad ESTANCIA Cogemos la fecha de hoy Cogemos el campo esVIP
			 * Cogemos la Parada actual del responsable de la parada Cogemos el objeto
			 * peregrino entero seleccionado de la tabla
			 */
			if (seHospeda) {

				nuevaEstancia.setFecha(LocalDate.now());
				nuevaEstancia.setVip(esVip);
				nuevaEstancia.setParada(paradaActual);
				nuevaEstancia.setPeregrino(peregrinoSeleccionado);

				// Guardamos la nueva estancia del peregrino que se ha hospedado

			}

			System.out.println("Se hospeda?" + seHospeda);
			if (seHospeda && esVip || seHospeda && !esVip) {
				estanciaService.guardarEstancia(nuevaEstancia);
				ParadaSellada p = paradaSelladaService.guardarParadaSellada(miParadaSellada);
				if (p == null) {
					mostrarAlerta("Error", "El peregrino ya ha sellado en esta parada en la misma fecha.",
							Alert.AlertType.ERROR);
					return;
				}
				// NUEVA IMPLEMENTACION PARA RELLENAR EL FORMULARIO DE ENVIO A CASA
				if (seHospeda && tieneEnvioACasa && grupo.getSelectedToggle() != null) {
					carnetService.GuardarCarnet(carnet); // pendiente de localizar
					mostrarAlerta("Éxito", "Carnet sellado correctamente.", Alert.AlertType.INFORMATION);
					GuardarConjunto();
					mostrarAlerta("Información", "Redirigiendo al formulario de Envío a Casa.",
							Alert.AlertType.INFORMATION);
					stageManager.switchScene(FxmlView.EnvioaCasa);
					return;
				} else {
					mostrarAlerta("Metodo de pago fallido",
							"seleccione un campo para poder continuar con el proceso de compra", AlertType.ERROR);
				}

			}
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

	@FXML
	private void irVerEnvios() {
		try {
			stageManager.switchScene(FxmlView.EnviosRealizados);
		}

		catch (Exception e) {
			System.out.println("Error en el metodo irVerEnvios" + e.getMessage());
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
		Alert miAlerta = new Alert(AlertType.CONFIRMATION);
		miAlerta.setTitle("Salir");
		miAlerta.setContentText("seguro que quiere salir?");
		Optional<ButtonType> resultado = miAlerta.showAndWait();

		if (resultado.get() == ButtonType.OK) {
			System.exit(0);
		}
	}

}
