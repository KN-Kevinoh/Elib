/**
 * 
 */
package vn.elib.model.dao;

import java.sql.Connection;

import vn.elib.model.ElibConnexion;
import vn.elib.model.pojo.Abonne;
import vn.elib.model.pojo.Employe;
import vn.elib.model.pojo.Emprunt;
import vn.elib.model.pojo.Exemplaire;
import vn.elib.model.pojo.Genre;
import vn.elib.model.pojo.Livre;
import vn.elib.model.pojo.LivreEmprunte;

/**
 * @author franel
 *
 */
public class DAOFactory {

	protected static final Connection conn = ElibConnexion.getInstance();
	
	/**
	 * Retourne un objet Livre interagissant avec la BDD
	 * @return DAO
	 */
	public static DAO<Livre> getLivreDAO(){
	   return new LivreDAO(conn);
	}
	
	/**
	 * Retourne un objet Abonne interagissant avec la BDD
	 * @return DAO
	 */
	public static DAO<Abonne> getAbonneDAO(){
	   return new AbonneDAO(conn);
	}
	
	/**
	 * Retourne un objet Emprunt interagissant avec la BDD
	 * @return DAO
	 */
	public static DAO<Emprunt> getEmpruntDAO(){
	   return new EmpruntDAO(conn);
	}
	
	/**
	 * Retourne un objet LivreEmprunte interagissant avec la BDD
	 * @return DAO
	 */
	public static DAO<LivreEmprunte> getLivreEmprunteDAO(){
	   return new LivreEmprunteDao(conn);
	}
	
	/**
	 * Retourne un objet Exemplaire interagissant avec la BDD
	 * @return DAO
	 */
	public static DAO<Exemplaire> getExempalireDAO(){
	   return new ExemplaireDAO(conn);
	}
	
	/**
	 * Retourne un objet Employe interagissant avec la BDD
	 * @return DAO
	 */
	public static DAO<Employe> getEmployeDAO(){
	   return new EmployeDAO(conn);
	}
	
	/**
	 * Retourne un objet Genre interagissant avec la BDD
	 * @return DAO
	 */
	public static DAO<Genre> getGenreDAO(){
	   return new GenreDAO(conn);
	}
}
