package daw.oliver.model;
/*-------------------------------------------------------------*/
//Importo las clases necesarias de Jakarta Persistence para la persistencia de datos
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
/*-------------------------------------------------------------*/
//Anoto la clase como una entidad JPA
@Entity
@Table(name="Vacantes")	// Mapeo esta clase a la tabla "Categorias" en la base de datos
public class Vacante {
	/*-------------------------------------------------------------*/
	/* VARIABLES */
	
	@Id	// Indico que este campo es la clave primaria
	@GeneratedValue(strategy=GenerationType.IDENTITY)	// Utilizo la estrategia de generación de claves primarias AUTO_INCREMENT (IDENTITY)
	private Integer id;
	
	private String nombre;
	private String descripcion;
	private Date fecha;
	private Double salario;			// Tiene que ser de tipo clase "Double" y no primitivo, porque sino se pone siempre 0.0 por defecto.
	private Integer destacado;
	private String imagen="no-image.png";
	private String estatus;
	private String detalles;
	
	// Para ignorar este atributo al realizar el mapeo se utiliza "TRANSIENT"
	// Si no le ponemos el transcient da error porque en la base de datos se llama "idCategoria" y es FK de la tabla "Categorias"
	//@Transient
	//@ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@OneToOne
	@JoinColumn(name="idCategoria") // "idCategoria" es el nombre de la FK
	private Categoria categoria;
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
	}//end setNombre(String nombre
	
	// Obtengo el valor del campo 'descripcion'
	public String getDescripcion() {
		return descripcion;
	}//end getDescripcion()
	
	// Establezco el valor del campo 'descripcion'
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}//end setDescripcion(String descripcion)
	
	// Obtengo el valor del campo 'fecha'
	public Date getFecha() {
		return fecha;
	}//end getFecha()
	
	// Establezco el valor del campo 'fecha'
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}//end setFecha(Date fecha)
	
	// Obtengo el valor del campo 'salario'
	public Double getSalario() {
		return salario;
	}//end getSalario()
	
	// Establezco el valor del campo 'salario'
	public void setSalario(Double salario) {
		this.salario = salario;
	}//end setSalario(Double salario)

	// Obtengo el valor del campo 'destacado'
	public Integer getDestacado() {
		return destacado;
	}//end getDestacado()

	// Establezco el valor del campo 'destacado'
	public void setDestacado(Integer destacado) {
		this.destacado = destacado;
	}//end setDestacado(Integer destacado)

	// Obtengo el valor del campo 'imagen'
	public String getImagen() {
		return imagen;
	}//end getImagen()

	// Establezco el valor del campo 'imagen'
	public void setImagen(String imagen) {
		this.imagen = imagen;
	}//end setImagen(String imagen)

	// Obtengo el valor del campo 'estatus'
	public String getEstatus() {
		return estatus;
	}//end getEstatus()

	// Establezco el valor del campo 'estatus'
	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}//end setEstatus(String estatus)

	// Obtengo el valor del campo 'detalles'
	public String getDetalles() {
		return detalles;
	}//end getDetalles()

	// Establezco el valor del campo 'detalles'
	public void setDetalles(String detalles) {
		this.detalles = detalles;
	}//end setDetalles(String detalles)

	// Obtengo el valor del campo 'idCategoria'
	public Categoria getCategoria() {
		return categoria;
	}//end getCategoria()

	// Establezco el valor del campo 'idCategoria'
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}//end setCategoria(Categoria categoria)

	// Método para reiniciar la ruta de la imagen
	public void resetImagen() {
		this.imagen = null;
	}//end 
	/*-------------------------------------------------------------*/
	/* toString() */
	
	// Sobrescribo el método toString para representar la clase como una cadena
	@Override
	public String toString() {
		return "Vacante [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", fecha=" + fecha
				+ ", salario=" + salario + ", destacado=" + destacado + ", imagen=" + imagen + ", estatus=" + estatus
				+ ", detalles=" + detalles + ", categoria=" + categoria + "]";
	}//end toString()
	/*-------------------------------------------------------------*/
}//end class Vacante
/*-------------------------------------------------------------*/
