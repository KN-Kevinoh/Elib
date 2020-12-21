/**
 * 
 */
package vn.elib.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.ObservableList;
import vn.elib.model.pojo.Employe;

/**
 * @author franel
 *
 */
public class EmployeDAO extends DAO<Employe> {

	public EmployeDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean create(Employe obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Employe obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Employe obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Employe find(int id) {
		// TODO Auto-generated method stub
		
		Employe employe = new Employe();    
		
	    try {
	    	String query = "SELECT * FROM employe";
	        query += " WHERE pseudo = ?";
	    	
	    	PreparedStatement prepare = this.connect.prepareStatement(query,
	    			ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
	    	
	    	prepare.setString(1, String.valueOf(id));
	    	
	    	ResultSet result = prepare.executeQuery();

	    	if(result.first()){
	    		employe.setId(result.getInt("id_employe"));
	    		employe.setNom(result.getString("nom_employe"));
	    		employe.setPrenom(result.getString("prenom_employe"));
	    		employe.setSexe(result.getString("sexe_employe"));
	    		employe.setPseudo(result.getString("pseudo"));
	    		employe.setPassword(result.getString("password"));
	    	}
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    }
	    
	    return employe;
	}

	@Override
	public ObservableList<Employe> find() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Employe find(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
