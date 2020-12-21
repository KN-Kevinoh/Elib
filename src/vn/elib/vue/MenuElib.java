/**
 * 
 */
package vn.elib.vue;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import vn.elib.controller.Global;
import vn.elib.model.dao.DAO;
import vn.elib.model.dao.DAOFactory;
import vn.elib.model.pojo.Abonne;
import vn.elib.model.pojo.Emprunt;
import vn.elib.model.pojo.Exemplaire;
import vn.elib.model.pojo.Livre;
import vn.elib.model.pojo.LivreEmprunte;

/**
 * @author franel
 *
 */
public class MenuElib implements Initializable {
	
	//les FXML de tableu livre
	@FXML
    private JFXTreeTableView<Livre> tableulivre;
    @FXML
    private JFXTreeTableColumn<Livre,String> isbn = new JFXTreeTableColumn<Livre, String>("ISBN");
    @FXML
    private JFXTreeTableColumn<Livre,String> titre  = new JFXTreeTableColumn<Livre, String>("Titre");
    @FXML
    private JFXTreeTableColumn<Livre,String> auteur = new JFXTreeTableColumn<Livre, String>("Auteur");
    @FXML
    private JFXTreeTableColumn<Livre,String> editeur = new JFXTreeTableColumn<Livre, String>("Editeur");
    @FXML
    private JFXTreeTableColumn<Livre,String> anne = new JFXTreeTableColumn<Livre, String>("Année");
    @FXML
    private JFXTreeTableColumn<Livre,Integer> nbrpage = new JFXTreeTableColumn<Livre, Integer>("Pages");
    @FXML
    private JFXTreeTableColumn<Livre,Integer> tome = new JFXTreeTableColumn<Livre, Integer>("Tome");
    @FXML
    private JFXTreeTableColumn<Livre,String> genre = new JFXTreeTableColumn<Livre, String>("Genre");

	@FXML
    private TextField cherche;
    @FXML
    private JFXButton emprunterB;
    @FXML
    private Label echecemprunter;
    @FXML
    private Label succeeprunter;
    
    //les FXML de tableu livre emprunté
    @FXML
    private TableView<LivreEmprunte> tableulivreE;
    @FXML
    private TableColumn<LivreEmprunte,String> isbnE;
    @FXML
    private TableColumn<LivreEmprunte,String> rfidE;
    @FXML
    private TableColumn<LivreEmprunte,String> titreE;
    @FXML
    private TableColumn<LivreEmprunte,String> auteurE;
    @FXML
    private TableColumn<LivreEmprunte,String>editeurE;
    @FXML
    private TableColumn<LivreEmprunte,Integer> nbrpageE;
    @FXML
    private TableColumn<LivreEmprunte,String> tomeE;
    @FXML
    private TableColumn<LivreEmprunte,Date> date_empruntE;
    @FXML
    private TableColumn<LivreEmprunte,Date> delaisE;
    @FXML
    private JFXButton remettreE;
    @FXML
    private Label echecRemettre;
    @FXML
    private Label succeeRemetre;
    
  //les FXML de tableu livre Historique
    @FXML
    private TableView<LivreEmprunte> tableulivreH;
    @FXML
    private TableColumn<LivreEmprunte,String> isbnH;
    @FXML
    private TableColumn<LivreEmprunte,String> titreH;
    @FXML
    private TableColumn<LivreEmprunte,String> auteurH;
    @FXML
    private TableColumn<LivreEmprunte,String>editeurH;
    @FXML
    private TableColumn<LivreEmprunte,Date> anneH;
    @FXML
    private TableColumn<LivreEmprunte,Integer> nbrpageH;
    @FXML
    private TableColumn<LivreEmprunte,String> tomeH;
    @FXML
    private TableColumn<LivreEmprunte,Date> date_empruntH;
    @FXML
    private TableColumn<LivreEmprunte,Date> date_remisH;
    
    @FXML
    private Label title;
    
    @FXML
    private Label suspendu;
    
    private ObservableList<Livre> data = FXCollections.observableArrayList();
    private ObservableList<LivreEmprunte> dataEmprunte = FXCollections.observableArrayList();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		title.setText(Global.abonne.getPrenom());
		
		isbn.setPrefWidth(100);
		titre.setPrefWidth(150);
		auteur.setPrefWidth(100);
		editeur.setPrefWidth(100);
		anne.setPrefWidth(70);
		nbrpage.setPrefWidth(60);
		tome.setPrefWidth(50);
		genre.setPrefWidth(100);
		
