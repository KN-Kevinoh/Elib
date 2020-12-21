/**
 * 
 */
package vn.elib.vue;

import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import vn.elib.model.dao.DAO;
import vn.elib.model.dao.DAOFactory;
import vn.elib.model.pojo.Genre;
import vn.elib.model.pojo.Livre;

/**
 * @author franel
 *
 */
public class CreerLivre  implements Initializable {

	@FXML
    private JFXTextField isbn;
	@FXML
    private JFXTextField titre;
	@FXML
    private JFXTextField auteur;
	@FXML
    private JFXTextField editeur;
	@FXML
    private JFXTextField tome;
	@FXML
    private JFXTextField annee;
	@FXML
    private JFXComboBox<Genre> genre;
	@FXML
    private JFXTextField page;
	@FXML
    private Label errorCreer;
	
	private ObservableList<Genre> genres = FXCollections.observableArrayList();
	
	@FXML
	public void valider() {
		String nomisbn = isbn.getText();
		String nomtitre = titre.getText();
		String nomauteur = auteur.getText();
		String nomediteur = editeur.getText();
		String nomtome = tome.getText();
		String nomannee = annee.getText();
		String nompage = page.getText();
		Genre nomgenre = genre.getSelectionModel().getSelectedItem();
		
		errorCreer.setText("");
		
		if(nomisbn.isEmpty() || nomtitre.isEmpty() || nomauteur.isEmpty() || nomediteur.isEmpty()
				|| nomannee.isEmpty() || nompage.isEmpty() || nomgenre == null) {
			errorCreer.setText("Veillez remplir tous les champs du formulaire");
		} else if(verifier_tome(nomtome) <= 0){
			errorCreer.setText("Le tome doit être un nombre positif");
		} else if(verifier_annee(nomannee) < 1800 || verifier_annee(nomannee) >  2021){
			errorCreer.setText("L'année doit être un nombre positif");
		} else {
			Livre livre = new Livre(nomisbn, nomtitre, nomauteur, nomediteur, nomgenre);
			livre.setTome(Integer.parseInt(nomtome));
			livre.setNbre_page(Integer.parseInt(nompage));
			Calendar cal = Calendar.getInstance();
			cal.clear();
			cal.set(Calendar.YEAR, Integer.parseInt(nomannee));
			cal.set(Calendar.MONTH, 1);
			cal.set(Calendar.DATE, 1);
			Date predefined = cal.getTime();
			livre.setAnnee(predefined);
			
			DAO<Livre> livreDao = DAOFactory.getLivreDAO();
			if(livreDao.create(livre)) {
				isbn.setText("");
				titre.setText("");
				auteur.setText("");
				editeur.setText("");
				tome.setText("");
				annee.setText("");
				page.setText("");
				errorCreer.setText("Livre créé avec succès");
			} else {
				errorCreer.setText("Une erreur s'est produite, veillez vérifier l'ISBN");
			}
			
		}
	}
	
	private int verifier_tome(String code) {
		
		if(!code.isEmpty() && code.length() > 0) {
			try {
				return Integer.parseInt(code);
			} catch (NumberFormatException e){
				errorCreer.setText("Le tome doit être un nombre positif");
			}
		}
		
		return 0;
	}
	
	private int verifier_annee(String code) {
		
		if(!code.isEmpty() && code.length() > 0) {
			try {
				return Integer.parseInt(code);
			} catch (NumberFormatException e){
				errorCreer.setText("L'année doit être un nombre positif");
			}
		}
		
		return 0;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		DAO<Genre> genreDao = DAOFactory.getGenreDAO();
		genres = genreDao.find();
		genre.setItems(genres);
	}
}
