/**
 * 
 */
package vn.elib.model.pojo;

import java.util.Date;

/**
 * @author franel
 *
 */
public class CarteMagnetique {

	private int id;
	private String code;
	private int codePin;
	private Date validide;
	
	public CarteMagnetique(String code, int codePin, Date validite) {
		this.code = code;
		this.codePin = codePin;
		this.validide = validite;
	}
	
	public CarteMagnetique() {
		
	}
	
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * @return the codePin
	 */
	public Integer getCodePin() {
		return codePin;
	}
	/**
	 * @param codePin the codePin to set
	 */
	public void setCodePin(int codePin) {
		this.codePin = codePin;
	}
	/**
	 * @return the validide
	 */
	public Date getValidide() {
		return validide;
	}
	/**
	 * @param validide the validide to set
	 */
	public void setValidide(Date validide) {
		this.validide = validide;
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
