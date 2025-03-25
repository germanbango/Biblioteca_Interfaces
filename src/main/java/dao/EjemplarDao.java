package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import modelo.Ejemplar;
import modelo.Estanteria;
import modelo.Libro;
import modelo.Prestamo;
import utiles.DBConnection;

public class EjemplarDao {

	PrestamoDao prestamoDao = new PrestamoDao();
	
	public boolean CrearEjemplar (Ejemplar ejemplar){
		
		String sql = "INSERT INTO EJEMPLARES(LIBRO_ID,ESTADO,UBICACION)"
				+ " VALUES (?,?,?)";
		
		try {
			Connection conexion = DBConnection.getConnection();
			PreparedStatement pstmt = conexion.prepareStatement(sql);
			pstmt.setInt(1, ejemplar.getLibro().getId());
			pstmt.setInt(2, ejemplar.isEstado()?1:0);
			pstmt.setString(3, ejemplar.getUbicacion().name());
			pstmt.executeUpdate();
			
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean PrestarEjemplar(Libro libro) {
	    List<Ejemplar> ejemplares = new ArrayList<>(); 
	    String sql = "SELECT ID,LIBRO_ID,ESTADO,UBICACION FROM EJEMPLARES WHERE LIBRO_ID = ?";
	    
	    try (Connection conexion = DBConnection.getConnection();
	         PreparedStatement pstmt = conexion.prepareStatement(sql)) {
	        
	        pstmt.setInt(1, libro.getId());
	        ResultSet rs = pstmt.executeQuery();
	        
	        while (rs.next()) {
	            Ejemplar ejemplar = new Ejemplar();
	            ejemplar.setId(rs.getInt("id"));
	            ejemplar.setLibro(libro);
	            ejemplar.setEstado(rs.getInt("estado") == 1);
	            ejemplar.setUbicacion(Estanteria.valueOf(rs.getString("ubicacion").toUpperCase()));
	            ejemplares.add(ejemplar);
	        }
	        
	        // Prestar solo el primer ejemplar disponible
	        for (Ejemplar ejemplar : ejemplares) {
	            if (!ejemplar.isEstado()) { // Si está disponible (estado == false)
	                Prestamo prestamo = new Prestamo(LocalDate.now(), LocalDate.now().plusDays(15), ejemplar);
	                prestamoDao.CrearPrestamo(prestamo);
	                ModificarEjemplarEstado(ejemplar);
	                return true; // Salir después de prestar un ejemplar
	            }
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    
	    return false; // No hay ejemplares disponibles
	}

	
	public boolean Disponible(Libro libro) {
		List<Ejemplar> ejemplares = new ArrayList<>(); 
		String sql = "SELECT ID,LIBRO_ID,ESTADO,UBICACION FROM EJEMPLARES WHERE LIBRO_ID = ?";
		
		try{
			Connection conexion = DBConnection.getConnection();
			PreparedStatement pstmt = conexion.prepareStatement(sql);
			
			pstmt.setInt(1, libro.getId());
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Ejemplar ejemplar = new Ejemplar();
				ejemplar.setId(rs.getInt("id"));
				ejemplar.setLibro(libro);
				ejemplar.setEstado(rs.getInt("estado")==1);
				ejemplar.setUbicacion(Estanteria.valueOf(rs.getString("ubicacion").toUpperCase()));
				ejemplares.add(ejemplar);
			}
			for(Ejemplar ejemplar : ejemplares) {
				if(ejemplar.isEstado()== false) {
					return true;
					
				}
			}
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean ModificarEjemplarEstado(Ejemplar ejemplar) {
        String sql = "UPDATE EJEMPLARES SET ESTADO = ? WHERE id = ?";

        try {
        	Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
           
            stmt.setInt(1, ejemplar.isEstado()?0:1);
            stmt.setInt(2, ejemplar.getId());
            
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
	

	public List<Ejemplar> ObtenerEjemplares(Libro libro) {
		List<Ejemplar> ejemplares = new ArrayList<>(); 
		String sql = "SELECT ID,LIBRO_ID,ESTADO,UBICACION FROM EJEMPLARES WHERE LIBRO_ID = ?";
		
		try{
			Connection conexion = DBConnection.getConnection();
			PreparedStatement pstmt = conexion.prepareStatement(sql);
			
			pstmt.setInt(1, libro.getId());
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Ejemplar ejemplar = new Ejemplar();
				ejemplar.setId(rs.getInt("id"));
				ejemplar.setLibro(libro);
				ejemplar.setEstado(rs.getInt("estado")==1);
				ejemplar.setUbicacion(Estanteria.valueOf(rs.getString("ubicacion").toUpperCase()));
				ejemplares.add(ejemplar);
			}
			
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ejemplares;
	}
	
	public boolean DevolverEjemplar(Ejemplar ejemplar) {
		String sql= "UPDATE EJEMPLARES SET ESTADO = 0 WHERE id = ?";
		 try {
	        	Connection conn = DBConnection.getConnection();
	            PreparedStatement stmt = conn.prepareStatement(sql);
	           
	            stmt.setInt(1, ejemplar.getId());
	            
	            stmt.executeUpdate();
	            return true;
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return false;
	    
	
		
	}
	
	public Ejemplar ObtenerEjemplare(Libro libro) {
		Ejemplar ejemplar= null;
		String sql = "SELECT ID,LIBRO_ID,ESTADO,UBICACION FROM EJEMPLARES WHERE LIBRO_ID = ?";
		
		try{
			Connection conexion = DBConnection.getConnection();
			PreparedStatement pstmt = conexion.prepareStatement(sql);
			
			pstmt.setInt(1, libro.getId());
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ejemplar = new Ejemplar();
				ejemplar.setId(rs.getInt("id"));
				ejemplar.setLibro(libro);
				ejemplar.setEstado(rs.getInt("estado")==1);
				ejemplar.setUbicacion(Estanteria.valueOf(rs.getString("ubicacion").toUpperCase()));
				return ejemplar;
			}
			
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ejemplar;
	}
	
	public boolean EliminarEjemplaresPorLibro(Libro libro) {
	    String sql = "DELETE FROM EJEMPLARES WHERE LIBRO_ID = ?";
	    
	    try (Connection conexion = DBConnection.getConnection();
	         PreparedStatement pstmt = conexion.prepareStatement(sql)) {
	        
	        pstmt.setInt(1, libro.getId());
	        int filasAfectadas = pstmt.executeUpdate();
	        
	        return filasAfectadas > 0; // Retorna true si se eliminó al menos un ejemplar
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    
	    return false;
	}

	
	
}
