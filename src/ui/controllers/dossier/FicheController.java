/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers.dossier;

import java.net.URL;
import java.sql.Time;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;
import javafx.stage.Modality;
import modeles.contact.Contact;
import modeles.dossiers.ContactDossier;
import modeles.dossiers.ContactDossierLibelle;
import modeles.dossiers.Dossier;
import modeles.dossiers.DossierLibelle;
import modeles.evenement.EvtDossierLibelle;
import modeles.facturation.FactureLibelle;
import modeles.intervenants.IntervDossierLibelle;
import modeles.intervenants.Intervenant;
import modeles.intervenants.IntervenantDossier;
import services.BaseService;
import services.DossierService;
import statiques.StageStatique;
import ui.controllers.ListeGeneriqueController;
import utilitaire.ReportUtil;
import utilitaire.Util;

/**
 * FXML Controller class
 *
 * @author Misaina
 */
public class FicheController implements Initializable {
    @FXML private CheckBox enCours;
    @FXML private Label dateOuverture;
    @FXML private Label gestionnaire;
    @FXML private Label juridiction;
    @FXML private Label lieu;
    @FXML private Label nature;
    @FXML private Label nomAdversaire;
    @FXML private Label nomClient;
    @FXML private Label noProcedure;
    @FXML private Label region;
    @FXML private Label vnomDossier;
    @FXML private ListView listeIntervenants;
    @FXML private Text infoApporteur;
    @FXML private TableView tabEvt;
    @FXML private TableColumn dateCol;
//    @FXML private TableColumn intervCol;
//    @FXML private TableColumn mtCol;
    @FXML private TableColumn dureeCol;
    @FXML private TableColumn tacheCol;
    
    private DossierLibelle dossierLib;
    private Dossier dossier;
    private List<IntervDossierLibelle> intervs;
    private ListeGeneriqueController listeContactCtrl;
    private ListeGeneriqueController listeIntervCtrl;
    private ContactDossierLibelle contDoss;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    public void initialize() throws Exception
    {
        BaseService bs=null;
        Util u=null;
        try
        {
            bs=new BaseService();
            dossierLib=(DossierLibelle)bs.findById(dossierLib);
            u=new Util();
            
            nomClient.setText(dossierLib.getNomClient());
            nomAdversaire.setText(dossierLib.getNomAdversaire());
            dateOuverture.setText(u.dateToString(dossierLib.getDateOuverture()));
            gestionnaire.setText(dossierLib.getNomGestionnaire());
            juridiction.setText(dossierLib.getJuridiction());
            lieu.setText(dossierLib.getLieu());
            nature.setText(dossierLib.getNature());
            noProcedure.setText(dossierLib.getNoProcedure());
            region.setText(dossierLib.getRegion());
            vnomDossier.setText(dossierLib.getVnomDossier());
            enCours.setSelected(dossierLib.getEnCours());
            initializeIntervs();   
            initApporteur();
            initListeEvt();
        }
        catch(Exception ex)
        {
            throw ex;
        }
    }

