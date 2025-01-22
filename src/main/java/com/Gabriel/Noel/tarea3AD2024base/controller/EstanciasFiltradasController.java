package com.Gabriel.Noel.tarea3AD2024base.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.Gabriel.Noel.tarea3AD2024base.config.StageManager;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Peregrino;
import com.Gabriel.Noel.tarea3AD2024base.view.FxmlView;

import java.util.Date;

@Controller
public class EstanciasFiltradasController {

    @FXML
    private Button btnLogout;

    @FXML
    private TableView<Peregrino> tablaPeregrinosFiltrados;

    @FXML
    private TableColumn<Peregrino, Long> colPeregrinoID;

    @FXML
    private TableColumn<Peregrino, String> colNombre;

    @FXML
    private TableColumn<Peregrino, String> colApellido;

    @FXML
    private TableColumn<Peregrino, Date> colFechaNacimiento;

    @FXML
    private TableColumn<Peregrino, String> colGenero;

    @FXML
    private TableColumn<Peregrino, Date> colFechaSellado;

    @FXML
    private TableColumn<Peregrino, String> colEstancias;

    @FXML
    private DatePicker fechaFiltradoInicio;

    @FXML
    private DatePicker fechaFiltradoFin;

    @FXML
    private Button botonFiltrarEstancias;
    
    @Lazy
    @Autowired
    private StageManager stageManager;
    
    
    // METODOS
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
    // Metodo para obtener todos los datos de los peregrinos filtrados
    
    
    
    
    
    
    
    
    
    
    
}
