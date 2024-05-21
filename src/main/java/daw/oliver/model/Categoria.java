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
@Table(name="Categorias")	// Mapeo esta clase a la tabla "Categorias" en la base de datos
public class Categoria {
	/*-------------------------------------------------------------*/
	/* VARIABLES */
	
	@Id	// Indico que este campo es la clave primaria
	@GeneratedValue(strategy=GenerationType.IDENTITY)	// Utilizo la estrategia de generación de claves primarias AUTO_INCREMENT (IDENTITY)
	private Integer id;
	
	private String nombre;
	private String descripcion;
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

	// Obtengo el valor del campo 'nombre'
	public String getNombre() {
		return nombre;
	}//end getNombre()

	// Establezco el valor del campo 'nombre'
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}//end setNombre(String nombre)

	// Obtengo el valor del campo 'descripcion'
	public String getDescripcion() {
		return descripcion;
	}//end getDescripcion()

	// Establezco el valor del campo 'descripcion'
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}//end setDescripcion(String descripcion)
	/*-------------------------------------------------------------*/
	/* toString() */
	
	// Sobrescribo el método toString para representar la clase como una cadena
	@Override
	public String toString() {
		return "Categoria [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + "]";
	}//end toString()
	/*-------------------------------------------------------------*/
}//end class Categoria
/*-------------------------------------------------------------*/
