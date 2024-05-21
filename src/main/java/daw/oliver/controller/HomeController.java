// Declaro el paquete al que pertenece esta clase
package daw.oliver.controller;
/*-------------------------------------------------------------*/
import java.util.Date;
//import java.util.LinkedList;
import java.util.List;

// Importo las clases y anotaciones necesarias de "Spring" y otros paquetes
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.core.Authentication;	// Debe ser este import y no otro
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import daw.oliver.model.Perfil;
import daw.oliver.model.Usuario;
import daw.oliver.model.Vacante;
import daw.oliver.service.ICategoriasService;
import daw.oliver.service.IUsuariosService;
import daw.oliver.service.IVacantesService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;		// Debe ser este import y no otro
/*-------------------------------------------------------------*/
// Anoto la clase con "@Controller" para indicar que es un controlador de "Spring"
// SI PONGO "CONTROLLER" PUEDO DEVOLVER UN HTML, UN "RESTCONTROLLER" NO. LOS RESTCONTROLLERS SON PARA LAS "RESTFULL API"
@Controller
public class HomeController {
	/*-------------------------------------------------------------*/
	// Inyecto los servicios necesarios
	// Creamos una variable con el tipo de la interfaz
	// @Autowired permite resolver la inyección de dependencias
	@Autowired
	private IVacantesService serviceVacantes;
	
	@Autowired
   	private IUsuariosService serviceUsuarios;
	
	@Autowired
	private ICategoriasService serviceCategorias;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	/*-------------------------------------------------------------*/
	// Defino el método para mostrar la página principal
	@GetMapping("/")
	public String mostrarHome() {
		return "home";
	}//end mostrarHome()
	
	// Defino el método para mostrar el index
	@GetMapping("/index")
	public String mostrarIndex(Authentication auth, HttpSession session) {
		// Obtengo el nombre del usuario que ha iniciado sesión
		String username = auth.getName();
		System.out.println("Nombre del usuario: " + username);
		
		// Obtengo y muestro los roles que tiene el usuario
		for (GrantedAuthority rol : auth.getAuthorities()) {
			System.out.println("Rol: " + rol.getAuthority());
		}//end for
		
		// Agrego atributos a la sesión del usuario si no existen
		if (session.getAttribute("usuario") == null) {
			// Si el atributo no existe lo creo
			Usuario usuario = serviceUsuarios.buscarPorUsername(username);
			
			// Por seguridad para que no se almacene en la sesión la contraseña
			usuario.setPassword(null);
			
			System.out.println("Usuario: " + usuario);
			session.setAttribute("usuario", usuario);		// Como valor se pasa un objeto
		}//end if
		
		return "redirect:/";
	}//end mostrarIndex(Authentication auth, HttpSession session)
	
	// Defino el método para mostrar el formulario de registro y permitir al usuario registrarse
	@GetMapping("/signup")
	public String registrarse(Usuario usuario) {
		return "formRegistro";
	}//end registrarse(Usuario usuario)
	
	 // Defino el método para guardar un nuevo usuario registrado
	@PostMapping("/signup")
	public String guardarRegistro(Usuario usuario, BindingResult result, RedirectAttributes atributos) {
		// Encripto la contraseña antes de guardarla
		String pwdPlano = usuario.getPassword();					// Contraseña sin encriptar
		String pwdEncriptado = passwordEncoder.encode(pwdPlano);	// Contraseña encriptada
		usuario.setPassword(pwdEncriptado);							// Guardo la contraseña encriptada y la asocio a ese usuario
		
		// Verifico si hay errores en la validación
		if (result.hasErrors()) {
			// Recorro los errores y los imprimo en la consola (OPCIONAL PERO RECOMENDABLE)
			for (ObjectError error: result.getAllErrors()){
				// En la consola nos salen los errores
				System.out.println("Ocurrió un ERROR: " + error.getDefaultMessage());
			}//end for
				
			return "formRegistro";
		}//end if
		
		// Creo el perfil que le asigno al usuario nuevo
		Perfil perfilUsuario = new Perfil();
		perfilUsuario.setId(3);				  // Perfil "USUARIO" de la base de datos
		usuario.setEstatus(1); 				  // "Activado" por defecto
		usuario.setFechaRegistro(new Date()); // Fecha de Registro, la fecha actual
		
		// Agrego el perfil al usuario
		usuario.agregar(perfilUsuario);
		
		// Guardo el usuario en la base de datos. El Perfil se guarda automáticamente.
		serviceUsuarios.guardar(usuario);
		
		// Añado un atributo flash para mostrar un mensaje de éxito
		// Los atributos flash proporcionan una forma de almacenar atributos para poder ser usados en otra petición diferente
		// Son almacenados temporalmente antes de hacer el redirect para tenerlos disponibles después del redirect. Despúes del Redirect se eliminan.
		atributos.addFlashAttribute("msg", "¡Usuario Creado!");
		
		// "Redirect" hace un rediccionamiento a la url de index para que se actualicen los cambios
		return "redirect:/usuarios/index";
	}//end guardarRegistro(Usuario usuario, BindingResult result, RedirectAttributes atributos)
	
	// Defino el método para mostrar el formulario de login
	@GetMapping("/login" )
	public String mostrarLogin() {
		return "formLogin";
	}//end mostrarLogin()
	
