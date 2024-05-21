package daw.oliver.repository;
/*-------------------------------------------------------------*/
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import daw.oliver.model.Vacante;
/*-------------------------------------------------------------*/
//public interface VacantesRepository extends CrudRepository<Vacante, Integer> {
public interface VacantesRepository extends JpaRepository<Vacante, Integer>{
	// "findBy" y luego lo que queremos buscar ("estatus" es de tipo "varchar" en la base de datos por lo que como parámetro pasamos un "string")
	List<Vacante> findByEstatus (String estatus);
	List<Vacante> findByDestacadoAndEstatusOrderByIdDesc (int destacado, String estatus);
	List<Vacante> findBySalarioBetweenOrderBySalarioDesc (double salario1, double salario2);
	List<Vacante> findByEstatusIn (String[] estatus);
}//end interface VacantesRepository
/*-------------------------------------------------------------*/
