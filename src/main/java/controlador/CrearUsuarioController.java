package controlador;


import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import dao.UsuarioDao;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.Usuario;
import modelo.UsuarioTipo;

public class CrearUsuarioController implements Initializable {
	
	@FXML private TextField txtNombre;
	@FXML private TextField txtApellido;
	@FXML private TextField txtCorreo;
	@FXML private TextField txtContraseÒa;
	@FXML private ComboBox<UsuarioTipo> cmbRol;
	@FXML private Button cerrarBtn;
	@FXML private ImageView imgAyuda;
	
	UsuarioDao usuarioDao = new UsuarioDao();
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		cmbRol.getItems().addAll(UsuarioTipo.values());
		imgAyuda.setOnMouseClicked(event -> mostrarAyuda());
	}

	
	@FXML
	private void CrearUsuario() {

		Usuario usuario = new Usuario();
		usuario.setNombre(txtNombre.getText());
		usuario.setApellidos(txtApellido.getText());
		usuario.setCorreoElectronico(txtCorreo.getText());	
		usuario.setContraseÒa(txtContraseÒa.getText());
		usuario.setUsuarioTipo(cmbRol.getValue());
			
			if( usuario.getCorreoElectronico().matches("^[A-Za-z0-9_]+@+[A-Za-z0-9]+\\.+[A-Za-z]{2,4}+$")) {
					usuarioDao.CrearUsuario(usuario);
					RegistroCompleto();
					txtNombre.clear();;
					txtApellido.clear();;
					txtCorreo.clear();
					txtContraseÒa.clear();
					cmbRol.getSelectionModel().clearSelection();
				
			}else {	
				Error();
			}
		}
		
	
	

	private void RegistroCompleto() {
		
		Alert alerta = new Alert(AlertType.INFORMATION);
		alerta.setTitle("Registro");
		alerta.setHeaderText("Usuario Registrado correctamente");
		
		alerta.showAndWait();
		
	}
	

	private void Error() {
		Alert alerta = new Alert(AlertType.ERROR);
		alerta.setTitle("Error");
		alerta.setHeaderText("Fallo en el  registro");
		alerta.setContentText("Ha introducido los datos de forma incorrecta");
		
		alerta.showAndWait();
		
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
	
	@FXML
    private void mostrarAyuda() {
        try {
            Stage ayudaStage = new Stage();
            ayudaStage.setTitle("Gu√≠a de Uso");
            WebView webView = new WebView();
            String ayudaPath = "src/main/resources/ayuda/AyudaCrearUsuario.html";
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
