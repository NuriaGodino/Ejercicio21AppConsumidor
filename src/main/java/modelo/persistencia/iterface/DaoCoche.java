package modelo.persistencia.iterface;

import java.util.List;

import modelo.entidad.Coche;

public interface DaoCoche {

	/**
	 * @author Nuria Godino
	 * Metodo que da de alta a un coche en una BBDD. Se genera ID de forma automatica.
	 * @param c el coche a dar de alta
	 * @return true en caso de que se haya dado de alta. false en caso de error con BBDD
	 */
	boolean alta(Coche c);
	
	/**
	 * @author Nuria Godino
	 * Metodo que da de baja a un coche en una BBDD. 
	 * @param id del coche a dar de baja
	 * @return true en caso de que se haya dado de baja. false en caso de error con BBDD
	 */
	boolean baja(int id);
	
	/**
	 * @author Nuria Godino
	 * Metodo que modifica un coche en una BBDD. 
	 * @param coche a modificar
	 * @return true en caso de que se haya modificado. false en caso de error con BBDD
	 */
	boolean modificar(Coche c);
	
	/**
	 * @author Nuria Godino
	 * Metodo que busca un coche en una BBDD segun el id
	 * @param id del coche a buscar
	 * @return el coche que se está buscando
	 */
	Coche buscarPorID(int id);
	
	/**
	 * @author Nuria Godino
	 * Metodo que busca un coche en una BBDD segun la matricula
	 * @param matricula del coche a buscar
	 * @return el coche que se está buscando
	 */
	Coche buscarPorMatricula(String matricula);
	
	/**
	 * @author Nuria Godino
	 * Metodo que busca un coche en una BBDD segun el marca
	 * @param marca del coche a buscar
	 * @return el coche que se está buscando
	 */
	Coche buscarPorMarca(String marca);
	
	/**
	 * @author Nuria Godino
	 * Metodo que busca un coche en una BBDD segun el modelo
	 * @param modelo del coche a buscar
	 * @return el coche que se está buscando
	 */
	Coche buscarPorModelo(String modelo);
	
	List<Coche> listar();
}
