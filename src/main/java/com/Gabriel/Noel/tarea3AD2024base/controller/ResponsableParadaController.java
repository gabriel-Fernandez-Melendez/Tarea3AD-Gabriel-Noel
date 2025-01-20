package com.Gabriel.Noel.tarea3AD2024base.controller;

import com.Gabriel.Noel.tarea3AD2024base.modelo.Peregrino;
import com.Gabriel.Noel.tarea3AD2024base.services.ParadaService;
//import com.Gabriel.Noel.tarea3AD2024base.services.PeregrinoService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class ResponsableParadaController {

    @Autowired
    private ParadaService paradaService;

//    @Autowired
//    private PeregrinoService peregrinoService;

    @FXML
    private TableView<Peregrino> tablaPeregrinos;

    @FXML
    private TableColumn<Peregrino, Long> colUserId;

    @FXML
    private TableColumn<Peregrino, String> colFirstName;

    @FXML
    private TableColumn<Peregrino, String> colLastName;

    @FXML
    private TableColumn<Peregrino, LocalDate> colDOB;

    @FXML
    private TableColumn<Peregrino, String> colGender;

    @FXML
    private TableColumn<Peregrino, String> colEmail;

    @FXML
    private DatePicker fechaInicio;

    @FXML
    private DatePicker fechaFin;

    @FXML
    private CheckBox esVip;

    @FXML
    private CheckBox noVip;

    @FXML
    private Label labelParadaResponsable;

    @FXML
    private Button botonExportarParada;

    @FXML
    private Button botonAceptarSellado;

    @FXML
    private Button btnLogout;

    @FXML
    private Button botonResetear;

    private String paradaDelResponsable; // La parada que pertenece al responsable
    
    private Long idParada;

    @FXML
    public void initialize() 
    {
        // Configurar las columnas de la tabla
    	colUserId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colFirstName.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colLastName.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        colDOB.setCellValueFactory(new PropertyValueFactory<>("fechaNacimiento"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("genero"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        
        // Cargar peregrinos al iniciar
        cargarTodosLosPeregrinos();

        
        // Establecer la parada del responsable 
        paradaDelResponsable = "Parada X"; 
        labelParadaResponsable.setText(paradaDelResponsable);
    }

    @FXML
    private void aceptarSellado() 
    {
        try 
        {
            boolean vip = esVip.isSelected();
            
            // Controlamos que se marque cualquiera de las dos box para saber si es vip o no
            if (!vip && !noVip.isSelected()) 
            {
                mostrarAlerta("Error", "Selecciona si el peregrino es VIP o no.", Alert.AlertType.ERROR);
                return;
            }

            Peregrino peregrinoSeleccionado = tablaPeregrinos.getSelectionModel().getSelectedItem();
            
            // Controlar que se selecciona a un peregrino para poder sellar el carnet
            if (peregrinoSeleccionado == null) 
            {
                mostrarAlerta("Error", "Selecciona un peregrino de la lista.", Alert.AlertType.ERROR);
                return;
            }

            // Sellamos el carnet del peregrino dandole los datos por parametro
            //paradaService.sellarCarnet(peregrinoSeleccionado, paradaDelResponsable, vip);
            
            // Alerta para confirmar de qyue el carnet se ha sellado perfectamente
            mostrarAlerta("Éxito", "Carnet sellado correctamente.", Alert.AlertType.INFORMATION);
            
        } 
        
        catch (Exception e) 
        {
            mostrarAlerta("Error", "No se pudo sellar el carnet: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void filtrarPorFechas() 
    {
        try 
        {
            LocalDate inicio = fechaInicio.getValue();
            LocalDate fin = fechaFin.getValue();
  
            // Comprobamos  que los campos de fecha no estén vacios
            if (inicio == null || fin == null) 
            {
                mostrarAlerta("Error", "Por favor selecciona ambas fechas.", Alert.AlertType.ERROR);
                return;
            }
            
            // Comprobamos que los la fecha de inicio no sea posterior a la de fin
            if (fin.isBefore(inicio))
            {
            	mostrarAlerta("Error", "Por favor la fecha de inicio no puede ser posterior a la de fin",
            			Alert.AlertType.ERROR);
            	return;
            }

            // Obtenermos a lista de los peregrinos filtrados por las fechas dadas
            //List<Peregrino> peregrinosFiltrados = paradaService.obtenerPeregrinosPorFecha(idParada, inicio, fin);
            
            // LLevamos a la tabla
            tablaPeregrinos.getItems().clear();
            //tablaPeregrinos.getItems().setAll(peregrinosFiltrados);
            
        } 
        
        catch (Exception e) 
        {
            mostrarAlerta("Error", "No se pudo filtrar por fechas: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void resetearFechas() 
    {
        try 
        {
            fechaInicio.setValue(null);
            fechaFin.setValue(null);
            cargarTodosLosPeregrinos();
            
        } 
        
        catch (Exception e) 
        {
            mostrarAlerta("Error", "No se pudo resetear las fechas: " + e.getMessage(), Alert.AlertType.ERROR);
        }
        
    }

    @FXML
    private void logout() 
    {
        mostrarAlerta("Info", "Sesión cerrada correctamente.", Alert.AlertType.INFORMATION);
    }

    private void cargarTodosLosPeregrinos() 
    {
        try 
        {
            // Metodo para obtener todos los peregrinos
        	List<Peregrino> todosLosPeregrinos = null; // Cambiarlo
            tablaPeregrinos.getItems().setAll(todosLosPeregrinos);
        }
        
        catch (Exception e) 
        {
            mostrarAlerta("Error", "No se pudieron cargar los peregrinos: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
