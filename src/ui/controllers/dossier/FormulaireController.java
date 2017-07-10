/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers.dossier;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import modeles.BaseModele;
import modeles.clients.Client;
import modeles.dossiers.Dossier;
import modeles.dossiers.DossierLibelle;
import modeles.intervenants.Intervenant;
import modeles.intervenants.IntervenantDossier;
import modeles.intervenants.IntervDossierLibelle;
import modeles.parametres.Juridiction;
import modeles.parametres.Nature;

import services.BaseService;
import services.DossierService;
import services.IntervenantService;
import statiques.ObjetStatique;
import statiques.StageStatique;
import ui.controllers.ListeGeneriqueController;
import utilitaire.ComboBoxUtil;
import utilitaire.FieldValidationUtil;
import utilitaire.Util;

/**
 * FXML Controller class
 *
 * @author Misaina
 */
public class FormulaireController implements Initializable {

    @FXML private Button ajouterIntervenant;
    @FXML private Button supprimerIntervenant;
    @FXML private Button valider;
    @FXML private Button selectClient;
    @FXML private ComboBox nature;
    @FXML private ComboBox gestionnaire;
    @FXML private ComboBox juridiction;
    @FXML private TextField lieu;
    @FXML private ListView intervenants;
    @FXML private CheckBox enCours;
    @FXML private ComboBox listeInterv;
    @FXML private TextField noProcedure;
    @FXML private TextField adversaire;
    @FXML private TextField nomClient;
    @FXML private TextField region;
    @FXML private TextField vnomDossier;
    @FXML private DatePicker dateOuverture;
    @FXML private AnchorPane ficheContactform;
    
    
    private DossierLibelle dossierLib;
    private TextField[] fieldToCheck;

    private ListeGeneriqueController listeCtrl;
    private ListeGeneriqueController listeIntervCtrl;
    private Stage stage;
    private ListeController precCtrl;
    
    private List<IntervDossierLibelle> listeIntervenantDos;
    private List<IntervenantDossier> listeIntervenantDosNouveau;
    private Stage currentStage;

