/**
 * 
 */
package vn.elib.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import vn.elib.controller.Global;
import vn.elib.model.pojo.Genre;
import vn.elib.model.pojo.LivreEmprunte;

/**
 * @author franel
 *
 */
public class LivreEmprunteDao extends DAO<LivreEmprunte> {

	public LivreEmprunteDao(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean create(LivreEmprunte obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(LivreEmprunte obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(LivreEmprunte obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public LivreEmprunte find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ObservableList<LivreEmprunte> find() {
		// TODO Auto-generated method stub
		
		ObservableList<LivreEmprunte> data = FXCollections.observableArrayList();
		
		try {
	    	String query = "SELECT * FROM livre";
	        query += " INNER JOIN genre ON genre = id_genre";
	        query += " INNER JOIN exemplaire ON livre = isbn";
	        query += " INNER JOIN emprunt ON emprunt.id_exemplaire = exemplaire.id_exemplaire";
	        
	        if(Global.abonne != null)
	        	query += " WHERE emprunt.id_abonne = ?";
	    	
	    	PreparedStatement prepare = this.connect.prepareStatement(query,
	    			ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
	    	
	    	if(Global.abonne != null)
	    		prepare.setInt(1, Global.abonne.getId());
	    	
	    	ResultSet result = prepare.executeQuery();
	    	
			while(result.next()) {
				Genre g = new Genre(result.getInt("id_genre"), result.getString("nom_genre"));
				LivreEmprunte l = new LivreEmprunte(result.getString("isbn"),
						result.getString("titre"),
						result.getString("auteur"),
						result.getString("editeur"),
						result.getInt("nbre_page"),
						result.getInt("tome"),
						result.getDate("date_emprunt"),
						result.getDate("date_retour"),
						g,
						result.getString("rfid"),
						result.getInt("id_exemplaire"),
						result.getInt("id_emprunt"));
						
						data.add(l);
			}
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    }
		
		return data;
	}

	@Override
	public LivreEmprunte find(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
