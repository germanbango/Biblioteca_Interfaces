package modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class Libro implements Serializable {

	private int id;
	private String titulo;
	private String autor;
	private Genero genero;
	private LocalDate fechaPublicacion;
	private List<Ejemplar> ejemplar;
	
	public Libro() {
		
	}

	public Libro(int id, String titulo, String autor, Genero genero, LocalDate fechaPublicacion,
			List<Ejemplar> ejemplar) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.autor = autor;
		this.genero = genero;
		this.fechaPublicacion = fechaPublicacion;
		this.ejemplar = ejemplar;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public Genero getGenero() {
		return genero;
	}

	public void setGenero(Genero genero) {
		this.genero = genero;
	}

	public LocalDate getFechaPublicacion() {
		return fechaPublicacion;
	}

	public void setFechaPublicacion(LocalDate fechaPublicacion) {
		this.fechaPublicacion = fechaPublicacion;
	}

	public List<Ejemplar> getEjemplar() {
		return ejemplar;
	}

	public void setEjemplar(List<Ejemplar> ejemplar) {
		this.ejemplar = ejemplar;
	}
	
	
	
	
}
