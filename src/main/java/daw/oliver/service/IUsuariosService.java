package daw.oliver.service;
/*-------------------------------------------------------------*/
import java.util.List;

import daw.oliver.model.Usuario;
/*-------------------------------------------------------------*/
/* INTERFAZ de Usuarios */
public interface IUsuariosService {
	List<Usuario> buscarTodos();
	void guardar(Usuario usuario);
	void eliminar(Integer idUsuario);
	Usuario buscarPorUsername(String username);
}//end interface IUsuariosService
/*-------------------------------------------------------------*/
