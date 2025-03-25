package controlador;

import java.net.URL;
import java.util.ResourceBundle;

import dao.UsuarioDao;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import modelo.Usuario;
import utiles.Sesion;

public class ModificarClienteController implements Initializable {
	@FXML
	private Label cliente;
	@FXML
	private TextField txtNombre;
	@FXML
	private TextField txtApellidos;
	@FXML
	private TextField txtCorreo;
	@FXML
	private TextField txtContraseña;

	UsuarioDao usuarioDao = new UsuarioDao();
	int id = Sesion.getId();
	Usuario usuario = usuarioDao.ImprimirUsuario(id);

	@Override
	public void initialize(URL location, ResourceBundle resoources) {
		DatosCliente();
	}

	private void DatosCliente() {
		txtNombre.setText(usuario.getNombre());
		txtApellidos.setText(usuario.getApellidos());
		txtCorreo.setText(usuario.getCorreoElectronico());
		txtContraseña.setText(usuario.getContraseña());
		cliente.setText(usuario.getNombre());
		cliente.setStyle("-fx-font-weight: bold;");
	}

	@FXML
	private void ModificarUsuario() {
		usuario.setId(Sesion.getId());
		usuario.setNombre(txtNombre.getText());
		usuario.setApellidos(txtApellidos.getText());
		usuario.setCorreoElectronico(txtCorreo.getText());
		usuario.setContraseña(txtContraseña.getText());
		if (usuarioDao.ModificarUsuario(usuario)) {
			ModificacionCompleta();
		} else {// Alert de error al modificar

		}

//		System.out.println(usuario.getNombre());
//		System.out.println(usuario.getApellidos());
//		System.out.println(usuario.getCorreoElectronico());
//		System.out.println(usuario.getContraseña());
	}

	private void ModificacionCompleta() {

		Alert alerta = new Alert(AlertType.INFORMATION);
		alerta.setTitle("Modificacion");
		alerta.setHeaderText("Usuario Modificado correctamente");

		alerta.showAndWait();

	}

}
