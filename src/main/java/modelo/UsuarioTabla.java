package modelo;

import javafx.beans.property.SimpleStringProperty;

public class UsuarioTabla {

    private final SimpleStringProperty correo;
    private final SimpleStringProperty rol;

    public UsuarioTabla(String correo, String rol) {
        this.correo = new SimpleStringProperty(correo);
        this.rol = new SimpleStringProperty(rol);
    }

    public String getCorreo() {
        return correo.get();
    }

    public void setCorreo(String correo) {
        this.correo.set(correo);
    }

    public SimpleStringProperty correoProperty() {
        return correo;
    }

    public String getRol() {
        return rol.get();
    }

    public void setRol(String rol) {
        this.rol.set(rol);
    }

    public SimpleStringProperty rolProperty() {
        return rol;
    }
}
