package controlador;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
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
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import utiles.DBConnection;
import utiles.Sesion;

public class MenuAdministradorController implements Initializable {

	@FXML private MenuBar menuAdministrador;
	@FXML private Label nombre;
	
	private UsuarioDao usuarioDao = new UsuarioDao();
	private Usuario usuario = usuarioDao.ImprimirUsuario(Sesion.getId());
	
    @FXML
    private void AbrirDarDeBaja() {
        abrirVentanaModal("/vista/DarDeBajaUsuario.fxml", "Dar de Baja Usuario");
    }

    @FXML
    private void AbrirModificarUsuario() {
        abrirVentanaModal("/vista/ModificarUsuario.fxml", "Modificar Usuario");
    }

    @FXML
    private void AbrirCrearUsuario() {
        abrirVentanaModal("/vista/CrearUsuario.fxml", "Crear Usuario");
    }

    @FXML
    private void AbrirBorrarLibro() {
        abrirVentanaModal("/vista/BorrarLibro.fxml", "Borrar Libro");
    }

    @FXML
    private void CerrarSesion() {
        try {
            // Cerrar la ventana actual
            Stage currentStage = (Stage) menuAdministrador.getScene().getWindow();
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
        Stage stage = (Stage) menuAdministrador.getScene().getWindow();
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

    @FXML
    public void mostrarInforme() {
        try {
            // Ubicar el archivo compilado del informe (.jasper) en el classpath
            String reportPath = "src\\main\\resources\\InformeBiblioteca.jasper";
            
            // Crear un mapa para los par�metros (si no tienes par�metros, lo dejas vac�o)
            Map<String, Object> parameters = new HashMap<>();
            
            // Establecer la conexi�n a la base de datos
            
            
            // Llenar el informe: se ejecuta la query definida en el informe y se obtienen los datos
            JasperPrint jasperPrint = JasperFillManager.fillReport(reportPath, parameters, DBConnection.getConnection());
            
            // Crear el visor de JasperReports (componente Swing) y mostrarlo
            JasperViewer viewer = new JasperViewer(jasperPrint, false);
            viewer.setVisible(true);
            
        } catch (Exception ex) {
         
        }
    }
    
    @FXML
    private void mostrarAyuda() {
        try {
            Stage ayudaStage = new Stage();
            ayudaStage.setTitle("Guía de Uso - Menú Cliente");

            WebView webView = new WebView();
            String ayudaPath = "src/main/resources/ayuda/AyudaMenuAdministrador.html";

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