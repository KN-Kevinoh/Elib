package vn.elib.vue;


import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import vn.elib.model.pojo.Livre;

public class DetailLivre {
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
    private JFXTextField genre;
	@FXML
    private JFXTextField page;
	
	private Livre livre;	
	Stage primaryStage;
	
	public void setLivre(Livre l) {
		this.livre = l;
		
		isbn.setText(livre.getId().get());
		titre.setText(livre.getTitre().get());
		auteur.setText(livre.getAuteur().get());
		editeur.setText(livre.getEditeur().get());
		tome.setText(String.valueOf(livre.getTome().get()));
		annee.setText(livre.getAnnee().toString().split("-")[0]);
		genre.setText(livre.getGenre().getNomGenre().get());
		genre.setEditable(false);
		page.setText(String.valueOf(livre.getNbre_page().get()));
	}
}
