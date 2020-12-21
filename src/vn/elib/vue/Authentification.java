package vn.elib.vue;

import java.io.IOException;
import java.util.Calendar;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Screen;
import javafx.stage.Stage;
import vn.elib.controller.Global;
import vn.elib.model.dao.DAO;
import vn.elib.model.dao.DAOFactory;
import vn.elib.model.pojo.Abonne;

public class Authentification {
	
	@FXML
	private Label messagec;
	@FXML
	private JFXTextField pseudoc;
	@FXML
	private JFXPasswordField motpassc;

	public void Connecter(ActionEvent e) throws IOException {
		messagec.setText("");
		
		DAO<Abonne> abonneDao = DAOFactory.getAbonneDAO();
		
		int code = verifier_code_carte(pseudoc.getText());
		if( code > 0) {
			Abonne abonne = abonneDao.find(code);
			if(abonne.getCarteMagnetique().getCodePin().toString().equals(motpassc.getText()) &&
				Calendar.getInstance().getTime().before(abonne.getCarteMagnetique().getValidide())) {
				Global.abonne = abonne;
				loadMenuElib(e);
			} else {
				messagec.setText("votre code carte et/ou votre code PIN est incorrect");
			}
		}
	}
	
	public void loadMenuElib(ActionEvent e) throws IOException{
		Parent root = FXMLLoader.load(getClass().getResource("MenuElib.fxml"));
		Scene s = new Scene(root,1070,590);
		Stage fenetre = (Stage) ((Node)e.getSource()).getScene().getWindow();
		fenetre.setScene(s);
		fenetre.setResizable(false);
		Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
		fenetre.setX((primScreenBounds.getWidth() - fenetre.getWidth()) / 2); 
		fenetre.setY((primScreenBounds.getHeight() - fenetre.getHeight()) / 4);  
		fenetre.show();		
	}
	
	private int verifier_code_carte(String code) {
		
		if(!code.isEmpty() && code.length() == 8) {
			try {
				return Integer.parseInt(code);
			} catch (NumberFormatException e){
				messagec.setText("Le code de votre carte est incorrect");
			}
		} else {
			messagec.setText("Le code de votre carte est incorrect");
		}
		
		return 0;
	}
}