		tableLivreEmprunt();
		tableLivreHistorique();
		if(Global.abonne.getEtat() == 1) {
			tableLivre();
			suspendu.setVisible(false);
		} else {
			suspendu.setVisible(true);
		}
	}
		
	public void  tableLivre(){
    	
		tableulivre.getColumns().clear();
    	DAO<Livre> livreDao = DAOFactory.getLivreDAO();
		data = livreDao.find();
		
		
		isbn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Livre,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Livre, String> param) {
				// TODO Auto-generated method stub
				return param.getValue().getValue().getId();
			}
		});
		
		titre.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Livre,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Livre, String> param) {
				// TODO Auto-generated method stub
				return param.getValue().getValue().getTitre();
			}
		});
		
		auteur.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Livre,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Livre, String> param) {
				// TODO Auto-generated method stub
				return param.getValue().getValue().getAuteur();
			}
		});
		
		editeur.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Livre,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Livre, String> param) {
				// TODO Auto-generated method stub
				return param.getValue().getValue().getEditeur();
			}
		});
		
		anne.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Livre,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Livre, String> param) {
				// TODO Auto-generated method stub
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	            Calendar c = Calendar.getInstance();
	            c.setTime(param.getValue().getValue().getAnnee());
				return new SimpleStringProperty(sdf.format(c.getTime()).split("/")[2]);
			}
		});
		
		nbrpage.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Livre,Integer>, ObservableValue<Integer>>() {
			
			@Override
			public ObservableValue<Integer> call(CellDataFeatures<Livre, Integer> param) {
				// TODO Auto-generated method stub
				return param.getValue().getValue().getNbre_page().asObject();
			}
		});
		
		tome.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Livre,Integer>, ObservableValue<Integer>>() {
			
			@Override
			public ObservableValue<Integer> call(CellDataFeatures<Livre, Integer> param) {
				// TODO Auto-generated method stub
				return param.getValue().getValue().getTome().asObject();
			}
		});
		
		genre.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Livre,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Livre, String> param) {
				// TODO Auto-generated method stub
				return param.getValue().getValue().getGenre().getNomGenre();
			}
		});
		
		final TreeItem<Livre> root = new RecursiveTreeItem<Livre>(data, RecursiveTreeObject::getChildren);
		tableulivre.getColumns().setAll(isbn, titre, auteur, editeur, anne, nbrpage, tome, genre);
		tableulivre.setRoot(root);
		tableulivre.setShowRoot(false);
		
		cherche.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldV, String newV) {
				// TODO Auto-generated method stub
				tableulivre.setPredicate(new Predicate<TreeItem<Livre>>() {
					
					@Override
					public boolean test(TreeItem<Livre> t) {
						// TODO Auto-generated method stub
						Boolean flag = t.getValue().getId().getValue().contains(newV)
								|| t.getValue().getTitre().getValue().contains(newV)
								|| t.getValue().getAuteur().getValue().contains(newV)
								|| t.getValue().getEditeur().getValue().contains(newV)
								|| t.getValue().getGenre().getNomGenre().getValue().contains(newV);
						return flag;
					}
				});
			}
			
		});
    }
	
	public void tableLivreEmprunt(){
    	
    	tableulivreE.getItems().clear();
    	
    	DAO<LivreEmprunte> livreEmprunte = DAOFactory.getLivreEmprunteDAO();
    	dataEmprunte = livreEmprunte.find();
    	
    	/////////////////////////////////////////////
    	
    	for(LivreEmprunte l_e : dataEmprunte) {
    		long diff = Calendar.getInstance().getTime().getTime() - l_e.getDate_emprunt().getTime();
    		long jour = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    		
    		if(jour > 45 && l_e.getDate_remise() == null && Global.abonne.getEtat() == 1) {
    			Global.abonne.setEtat(0);
    			DAO<Abonne> abonneDao = DAOFactory.getAbonneDAO();
    			abonneDao.update(Global.abonne);
    		}
    	}
    	
    	////////////////////////////////////////////
			
		isbnE.setCellValueFactory(new PropertyValueFactory<LivreEmprunte,String>("isbn"));
		rfidE.setCellValueFactory(new PropertyValueFactory<LivreEmprunte,String>("rfid"));
		titreE.setCellValueFactory(new PropertyValueFactory<LivreEmprunte,String>("titre"));
		auteurE.setCellValueFactory(new PropertyValueFactory<LivreEmprunte,String>("auteur"));
		editeurE.setCellValueFactory(new PropertyValueFactory<LivreEmprunte,String>("editeur"));
		tomeE.setCellValueFactory(new PropertyValueFactory<LivreEmprunte,String>("tome"));
		nbrpageE.setCellValueFactory(new PropertyValueFactory<LivreEmprunte,Integer>("nbre_page"));
	    date_empruntE.setCellValueFactory(new PropertyValueFactory<LivreEmprunte,Date>("date_emprunt"));
	    date_empruntE.setCellFactory(column -> {
	        TableCell<LivreEmprunte, Date> cell = new TableCell<LivreEmprunte, Date>() {
	        	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	            
	            Calendar c = Calendar.getInstance();

	            @Override
	            protected void updateItem(Date item, boolean empty) {
	                super.updateItem(item, empty);
	                if(empty) {
	                    setText(null);
	                }
	                else {
	                	c.setTime(item);
	                    this.setText(sdf.format(c.getTime()));

	                }
	            }
	        };

	        return cell;
	    });
	    delaisE.setCellValueFactory(new PropertyValueFactory<LivreEmprunte,Date>("delais"));
	    delaisE.setCellFactory(column -> {
	        TableCell<LivreEmprunte, Date> cell = new TableCell<LivreEmprunte, Date>() {
	        	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	            
	            Calendar c = Calendar.getInstance();

	            @Override
	            protected void updateItem(Date item, boolean empty) {
	                super.updateItem(item, empty);
	                if(empty) {
	                    setText(null);
	                }
	                else {
	                	c.setTime(item);
	                    this.setText(sdf.format(c.getTime()));

	                }
	            }
	        };

	        return cell;
	    });
		
		tableulivreE.setItems(dataEmprunte.filtered(t -> t.getDate_remise() == null));
    }
	
	public void  tableLivreHistorique(){
    	
    	tableulivreH.getItems().clear();
    	
    	DAO<LivreEmprunte> livreEmprunte = DAOFactory.getLivreEmprunteDAO();
    	dataEmprunte = livreEmprunte.find();
    	
    	
			
		isbnH.setCellValueFactory(new PropertyValueFactory<LivreEmprunte,String>("isbn"));
		titreH.setCellValueFactory(new PropertyValueFactory<LivreEmprunte,String>("titre"));
		auteurH.setCellValueFactory(new PropertyValueFactory<LivreEmprunte,String>("auteur"));
		editeurH.setCellValueFactory(new PropertyValueFactory<LivreEmprunte,String>("editeur"));
		tomeH.setCellValueFactory(new PropertyValueFactory<LivreEmprunte,String>("tome"));
		nbrpageH.setCellValueFactory(new PropertyValueFactory<LivreEmprunte,Integer>("nbre_page"));
		date_empruntH.setCellValueFactory(new PropertyValueFactory<LivreEmprunte,Date>("date_emprunt"));
		date_empruntH.setCellFactory(column -> {
	        TableCell<LivreEmprunte, Date> cell = new TableCell<LivreEmprunte, Date>() {
	        	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	            
	            Calendar c = Calendar.getInstance();

	            @Override
	            protected void updateItem(Date item, boolean empty) {
	                super.updateItem(item, empty);
	                if(empty) {
	                    setText(null);
	                }
	                else {
	                	c.setTime(item);
	                    this.setText(sdf.format(c.getTime()));

	                }
	            }
	        };

	        return cell;
	    });
		date_remisH.setCellValueFactory(new PropertyValueFactory<LivreEmprunte,Date>("date_remise"));
		date_remisH.setCellFactory(column -> {
	        TableCell<LivreEmprunte, Date> cell = new TableCell<LivreEmprunte, Date>() {
	        	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	            
	            Calendar c = Calendar.getInstance();

	            @Override
	            protected void updateItem(Date item, boolean empty) {
	                super.updateItem(item, empty);
	                if(empty) {
	                    setText(null);
	                }
	                else {
	                	c.setTime(item);
	                    this.setText(sdf.format(c.getTime()));

	                }
	            }
	        };

	        return cell;
	    });
		
		tableulivreH.setItems(dataEmprunte.filtered(t -> t.getDate_remise() != null));
    }
	
    public void display(MouseEvent e)throws IOException,SQLException {
		Livre livreSelection = tableulivre.getSelectionModel().getSelectedItem().getValue();
		if(livreSelection == null) {
			emprunterB.setDisable(true);
		}
		else {
			emprunterB.setDisable(false);
		}
    }
	   
	@FXML
	public void Emprunter(ActionEvent e)throws IOException,SQLException{
		Livre livreSelection = tableulivre.getSelectionModel().getSelectedItem().getValue();
		
		Exemplaire exemplaire = livreSelection.getOneDisponible();
		
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Erreur");
		
		echecemprunter.setText("");
		
		if(exemplaire != null) {
			if (!livreSelection.estDisponible()){
				alert.setContentText("Aucun exemplaire disponible pour ce livre");
				alert.showAndWait();
			} else if(Global.abonne.getEmpruntEnCour().size() >= Global.MaxPret) {
				emprunterB.setDisable(true);
				alert.setContentText("nombre maximal de livre emprunté");
				alert.showAndWait();
			} else {
				DAO<Emprunt> empruntDao = DAOFactory.getEmpruntDAO();
				Emprunt emprunt = new Emprunt(Global.abonne, exemplaire, Calendar.getInstance().getTime(), null);
				if(empruntDao.create(emprunt)) {
					succeeprunter.setText("Vous avez emprunter "+livreSelection.getTitre().get());
					echecemprunter.setVisible(false);
				} else {
					echecemprunter.setText("Désole! il ya une erreur.");
					succeeprunter.setVisible(false);
				}
				
				DAO<Abonne> abonneDao = DAOFactory.getAbonneDAO();
				Global.abonne = abonneDao.find(Integer.parseInt(Global.abonne.getCarteMagnetique().getCode()));
				emprunterB.setDisable(true);
				DAO<LivreEmprunte> livreEmprunte = DAOFactory.getLivreEmprunteDAO();
				dataEmprunte = livreEmprunte.find();
		    	tableulivreE.setItems(dataEmprunte.filtered(t -> t.getDate_remise() == null));
				tableLivre();
		    	emprunterB.setDisable(true);
			}
		} else {
			alert.setContentText("Aucun exemplaire disponible pour ce livre");
			alert.showAndWait();
		}
	}
	
	public void EmpruntSelection(MouseEvent e)throws IOException,SQLException {
		LivreEmprunte livreSelection = tableulivreE.getSelectionModel().getSelectedItem();
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Erreur");
		if(livreSelection == null) {
			remettreE.setDisable(true);
		}
		else {
			remettreE.setDisable(false);
		}
	}
	
	@FXML
	public void Remettre() {
		LivreEmprunte livreSelection = tableulivreE.getSelectionModel().getSelectedItem();
		if(livreSelection != null) {
			DAO<Emprunt> empruntDao = DAOFactory.getEmpruntDAO();
			Emprunt emprunt = empruntDao.find(livreSelection.getId_emprunt());
			
			if(empruntDao.update(emprunt)) {
				succeeRemetre.setText("Vous avez remis le livre "+livreSelection.getTitre());
				echecRemettre.setVisible(false);
			} else {
				echecRemettre.setText("Désole! il ya une erreur.");
				succeeRemetre.setVisible(false);
			}
			
			DAO<Abonne> abonneDao = DAOFactory.getAbonneDAO();
			Global.abonne = abonneDao.find(Integer.parseInt(Global.abonne.getCarteMagnetique().getCode()));
			DAO<LivreEmprunte> livreEmprunte = DAOFactory.getLivreEmprunteDAO();
			dataEmprunte = livreEmprunte.find();
	    	tableulivreE.setItems(dataEmprunte.filtered(t -> t.getDate_remise() == null));
	    	tableulivreH.setItems(dataEmprunte.filtered(t -> t.getDate_remise() != null));
	    	tableLivre();
		}
		remettreE.setDisable(true);
	}
	
	@FXML
	public void deconnecter(ActionEvent e)throws IOException,SQLException{
		Global.abonne = null;
		loadAuthen(e);
	}
	
	public void loadAuthen(ActionEvent e) throws IOException{
		Parent root = FXMLLoader.load(getClass().getResource("Authentification.fxml"));
		Scene s = new Scene(root,1070,590);
		Stage fenetre = (Stage) ((Node)e.getSource()).getScene().getWindow();
		fenetre.setScene(s);
		fenetre.setResizable(false);
        fenetre.show();		
	}
		
	@FXML
    public void afficheDetLivre(ActionEvent e)throws IOException,SQLException {
		//DocumentBD.getConnection();
		detLivre(e);
    }
	
	private void detLivre(ActionEvent e) throws IOException{
		Livre livreSelection = tableulivre.getSelectionModel().getSelectedItem().getValue();
		
		if(livreSelection != null) {
			FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(getClass().getResource("DetailLivre2.fxml"));
	        AnchorPane page = (AnchorPane) loader.load();
	        
	    	//Parent root = FXMLLoader.load(loader);
			Scene s = new Scene(page,841,584);
			Stage parentStage = (Stage) ((Node)e.getSource()).getScene().getWindow();	
	    	
	    	Stage dialog = new Stage();
	    	dialog.setScene(s);
	    	dialog.setTitle(livreSelection.getTitre().get());
	    	
	    	dialog.initOwner(parentStage);
	    	dialog.initModality(Modality.APPLICATION_MODAL);
	    	
	    	DetailLivre controller = loader.getController();
	    	controller.setLivre(livreSelection);
	    	
	    	dialog.showAndWait();
		}
	}
}
