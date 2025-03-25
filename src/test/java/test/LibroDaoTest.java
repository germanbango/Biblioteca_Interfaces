package test;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import dao.LibroDao;
import modelo.Libro;
import modelo.Genero;
import utiles.DBConnection;

public class LibroDaoTest {

 

    
    @Mock
    private Connection connectionMock;
    
    @Mock
    private PreparedStatement preparedStatementMock;
    
    @Mock
    private ResultSet resultSetMock;

    private LibroDao libroDao;

    @SuppressWarnings("deprecation")
    @Before
    public void setUp() { 
        MockitoAnnotations.initMocks(this);
        libroDao = new LibroDao();
        
        // Mock de la conexión a la base de datos
        try (MockedStatic<DBConnection> mockedStatic = mockStatic(DBConnection.class)) {
            mockedStatic.when(DBConnection::getConnection).thenReturn(connectionMock);
        }
    }
    @Test
    public void testRegistrarLibroExitoso() throws SQLException {
        // Preparar datos de prueba
        Libro libro = new Libro();
        libro.setTitulo("Test Libro");
        libro.setAutor("Autor Test");
        libro.setGenero(Genero.FANTASIA); // Cambio aquí de FICCION a FANTASIA
        libro.setFechaPublicacion(java.time.LocalDate.of(2022, 1, 1));

        // Mockear el PreparedStatement y ResultSet
        when(connectionMock.prepareStatement(any(String.class), eq(PreparedStatement.RETURN_GENERATED_KEYS)))
            .thenReturn(preparedStatementMock);
        when(preparedStatementMock.executeUpdate()).thenReturn(1); // Simula que se insertaron filas
        when(preparedStatementMock.getGeneratedKeys()).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(true); // Simula que hay una clave generada
        when(resultSetMock.getInt(1)).thenReturn(1); // Simula que el ID generado es 1 (significando éxito)

        // Ejecutar la prueba
        boolean result = libroDao.RegistrarLibro(libro);

        // Verificar el resultado
        assertTrue(result);  // Verifica que el libro se registró correctamente
        assertEquals(1, libro.getId()); // Verifica que el ID se haya asignado correctamente
    }


    @Test
    public void testRegistrarLibroFallido() throws SQLException {
        // Preparar datos de prueba
        Libro libro = new Libro();
        libro.setTitulo("Test Libro");
        libro.setAutor("Autor Test");
        libro.setGenero(Genero.FANTASIA); // Cambio aquí de FICCION a FANTASIA
        libro.setFechaPublicacion(java.time.LocalDate.of(2022, 1, 1));

        // Mockear el PreparedStatement
        when(connectionMock.prepareStatement(any(String.class), eq(PreparedStatement.RETURN_GENERATED_KEYS))).thenReturn(preparedStatementMock);
        when(preparedStatementMock.executeUpdate()).thenReturn(0); // Simula que no se han afectado filas

        // Ejecutar la prueba
        boolean result = libroDao.RegistrarLibro(libro);

        // Verificar que no se registró el libro
        assertFalse(result);
        assertEquals(0, libro.getId()); // El ID no debe cambiar
    }

    @Test
    public void testListaTitulos() throws SQLException {
        // Mockear el ResultSet
        when(connectionMock.prepareStatement(any(String.class))).thenReturn(preparedStatementMock);
        when(preparedStatementMock.executeQuery()).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(resultSetMock.getString("titulo")).thenReturn("Libro 1").thenReturn("Libro 2");

        // Ejecutar la prueba
        List<String> titulos = libroDao.ListaTitulos();

        // Verificar los resultados
        assertNotNull(titulos);
        assertEquals(2, titulos.size());
        assertTrue(titulos.contains("Libro 1"));
        assertTrue(titulos.contains("Libro 2"));
    }

    @Test
    public void testDatosLibro() throws SQLException {
        // Preparar datos de prueba
        String titulo = "Test Libro";

        // Mockear el ResultSet
        when(connectionMock.prepareStatement(any(String.class))).thenReturn(preparedStatementMock);
        when(preparedStatementMock.executeQuery()).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(true).thenReturn(false);
        when(resultSetMock.getInt("id")).thenReturn(1);
        when(resultSetMock.getString("titulo")).thenReturn(titulo);
        when(resultSetMock.getString("autor")).thenReturn("Autor Test");
        when(resultSetMock.getString("genero")).thenReturn("FANTASIA"); // Cambio aquí de FICCION a FANTASIA
        when(resultSetMock.getDate("fechapublicacion")).thenReturn(java.sql.Date.valueOf("2022-01-01"));

        // Ejecutar la prueba
        Libro libro = libroDao.DatosLibro(titulo);

        // Verificar el resultado
        assertNotNull(libro);
        assertEquals("Test Libro", libro.getTitulo());
        assertEquals("Autor Test", libro.getAutor());
        assertEquals(Genero.FANTASIA, libro.getGenero()); // Cambio aquí de FICCION a FANTASIA
        assertEquals(java.time.LocalDate.of(2022, 1, 1), libro.getFechaPublicacion());
    }

    @Test
    public void testEliminarLibroPorTituloExitoso() throws SQLException {
        // Mockear PreparedStatement
        when(connectionMock.prepareStatement(any(String.class))).thenReturn(preparedStatementMock);
        when(preparedStatementMock.executeUpdate()).thenReturn(1); // Simula una fila afectada

        // Ejecutar la prueba
        boolean result = libroDao.EliminarLibroPorTitulo("Test Libro");

        // Verificar que se eliminó correctamente
        assertTrue(result);
    }

    @Test
    public void testEliminarLibroPorTituloFallido() throws SQLException {
        // Mockear PreparedStatement
        when(connectionMock.prepareStatement(any(String.class))).thenReturn(preparedStatementMock);
        when(preparedStatementMock.executeUpdate()).thenReturn(0); // Simula que no se eliminó nada

        // Ejecutar la prueba
        boolean result = libroDao.EliminarLibroPorTitulo("Test Libro");

        // Verificar que no se eliminó el libro
        assertFalse(result);
    }
}