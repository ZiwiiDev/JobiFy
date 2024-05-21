package daw.oliver.service;
/*-------------------------------------------------------------*/
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import daw.oliver.model.Solicitud;
/*-------------------------------------------------------------*/
/* INTERFAZ de Solicitudes */
public interface ISolicitudesService {
	void guardar(Solicitud solicitud);
	void eliminar(Integer idSolicitud);
	List<Solicitud> buscarTodas();
	Solicitud buscarPorId(Integer idSolicitud);
	
	// Para integrar la paginación
	Page<Solicitud> buscarTodas(Pageable page);
}//end interface ISolicitudesService
/*-------------------------------------------------------------*/
