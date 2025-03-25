package controlador;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import dao.EjemplarDao;
import dao.LibroDao;
import dao.PrestamoDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import modelo.Ejemplar;
import modelo.Libro;
import modelo.LibroPrestadoTabla;
import modelo.Prestamo;

public class DevolverLibroController implements Initializable {
	
	@FXML private TableView<LibroPrestadoTabla> tablaLibros; 
	@FXML private TableColumn<LibroPrestadoTabla, String> columnaLibros;
	@FXML private Label lblTitulo;
	@FXML private Label lblAutor;
	@FXML private Label lblGenero;
	@FXML private Label lblFechaDevolucion;
	@FXML private Button cerrarBtn;
	@FXML private ImageView imgAyuda;
	
	private LibroDao libroDao = new LibroDao(); 
	private ObservableList<LibroPrestadoTabla> listaTitulos;
	private PrestamoDao prestamoDao = new PrestamoDao();
	private EjemplarDao ejemplarDao = new EjemplarDao(); 
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		listaTitulos = FXCollections.observableArrayList();
        ConfigurarTabla();
        CargarTitulos();
		ConfigurarEventoTabla();

		// Evento para mostrar la ayuda al hacer clic en la imagen de ayuda
		imgAyuda.setOnMouseClicked(event -> mostrarAyuda());
	} 
	
	private void ConfigurarTabla() {
		columnaLibros.setCellValueFactory(data -> data.getValue().tituloProperty()); 
		tablaLibros.setItems(listaTitulos);
	}

	private void CargarTitulos() {
		List<LibroPrestadoTabla> librosPrestados = prestamoDao.ListaLibrosPrestadosPorUsuario();
		listaTitulos.addAll(librosPrestados);
	}
	 
	private void ConfigurarEventoTabla() {
		tablaLibros.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				ActualizarLabels(newSelection.getTitulo());
			}
		});
	}
	 
	private void ActualizarLabels(String titulo) {
		Libro libro = libroDao.DatosLibro(titulo);
	
		lblTitulo.setText(libro.getTitulo());
		lblAutor.setText(libro.getAutor());   
		lblGenero.setText(libro.getGenero().name());

		Prestamo prestamo = prestamoDao.obtenerPrestamoPorLibro(libro.getId());
		if (prestamo != null) {
			lblFechaDevolucion.setText(prestamo.getFechaDevolucion().toString());   
		}  
	}
	 
	@FXML
	private void DevovlerLibro() {
		LibroPrestadoTabla libroSeleccionado = tablaLibros.getSelectionModel().getSelectedItem();
		Libro libro = libroDao.DatosLibro(libroSeleccionado.getTitulo());
		Ejemplar ejemplar = ejemplarDao.ObtenerEjemplare(libro);
		Prestamo prestamo = prestamoDao.SeleccionarPrestamo(ejemplar);
		 
		if (prestamoDao.BorrarPrestamo(prestamo)) {
			if (ejemplarDao.DevolverEjemplar(ejemplar)) {
				LibroDevuelto();
				listaTitulos.remove(libroSeleccionado);
				lblTitulo.setText("");
				lblAutor.setText("");
				lblGenero.setText("");
				lblFechaDevolucion.setText("");
			}
		}
	}
	
	@FXML
	private void Cerrar() {
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
	 
	private void LibroDevuelto() {
		Alert alerta = new Alert(AlertType.INFORMATION);
		alerta.setTitle("Devolución");
		alerta.setHeaderText("Libro devuelto correctamente");
		alerta.showAndWait();
	}

	/**
	 * Muestra la ventana de ayuda en HTML
	 */
	@FXML
	private void mostrarAyuda() {
		try {
			Stage ayudaStage = new Stage();
			ayudaStage.setTitle("Guía de Uso - Devolución de Libros");

			WebView webView = new WebView();

			// Ruta del archivo de ayuda
			String ayudaPath = "src/main/resources/ayuda/AyudaDevolverLibro.html"; 

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
