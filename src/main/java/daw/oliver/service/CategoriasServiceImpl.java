package daw.oliver.service;
/*-------------------------------------------------------------*/
import java.util.LinkedList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import daw.oliver.model.Categoria;
/*-------------------------------------------------------------*/
@Service					
//@Primary 			<-- Si ponemos "@Primary" nos saldrán las categorías de la lista, y no de la base de datos
public class CategoriasServiceImpl implements ICategoriasService {
	/*-------------------------------------------------------------*/
	/* VARIABLES */
	
	private List<Categoria> lista = null;
	/*-------------------------------------------------------------*/
	/* CONSTRUCTOR */
	
	public CategoriasServiceImpl() {
		lista = new LinkedList<Categoria>();
		
		Categoria cat1 = new Categoria();
		Categoria cat2 = new Categoria();
		Categoria cat3 = new Categoria();
		Categoria cat4 = new Categoria();
		Categoria cat5 = new Categoria();
		Categoria cat6 = new Categoria();
		
		cat1.setId(1);
		cat1.setNombre("Contabilidad");
		cat1.setDescripcion("Descripcion de la categoria Contabilidad");
		
		cat2.setId(2);
		cat2.setNombre("Ventas");
		cat2.setDescripcion("Trabajos relacionados con Ventas");
		
		cat3.setId(3);
		cat3.setNombre("Comunicaciones");
		cat3.setDescripcion("Trabajos relacionados con Comunicaciones");
		
		cat4.setId(4);
		cat4.setNombre("Arquitectura");
		cat4.setDescripcion("Trabajos de Arquitectura");
		
		cat5.setId(5);
		cat5.setNombre("Educacion");
		cat5.setDescripcion("Maestros, tutores, etc");
		
		cat6.setId(6);
		cat6.setNombre("Desarrollo de software");
		cat6.setDescripcion("Trabajo para programadores");
		
		lista.add(cat1);			
		lista.add(cat2);
		lista.add(cat3);
		lista.add(cat4);
		lista.add(cat5);
		lista.add(cat6);
	}//end constructor CategoriasServiceImpl()
	/*-------------------------------------------------------------*/
	/* MÉTODOS */
	
	@Override
	public void guardar(Categoria categoria) {
		lista.add(categoria);
	}//end guardar(Categoria categoria)

	@Override
	public List<Categoria> buscarTodas() {
		return lista;
	}//end buscarTodas()

	@Override
	public Categoria buscarPorId(Integer idCategoria) {

		// Recorro la lista de categorías y si el ID coincide con la variable devuelvo toda la información del objeto
		for (Categoria categoria : lista) {
			if (categoria.getId() == idCategoria) {
				return categoria;
			}//end if
		}//end for
		
		return null;
	}//end buscarPorId(Integer idCategoria)

	// NO IMPLEMENTO ESTE MÉTODO PORQUE ES PARA LA LISTA, NO LA BASE DE DATOS
	@Override
	public void eliminar(Integer idCategoria) {
	}//end eliminar(Integer idCategoria)

	// NO IMPLEMENTO ESTE MÉTODO PORQUE ES PARA LA LISTA, NO LA BASE DE DATOS
	@Override
	public Page<Categoria> buscarTodas(Pageable page) {
		return null;
	}//end buscarTodas(Pageable page)
	/*-------------------------------------------------------------*/
}//end class CategoriasServiceImpl
/*-------------------------------------------------------------*/
