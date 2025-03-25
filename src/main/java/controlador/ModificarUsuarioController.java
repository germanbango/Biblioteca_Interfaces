package controlador;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import dao.UsuarioDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.Usuario;
import modelo.UsuarioTabla;
import modelo.UsuarioTipo;


public class ModificarUsuarioController implements Initializable{

	 @FXML private TableView<UsuarioTabla> tablaUsuarios;   
	 @FXML private TableColumn<UsuarioTabla, String> columnaCorreo;   
	 @FXML private TableColumn<UsuarioTabla, String> columnaRol;
	 @FXML private TextField txtNombre;
	 @FXML private TextField txtApellido;
	 @FXML private TextField txtCorreo;
	 @FXML private TextField txtPassword;
	 @FXML private ComboBox<UsuarioTipo> cmbRol;
	 @FXML private Button cerrarBtn;
	 @FXML private ImageView imgAyuda;
	 
	    private ObservableList<UsuarioTabla> listaUsuarios;
	    private UsuarioDao usuarioDao = new UsuarioDao();

	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
        listaUsuarios = FXCollections.observableArrayList();
        configurarTabla();
        cargarUsuarios();
        cmbRol.getItems().addAll(UsuarioTipo.values());
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
	        	txtNombre.setText(usuario.getNombre());
	        	txtApellido.setText(usuario.getApellidos());
	        	txtCorreo.setText(usuario.getCorreoElectronico());
	        	txtPassword.setText(usuario.getContraseÒa());
	        	cmbRol.setValue(usuario.getUsuarioTipo());
	        }
	    }
	    
	    @FXML
	    private void ModificarUsuario() {
	        UsuarioTabla usuarioSeleccionado = tablaUsuarios.getSelectionModel().getSelectedItem();
	        Usuario usuario = usuarioDao.EncontrarUsuarioPorCorreo(usuarioSeleccionado.getCorreo());

        	usuario.setNombre(txtNombre.getText());
        	usuario.setApellidos(txtApellido.getText());
        	usuario.setCorreoElectronico(txtCorreo.getText());
        	usuario.setContraseÒa(txtPassword.getText());
        	usuario.setUsuarioTipo(cmbRol.getValue());
	        
	        String correoAntiguo = usuarioSeleccionado.getCorreo(); 
	        usuario.setCorreoElectronico(txtCorreo.getText()); 
	        if(usuarioDao.ModificarUsuarioPorCorreo(usuario, correoAntiguo)){
	            ModificacionCompleta();
	            listaUsuarios.clear();
	            listaUsuarios.addAll(usuarioDao.obtenerUsuarios()); 
	            tablaUsuarios.refresh(); 
	        }
	    }
	    
	    @FXML
	    private void cerrar() {
	        Alert alerta = new Alert(AlertType.CONFIRMATION);
	        alerta.setTitle("Cerrar");
	        alerta.setHeaderText("ÔøΩEstÔøΩ seguro que quiere cerrar la ventana?");
	        
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
			alerta.setHeaderText("Usuario modificado correctamente");

			alerta.showAndWait();

		}


	    @FXML
		private void mostrarAyuda() {
		    try {
		        Stage ayudaStage = new Stage();
		        ayudaStage.setTitle("Gu√≠a de Uso - Modificar Cliente");

		        WebView webView = new WebView();
		        String ayudaPath = "src/main/resources/ayuda/AyudaModificarUsuario.html";

		        File file = new File(ayudaPath);
		        if (file.exists()) {
		            webView.getEngine().load(file.toURI().toString());
		        } else {
		            webView.getEngine().loadContent("<h1>Error</h1><p>No se encontr√≥ el archivo de ayuda.</p>");
		        }

		        Scene scene = new Scene(webView, 800, 600);
		        ayudaStage.setScene(scene);
		        ayudaStage.initModality(Modality.APPLICATION_MODAL);
		        ayudaStage.show();
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		}
}
