package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import modelo.Usuario;
import modelo.UsuarioTipo;
import utiles.DBConnection;

public class UsuarioDao {
	
	public void CrearCliente(Usuario usuario){
	
		String sql = "INSERT INTO USUARIO(NOMBRE,APELLIDOS,CORREOELECTRONICO,CONTRASEÑA,TIPOUSUARIO)"
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
	
	public Usuario EncontrarUsuario(String correoElectronico, String contraseña) {
        String sql = "SELECT * FROM Usuario WHERE correoelectronico = ? AND contraseña = ?";
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
        String sql = "SELECT * FROM Usuario WHERE correoelectronico = ? AND contraseña = ?";

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
        String sql = "SELECT tipousuario FROM Usuario WHERE correoelectronico = ? AND contraseña = ?";
        
        
        
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
        String sql = "UPDATE USUARIO SET NOMBRE = ?, APELLIDOS = ?, CORREOELECTRONICO = ?, CONTRASEÑA = ? WHERE id = ?";

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
        String sql = "SELECT * FROM Usuario WHERE id = ?";
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
                usuario.setApellidos(rs.getString("apellidos"));
                usuario.setCorreoElectronico(rs.getString("correoelectronico"));
                usuario.setUsuarioTipo(UsuarioTipo.valueOf(rs.getString("tipousuario").toUpperCase()) );
                usuario.setContraseña(rs.getString("contraseña"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuario;
    }
}
