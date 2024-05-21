package daw.oliver.model;
/*-------------------------------------------------------------*/
//Importo las clases necesarias de Jakarta Persistence para la persistencia de datos
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
/*-------------------------------------------------------------*/
//Anoto la clase como una entidad JPA
@Entity
@Table(name="Usuarios")	// Mapeo esta clase a la tabla "Usuarios" en la base de datos
public class Usuario {
	/*-------------------------------------------------------------*/
	/* VARIABLES */
	
	@Id	// Indico que este campo es la clave primaria
	@GeneratedValue(strategy=GenerationType.IDENTITY)	// Utilizo la estrategia de generación de claves primarias AUTO_INCREMENT (IDENTITY)
	private Integer id;
	
	private String username;
	private String nombre;
	private String email;
	private String password;
	private Integer estatus;
	private Date fechaRegistro;
	
	// Relación muchos a muchos con la entidad Perfil
	// Cuando buscamos un usuario recuperamos todos los perfiles asociados a ese usuario
	@ManyToMany(fetch=FetchType.EAGER)		// Al guardar un usuario nuevo se agregan los perfiles que añadimos automáticamente
	@JoinTable(name="UsuarioPerfil", 							// El nombre de la tabla de unión tiene que ser igual que en la base de datos
		joinColumns = @JoinColumn(name="idUsuario"),			// El orden de la FK importa, primero "idUsuario" porque estamos en la clase de la tabla "Usuario"
		inverseJoinColumns = @JoinColumn(name="idPerfil"))		// // Columna de clave primaria de "Perfil" en la tabla de unión
	private List<Perfil> perfiles;
	/*-------------------------------------------------------------*/
	/* MÉTODOS */
	
	// Método para agregar un perfil a la lista de perfiles asociados
	public void agregar(Perfil tempPerfil) {
		// Verifico si la lista no está creada
		if (perfiles == null) {
			perfiles = new LinkedList<Perfil>();
		}//end if
		
		// Si no es nulo la lista está creada
		perfiles.add(tempPerfil);
	}//end agregar(Perfil tempPerfil)
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
	
	// Obtengo el valor del campo 'username'
	public String getUsername() {
		return username;
	}//end getUsername()
	
	// Establezco el valor del campo 'username'
	public void setUsername(String username) {
		this.username = username;
	}//end setUsername(String username)
	
	// Obtengo el valor del campo 'nombre'
	public String getNombre() {
		return nombre;
	}//end getNombre()
	
	// Establezco el valor del campo 'nombre'
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}//end setNombre(String nombre)
	
	// Obtengo el valor del campo 'email'
	public String getEmail() {
		return email;
	}//end getEmail()
	
	// Establezco el valor del campo 'email'
	public void setEmail(String email) {
		this.email = email;
	}//end setEmail(String email)
	
	// Obtengo el valor del campo 'password'
	public String getPassword() {
		return password;
	}//end getPassword()
	
	// Establezco el valor del campo 'password'
	public void setPassword(String password) {
		this.password = password;
	}//end setPassword(String password)
	
	// Obtengo el valor del campo 'estatus'
	public Integer getEstatus() {
		return estatus;
	}//end getEstatus()
	
	// Establezco el valor del campo 'estatus'
	public void setEstatus(Integer estatus) {
		this.estatus = estatus;
	}//end setEstatus(Integer estatus)
	
	// Obtengo el valor del campo 'fechaRegistro'
	public Date getFechaRegistro() {
		return fechaRegistro;
	}//end getFechaRegistro()
	
	// Establezco el valor del campo 'fechaRegistro'
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}//end setFechaRegistro(Date fechaRegistro)
	
	// Obtengo el valor de la lista de perfiles
	public List<Perfil> getPerfiles() {
		return perfiles;
	}//end getPerfiles()
	
	// Establezco el valor de la lista de perfiles
	public void setPerfiles(List<Perfil> perfiles) {
		this.perfiles = perfiles;
	}//end setPerfiles(List<Perfil> perfiles)
	/*-------------------------------------------------------------*/
	/* toString() */
	
	// Sobrescribo el método toString para representar la clase como una cadena
	@Override
	public String toString() {
		return "Usuario [id=" + id + ", username=" + username + ", nombre=" + nombre + ", email=" + email
				+ ", password=" + password + ", estatus=" + estatus + ", fechaRegistro=" + fechaRegistro;
	}//end toString()
	/*-------------------------------------------------------------*/
}//end class Usuario
/*-------------------------------------------------------------*/
