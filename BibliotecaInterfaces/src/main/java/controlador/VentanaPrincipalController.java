package controlador;


import java.io.IOException;

import dao.UsuarioDao;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelo.Usuario;
import modelo.UsuarioTipo;
import utiles.Sesion;

public class VentanaPrincipalController {
	
	@FXML private TextField txtUsuario; 
	@FXML private TextField txtPassword; 
	
	UsuarioDao usuarioDao =  new UsuarioDao();
	
	
	@FXML 
	private void  Login(){
		
		String correoElectronico = txtUsuario.getText();
		String contraseña = txtPassword.getText();
		Usuario usu = usuarioDao.EncontrarUsuario(correoElectronico, contraseña);
		if(usu!= null) {
			if(usuarioDao.EncontrarRol(correoElectronico, contraseña).equals(UsuarioTipo.CLIENTE)) {
				System.out.println("Se ha autenticado correctamente el usuario: " + usu.getCorreoElectronico());
				Sesion.setId(usu.getId());
				abrirVentanaUsuario("/vista/MenuCliente.fxml", "Iniciar Sesión", usu.getId());	
			}
		}
		
	}
	
	
	@FXML
	    private void abrirRegistrarUsuario() {
	    System.out.println("funciona el cambio");   
		abrirVentana("/vista/RegistrarUsuario.fxml", "Registrarse");
	    } 
	
	private void abrirVentana(String fxmlPath, String titulo) {
	        try {
	            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
	            Parent root = loader.load();
	            Stage stage = new Stage();
	            stage.setTitle(titulo);
	            stage.setScene(new Scene(root));
	            stage.show();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	private void abrirVentanaUsuario(String fxmlPath, String titulo, int id) {
        try {
        	
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	
	

}
