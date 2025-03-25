package utiles;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/bibliotecainterfaces2";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private DBConnection() {
        // Constructor privado para evitar instancias
    }

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Eliminar par�ntesis innecesarios
        } catch (ClassNotFoundException e) {
            System.err.println("Error 302: No se encontr� el driver de MySQL.");
            e.printStackTrace(); // Muestra detalles del error
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
