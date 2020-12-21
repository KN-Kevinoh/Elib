package vn.elib.model.pojo;

import java.util.Date;

public class Emprunt {
	private int id;
	private Abonne abonne;
	private Exemplaire exempalire;
	private Date date_emprunt;
	private Date date_retour;
	
	public Emprunt(Abonne abonne, Exemplaire exempalire, Date date_emprunt, Date date_retour) {
		this.abonne = abonne;
		this.exempalire = exempalire;
		this.date_emprunt = date_emprunt;
		this.date_retour = date_retour;
	}
	
	public Emprunt() {
		
	}

	/**
	 * @return the abonne
	 */
	public Abonne getAbonne() {
		return abonne;
	}

	/**
	 * @param abonne the abonne to set
	 */
	public void setAbonne(Abonne abonne) {
		this.abonne = abonne;
	}

	/**
	 * @return the exempalire
	 */
	public Exemplaire getExempalire() {
		return exempalire;
	}

	/**
	 * @param exempalire the exempalire to set
	 */
	public void setExempalire(Exemplaire exempalire) {
		this.exempalire = exempalire;
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
	}

	/**
	 * @return the date_retour
	 */
	public Date getDate_retour() {
		return date_retour;
	}

	/**
	 * @param date_retour the date_retour to set
	 */
	public void setDate_retour(Date date_retour) {
		this.date_retour = date_retour;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	
}
