package com.Gabriel.Noel.tarea3AD2024base.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.Gabriel.Noel.tarea3AD2024base.config.StageManager;
import com.Gabriel.Noel.tarea3AD2024base.controller.CredencialesController;
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
    
    // Inyectamos nuestra conexión a eXistDB
    private ExistdbConnection exbd = new ExistdbConnection("xmldb:exist://localhost:8080/exist/xmlrpc/db/Paradas"+ "/" + paradaActual);

    // -----------------------------------------------------------------------------------
    // MÉTODOS FXML
    // -----------------------------------------------------------------------------------
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Configuramos la columna para mostrar la cadena
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

        // Inicializar la parada y cargar carnets
        inicializarParadaActual();
        cargarListaCarnets();
    }

    /**
     * Botón "Volver Atrás": vuelve a la pantalla de responsable (o la que corresponda).
     */
    @FXML
    private void volverAtras() {
        stageManager.switchScene(FxmlView.RESPONSABLE);
    }

    /**
     * Botón "Cerrar Sesión": vuelve al login.
     */
    @FXML
    private void cerrarSesion() {
        stageManager.switchScene(FxmlView.LOGIN);
    }

    // -----------------------------------------------------------------------------------
    // LÓGICA DE CARGA DE CARNETS
    // -----------------------------------------------------------------------------------

    /**
     * Busca la parada correspondiente al usuario logueado y la asigna a paradaActual.
     * Si no encuentras la parada, podrías asignar un valor por defecto o lanzar alerta.
     */
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

    /**
     * Carga la lista de carnets (ficheros .xml) desde la subcolección eXistDB
     * correspondiente al nombre de la parada.
     */
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

        // Volcamos en la tabla
        tablaCarnets.getItems().clear();
        tablaCarnets.getItems().addAll(listaFicheros);
    }

    /**
     * Descarga el contenido XML del carnet y lo muestra en el TreeView.
     */
    private void mostrarCarnetEnTreeView(String nombreFichero) {
        if (paradaActual == null) {
            return;
        }
        String nombreParada = paradaActual.getNombre();
        // Pedir a eXist el contenido
        String xmlContenido = exbd.obtenerContenidoCarnet(nombreParada, nombreFichero);
        if (xmlContenido == null) {
            System.out.println("No se pudo obtener el contenido XML de: " + nombreFichero);
            return;
        }
        // Parsear el XML y construir un TreeItem
        TreeItem<String> rootItem = parseXMLtoTree(xmlContenido);

        // Asignar al TreeView
        tablaContenidoXML.setRoot(rootItem);
        tablaContenidoXML.setShowRoot(true);
    }

    // -----------------------------------------------------------------------------------
    // MÉTODO PARA PARSEAR XML A UN ÁRBOL (TreeItem<String>)
    // -----------------------------------------------------------------------------------
    private TreeItem<String> parseXMLtoTree(String xml) {
        try {
            // Parseamos la cadena a Document
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

    /**
     * Recorre los nodos hijos de 'node' y los añade recursivamente al 'parentItem'.
     */
    private void addChildrenNodes(Node node, TreeItem<String> parentItem) {
        NodeList nodeList = node.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node hijo = nodeList.item(i);

            // Filtramos nodos de tipo texto en blanco o saltos de línea
            if (hijo.getNodeType() == Node.TEXT_NODE && hijo.getNodeValue().trim().isEmpty()) {
                continue;
            }

            // Creamos un TreeItem para este nodo
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
