/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers.client;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import modeles.clients.Client;

import modeles.parametres.Titre;
import modeles.parametres.TypeFacturationClient;
import services.ClientService;
import statiques.ObjetStatique;
import utilitaire.ComboBoxUtil;
import utilitaire.FieldValidationUtil;

/**
 * FXML Controller class
 *
 * @author Misaina
 */
public class FormulaireClientController implements Initializable {
    @FXML private TextField numero;
    @FXML private TextField nom;
    @FXML private TextField cp;
    @FXML private TextField ville;
    @FXML private TextField mobile;
    @FXML private TextField standard;
    @FXML private TextField bureau;
    @FXML private TextField fax;
    @FXML private TextField email;
    @FXML private TextField telex;
    @FXML private TextField web;
    @FXML private TextField autre;
    @FXML private TextArea adresse;
    @FXML private TextArea commentaire;
    @FXML private ComboBox titre;
    @FXML private TextField pays;
    private TextField[] fieldToCheck;
    private String action;
    private PanneauPrincipalClientController panPrClCtrl;
    
    private Client client;
    
    /**
     * Initializes the controller class.
     */
    
  
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try
        {
            //System.out.println(getClient().getNom());
//            initializeChamp();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        
    }    
    
    public void initializeChamp() throws Exception
    {
        
        numero.setText(getClient().getId().toString());
        ObservableList<Titre> dataTitre;
            dataTitre =FXCollections.observableArrayList();
            dataTitre.setAll(ObjetStatique.getTitres());
        titre.setItems(dataTitre);
        int ind=0;
        if(getClient().getIdTitre()!=null)
        {
            int i=0;
            for(Titre t:ObjetStatique.getTitres())
            {
                if(Objects.equals(getClient().getIdTitre(), t.getId())) 
                {
                    ind=i;
                    break;
                }
                i++;
            }
        }
      
        titre.getSelectionModel().select(ind);
        ComboBoxUtil cbu=new ComboBoxUtil();
        String[] colT=new String[1];
        colT[0]="libelle";
        
        titre.setCellFactory(cbu.buildCellFactory(colT, ""));
            
        nom.setText(getClient().getNom());
        adresse.setText(getClient().getAdresse());
        cp.setText(getClient().getCodePostal().toString());
        ville.setText(getClient().getVille());
        pays.setText(getClient().getPays());
        mobile.setText(getClient().getMobile());
        standard.setText(getClient().getStandard());
        telex.setText(getClient().getTelex());
        fax.setText(getClient().getFax());
        email.setText(getClient().getEmail());
        web.setText(getClient().getWeb());
        autre.setText(getClient().getAutre());
//        commentaire.setText(getClient().get);
        isValidTf();
    }
    public Boolean isValidTf()throws Exception
    {
        try
        {
            FieldValidationUtil fieldValidationUtil=new FieldValidationUtil();
            fieldToCheck=new TextField[]{nom};
            return fieldValidationUtil.checkValueFromTextField(fieldToCheck);
        }
        catch(Exception ex){
            throw ex;
        }
    }
    public void clickValidButton() 
    {
        
        try
        {
            FieldValidationUtil fieldValidationUtil=new FieldValidationUtil();
            fieldValidationUtil.setFocusError(fieldToCheck);
            client.setIdTitre(((Titre)titre.getSelectionModel().getSelectedItem()).getId());
            client.setNom(nom.getText());
            client.setAdresse(adresse.getText());
            client.setCodePostal(new Integer(cp.getText()));
            client.setVille(ville.getText());
            client.setMobile(mobile.getText());
            client.setStandard(standard.getText());
            client.setTelex(telex.getText());
            client.setFax(fax.getText());
            client.setEmail(email.getText());
             
            client.setPays(pays.getText());
            client.setWeb(web.getText());
            client.setAutre(autre.getText());
            
            TypeFacturationClient tf=new TypeFacturationClient();
            tf.setCategorieComptable("");
            tf.setCompteComptable("70");
            tf.setLangue("Francais");
            tf.setEcheance(30);
            tf.setPeriodicite("");
            
            tf.setCompteTiers("");
            tf.setTypeFacture("");
            tf.setPeriodicite("Mensuelle");
            tf.setTauxTva(new Float(20.0));
            tf.setTauxMode(new Float(100.0));
            tf.setMtForfait(new Float(0.0));
            tf.setIdMode(1);
            tf.setIdTypeFacture(ObjetStatique.getTypeFacture().get(0).getId());
            
            ClientService cs=new ClientService();
            if(action.compareToIgnoreCase("create")==0)cs.saveClient(client, tf);
            if(action.compareToIgnoreCase("update")==0){
                cs.update(client);
            }
            getPanPrClCtrl().setListe(cs.findClient(new Client()));
            getPanPrClCtrl().initializeListeClient();
            
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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public PanneauPrincipalClientController getPanPrClCtrl() {
        return panPrClCtrl;
    }

    public void setPanPrClCtrl(PanneauPrincipalClientController panPrClCtrl) {
        this.panPrClCtrl = panPrClCtrl;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
    
    
    
}
