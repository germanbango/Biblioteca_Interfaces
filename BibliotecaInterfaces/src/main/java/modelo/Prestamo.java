package modelo;

import java.io.Serializable;
import java.time.LocalDate;

public class Prestamo implements Serializable {
	
	private LocalDate fechaPrestamo;
	private LocalDate fechaDevolucion;
	private Ejemplar ejemplar;
	private Usuario usuario;
	
	public Prestamo() {
		
	}

	public Prestamo(LocalDate fechaPrestamo, LocalDate fechaDevolucion, Ejemplar ejemplar,
			Usuario usuario) {
		super();
		this.fechaPrestamo = fechaPrestamo;
		this.fechaDevolucion = fechaDevolucion;
		this.ejemplar = ejemplar;
		this.usuario = usuario;
	}

	public LocalDate getFechaPrestamo() {
		return fechaPrestamo;
	}

	public void setFechaPrestamo(LocalDate fechaPrestamo) {
		this.fechaPrestamo = fechaPrestamo;
	}

	public LocalDate getFechaDevolucion() {
		return fechaDevolucion;
	}

	public void setFechaDevolucion(LocalDate fechaDevolucion) {
		this.fechaDevolucion = fechaDevolucion;
	}

	public Ejemplar getEjemplar() {
		return ejemplar;
	}

	public void setEjemplar(Ejemplar ejemplar) {
		this.ejemplar = ejemplar;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	
		
	
	
	

}
