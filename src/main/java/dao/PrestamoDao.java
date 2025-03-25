package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelo.Ejemplar;
import modelo.LibroPrestadoTabla;
import modelo.Prestamo;
import modelo.Usuario;
import utiles.DBConnection;
import utiles.Sesion;

public class PrestamoDao {

	public void CrearPrestamo(Prestamo prestamo){
		
		String sql = "INSERT INTO PRESTAMOS(USUARIO_ID,EJEMPLAR_ID,FECHAPRESTAMO,FECHADEVOLUCION)"
				+ " VALUES (?,?,?,?)";
		
		
		
		try {
			Connection conexion = DBConnection.getConnection();
			PreparedStatement pstmt = conexion.prepareStatement(sql);
			pstmt.setInt(1, Sesion.getId());
			pstmt.setInt(2, prestamo.getEjemplar().getId());
			pstmt.setDate(3, Date.valueOf(prestamo.getFechaPrestamo()));
			pstmt.setDate(4, Date.valueOf(prestamo.getFechaDevolucion()));
			
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
		
	 public List<LibroPrestadoTabla> ListaLibrosPrestadosPorUsuario() {
	        
		 List<LibroPrestadoTabla> librosPrestados = new ArrayList<>();   
		 String sql = "SELECT l.TITULO "
	                   + "FROM PRESTAMOS p "
	                   + "JOIN EJEMPLARES e ON p.EJEMPLAR_ID = e.ID "
	                   + "JOIN LIBROS l ON e.LIBRO_ID = l.ID "
	                   + "WHERE p.USUARIO_ID = ?";
	       
		 try (Connection conexion = DBConnection.getConnection();
	            PreparedStatement pstmt = conexion.prepareStatement(sql)) {
	          
	            pstmt.setInt(1, Sesion.getId()); 
	            ResultSet rs = pstmt.executeQuery();
	            
	            while (rs.next()) {
	                librosPrestados.add(new LibroPrestadoTabla(rs.getString("titulo")));
	            }

	        } catch (SQLException e) {
	            System.err.println("Error al obtener los libros prestados: " + e.getMessage());
	        }
   
		 return librosPrestados;
	    
	 }

	 public Prestamo obtenerPrestamoPorLibro(int libroId) {
		   
		 String sql = "SELECT p.FECHADEVOLUCION "
		               + "FROM PRESTAMOS p "
		               + "JOIN EJEMPLARES e ON p.EJEMPLAR_ID = e.ID "
		               + "WHERE e.LIBRO_ID = ? AND p.USUARIO_ID = ?";
		    
		 Prestamo prestamo = null;
		 
		 try (Connection conexion = DBConnection.getConnection();
		         PreparedStatement pstmt = conexion.prepareStatement(sql)) {
		        
		        pstmt.setInt(1, libroId);
		        pstmt.setInt(2, Sesion.getId());
		        
		        ResultSet rs = pstmt.executeQuery();
		        
		        if (rs.next()) {
		        	prestamo = new Prestamo();
		            prestamo.setFechaDevolucion( rs.getDate("fechadevolucion").toLocalDate());
		            return prestamo;
		        }

		    } catch (SQLException e) {
		        System.err.println("Error al obtener el pr�stamo: " + e.getMessage());
		    }

		    
		 return null;
		
	 }
	 
	 public boolean BorrarPrestamo(Prestamo prestamo) {
		    if (prestamo == null) {
		        System.out.println("Error: El préstamo es nulo");
		        return false;  
		    }
		    
		    String sql = "DELETE FROM PRESTAMOS WHERE EJEMPLAR_ID = ?";
		    
		    try (Connection conexion = DBConnection.getConnection();
		         PreparedStatement pstmt = conexion.prepareStatement(sql)) {
		        
		        pstmt.setInt(1, prestamo.getEjemplar().getId());
		        
		        int filasAfectadas = pstmt.executeUpdate(); 
		        
		        return filasAfectadas > 0; 

		    } catch (SQLException e) {
		        System.err.println("Error al borrar el préstamo: " + e.getMessage());
		    }

		    return false;
		}

	 
	 public Prestamo SeleccionarPrestamo(Ejemplar ejemplar) {
		 Prestamo prestamo = null;
		 String sql = "SELECT * FROM PRESTAMOS WHERE EJEMPLAR_ID = ?";
		 
		 
		try {
			Connection conexion = DBConnection.getConnection();
			 PreparedStatement pstmt = conexion.prepareStatement(sql);
			 
			 pstmt.setInt(1, ejemplar.getId ());
			 
			 ResultSet rs = pstmt.executeQuery();
			 
			 if(rs.next()) {
				 prestamo = new Prestamo();
				 Usuario usuario = new Usuario();
				 prestamo.setId(rs.getInt("id"));
				 usuario.setId(rs.getInt("usuario_id"));
				 prestamo.setUsuario(usuario);
				 prestamo.setEjemplar(ejemplar);
				 prestamo.setFechaPrestamo(rs.getDate("fechaprestamo").toLocalDate());
				 prestamo.setFechaDevolucion(rs.getDate("fechadevolucion").toLocalDate());
				 
			 }
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		 
		 return prestamo;
	 }
		
	 public List<Prestamo> ObtenerPrestamosPorUsuario(int usuarioId) {
		    List<Prestamo> prestamos = new ArrayList<>();
		    String sql = "SELECT * FROM PRESTAMOS WHERE USUARIO_ID = ?";

		    try (Connection conexion = DBConnection.getConnection();
		         PreparedStatement pstmt = conexion.prepareStatement(sql)) {
		        
		        pstmt.setInt(1, usuarioId);
		        ResultSet rs = pstmt.executeQuery();

		        while (rs.next()) {
		            Prestamo prestamo = new Prestamo();
		            prestamo.setId(rs.getInt("id"));
		            prestamo.setFechaPrestamo(rs.getDate("fechaprestamo").toLocalDate());
		            prestamo.setFechaDevolucion(rs.getDate("fechadevolucion").toLocalDate());

		            // Obtener el ID del ejemplar
		            int ejemplarId = rs.getInt("ejemplar_id");
		            Ejemplar ejemplar = new Ejemplar();
		            ejemplar.setId(ejemplarId);
		            prestamo.setEjemplar(ejemplar);

		            prestamos.add(prestamo);
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }

		    return prestamos;
		}

	 public boolean EliminarPrestamosPorUsuario(int usuarioId) {
		    String sql = "DELETE FROM PRESTAMOS WHERE USUARIO_ID = ?";
		    
		    try (Connection conexion = DBConnection.getConnection();
		         PreparedStatement pstmt = conexion.prepareStatement(sql)) {
		        
		        pstmt.setInt(1, usuarioId);
		        int filasAfectadas = pstmt.executeUpdate();
		        
		        return filasAfectadas > 0;
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    
		    return false;
		}

	 
}
