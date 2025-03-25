package utiles;

import modelo.Estanteria;
import modelo.Genero;

public class AsignarEstanteria {

	private Estanteria estanteria;
	
	public AsignarEstanteria() {
		
	}
	
	public Estanteria obtenerUbicacionPorGenero(Genero genero) {
        // Asignamos la ubicación según el género
        if (genero.equals(Genero.FANTASIA)) {
            return Estanteria.EFANTASIA;
        } else if (genero.equals(Genero.TERROR)) {
            return Estanteria.ETERROR;
        } else if (genero.equals(Genero.POLICIAL)) {
            return Estanteria.EPOLICIAL;
        } else if (genero.equals(Genero.MISTERIO)) {
            return Estanteria.EMISTERIO;
        } else if (genero.equals(Genero.ROMANCE)) {
            return Estanteria.EROMANCE;
        } else {
            // Default value (si el género no coincide con ninguno)
            return Estanteria.EFANTASIA;
        }
    }

	public Estanteria getEstanteria() {
		return estanteria;
	}

	public void setEstanteria(Estanteria estanteria) {
		this.estanteria = estanteria;
	}
	
	
	
	
}
