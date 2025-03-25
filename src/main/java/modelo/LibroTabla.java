package modelo;

import javafx.beans.property.SimpleStringProperty;

public class LibroTabla {
    private final SimpleStringProperty titulo;

    // 🔹 Constructor correctamente definido
    public LibroTabla(String titulo) {
        this.titulo = new SimpleStringProperty(titulo);
    }

    public String getTitulo() {
        return titulo.get();
    }

    public void setTitulo(String titulo) {
        this.titulo.set(titulo);
    }

    public SimpleStringProperty tituloProperty() {
        return titulo;
    }
}
