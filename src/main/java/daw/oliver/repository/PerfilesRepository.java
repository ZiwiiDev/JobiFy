package daw.oliver.repository;
/*-------------------------------------------------------------*/
import org.springframework.data.jpa.repository.JpaRepository;

import daw.oliver.model.Perfil;
/*-------------------------------------------------------------*/
//public interface PerfilesRepository extends CrudRepository<Perfil, Integer> {
public interface PerfilesRepository extends JpaRepository<Perfil, Integer> {
}//end interface PerfilesRepository
/*-------------------------------------------------------------*/
