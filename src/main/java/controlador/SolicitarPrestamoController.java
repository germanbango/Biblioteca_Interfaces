package controlador;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import dao.EjemplarDao;
import dao.LibroDao;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import modelo.Ejemplar;
import modelo.Libro;

public class SolicitarPrestamoController implements Initializable {
    
    @FXML private ComboBox<String> cmbTitulos;
    @FXML private Label autor;
    @FXML private Label genero;
    @FXML private Label a�oPublicacion;
    @FXML private Label estado;
    @FXML private Label fechaDevolucion;
    @FXML private Button cerrarBtn;
    @FXML private ImageView imgAyuda;
    
    private final LibroDao libroDao = new LibroDao();
    private final EjemplarDao ejemplarDao = new EjemplarDao();
    private final LocalDate fechaDev = LocalDate.now().plusDays(15);
    private List<String> titulos;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        titulos = libroDao.ListaTitulos();
        cmbTitulos.getItems().addAll(titulos);
        cmbTitulos.setOnAction(event -> actualizarLabels());
        
        // Evento para mostrar la ayuda
        imgAyuda.setOnMouseClicked(event -> mostrarAyuda());
    }
    
    private void actualizarLabels() {
        String tituloSeleccionado = cmbTitulos.getValue();
        if (tituloSeleccionado != null) {
            Libro libro = libroDao.DatosLibro(tituloSeleccionado);
            
            autor.setText(libro.getAutor());
            genero.setText(libro.getGenero().name());
            a�oPublicacion.setText(libro.getFechaPublicacion().toString());
            fechaDevolucion.setText(fechaDev.toString());
            
            List<Ejemplar> ejemplares = ejemplarDao.ObtenerEjemplares(libro);
            long prestados = ejemplares.stream().filter(Ejemplar::isEstado).count();
            
            estado.setText(prestados == ejemplares.size() ? "No Disponible" : "Disponible");
        }
    }
    
    @FXML
    private void GenerarPrestamo() {
        if (cmbTitulos.getValue() != null) {
            ejemplarDao.PrestarEjemplar(libroDao.DatosLibro(cmbTitulos.getValue()));
            mostrarPrestamoRealizado();
        }
    }
    
    @FXML
    private void cerrar() {
        Alert alerta = new Alert(AlertType.CONFIRMATION);
        alerta.setTitle("Cerrar");
        alerta.setHeaderText("¿Está seguro que quiere cerrar la ventana?");
        
        alerta.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) { 
                Stage stage = (Stage) cerrarBtn.getScene().getWindow();
                stage.close();
            }
        });
    }
    
    private void mostrarPrestamoRealizado() {
        Alert alerta = new Alert(AlertType.INFORMATION);
        alerta.setTitle("Préstamo");
        alerta.setHeaderText("Préstamo realizado correctamente");
        alerta.showAndWait();
    }

    /**
     * Muestra la ventana de ayuda en HTML
     */
    @FXML
    private void mostrarAyuda() {
        try {
            Stage ayudaStage = new Stage();
            ayudaStage.setTitle("Guía de Uso - Solicitar Préstamo");

            WebView webView = new WebView();

            // Ruta del archivo de ayuda
            String ayudaPath = "src/main/resources/ayuda/AyudaSolicitarPrestamo.html"; 

            File file = new File(ayudaPath);
            if (file.exists()) {
                webView.getEngine().load(file.toURI().toString());
            } else {
                webView.getEngine().loadContent("<h1>Error</h1><p>No se encontró el archivo de ayuda.</p>");
            }

            Scene scene = new Scene(webView, 800, 600);
            ayudaStage.setScene(scene);
            ayudaStage.initModality(Modality.WINDOW_MODAL);
            ayudaStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}