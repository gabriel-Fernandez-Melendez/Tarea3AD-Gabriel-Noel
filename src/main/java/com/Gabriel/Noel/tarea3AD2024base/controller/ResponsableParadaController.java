package com.Gabriel.Noel.tarea3AD2024base.controller;


import java.time.LocalDate;
import java.util.List;

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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

@Controller
public class ResponsableParadaController {

	private static Parada paradaActual;
	
	private String nombreParada;

	@FXML
	private Button btnLogout;

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
	private CheckBox checkNoEsVIP;

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
	private CredencialesService credencialesService;
	
	@Autowired
	private ParadaService paradaService;

	@Lazy
	@Autowired
	private StageManager stageManager;

	// Metodo inicializar para cargar datos a la tableView
	@FXML
	public void initialize() {
		cargarColumnas();
		cargarPeregrinos();
		inicializarParadaActual();
	}

	// Metodo para cambiar de ventana a Filtrar Estancias
	@FXML
	private void redirigirEstanciasFiltradas() {
		try {
			stageManager.switchScene(FxmlView.ESTANCIAS_FILTRADAS);
		}

		catch (Exception e) {
			System.out.println("Error en el metodo redirigirEstanciasFiltradas");
		}
	}

	// Metodo para sellar el carnet de un peregrino
	 @FXML
	    private void sellarCarnet() {
	        try {
	            Peregrino peregrinoSeleccionado = tablaPeregrinos.getSelectionModel().getSelectedItem();

	            if (peregrinoSeleccionado == null) {
	                mostrarAlerta("Error", "Por favor, selecciona a un peregrino de la lista.", Alert.AlertType.ERROR);
	                return;
	            }

	            boolean seHospeda = checkHospedaje.isSelected();
	            boolean esVip = checkEsVIP.isSelected();
	            boolean noEsVip = checkNoEsVIP.isSelected();

	            if (esVip && noEsVip) {
	                mostrarAlerta("Error", "No puedes seleccionar 'Es VIP' y 'No es VIP' al mismo tiempo.", Alert.AlertType.ERROR);
	                return;
	            }

	            if (seHospeda && (!esVip && !noEsVip)) {
	                mostrarAlerta("Error", "Si te hospedas, debes seleccionar si es VIP o no.", Alert.AlertType.ERROR);
	                return;
	            }

	            if (!seHospeda && (esVip || noEsVip)) {
	                mostrarAlerta("Error", "No puedes seleccionar 'Es VIP' o 'No es VIP' si no te hospedas.", Alert.AlertType.ERROR);
	                return;
	            }

	            Carnet carnet = peregrinoSeleccionado.getCarnet();

	            carnet.setDistancia(carnet.getDistancia() + 5.0);

	            if (seHospeda && esVip) {
	                carnet.setNvips(carnet.getNvips() + 1);
	            }

	            carnetService.GuardarCarnet(carnet);

	            if (seHospeda) 
	            {
	                Estancia nuevaEstancia = new Estancia();
	                nuevaEstancia.setFecha(LocalDate.now());
	                nuevaEstancia.setVip(esVip);
	                nuevaEstancia.setParada(paradaActual);
	                nuevaEstancia.setPeregrino(peregrinoSeleccionado);

	                estanciaService.guardarEstancia(nuevaEstancia);
	            }
	            
	            // Registramos al peregrino en la parada_sellada
	            registrarParadaSellada(peregrinoSeleccionado);

	            mostrarAlerta("Éxito", "Carnet sellado correctamente.", Alert.AlertType.INFORMATION);

	        } catch (Exception e) {
	            mostrarAlerta("Error", "No se pudo sellar el carnet: " + e.getMessage(), Alert.AlertType.ERROR);
	        }
	    }

	 // Metodo para registrar en paradas selladas
	 private void registrarParadaSellada(Peregrino miPeregrino)
	 {
		 try
		 {
			 ParadaSellada miParadaSellada = new ParadaSellada();
			 
			 // Construyo el objeto parada que luego se va a guardar
			 miParadaSellada.setPeregrino(miPeregrino);
			 miParadaSellada.setParada(paradaActual);
			 miParadaSellada.setFechaParada(LocalDate.now());
			 
			 // Guardamos la parada
			 paradaSelladaService.guardarParadaSellada(miParadaSellada);
		 }
		 
		 catch(Exception e)
		 {
			 System.out.println("Error en el metodo registrarParadaSellada()");
		 }
		 
	 }
	 
	// Metodo para cerrar sesion y volver al Login.
	@FXML
	private void volverALogin() {
		try {
			stageManager.switchScene(FxmlView.LOGIN);
		}

		catch (Exception e) {
			System.out.println("Error en el metodo volverALogin");
		}
	}	
	// Cargar Columnas del TableView
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

	// Cargar la lista de los peregrinos
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

	private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
		Alert miAlerta = new Alert(tipo);
		miAlerta.setTitle(titulo);
		miAlerta.setContentText(mensaje);
		miAlerta.showAndWait();
	}

	public void setParada(Parada parada) {
		ResponsableParadaController.paradaActual = parada;
	}
	
	public static Parada getParada()
	{
		return paradaActual;
	}
	
	
	// Metodo para inicializar la parada actual
	private void inicializarParadaActual()
	{ 
		try
		{
			// Obtengo la credencial entera a traves del nombre de usuario que se ha logueado
			Credenciales miCredencial = CredencialesController.getCredenciales();
			
			Credenciales algo = new Credenciales();
			algo.setId(2L);
			algo.setNombreUsuario("Paco");
			algo.setTipo(Usuarios.Responsable_Parada);
			algo.setContraseñaUsuario("1234");
			
			// Asigno la parada buscada a traves del objeto credenciales
			paradaActual = paradaService.buscarParadaPorCredenciales(algo);
			
			// asigno el valor del nombre de la parada
			nombreParada = paradaActual.getNombre();
		}
		
		catch(Exception e)
		{
			System.out.println("Error en el metodo inicializarParadaActual()");
		}
		
		// Le damos a la label el nombre de la parada
		labelParadaResponsable.setText(nombreParada);
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
