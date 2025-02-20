package com.Gabriel.Noel.tarea3AD2024base.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.Gabriel.Noel.tarea3AD2024base.config.StageManager;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Servicio;
import com.Gabriel.Noel.tarea3AD2024base.services.ServiciosService;
import com.Gabriel.Noel.tarea3AD2024base.view.FxmlView;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

@Controller
public class EditarServiciosController {

    // BOTONES
    @FXML
    private Button btnLogout;
    
    @FXML
    private Button botonGuardar;
    
    @FXML
    private Button botonVolver;

    // Tabla de servicios
    @FXML
    private TableView<Servicio> tablaServicios;
    
    // Campos de texto para editar
    @FXML
    private TextField textoNombre;
    
    @FXML
    private TextField textoPrecio;
    
    @FXML
    private TextField textoParadas;

    // Columnas de la tabla
    @FXML
    private TableColumn<Servicio, Long> idServicio;
    
    @FXML
    private TableColumn<Servicio, String> nombreServicio;
    
    @FXML
    private TableColumn<Servicio, Double> precioServicio;
    
    @FXML
    private TableColumn<Servicio, String> idParadas;

    @Autowired
    private ServiciosService servicioService;
    
    @Lazy
    @Autowired
    private StageManager stageManager;


    private Servicio servicioSeleccionado;

    @FXML
    private void initialize() {
        cargarColumnasServicios();
        cargarServicios();

        // listener para cuando se seleccione un servicio de la tabla
        tablaServicios.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                cargarDatosServicio(newSelection);
            }
        });
    }

    @FXML
    private void cargarColumnasServicios() {
        try {
            idServicio.setCellValueFactory(new PropertyValueFactory<>("id"));
            nombreServicio.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            precioServicio.setCellValueFactory(new PropertyValueFactory<>("precio"));

            idParadas.setCellValueFactory(cellData -> 
            {
                List<Long> idParadasList = cellData.getValue().getIdParada(); // Obtiene la lista de IDs
                
                String ids = (idParadasList != null && !idParadasList.isEmpty()) ? 
                		idParadasList.toString().replace("[", "").replace("]", "") : "Sin paradas";
                return new SimpleStringProperty(ids); // Devuelve la lista como String limpio
            });

        } catch (Exception e) {
            System.out.println("Error al configurar las columnas: " + e.getMessage());
        }
    }

    /**
     * Carga los servicios en la tabla desde la base de datos.
     */
    private void cargarServicios() {
        try 
        {
            List<Servicio> misServicios = servicioService.obtenerTodosLosServicios();
            
            // Imprimir los servicios recuperados
            System.out.println("Servicios recuperados desde DB4O:");
            for (Servicio s : misServicios) {
                System.out.println(s.toString());
            }
        
            // Convertimos la lista a un ObservableList
            ObservableList<Servicio> miObservable = FXCollections.observableArrayList(misServicios);
            
            // Cargar en la tabla
            tablaServicios.setItems(miObservable);
        } catch (Exception e) {
            System.out.println("Error al cargar los servicios: " + e.getMessage());
        }
    }

    /**
     * Carga los datos del servicio seleccionado en los campos de texto.
     */
    private void cargarDatosServicio(Servicio servicio) 
    {
        servicioSeleccionado = servicio;
        textoNombre.setText(servicio.getNombre());
        textoPrecio.setText(String.valueOf(servicio.getPrecio()));

        // Convertir lista de IDs a String
        List<Long> idParadasList = servicio.getIdParada();
        textoParadas.setText((idParadasList != null && !idParadasList.isEmpty()) ? 
                             idParadasList.toString().replace("[", "").replace("]", "") : "");
    }

   
    @FXML
    private void guardarCambios() 
    {
        if (servicioSeleccionado == null) 
        {
            mostrarAlerta("Error", "Selecciona un servicio antes de modificar", Alert.AlertType.WARNING);
            return;
        }

        try {
            // Obtener valores de los campos
            String nuevoNombre = textoNombre.getText();
            Double nuevoPrecio = Double.parseDouble(textoPrecio.getText());
            String nuevasParadasTexto = textoParadas.getText();

            // Validaciones básicas
            if (nuevoNombre.isEmpty() || nuevasParadasTexto.isEmpty()) {
                mostrarAlerta("Error", "Los campos no pueden estar vacíos", Alert.AlertType.ERROR);
                return;
            }

            // Convertir ID Paradas a lista
            List<Long> nuevasIdParadas = new ArrayList<>();
            for (String id : nuevasParadasTexto.split(",")) {
                nuevasIdParadas.add(Long.parseLong(id.trim()));
            }

            // Actualizar el objeto servicio
            servicioSeleccionado.setNombre(nuevoNombre);
            servicioSeleccionado.setPrecio(nuevoPrecio);
            servicioSeleccionado.setIdParada(nuevasIdParadas);

            // Guardar cambios en la base de datos
            if (servicioService.actualizarServicio(servicioSeleccionado)) {
                cargarServicios();
                mostrarAlerta("Éxito", "Servicio actualizado correctamente", Alert.AlertType.INFORMATION);
            } else {
                mostrarAlerta("Error", "No se pudo actualizar el servicio", Alert.AlertType.ERROR);
            }

        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Formato de ID de paradas incorrecto. Ingresa números separados por comas.", Alert.AlertType.ERROR);
        } catch (Exception e) {
            mostrarAlerta("Error", "Ocurrió un error inesperado", Alert.AlertType.ERROR);
            System.out.println("Error: " + e.getMessage());
        }
        
        tablaServicios.refresh();
    }



    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert miAlerta = new Alert(tipo);
        miAlerta.setTitle(titulo);
        miAlerta.setContentText(mensaje);
        miAlerta.showAndWait();
    }

    /**
     * Cambia la vista para volver a la ventana del Responsable de Parada.
     */
    @FXML
    private void volverAtras() {
        stageManager.switchScene(FxmlView.ServiciosAdministrador);
    }

    
    @FXML
    private void volverALogin() {
        try {        
            stageManager.switchScene(FxmlView.LOGIN);
        } catch (Exception e) {
            System.out.println("Error en el método volverALogin: " + e.getMessage());
        }
    }
}
