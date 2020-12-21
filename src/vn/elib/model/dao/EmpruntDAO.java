package vn.elib.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import javafx.collections.ObservableList;
import vn.elib.controller.Global;
import vn.elib.model.pojo.Emprunt;
import vn.elib.model.pojo.Exemplaire;
import vn.elib.model.pojo.Rfid;

public class EmpruntDAO extends DAO<Emprunt>{

	public EmpruntDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean create(Emprunt obj) {
		// TODO Auto-generated method stub
		
		try {
	    	String query = "INSERT INTO emprunt (id_abonne, id_exemplaire, date_emprunt)";
	        query += "  VALUES(?, ?, ?)";
	        
	    	PreparedStatement prepare = this.connect.prepareStatement(query,
	    			ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
	    	
	    	prepare.setInt(1, obj.getAbonne().getId());
	    	prepare.setInt(2, obj.getExempalire().getId());
	    	prepare.setDate(3, new Date(obj.getDate_emprunt().getTime()));
	    	
	    	prepare.executeUpdate();
	    	
	    	
	    	query = "SELECT * FROM exemplaire";
	        query += " WHERE id_exemplaire = ?";
	    	
	    	prepare = this.connect.prepareStatement(query,
	    			ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
	    	
	    	prepare.setInt(1, obj.getExempalire().getId());
	    	
	    	ResultSet result = prepare.executeQuery();
	    	
	    	if(result.first()){
	    		result.updateBoolean("etat_emprunt", true);
	    		result.updateRow();
	    	}
	    	
	    	
	    	return true;
	    	
		} catch (SQLException e) {
	    	e.printStackTrace();
	    }
		
		return false;
	}

	@Override
	public boolean delete(Emprunt obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Emprunt obj) {
		// TODO Auto-generated method stub
		try {
	    	String query = "SELECT * FROM emprunt";
	        query += " INNER JOIN exemplaire ON emprunt.id_exemplaire = exemplaire.id_exemplaire";
	        query += " INNER JOIN rfid ON code_rfid = rfid";
	        query += " WHERE id_emprunt = ?";
	    	
	    	PreparedStatement prepare = this.connect.prepareStatement(query,
	    			ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
	    	
	    	prepare.setInt(1, obj.getId());
	    	
	    	ResultSet result = prepare.executeQuery();
	    	
	    	if(result.first()){
	    		obj.setDate_retour(Calendar.getInstance().getTime());
	    		result.updateDate("date_retour", new Date(obj.getDate_retour().getTime()));
	    		result.updateRow();
	    	}
	    	
	    	
	    	query = "SELECT * FROM exemplaire";
	        query += " WHERE id_exemplaire = ?";
	    	
	    	prepare = this.connect.prepareStatement(query,
	    			ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
	    	
	    	prepare.setInt(1, obj.getExempalire().getId());
	    	
	    	result = prepare.executeQuery();
	    	
	    	if(result.first()){
	    		result.updateBoolean("etat_emprunt", false);
	    		result.updateRow();
	    	}
	    	
	    	
	    	return true;
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    }
		
		return false;
	}

	@Override
	public Emprunt find(int id) {
		// TODO Auto-generated method stub
		
		Emprunt emprunt = null;    
		
	    try {
	    	String query = "SELECT * FROM emprunt";
	        query += " INNER JOIN exemplaire ON emprunt.id_exemplaire = exemplaire.id_exemplaire";
	        query += " INNER JOIN rfid ON code_rfid = rfid";
	        query += " WHERE id_emprunt = ?";
	    	
	    	PreparedStatement prepare = this.connect.prepareStatement(query,
	    			ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
	    	
	    	prepare.setInt(1, id);
	    	
	    	ResultSet result = prepare.executeQuery();

	    	if(result.first()){
	    		emprunt = new Emprunt();
	    		Rfid rfid = new Rfid(result.getString("code_rfid"));
	    		
	    		Exemplaire ex = new Exemplaire(result.getInt("id_exemplaire"), rfid,
	    				!result.getBoolean("etat_emprunt"));
	    		
	    		emprunt.setExempalire(ex);
	    		emprunt.setAbonne(Global.abonne);
	    		emprunt.setDate_emprunt(result.getDate("date_emprunt"));
	    		emprunt.setDate_retour(result.getDate("date_retour"));
	    		emprunt.setId(result.getInt("id_emprunt"));
	    	}
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    }
	    return emprunt;
	}

	@Override
	public ObservableList<Emprunt> find() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Emprunt find(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
