/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers.facturation;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import modeles.BaseModele;
import modeles.dossiers.ContactDossierLibelle;
import modeles.dossiers.Dossier;
import modeles.facturation.Facture;
import modeles.facturation.FactureLibelle;
import modeles.facturation.TarifFactIntervttarLibelle;

import modeles.intervenants.Intervenant;
import modeles.parametres.TypeTarifEvt;
import services.BaseService;
import services.FactureService;
import statiques.ObjetStatique;
import utilitaire.ComboBoxUtil;
import utilitaire.Util;

/**
 * FXML Controller class
 *
 * @author Misaina
 */
public class FicheController implements Initializable {
    @FXML private CheckBox sansSuite;
    @FXML private CheckBox editee;
    @FXML private ComboBox comboEditee;
    @FXML private Button sauvegarder;
    @FXML private ComboBox typeFact;
    @FXML private DatePicker dateEcheance;
    @FXML private DatePicker dateReglement;
    @FXML private DatePicker dateFact;
    @FXML private ListView intervs;
    @FXML private TextField totalht;
    @FXML private TextField tva;
    @FXML private TextField mtttc;
    @FXML private TextField mttva;
    @FXML private TextField noDossier;
    @FXML private TextField nomDossier;
    @FXML private TextField noFact;
    @FXML private GridPane paneMt;
    @FXML private Text infoAdresseFact;
    
