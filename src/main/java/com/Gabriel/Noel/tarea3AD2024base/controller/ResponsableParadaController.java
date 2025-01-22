package com.Gabriel.Noel.tarea3AD2024base.controller;


import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.Gabriel.Noel.tarea3AD2024base.config.StageManager;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Carnet;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Estancia;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Parada;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Peregrino;
import com.Gabriel.Noel.tarea3AD2024base.services.CarnetService;
import com.Gabriel.Noel.tarea3AD2024base.services.EstanciaService;
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

	private Parada paradaActual;

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

	@Lazy
	@Autowired
	private StageManager stageManager;

	// Metodo inicializar para cargar datos a la tableView
	@FXML
	public void initialize() {
		cargarColumnas();
		cargarPeregrinos();
		//mostrarParada();
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

	            if (seHospeda) {
	                LocalDate fechaHoy = LocalDate.now();
	                Estancia nuevaEstancia = new Estancia();

	                nuevaEstancia.setFecha(fechaHoy);
	                nuevaEstancia.setVip(esVip);
	                nuevaEstancia.setParada(paradaActual);
	                nuevaEstancia.setPeregrino(peregrinoSeleccionado);

	                estanciaService.guardarEstancia(nuevaEstancia);
	            }

	            mostrarAlerta("Éxito", "Carnet sellado correctamente.", Alert.AlertType.INFORMATION);

	        } catch (Exception e) {
	            mostrarAlerta("Error", "No se pudo sellar el carnet: " + e.getMessage(), Alert.AlertType.ERROR);
	        }
	    }

	// Metodo para cerrar sesion y volver al Login
	@FXML
	private void volverALogin() {
		try {
			stageManager.switchScene(FxmlView.LOGIN);
		}

		catch (Exception e) {
			System.out.println("Error en el metodo volverALogin");
		}
	}

	// Metodo para cambiar el Parada X a la parada actual y saber en que parada me
	// encuentro
	private void mostrarParada() {
		labelParadaResponsable.setText(paradaActual.getNombre());
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
		this.paradaActual = parada;
	}

}