	// Método para cerrar sesión y que no pete al personalizar el formulario de login
	@GetMapping("/logout")
	public String logout(HttpServletRequest request){
		// Tipo de variable necesaria para cerrar la sesión
		SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
		
		// El método logout() sirve para cerrar la sesión
		logoutHandler.logout(request, null, null);
		
		return "redirect:/login";
	}//end logout(HttpServletRequest request)
	
	// Defino el método para buscar ofertas de trabajo mediante filtración por descripción, categoría o ambas
	@GetMapping("/searchOfertas")
	public String buscar(@ModelAttribute("searchOfertas") Vacante vacante, Model model) {
		System.out.println("Buscando por " + vacante);
		
		// PARA QUE NO SE PONGA LA DESCRIPCIÓN EXACTA, QUE CON PONER "servidores linux" BUSQUE LAS QUE CONTENGAN ESO EN LA DESCRIPCIÓN
		ExampleMatcher coincideDescripcion = ExampleMatcher.
				// EN LA CONSULTA SELECT SE EJECUTA: WHERE DESCRIPCION LIKE '%?%'
				matching().withMatcher("descripcion", ExampleMatcher.GenericPropertyMatchers.contains());
		
		// Encuentra todas las vacantes, pero sólo mediante la búsqueda de las variables que no sean null
		Example<Vacante> example = Example.of(vacante, coincideDescripcion);
		
		// Meto la variable example en una lista
		List<Vacante> lista = serviceVacantes.buscarByExample(example);
		
		// Guardo la lista en un modelo
		model.addAttribute("vacantes", lista);
		
		return "home";
	}//end buscar(@ModelAttribute("searchOfertas") Vacante vacante, Model model)
	
	// Defino el método "initBinder" para convertir strings vacíos a null
	// Para hacer el select de las vacantes de la página principal
	@InitBinder
	public void initBinder (WebDataBinder binder) {
		// Para los String busca un modificador que si es true los cambia a null
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}//end initBinder (WebDataBinder binder)
	
	// Defino el método "setGenericos" para agregar atributos comunes al modelo
	// Con la anotación a nivel de método podemos agregar al model todos los atributos que queramos y estarán disponibles para todos los método del "HomeController"
	@ModelAttribute
	public void setGenericos(Model model) {
		Vacante vacanteSearch = new Vacante();
		vacanteSearch.resetImagen();
		
		// Añado al modelo un objeto de tipo vacante para poder buscar las posibles vacantes que existan de la categoría seleccionada
		model.addAttribute("searchOfertas", vacanteSearch);
		
		// Para añadir todas las ofertas de trabajo de la base de datos
		model.addAttribute("vacantes", serviceVacantes.buscarDestacadas());
		
		// Para añadir todas las categorías de la base de datos
		model.addAttribute("categorias", serviceCategorias.buscarTodas());
	}//end setGenericos(Model model)
	/*-------------------------------------------------------------*/
	
	/* - PRUEBAS -
	---------------------------------------------------------------------------------------------------
	// Defino el método para encriptar un texto usando Bcrypt
	// Al entrar a esta url nos sale cuál sería la contraseña encriptada de cualquier texto que pongamos
	// PRUEBA: http://localhost:8080/bcrypt/prueba
	@GetMapping("/bcrypt/{texto}")
	@ResponseBody		// Esta anotación lo que hace es que cuando haces una petición a este método devuelve el texto del return a la url del navegador
	public String encriptar(@PathVariable("texto") String texto) {
		// Encripto las contraseñas que se han guardado sin encriptar antes de meter el método para encriptarlas
		// Para que los que se han registrado antes de encriptar las contraseñas no pierdan su cuenta
		return texto + " Encriptado en Bcrypt: " + passwordEncoder.encode(texto);
	}//end encriptar(@PathVariable("texto") String texto)
	---------------------------------------------------------------------------------------------------
	// Defino el método para mostrar una tabla de vacantes
	@GetMapping("/tabla")
	public String mostrarTabla(Model model) {
		List<Vacante> lista = serviceVacantes.buscarTodas();
		model.addAttribute("vacantes", lista);
		
		return "tabla";
	}//end mostrarTabla(Model model)
	---------------------------------------------------------------------------------------------------
	// Defino el método para mostrar el detalle de una vacante
	@GetMapping("/detalle")
	public String mostrarDetalle(Model model) {
		Vacante vacante = new Vacante();
		
		vacante.setNombre("Ingeniero de comunicaciones");
		vacante.setDescripcion("Se solicita ingeniero para dar soporte a intranet");
		vacante.setFecha(new Date());
		vacante.setSalario(9700.0);
		model.addAttribute("vacante", vacante);
		
		return "detalle";
	}//end mostrarDetalle(Model model)
	---------------------------------------------------------------------------------------------------
	@GetMapping("/listado")
	public String mostrarListado(Model model) {
		List<String> lista = new LinkedList<String>();
		lista.add("Ingeniero de Sistemas");
		lista.add("Auxiliar de Contabilidad");
		lista.add("Vendedor");
		lista.add("Arquitecto");
		
		model.addAttribute("empleos", lista);
		
		return "listado";
	}//end mostrarListado(Model model)
	---------------------------------------------------------------------------------------------------
	*/
}//end class HomeController
/*-------------------------------------------------------------*/
