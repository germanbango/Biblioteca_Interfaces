package controlador;

import dao.UsuarioDao;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import modelo.Usuario;
import modelo.UsuarioTipo;

public class RegistrarUsuariosController {

	@FXML private TextField txtNombre;
	@FXML private TextField txtApellido;
	@FXML private TextField txtCorreo;
	@FXML private TextField txtPassword;
	@FXML private TextField txtPasswordC;
	@FXML private Button cerrarBtn;
	UsuarioDao usuarioDao = new UsuarioDao();
	
	
	
	@FXML
	private void Registrar() {
		System.out.println("registrado");
		String nombre = txtNombre.getText();
		String apellidos = txtApellido.getText();
		String correo  = txtCorreo.getText();
		String contraseña = txtPassword.getText();
		String contraseñaC = txtPasswordC.getText();
		//validaciones a posteriori
		
		if(contraseñaC.equals(contraseña)&& correo.matches("^[A-Za-z0-9_]+@+[A-Za-z0-9]+\\.+[A-Za-z]{2,4}+$")) {
			Usuario usuario = new Usuario(nombre,apellidos,correo,contraseña,UsuarioTipo.CLIENTE);
			usuarioDao.CrearCliente(usuario);
			txtNombre.clear();
			txtApellido.clear();
			txtCorreo.clear();
			txtPassword.clear();
			txtPasswordC.clear();
			RegistroCompleto();
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
	  private void showAlert() {
	        Alert alert = new Alert(AlertType.INFORMATION); // Tipo de alerta (INFORMATION, WARNING, ERROR, etc.)
	        alert.setTitle("Alerta de Informaci�n");
	        alert.setHeaderText("Este es un mensaje informativo");
	        alert.setContentText("Aqu� va el contenido del mensaje.");

	        alert.showAndWait();  // Muestra la alerta y espera a que el usuario la cierre
	    }
	
	@FXML
	private void Cerrar() {
		Alert alerta = new Alert(AlertType.CONFIRMATION);
		alerta.setTitle("Cerrar");
		alerta.setHeaderText("�Esta seguro que quiere cerrar la ventana?");
		
		 alerta.showAndWait().ifPresent(response -> {
			 if (response == ButtonType.OK) { 
	                // Obtener la ventana y cerrarla
				 Stage stage = (Stage) cerrarBtn.getScene().getWindow();
			        stage.close();
	            }
	        });
		
		
		
	}
	
}
