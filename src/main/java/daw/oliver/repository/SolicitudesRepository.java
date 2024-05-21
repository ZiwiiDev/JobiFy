package daw.oliver.repository;
/*-------------------------------------------------------------*/
import org.springframework.data.jpa.repository.JpaRepository;

import daw.oliver.model.Solicitud;
/*-------------------------------------------------------------*/
public interface SolicitudesRepository extends JpaRepository<Solicitud, Integer> {	// Integer es el tipo de la PK
}//end interface SolicitudesRepository
/*-------------------------------------------------------------*/
