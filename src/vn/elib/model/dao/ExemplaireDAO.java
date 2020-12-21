/**
 * 
 */
package vn.elib.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.ObservableList;
import vn.elib.controller.Global;
import vn.elib.model.pojo.Exemplaire;
import vn.elib.model.pojo.Rfid;

/**
 * @author franel
 *
 */
public class ExemplaireDAO extends DAO<Exemplaire> {

	public ExemplaireDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean create(Exemplaire obj) {
		// TODO Auto-generated method stub
		try {
			String query = "INSERT INTO rfid (code_rfid)";
	        query += " VALUES(?)";
	        
	        PreparedStatement prepare = this.connect.prepareStatement(query,
	    			ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
			
	        prepare.setString(1, obj.getRfid().getCodeRFID());
	        
	        prepare.executeUpdate();
			
	    	query = "INSERT INTO exemplaire (rfid, livre, etat_emprunt)";
	        query += " VALUES(?, ?, ?)";
	    		        
	    	prepare = this.connect.prepareStatement(query,
	    			ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
	    	
	    	prepare.setString(1, obj.getRfid().getCodeRFID());
	    	prepare.setString(2, Global.livreExp.getId().get());
	    	prepare.setBoolean(3, false);
	    	
	    	prepare.executeUpdate();
	    	
	    	return true;
	    	
		} catch (SQLException e) {
	    	e.printStackTrace();
	    }
		
		return false;
	}

	@Override
	public boolean delete(Exemplaire obj) {
		// TODO Auto-generated method stub
		try {
	    	String query = "DELETE FROM exemplaire WHERE rfid = ?";
	        
	    	PreparedStatement prepare = this.connect.prepareStatement(query,
	    			ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
	    	
	    	prepare.setString(1, obj.getRfid().getCodeRFID());
	    	
	    	prepare.executeUpdate();
	    	
	    	query = "DELETE FROM rfid WHERE code_rfid = ?";
	    	
	    	prepare = this.connect.prepareStatement(query,
	    			ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
	    	
	    	prepare.setString(1, obj.getRfid().getCodeRFID());
	    	
	    	prepare.executeUpdate();
	    	
	    	return true;
	    	
		} catch (SQLException e) {
	    	e.printStackTrace();
	    }
		
		return false;
	}

	@Override
	public boolean update(Exemplaire obj) {
		// TODO Auto-generated method stub
		
		return false;
	}

	@Override
	public Exemplaire find(int id) {
		// TODO Auto-generated method stub
		
		Exemplaire exemplaire = null;
		
	    try {
	    	String query = "SELECT * FROM exempmlaire";
	        query += " INNER JOIN rfid ON code_rfid = rfid";
	        query += " WHERE id_exemplaire = ?";
	    	
	    	PreparedStatement prepare = this.connect.prepareStatement(query,
	    			ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
	    	
	    	prepare.setInt(1, id);
	    	
	    	ResultSet result = prepare.executeQuery();

	    	if(result.first()){
	    		Rfid rfid = new Rfid(result.getString("code_rfid"));
	    		exemplaire = new Exemplaire(result.getInt("id_exemplaire"), rfid,
						!result.getBoolean("etat_emprunt"));
	    	}
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    }
	    return exemplaire;
	}

	@Override
	public ObservableList<Exemplaire> find() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Exemplaire find(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
