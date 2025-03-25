package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Usuario implements Serializable {
	
	private int id;
	private String nombre;
	private String apellidos;
	private String correoElectronico;
	private String contraseña;
	private UsuarioTipo usuarioTipo;
	private List<Prestamo> prestamo;
	
	public Usuario(){
		
	}

	public Usuario( String nombre, String apellidos, String correoElectronico, String contraseña,
			UsuarioTipo usuarioTipo) {
		super();
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.correoElectronico = correoElectronico;
		this.contraseña = contraseña;
		this.usuarioTipo = usuarioTipo;
		this.prestamo = new ArrayList<Prestamo>();
	}


	

	public Usuario( String nombre, String apellidos, String correoElectronico, String contraseña,
			UsuarioTipo usuarioTipo, List<Prestamo> prestamo) {
		super();
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.correoElectronico = correoElectronico;
		this.contraseña = contraseña;
		this.usuarioTipo = usuarioTipo;
		this.prestamo = prestamo;
	}




	public int getId() {
		return id;
	}




	public void setId(int id) {
		this.id = id;
	}




	public String getNombre() {
		return nombre;
	}




	public void setNombre(String nombre) {
		this.nombre = nombre;
	}




	public String getApellidos() {
		return apellidos;
	}




	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}




	public String getCorreoElectronico() {
		return correoElectronico;
	}




	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}




	public String getContraseña() {
		return contraseña;
	}




	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}




	public UsuarioTipo getUsuarioTipo() {
		return usuarioTipo;
	}




	public void setUsuarioTipo(UsuarioTipo usuarioTipo) {
		this.usuarioTipo = usuarioTipo;
	}




	public List<Prestamo> getPrestamo() {
		return prestamo;
	}




	public void setPrestamo(List<Prestamo> prestamo) {
		this.prestamo = prestamo;
	}
	

}
