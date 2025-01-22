package com.Gabriel.Noel.tarea3AD2024base.controller;



import java.net.URL;
import java.util.ResourceBundle;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.Gabriel.Noel.tarea3AD2024base.config.StageManager;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

@Controller
public class NuevoPeregrinoController implements Initializable {
    
    @Lazy
    @Autowired
    private StageManager stageManager;
    
    
    @FXML
    private Button boton;
        
	@FXML
	private void e(ActionEvent d){

	    System.out.println("se preciono");
	}
	
	@FXML
	public void AyudaJavaFX( ) {
		try {
			System.out.println("entro a la funcion de ayuda pero no cargo la ventana");
		    // Crear un WebView para mostrar la ayuda
		    WebView webView = new WebView();

		    // Cargar el archivo HTML desde los recursos
		    String url = getClass().getResource("resources/ayuda/help.html").toExternalForm();
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
		    //esta linea en caso de que necesitemos detectar el origen del fallo atravez de consola
		    e.printStackTrace();
		}

		
		}
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	
}
