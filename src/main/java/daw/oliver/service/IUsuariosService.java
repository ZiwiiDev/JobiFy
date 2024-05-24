package daw.oliver.service;
/*-------------------------------------------------------------*/
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import daw.oliver.model.Usuario;
/*-------------------------------------------------------------*/
/* INTERFAZ de Usuarios */
public interface IUsuariosService {
	List<Usuario> buscarTodos();
	void guardar(Usuario usuario);
	void eliminar(Integer idUsuario);
	Usuario buscarPorUsername(String username);
	
	// Para integrar la paginación
	Page<Usuario> buscarTodos(Pageable page);
}//end interface IUsuariosService
/*-------------------------------------------------------------*/
