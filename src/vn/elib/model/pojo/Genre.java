/**
 * 
 */
package vn.elib.model.pojo;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @author franel
 *
 */
public class Genre {

	private int id_genre;
	private StringProperty nom_genre;
	
	/**
	 * @param id
	 * @param nomGenre
	 */
	public Genre(int id, String nomGenre) {
		super();
		this.id_genre = id;
		this.nom_genre = new SimpleStringProperty(nomGenre);
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id_genre;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id_genre = id;
	}

	/**
	 * @return the nomGenre
	 */
	public StringProperty getNomGenre() {
		return nom_genre;
	}

	/**
	 * @param nomGenre the nomGenre to set
	 */
	public void setNomGenre(String nomGenre) {
		this.nom_genre = new SimpleStringProperty(nomGenre);
	}

	@Override
	public String toString() {
		return nom_genre.get();
	}
	
	
}
