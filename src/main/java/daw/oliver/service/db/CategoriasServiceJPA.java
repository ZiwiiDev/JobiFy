package daw.oliver.service.db;
/*-------------------------------------------------------------*/
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import daw.oliver.model.Categoria;
import daw.oliver.repository.CategoriasRepository;
import daw.oliver.service.ICategoriasService;
/*-------------------------------------------------------------*/
/*
 * Se utiliza "@Primary" o "@Qualifier", no los 2 a la vez ("@Primary" es más simple porque sólo es en la clase que se use)
 * */

@Service
@Primary			// Si ponemos "@Primary" nos saldrán las categorías de la base de datos, y no de la lista
public class CategoriasServiceJPA implements ICategoriasService {
	/*-------------------------------------------------------------*/
	/* VARIABLES */
	
	@Autowired
	private CategoriasRepository categoriasRepo;
	/*-------------------------------------------------------------*/
	/* MÉTODOS */
	
	// Guardamos en la base de datos una nueva categoría
	@Override
	public void guardar(Categoria categoria) {
		// Con "save" se puede hacer tanto un insert como un update, se diferencia por el valor ID, si existe lo actualiza y si no existe se crea
		categoriasRepo.save(categoria);
	}//end guardar(Categoria categoria)

	@Override
	public List<Categoria> buscarTodas() {
		return categoriasRepo.findAll();
	}//end buscarTodas()

	@Override
	public Categoria buscarPorId(Integer idCategoria) {
		Optional<Categoria> opcional = categoriasRepo.findById(idCategoria);
		
		// Si devuelve true es que ha encontrado el ID en la base de datos
		if (opcional.isPresent()) {
			// El método get() nos devuelve el objeto categoría
			return opcional.get();
		}//end if
		
		// Si no encuentra el ID no se ejecuta el if, por lo tanto devuelve null, ya que no hay registro con ese ID
		return null;
	}//end buscarPorId(Integer idCategoria)

	// Método que elimina una categoría
	@Override
	public void eliminar(Integer idCategoria) {
		categoriasRepo.deleteById(idCategoria);
	}//end eliminar(Integer idCategoria)

	@Override
	public Page<Categoria> buscarTodas(Pageable page) {		
		return categoriasRepo.findAll(page);
	}//end buscarTodas(Pageable page)
	/*-------------------------------------------------------------*/
}//end class CategoriasServiceJPA
/*-------------------------------------------------------------*/
