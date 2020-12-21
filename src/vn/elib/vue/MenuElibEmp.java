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
import java.util.function.Predicate;

import javax.swing.JOptionPane;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
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
import vn.elib.model.pojo.CarteMagnetique;
import vn.elib.model.pojo.Exemplaire;
import vn.elib.model.pojo.Genre;
import vn.elib.model.pojo.Livre;
import vn.elib.model.pojo.LivreEmprunte;

/**
 * @author franel
 *
 */
public class MenuElibEmp implements Initializable {

	int by;
	
	
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
    private JFXTreeTableColumn<Livre,Integer> nbreExemplaire = new JFXTreeTableColumn<Livre, Integer>("Exemplaires");
    @FXML
    private JFXTreeTableColumn<Livre,Integer> nbreDisponible = new JFXTreeTableColumn<Livre, Integer>("Disponible");
	@FXML
    private JFXTextField cherche;
    @FXML
    private JFXButton emprunterB;
    @FXML
    private JFXButton exemplaire;
    @FXML
    private Label echecemprunter;
    @FXML
    private Label succeeprunter;
    
    //les FXML de tableu livre emprunté
    @FXML
    private TableView<Abonne> tableuaAbonne;
    @FXML
    private TableColumn<Abonne,CarteMagnetique> idAbonne;
    @FXML
    private TableColumn<Abonne,String> nomAbonne;
    @FXML
    private TableColumn<Abonne,String> prenomAbonne;
    @FXML
    private TableColumn<Abonne,Character> sexeAbonne;
    @FXML
    private TableColumn<Abonne,Integer>etaAbonne;
    @FXML
    private JFXButton creerAbonne;
    @FXML
    private JFXButton activerAbonne;
    @FXML
    private JFXButton deactiverAbonne;
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
    
    private ObservableList<Livre> data = FXCollections.observableArrayList();
    private ObservableList<Abonne> dataAbonne = FXCollections.observableArrayList();
    private ObservableList<LivreEmprunte> dataEmprunte = FXCollections.observableArrayList();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		title.setText(Global.employe.getPrenom());
		
		isbn.setPrefWidth(100);
		titre.setPrefWidth(150);
		auteur.setPrefWidth(100);
		editeur.setPrefWidth(100);
		anne.setPrefWidth(70);
		nbrpage.setPrefWidth(60);
		tome.setPrefWidth(50);
		genre.setPrefWidth(100);
		nbreExemplaire.setPrefWidth(100);
		nbreDisponible.setPrefWidth(100);
		
		tableLivre();
		tableAbonne();
		tableLivreHistorique();
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
		
