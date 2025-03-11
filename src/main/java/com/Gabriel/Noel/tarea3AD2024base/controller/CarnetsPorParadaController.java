package com.Gabriel.Noel.tarea3AD2024base.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.Gabriel.Noel.tarea3AD2024base.config.StageManager;
import com.Gabriel.Noel.tarea3AD2024base.dataeXistdb.ExistdbConnection;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Credenciales;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Parada;
import com.Gabriel.Noel.tarea3AD2024base.services.ParadaService;
import com.Gabriel.Noel.tarea3AD2024base.view.FxmlView;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Controlador para el CU11: Ver Carnets expedidos en una Parada.
 */
@Controller
public class CarnetsPorParadaController implements Initializable {

    @FXML
    private Button botonVolver;

    @FXML
    private Button btnLogout;

    @FXML
    private TableView<String> tablaCarnets;

    @FXML
    private TableColumn<String, String> nombreCarnet;

    @FXML
    private TreeView<String> tablaContenidoXML;

    @Autowired
    private ParadaService paradaService;

    @Lazy
    @Autowired
    private StageManager stageManager;

    @PersistenceContext
    private EntityManager entityManager;

    // Parada actual (si la necesitas para filtrar)
    private Parada paradaActual;
    

    private ExistdbConnection exbd = new ExistdbConnection("xmldb:exist://localhost:8080/exist/xmlrpc/db/Paradas"+ "/" + paradaActual);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nombreCarnet.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));
        // Permitir seleccionar solo una fila a la vez
        tablaCarnets.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        // Al hacer doble clic (o clic simple, según prefieras) en la tabla, cargar el XML
        tablaCarnets.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() == 2) { // Doble clic
                String ficheroSeleccionado = tablaCarnets.getSelectionModel().getSelectedItem();
                if (ficheroSeleccionado != null) {
                    mostrarCarnetEnTreeView(ficheroSeleccionado);
                }
            }
        });

        inicializarParadaActual();
        cargarListaCarnets();
    }


    @FXML
    private void volverAtras() {
        stageManager.switchScene(FxmlView.RESPONSABLE);
    }

    @FXML
    private void cerrarSesion() {
        stageManager.switchScene(FxmlView.LOGIN);
    }


    private void inicializarParadaActual() {
        try {
            // Obtener credenciales del usuario actual
            Credenciales cred = CredencialesController.getCredenciales();
            // Buscar la parada que tiene dichas credenciales (depende de tu modelo)
            paradaActual = paradaService.buscarParadaPorCredenciales(cred);

            // Si la parada es nula, podrías poner una de prueba
            if (paradaActual == null) {
                System.out.println("No se encontró parada para las credenciales del usuario. Usando dummy...");
                // O podrías forzar una parada
                // paradaActual = new Parada();
                // paradaActual.setNombre("Granda");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void cargarListaCarnets() {
        if (paradaActual == null) {
            System.out.println("paradaActual es null. No se pueden cargar carnets.");
            return;
        }

        String nombreParada = paradaActual.getNombre();
        if (nombreParada == null || nombreParada.isEmpty()) {
            System.out.println("La parada no tiene nombre. No se pueden cargar carnets.");
            return;
        }

        // Llamamos a eXist para obtener la lista de ficheros
        List<String> listaFicheros = exbd.listarCarnetsDeParada(nombreParada);

        //cargamos los datos
        tablaCarnets.getItems().clear();
        tablaCarnets.getItems().addAll(listaFicheros);
    }


    //cargar los datos en la treeview
    private void mostrarCarnetEnTreeView(String nombreFichero) {
        if (paradaActual == null) {
            return;
        }
        String nombreParada = paradaActual.getNombre();

        String xmlContenido = exbd.obtenerContenidoCarnet(nombreParada, nombreFichero);
        if (xmlContenido == null) {
            System.out.println("No se pudo obtener el contenido XML de: " + nombreFichero);
            return;
        }

        TreeItem<String> rootItem = parseXMLtoTree(xmlContenido);

        // Asignar al TreeView
        tablaContenidoXML.setRoot(rootItem);
        tablaContenidoXML.setShowRoot(true);
    }


    private TreeItem<String> parseXMLtoTree(String xml) {
        try {

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(
                new java.io.ByteArrayInputStream(xml.getBytes(java.nio.charset.StandardCharsets.UTF_8))
            );
            doc.getDocumentElement().normalize();

            // Crear un TreeItem raíz con el nombre de la etiqueta raíz
            Node rootNode = doc.getDocumentElement();
            TreeItem<String> rootItem = new TreeItem<>(rootNode.getNodeName());

            // Recorrer hijos recursivamente
            addChildrenNodes(rootNode, rootItem);

            return rootItem;
        } catch (Exception e) {
            e.printStackTrace();
            // Si falla, devolvemos un item con mensaje de error
            return new TreeItem<>("Error al parsear XML");
        }
    }


    
    private void addChildrenNodes(Node node, TreeItem<String> parentItem) {
        NodeList nodeList = node.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node hijo = nodeList.item(i);


            if (hijo.getNodeType() == Node.TEXT_NODE && hijo.getNodeValue().trim().isEmpty()) {
                continue;
            }


            String nombre = hijo.getNodeName();
            if (hijo.getNodeType() == Node.TEXT_NODE) {
                // Para texto, mostramos "texto: <valor>"
                nombre = "Texto: " + hijo.getNodeValue();
            }
            TreeItem<String> itemHijo = new TreeItem<>(nombre);

            // Añadirlo al padre
            parentItem.getChildren().add(itemHijo);

            // Si es un elemento con hijos, los añadimos recursivamente
            if (hijo.hasChildNodes()) {
                addChildrenNodes(hijo, itemHijo);
            }
        }
    }
}
