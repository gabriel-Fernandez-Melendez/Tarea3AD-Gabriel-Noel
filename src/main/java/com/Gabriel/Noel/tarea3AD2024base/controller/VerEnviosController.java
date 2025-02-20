package com.Gabriel.Noel.tarea3AD2024base.controller;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.Gabriel.Noel.tarea3AD2024base.config.StageManager;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Credenciales;
import com.Gabriel.Noel.tarea3AD2024base.modelo.EnvioACasa;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Parada;
import com.Gabriel.Noel.tarea3AD2024base.services.EnvioACasaService;
import com.Gabriel.Noel.tarea3AD2024base.services.ParadaService;
import com.Gabriel.Noel.tarea3AD2024base.view.FxmlView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;


import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;



@Controller
public class VerEnviosController implements Serializable {

	
	@FXML
	private Button botonVolver;
	
	@FXML
	private TableView <EnvioACasa> tablaEnvios;
	
	@FXML
	private TableColumn<EnvioACasa,Long> idEnvio;
	
	@FXML
	private TableColumn<EnvioACasa,Double> pesoEnvio;
	@FXML
	private TableColumn<EnvioACasa,Double> largoEnvio;
	@FXML
	private TableColumn<EnvioACasa,Double> anchoEnvio;
	@FXML
	private TableColumn<EnvioACasa,Double> altoEnvio;
	@FXML
	private TableColumn<EnvioACasa,Boolean> esUrgente;
	@FXML
	private TableColumn<EnvioACasa,String> direccionEnvio;
	
	@Autowired
	private ParadaService paradaService;
	
	
	@Autowired
    private EnvioACasaService envioService;

    @Lazy
    @Autowired
    private StageManager stageManager;

    /**
     * Inicializa la vista y carga la lista de envíos desde la base de datos.
     */
    @FXML
    public void initialize() {
        configurarColumnas();
        cargarEnvios();
    }

    /**
     * Configura las columnas de la tabla con las propiedades de `EnvioACasa`.
     */
    private void configurarColumnas() {
    	 idEnvio.setCellValueFactory(new PropertyValueFactory<>("id"));
         pesoEnvio.setCellValueFactory(new PropertyValueFactory<>("peso"));
         largoEnvio.setCellValueFactory(new PropertyValueFactory<>("largo"));
         anchoEnvio.setCellValueFactory(new PropertyValueFactory<>("ancho"));
         altoEnvio.setCellValueFactory(new PropertyValueFactory<>("alto"));
         esUrgente.setCellValueFactory(new PropertyValueFactory<>("esUrgente"));
         direccionEnvio.setCellValueFactory(new PropertyValueFactory<>("direccion"));
     }

    /**
     * Carga los envíos desde la base de datos y los muestra en la tabla.
     */
    private void cargarEnvios() {
        try {
        	
        	System.out.println("El id de la parada es"+ recogerIDParada().toString());
        	
            ObservableList<EnvioACasa> envios = FXCollections.observableArrayList(envioService.obtenerEnviosPorParada(recogerIDParada()));
            tablaEnvios.setItems(envios);

            if (envios.isEmpty()) {
                System.out.println("No hay envíos registrados.");
            } else {
                System.out.println("Se han cargado " + envios.size() + " envíos en la tabla.");
            }
        } catch (Exception e) {
            System.out.println("Error al cargar envíos: " + e.getMessage());
        }
    }

    /**
     * Vuelve a la vista anterior.
     */
    @FXML
    private void volverAtras() {
        stageManager.switchScene(FxmlView.RESPONSABLE);
    }
    
    
    
    private Long recogerIDParada()
    {
    	Long idParada = 0L;
    	
    	try 
    	{
    		Credenciales miCredencial = CredencialesController.getCredenciales();
    		
    		Parada paradaActual = paradaService.buscarParadaPorCredenciales(miCredencial);
    		
    		idParada = paradaActual.getId();
    		  		
    	}
    	
    	catch(Exception e)
    	{
    		System.out.println("Error en el metodo de recogerIDParada"+ e.getMessage());
    	}

    	return idParada;
    }
    
	
}
