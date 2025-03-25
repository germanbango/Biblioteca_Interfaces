package modelo;

import java.io.Serializable;
import java.util.List;

public class Ejemplar implements Serializable{
	
	private int id;
	private Libro libro;
	private boolean estado;
	private String ubicacion;
	private List<Prestamo> prestamos;
	
	public Ejemplar() {
		
	}


	public Ejemplar(int id, Libro libro, boolean estado, String ubicacion, List<Prestamo> prestamos) {
		super();
		this.id = id;
		this.libro = libro;
		this.estado = estado;
		this.ubicacion = ubicacion;
		this.prestamos = prestamos;
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


	public String getUbicacion() {
		return ubicacion;
	}


	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}


	public List<Prestamo> getPrestamos() {
		return prestamos;
	}


	public void setPrestamos(List<Prestamo> prestamos) {
		this.prestamos = prestamos;
	}


}
