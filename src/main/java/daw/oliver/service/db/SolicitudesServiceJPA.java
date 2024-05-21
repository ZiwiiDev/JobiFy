package daw.oliver.service.db;
/*-------------------------------------------------------------*/
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import daw.oliver.model.Solicitud;
import daw.oliver.repository.SolicitudesRepository;
import daw.oliver.service.ISolicitudesService;
/*-------------------------------------------------------------*/
@Service
public class SolicitudesServiceJPA implements ISolicitudesService {
	/*-------------------------------------------------------------*/
	
	
	@Autowired
	private SolicitudesRepository solicitudesRepo;
	
	@Override
	public void guardar(Solicitud solicitud) {
		solicitudesRepo.save(solicitud);
	}

	@Override
	public void eliminar(Integer idSolicitud) {
		solicitudesRepo.deleteById(idSolicitud);
	}

	@Override
	public List<Solicitud> buscarTodas() {
		return solicitudesRepo.findAll();
	}

	@Override
	public Solicitud buscarPorId(Integer idSolicitud) {
		Optional<Solicitud> opcional = solicitudesRepo.findById(idSolicitud);
		
		// Si devuelve true es que ha encontrado el id en la base de datos
		if (opcional.isPresent()) {
			// El método get() nos devuelve el objeto categoría
			return opcional.get();
		}
		
		// Si no encuentra el id no se ejecuta el if, por lo tanto devuelve null, ya que no hay registro con ese id
		return null;
	}

	@Override
	public Page<Solicitud> buscarTodas(Pageable page) {
		return solicitudesRepo.findAll(page);
	}
}
