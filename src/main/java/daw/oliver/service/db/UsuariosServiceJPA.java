package daw.oliver.service.db;
/*-------------------------------------------------------------*/
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import daw.oliver.model.Usuario;
import daw.oliver.repository.UsuariosRepository;
import daw.oliver.service.IUsuariosService;
/*-------------------------------------------------------------*/
/*
 * Se utiliza "@Primary" o "@Qualifier", no los 2 a la vez ("@Primary" es más simple porque sólo es en la clase que se use)
 * */

@Service
@Primary			// Si ponemos "@Primary" nos saldrán las categorías de la base de datos, y no de la lista
public class UsuariosServiceJPA implements IUsuariosService{
	/*-------------------------------------------------------------*/
	/* VARIABLES */
	
	@Autowired
	private UsuariosRepository usuarioRepo;
	/*-------------------------------------------------------------*/
	/* MÉTODOS */
	
	@Override
	public List<Usuario> buscarTodos() {
		return usuarioRepo.findAll();
	}//end buscarTodos()

	@Override
	public void guardar(Usuario usuario) {
		// Con save se puede hacer tanto un insert como un update, se diferencia por el valor id, si existe lo actualiza y si no existe se crea
		usuarioRepo.save(usuario);
	}//end guardar(Usuario usuario)

	// Elimino un usuario de la base de datos
	@Override
	public void eliminar(Integer idUsuario) {
		usuarioRepo.deleteById(idUsuario);
	}//end eliminar(Integer idUsuario)

	@Override
	public Usuario buscarPorUsername(String username) {
		return usuarioRepo.findByUsername(username);
	}//end buscarPorUsername(String username)
	/*-------------------------------------------------------------*/
}//end class UsuariosServiceJPA
/*-------------------------------------------------------------*/
