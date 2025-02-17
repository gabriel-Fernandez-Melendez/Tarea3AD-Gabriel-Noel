package com.Gabriel.Noel.tarea3AD2024base;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import com.Gabriel.Noel.tarea3AD2024base.config.StageManager;
import com.Gabriel.Noel.tarea3AD2024base.controller.CredencialesController;
import com.Gabriel.Noel.tarea3AD2024base.controller.NuevoPeregrinoController;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Carnet;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Credenciales;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Parada;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Peregrino;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Usuarios;
import com.Gabriel.Noel.tarea3AD2024base.services.CredencialesService;
import com.Gabriel.Noel.tarea3AD2024base.view.FxmlView;
import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;

import javafx.application.Application;
import javafx.stage.Stage;
//prueba
@SpringBootApplication
public class Tarea3Ad2024baseApplication extends Application {

	protected ConfigurableApplicationContext springContext;
	protected StageManager stageManager;

	@Override
	public void init() throws Exception {
		springContext = springBootApplicationContext();
		
	

	}

	public static void main(final String[] args) {
		// ESTO NO BORRAR
		Application.launch(args);
		
		// ESTO ES UNA TEST DE DB4O
		 ObjectContainer db = Db4oEmbedded.openFile("DB4O_Peregrinos.db4o");

	        try {
	            // Insertar un objeto de prueba
	            System.out.println("Guardando objeto en DB4O...");
	            Persona p = new Persona("Peregrino", 30);
	            db.store(p);
	            db.commit();

	            // Recuperar objetos
	            List<Persona> resultados = db.query(Persona.class);
	            for (Persona persona : resultados) {
	                System.out.println("Recuperado: " + persona);
	            }

	        } finally {
	            db.close();
	        }
	    }

	// CLASE TEMPORAL 
	    static class Persona {
	        private String nombre;
	        private int edad;

	        public Persona() {}

	        public Persona(String nombre, int edad) {
	            this.nombre = nombre;
	            this.edad = edad;
	        }

	        @Override
	        public String toString() {
	            return "Persona{nombre='" + nombre + "', edad=" + edad + "}";
	        }
		
		
		
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		stageManager = springContext.getBean(StageManager.class, primaryStage);
		displayInitialScene();

	}

	/**
	 * Useful to override this method by sub-classes wishing to change the first
	 * Scene to be displayed on startup. Example: Functional tests on main window.
	 */
	//es aqui  donde tengo que modificar la vista
	protected void displayInitialScene() {
		
		
		
		stageManager.switchScene(FxmlView.LOGIN);
	}

	private ConfigurableApplicationContext springBootApplicationContext() {
		SpringApplicationBuilder builder = new SpringApplicationBuilder(Tarea3Ad2024baseApplication.class);
		String[] args = getParameters().getRaw().stream().toArray(String[]::new);
		return builder.run(args);
	}

}
