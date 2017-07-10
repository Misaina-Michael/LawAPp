/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers.facturation;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import modeles.dossiers.Dossier;
import modeles.dossiers.DossierLibelle;
import modeles.evenement.EvtDossierLibelle;
import modeles.facturation.FactureLibelle;
import modeles.parametres.TypeTarifEvt;
import services.BaseService;
import services.EvenementService;
import statiques.ObjetStatique;
import ui.controllers.evenement.ListeController;

/**
 * FXML Controller class
 *
 * @author Misaina
 */
public class PreFacturationController implements Initializable {
    
    @FXML private TextField totalht;
    @FXML private TextField tva;
    @FXML private TextField mtttc;
    @FXML private TextField mttva;
    @FXML private TextField nodossier;
    @FXML private TextField nomdossier;
    @FXML private TextField nofacture;
    @FXML private DatePicker datefact;
    @FXML private TableView tabEvt;
    @FXML private TableColumn colDaty;
    @FXML private TableColumn colEvt;
    @FXML private TableColumn colMt;
    @FXML private GridPane gridMts;
    
    private Stage stage;
    private DossierLibelle dossierLib;
    private List<EvtDossierLibelle> listeEvt;
    private Float defaultTva=new Float(20);
    private ListeController precCtrl;
    
    private TextField[] tabMts;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    public void initAll()
    {
        try
        {
            datefact.setValue(LocalDate.now());
            initListe();
            initializeMt();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void initializeMt()
    {
        EvenementService es=null;
        try
        {
            es=new EvenementService();
            int r=0;
            tabMts=new TextField[ObjetStatique.getTypeTarifEvt().size()];
            Float tht=new Float(0);
            for(TypeTarifEvt tte:ObjetStatique.getTypeTarifEvt())
            {
                tabMts[r]=new TextField();
                tabMts[r].setAlignment(Pos.CENTER_RIGHT);
                gridMts.add(new Label(tte.getLibelle()), 0, r);
                Float m=new Float(0);
                for(EvtDossierLibelle edl:listeEvt)
                {
                    if(tte.getId().equals(edl.getIdTypeTarif())) m+=edl.getMt();
                }
                tht+=m;
                tabMts[r].setText(m.toString());
                gridMts.add(tabMts[r], 1, r);
                
                r++;
            }
            tva.setAlignment(Pos.CENTER_RIGHT);
            tva.setText(defaultTva.toString());
            totalht.setText(tht.toString());
            Float stva=tht*defaultTva/100;
            Float ttc=stva+tht;
            mttva.setText(stva.toString());
            mtttc.setText(ttc.toString());
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void initializeDossier()
    {
        try
        {
            nodossier.setText(dossierLib.getNumeroDossier());
            nomdossier.setText((dossierLib.getNomClient()+" # "+dossierLib.getNomAdversaire()+" "+dossierLib.getNumeroDossier()).toUpperCase());
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void initListe()
    {
        try
        {
            colDaty.setCellValueFactory(
            new PropertyValueFactory<EvtDossierLibelle, String>("daty"));
            colEvt.setCellValueFactory(
                new PropertyValueFactory<EvtDossierLibelle, String>("libelle"));
            colMt.setCellValueFactory(
                new PropertyValueFactory<EvtDossierLibelle, String>("mt"));
            
            ObservableList<EvtDossierLibelle> data;
            data =FXCollections.observableArrayList();
            data.setAll(listeEvt);

            tabEvt.setItems(data);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void enleverEvt(ActionEvent ae)
    {
        try
        {
            listeEvt.remove(tabEvt.getSelectionModel().getSelectedItem());
            initAll();
        }
        catch(Exception ex)
        {
            throw ex;
        }
    }
    public void genererFacture(ActionEvent ae)
    {
        EvenementService es=null;
        BaseService bs=null;
        try
        {
            bs=new BaseService();
            es=new EvenementService();
            es.genererFacture(listeEvt,new Float(tva.getText()));
            stage.close();
            
//            maj liste evt
            EvtDossierLibelle el=new EvtDossierLibelle();
            el.setAfacturer(true);
            el.setIdDossier(dossierLib.getId());
            this.getPrecCtrl().initializeSearch();
            this.getPrecCtrl().initPrim(el);
            
//            ouverture fiche facture
            FactureLibelle currFact=new FactureLibelle();
            currFact.setId(es.getCurrVal("facture_idfacture_seq"));
            Stage s=new Stage();
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/ui/fxml/facturation/PanneauFact.fxml"));
            Parent root = (AnchorPane)loader.load();
            PanneauFactController panneauCtrl=loader.<PanneauFactController>getController();
            panneauCtrl.setFactLibelle(currFact);
            Dossier dos=new Dossier();
            dos.setId(dossierLib.getId());
            dos=(Dossier)bs.findById(dos);
            panneauCtrl.setDossier(dos);
            panneauCtrl.refresh();
            panneauCtrl.fiche();
            Scene sc=new Scene(root);
            s.setScene(sc);
            s.show();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void annuler(ActionEvent ae)
    {
        stage.close();
    }
    public List<EvtDossierLibelle> getListeEvt() {
        return listeEvt;
    }

    public void setListeEvt(List<EvtDossierLibelle> listeEvt) {
        this.listeEvt = listeEvt;
    }

    public DossierLibelle getDossierLib() {
        return dossierLib;
    }

    public void setDossierLib(DossierLibelle dossierLib) {
        this.dossierLib = dossierLib;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public ListeController getPrecCtrl() {
        return precCtrl;
    }

    public void setPrecCtrl(ListeController precCtrl) {
        this.precCtrl = precCtrl;
    }
}
