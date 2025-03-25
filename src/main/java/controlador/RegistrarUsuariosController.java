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
    @FXML private Label contraseña;
    @FXML private Label repetirContraseña;
    @FXML private ImageView imgContraseña;
    @FXML private ImageView imgRepContraseña;
    @FXML private ImageView imgAyuda;
    
    private boolean mostrarContraseña = false;
    private boolean mostrarRepContraseña = false;
    public UsuarioDao usuarioDao = new UsuarioDao();
    
    @FXML
	public void Registrar() {
        System.out.println("registrado");
        String nombre = txtNombre.getText();
        String apellidos = txtApellido.getText();
        String correo  = txtCorreo.getText();
        String contraseña = txtPassword.getText();
        String contraseñaC = txtPasswordC.getText();
        
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
    private void mostrarAyuda() {
        try {
            Stage ayudaStage = new Stage();
            ayudaStage.setTitle("GuÃ­a de Uso");
            WebView webView = new WebView();
            String ayudaPath = "src/main/resources/ayuda/AyudaRegistrarLector.html";
            File file = new File(ayudaPath);
            if (file.exists()) {
                webView.getEngine().load(file.toURI().toString());
            } else {
                webView.getEngine().loadContent("<h1>Error</h1><p>No se encontrÃ³ el archivo de ayuda.</p>");
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
        alerta.setHeaderText("Â¿EstÃ¡ seguro que quiere cerrar la ventana?");
        alerta.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) { 
                Stage stage = (Stage) cerrarBtn.getScene().getWindow();
                stage.close();
            }
        });
    }
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        imgContraseña.setOnMouseClicked(event -> {
            mostrarContraseña = !mostrarContraseña;
            if(mostrarContraseña) {
                contraseña.setText(txtPassword.getText());
            } else {
                contraseña.setText("");
            }
        });
        
        imgRepContraseña.setOnMouseClicked(event -> {
            mostrarRepContraseña = !mostrarRepContraseña;
            if(mostrarRepContraseña) {
                repetirContraseña.setText(txtPasswordC.getText());
            } else {
                repetirContraseña.setText("");
            }
        });
        
        imgAyuda.setOnMouseClicked(event -> mostrarAyuda());
    }
}