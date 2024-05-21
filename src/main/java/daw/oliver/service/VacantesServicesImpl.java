package daw.oliver.service;
/*-------------------------------------------------------------*/
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import daw.oliver.model.Vacante;
/*-------------------------------------------------------------*/
@Service
//@Primary 			<-- Si ponemos "@Primary" nos saldrán las categorías de la lista, y no de la base de datos
public class VacantesServicesImpl implements IVacantesService {
	/*-------------------------------------------------------------*/
	/* VARIABLES */
	
	private List<Vacante> lista = null;
	/*-------------------------------------------------------------*/
	/* CONSTRUCTOR */
	
	public VacantesServicesImpl () {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		lista = new LinkedList<Vacante>();
		
		try {
				Vacante vacante1 = new Vacante();
				Vacante vacante2 = new Vacante();
				Vacante vacante3 = new Vacante();
				Vacante vacante4 = new Vacante();
				
				vacante1.setId(1);
				vacante1.setNombre("Ingeniero Civil");
				vacante1.setDescripcion("Solicitamos Ing. Civil para diseñar puente peatonal.");
				vacante1.setFecha(sdf.parse("08-02-2019"));
				vacante1.setSalario(14000.0);
				vacante1.setDestacado(1);
				vacante1.setEstatus("Aprobada");
				vacante1.setImagen("empresa1.png");
									
				vacante2.setId(2);
				vacante2.setNombre("Contador Publico");
				vacante2.setDescripcion("Empresa importante solicita contador con 5 años de experiencia titulado.");
				vacante2.setFecha(sdf.parse("09-02-2019"));
				vacante2.setSalario(12000.0);
				vacante2.setDestacado(0);
				vacante2.setEstatus("Creada");
				vacante2.setImagen("empresa2.png");
				
				vacante3.setId(3);
				vacante3.setNombre("Ingeniero Eléctrico");
				vacante3.setDescripcion("Empresa internacional solicita Ingeniero mecánico para mantenimiento de la instalación eléctrica.");
				vacante3.setFecha(sdf.parse("10-02-2019"));
				vacante3.setSalario(10500.0);
				vacante3.setEstatus("Aprobada");
				vacante3.setDestacado(0);

				vacante4.setId(4);
				vacante4.setNombre("Diseñador Gráfico");
				vacante4.setDescripcion("Solicitamos Diseñador Gráfico titulado para diseñar publicidad de la empresa.");
				vacante4.setFecha(sdf.parse("11-02-2019"));
				vacante4.setSalario(7500.0);
				vacante4.setEstatus("Eliminada");
				vacante4.setDestacado(1);
				vacante4.setImagen("empresa3.png");
				
				lista.add(vacante1);			
				lista.add(vacante2);
				lista.add(vacante3);
				lista.add(vacante4);
				
			} catch (ParseException e) {
				System.out.println("Error: " + e.getMessage());
			}//end catch
	}//end constructor VacantesServicesImpl()
	
	@Override
	public List<Vacante> buscarTodas() {
		return lista;
	}//end buscarTodas()

	@Override
	public Vacante buscarPorId(Integer idVacante) {
		
		// Recorro la lista de vacantes y si el ID coincide con la variable devuelvo toda la información del objeto
		for (Vacante miVacante : lista) {
			if (miVacante.getId() == idVacante) {
				return miVacante;
			}//end if
		}//end for
		
		return null;
	}//end buscarPorId(Integer idVacante)

	@Override
	public void guardar(Vacante vacante) {
		lista.add(vacante);
	}//end guardar(Vacante vacante)

	// NO IMPLEMENTO ESTE MÉTODO PORQUE ES PARA LA LISTA, NO LA BASE DE DATOS
	@Override
	public List<Vacante> buscarDestacadas() {
		return null;
	}//end buscarDestacadas()

	// NO IMPLEMENTO ESTE MÉTODO PORQUE ES PARA LA LISTA, NO LA BASE DE DATOS
	@Override
	public void eliminar(Integer idVacante) {
	}//end eliminar(Integer idVacante)

	// NO IMPLEMENTO ESTE MÉTODO PORQUE ES PARA LA LISTA, NO LA BASE DE DATOS
	@Override
	public List<Vacante> buscarByExample(Example<Vacante> example) {
		return null;
	}//end buscarByExample(Example<Vacante> example)

	// NO IMPLEMENTO ESTE MÉTODO PORQUE ES PARA LA LISTA, NO LA BASE DE DATOS
	@Override
	public Page<Vacante> buscarTodas(Pageable page) {
		return null;
	}//end buscarTodas(Pageable page)
	/*-------------------------------------------------------------*/
}//end class VacantesServicesImpl
/*-------------------------------------------------------------*/
