package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelo.Usuario;
import modelo.UsuarioTabla;
import modelo.UsuarioTipo;
import utiles.DBConnection;

public class UsuarioDao {
	
	public void CrearCliente(Usuario usuario){
	
		String sql = "INSERT INTO USUARIOS(NOMBRE,APELLIDO,CORREOELECTRONICO,CONTRASEÑA,TIPOUSUARIO)"
				+ " VALUES (?,?,?,?,'cliente')";
		
		try {
			Connection conexion = DBConnection.getConnection();
			PreparedStatement pstmt = conexion.prepareStatement(sql);
			pstmt.setString(1, usuario.getNombre());
			pstmt.setString(2, usuario.getApellidos());
			pstmt.setString(3, usuario.getCorreoElectronico());
			pstmt.setString(4, usuario.getContraseña());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void CrearUsuario(Usuario usuario) {
		
		String sql = "INSERT INTO USUARIOS(NOMBRE,APELLIDO,CORREOELECTRONICO,CONTRASEÑA,TIPOUSUARIO)"
				+ " VALUES (?,?,?,?,?)";
		
		try {
			Connection conexion = DBConnection.getConnection();
			PreparedStatement pstmt = conexion.prepareStatement(sql);
			pstmt.setString(1, usuario.getNombre());
			pstmt.setString(2, usuario.getApellidos());
			pstmt.setString(3, usuario.getCorreoElectronico());
			pstmt.setString(4, usuario.getContraseña());
			pstmt.setString(5, usuario.getUsuarioTipo().name().toLowerCase());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public Usuario EncontrarUsuario(String correoElectronico, String contraseña) {
        String sql = "SELECT * FROM Usuarios WHERE correoelectronico = ? AND contraseña = ?";
        Usuario usuario = null;

        try {
        	Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, correoElectronico);
            stmt.setString(2, contraseña);
            
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setCorreoElectronico(rs.getString("correoelectronico"));
                usuario.setUsuarioTipo(UsuarioTipo.valueOf(rs.getString("tipousuario").toUpperCase()) );
                usuario.setContraseña(rs.getString("contraseña"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuario;
    }
	
	public int ValidarUsuario(String correoElectronico, String contraseña) {
        String sql = "SELECT * FROM Usuarios WHERE correoelectronico = ? AND contraseña = ?";

        try {
        	Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, correoElectronico);
            stmt.setString(2, contraseña);
            
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
            	return rs.getInt("id");
            	
            }else {
            	return -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
        

    }
	
	public UsuarioTipo EncontrarRol(String correoElectronico, String contraseña) {
        String sql = "SELECT tipousuario FROM Usuarios WHERE correoelectronico = ? AND contraseña = ?";
        
        
        
        try {
        	Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, correoElectronico);
            stmt.setString(2, contraseña);
            
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
               return UsuarioTipo.valueOf(rs.getString("tipousuario").toUpperCase());
            }else {
            	return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            	return null;
        }

    }
	


	public boolean ModificarUsuario(Usuario usuario) {
        String sql = "UPDATE USUARIOS SET NOMBRE = ?, APELLIDO = ?, CORREOELECTRONICO = ?, CONTRASEÑA = ? WHERE id = ?";

        try {
        	Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
           
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getApellidos());
            stmt.setString(3, usuario.getCorreoElectronico());
            stmt.setString(4, usuario.getContraseña());
            stmt.setInt(5, usuario.getId());
            
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
	
	public Usuario ImprimirUsuario(int id ) {
        String sql = "SELECT * FROM Usuarios WHERE id = ?";
        Usuario usuario = null;

        try {
        	Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, id);
            
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setApellidos(rs.getString("apellido"));
                usuario.setCorreoElectronico(rs.getString("correoelectronico"));
                usuario.setUsuarioTipo(UsuarioTipo.valueOf(rs.getString("tipousuario").toUpperCase()) );
                usuario.setContraseña(rs.getString("contraseña"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuario;
    }
	
	public boolean ModificarUsuarioPorCorreo(Usuario usuario, String correoAntiguo) {
	    String sql = "UPDATE USUARIOS SET NOMBRE = ?, APELLIDO = ?, CORREOELECTRONICO = ?, CONTRASEÃÑA = ?, TIPOUSUARIO = ? WHERE CORREOELECTRONICO = ?";

	    try {
	        Connection conn = DBConnection.getConnection();
	        PreparedStatement stmt = conn.prepareStatement(sql);

	        stmt.setString(1, usuario.getNombre());
	        stmt.setString(2, usuario.getApellidos());
	        stmt.setString(3, usuario.getCorreoElectronico()); 
	        stmt.setString(4, usuario.getContraseña());
	        stmt.setString(5, usuario.getUsuarioTipo().name());
	        stmt.setString(6, correoAntiguo); 

	        int filasAfectadas = stmt.executeUpdate();
	        return filasAfectadas > 0; 
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}

	
	public List<UsuarioTabla> obtenerUsuarios() {
	    List<UsuarioTabla> listaUsuarios = new ArrayList<>();
	    String sql = "SELECT correoelectronico, tipousuario FROM Usuarios"; 

	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql);
	         ResultSet rs = stmt.executeQuery()) {

	        while (rs.next()) {
	            listaUsuarios.add(new UsuarioTabla(rs.getString("correoelectronico"), rs.getString("tipousuario")));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return listaUsuarios;
	}


	public Usuario EncontrarUsuarioPorCorreo(String correoElectronico) {
	    String sql = "SELECT * FROM Usuarios WHERE correoelectronico = ?";
	    Usuario usuario = null;

	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        
	        stmt.setString(1, correoElectronico);
	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	            usuario = new Usuario();
	            usuario.setId(rs.getInt("id"));
	            usuario.setNombre(rs.getString("nombre"));
	            usuario.setApellidos(rs.getString("apellido"));
	            usuario.setCorreoElectronico(rs.getString("correoelectronico"));
	            usuario.setUsuarioTipo(UsuarioTipo.valueOf(rs.getString("tipousuario").toUpperCase()));
	            usuario.setContraseña(rs.getString("contraseÃña"));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return usuario;
	}
	
	 public boolean BorrarUsuario(Usuario usuario) {
		    if (usuario == null) {
		        System.out.println("Error: El usuario es nulo");
		        return false;  
		    }
		    
		    String sql = "DELETE FROM USUARIOS WHERE CORREOELECTRONICO = ?";
		    
		    try (Connection conexion = DBConnection.getConnection();
		         PreparedStatement pstmt = conexion.prepareStatement(sql)) {
		        
		        pstmt.setString(1, usuario.getCorreoElectronico());
		        
		        int filasAfectadas = pstmt.executeUpdate(); 
		        
		        return filasAfectadas > 0; 

		    } catch (SQLException e) {
		        System.err.println("Error al borrar el Usuario: " + e.getMessage());
		    }

		    return false;
		}

	
}
