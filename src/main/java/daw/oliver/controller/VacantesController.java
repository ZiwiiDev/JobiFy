// Declaro el paquete al que pertenece esta clase
package daw.oliver.controller;
/*-------------------------------------------------------------*/
// Importación de clases necesarias
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import daw.oliver.model.Vacante;
import daw.oliver.service.ICategoriasService;
import daw.oliver.service.IVacantesService;
import daw.oliver.util.Utilidades;
/*-------------------------------------------------------------*/
// Anotación para indicar que esta clase es un controlador de "Spring"
@Controller
@RequestMapping("/vacantes")	// Mapeo las solicitudes "HTTP" que comienzan con "/vacantes" a este controlador
public class VacantesController {
	/*-------------------------------------------------------------*/
	// Para inyectar el valor de application.properties (jobify.ruta.imagenes) utilizado "@Value"
	@Value("${jobify.ruta.imagenes}")
	private String ruta;
	
	// Inyección de dependencias para el servicio de "Vacantes"
	// Con Autowired le digo que quiero utilizar este servicio (en este caso interfaz) y consume menos
	@Autowired
	private IVacantesService serviceVacantes;
	
	// Inyección de dependencias para el servicio de "Categorías"
	@Autowired
	//@Qualifier("categoriasServiceJPA") 	// Decirle el nombre de la implementación que queremos usar a la hora de hacer la implementación de la interfaz. Normalente es el nombre del servicio pero con la primera en minúscula
	private ICategoriasService serviceCategorias;
	/*-------------------------------------------------------------*/
	/*	LO MISMO QUE EL MÉTODO DE ABAJO PERO SIN PAGINACIÓN
	@GetMapping("/index")
	public String mostrarIndex(Model model) {
		// Obtener todas las vacantes (recuperarlas con la clase de servicio)
		List<Vacante> lista = serviceVacantes.buscarTodas();
		
		// Agregar la lista al modelo
		model.addAttribute("vacantes", lista);
		
		// Renderizar las vacantes en la vista (devolver la vista de listVacantes)
		return "vacantes/listVacantes";
	}
	*/
	
	// Método para mostrar el index paginado de vacantes
	@GetMapping("/index")
	public String mostrarIndexPaginado(Model model, Pageable page) {
		// Obtengo todas las vacantes paginadas (recuperarlas con la clase de servicio)
		Page<Vacante> lista = serviceVacantes.buscarTodas(page);
		
		// Agrego la lista de vacantes al modelo
		model.addAttribute("vacantes", lista);
		
		// Renderizo las vacantes en la vista (devuelvo la vista de vacantes)
		return "vacantes/listVacantes";
	}//end mostrarIndexPaginado(Model model, Pageable page)
	
	// Método para mostrar el formulario de creación de vacantes
	@GetMapping("/create")
	public String crear(Vacante vacante, BindingResult result) {
		return "vacantes/formVacante";
	}//end crear(Vacante vacante, BindingResult result)
	
	// Método para guardar una vacante
	// CUANDO ES UNA "RESTFUL API" Y GUARDAMOS UN REGISTRO EN LA BASE DE DATOS SE GUARDA COMO TIPO "POST"
	// Para revisar posibles errores después del "Data Binding" debemos agregar un parámetro de tipo "BindingResult"
	@PostMapping("/save")
	public String guardar(Vacante vacante, BindingResult result, RedirectAttributes atributos, @RequestParam("archivoImagen") MultipartFile multiPart) {
		// Validación de errores en el formulario
		if (result.hasErrors()) {
			// Recorro los errores y los imprimo en la consola (OPCIONAL PERO RECOMENDABLE)
			for (ObjectError error: result.getAllErrors()){
				// En la consola nos salen los errores
				System.out.println("Ocurrió un error: " + error.getDefaultMessage());
			}//end for
			
			// Si hay errores, devolver al formulario
			return "vacantes/formVacante";
		}//end if
		
		// Si se ha subido un archivo
		if (!multiPart.isEmpty()) {
			// Guardo el archivo en la ruta especificada
			//String ruta = "/empleos/img-vacantes/"; // Linux/MAC
			//String ruta = "C:/empleos/img-vacantes/"; // Windows			<-- La tenemos en "application.properties"
			String nombreImagen = Utilidades.guardarArchivo(multiPart, ruta);
			
			// Si la imagen se ha subido correctamente
			if (nombreImagen != null){
				// Establezco la imagen en el objeto vacante
				vacante.setImagen(nombreImagen);
			}//end if
		}//end if
		
		// Cuando el objeto vacante llegue al controlador se agregará en la base de datos
		serviceVacantes.guardar(vacante);
		
		// Agrego un mensaje de éxito como atributo flash
		atributos.addFlashAttribute("msg", "¡Datos guardados!");
		
		// No necesitamos un modelo porque ya tenemos atributos flash
		//model.addAttribute("msg", "¡Registro Guardado!");
		
		// "Redirect" hace un rediccionamiento a la url de index de vacantes para que se actualicen los cambios
		return "redirect:/vacantes/index";
	}
	
