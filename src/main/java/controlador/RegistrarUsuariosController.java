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
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import modelo.Usuario;
import modelo.UsuarioTipo;

public class RegistrarUsuariosController implements Initializable{

    @FXML private TextField txtNombre;
    @FXML private TextField txtApellido;
    @FXML private TextField txtCorreo;
    @FXML private TextField txtPassword;
    @FXML private TextField txtPasswordC;
    @FXML private Button cerrarBtn;
    @FXML private Label contrase�a;
    @FXML private Label repetirContrase�a;
    @FXML private ImageView imgContrase�a;
    @FXML private ImageView imgRepContrase�a;
    @FXML private ImageView imgAyuda;
    
    private boolean mostrarContrase�a = false;
    private boolean mostrarRepContrase�a = false;
    public UsuarioDao usuarioDao = new UsuarioDao();
    
    @FXML
	public void Registrar() {
        System.out.println("registrado");
        String nombre = txtNombre.getText();
        String apellidos = txtApellido.getText();
        String correo  = txtCorreo.getText();
        String contrase�a = txtPassword.getText();
        String contrase�aC = txtPasswordC.getText();
        
        if(contrase�aC.equals(contrase�a)&& correo.matches("^[A-Za-z0-9_]+@+[A-Za-z0-9]+\\.+[A-Za-z]{2,4}+$")) {
            Usuario usuario = new Usuario(nombre,apellidos,correo,contrase�a,UsuarioTipo.CLIENTE);
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
    private void mostrarAyuda() {
        try {
            Stage ayudaStage = new Stage();
            ayudaStage.setTitle("Guía de Uso");
            WebView webView = new WebView();
            String ayudaPath = "src/main/resources/ayuda/AyudaRegistrarLector.html";
            File file = new File(ayudaPath);
            if (file.exists()) {
                webView.getEngine().load(file.toURI().toString());
            } else {
                webView.getEngine().loadContent("<h1>Error</h1><p>No se encontró el archivo de ayuda.</p>");
            }
            Scene scene = new Scene(webView, 800, 600);
            ayudaStage.setScene(scene);
            ayudaStage.initModality(Modality.WINDOW_MODAL);
            ayudaStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void Cerrar() {
        Alert alerta = new Alert(AlertType.CONFIRMATION);
        alerta.setTitle("Cerrar");
        alerta.setHeaderText("¿Está seguro que quiere cerrar la ventana?");
        alerta.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) { 
                Stage stage = (Stage) cerrarBtn.getScene().getWindow();
                stage.close();
            }
        });
    }
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        imgContrase�a.setOnMouseClicked(event -> {
            mostrarContrase�a = !mostrarContrase�a;
            if(mostrarContrase�a) {
                contrase�a.setText(txtPassword.getText());
            } else {
                contrase�a.setText("");
            }
        });
        
        imgRepContrase�a.setOnMouseClicked(event -> {
            mostrarRepContrase�a = !mostrarRepContrase�a;
            if(mostrarRepContrase�a) {
                repetirContrase�a.setText(txtPasswordC.getText());
            } else {
                repetirContrase�a.setText("");
            }
        });
        
        imgAyuda.setOnMouseClicked(event -> mostrarAyuda());
    }
}