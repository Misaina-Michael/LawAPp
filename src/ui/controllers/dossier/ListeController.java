/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers.dossier;


import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modeles.BaseModele;
import modeles.dossiers.DossierLibelle;
import modeles.facturation.FactureLibelle;
import modeles.intervenants.IntervDossierLibelle;
import modeles.intervenants.Intervenant;
import modeles.parametres.Nature;
import services.BaseService;
import services.DossierService;
import statiques.ObjetStatique;
import statiques.StageStatique;
import utilitaire.ComboBoxUtil;
import utilitaire.ReportUtil;
import utilitaire.Util;

/**
 * FXML Controller class
 *
 * @author Misaina
 */
public class ListeController implements Initializable {

    @FXML TableView liste;
    @FXML TableColumn colNumero;
    @FXML TableColumn colClient;
    @FXML TableColumn colDate;
    @FXML TableColumn colNature;
    @FXML TableColumn colGestionnaire;
    @FXML ComboBox searchGestionnaire;
    @FXML ComboBox searchNature;
    @FXML DatePicker searchDate;
    @FXML TextField searchNomDossier;
    @FXML Label nbLigne;
    @FXML TextField searchLieu;
 
    @FXML Button print;
    
    private Stage currentStage;

    public Stage getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(Stage currentStage) {
        this.currentStage = currentStage;
    }
    
    
    private List<DossierLibelle> currListe;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        try
        {
            Stage stageTemp=new Stage();
            stageTemp=StageStatique.getStage1();
            System.out.println("stagetemp "+stageTemp);
            System.out.println("static "+StageStatique.getStage1());
            initPrim();
            initSearch();
            setCurrentStage(stageTemp);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }    
    public void initPrim()
    {
       BaseService ds=null;
       List<DossierLibelle> liste=null;
        try {
       
            ds=new BaseService();
//            liste=ds.findDossierLib(new DossierLibelle());
            liste=(List<DossierLibelle>)(List<?>)ds.find(new DossierLibelle());
            nbLigne.setText(liste.size()+" lignes");
            initializeListe(liste);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void initSearch()
    {
        BaseService bs=null;
        ComboBoxUtil cbu=new ComboBoxUtil();
        List<BaseModele> intervs=null;
        Intervenant intervTous=null;
        List<BaseModele> naturesData=null;
        try
        {
            bs=new BaseService();
            Intervenant gest=new Intervenant();
            gest.setGestionnaire(true);
            intervs=bs.find(gest);
            intervTous =new Intervenant();
            intervTous.setId(0);
            intervTous.setNom("Tous");
            intervTous.setPrenom("");
            intervs.add(0, intervTous);
            cbu.fillComboBox(searchGestionnaire, intervs, 0, new String[]{"nom","prenom"});
            naturesData=new ArrayList<BaseModele>(ObjetStatique.getNatures().size()+1);
            Nature n=new Nature();
            n.setLibelle("Tous");
            n.setId(0);
            naturesData.add(0, n);    
            naturesData.addAll((List<BaseModele>)(List<?>)ObjetStatique.getNatures());
            cbu.fillComboBox(searchNature, naturesData, 0, new String[]{"libelle"});
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void initializeListe(List<DossierLibelle> dataListe)
    {
        
        colNumero.setCellValueFactory(
            new PropertyValueFactory<DossierLibelle, String>("numeroDossier"));
        colClient.setCellValueFactory(
            new PropertyValueFactory<DossierLibelle, String>("vnomDossier"));
        colDate.setCellValueFactory(
            new PropertyValueFactory<DossierLibelle, String>("dateOuverture"));
        colNature.setCellValueFactory(
            new PropertyValueFactory<DossierLibelle, String>("nature"));
        colGestionnaire.setCellValueFactory(
            new PropertyValueFactory<DossierLibelle, String>("nomGestionnaire"));
        
        ObservableList<DossierLibelle> data;
        data =FXCollections.observableArrayList();
        data.setAll(dataListe);
        
        liste.setItems(data);
        colDate.setCellFactory(column -> {
            return new TableCell<FactureLibelle,Date>() {
                protected void updateItem(Date item, boolean empty) {
                    super.updateItem(item, empty);
                    if(item!=null){

                        setText(new SimpleDateFormat("dd/MM/yyyy").format(item));
                    }
                }
            };
        });
        setCurrListe(dataListe);
    }
    
    public void createDossier(ActionEvent ae)
    {
        Stage s=null;
        try
        {
            s=new Stage();
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/ui/fxml/dossier/Formulaire.fxml"));
            Parent root = (AnchorPane)loader.load();
            FormulaireController formCtrl=loader.<FormulaireController>getController();
            DossierLibelle dl=new DossierLibelle();
            dl.initValues();
            formCtrl.setDossierLib(dl);
            formCtrl.setListeIntervenantDos(new ArrayList<IntervDossierLibelle>());
            formCtrl.initializeChamp();
            formCtrl.setPrecCtrl(this);
            Scene scene = new Scene(root);
            s.setScene(scene);
            formCtrl.setStage(s);
            
            formCtrl.setCurrentStage(getCurrentStage());
            StageStatique.setStage2(s);
            s.initOwner(StageStatique.getStage1());
            s.initModality(Modality.WINDOW_MODAL); 
            s.showAndWait();
            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void viewDossier(MouseEvent me)
    {
        Stage s=null;
      
        DossierLibelle dl=null;
        try
        {
            if (me.isPrimaryButtonDown() && me.getClickCount() == 2) {
                dl=((DossierLibelle)liste.getSelectionModel().getSelectedItem());
                s=new Stage();
                
                
                FXMLLoader loader=new FXMLLoader(getClass().getResource("/ui/fxml/dossier/PanneauDossier.fxml"));
                Parent root = (Pane)loader.load();
                PanneauDossierController panCtrl=loader.<PanneauDossierController>getController();

                panCtrl.setDossierLib(dl);
//                panCtrl.getNomDossier().setText((dl.getNomClient()+" # "+dl.getNomAdversaire()+" "+dl.getNumeroDossier()).toUpperCase());
                panCtrl.getNomDossier().setText("N° "+dl.getNumeroDossier()+ " : "+dl.getVnomDossier().toUpperCase());
                panCtrl.viewFicheFunction();
                
                Scene sc=new Scene(root);
                
                panCtrl.setCurrentStage(currentStage);
                s.setScene(sc);
                StageStatique.setStage2(s);
                s.initOwner(StageStatique.getStage1());
                s.initModality(Modality.WINDOW_MODAL); 
                s.showAndWait();
                
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void search(ActionEvent ae)
    {
        Util util=new Util();
        try {
       
            BaseService ds=new BaseService();
            DossierLibelle dl=new DossierLibelle();
            if(searchNomDossier.getText()!="") dl.setVnomDossier(searchNomDossier.getText());
            if(((Intervenant)searchGestionnaire.getSelectionModel().getSelectedItem()).getId()!=0)
                dl.setIdGestionnaire(((Intervenant)searchGestionnaire.getSelectionModel().getSelectedItem()).getId());
            if(((Nature)searchNature.getSelectionModel().getSelectedItem()).getId()!=0)
                dl.setIdNature(((Nature)searchNature.getSelectionModel().getSelectedItem()).getId());
            if(searchLieu.getText()!="") dl.setLieu(searchLieu.getText());
            if(searchDate.getValue()!=null)dl.setDateOuverture(util.datePickerToDate(searchDate));
            
            List<DossierLibelle> liste=(List<DossierLibelle>)(List<?>)ds.find(dl);
            nbLigne.setText(liste.size()+" lignes");
            initializeListe(liste);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void refresh(ActionEvent event)
    {
        try{
            initPrim();
            initSearch();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
  
    public void print(ActionEvent event)
    {
        DossierService dossSrv=null;
        ReportUtil reportUtil=new ReportUtil();
        try{
           dossSrv=new DossierService();
           Map map=dossSrv.produceMapDossierLib("Dossiers", currListe);
           String pathReport=ReportUtil.pathDocs+"/report/dossier-liste";
           reportUtil.showViewer(map, pathReport);
           
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public List<DossierLibelle> getCurrListe() {
        return currListe;
    }

    public void setCurrListe(List<DossierLibelle> currListe) {
        this.currListe = currListe;
    }
    
}

