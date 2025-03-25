package controlador;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dao.UsuarioDao;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.Usuario;
import utiles.Sesion;

public class MenuBibliotecarioController implements Initializable{

	@FXML private MenuBar MenuBibliotecario;
	@FXML private Label nombre;
	
	UsuarioDao usuarioDao = new UsuarioDao();
	Usuario usuario = usuarioDao.ImprimirUsuario(Sesion.getId());
	
    @FXML
    private void RegistrarLibro() {
        abrirVentanaModal("/vista/RegistrarLibro.fxml", "Registrar Libro");
    }

    @FXML
    private void ModificarLibro() {
        abrirVentanaModal("/vista/ModificarLibro.fxml", "Modificar Libro");
    }

    @FXML
    private void ModificarUsuario() {
        abrirVentanaModal("/vista/ModificarCliente.fxml", "Modificar Usuario");
    }

    @FXML
    private void CerrarSesion() {
        try {
            // Cerrar la ventana actual
            Stage currentStage = (Stage) MenuBibliotecario.getScene().getWindow();
            currentStage.close();

            // Abrir la ventana de inicio de sesi�n (o ventana principal)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/VentanaPrincipal.fxml")); // Cambia esto a la ruta correcta de tu ventana principal o login
            Parent root = loader.load();
            Stage newStage = new Stage();
            newStage.setTitle("BibliotecaInterfaces"); // T�tulo de la ventana de inicio de sesi�n
            newStage.setScene(new Scene(root));
            newStage.setResizable(false);
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void Salir() {
        Stage stage = (Stage) MenuBibliotecario.getScene().getWindow();
        stage.close(); // Cierra la ventana actual
        System.exit(0);  // Cierra la aplicaci�n
    }

    private void abrirVentanaModal(String fxmlPath, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle(titulo);

            // Hacer la ventana modal y no escalable
            stage.initModality(Modality.APPLICATION_MODAL);  // Ventana modal
            stage.setResizable(false);  // No escalable

            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void Nombreusuario() {
    	nombre.setText(usuario.getNombre());
    	nombre.setStyle("-fx-font-weight: bold;");
    }
    
    @FXML
    private void mostrarAyuda() {
        try {
            Stage ayudaStage = new Stage();
            ayudaStage.setTitle("Guía de Uso - Menú Cliente");

            WebView webView = new WebView();
            String ayudaPath = "src/main/resources/ayuda/AyudaMenuBibliotecario.html";

            File file = new File(ayudaPath);
            if (file.exists()) {
                webView.getEngine().load(file.toURI().toString());
            } else {
                webView.getEngine().loadContent("<h1>Error</h1><p>No se encontró el archivo de ayuda.</p>");
            }

            Scene scene = new Scene(webView, 800, 600);
            ayudaStage.setScene(scene);
            ayudaStage.initModality(Modality.APPLICATION_MODAL);
            ayudaStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		Nombreusuario();
	}
}