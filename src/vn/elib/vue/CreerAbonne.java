package vn.elib.vue;

import java.net.URL;
import java.util.Date;
import java.util.GregorianCalendar;
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
import vn.elib.model.pojo.Abonne;
import vn.elib.model.pojo.CarteMagnetique;

public class CreerAbonne implements Initializable {
	@FXML
    private JFXTextField code;
	@FXML
    private JFXTextField codePin;
	@FXML
    private JFXTextField nom;
	@FXML
    private JFXTextField prenom;
	@FXML
    private JFXComboBox<String> sexe;
	@FXML
    private Label errorCreer;
	
	private ObservableList<String> sexes = FXCollections.observableArrayList();
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		sexes.add("Masculin");
		sexes.add("Feminin");
		sexe.setItems(sexes);
	}
	
	@FXML
	public void valider() {
		String cod = code.getText();
		String codPin = codePin.getText();
		String nomm = nom.getText();
		String prenomm = prenom.getText();
		String sex = sexe.getSelectionModel().getSelectedItem();
		
		errorCreer.setText("");
		
		if(cod.isEmpty() || codPin.isEmpty() || nomm.isEmpty() || prenomm.isEmpty() || sex == null) {
			errorCreer.setText("Veillez remplir tous les champs du formulaire");
		} else {
			
			if(verifier_carte(cod) > 0 && verifier_codePin(codPin) > 0) {
				Abonne abonne = new Abonne();
				GregorianCalendar calStr1 = new GregorianCalendar();
				calStr1.setTime(new Date());
				calStr1.add(GregorianCalendar.YEAR, +1);
				CarteMagnetique c = new CarteMagnetique(cod, Integer.parseInt(codPin), calStr1.getTime());
				
				
				abonne.setCarteMagnetique(c);
				abonne.setNom(nomm);
				abonne.setPrenom(prenomm);
				abonne.setSexe(sex.charAt(0));
				
				DAO<Abonne> abonneDao = DAOFactory.getAbonneDAO();
				if(abonneDao.create(abonne)) {
					code.setText("");
					codePin.setText("");
					nom.setText("");
					prenom.setText("");
					sexe.getSelectionModel().clearSelection();
					errorCreer.setText("Abonné créé avec succès");
				} else {
					errorCreer.setText("Une erreur s'est produite, veillez contacter l'Administrateur");
				}
			}
		}
	}
	
	private int verifier_carte(String code) {
		
		if(!code.isEmpty() && code.length() == 8) {
			try {
				return Integer.parseInt(code);
			} catch (NumberFormatException e){
				errorCreer.setText("Le code doit être 8 caractère numériques");
			}
		} else {
			errorCreer.setText("Le code doit être 8 caractère numériques");
		}
		
		return 0;
	}
	
private int verifier_codePin(String code) {
		
		if(!code.isEmpty() && code.length() > 0) {
			try {
				return Integer.parseInt(code);
			} catch (NumberFormatException e){
				errorCreer.setText("Le code PIN doit être des caractère numériques");
			}
		} else {
			errorCreer.setText("Le code PIN doit être des caractère numériques");
		}
		
		return 0;
	}
}
