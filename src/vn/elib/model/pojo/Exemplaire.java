/**
 * 
 */
package vn.elib.model.pojo;

/**
 * @author franel
 *
 */
public class Exemplaire {

	private int id;
	private Rfid rfid;
	private Boolean Disponible;
	
	/**
	 * @param id
	 * @param rfid
	 */
	public Exemplaire(int id, Rfid rfid, Boolean Disponible) {
		this.id = id;
		this.rfid = rfid;
		this.Disponible = Disponible;
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
	/**
	 * @return the rfid
	 */
	public Rfid getRfid() {
		return rfid;
	}
	/**
	 * @param rfid the rfid to set
	 */
	public void setRfid(Rfid rfid) {
		this.rfid = rfid;
	}
	/**
	 * @return the disponible
	 */
	public Boolean getDisponible() {
		return Disponible;
	}
	/**
	 * @param disponible the disponible to set
	 */
	public void setDisponible(Boolean disponible) {
		Disponible = disponible;
	}
	
	
}
