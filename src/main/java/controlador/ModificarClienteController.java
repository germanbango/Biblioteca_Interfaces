package controlador;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import dao.UsuarioDao;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebView;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import modelo.Usuario;
import utiles.Sesion;

public class ModificarClienteController implements Initializable {

	@FXML
	private TextField txtNombre;
	@FXML
	private TextField txtApellidos;
	@FXML
	private TextField txtCorreo;
	@FXML
	private TextField txtContraseÒa;
	@FXML private Button cerrarBtn;
	@FXML private ImageView imgAyuda;
	
	UsuarioDao usuarioDao = new UsuarioDao();
	int id = Sesion.getId();
	Usuario usuario = usuarioDao.ImprimirUsuario(id);

	@Override
	public void initialize(URL location, ResourceBundle resoources) {
		DatosCliente();
		imgAyuda.setOnMouseClicked(event -> mostrarAyuda());
	}

	private void DatosCliente() {
		txtNombre.setText(usuario.getNombre());
		txtApellidos.setText(usuario.getApellidos());
		txtCorreo.setText(usuario.getCorreoElectronico());
		txtContraseÒa.setText(usuario.getContraseÒa());
	}

	@FXML
	private void ModificarUsuario() {
		usuario.setId(Sesion.getId());
		usuario.setNombre(txtNombre.getText());
		usuario.setApellidos(txtApellidos.getText());
		usuario.setCorreoElectronico(txtCorreo.getText());
		usuario.setContraseÒa(txtContraseÒa.getText());
		if (usuarioDao.ModificarUsuario(usuario)) {
			ModificacionCompleta();
			
		} else {// Alert de error al modificar

		}

//		System.out.println(usuario.getNombre());
//		System.out.println(usuario.getApellidos());
//		System.out.println(usuario.getCorreoElectronico());
//		System.out.println(usuario.getContrase√±a());
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
	
	private void ModificacionCompleta() {

		Alert alerta = new Alert(AlertType.INFORMATION);
		alerta.setTitle("Modificacion");
		alerta.setHeaderText("Usuario Modificado correctamente");

		alerta.showAndWait();

	}
	
	@FXML
	private void mostrarAyuda() {
	    try {
	        Stage ayudaStage = new Stage();
	        ayudaStage.setTitle("Gu√≠a de Uso - Modificar Cliente");

	        WebView webView = new WebView();
	        String ayudaPath = "src/main/resources/ayuda/AyudaModificarLector.html";

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
