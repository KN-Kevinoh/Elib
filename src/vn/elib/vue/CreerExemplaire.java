package vn.elib.vue;

import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import vn.elib.controller.Global;
import vn.elib.model.dao.DAO;
import vn.elib.model.dao.DAOFactory;
import vn.elib.model.pojo.Exemplaire;
import vn.elib.model.pojo.Livre;
import vn.elib.model.pojo.Rfid;

public class CreerExemplaire {
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
	
	@FXML
    private TableView<Exemplaire> exemplaireTableau;
	@FXML
    private TableColumn<Exemplaire,Rfid> rfid;
	@FXML
    private TableColumn<Exemplaire,String> dispo;
	
	@FXML
    private JFXButton supprimerB;
	
	private Livre livre;	
	Stage primaryStage;
	
	private void tableau() {
		exemplaireTableau.getItems().clear();
		
		rfid.setCellValueFactory(new PropertyValueFactory<Exemplaire,Rfid>("rfid"));
		rfid.setCellFactory(column -> {
	        TableCell<Exemplaire, Rfid> cell = new TableCell<Exemplaire, Rfid>() {

	            @Override
	            protected void updateItem(Rfid item, boolean empty) {
	                super.updateItem(item, empty);
	                if(empty) {
	                    setText(null);
	                }
	                else {
	                    this.setText(item.getCodeRFID());
	                }
	            }
	        };

	        return cell;
	    });
		dispo.setCellValueFactory(new PropertyValueFactory<Exemplaire,String>("Disponible"));
		
		ObservableList<Exemplaire> data = FXCollections.observableArrayList(livre.getListExemplaire());
		
		exemplaireTableau.setItems(data);
	}
	
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
		
		tableau();
	}
	
	@FXML
	public void valider() {
		String rfidVal = JOptionPane.showInputDialog(null, "code RFID",
				"RFID", JOptionPane.PLAIN_MESSAGE);
		
		if(rfidVal != null && !rfidVal.trim().equals("")) {
			DAO<Exemplaire> exemplaireDao = DAOFactory.getExempalireDAO();
			Rfid rfid = new Rfid(rfidVal);
			Exemplaire exemplaire = new Exemplaire(1, rfid, true);
			//Ceci à retirer absolument
			Global.livreExp = livre;
			if(exemplaireDao.create(exemplaire)) {
				livre.addExemplaire(exemplaire);
				ObservableList<Exemplaire> data = FXCollections.observableArrayList(livre.getListExemplaire());
				exemplaireTableau.setItems(data);
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Erreur");
				alert.setContentText("Code RFID indisponible, veillez changer");
				alert.showAndWait();
			}
		}
	}
	
	public void display(MouseEvent e)throws IOException,SQLException {
		Exemplaire exemplaireSelection = exemplaireTableau.getSelectionModel().getSelectedItem();
		if(exemplaireSelection == null) {
			supprimerB.setDisable(true);
		}
		else {
			supprimerB.setDisable(false);
		}
    }
	
	@FXML
	public void supprimer() {
		//Pour supprimer un exemplaire
		Exemplaire exempalireSelection = exemplaireTableau.getSelectionModel().getSelectedItem();
		
		if(exempalireSelection != null) {
			DAO<Exemplaire> exemplaireDao = DAOFactory.getExempalireDAO();
			
			if(exempalireSelection.getDisponible()) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Erreur de suppression");
				if(exemplaireDao.delete(exempalireSelection)) {
					livre.removeExemplaire(exempalireSelection);
					ObservableList<Exemplaire> data = FXCollections.observableArrayList(livre.getListExemplaire());
					exemplaireTableau.setItems(data);
				} else {
					alert.setContentText("Une erreur est survenue");
					alert.showAndWait();
				}
			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Attention");
				alert.setContentText("Cet exemplaire est actuellement prêté");
				alert.showAndWait();
			}
		}
		supprimerB.setDisable(true);
	}
}
