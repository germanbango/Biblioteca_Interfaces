package controlador;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import dao.EjemplarDao;
import dao.LibroDao;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.Ejemplar;
import modelo.Genero;
import modelo.Libro;
import utiles.AsignarEstanteria;

public class RegistrarLibroController implements Initializable {

	@FXML private TextField txtTitulo;
	@FXML private TextField txtAutor;
	@FXML private ComboBox<Genero> genero;
	@FXML private DatePicker fechaPublicacion;
	@FXML private Button btnSalir;
	@FXML private ImageView imgAyuda;
	
	private Genero generoL;
	LibroDao libroDao = new LibroDao();
	EjemplarDao ejemplarDao = new EjemplarDao();
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ImprimirDatos();
		imgAyuda.setOnMouseClicked(event -> mostrarAyuda());
	}
	
	private void ImprimirDatos() {
		genero.getItems().setAll(Genero.values());
	}
	
	@FXML
	private void RegistrarLibro() {
		String titulo = txtTitulo.getText();
		String autor = txtAutor.getText();
		generoL = genero.getValue();
		LocalDate fechaPublicaciomL = fechaPublicacion.getValue();
		AsignarEstanteria asingarEstanteria = new AsignarEstanteria();
		
		Libro libro = new Libro(titulo,autor,generoL,fechaPublicaciomL);
		if(libroDao.RegistrarLibro(libro)) {
			Ejemplar ejemplar = new Ejemplar(libro,asingarEstanteria.obtenerUbicacionPorGenero(generoL));
			ejemplarDao.CrearEjemplar(ejemplar);
			RegistroCompleto();
		}
	}
	
	 @FXML
	    private void Salir() {
	        Alert alerta = new Alert(AlertType.CONFIRMATION);
	        alerta.setTitle("Cerrar");
	        alerta.setHeaderText("�Est� seguro que quiere cerrar la ventana?");
	        
	        alerta.showAndWait().ifPresent(response -> {
	            if (response == ButtonType.OK) { 
	                Stage stage = (Stage) btnSalir.getScene().getWindow();
	                stage.close();
	            }
	        });
	    }
	 @FXML
	    private void mostrarAyuda() {
	        try {
	            Stage ayudaStage = new Stage();
	            ayudaStage.setTitle("Guía de Uso");
	            WebView webView = new WebView();
	            String ayudaPath = "src/main/resources/ayuda/AyudaRegistrarLector.html";
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
	 
	private void RegistroCompleto() {

		Alert alerta = new Alert(AlertType.INFORMATION);
		alerta.setTitle("Registro");
		alerta.setHeaderText("Libro Registrado correctamente");

		alerta.showAndWait();

	}
	
}
