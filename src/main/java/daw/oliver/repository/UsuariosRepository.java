package daw.oliver.repository;
/*-------------------------------------------------------------*/
import org.springframework.data.jpa.repository.JpaRepository;

import daw.oliver.model.Usuario;
/*-------------------------------------------------------------*/
//public interface UsuariosRepository extends CrudRepository<Usuario, Integer> {
public interface UsuariosRepository extends JpaRepository<Usuario, Integer> {
	Usuario findByUsername (String username);
}//end interface UsuariosRepository
/*-------------------------------------------------------------*/
