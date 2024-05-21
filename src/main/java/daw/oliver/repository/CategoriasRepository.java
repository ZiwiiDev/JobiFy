package daw.oliver.repository;
/*-------------------------------------------------------------*/
import org.springframework.data.jpa.repository.JpaRepository;

import daw.oliver.model.Categoria;
/*-------------------------------------------------------------*/
//public interface CategoriasRepository extends CrudRepository<Categoria, Integer> {
public interface CategoriasRepository extends JpaRepository<Categoria, Integer> {
}//end interface CategoriasRepository
/*-------------------------------------------------------------*/