	/* 	-- REFERENCIA PRUEBA (Lo mismo que el método de arriba)
	@PostMapping("/save")
	public String guardar(@RequestParam("nombre") String nombre, @RequestParam("descripcion") String descripcion, @RequestParam("estatus") String estatus,
			@RequestParam("fecha") String fecha, @RequestParam("destacado") int destacado, @RequestParam("salario") double salario, 
			@RequestParam("detalles") String detalles) {
		
		System.out.println("Nombre: " + nombre);
		System.out.println("Descripción: " + descripcion);
		System.out.println("Estatus: " + estatus);
		System.out.println("Fecha Publicación: " + fecha);
		System.out.println("Destacado: " + destacado);
		System.out.println("Salario: " + salario);
		System.out.println("Detalles: " + detalles);
		
		return "vacantes/listVacantes";
	}
	*/
	
	// Método para eliminar una vacante de la base de datos por ID
	// "@PathVariable" es para crear URL dinámicas, en este caso buscando por ID. Para vincular un parámetro de una URL dinámica a una variable en el controlador.
	@GetMapping("/delete/{id}")
	public String eliminar(@PathVariable("id") int idVacante, RedirectAttributes atributos) {
		// Lo meto en un try-catch por si puede llegar a suceder algún error
		try {
			serviceVacantes.eliminar(idVacante);	// Elimino la vacante por ID
			
			atributos.addFlashAttribute("msg", "¡Vacante eliminada!");	// Agregar mensaje de éxito
		}catch(Exception ex) {
			atributos.addFlashAttribute("msg", "¡No es posible eliminar la Vacante seleccionada!");	// Agregar mensaje de error
		}//end catch
		
		// Redirigir a la página de index de vacantes
		return "redirect:/vacantes/index";
	}//end eliminar(@PathVariable("id") int idVacante, RedirectAttributes atributos)
	
	// Lo mismo que el método de arriba pero peor (ESTE NO ES DINÁMICO POR ID)
	/*
	@GetMapping("/delete")			// Para RequestParam hay que utilizar la ? y el parámetro (Ej: localhost:8080/vacantes/delete?id=3)
	public String eliminar(@RequestParam("id") int idVacante, Model model) {
		System.out.println("Borrando vacante con id: "+ idVacante);
		model.addAttribute("id", idVacante);
		return "mensaje";
	}
	*/
	
	// Método para editar una vacante
	@GetMapping("/edit/{id}")
	public String editar(@PathVariable("id") int idVacante, Model model) {
		Vacante vacante = serviceVacantes.buscarPorId(idVacante);	// Busco la vacante por ID
		model.addAttribute("vacante", vacante);	// Agrego la vacante al modelo
		
		// Devuelvo el formulario de vacante
		return "vacantes/formVacante";
	}//end editar(@PathVariable("id") int idVacante, Model model)

	// Método para ver el detalle de una vacante
	@GetMapping("/view/{id}")
	public String verDetalle(@PathVariable("id") int idVacante, Model model) {
		Vacante vacante = serviceVacantes.buscarPorId(idVacante);	// Busco la vacante por ID
		
		System.out.println("Vacante: "+ vacante);
		model.addAttribute("vacante", vacante);	// Agrego la vacante al modelo
		
		// Devuelvo la vista de detalle
		return "/detalle";
	}//end verDetalle(@PathVariable("id") int idVacante, Model model)
	
	// Método para establecer atributos comunes en el modelo
	// Con la anotación a nivel de método podemos agregar al model todos los atributos que queramos y estarán disponibles para todos los método del "VacantesController"
	@ModelAttribute
	public void setGenericos(Model model) {
		// Agrego todas las categorías al modelo
		model.addAttribute("categorias", serviceCategorias.buscarTodas());
	}//end setGenericos(Model model)
	
	// Método para configurar el formato de fechas en el DataBinder
	// SIEMPRE que el controlador necesite un formato de fecha hay que utilizar este método
	@InitBinder		// Esta anotación permite inicializar el "WebDataBinder" que se utilizará para inicializar los formularios asociados al controlador
	public void initBinder(WebDataBinder webDataBinder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");	// Formato de fecha
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));	// Registrar el editor de fechas
	}//end initBinder(WebDataBinder webDataBinder)
	/*-------------------------------------------------------------*/
}//end class VacantesController
/*-------------------------------------------------------------*/
