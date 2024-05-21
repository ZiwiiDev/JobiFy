// Declaro el paquete al que pertenece esta clase
package daw.oliver.controller;
/*-------------------------------------------------------------*/
import java.util.List;

//Importo las clases y anotaciones necesarias de "Spring"
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import daw.oliver.model.Usuario;
import daw.oliver.service.IUsuariosService;
/*-------------------------------------------------------------*/
//Anoto la clase como un controlador de "Spring"
@Controller
@RequestMapping("/usuarios")	// Mapeo las solicitudes HTTP que comienzan con "/usuarios" a este controlador
public class UsuariosController {
	/*-------------------------------------------------------------*/
	// Inyecto el servicio de "usuarios" en tiempo de ejecución
	@Autowired		// El autowired hace que en tiempo de ejecución de esta variable se use una instancia de la clase que implemente este tipo
	private IUsuariosService serviceUsuarios;
	/*-------------------------------------------------------------*/
	// Método para mostrar el index de "usuarios"
    @GetMapping("/index")
	public String mostrarIndex(Model model) {
    	// Obtener todos los usuarios (recuperarlos con la clase de servicio)
    	List<Usuario> lista = serviceUsuarios.buscarTodos();
    	
    	// Agregar la lista de usuarios al modelo
    	model.addAttribute("usuarios", lista);
    	
    	// Devuelvo la vista que muestra la lista de usuarios
    	return "usuarios/listUsuarios";
	}//end mostrarIndex(Model model)
    
    // Método para eliminar un usuario de la base de datos por ID
    @GetMapping("/delete/{id}")
	public String eliminar(@PathVariable("id") int idUsuario, RedirectAttributes atributos) {		    	
    	// Lo meto en un try-catch por si puede llegar a suceder algún error
    	try {
    		serviceUsuarios.eliminar(idUsuario);	// Elimino el usuario por ID
    		
    		// Agrego un mensaje de éxito como atributo flash
    		atributos.addFlashAttribute("msg", "¡Usuario Eliminado!");
    	} catch(Exception ex) {
    		// Agrego un mensaje de error como atributo flash
    		atributos.addFlashAttribute("msg", "¡No es posible eliminar el Usuario seleccionada!");
    	}//end catch

    	// Redirijo a la página de index de usuarios
		return "redirect:/usuarios/index";
	}//end eliminar(@PathVariable("id") int idUsuario, RedirectAttributes atributos)
    /*-------------------------------------------------------------*/
}//end class UsuariosController
/*-------------------------------------------------------------*/
