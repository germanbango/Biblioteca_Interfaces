package modelo;

import java.io.Serializable;
import java.util.List;

public class Ejemplar implements Serializable{
	
	private int id;
	private Libro libro;
	private boolean estado;
	private  Estanteria ubicacion;
	private List<Prestamo> prestamos;
	
	public Ejemplar() {
		
	}

	public Ejemplar(int id, Libro libro, boolean estado, Estanteria ubicacion, List<Prestamo> prestamos) {
		super();
		this.id = id;
		this.libro = libro;
		this.estado = estado;
		this.ubicacion = ubicacion;
		this.prestamos = prestamos;
	}

	public Ejemplar(Libro libro, boolean estado, Estanteria ubicacion, List<Prestamo> prestamos) {
		super();
		this.libro = libro;
		this.estado = estado;
		this.ubicacion = ubicacion;
		this.prestamos = prestamos;
	}
	
	

	

	public Ejemplar(Libro libro, Estanteria ubicacion) {
		super();
		this.libro = libro;
		this.ubicacion = ubicacion;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Libro getLibro() {
		return libro;
	}

	public void setLibro(Libro libro) {
		this.libro = libro;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public Estanteria getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(Estanteria ubicacion) {
		this.ubicacion = ubicacion;
	}

	public List<Prestamo> getPrestamos() {
		return prestamos;
	}

	public void setPrestamos(List<Prestamo> prestamos) {
		this.prestamos = prestamos;
	}
	
	
	

}

