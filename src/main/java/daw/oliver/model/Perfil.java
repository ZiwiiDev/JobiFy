package daw.oliver.model;
/*-------------------------------------------------------------*/
// Importo las clases necesarias de Jakarta Persistence para la persistencia de datos
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
/*-------------------------------------------------------------*/
// Anoto la clase como una entidad JPA
@Entity
@Table(name="Perfiles")	// Mapeo esta clase a la tabla "Perfiles" en la base de datos
public class Perfil {
	/*-------------------------------------------------------------*/
	/* VARIABLES */
	
	@Id // Indico que este campo es la clave primaria
	@GeneratedValue(strategy=GenerationType.IDENTITY) // Utilizo la estrategia de generación de claves primarias AUTO_INCREMENT (IDENTITY)
	private Integer id;
	
	private String perfil;
	/*-------------------------------------------------------------*/
	/* GETTERS Y SETTERS */
	
	// Obtengo el valor del campo 'id'
	public Integer getId() {
		return id;
	}//end getId()

	// Establezco el valor del campo 'id'
	public void setId(Integer id) {
		this.id = id;
	}//end setId(Integer id)

	// Obtengo el valor del campo 'perfil'
	public String getPerfil() {
		return perfil;
	}//end getPerfil()

	// Establezco el valor del campo 'perfil'
	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}//end setPerfil(String perfil)
	/*-------------------------------------------------------------*/
	/* toString() */
	
	// Sobrescribo el método toString para representar la clase como una cadena
	@Override
	public String toString() {
		return "Perfil [id=" + id + ", perfil=" + perfil + "]";
	}//end toString()
	/*-------------------------------------------------------------*/
}//end class Perfil
/*-------------------------------------------------------------*/
