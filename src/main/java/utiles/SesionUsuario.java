package utiles;

import modelo.Usuario;

public class SesionUsuario {
    private static SesionUsuario instancia;
    private Usuario usuarioActual;

    // Constructor privado para evitar instancias externas
    private SesionUsuario() {}

    // Obtener la �nica instancia de la sesi�n
    public static SesionUsuario getInstancia() {
        if (instancia == null) {
            instancia = new SesionUsuario();
        }
        return instancia;
    }

    // Iniciar sesi�n guardando el usuario actual
    public void iniciarSesion(Usuario usuario) {
        this.usuarioActual = usuario;
    }

    // Obtener el usuario actual
    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    // Cerrar sesi�n limpiando los datos
    public void cerrarSesion() {
        usuarioActual = null;
    }

    // Verificar si hay un usuario en sesi�n
    public boolean haySesionActiva() {
        return usuarioActual != null;
    }
}
