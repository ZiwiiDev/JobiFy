package daw.oliver.service;
/*-------------------------------------------------------------*/
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import daw.oliver.model.Categoria;
/*-------------------------------------------------------------*/
/* INTERFAZ de Categorias */
public interface ICategoriasService {
	List<Categoria> buscarTodas();
	Categoria buscarPorId(Integer idCategoria);
	void guardar(Categoria categoria);
	void eliminar(Integer idCategoria);
	
	// Para integrar la paginación
	Page<Categoria> buscarTodas(Pageable page);
}//end interface ICategoriasService
/*-------------------------------------------------------------*/
