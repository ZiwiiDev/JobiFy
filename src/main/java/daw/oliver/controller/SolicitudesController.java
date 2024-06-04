// Declaro el paquete al que pertenece esta clase
package daw.oliver.controller;
import java.text.SimpleDateFormat;
import java.util.Date;

/*-------------------------------------------------------------*/
// Importo las clases necesarias
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import daw.oliver.model.Solicitud;
import daw.oliver.model.Usuario;
import daw.oliver.model.Vacante;
import daw.oliver.service.ISolicitudesService;
import daw.oliver.service.IUsuariosService;
import daw.oliver.service.IVacantesService;
import daw.oliver.util.Utilidades;
/*-------------------------------------------------------------*/
//Anoto la clase como un controlador de "Spring"
@Controller
@RequestMapping("/solicitudes")	// Mapeo las solicitudes HTTP que comienzan con "/solicitudes" a este controlador
public class SolicitudesController {
	/*-------------------------------------------------------------*/
	// Inyecto el servicio de "Vacantes"
	@Autowired
	private IVacantesService serviceVacantes;
	
	// Inyecto el servicio de "Usuarios"
	@Autowired
	private IUsuariosService serviceUsuarios;
	
	// Inyecto el servicio de "Solicitudes"
	@Autowired
	private ISolicitudesService serviceSolicitudes;
	
	// Inyecto el valor desde el archivo de configuración "application.properties"
	@Value("${jobify.ruta.cv}")
	private String rutaCV;
	/*-------------------------------------------------------------*/
	// Método para mostrar el index paginado de solicitudes
	@GetMapping("/index")
	public String mostrarIndexPaginado(Model model, Pageable page) {
		// Obtengo todas las solicitudes paginadas (recuperarlas con la clase de servicio)
		Page<Solicitud> lista = serviceSolicitudes.buscarTodas(page);
			
		// Agrego la lista al modelo
		model.addAttribute("solicitudes", lista);
			
		// Devuelvo la vista que muestra la lista de solicitudes
		return "solicitudes/listSolicitudes";
	}//end mostrarIndexPaginado(Model model, Pageable page)

	// Método para mostrar el formulario de creación de solicitudes
	@GetMapping("/create/{idVacante}")
	public String crear(Solicitud solicitud, @PathVariable("idVacante") Integer idVacante, Model model) {
		Vacante vacante = serviceVacantes.buscarPorId(idVacante);	// Busco la vacante por ID
		
		// Hay que añadir la vacante al modelo para que podamos utilizarlo (ej: th:text="${vacante.nombre}")
		model.addAttribute("vacante", vacante);
		System.out.println("id Vacante: " + idVacante);
		
		// Devuelvo el formulario de solicitud
		return "solicitudes/formSolicitud";
	}//end crear(Solicitud solicitud, @PathVariable("idVacante") Integer idVacante, Model model)
	
	// Método para guardar una solicitud
	// Para revisar posibles errores agregar un parámetro de tipo "BindingResult"
	// "MultipartFile" lo usamos para poder pasarle archivos al HTML
	// "archivoCV" tiene que ser el mismo nombre que hay en "formSolicitud"
	@PostMapping("/save")
	public String guardar(Solicitud solicitud, BindingResult result, @RequestParam("archivoCV") MultipartFile multiPart, 
		Authentication authentication, RedirectAttributes atributos) {
		
		// Con authentication recuperamos el nombre del usuario autenticado
		String username = authentication.getName();
		
		// Verifico si hay errores en la validación. Si tiene algún error lo aviso
		if (result.hasErrors()) {
			// Recorro los errores y los imprimo en la consola (OPCIONAL PERO RECOMENDABLE)
			for (ObjectError error: result.getAllErrors()){
				// En la consola nos salen los errores
				System.out.println("Ocurrió un error: " + error.getDefaultMessage());
			}//end for
			
			// Vuelvo al formulario de solicitud para que el usuario pueda corregir los errores
			return "solicitudes/formSolicitud";
		}//end if
		
		// Si el archivo no está vacío, lo guardo
		if (!multiPart.isEmpty()) {
			String nombreArchivo = Utilidades.guardarArchivo(multiPart, rutaCV);
			
			// Si el archivo se ha subido correctamente
			if (nombreArchivo != null){
				// Establezco el nombre del archivo en la solicitud
				solicitud.setArchivo(nombreArchivo);
			}//end if
		}//end if
		
		// Crear relación del usuario con la solicitud
		Usuario usuario = serviceUsuarios.buscarPorUsername(username);
		solicitud.setUsuario(usuario);
		
		// Guardo la solicitud en la base de datos
		serviceSolicitudes.guardar(solicitud);
		
		// Agrego un mensaje de éxito como atributo flash
		atributos.addFlashAttribute("msg", "¡Gracias por enviar tu CV!");
		System.out.println("Solicitud: " + solicitud);
		
		// Redirijo a la página principal
		return "redirect:/";
	}//end guardar(Solicitud solicitud, BindingResult result, @RequestParam("archivoCV") MultipartFile multiPart, Authentication authentication, RedirectAttributes atributos)
	
	// Método para eliminar una solicitud de la base de datos por ID
	@GetMapping("/delete/{id}")
	public String eliminar(@PathVariable("id") int idSolicitud, RedirectAttributes atributos) {
		// Lo meto en un try-catch por si puede llegar a suceder algún error
		try {
			serviceSolicitudes.eliminar(idSolicitud);	// Elimino la solicitud
				
			// Añado un atributo flash para mostrar un mensaje de éxito
			atributos.addFlashAttribute("msg", "¡Solicitud eliminada!");
		}catch(Exception ex) {
			// Añado un atributo flash para mostrar un mensaje de error
			atributos.addFlashAttribute("msg", "¡No es posible eliminar la Solicitud seleccionada!");
		}//end catch
			
		// Redirijo a la página de index de solicitudes
		return "redirect:/solicitudes/index";
	}//end eliminar(@PathVariable("id") int idSolicitud, RedirectAttributes atributos)
	
	// Método para configurar el formato de fechas en el DataBinder
	// SIEMPRE que el controlador necesite un formato de fecha hay que utilizar este método
	@InitBinder		// Esta anotación permite inicializar el "WebDataBinder" que se utilizará para inicializar los formularios asociados al controlador
	public void initBinder(WebDataBinder webDataBinder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");	// Formato de fecha
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));	// Registrar el editor de fechas
	}//end initBinder(WebDataBinder webDataBinder)
	/*-------------------------------------------------------------*/
}//end class SolicitudesController
/*-------------------------------------------------------------*/