		nbreExemplaire.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Livre,Integer>, ObservableValue<Integer>>() {
			
			@Override
			public ObservableValue<Integer> call(CellDataFeatures<Livre, Integer> param) {
				// TODO Auto-generated method stub
				return param.getValue().getValue().getNombreExemplaire().asObject();
			}
		});
		
		nbreDisponible.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Livre,Integer>, ObservableValue<Integer>>() {
			
			@Override
			public ObservableValue<Integer> call(CellDataFeatures<Livre, Integer> param) {
				// TODO Auto-generated method stub
				return param.getValue().getValue().getNombreDisponible().asObject();
			}
		});
		
		final TreeItem<Livre> root = new RecursiveTreeItem<Livre>(data, RecursiveTreeObject::getChildren);
		tableulivre.getColumns().setAll(isbn, titre, auteur, editeur, anne, nbrpage, tome, genre, nbreExemplaire, nbreDisponible);
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
	
	public void tableAbonne(){
    	
		tableuaAbonne.getItems().clear();
    	
    	DAO<Abonne> abonneDAO = DAOFactory.getAbonneDAO();
    	dataAbonne = abonneDAO.find();
			
		idAbonne.setCellValueFactory(new PropertyValueFactory<Abonne,CarteMagnetique>("CarteMagnetique"));
		idAbonne.setCellFactory(column -> {
	        TableCell<Abonne, CarteMagnetique> cell = new TableCell<Abonne, CarteMagnetique>() {
	            @Override
	            protected void updateItem(CarteMagnetique item, boolean empty) {
	                super.updateItem(item, empty);
	                if(empty) {
	                    setText(null);
	                }
	                else {
	                	this.setText(item.getCode());
	                }
	            }
	        };

	        return cell;
	    });
		nomAbonne.setCellValueFactory(new PropertyValueFactory<Abonne,String>("nom"));
		prenomAbonne.setCellValueFactory(new PropertyValueFactory<Abonne,String>("prenom"));
		sexeAbonne.setCellValueFactory(new PropertyValueFactory<Abonne,Character>("sexe"));
		sexeAbonne.setCellFactory(column -> {
	        TableCell<Abonne, Character> cell = new TableCell<Abonne, Character>() {
	            @Override
	            protected void updateItem(Character item, boolean empty) {
	                super.updateItem(item, empty);
	                if(empty) {
	                    setText(null);
	                }
	                else {
	                	if(item.equals('M')) {
	                		this.setText("Masculin");
	                	} else {
	                		this.setText("Féminin");
	                	}
	                }
	            }
	        };

	        return cell;
	    });
		etaAbonne.setCellValueFactory(new PropertyValueFactory<Abonne,Integer>("etat"));
		etaAbonne.setCellFactory(column -> {
	        TableCell<Abonne, Integer> cell = new TableCell<Abonne, Integer>() {
	            @Override
	            protected void updateItem(Integer item, boolean empty) {
	                super.updateItem(item, empty);
	                if(empty) {
	                    setText(null);
	                }
	                else {
	                	if(item == 1) {
	                		this.setText("Actif");
	                	} else {
	                		this.setText("Inactif");
	                	}
	                }
	            }
	        };

	        return cell;
	    });
		
		tableuaAbonne.setItems(dataAbonne);
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
	                	if(item == null) {
	                		this.setText("Non remis");
	                	} else {
	                		c.setTime(item);
	                		this.setText(sdf.format(c.getTime()));
	                	}
	                }
	            }
	        };

	        return cell;
	    });
		
		tableulivreH.setItems(dataEmprunte);
    }
	
    public void display(MouseEvent e)throws IOException,SQLException {
		if(tableulivre.getSelectionModel().getSelectedItem().getValue() != null) {
	    	Livre livreSelection = tableulivre.getSelectionModel().getSelectedItem().getValue();
			if(livreSelection == null) {
				emprunterB.setDisable(true);
				exemplaire.setDisable(true);
			}
			else {
				emprunterB.setDisable(false);
				exemplaire.setDisable(false);
			}
		}
    }
    
    @FXML
    public void nouveauLivre(ActionEvent e) throws IOException{    	
    	
    	Parent root = FXMLLoader.load(getClass().getResource("CreerLivre.fxml"));
		Scene s = new Scene(root,841,584);
		Stage parentStage = (Stage) ((Node)e.getSource()).getScene().getWindow();	
    	
    	Stage dialog = new Stage();
    	dialog.setScene(s);

    	dialog.initOwner(parentStage);
    	dialog.initModality(Modality.APPLICATION_MODAL); 
    	dialog.showAndWait();
    	tableLivre();
    }
	   
	@FXML
	public void Emprunter(ActionEvent e)throws IOException,SQLException{
		//Pour supprimer un livre
		Livre livreSelection = tableulivre.getSelectionModel().getSelectedItem().getValue();
		
		if(livreSelection != null) {
			DAO<Exemplaire> exemplaireDao = DAOFactory.getExempalireDAO();
			DAO<Livre> livreDao = DAOFactory.getLivreDAO();
			for(Exemplaire exemplaire: livreSelection.getListExemplaire()) {
				exemplaireDao.delete(exemplaire);
			}
						
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erreur");
			
			echecemprunter.setText("");
			
			if(livreDao.delete(livreSelection)) {
				tableLivre();
				succeeprunter.setText("Livre supprimé avec succès");
			} else {
				alert.setContentText("Une erreur est survenue");
				alert.showAndWait();
			}
		}
		emprunterB.setDisable(true);
		exemplaire.setDisable(true);
	}
	
	@FXML
    public void Exemplaire(ActionEvent e) throws IOException{    
		Livre livreSelection = tableulivre.getSelectionModel().getSelectedItem().getValue();
		
		if(livreSelection != null) {
			FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(getClass().getResource("CreerExemplaire.fxml"));
	        AnchorPane page = (AnchorPane) loader.load();
	        
	    	//Parent root = FXMLLoader.load(loader);
			Scene s = new Scene(page,841,584);
			Stage parentStage = (Stage) ((Node)e.getSource()).getScene().getWindow();	
	    	
	    	Stage dialog = new Stage();
	    	dialog.setScene(s);
	    	dialog.setTitle("Liste des exemplaires de " + livreSelection.getTitre().get());
	    	
	    	dialog.initOwner(parentStage);
	    	dialog.initModality(Modality.APPLICATION_MODAL);
	    	
	    	CreerExemplaire controller = loader.getController();
	    	controller.setLivre(livreSelection);
	    	
	    	dialog.showAndWait();
	    	tableLivre();
		}
    }
	
	public void AbonneSelection(MouseEvent e)throws IOException,SQLException {
		Abonne abonneSelection = tableuaAbonne.getSelectionModel().getSelectedItem();
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Erreur");
		if(abonneSelection == null) {
			activerAbonne.setDisable(true);
			deactiverAbonne.setDisable(true);
		}
		else {
			if(abonneSelection.getEtat() == 1) {
				activerAbonne.setDisable(true);
				deactiverAbonne.setDisable(false);
			} else {
				activerAbonne.setDisable(false);
				deactiverAbonne.setDisable(true);
			}
		}
	}
	
	@FXML
	public void ActiverA() {
		Abonne abonneSelection = tableuaAbonne.getSelectionModel().getSelectedItem();
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Erreur");
		if(abonneSelection == null) {
			activerAbonne.setDisable(true);
			deactiverAbonne.setDisable(true);
		}
		else {
			abonneSelection.setEtat(1);
			DAO<Abonne> abonneDao = DAOFactory.getAbonneDAO();
			abonneDao.update(abonneSelection);
			tableAbonne();
		}
		activerAbonne.setDisable(true);
		deactiverAbonne.setDisable(true);
	}
	
	@FXML
	public void DeactiverA() {
		Abonne abonneSelection = tableuaAbonne.getSelectionModel().getSelectedItem();
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Erreur");
		if(abonneSelection == null) {
			activerAbonne.setDisable(true);
			deactiverAbonne.setDisable(true);
		}
		else {
			abonneSelection.setEtat(0);
			DAO<Abonne> abonneDao = DAOFactory.getAbonneDAO();
			abonneDao.update(abonneSelection);
			tableAbonne();
		}
		activerAbonne.setDisable(true);
		deactiverAbonne.setDisable(true);
	}
	
	
	@FXML
	public void deconnecter(ActionEvent e)throws IOException,SQLException{
		Global.abonne = null;
		loadAuthen(e);
	}
	
	public void loadAuthen(ActionEvent e) throws IOException{
		Parent root = FXMLLoader.load(getClass().getResource("AuthentificationEmp.fxml"));
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
	
	@FXML
    public void CreerAbonne(ActionEvent e)throws IOException,SQLException {
		Parent root = FXMLLoader.load(getClass().getResource("CreerAbonne.fxml"));
		Scene s = new Scene(root,841,584);
		Stage parentStage = (Stage) ((Node)e.getSource()).getScene().getWindow();	
    	
    	Stage dialog = new Stage();
    	dialog.setScene(s);

    	dialog.initOwner(parentStage);
    	dialog.initModality(Modality.APPLICATION_MODAL); 
    	dialog.showAndWait();
    	tableAbonne();
    }
	
	@FXML
    public void creerGenre(ActionEvent e)throws IOException,SQLException {
		String genreVal = JOptionPane.showInputDialog(null, "Nom du genre",
				"Nouveau genre", JOptionPane.PLAIN_MESSAGE);
		
		succeeprunter.setText("");
		if(genreVal != null && !genreVal.trim().equals("")) {
			DAO<Genre> genreDao = DAOFactory.getGenreDAO();
			Genre genre = new Genre(1, genreVal);
			if(genreDao.create(genre)) {
				tableLivre();
				succeeprunter.setText(genre.getNomGenre().get() + " créé avec succès");
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Erreur");
				alert.setContentText("Le genre n'a pas pu être créer");
				alert.showAndWait();
			}
		}
    }
	
	private void detLivre(ActionEvent e) throws IOException{
		Livre livreSelection = tableulivre.getSelectionModel().getSelectedItem().getValue();
		
		if(livreSelection != null) {
			FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(getClass().getResource("DetailLivre.fxml"));
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
