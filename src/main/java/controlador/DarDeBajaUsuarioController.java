package controlador;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import dao.EjemplarDao;
import dao.PrestamoDao;
import dao.UsuarioDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.UsuarioTabla;
import modelo.Ejemplar;
import modelo.Prestamo;
import modelo.Usuario;

public class DarDeBajaUsuarioController implements Initializable {

    @FXML private TableView<UsuarioTabla> tablaUsuarios;
    @FXML private TableColumn<UsuarioTabla, String> columnaCorreo;
    @FXML private TableColumn<UsuarioTabla, String> columnaRol;
    @FXML private Label lblNombre;
    @FXML private Label lblApellido;
    @FXML private Label lblCorreo;
    @FXML private Label lblRol;
    @FXML private Button cerrarBtn;
    @FXML private ImageView imgAyuda;
    
    private ObservableList<UsuarioTabla> listaUsuarios;
    private UsuarioDao usuarioDao = new UsuarioDao();
    private PrestamoDao prestamoDao = new PrestamoDao();
    private EjemplarDao ejemplarDao = new EjemplarDao();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listaUsuarios = FXCollections.observableArrayList();
        configurarTabla();
        cargarUsuarios();
        configurarEventoTabla();
        imgAyuda.setOnMouseClicked(event -> mostrarAyuda());
    }

    private void configurarTabla() {
        columnaCorreo.setCellValueFactory(new PropertyValueFactory<>("correo")); 
        columnaRol.setCellValueFactory(new PropertyValueFactory<>("rol"));
        tablaUsuarios.setItems(listaUsuarios);
    }

    private void cargarUsuarios() {
        List<UsuarioTabla> usuarios = usuarioDao.obtenerUsuarios();
        listaUsuarios.addAll(usuarios);
    }

    private void configurarEventoTabla() {
        tablaUsuarios.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                actualizarCampos(newSelection.getCorreo()); 
            }
        });
    }

    private void actualizarCampos(String correo) {
        Usuario usuario = usuarioDao.EncontrarUsuarioPorCorreo(correo);

        if (usuario != null) {
            lblNombre.setText(usuario.getNombre());
            lblApellido.setText(usuario.getApellidos());
            lblCorreo.setText(usuario.getCorreoElectronico());
            lblRol.setText(usuario.getUsuarioTipo().toString());
        }
    }
    
    @FXML
    private void BorrarUsuarios() {
        UsuarioTabla usuarioSeleccionado = tablaUsuarios.getSelectionModel().getSelectedItem();

        if (usuarioSeleccionado == null) {
            return;
        }

        Usuario usuario = usuarioDao.EncontrarUsuarioPorCorreo(usuarioSeleccionado.getCorreo());

        if (usuario == null) {
            return;
        }
        List<Prestamo> prestamos = prestamoDao.ObtenerPrestamosPorUsuario(usuario.getId());
        for (Prestamo prestamo : prestamos) {
            Ejemplar ejemplar = prestamo.getEjemplar();
            
            if (ejemplar != null) {
                ejemplar.setEstado(false); 
                ejemplarDao.ModificarEjemplarEstado(ejemplar);
            }
        }
        prestamoDao.EliminarPrestamosPorUsuario(usuario.getId());

        if (usuarioDao.BorrarUsuario(usuario)) {
            UsuarioDadoDeAlta();
            listaUsuarios.remove(usuarioSeleccionado);
            lblNombre.setText("");
            lblApellido.setText("");
            lblCorreo.setText("");
            lblRol.setText("");
        }
    }

    

	@FXML
	private void Cerrar() {
		Alert alerta = new Alert(AlertType.CONFIRMATION);
		alerta.setTitle("Cerrar");
		alerta.setHeaderText("ï¿½Esta seguro que quiere cerrar la ventana?");
		
		 alerta.showAndWait().ifPresent(response -> {
			 if (response == ButtonType.OK) { 
	                // Obtener la ventana y cerrarla
				 Stage stage = (Stage) cerrarBtn.getScene().getWindow();
			        stage.close();
	            }
	        });
		 
	}
    
    private void UsuarioDadoDeAlta() {

		Alert alerta = new Alert(AlertType.INFORMATION);
		alerta.setTitle("Dar de Baja");
		alerta.setHeaderText("Usuario dado de baja");

		alerta.showAndWait();

	}
    
    @FXML
	private void mostrarAyuda() {
		try {
			Stage ayudaStage = new Stage();
			ayudaStage.setTitle("GuÃ­a de Uso - DevoluciÃ³n de Libros");

			WebView webView = new WebView();

			// Ruta del archivo de ayuda
			String ayudaPath = "src/main/resources/ayuda/AyudaDarDeBaja.html"; 

			File file = new File(ayudaPath);
			if (file.exists()) {
				webView.getEngine().load(file.toURI().toString());
			} else {
				webView.getEngine().loadContent("<h1>Error</h1><p>No se encontrÃ³ el archivo de ayuda.</p>");
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