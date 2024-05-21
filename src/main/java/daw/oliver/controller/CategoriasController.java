// Declaro el paquete al que pertenece esta clase
package daw.oliver.controller;
/*-------------------------------------------------------------*/
// Importo las clases y anotaciones necesarias de "Spring"
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
//import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import daw.oliver.model.Categoria;
import daw.oliver.service.ICategoriasService;
/*-------------------------------------------------------------*/
//Anoto la clase con "@Controller" y establezco la ruta base "/categorias"
@Controller
@RequestMapping("/categorias")			// Anotación a nivel de clase (localhost:8080/categorias/...)
public class CategoriasController {
	/*-------------------------------------------------------------*/
	// Inyecto la clase del servicio de categorías
    @Autowired
    //@Qualifier("categoriasServiceJPA") 	// Decirle el nombre de la implementación que queremos usar a la hora de hacer la implementación de la interfaz. Normalente es el nombre del servicio pero con la primera en minúscula.
    private ICategoriasService serviceCategorias;

	/*	LO MISMO QUE EL MÉTODO DE ABAJO PERO SIN PAGINACIÓN
	// @GetMapping("/index")			<-- Lo mismo que el "@RequestMapping(value="/index", method=RequestMethod.GET)"
	@RequestMapping(value="/index", method=RequestMethod.GET)
	public String mostrarIndex(Model model) {
		List<Categoria> lista = serviceCategorias.buscarTodas();
		model.addAttribute("categorias", lista);
		return "categorias/listCategorias";
	}
	*/
    /*-------------------------------------------------------------*/
	// Defino el método para mostrar el index paginado de categorías
	@GetMapping(value = "/index")
	public String mostrarIndexPaginado(Model model, Pageable page) {
		 Page<Categoria> lista = serviceCategorias.buscarTodas(page);
		 model.addAttribute("categorias", lista);
		 return "categorias/listCategorias";
	}//end mostrarIndexPaginado(Model model, Pageable page)
	
	// Defino el método para mostrar el formulario de creación de categorías
	// @GetMapping("/create")			<-- Lo mismo que el "@RequestMapping(value="/create", method=RequestMethod.GET)"
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public String crear(Categoria categoria) {
		return "categorias/formCategoria";
	}//end crear(Categoria categoria)
	
	// Defino el método para guardar una categoría
	// @PostMapping("/save")			<-- Lo mismo que el "@RequestMapping(value="/save", method=RequestMethod.POST)"
	@RequestMapping(value="/save", method=RequestMethod.POST)
	public String guardar(Categoria categoria, BindingResult result, RedirectAttributes atributos) {
		// Verifico si hay errores en la validación. Si tiene algún error lo aviso
		if (result.hasErrors()) {
			// Recorro los errores y los imprimo en la consola (OPCIONAL PERO RECOMENDABLE)
			for (ObjectError error: result.getAllErrors()){
				// En la consola nos salen los errores
				System.out.println("¡Ocurrió un ERROR!: " + error.getDefaultMessage());
			}//end for
			
			return "categorias/formCategoria";
		}//end if
		
		// Cuando el objeto categoría llegue al controlador se agregará a la lista
		// Guardo la categoría usando el servicio de categorías
		serviceCategorias.guardar(categoria);
		
		// Añado un atributo flash para mostrar un mensaje de éxito
		// Los atributos flash proporcionan una forma de almacenar atributos para poder ser usados en otra petición diferente
		// Son almacenados temporalmente antes de hacer el "redirect" para tenerlos disponibles después del "redirect". Despúes del "redirect" se eliminan
		atributos.addFlashAttribute("msg", "¡Los datos de la categoría fueron guardados con éxito!");
		
		System.out.println("Categoría: " + categoria);
		
		// Redirecciono a la página de index de categorías
		return "redirect:/categorias/index";
	}//end guardar(Categoria categoria, BindingResult result, RedirectAttributes atributos)
	
	// Defino el método para eliminar una categoría
	@GetMapping("/delete/{id}")
	public String eliminar(@PathVariable("id") int idCategoria, RedirectAttributes atributos) {
		// Uso un bloque try-catch para manejar posibles excepciones
		// Lo meto en un try-catch porque "idCategoria" es una FK de la tabla "Vacantes" y hay algunas categorías que no se pueden eliminar
		try {
			System.out.println("Borrando vacante con id: "+ idCategoria);
			
			// Elimino la categoría usando el servicio.
			serviceCategorias.eliminar(idCategoria);
			
			// Añado un atributo flash para mostrar un mensaje de éxito
			atributos.addFlashAttribute("msg", "¡Categoría eliminada!");
		}catch(Exception ex) {
			// Añado un atributo flash para mostrar un mensaje de error
			atributos.addFlashAttribute("msg", "¡No es posible eliminar la Categoría seleccionada!");
		}//end catch(Exception ex)
		
		// Redirecciono a la página de index de categorías
		return "redirect:/categorias/index";
	}//end eliminar(@PathVariable("id") int idCategoria, RedirectAttributes atributos)
	
	// Defino el método para editar una categoría
	@GetMapping("/edit/{id}")
	public String editar(@PathVariable("id") int idCategoria, Model model) {
		Categoria categoria = serviceCategorias.buscarPorId(idCategoria);
		model.addAttribute("categoria", categoria);
		
		return "categorias/formCategoria";
	}//end editar(@PathVariable("id") int idCategoria, Model model)
	
	// Uso la anotación "@ModelAttribute" para añadir atributos comunes al modelo
	// Con la anotación a nivel de método podemos agregar al model todos los atributos que queramos y estarán disponibles para todos los método de "VacantesController"
	@ModelAttribute
	public void setGenericos(Model model) {
		model.addAttribute("categorias", serviceCategorias.buscarTodas());
	}//end setGenericos(Model model)
	/*-------------------------------------------------------------*/
}//end class CategoriasController
/*-------------------------------------------------------------*/
