package daw.oliver.service;
/*-------------------------------------------------------------*/
import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;			// Debe ser este import, no otro
import org.springframework.data.domain.Pageable;

import daw.oliver.model.Vacante;
/*-------------------------------------------------------------*/
/* INTERFAZ de Vacantes */
public interface IVacantesService {
	List<Vacante> buscarTodas();
	Vacante buscarPorId(Integer idVacante);
	void guardar(Vacante vacante);
	List<Vacante> buscarDestacadas();
	void eliminar(Integer idVacante);
	
	// Este método ejecuta una query de select pero la parte del where es dinámica dependiendo de los atributos que no sean null
	List<Vacante> buscarByExample(Example<Vacante> example);
	
	// Para integrar la paginación
	Page<Vacante> buscarTodas(Pageable page);
}//end interface IVacantesService
/*-------------------------------------------------------------*/
