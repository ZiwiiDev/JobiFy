package daw.oliver.service.db;
/*-------------------------------------------------------------*/
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import daw.oliver.model.Vacante;
import daw.oliver.repository.VacantesRepository;
import daw.oliver.service.IVacantesService;
/*-------------------------------------------------------------*/
/*
 * Se utiliza "@Primary" o "@Qualifier", no los 2 a la vez ("@Primary" es más simple porque sólo es en la clase que se use)
 * */

@Service
@Primary			// Si ponemos "@Primary" nos saldrán las categorías de la base de datos, y no de la lista
public class VacantesServiceJPA implements IVacantesService {
	/*-------------------------------------------------------------*/
	/* VARIABLES */
	
	@Autowired
	private VacantesRepository vacantesRepo;
	/*-------------------------------------------------------------*/
	/* MÉTODOS */
	
	@Override
	public List<Vacante> buscarTodas() {
		return vacantesRepo.findAll();
	}//end buscarTodas()

	@Override
	public Vacante buscarPorId(Integer idVacante) {
		Optional<Vacante> opcional = vacantesRepo.findById(idVacante);
		
		// Si devuelve true es que ha encontrado el ID en la base de datos
		if (opcional.isPresent()) {
			// El método get() nos devuelve el objeto categoría
			return opcional.get();
		}//end if
		
		// Si no encuentra el ID no se ejecuta el if, por lo tanto devuelve null, ya que no hay registro con ese ID
		return null;
	}//end buscarPorId(Integer idVacante)

	// Guardamos en la base de datos una nueva vacante
	@Override
	public void guardar(Vacante vacante) {
		// Con save se puede hacer tanto un insert como un update, se diferencia por el valor ID, si existe lo actualiza y si no existe se crea
		vacantesRepo.save(vacante);
	}//end guardar(Vacante vacante)

	@Override
	public List<Vacante> buscarDestacadas() {
		return vacantesRepo.findByDestacadoAndEstatusOrderByIdDesc(1, "Aprobada");		// 1 es Destacado
	}//end buscarDestacadas()

	// Elimino una vacante de la base de datos
	@Override
	public void eliminar(Integer idVacante) {
		vacantesRepo.deleteById(idVacante);
	}//end eliminar(Integer idVacante)

	@Override
	public List<Vacante> buscarByExample(Example<Vacante> example) {
		// Encuentra todas las vacantes, pero sólo mediante la búsqueda de las variables que no sean null
		return vacantesRepo.findAll(example);
	}//end buscarByExample(Example<Vacante> example)

	// Para integrar la paginación
	@Override
	public Page<Vacante> buscarTodas(Pageable page) {
		return vacantesRepo.findAll(page);
	}//end buscarTodas(Pageable page)
	/*-------------------------------------------------------------*/
}//end class VacantesServiceJPA
/*-------------------------------------------------------------*/
