package com.Gabriel.Noel.tarea3AD2024base.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.Gabriel.Noel.tarea3AD2024base.config.StageManager;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Parada;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Servicio;
import com.Gabriel.Noel.tarea3AD2024base.services.ParadaService;
import com.Gabriel.Noel.tarea3AD2024base.services.ServiciosService;
import com.Gabriel.Noel.tarea3AD2024base.view.FxmlView;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
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
    
	@FXML
	private TableColumn<Parada,Long> idParada;
	
	@FXML
	private TableColumn<Parada,String> nombreParada;
	
	// TABLAS
	@FXML
	private TableView<Parada> tablaParadas;
	
	@Autowired
	private ParadaService parada_service;

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
        cargarColumnasParadas();

        // listener para cuando se seleccione un servicio de la tabla
        tablaServicios.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                cargarDatosServicio(newSelection);
            }
        });
    }
    
    /**
	 * Método que carga las columnas de la tabla de paradas con los datos almacenados en la base de datos.
	 * 
	 * Este método configura la tabla para permitir la selección múltiple de filas, 
	 * define las propiedades de las columnas y recupera la lista de paradas desde el servicio correspondiente.
	 * Luego, convierte esta lista en un objeto `ObservableList` para su correcta visualización en la UI.
	 * 
	 * En caso de error durante la carga de datos, se captura la excepción y se muestra un mensaje en la consola.
	 */
	@FXML
	private void cargarColumnasParadas()
	{
		try 
		{
		tablaParadas.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		idParada.setCellValueFactory(new PropertyValueFactory<>("id"));
		nombreParada.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		
		
		// Cargamos las paradas
		List<Parada> misParadas = parada_service.ListaDeParadas();
		
		
		// Convertimos la lista a un Observable
		ObservableList<Parada> miObservable = FXCollections.observableArrayList(misParadas);
		
		// Cargamos las paradas a la TableView
		tablaParadas.setItems(miObservable);
		}
		
		catch(Exception e)
		{
			System.out.println("Error en el metodo cargarColumnasParadas()");
		}
		
	}

	@FXML
	private void cargarColumnasServicios() {
	    try {
	        idServicio.setCellValueFactory(new PropertyValueFactory<>("id"));
	        nombreServicio.setCellValueFactory(new PropertyValueFactory<>("nombre"));
	        precioServicio.setCellValueFactory(new PropertyValueFactory<>("precio"));

	        // Mostrar nombres de paradas en lugar de IDs
	        idParadas.setCellValueFactory(cellData -> {
	            List<String> nombreParadasList = cellData.getValue().getNombreParadas();
	            String nombres = (nombreParadasList != null && !nombreParadasList.isEmpty())
	                    ? String.join(", ", nombreParadasList)
	                    : "Sin paradas";
	            return new SimpleStringProperty(nombres);
	        });

	        List<Servicio> misServicios = servicioService.obtenerTodosLosServicios();

	        // Convertimos la lista a un Observable
	        ObservableList<Servicio> miObservable = FXCollections.observableArrayList(misServicios);

	        // Cargar la tabla
	        tablaServicios.setItems(miObservable);
	    } catch (Exception e) {
	        System.out.println("Error al cargar los servicios: " + e.getMessage());
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
        List<String> nombreParadas = servicio.getNombreParadas();
        textoParadas.setText((nombreParadas != null && !nombreParadas.isEmpty()) ? 
                             nombreParadas.toString().replace("[", "").replace("]", "") : "");
    }

   
    @FXML
    private void guardarCambios() {
        if (servicioSeleccionado != null) {
            try {
                // Obtener valores de los campos
                String nuevoNombre = textoNombre.getText().trim();
                String precioTexto = textoPrecio.getText().trim();
                String nuevasParadasTexto = textoParadas.getText().trim();

                // Validaciones básicas
                if (nuevoNombre.isEmpty() || precioTexto.isEmpty()) {
                    mostrarAlerta("Error", "Los campos no pueden estar vacíos", AlertType.ERROR);
                    return;
                }

                // Verificar que el precio usa punto (.) como separador decimal
                if (!precioTexto.matches("\\d+(\\.\\d{1,2})?")) {
                    mostrarAlerta("Error", "El precio debe ser un número válido con punto (.) como separador decimal", AlertType.ERROR);
                    return;
                }

                double precio = Double.parseDouble(precioTexto);
                if (precio < 0) {
                    mostrarAlerta("Error", "El precio no puede ser negativo", AlertType.ERROR);
                    return;
                }

                // Obtener la lista de paradas disponibles de la base de datos
                List<Parada> misParadas = parada_service.ListaDeParadas();
                List<String> paradasValidas = new ArrayList<>();

                if (!nuevasParadasTexto.isEmpty()) {
                    // Separamos las paradas por comas (ignorando espacios)
                    String[] paradasIngresadas = nuevasParadasTexto.split("\\s*,\\s*");

                    for (String nombreParada : paradasIngresadas) {
                        boolean existe = false;

                        // Comprobar si ya existe en la lista de paradas válidas (evitar duplicados)
                        // Usa equalsIgnoreCase si deseas que "Oviedo" y "oviedo" se consideren duplicados
                        for (String paradaValida : paradasValidas) {
                            if (paradaValida.equalsIgnoreCase(nombreParada)) {
                                mostrarAlerta("Error",
                                        "La parada '" + nombreParada + "' está repetida. Elimine duplicados.",
                                        AlertType.ERROR);
                                return;
                            }
                        }

                        // Verificar si la parada existe en la BD
                        for (Parada p : misParadas) {
                            if (p.getNombre().equalsIgnoreCase(nombreParada)) {
                                existe = true;
                                // Almacena el nombre original o en un formato unificado (p.ej. con mayúscula inicial)
                                paradasValidas.add(p.getNombre());
                                break;
                            }
                        }

                        if (!existe) {
                            mostrarAlerta("Error",
                                    "La parada '" + nombreParada + "' no existe en la base de datos.",
                                    AlertType.ERROR);
                            return;
                        }
                    }
                }

                // Actualizar el objeto servicio
                servicioSeleccionado.setNombre(nuevoNombre);
                servicioSeleccionado.setPrecio(precio);
                servicioSeleccionado.setNombreParadas(paradasValidas);

                // Guardar cambios en la base de datos
                servicioService.actualizarServicio(servicioSeleccionado);

                // Recargar la tabla para reflejar los cambios
                tablaServicios.refresh();

                mostrarAlerta("Éxito", "Servicio actualizado correctamente", Alert.AlertType.INFORMATION);

            } catch (Exception e) {
                mostrarAlerta("Error", "El precio debe ser un número válido", AlertType.ERROR);
                System.out.println("Error: " + e.getMessage());
            }
        } else {
            mostrarAlerta("Error", "Selecciona un servicio antes de modificar", AlertType.WARNING);
        }
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
