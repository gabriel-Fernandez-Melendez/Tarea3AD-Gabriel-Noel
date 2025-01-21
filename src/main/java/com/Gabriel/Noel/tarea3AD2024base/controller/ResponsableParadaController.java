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
		mostrarParada();
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
			// Primero obtener el peregrino seleccionado de la tabla
			Peregrino peregrinoSeleccionado = tablaPeregrinos.getSelectionModel().getSelectedItem();

			// Comprobar que se ha seleccionado un peregrino
			if (peregrinoSeleccionado == null) {
				mostrarAlerta("Error", "Por favor, selecciona a un peregrino de la lista.", Alert.AlertType.ERROR);
				return;
			}

			// Verificar si el peregrino quiere hospedarse
			boolean seHospeda = checkHospedaje.isSelected();

			// Si se hospeda, verificar que se selecciona VIP o No VIP
			boolean esVip = checkEsVIP.isSelected();
			boolean noEsVip = checkNoEsVIP.isSelected();

			if (seHospeda && (!esVip && !noEsVip)) {
				mostrarAlerta("Error", "Por favor, selecciona si el hospedaje es VIP o no.", Alert.AlertType.ERROR);
				return;
			}

			// Obtener el carnet del peregrino
			Carnet carnet = peregrinoSeleccionado.getCarnet();

			// Incrementar la distancia recorrida en el carnet
			carnet.setDistancia(carnet.getDistancia() + 5.0);

			// Si se seleccionó hospedaje y es VIP, incrementar el número de estancias VIP
			if (seHospeda && esVip) {
				carnet.setNvips(carnet.getNvips() + 1);
			}

			// Guardar los cambios en el carnet
			carnetService.GuardarCarnet(carnet);

			// Si el peregrino decide hospedarse, registrar la estancia
			if (seHospeda) {
				LocalDate fechaHoy = LocalDate.now();
				Estancia nuevaEstancia = new Estancia();

				nuevaEstancia.setFecha(fechaHoy);// Fecha actual
				nuevaEstancia.setVip(esVip); // Si es VIP o no
				nuevaEstancia.setParada(paradaActual); // Parada actual
				nuevaEstancia.setPeregrino(peregrinoSeleccionado);

				// Guardar la estancia
				//estanciaService.guardarEstancia(nuevaEstancia);
				estanciaService.guardarEstancia(nuevaEstancia);
			}

			// Mostrar mensaje de éxito
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
