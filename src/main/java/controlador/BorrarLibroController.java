package controlador;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import dao.EjemplarDao;
import dao.LibroDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebView;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import modelo.Libro;
import modelo.LibroTabla;

public class BorrarLibroController implements Initializable {

	 @FXML private TableView<LibroTabla> tablaLibros;
	 @FXML private TableColumn<LibroTabla, String> columnaLibros;
	 @FXML private Label lblTitulo;
	 @FXML private Label lblAutor;
	 @FXML private Label lblGenero;
	 @FXML private Label lblAÒopublicacion;
	 @FXML private Button cerrarBtn;
	 @FXML private ImageView imgAyuda;
	
	 private LibroDao libroDao = new LibroDao();
	 private EjemplarDao ejemplarDao = new EjemplarDao();
	 private ObservableList<LibroTabla> listaTitulos;
	 
	 @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		 listaTitulos = FXCollections.observableArrayList();
		 ConfigurarTabla();
		 CargarTitulos();
		 ConfigurarEventoTabla();
		 imgAyuda.setOnMouseClicked(event -> mostrarAyuda());
	}
	 
	 private void ConfigurarTabla() {
	        columnaLibros.setCellValueFactory(data -> data.getValue().tituloProperty());
	        tablaLibros.setItems(listaTitulos);
	    }

	   
	 private void CargarTitulos() {
		 List<LibroTabla> titulos = libroDao.ListaTitulosTabla();
		 listaTitulos.addAll(titulos);   
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
		 lblAÒopublicacion.setText(libro.getFechaPublicacion().toString());   
	 }
	 
	 @FXML
	 private void BorrarLibro() {
		 
		 LibroTabla libroSeleccionado = tablaLibros.getSelectionModel().getSelectedItem();   
		    
		 if (libroSeleccionado != null) {		        
			 String titulo = libroSeleccionado.getTitulo();		        
			 Libro libro = libroDao.DatosLibro(titulo); 
		     
			 if (libro != null) {
		     
				 ejemplarDao.EliminarEjemplaresPorLibro(libro);
		         
				 boolean libroEliminado = libroDao.EliminarLibroPorTitulo(titulo);		         				 
		            
				 if (libroEliminado) {		         		         
					 listaTitulos.remove(libroSeleccionado);		                		            
					 lblTitulo.setText("");		            		            
					 lblAutor.setText("");		                		            
					 lblGenero.setText("");		                		            
					 lblAÒopublicacion.setText("");	
					 LibroBorrado();
				 }		        
			 }		    
		 }
	 }
	 

		@FXML
		private void Cerrar() {
			Alert alerta = new Alert(AlertType.CONFIRMATION);
			alerta.setTitle("Cerrar");
			alerta.setHeaderText("ÔøΩEsta seguro que quiere cerrar la ventana?");
			
			 alerta.showAndWait().ifPresent(response -> {
				 if (response == ButtonType.OK) { 
		                // Obtener la ventana y cerrarla
					 Stage stage = (Stage) cerrarBtn.getScene().getWindow();
				        stage.close();
		            }
		        });
			 
		}
	 
	 private void LibroBorrado() {

			Alert alerta = new Alert(AlertType.INFORMATION);
			alerta.setTitle("Borrar Libo");
			alerta.setHeaderText("LibroBorrado");

			alerta.showAndWait();

		}
	 
		@FXML
	    private void mostrarAyuda() {
	        try {
	            Stage ayudaStage = new Stage();
	            ayudaStage.setTitle("Gu√≠a de Uso");
	            WebView webView = new WebView();
	            String ayudaPath = "src/main/resources/ayuda/AyudaBorrarLibro.html";
	            File file = new File(ayudaPath);
	            if (file.exists()) {
	                webView.getEngine().load(file.toURI().toString());
	            } else {
	                webView.getEngine().loadContent("<h1>Error</h1><p>No se encontr√≥ el archivo de ayuda.</p>");
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
