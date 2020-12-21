/**
 * 
 */
package vn.elib.model.pojo;

import java.util.Calendar;
import java.util.Date;

import vn.elib.controller.Global;

/**
 * @author franel
 *
 */
public class LivreEmprunte {
	private String isbn;
	private String titre;
	private String auteur;
	private String editeur;
	private int nbre_page;
	private int tome;
	private Date date_emprunt;
	private Date date_remise;
	private Date delais;
	private Genre genre;
	private String rfid;
	private int id_exemplaire;
	private int id_emprunt;
	
	/**
	 * @param isbn
	 * @param titre
	 * @param auteur
	 * @param editeur
	 * @param nbre_page
	 * @param tome
	 * @param date_emprunt
	 * @param date_remise
	 * @param genre
	 * @param rfid
	 */
	public LivreEmprunte(String isbn, String titre, String auteur, String editeur, int nbre_page, int tome,
			Date date_emprunt, Date date_remise, Genre genre, String rfid, int id_exemplaire, int id_emprunt) {
		super();
		this.isbn = isbn;
		this.titre = titre;
		this.auteur = auteur;
		this.editeur = editeur;
		this.nbre_page = nbre_page;
		this.tome = tome;
		this.date_emprunt = date_emprunt;
		this.date_remise = date_remise;
		this.genre = genre;
		this.rfid = rfid;
		this.id_exemplaire = id_exemplaire;
		this.id_emprunt = id_emprunt;
		
		Calendar c = Calendar.getInstance();
		c.setTime(date_emprunt);
		c.add(Calendar.DATE, Global.DelaisEmprunt);
		delais = c.getTime();
	}

	/**
	 * @return the isbn
	 */
	public String getIsbn() {
		return isbn;
	}

	/**
	 * @param isbn the isbn to set
	 */
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	/**
	 * @return the titre
	 */
	public String getTitre() {
		return titre;
	}

	/**
	 * @param titre the titre to set
	 */
	public void setTitre(String titre) {
		this.titre = titre;
	}

	/**
	 * @return the auteur
	 */
	public String getAuteur() {
		return auteur;
	}

	/**
	 * @param auteur the auteur to set
	 */
	public void setAuteur(String auteur) {
		this.auteur = auteur;
	}

	/**
	 * @return the editeur
	 */
	public String getEditeur() {
		return editeur;
	}

	/**
	 * @param editeur the editeur to set
	 */
	public void setEditeur(String editeur) {
		this.editeur = editeur;
	}

	/**
	 * @return the nbre_page
	 */
	public int getNbre_page() {
		return nbre_page;
	}

	/**
	 * @param nbre_page the nbre_page to set
	 */
	public void setNbre_page(int nbre_page) {
		this.nbre_page = nbre_page;
	}

	/**
	 * @return the tome
	 */
	public int getTome() {
		return tome;
	}

	/**
	 * @param tome the tome to set
	 */
	public void setTome(int tome) {
		this.tome = tome;
	}

	/**
	 * @return the date_emprunt
	 */
	public Date getDate_emprunt() {
		return date_emprunt;
	}

	/**
	 * @param date_emprunt the date_emprunt to set
	 */
	public void setDate_emprunt(Date date_emprunt) {
		this.date_emprunt = date_emprunt;
		
		Calendar c = Calendar.getInstance();
		c.setTime(date_emprunt);
		c.add(Calendar.DATE, 1);
		delais = c.getTime();
	}

	/**
	 * @return the date_remise
	 */
	public Date getDate_remise() {
		return date_remise;
	}

	/**
	 * @param date_remise the date_remise to set
	 */
	public void setDate_remise(Date date_remise) {
		this.date_remise = date_remise;
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
	 * @return the rfid
	 */
	public String getRfid() {
		return rfid;
	}

	/**
	 * @param rfid the String to set
	 */
	public void setRfid(String rfid) {
		this.rfid = rfid;
	}

	/**
	 * @return the delais
	 */
	public Date getDelais() {
		return delais;
	}

	/**
	 * @param delais the delais to set
	 */
	public void setDelais(Date delais) {
		this.delais = delais;
	}

	/**
	 * @return the id_exemplaire
	 */
	public int getId_exemplaire() {
		return id_exemplaire;
	}

	/**
	 * @param id_exemplaire the id_exemplaire to set
	 */
	public void setId_exemplaire(int id_exemplaire) {
		this.id_exemplaire = id_exemplaire;
	}

	/**
	 * @return the id_emprunt
	 */
	public int getId_emprunt() {
		return id_emprunt;
	}

	/**
	 * @param id_emprunt the id_emprunt to set
	 */
	public void setId_emprunt(int id_emprunt) {
		this.id_emprunt = id_emprunt;
	}
	
	
}
