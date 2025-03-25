package utiles;

import modelo.Usuario;

public class SesionUsuario {
    private static SesionUsuario instancia;
    private Usuario usuarioActual;

    // Constructor privado para evitar instancias externas
    private SesionUsuario() {}

    // Obtener la única instancia de la sesión
    public static SesionUsuario getInstancia() {
        if (instancia == null) {
            instancia = new SesionUsuario();
        }
        return instancia;
    }

    // Iniciar sesión guardando el usuario actual
    public void iniciarSesion(Usuario usuario) {
        this.usuarioActual = usuario;
    }

    // Obtener el usuario actual
    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    // Cerrar sesión limpiando los datos
    public void cerrarSesion() {
        usuarioActual = null;
    }

    // Verificar si hay un usuario en sesión
    public boolean haySesionActiva() {
        return usuarioActual != null;
    }
}
