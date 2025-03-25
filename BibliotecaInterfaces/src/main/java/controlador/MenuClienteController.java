package controlador;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class MenuClienteController {

	@FXML private MenuBar menuCliente;
    @FXML private MenuItem modificarCliente;
    @FXML private MenuItem registrarPrestamo;
    @FXML private MenuItem salir;
   
	
    

	@FXML
	    private void abrirModificarUsuario() {
	    System.out.println("funciona el cambio");   
		abrirVentanaUsuario("/vista/ModificarCliente.fxml", "Modificar");
	    } 
	
	private void abrirVentanaUsuario(String fxmlPath, String titulo) {
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
