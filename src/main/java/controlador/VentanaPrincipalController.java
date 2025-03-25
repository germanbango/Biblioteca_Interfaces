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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.Usuario;
import modelo.UsuarioTipo;
import utiles.Sesion;

public class VentanaPrincipalController implements Initializable {

    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtPassword;
    @FXML private Button btnIniciarSesion;
    @FXML private ImageView imgContrase�a;
    @FXML private Label mostrarContrase�a;
    @FXML private ImageView imgAyuda;

    private boolean contrase�aVisible = false;


    UsuarioDao usuarioDao = new UsuarioDao();

    @FXML
    private void Login() {
        String correoElectronico = txtUsuario.getText();
        String contrase�a = txtPassword.getText();
        Usuario usu = usuarioDao.EncontrarUsuario(correoElectronico, contrase�a);
        if (usu != null) {
            if (usuarioDao.EncontrarRol(correoElectronico, contrase�a).equals(UsuarioTipo.CLIENTE)) {
                System.out.println("Se ha autenticado correctamente el usuario: " + usu.getCorreoElectronico());
                Sesion.setId(usu.getId());
                abrirVentanaUsuario("/vista/MenuCliente.fxml", "Iniciar Sesion", usu.getId());
            } else if (usuarioDao.EncontrarRol(correoElectronico, contrase�a).equals(UsuarioTipo.BIBLIOTECARIO)) {
                Sesion.setId(usu.getId());
                abrirVentanaUsuario("/vista/MenuBibliotecario.fxml", "Iniciar Sesion", usu.getId());
            } else if (usuarioDao.EncontrarRol(correoElectronico, contrase�a).equals(UsuarioTipo.ADMINISTRADOR)) {
            	 Sesion.setId(usu.getId());
            	abrirVentanaUsuario("/vista/MenuAdministrador.fxml", "Iniciar Sesion", usu.getId());
            } 
        }else {
        	Error();
        	txtPassword.clear();
        	txtUsuario.clear();
        	mostrarContrase�a.setText("");
        }
    }

    @FXML
    private void abrirRegistrarUsuario() {
        System.out.println("funciona el cambio");
        abrirRegistrarUsuarioHija();
    }


    private void abrirVentanaUsuario(String fxmlPath, String titulo, int id) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();

            // Cierra la ventana actual correctamente
            Stage Nstage = (Stage) btnIniciarSesion.getScene().getWindow();
	        Nstage.close();       
	        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void abrirRegistrarUsuarioHija() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/RegistrarUsuario.fxml"));
            Parent root = loader.load();

            Stage registroStage = new Stage();
            registroStage.setTitle("Registro de Usuario");
            registroStage.setScene(new Scene(root));
            registroStage.setResizable(false);
            registroStage.initModality(Modality.WINDOW_MODAL);
            registroStage.initOwner(txtUsuario.getScene().getWindow());
            registroStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void Error() {
		Alert alerta = new Alert(AlertType.ERROR);
		alerta.setTitle("Error");
		alerta.setHeaderText("Fallo al iniciar sesión");
		alerta.setContentText("el correo o la contraseña son incorrectos");
		
		alerta.showAndWait();
		
	}
    
    @FXML
    private void mostrarAyuda() {
        try {
            // Crear una nueva ventana para la ayuda
            Stage ayudaStage = new Stage();
            ayudaStage.setTitle("Gu�a de Uso");

            // Crear un WebView
            WebView webView = new WebView();

            // Ubicar el archivo HTML en el directorio de recursos (resources)
            String ayudaPath = "src/main/resources/ayuda/AyudaLogin.html";  // Cambi� la ruta aqu�

            // Verificar si el archivo existe
            File file = new File(ayudaPath);
            if (file.exists()) {
                // Cargar el archivo HTML en el WebView
                webView.getEngine().load(file.toURI().toString());
            } else {
                // Si no se encuentra el archivo, mostrar un mensaje de error
                webView.getEngine().loadContent("<h1>Error</h1><p>No se encontr� el archivo de ayuda.</p>");
            }

            // Crear la escena y mostrar la ventana
            Scene scene = new Scene(webView, 800, 600);
            ayudaStage.setScene(scene);
            ayudaStage.initModality(Modality.WINDOW_MODAL);
            ayudaStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    	imgContrase�a.setOnMouseClicked(event -> {
    		contrase�aVisible = !contrase�aVisible;
    		if(contrase�aVisible) {
    			mostrarContrase�a.setText(txtPassword.getText());
    		}else{
    			mostrarContrase�a.setText("");
    		}
    	});
    
    	 imgAyuda.setOnMouseClicked(event -> mostrarAyuda());
    }
}