    public void initListeEvt()
    {
        BaseService bs=null;
        Util u=null;
        try
        {
            bs=new BaseService();
            u=new Util();
            dateCol.setCellValueFactory(new PropertyValueFactory<EvtDossierLibelle, Date>("daty"));
//            intervCol.setCellValueFactory(new PropertyValueFactory<EvtDossierLibelle, String>("nomInterv"));
            tacheCol.setCellValueFactory(new PropertyValueFactory<EvtDossierLibelle, String>("libelle"));
            dureeCol.setCellValueFactory(new PropertyValueFactory<EvtDossierLibelle, Float>("duree"));
//            mtCol.setCellValueFactory(new PropertyValueFactory<EvtDossierLibelle, Time>("mt"));
            EvtDossierLibelle evl=new EvtDossierLibelle();
            evl.setIdDossier(dossierLib.getId());
            evl.setAfacturer(true);
            List<EvtDossierLibelle> liste=(List<EvtDossierLibelle>)(List<?>)bs.find(evl);
            ObservableList<EvtDossierLibelle> data;
            data =FXCollections.observableArrayList();
            data.setAll(liste);
            tabEvt.setItems(data);
            dateCol.setCellFactory(column -> {
                return new TableCell<FactureLibelle,Date>() {
                    protected void updateItem(Date item, boolean empty) {
                        super.updateItem(item, empty);
                        if(item!=null){
                            setText(new SimpleDateFormat("dd/MM/yyyy").format(item));
                        }
                    }
                };
            });
//            mtCol.setCellFactory(column -> {
//                return new TableCell<FactureLibelle,Float>() {
//                    protected void updateItem(Float item, boolean empty) {
//                        super.updateItem(item, empty);
//                        setAlignment(Pos.CENTER_RIGHT);
//                        if(item!=null){
//                            setText(new DecimalFormat("#,##0.00").format(item));
//                        }
//                    }
//                };
//            });
            dureeCol.setCellFactory(column -> {
                return new TableCell<FactureLibelle,Time>() {
                    protected void updateItem(Time item, boolean empty) {
                        super.updateItem(item, empty);
                        setAlignment(Pos.CENTER);
                        if(item!=null){
                            setText(new SimpleDateFormat("HH:mm").format(item));
                        }
                    }
                };
            });
                  
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        
    }
    public void initializeIntervs()
    {
        ObservableList<IntervDossierLibelle> data;
        data =FXCollections.observableArrayList();
        data.setAll(getIntervs());
        listeIntervenants.setItems(data);
    }
    public void initApporteur()
    {
        BaseService bs=null;
        try
        {
            bs=new BaseService();
            contDoss=new ContactDossierLibelle();
            contDoss.setIdDossier(dossierLib.getId());
            contDoss.setTypeContact("APP");
            contDoss=((List<ContactDossierLibelle>)(List<?>)bs.find(contDoss)).get(0);
            Util u=new Util();
            String nom=u.escapeNullString(contDoss.getTitreContact())+" "+u.escapeNullString(contDoss.toString());
            String adresse="\n"+u.escapeNullString(contDoss.getAdresse());
            String cpVille="\n"+u.escapeNullString(contDoss.getCp().toString())+" "+u.escapeNullString(contDoss.getVille());
            String pays="\n"+u.escapeNullString(contDoss.getPays());
            String contacts="\n\n\t"
                    + "Bureau : "+u.escapeNullString(contDoss.getBureau())
                    + "\n\tMobile : "+u.escapeNullString(contDoss.getMobile())
                    + "\n\tStandard : "+u.escapeNullString(contDoss.getStandard())
                    + "\n\tFax : "+u.escapeNullString(contDoss.getFax());
                   


            infoApporteur.setText(nom
                    +adresse
                    +cpVille
                    +pays
                    +contacts
            );      
                    
            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public DossierLibelle getDossierLib() {
        return dossierLib;
    }

    public void setDossierLib(DossierLibelle dossierLib) {
        this.dossierLib = dossierLib;
    }

    public Dossier getDossier() {
        return dossier;
    }

    public void setDossier(Dossier dossier) {
        this.dossier = dossier;
    }

    public List<IntervDossierLibelle> getIntervs() {
        return intervs;
    }

    public void setIntervs(List<IntervDossierLibelle> intervs) {
        this.intervs = intervs;
    }

    public void addApporteur(ActionEvent ae)
    {
        Stage stage=new Stage();
        try
        {
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/ui/fxml/ListeGenerique.fxml"));
            Parent root = (Pane)loader.load();
            listeContactCtrl=loader.<ListeGeneriqueController>getController();
            listeContactCtrl.setBase(new Contact());
            listeContactCtrl.getValider().setVisible(false);
            listeContactCtrl.getCustomValid().setVisible(true);
            
            listeContactCtrl.getCustomValid().setOnAction(new EventHandler<ActionEvent>() {    
                public void handle(ActionEvent event) { 
                    System.out.println("ok");
                    BaseService bs=null;
                    try
                    {
                        bs=new BaseService();
                        listeContactCtrl.validAction(event);
                        ContactDossier cDoss=new ContactDossier();
                        cDoss.setId(contDoss.getId());
                        cDoss.setIdDossier(dossierLib.getId());
                        cDoss.setTypeContact("APP");
                        cDoss.setIdContact(listeContactCtrl.getItemSelected().getId());
                        bs.update(cDoss);
                        initApporteur();
                    }
                    catch(Exception ex)
                    {
                        ex.printStackTrace();
                    }
                }
            });
            
            String[] cols=new String[]{"nom", "prenom"};
            
            listeContactCtrl.setCols(cols);
            listeContactCtrl.setChamp(new TextField());
            listeContactCtrl.initializeListe();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            listeContactCtrl.setStage(stage);
            StageStatique.setStage3(stage);
            stage.initOwner(StageStatique.getStage2());
            stage.initModality(Modality.WINDOW_MODAL); 
            stage.showAndWait();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void delIntervenant(ActionEvent ae)
    {
        BaseService bs=null;
        try
        {
            bs=new BaseService();
            IntervDossierLibelle iDSelected=new IntervDossierLibelle();
            
            iDSelected.setId(((IntervDossierLibelle)listeIntervenants.getSelectionModel().getSelectedItem()).getId());
            IntervenantDossier idRemove=new IntervenantDossier();
            idRemove.setId(((IntervDossierLibelle)listeIntervenants.getSelectionModel().getSelectedItem()).getId());
            bs.delete(idRemove);
            iDSelected.setIdDossier(dossierLib.getId());
            setIntervs((List<IntervDossierLibelle>)(List<?>)bs.find(iDSelected));
            initializeIntervs();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void addIntervenant(ActionEvent ae)
    {
        Stage stage=new Stage();
        try
        {
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/ui/fxml/ListeGenerique.fxml"));
            Parent root = (Pane)loader.load();
            listeIntervCtrl=loader.<ListeGeneriqueController>getController();
            listeIntervCtrl.setBase(new Intervenant());
            listeIntervCtrl.getValider().setVisible(false);
            listeIntervCtrl.getCustomValid().setVisible(true);
            
            listeIntervCtrl.getCustomValid().setOnAction(new EventHandler<ActionEvent>() {    
                public void handle(ActionEvent event) { 
                    BaseService bs=null;
                    try
                    {
                        bs=new BaseService();
                        listeIntervCtrl.validAction(event);
                        IntervenantDossier intervDoss=new IntervenantDossier();
                        intervDoss.setIdDossier(dossierLib.getId());
                        intervDoss.setIdIntervenant(listeIntervCtrl.getItemSelected().getId());
                        int mtovy=0;
                        for(IntervDossierLibelle idl:getIntervs())
                        {
                            if(idl.getIdIntervenant().equals(intervDoss.getIdIntervenant())) 
                            {
                                mtovy++;
                                break;
                            }
                        }
                        if(mtovy==0) {
                            bs.save(intervDoss);
                            IntervDossierLibelle idl=new IntervDossierLibelle();
                            idl.setIdDossier(dossierLib.getId());
                            setIntervs((List<IntervDossierLibelle>)(List<?>)bs.find(idl));
                            initializeIntervs();
                        }
                    }
                    catch(Exception ex)
                    {
                        ex.printStackTrace();
                    }
                }
            });
            
            String[] cols=new String[]{"nom", "prenom"};
            
            listeIntervCtrl.setCols(cols);
            listeIntervCtrl.setChamp(new TextField());
            listeIntervCtrl.initializeListe();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            listeIntervCtrl.setStage(stage);
            StageStatique.setStage3(stage);
            stage.initOwner(StageStatique.getStage2());
            stage.initModality(Modality.WINDOW_MODAL); 
            stage.showAndWait();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void editInfo(ActionEvent event){
        Stage s=null;
        try
        {
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/ui/fxml/dossier/ModifInfoDossier.fxml"));
            Parent root = (Pane)loader.load();
            ModifInfoDossierController modifCtrl=loader.<ModifInfoDossierController>getController();
            modifCtrl.setDossierLib(dossierLib);
            s=new Stage();
            modifCtrl.setStage(s);
            modifCtrl.setPrecCtrl(this);
            modifCtrl.initChamps();
            Scene sc=new Scene(root);
            s.setScene(sc);
            StageStatique.setStage3(s);
            s.initOwner(StageStatique.getStage2());
            s.initModality(Modality.WINDOW_MODAL); 
            s.showAndWait();
            
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    public void printFiche(ActionEvent event){
        DossierService dossSrv=null;
        ReportUtil reportUtil=new ReportUtil();
        try{
           dossSrv=new DossierService();
           Map map=dossSrv.produceMapFicheDossier(dossierLib);
           String pathReport=ReportUtil.pathDocs+"/report/dossier-fiche";
           reportUtil.showViewer(map, pathReport);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
}
