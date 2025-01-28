package com.Gabriel.Noel.tarea3AD2024base.controller;

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
import com.Gabriel.Noel.tarea3AD2024base.modelo.PeregrinoTabla;
import com.Gabriel.Noel.tarea3AD2024base.services.EstanciaService;
import com.Gabriel.Noel.tarea3AD2024base.services.ParadaSelladaService;
import com.Gabriel.Noel.tarea3AD2024base.services.ParadaService;
import com.Gabriel.Noel.tarea3AD2024base.view.FxmlView;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
public class EstanciasFiltradasController {

    @FXML
    private Button btnLogout;

    @FXML
    private Button botonFiltrarEstancias;
    
    @FXML
    private Button botonReseteo;
    
    @FXML
    private TableView<PeregrinoTabla> tablaPeregrinosFiltrados;

    @FXML
    private TableColumn<PeregrinoTabla, Long> colPeregrinoID;

    @FXML
    private TableColumn<PeregrinoTabla, String> colNombre;

    @FXML
    private TableColumn<PeregrinoTabla, String> colFechaSellado;

    @FXML
    private TableColumn<PeregrinoTabla, String> colSeEstancio;

    @FXML
    private TableColumn<PeregrinoTabla, String> colEsVIP;


    @FXML
    private DatePicker fechaFiltradoInicio;

    @FXML
    private DatePicker fechaFiltradoFin;

    @Autowired
    private ParadaSelladaService paradaSelladaService;

    @Autowired
    private EstanciaService estanciaService;
    
    @Lazy
    @Autowired
    private StageManager stageManager;

    // Parada actual (para filtrar por parada)
    private Parada paradaActual;

    @FXML
    private void initialize() {
    	cargarColumnas();
        paradaActual = ResponsableParadaController.getParada();// Asigna la parada actual usando tu lógica (ResponsableParadaController o credenciales)
    }

    
    private void cargarColumnas() {
        colPeregrinoID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colFechaSellado.setCellValueFactory(new PropertyValueFactory<>("fechaSellado"));
        colSeEstancio.setCellValueFactory(new PropertyValueFactory<>("seEstancio"));
        colEsVIP.setCellValueFactory(new PropertyValueFactory<>("esVIP"));
    }


//    @FXML
//    private void filtrarPorFechas() {
//        try {
//            LocalDate fechaInicio = fechaFiltradoInicio.getValue();
//            LocalDate fechaFin = fechaFiltradoFin.getValue();
//
//            // Validaciones de fechas
//            if (fechaInicio == null || fechaFin == null) {
//                mostrarAlerta("Error", "Por favor, selecciona ambas fechas.", Alert.AlertType.ERROR);
//                return;
//            }
//
//            if (fechaInicio.isAfter(fechaFin)) {
//                mostrarAlerta("Error", "La fecha de inicio no puede ser posterior a la fecha de fin.", Alert.AlertType.ERROR);
//                return;
//            }
//
//            if (paradaActual == null) {
//                mostrarAlerta("Error", "No se ha determinado la parada actual.", Alert.AlertType.ERROR);
//                return;
//            }
//
//            // Obtener las paradas selladas en el rango de fechas
//            List<ParadaSellada> misParadas = paradaSelladaService.obtenerTodasParadasSelladas();
//            List<ParadaSellada> paradasSelladas = paradaSelladaService.filtrarPorParadaYFechas(paradaActual.getId(), fechaInicio, fechaFin);
//            
//            System.out.println(misParadas);
//            System.out.println(paradasSelladas);
//
//            if (paradasSelladas.isEmpty()) {
//                mostrarAlerta("Información", "No se encontraron sellados en este rango de fechas.", Alert.AlertType.INFORMATION);
//                tablaPeregrinosFiltrados.getItems().clear();
//                return;
//            }
//
//
//            
//         // Crear lista de modelos para la tabla
//            ObservableList<PeregrinoTabla> peregrinosTabla = FXCollections.observableArrayList();
//            
//            for (ParadaSellada sellada : paradasSelladas) 
//            {
//                Peregrino peregrino = sellada.getPeregrino();
//                
//                Long id = peregrino.getId();
//                String nombre = peregrino.getNombre();
//                String fechaSellado = sellada.getFechaParada().toString();
//               String seEstancio = "si";
//               String esVIP = "si";
//
//
//                // Crear objeto PeregrinoTabla
//                PeregrinoTabla modelo = new PeregrinoTabla(id, nombre, fechaSellado, seEstancio, esVIP);
//                peregrinosTabla.add(modelo);
//            }
//            
//            System.out.println("ESTOS SON LOS PEREGRINOS FILTRADOS");
//            System.out.println(peregrinosTabla.toString());
//            
//            tablaPeregrinosFiltrados.setItems(peregrinosTabla);
//
//        } 
//        
//        catch (Exception e) {
//            mostrarAlerta("Error", e.getMessage(), Alert.AlertType.ERROR);
//        }
//    }
    
    @FXML
    private void filtrarEstancias() {
        try {
            LocalDate fechaInicio = fechaFiltradoInicio.getValue();
            LocalDate fechaFin = fechaFiltradoFin.getValue();

            if (fechaInicio == null || fechaFin == null || paradaActual == null) {
                mostrarAlerta("Error", "Debes seleccionar fechas válidas y tener una parada asignada.", Alert.AlertType.ERROR);
                return;
            }

            List<Map<String, Object>> estancias = estanciaService.obtenerEstanciasFiltradas(paradaActual.getId(), fechaInicio, fechaFin);

            ObservableList<PeregrinoTabla> peregrinosTabla = FXCollections.observableArrayList();

            for (Map<String, Object> estancia : estancias) 
            {
                PeregrinoTabla modelo = new PeregrinoTabla((Long) estancia.get("id"),(String) estancia.get("nombre"),(String) estancia.get("nacionalidad"),
                    (boolean) estancia.get("seEstancio") ? "Sí" : "No",
                    (boolean) estancia.get("esVIP") ? "Sí" : "No"
                );
                peregrinosTabla.add(modelo);
            }

            tablaPeregrinosFiltrados.setItems(peregrinosTabla);

        } catch (Exception e) {
            mostrarAlerta("Error", "Ha ocurrido un error al filtrar las estancias: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }


    // Método para mostrar alertas
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    @FXML
    private void volverALogin() {
        stageManager.switchScene(FxmlView.LOGIN);
    }
}
