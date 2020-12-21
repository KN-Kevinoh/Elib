package vn.elib.vue;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import vn.elib.model.pojo.Livre;

public class DetailLivreEmp {
	@FXML
    private TableView<Livre> tableulivreDet;

    @FXML
    private TableColumn<Livre, String> isbndet;

    @FXML
    private TableColumn<Livre, String> titredet;

    @FXML
    private TableColumn<Livre, String> editeurdet;

    @FXML
    private TableColumn<Livre, Integer> annedet;

    @FXML
    private TableColumn<Livre, Integer> nbrpagedet;

    @FXML
    private TableColumn<Livre, String> typedet;

    @FXML
    private TableColumn<Livre, String> tomedet;

    @FXML
    private TableColumn<Livre, Integer> nbrdispodet;

    @FXML
    private TableColumn<Livre, String> auteur1detl;

    @FXML
    private TableColumn<Livre, String> auteur2detl;

    @FXML
    private TableColumn<Livre, String> auteur3detl;

    @FXML
    private TableColumn<Livre, String> auteur4detl;
   
    
    
	 public ObservableList<Livre> data = FXCollections.observableArrayList();

    
    
	public void initialize(URL location, ResourceBundle resources){
	
    	tableLivre();
    	
	}
	public void tableLivre() {
		

		
		isbndet.setCellValueFactory(new PropertyValueFactory<Livre,String>("ISBN"));
		titredet.setCellValueFactory(new PropertyValueFactory<Livre,String>("Titre"));
		editeurdet.setCellValueFactory(new PropertyValueFactory<Livre,String>("editeur"));
		annedet.setCellValueFactory(new PropertyValueFactory<Livre,Integer>("Annee"));
		nbrpagedet.setCellValueFactory(new PropertyValueFactory<Livre,Integer>("Nbrpage"));
		typedet.setCellValueFactory(new PropertyValueFactory<Livre,String>("Type"));
		tomedet.setCellValueFactory(new PropertyValueFactory<Livre,String>("tome"));
		nbrdispodet.setCellValueFactory(new PropertyValueFactory<Livre,Integer>("nombreExemplaire"));
		auteur1detl.setCellValueFactory(new PropertyValueFactory<Livre,String>("auteur1"));
		auteur2detl.setCellValueFactory(new PropertyValueFactory<Livre,String>("auteur2"));
		auteur3detl.setCellValueFactory(new PropertyValueFactory<Livre,String>("auteur3"));
		auteur4detl.setCellValueFactory(new PropertyValueFactory<Livre,String>("auteur4"));
		
		tableulivreDet.setItems(data);
	}
}
