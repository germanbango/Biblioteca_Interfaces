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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebView;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import modelo.Ejemplar;
import modelo.Estanteria;
import modelo.Genero;
import modelo.Libro;
import modelo.LibroTabla;
import utiles.AsignarEstanteria;

public class ModificarLibroController implements Initializable {

    @FXML private TableView<LibroTabla> tablaLibros;
    @FXML private TableColumn<LibroTabla, String> columnaLibros;
    @FXML private TextField txtTitulo;
    @FXML private TextField txtAutor;
    @FXML private DatePicker fechaPublicacion;
    @FXML private ComboBox<Genero> cmbGenero;
    @FXML private Spinner<Integer> spinnerEjemplares;
    @FXML private Button cerrarBtn;
    @FXML private ImageView imgAyuda;

    private LibroDao libroDao = new LibroDao();
    private EjemplarDao ejemplarDao = new EjemplarDao();
    private ObservableList<LibroTabla> listaTitulos;

    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listaTitulos = FXCollections.observableArrayList();
        ConfigurarTabla();
        CargarTitulos();
        cmbGenero.getItems().addAll(Genero.values());
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
                ActualizarCampos(newSelection.getTitulo());
            }
        });
    }
    
    private void ActualizarCampos(String titulo) {
        Libro libro = libroDao.DatosLibro(titulo);

        if (libro != null) {
        	
            txtTitulo.setText(libro.getTitulo());
            txtAutor.setText(libro.getAutor());
            fechaPublicacion.setValue(libro.getFechaPublicacion());
            cmbGenero.setValue(libro.getGenero()); 
            SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, 0,1);
            spinnerEjemplares.setValueFactory(valueFactory);
            spinnerEjemplares.setEditable(true);

        }
    }
    
    
    @FXML
    private void ModificarLibro() {
    	
    	AsignarEstanteria asignarEstanteria = new AsignarEstanteria();
    	Libro libro = libroDao.DatosLibro(txtTitulo.getText());
    	
    	libro.setTitulo(txtTitulo.getText());
    	libro.setAutor(txtAutor.getText());
    	libro.setGenero(cmbGenero.getValue());
    	libro.setFechaPublicacion(fechaPublicacion.getValue());
    	int cantidad = spinnerEjemplares.getValue();
    	Estanteria estanteria = asignarEstanteria.obtenerUbicacionPorGenero(cmbGenero.getValue());
    	
    	if(libroDao.ModificarLibro(libro)) {
    		for(int i = 0; i < cantidad; i++) {
    			Ejemplar ejemplar = new Ejemplar(libro,estanteria);
    			ejemplarDao.CrearEjemplar(ejemplar);
    			ModificacionCompleta();
    		}
    	}
    	
    }
    
    @FXML
    private void cerrar() {
        Alert alerta = new Alert(AlertType.CONFIRMATION);
        alerta.setTitle("Cerrar");
        alerta.setHeaderText("�Est� seguro que quiere cerrar la ventana?");
        
        alerta.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) { 
                Stage stage = (Stage) cerrarBtn.getScene().getWindow();
                stage.close();
            }
        });
    }
    
    private void ModificacionCompleta() {

		Alert alerta = new Alert(AlertType.INFORMATION);
		alerta.setTitle("Modificacion");
		alerta.setHeaderText("Libro Modificado correctamente");

		alerta.showAndWait();

	}

    @FXML
    private void mostrarAyuda() {
        try {
            Stage ayudaStage = new Stage();
            ayudaStage.setTitle("Guía de Uso");
            WebView webView = new WebView();
            String ayudaPath = "src/main/resources/ayuda/AyudaModifcarLibro.html";
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

