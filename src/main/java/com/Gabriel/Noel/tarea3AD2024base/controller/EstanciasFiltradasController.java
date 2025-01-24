package com.Gabriel.Noel.tarea3AD2024base.controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.Gabriel.Noel.tarea3AD2024base.config.StageManager;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Estancia;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Parada;
import com.Gabriel.Noel.tarea3AD2024base.modelo.ParadaSellada;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Peregrino;
import com.Gabriel.Noel.tarea3AD2024base.services.EstanciaService;
import com.Gabriel.Noel.tarea3AD2024base.services.ParadaSelladaService;
import com.Gabriel.Noel.tarea3AD2024base.view.FxmlView;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Controller
public class EstanciasFiltradasController {

    @FXML
    private Button btnLogout;
    
    @FXML
    private Button botonReseteo;

    @FXML
    private TableView<Peregrino> tablaPeregrinosFiltrados;

    @FXML
    private TableColumn<Peregrino, Long> colPeregrinoID;

    @FXML
    private TableColumn<Peregrino, String> colNombre;

    @FXML
    private TableColumn<Peregrino, Date> colFechaSellado;

    @FXML
    private TableColumn<Estancia, String> colSeEstancio;

    @FXML
    private TableColumn<Estancia, String> colEsVIP;

    @FXML
    private DatePicker fechaFiltradoInicio;

    @FXML
    private DatePicker fechaFiltradoFin;

    @FXML
    private Button botonFiltrarEstancias;
    
    private ResponsableParadaController miResponsable;
    
    @Autowired
    private ParadaSelladaService paradaSelladaService;
    
    @Autowired
    private EstanciaService estanciaService;
    
    @Lazy
    @Autowired
    private StageManager stageManager;
    
    
    // Obtengo el objeto parada para luego trabajar con el
    Parada miParada = ResponsableParadaController.getParada();
    
    // METODOS
    
    @FXML
    private void initialize()
    {
    	cargarColumnas();
    }
    
    // Metodo para volver a la ventana del login
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
 	private void filtrarPorFechas() {
 	    try {
 	        LocalDate fechaInicio = fechaFiltradoInicio.getValue();
 	        LocalDate fechaFin = fechaFiltradoFin.getValue();

 	        // Validar que las fechas no sean nulas
 	        if (fechaInicio == null || fechaFin == null) {
 	            mostrarAlerta("Error", "Por favor, selecciona ambas fechas para filtrar.", Alert.AlertType.ERROR);
 	            return;
 	        }

 	        // Evitar que la fecha de inicio sea posterior a la de fin
 	        if (fechaInicio.isAfter(fechaFin)) {
 	            mostrarAlerta("Error", "La fecha de inicio no puede ser posterior a la fecha de fin.", Alert.AlertType.ERROR);
 	            return;
 	        }

 	        // Obtener la parada actual
 	        Long paradaId = miParada.getId();
 	        if (paradaId == null) {
 	            mostrarAlerta("Error", "No se pudo determinar la parada actual.", Alert.AlertType.ERROR);
 	            return;
 	        }

 	        // Obtener los sellados filtrados por fechas y parada
 	        List<ParadaSellada> selladosFiltrados = paradaSelladaService.filtrarPorParadaYFechas(paradaId, fechaInicio, fechaFin);

 	        // Alerta en caso de no encontrar ninguna parada en ese rango de fechas
 	        if (selladosFiltrados == null || selladosFiltrados.isEmpty()) {
 	            mostrarAlerta("Información", "No se encontraron sellados en el rango de fechas especificado.", Alert.AlertType.INFORMATION);
 	            tablaPeregrinosFiltrados.getItems().clear();
 	            return;
 	        }

 	        // Convertir los sellados a peregrinos con datos adicionales
 	        ObservableList<Peregrino> peregrinos = FXCollections.observableArrayList();
 	        for (ParadaSellada sellado : selladosFiltrados) {
 	            Peregrino peregrino = sellado.getPeregrino();

 	            // Verificar si el peregrino se hospedó y si es VIP
 	            boolean seEstancio = estanciaService.verificarEstancia(peregrino.getId(), paradaId);
 	            boolean esVIP = estanciaService.esVIPEnParada(peregrino.getId(), paradaId);
 	            
 	            peregrinos.add(peregrino);
 	        }

 	        // Actualizar la tabla con los peregrinos enriquecidos
 	        tablaPeregrinosFiltrados.setItems(peregrinos);

 	    } catch (Exception e) {
 	        System.out.println("Error en el método filtrarPorFechas(): " + e.getMessage());
 	    }
 	}

 	
 // Método para cargar las columnas de la tabla
    private void cargarColumnas() {
        colPeregrinoID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colFechaSellado.setCellValueFactory(new PropertyValueFactory<>("fechaSellado"));

    }

    
    
    // Metodo mostrar alertas
 	private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
		Alert miAlerta = new Alert(tipo);
		miAlerta.setTitle(titulo);
		miAlerta.setContentText(mensaje);
		miAlerta.showAndWait();
	}
    
    
    
    
    
    
}
