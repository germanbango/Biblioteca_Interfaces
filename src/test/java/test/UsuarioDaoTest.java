
package test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.MockedStatic;

import dao.UsuarioDao;
import modelo.Usuario;
import modelo.UsuarioTipo;
import utiles.DBConnection;

public class UsuarioDaoTest {
    
    private UsuarioDao usuarioDao;
    
    @Mock private Connection mockConnection;
    @Mock private PreparedStatement mockPreparedStatement;
    @Mock private ResultSet mockResultSet;
    
    @SuppressWarnings("deprecation")
	@Before
    public void setUp() throws SQLException {
        MockitoAnnotations.initMocks(this);
        usuarioDao = new UsuarioDao();
        
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
    }
    
    @Test
    public void testCrearUsuario() throws SQLException {
        try (MockedStatic<DBConnection> mockedDb = Mockito.mockStatic(DBConnection.class)) {
            mockedDb.when(DBConnection::getConnection).thenReturn(mockConnection);
            when(mockPreparedStatement.executeUpdate()).thenReturn(1);
            
            Usuario usuario = new Usuario("Juan", "Perez", "juan@example.com", "password", UsuarioTipo.CLIENTE);
            usuarioDao.CrearUsuario(usuario);
            
            verify(mockPreparedStatement, times(1)).executeUpdate();
        }
    }
    
    @Test
    public void testEncontrarUsuario() throws SQLException {
        try (MockedStatic<DBConnection> mockedDb = Mockito.mockStatic(DBConnection.class)) {
            mockedDb.when(DBConnection::getConnection).thenReturn(mockConnection);
            when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
            when(mockResultSet.next()).thenReturn(true);
            when(mockResultSet.getInt("id")).thenReturn(1);
            when(mockResultSet.getString("nombre")).thenReturn("Juan");
            when(mockResultSet.getString("correoelectronico")).thenReturn("juan@example.com");
            when(mockResultSet.getString("tipousuario")).thenReturn("cliente");
            when(mockResultSet.getString("contraseña")).thenReturn("password");
            
            Usuario usuario = usuarioDao.EncontrarUsuario("juan@example.com", "password");
            
            assertNotNull(usuario);
            assertEquals("Juan", usuario.getNombre());
            assertEquals("juan@example.com", usuario.getCorreoElectronico());
            assertEquals(UsuarioTipo.CLIENTE, usuario.getUsuarioTipo());
        }
    }
    
    @Test
    public void testBorrarUsuario() throws SQLException {
        try (MockedStatic<DBConnection> mockedDb = Mockito.mockStatic(DBConnection.class)) {
            mockedDb.when(DBConnection::getConnection).thenReturn(mockConnection);
            when(mockPreparedStatement.executeUpdate()).thenReturn(1);
            
            Usuario usuario = new Usuario("Juan", "Perez", "juan@example.com", "password", UsuarioTipo.CLIENTE);
            boolean resultado = usuarioDao.BorrarUsuario(usuario);
            
            assertTrue(resultado);
            verify(mockPreparedStatement, times(1)).executeUpdate();
        }
    }
}