    private Dossier dossier;
    private FactureLibelle fact;
    private TextField[] tabMts;
    private List<TarifFactIntervttarLibelle> tarifsParInterv;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void initializeChamps()
    {
        ComboBoxUtil cbu=new ComboBoxUtil();
        BaseService bs=null;
        Util util=null;
        try
        {
            util=new Util();
            bs=new BaseService();
            tva.setText(fact.getTva().toString());
            dateFact.setValue(LocalDate.of(fact.getDateFacture().getYear()+1900, fact.getDateFacture().getMonth()+1, fact.getDateFacture().getDate()));
                   
            noDossier.setText(fact.getNumeroDossier());
            nomDossier.setText(dossier.getVnomDossier());
            noFact.setText(fact.getId().toString());
            cbu.fillComboBox(typeFact, (List<BaseModele>)(List<?>)ObjetStatique.getTypeFacture(), fact.getIdTypeFacture(), new String[]{"libelle"});
            
            sansSuite.setSelected(fact.getSansSuite());
            editee.setSelected(fact.getEditee());
            if(fact.getEditee())
            {
                cbu.fillComboBox(comboEditee, (List<BaseModele>)(List<?>)bs.find(new Intervenant()), fact.getIdIntervenantEdit(), new String[]{"nom", "prenom"});
                paneMt.setDisable(true);
                typeFact.setDisable(true);
                dateFact.setDisable(true);
                dateEcheance.setDisable(true);
                tva.setDisable(true);
                sauvegarder.setDisable(true);
            }
            if(fact.getReglee())
            {
               dateReglement.setDisable(false);
               paneMt.setDisable(true);
               dateEcheance.setDisable(true);
               dateFact.setDisable(true);
               typeFact.setDisable(true);
               sauvegarder.setDisable(true);
               tva.setDisable(true);
            }
            initializeMts();
            initAdresseFact();
            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void initAdresseFact()
    {
        BaseService bs=null;
        try
        {
            bs=new BaseService();
            ContactDossierLibelle contDoss=new ContactDossierLibelle();
            contDoss.setIdDossier(fact.getIdDossier());
            contDoss.setTypeContact("FACT");
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
                    + "\n\tFax : "+u.escapeNullString(contDoss.getFax())
                    + "\n\tWeb : "+u.escapeNullString(contDoss.getWeb())
                    + "\n\tAutres : "+u.escapeNullString(contDoss.getAutre());


            infoAdresseFact.setText(nom
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
    
    public void sauvegarder(ActionEvent ae)
    {
        try
        {
            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void initializeMts()
    {
        BaseService bs=new BaseService();
        try
        {
            TarifFactIntervttarLibelle t=new TarifFactIntervttarLibelle();
            t.setIdFacture(fact.getId());
            tarifsParInterv=(List<TarifFactIntervttarLibelle>)(List<?>)bs.find(t);
            
//            données intervenant
            List<TarifFactIntervttarLibelle> dataIntervs=new ArrayList<TarifFactIntervttarLibelle>();
            dataIntervs.add(tarifsParInterv.get(0));
            List<Integer> listeIdInterv=new ArrayList<Integer>(tarifsParInterv.size()/ObjetStatique.getTypeTarifEvt().size());
            listeIdInterv.add(dataIntervs.get(0).getIdIntervenant());
            for(TarifFactIntervttarLibelle tfl:tarifsParInterv)
            {
                Integer idAzo=null;
                int mt=0;
                for(Integer ic:listeIdInterv)
                {
                    if(ic.equals(tfl.getIdIntervenant()))
                    {
                        mt++;
                        break;
                    }
                    
                }
                if(mt==0)
                {
                    dataIntervs.add(tfl);
                    idAzo=tfl.getIdIntervenant();
                    listeIdInterv.add(idAzo);  
                }
            }
            t.setNomInterv("Tous");
            t.setPrenomInterv("");
            t.setId(0);
            t.setIdIntervenant(0);
            
            dataIntervs.add(0, t);
            ObservableList<TarifFactIntervttarLibelle> data;
            data =FXCollections.observableArrayList();
            data.setAll(dataIntervs);
            intervs.setItems(data);
            initChampsMts(0);
            intervs.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TarifFactIntervttarLibelle>() {
                public void changed(ObservableValue<? extends TarifFactIntervttarLibelle> observable,TarifFactIntervttarLibelle oldValue, TarifFactIntervttarLibelle newValue) {
                    try
                    {
                        initChampsMts(newValue.getIdIntervenant()); 
                    }
                    catch(Exception ex)
                    {
                        ex.printStackTrace();
                    }
                }
            });
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void sansSuite(ActionEvent ae)
    {
        BaseService bs=new BaseService();
        try
        {
            
            Facture f=new Facture();
            f.setId(fact.getId());
            f=(Facture)bs.findById(f);
            f.setSansSuite(sansSuite.isSelected());
            bs.update(f);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    
    public void initChampsMts(Integer idintervenant)
    {
        try
        {
            
            int r=0;
            tabMts=new TextField[ObjetStatique.getTypeTarifEvt().size()];
            Float tht=new Float(0);
            paneMt.getChildren().clear();
            for(TypeTarifEvt tte:ObjetStatique.getTypeTarifEvt())
            {
                tabMts[r]=new TextField();
                tabMts[r].setAlignment(Pos.CENTER_RIGHT);
                paneMt.add(new Label(tte.getLibelle()), 0, r);
                Float m=new Float(0);
                
                if(idintervenant!=0)
                {
                    tabMts[r].setOnAction(new EventHandler<ActionEvent>(){
                        @Override
                        public void handle(ActionEvent event) {
                            FactureService factServ=null;
                            try
                            {
                                factServ=new FactureService();
                                System.out.println("idintervenant="+idintervenant);
                                System.out.println("typetarif="+tte.getId());
                                System.out.println("facture="+fact.getId());
                                
                                factServ.updateMtParTarifInterv(fact.getId(), tte.getId(), idintervenant, new Float(((TextField)event.getSource()).getText()));
                                        
                                
                            }
                            catch(Exception ex)
                            {
                                ex.printStackTrace();
                            }
                        }
                    }
                    );
                }
                for(TarifFactIntervttarLibelle tfi:tarifsParInterv)
                {
                    if(idintervenant==0)
                    {
                        if(tte.getId().equals(tfi.getIdTypeTarif())) m+=tfi.getMt();
                    }
                    else
                    {
                        if(tte.getId().equals(tfi.getIdTypeTarif()) && tfi.getIdIntervenant().equals(idintervenant)) m+=tfi.getMt();
                    }
                    
                }
                tht+=m;
                tabMts[r].setText(m.toString());
                paneMt.add(tabMts[r], 1, r);
                
                r++;
            }
            totalht.setText(tht.toString());
            Float stva=fact.getTva()*tht/100;
            mttva.setText(stva.toString());
            Float ttc=stva+tht;
            mtttc.setText(ttc.toString());
            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
       
    }
    public FactureLibelle getFact() {
        return fact;
    }

    public void setFact(FactureLibelle fact) {
        this.fact = fact;
    }

    public Dossier getDossier() {
        return dossier;
    }

    public void setDossier(Dossier dossier) {
        this.dossier = dossier;
    }
    
     
}
