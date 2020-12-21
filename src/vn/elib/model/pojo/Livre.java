/**
 * 
 */
package vn.elib.model.pojo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @author franel
 *
 */
public class Livre extends RecursiveTreeObject<Livre> {

	private StringProperty isbn;
	private StringProperty titre;
	private StringProperty auteur;
	private StringProperty editeur;
	private IntegerProperty nbre_page;
	private IntegerProperty tome;
	private Date annee;
	private Genre genre;
	private Set<Exemplaire> listExemplaire = new HashSet<Exemplaire>();
	private IntegerProperty nombreExemplaire;
	
	/**
	 * @param id
	 * @param titre
	 * @param auteur
	 * @param editeur
	 * @param genre
	 */
	public Livre(String id, String titre, String auteur, String editeur, Genre genre) {
		this.isbn = new SimpleStringProperty(id);
		this.titre = new SimpleStringProperty(titre);
		this.auteur = new SimpleStringProperty(auteur);
		this.editeur = new SimpleStringProperty(editeur);
		this.genre = genre;
		this.nombreExemplaire = new SimpleIntegerProperty(0);
	}
	
	public Livre() {
		this.nombreExemplaire = new SimpleIntegerProperty(0);
	}

	/**
	 * @return the id
	 */
	public StringProperty getId() {
		return isbn;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.isbn = new SimpleStringProperty(id);
	}

	/**
	 * @return the titre
	 */
	public StringProperty getTitre() {
		return titre;
	}

	/**
	 * @param titre the titre to set
	 */
	public void setTitre(String titre) {
		this.titre = new SimpleStringProperty(titre);
	}

	/**
	 * @return the auteur
	 */
	public StringProperty getAuteur() {
		return auteur;
	}

	/**
	 * @param auteur the auteur to set
	 */
	public void setAuteur(String auteur) {
		this.auteur = new SimpleStringProperty(auteur);
	}

	/**
	 * @return the editeur
	 */
	public StringProperty getEditeur() {
		return editeur;
	}

	/**
	 * @param editeur the editeur to set
	 */
	public void setEditeur(String editeur) {
		this.editeur = new SimpleStringProperty(editeur);
	}

	/**
	 * @return the genre
	 */
	public Genre getGenre() {
		return genre;
	}

	/**
	 * @param genre the genre to set
	 */
	public void setGenre(Genre genre) {
		this.genre = genre;
	}
	
	/**
	 * @return list of exemplaire
	 */
	public Set<Exemplaire> getListExemplaire() {
		return listExemplaire;
	}

	/**
	 * @param genre the genre to set
	 */
	public void setListExemplaire(Set<Exemplaire> listExemplaire) {
		this.listExemplaire = listExemplaire;
		this.nombreExemplaire = new SimpleIntegerProperty(listExemplaire.size());
	}

	/**
	 * @param Exemplaire
	 */
	public void addExemplaire(Exemplaire exemplaire){
		if(!this.listExemplaire.contains(exemplaire)) {
			this.listExemplaire.add(exemplaire);
			this.nombreExemplaire = new SimpleIntegerProperty(listExemplaire.size());
		}
	}

	/**
	 * @param Exemplaire
	 */
	public void removeExemplaire(Exemplaire exemplaire){
		this.listExemplaire.remove(exemplaire);
		this.nombreExemplaire = new SimpleIntegerProperty(listExemplaire.size());
	}

	/**
	 * @return the tome
	 */
	public IntegerProperty getTome() {
		return tome;
	}

	/**
	 * @param tome the tome to set
	 */
	public void setTome(int tome) {
		this.tome = new SimpleIntegerProperty(tome);
	}

	/**
	 * @return the annee
	 */
	public Date getAnnee() {
		return annee;
	}

	/**
	 * @param annee the annee to set
	 */
	public void setAnnee(Date annee) {
		this.annee = annee;
	}

	/**
	 * @return the nbre_page
	 */
	public IntegerProperty getNbre_page() {
		return nbre_page;
	}

	/**
	 * @param nbre_page the nbre_page to set
	 */
	public void setNbre_page(int nbre_page) {
		this.nbre_page = new SimpleIntegerProperty(nbre_page);
	}

	/**
	 * @return the nombreExemplaire
	 */
	public IntegerProperty getNombreExemplaire() {
		return nombreExemplaire;
	}

	/**
	 * @param nombreExemplaire the nombreExemplaire to set
	 */
	public void setNombreExemplaire(int nombreExemplaire) {
		this.nombreExemplaire = new SimpleIntegerProperty(nombreExemplaire);
	}
	
	/**
	 * @return Boolean if one exemplaire is disponible
	 */
	public Boolean estDisponible() {
		for(Exemplaire exp :listExemplaire) {
			if(exp.getDisponible())
				return true;
		}
		return false;
	}
	
	/**
	 * @return One exemplaire disponible
	 */
	public Exemplaire getOneDisponible() {
		for(Exemplaire exp :listExemplaire) {
			if(exp.getDisponible())
				return exp;
		}
		return null;
	}
	
	/**
	 * @return nbre exemplaire disponible
	 */
	public IntegerProperty getNombreDisponible() {
		int nbre = 0;
		for(Exemplaire exp :listExemplaire) {
			if(exp.getDisponible())
				nbre++;
		}
		return new SimpleIntegerProperty(nbre);
	}
}
