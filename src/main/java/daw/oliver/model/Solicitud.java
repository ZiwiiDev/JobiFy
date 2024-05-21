package daw.oliver.model;
/*-------------------------------------------------------------*/
//Importo las clases necesarias de Jakarta Persistence para la persistencia de datos
import java.time.LocalDate;
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
@Table(name = "Solicitudes")	// Mapeo esta clase a la tabla "Solicitudes" en la base de datos
public class Solicitud {
	/*-------------------------------------------------------------*/
	/* VARIABLES */
	
	@Id // Indico que este campo es la clave primaria
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Utilizo la estrategia de generación de claves primarias AUTO_INCREMENT (IDENTITY)
	private Integer id;
	
	private LocalDate fecha;
	private String comentarios;
	private String archivo; // El nombre del archivo PDF, DOCX del CV.

	@OneToOne
	@JoinColumn(name = "idVacante") // FK en la tabla de "solicitudes"
	private Vacante vacante;

	@OneToOne
	@JoinColumn(name = "idUsuario") // FK en la tabla de "usuarios"
	private Usuario usuario;
	/*-------------------------------------------------------------*/
	/* CONSTRUCTOR */
	
	// Constructor para inicializar la fecha con la fecha actual
	public Solicitud() {
		this.fecha = LocalDate.now();	// Para guardar la fecha del momento
	}//end constructor Solicitud()
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

	// Obtengo el valor del campo 'fecha'
	public LocalDate getFecha() {
		return fecha;
	}//end getFecha()

	// Establezco el valor del campo 'fecha'
	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}//end setFecha(LocalDate fecha)

	// Obtengo el valor del campo 'archivo'
	public String getArchivo() {
		return archivo;
	}//end getArchivo()

	// Establezco el valor del campo 'archivo'
	public void setArchivo(String archivo) {
		this.archivo = archivo;
	}//end setArchivo(String archivo)

	// Obtengo el valor del campo 'vacante'
	public Vacante getVacante() {
		return vacante;
	}//end getVacante()

	// Establezco el valor del campo 'vacante'
	public void setVacante(Vacante vacante) {
		this.vacante = vacante;
	}//end setVacante(Vacante vacante)

	// Obtengo el valor del campo 'usuario'
	public Usuario getUsuario() {
		return usuario;
	}//end getUsuario()

	// Establezco el valor del campo 'usuario'
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}//end setUsuario(Usuario usuario)

	// Obtengo el valor del campo 'comentarios'
	public String getComentarios() {
		return comentarios;
	}//end getComentarios()

	// Establezco el valor del campo 'comentarios'
	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}//end setComentarios(String comentarios)
	/*-------------------------------------------------------------*/
	/* toString() */
	
	// Sobrescribo el método toString para representar la clase como una cadena
	@Override
	public String toString() {
		return "Solicitud [id=" + id + ", fecha=" + fecha + ", comentarios=" + comentarios + ", archivo=" + archivo
				+ ", vacante=" + vacante + ", usuario=" + usuario + "]";
	}//end toString()
/*-------------------------------------------------------------*/
}//end class Solicitud
/*-------------------------------------------------------------*/
