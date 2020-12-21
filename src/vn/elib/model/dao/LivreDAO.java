/**
 * 
 */
package vn.elib.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import vn.elib.model.pojo.Exemplaire;
import vn.elib.model.pojo.Genre;
import vn.elib.model.pojo.Livre;
import vn.elib.model.pojo.Rfid;

/**
 * @author franel
 *
 */
public class LivreDAO extends DAO<Livre> {

	public LivreDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean create(Livre obj) {
		// TODO Auto-generated method stub
		
		try {
	    	String query = "INSERT INTO livre (isbn, titre, auteur, editeur, tome, annee, genre, nbre_page)";
	        query += "  VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
	    		        
	    	PreparedStatement prepare = this.connect.prepareStatement(query,
	    			ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
	    	
	    	prepare.setString(1, obj.getId().get());
	    	prepare.setString(2, obj.getTitre().get());
	    	prepare.setString(3, obj.getAuteur().get());
	    	prepare.setString(4, obj.getEditeur().get());
	    	prepare.setInt(5, obj.getTome().get());
	    	prepare.setDate(6, new Date(obj.getAnnee().getTime()));
	    	prepare.setInt(7, obj.getGenre().getId());
	    	prepare.setInt(8, obj.getNbre_page().get());
	    	
	    	prepare.executeUpdate();
	    	
	    	return true;
	    	
		} catch (SQLException e) {
	    	e.printStackTrace();
	    }
		
		return false;
	}

	@Override
	public boolean delete(Livre obj) {
		// TODO Auto-generated method stub
		try {
	    	String query = "DELETE FROM livre WHERE isbn = ?";
	        
	    	PreparedStatement prepare = this.connect.prepareStatement(query,
	    			ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
	    	
	    	prepare.setString(1, obj.getId().get());
	    	
	    	prepare.executeUpdate();
	    	
	    	return true;
	    	
		} catch (SQLException e) {
	    	e.printStackTrace();
	    }
		
		return false;
	}

	@Override
	public boolean update(Livre obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Livre find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ObservableList<Livre> find() {
		// TODO Auto-generated method stub
		
		ObservableList<Livre> data = FXCollections.observableArrayList();
		
		try {
	    	String query = "SELECT * FROM livre";
	        query += " INNER JOIN genre ON genre = id_genre";
	    	
	    	PreparedStatement prepare = this.connect.prepareStatement(query,
	    			ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
	    	
	    	ResultSet result = prepare.executeQuery();
	    	
			while(result.next()) {
				Livre l = new Livre();
						l.setId(result.getString("isbn")); 
						l.setTitre(result.getString("titre"));
						l.setAuteur(result.getString("auteur"));
						l.setEditeur(result.getString("editeur"));
						l.setNbre_page(result.getInt("nbre_page"));
						l.setTome(result.getInt("tome"));
						l.setAnnee(result.getDate("annee"));
						
						Genre g = new Genre(result.getInt("id_genre"), result.getString("nom_genre"));
						l.setGenre(g);
						
						String query2 = "SELECT * FROM exemplaire";
				        query2 += " INNER JOIN rfid ON rfid = code_rfid";
				        query2 += " WHERE livre = ?";
				    	
				    	PreparedStatement prepare2 = this.connect.prepareStatement(query2,
				    			ResultSet.TYPE_SCROLL_SENSITIVE,
			                    ResultSet.CONCUR_UPDATABLE);
				    	
				    	prepare2.setString(1, result.getString("isbn"));
				    	
				    	ResultSet result2 = prepare2.executeQuery();
				    	
				    	while(result2.next()) {
				    		Rfid rfid = new Rfid(result2.getString("rfid"));
				    		Exemplaire ex = new Exemplaire(result2.getInt("id_exemplaire"), rfid,
				    				!result2.getBoolean("etat_emprunt"));
				    		l.addExemplaire(ex);
				    	}
						
						data.add(l);
			}
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    }
		
		return data;
	}

	@Override
	public Livre find(String id) {
		// TODO Auto-generated method stub
		
		Livre livre = null;
		
		try {
	    	String query = "SELECT * FROM livre";
	        query += " INNER JOIN genre ON genre = id_genre";
	        query += " WHERE isbn = ?";
	    	
	    	PreparedStatement prepare = this.connect.prepareStatement(query,
	    			ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
	    	
	    	prepare.setString(1, id);
	    	
	    	ResultSet result = prepare.executeQuery();
	    	
	    	if(result.first()){
	    		
						livre.setId(result.getString("isbn")); 
						livre.setTitre(result.getString("titre"));
						livre.setAuteur(result.getString("auteur"));
						livre.setEditeur(result.getString("editeur"));
						livre.setNbre_page(result.getInt("nbre_page"));
						livre.setTome(result.getInt("tome"));
						livre.setAnnee(result.getDate("annee"));
						
						Genre g = new Genre(result.getInt("id_genre"), result.getString("nom_genre"));
						livre.setGenre(g);
						
						String query2 = "SELECT * FROM exemplaire";
				        query2 += " INNER JOIN rfid ON rfid = code_rfid";
				        query2 += " WHERE livre = ?";
				    	
				    	PreparedStatement prepare2 = this.connect.prepareStatement(query2,
				    			ResultSet.TYPE_SCROLL_SENSITIVE,
			                    ResultSet.CONCUR_UPDATABLE);
				    	
				    	prepare2.setString(1, result.getString("isbn"));
				    	
				    	ResultSet result2 = prepare2.executeQuery();
				    	
				    	while(result2.next()) {
				    		Rfid rfid = new Rfid(result2.getString("rfid"));
				    		Exemplaire ex = new Exemplaire(result2.getInt("id_exemplaire"), rfid,
				    				!result2.getBoolean("etat_emprunt"));
				    		livre.addExemplaire(ex);
				    	}
			}
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    }
		
		return livre;
	}	
}
