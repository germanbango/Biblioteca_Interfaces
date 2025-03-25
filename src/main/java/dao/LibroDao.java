package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelo.Ejemplar;
import modelo.Genero;
import modelo.Libro;
import modelo.LibroPrestadoTabla;
import modelo.LibroTabla;
import utiles.DBConnection;

public class LibroDao {
	
	public boolean RegistrarLibro(Libro libro){
		
		String sql = "INSERT INTO LIBROS(TITULO,AUTOR,GENERO,FECHAPUBLICACION)"
				+ " VALUES (?,?,?,?)";
		
		try {
			Connection conexion = DBConnection.getConnection();
			PreparedStatement pstmt = conexion.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, libro.getTitulo());
			pstmt.setString(2, libro.getAutor());
			pstmt.setString(3, libro.getGenero().name());
			pstmt.setDate(4, Date.valueOf(libro.getFechaPublicacion()));
			int rowsAffected = pstmt.executeUpdate();
			System.out.println(rowsAffected);
			
			if(rowsAffected >0) {
				ResultSet rs = pstmt.getGeneratedKeys();
				if(rs.next()) {
					
					libro.setId(rs.getInt(1));
					return true;
				}
			}else {
				return false;
			}
		
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return false;
		
	}
	
	public List<String> ListaTitulos(){
		List<String> titulos = new ArrayList<>();
		String sql = "SELECT TITULO FROM LIBROS";

		try {
			Connection conexion = DBConnection.getConnection();
			PreparedStatement pstmt = conexion.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				titulos.add(rs.getString("titulo"));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
			
		return titulos;
	}
	
	public Libro DatosLibro(String titulo){
		Libro libro = null;
		String sql = "SELECT * FROM LIBROS WHERE TITULO = ?";

		try {
			Connection conexion = DBConnection.getConnection();
			PreparedStatement pstmt = conexion.prepareStatement(sql);
			
			pstmt.setString(1, titulo);
			
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				libro = new Libro();
				libro.setId(rs.getInt("id"));
				libro.setTitulo(rs.getString("titulo"));
				libro.setAutor(rs.getString("autor"));
				libro.setGenero(Genero.valueOf(rs.getString("genero").toUpperCase()));
				libro.setFechaPublicacion(rs.getDate("fechapublicacion").toLocalDate());
				libro.setEjemplares(new EjemplarDao().ObtenerEjemplares(libro));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
			
		return libro;
	}
	
	public List<LibroTabla> ListaTitulosTabla(){
	    List<LibroTabla> titulos = new ArrayList<>();
	    String sql = "SELECT TITULO FROM LIBROS";

	    try (Connection conexion = DBConnection.getConnection();
	         PreparedStatement pstmt = conexion.prepareStatement(sql);
	         ResultSet rs = pstmt.executeQuery()) {

	        while (rs.next()) {
	            titulos.add(new LibroTabla(rs.getString("titulo")));
	        }

	    } catch (SQLException e) {
	        System.err.println("Error al obtener la lista de t�tulos: " + e.getMessage());
	    }

	    return titulos;
	}

	public boolean ModificarLibro(Libro libro) {
        String sql = "UPDATE LIBROS SET TITULO = ?, AUTOR = ?, GENERO = ?, FECHAPUBLICACION = ? WHERE id = ?";

        try {
        	Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
           
            stmt.setString(1, libro.getTitulo());
            stmt.setString(2, libro.getAutor());
            stmt.setString(3, libro.getGenero().name());
            stmt.setDate(4, Date.valueOf(libro.getFechaPublicacion()));
            stmt.setInt(5, libro.getId());
            
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
	
	
	public List<LibroPrestadoTabla> ListaLibrosPrestadosTabla(Ejemplar ejemplar) {
	    List<LibroPrestadoTabla> titulos = new ArrayList<>();
	    String sql = "SELECT TITULO FROM LIBROS WHERE ID = ?";

	    try (Connection conexion = DBConnection.getConnection();
	         PreparedStatement pstmt = conexion.prepareStatement(sql)) {

	        pstmt.setInt(1, ejemplar.getLibro().getId()); 
	        ResultSet rs = pstmt.executeQuery();

	        while (rs.next()) {
	            titulos.add(new LibroPrestadoTabla(rs.getString("titulo")));
	        }

	    } catch (SQLException e) {
	        System.err.println("Error al obtener la lista de t�tulos: " + e.getMessage());
	    }

	    return titulos;
	}
	
	public boolean EliminarLibroPorTitulo(String titulo) {
	    String sql = "DELETE FROM LIBROS WHERE TITULO = ?";

	    try (Connection conexion = DBConnection.getConnection();
	         PreparedStatement pstmt = conexion.prepareStatement(sql)) {

	        pstmt.setString(1, titulo);
	        int filasAfectadas = pstmt.executeUpdate();

	        return filasAfectadas > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return false;
	}


}