    public Stage getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(Stage currentStage) {
        this.currentStage = currentStage;
    }
    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void initializeChamp() 
    {
        
        try
        {
         listeIntervenantDosNouveau=new ArrayList<IntervenantDossier>();
            adversaire.setText(dossierLib.getNomAdversaire());            
            noProcedure.setText(dossierLib.getNoProcedure());
            region.setText(dossierLib.getRegion());

            ObservableList<Juridiction> dataJuridiction;
                dataJuridiction =FXCollections.observableArrayList();
                dataJuridiction.setAll(ObjetStatique.getJuridictions());
            juridiction.setItems(dataJuridiction);
            int ind=0;
            int i=0;
            for(Juridiction t:ObjetStatique.getJuridictions())
            {
                if(Objects.equals(dossierLib.getIdJuridiction(), t.getId())) 
                {
                    ind=i;
                    break;
                }
                i++;
            }
            ComboBoxUtil cbu=new ComboBoxUtil();
            String[] colT=new String[1];
            colT[0]="code";
            juridiction.setCellFactory(cbu.buildCellFactory(colT, " "));
            juridiction.getSelectionModel().select(ind);
            
            cbu.fillComboBox(nature, (List<BaseModele>)(List<?>)ObjetStatique.getNatures(), ObjetStatique.getNatures().get(0).getId(), new String[]{"libelle"});
            
            
            IntervenantService ins=new IntervenantService();
            Intervenant interv=new Intervenant();
            interv.setGestionnaire(true);
            List<Intervenant> listeGestionnaire=ins.find(interv);
            ObservableList<Intervenant> dataGestionnaire;
                dataGestionnaire =FXCollections.observableArrayList();
                dataGestionnaire.setAll(listeGestionnaire);
            gestionnaire.setItems(dataGestionnaire);
            int indg=0;
            i=0;
            for(Intervenant t:listeGestionnaire)
            {
                if(Objects.equals(dossierLib.getIdGestionnaire(), t.getId())) 
                {
                    indg=i;
                    break;
                }
                i++;
            }
            String[] colG=new String[1];
            colG[0]="code";
            gestionnaire.setCellFactory(cbu.buildCellFactory(colG, " "));
            gestionnaire.getSelectionModel().select(indg);
            
            BaseService bs=new BaseService();
            cbu.fillComboBox(listeInterv, bs.find(new Intervenant()), 0, new String[]{"nom", "prenom"});
            isValidTf();
            initializeListeIntervDossier();
            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        
    }

    public void initializeListeIntervDossier()
    {
        ObservableList<IntervDossierLibelle> data;
        data =FXCollections.observableArrayList();
        data.setAll(getListeIntervenantDos());
        intervenants.setItems(data);
    }
    
    public void addInterv(ActionEvent ae)
    {
        Intervenant i=(Intervenant)listeInterv.getSelectionModel().getSelectedItem();
        String in=((Intervenant)listeInterv.getSelectionModel().getSelectedItem()).getNom()+" "+((Intervenant)listeInterv.getSelectionModel().getSelectedItem()).getPrenom();
        
        IntervenantDossier idd=new IntervenantDossier();
        idd.setIdIntervenant(i.getId());
        int mtovy=0;
        for(IntervenantDossier ido:listeIntervenantDosNouveau)
        {
            if(ido.getIdIntervenant().equals(idd.getIdIntervenant())) {
                mtovy++;
                break;
            }
        }
        if(mtovy==0) {
            listeIntervenantDosNouveau.add(idd);
            intervenants.getItems().add(in);
        }
        
    }
    public void delInterv(ActionEvent ae)
    {
        intervenants.getItems().remove(intervenants.getSelectionModel().getSelectedItem());
        int indic=0;
        for(IntervenantDossier ido:listeIntervenantDosNouveau)
        {
            if(intervenants.getSelectionModel().getSelectedIndex()==indic) {
                listeIntervenantDosNouveau.remove(ido);
                break;
            }
            indic++;
        }
        
    }
    public void selectClient(ActionEvent ae)
    {
        Stage stage=new Stage();
        try
        {
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/ui/fxml/ListeGenerique.fxml"));
            Parent root = (Pane)loader.load();
            listeCtrl=loader.<ListeGeneriqueController>getController();
            listeCtrl.setBase(new Client());
            String[] cols=new String[1];
            cols[0]="nom";
            listeCtrl.setCols(cols);
            listeCtrl.setChamp(nomClient);
            listeCtrl.initializeListe();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            listeCtrl.setStage(stage);
            stage.initOwner(StageStatique.getStage2());
            stage.initModality(Modality.WINDOW_MODAL); 
            stage.showAndWait();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public Boolean isValidTf()throws Exception
    {
        try
        {
            FieldValidationUtil fieldValidationUtil=new FieldValidationUtil();
            fieldToCheck=new TextField[]{vnomDossier, nomClient, adversaire};
            
            return fieldValidationUtil.checkValueFromTextField(fieldToCheck);
        }
        catch(Exception ex){
            throw ex;
        }
    }
    public void create()throws Exception
    {
        Dossier d=null;
        try
        {
            FieldValidationUtil fieldValidationUtil=new FieldValidationUtil();
            fieldValidationUtil.setFocusError(fieldToCheck);
            d=new Dossier();
//            System.out.println(((Client)listeCtrl.getItemSelected()).getId().toString());
            d.setIdClient(((Client)listeCtrl.getItemSelected()).getId());
            d.setIdJuridiction(((Juridiction)juridiction.getSelectionModel().getSelectedItem()).getId());
            d.setIdNature(((Nature)nature.getSelectionModel().getSelectedItem()).getId());
            d.setIdGestionnaire(((Intervenant)gestionnaire.getSelectionModel().getSelectedItem()).getId());
            Util u=new Util();
            d.setDateOuverture(u.datePickerToDate(dateOuverture));
            d.setLieu(lieu.getText());
            d.setNoProcedure(noProcedure.getText());
            d.setNomAdversaire(adversaire.getText());
            d.setRegion(region.getText());
            d.setEnCours(enCours.isSelected());
            d.setVnomDossier(vnomDossier.getText());
            DossierService ds=new DossierService();
            Integer iddoss=ds.getNextVal("dossier_iddossier_seq");
            d.setNumeroDossier(u.dateToString(d.getDateOuverture()).substring(8, 10)+""+u.addPrefix(4, iddoss.toString(), "0"));
            d.setId(iddoss);
            ds.save(d);
            if(listeIntervenantDosNouveau!=null)
            {
                if(listeIntervenantDosNouveau.size()!=0)
                {
                    BaseService ba=new BaseService();
                    for(IntervenantDossier idooo:listeIntervenantDosNouveau)
                    {
                        idooo.setIdDossier(d.getId());
                        ba.save(idooo);
                    }
                }
            }
            stage.close();
            precCtrl.initPrim();
            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText(ex.getMessage());
            alert.show();
        }
    }
    
    public DossierLibelle getDossierLib() {
        return dossierLib;
    }

    public void setDossierLib(DossierLibelle dossierLib) {
        this.dossierLib = dossierLib;
    }

    public TextField getNomClient() {
        return nomClient;
    }

    public void setNomClient(TextField nomClient) {
        this.nomClient = nomClient;
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

    public List<IntervDossierLibelle> getListeIntervenantDos() {
        return listeIntervenantDos;
    }

    public void setListeIntervenantDos(List<IntervDossierLibelle> listeIntervenantDos) {
        this.listeIntervenantDos = listeIntervenantDos;
    }

    public List<IntervenantDossier> getListeIntervenantDosNouveau() {
        return listeIntervenantDosNouveau;
    }

    public void setListeIntervenantDosNouveau(List<IntervenantDossier> listeIntervenantDosNouveau) {
        this.listeIntervenantDosNouveau = listeIntervenantDosNouveau;
    }
}
