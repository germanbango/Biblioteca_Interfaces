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
import javafx.scene.control.MenuItem;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.Usuario;
import utiles.Sesion;

public class MenuClienteController implements Initializable {

    @FXML private MenuBar menuCliente;
    @FXML private MenuItem modificarCliente;
    @FXML private MenuItem registrarPrestamo;
    @FXML private MenuItem salir;
    @FXML private MenuItem cerrarAplicacion;
    @FXML private MenuItem cerrarSesion;
    @FXML private Label nombre;
    
    UsuarioDao usuarioDao = new UsuarioDao(); 
    Usuario usuario = usuarioDao.ImprimirUsuario(Sesion.getId());

    private Stage stage;  // Necesitamos manejar la ventana actual para cerrarla

    // Este m�todo se utiliza para pasar la ventana principal cuando se abre.
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void abrirModificarUsuario() {
        System.out.println("funciona el cambio");
        abrirVentanaUsuario("/vista/ModificarCliente.fxml", "Modificar");
    }

    @FXML
    private void abrirSolicitarPrestamo() {
        System.out.println("funciona el cambio");
        abrirVentanaUsuario("/vista/SolicitarPrestamo.fxml", "Solicitar");
    }

    @FXML
    private void abrirDevolverLibro() {
        abrirVentanaUsuario("/vista/DevolverLibro.fxml", "Devolver Libro");
    }

    private void abrirVentanaUsuario(String fxmlPath, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage newStage = new Stage();
            newStage.setTitle(titulo);

            // Ventana modal y no escalable
            newStage.initModality(Modality.APPLICATION_MODAL);  // Hacer la ventana modal
            newStage.setResizable(false);  // No escalable

            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // M�todo para cerrar la ventana actual y abrir la ventana principal
    @FXML
    private void CerrarSesion() {
        try {
            // Cerrar la ventana actual
            Stage currentStage = (Stage) menuCliente.getScene().getWindow();
            currentStage.close();
            
            // Abrir la ventana de inicio de sesi�n (o ventana principal si es el caso)
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

    // M�todo para cerrar el programa
    @FXML
    private void cerrarPrograma() {
        if (stage != null) {
            stage.close(); // Cierra la ventana actual
        }
        System.exit(0);  // Cierra la aplicaci�n
    }

    @FXML
    private void mostrarAyuda() {
        try {
            Stage ayudaStage = new Stage();
            ayudaStage.setTitle("Guía de Uso - Menú Cliente");

            WebView webView = new WebView();
            String ayudaPath = "src/main/resources/ayuda/AyudaMenuLector.html";

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

    
    private void Nombreusuario() {
    	nombre.setText(usuario.getNombre());
    	nombre.setStyle("-fx-font-weight: bold;");
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		Nombreusuario();
	}
}