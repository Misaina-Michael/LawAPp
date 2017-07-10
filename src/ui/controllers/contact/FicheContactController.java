/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers.contact;

import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import modeles.BaseModele;
import modeles.contact.Contact;
//import services.TitreService;

import modeles.parametres.Titre;
import org.hibernate.exception.ConstraintViolationException;
import services.BaseService;
import services.ContactService;
import statiques.ObjetStatique;
import utilitaire.ComboboxUtilitaire;

/**
 * FXML Controller class
 *
 * @author RABENANTOANDRO
 */
public class FicheContactController implements Initializable {

    private Contact contact;
    private Titre titret;
    private Boolean disableButtonContact;
    private Titre titreTemp;

    public Boolean getDisableButtonContact() {
        return disableButtonContact;
    }

    public void setDisableButtonContact(Boolean disableButtonContact) {
        this.disableButtonContact = disableButtonContact;
    }
    private ContactService contactService = new ContactService();
    private PanneauPrincipaleContactController ppcController;

    public FicheContactController fc;
    @FXML
    ComboBox titre;
    @FXML
    TextField nom;
    @FXML
    TextField prenom;
    @FXML
    TextField fonction;
    @FXML
    TextField adresse;
    @FXML
    TextField codePostale;
    @FXML
    TextField standard;
    @FXML
    TextField bureau;
    @FXML
    TextField mobile;
    @FXML
    TextField fax;
    @FXML
    TextField telex;
    @FXML
    TextField mail;
    @FXML
    TextField web;
    @FXML
    TextField pays;
    @FXML
    TextField ville;
    @FXML
    TextField autre;
    @FXML
    CheckBox enSommeil;
    @FXML
    Button saveDataContactButton;
    @FXML
    Button updateDataContactButton;
    @FXML
    AnchorPane ficheContactform;
    BaseService bs = new BaseService();

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Titre getTitret() {
        return titret;
    }

    public void setTitret(Titre titret) {
        this.titret = titret;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.initialisedata();

        //System.out.println(ObjetStatique.getTitres().get(0).getClass().toString());
        // TODO
    }

    public void initialisedata() {
        ObservableList<Titre> dataTitre;
        dataTitre = FXCollections.observableArrayList();
        dataTitre.setAll(ObjetStatique.getTitres());
        titre.getSelectionModel().selectFirst();
        String[] colons = new String[1];
        colons[0] = "libelle";
        titre.setCellFactory(new ComboboxUtilitaire().comboboxCallback(colons, ""));
        titre.setButtonCell(new ComboboxUtilitaire().setButtonValue(colons, ""));
        titre.setItems(dataTitre);
    }

    public void initializeInfoContact() {
        try {

            /*ObservableList<Titre> dataTitre;
            dataTitre = FXCollections.observableArrayList();
            dataTitre.setAll(ObjetStatique.getTitres());
            Titre t = this.getTitret();
            titre.setItems(dataTitre);
            titre.setValue(this.getTitret().getLibelle());*/
            ObservableList<Titre> dataTitre;
            dataTitre = FXCollections.observableArrayList();
            dataTitre.setAll(ObjetStatique.getTitres());
            List<BaseModele> fct = bs.find(new Titre());
            Titre tte = new Titre();
            tte.setId(this.getTitret().getId());
            Titre f = (Titre) bs.findById(tte);
            String[] colons = new String[1];
            colons[0] = "libelle";
            titre.getSelectionModel().select(f);
            titre.setCellFactory(new ComboboxUtilitaire().comboboxCallback(colons, ""));
            titre.setButtonCell(new ComboboxUtilitaire().setButtonValue(colons, ""));
            titre.setItems(dataTitre);
            this.setTitreTemp(f);
            pays.setText(this.getContact().getPays());
            ville.setText(this.getContact().getVille());
            nom.setText(this.getContact().getNom());
            prenom.setText(this.getContact().getPrenom());
            fonction.setText(this.getContact().getFonction());
            adresse.setText(this.getContact().getAdresse());
            codePostale.setText(this.getContact().getCodePostal().toString());
            standard.setText(this.getContact().getStandard());
            bureau.setText(this.getContact().getBureau());
            mobile.setText(this.getContact().getMobile());
            fax.setText(this.getContact().getFax());
            telex.setText(this.getContact().getTelex());
            mail.setText(this.getContact().getEmail());
            web.setText(this.getContact().getWeb());
            autre.setText(this.getContact().getAutre());
            //enSommeil.setSelected(this.getContact().getEnSommeil());
            enSommeil.setSelected(this.getContact().getEnSommeil());
            this.saveDataContactButton.setDisable(this.getDisableButtonContact());
            
            

        } catch (Exception ex) {
            Logger.getLogger(FicheContactController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void clearinfo() {
        ObservableList<Titre> dataTitre;
        dataTitre = FXCollections.observableArrayList();
        dataTitre.setAll(ObjetStatique.getTitres());
        titre.getSelectionModel().selectFirst();
        String[] colons = new String[1];
        colons[0] = "libelle";
        titre.setCellFactory(new ComboboxUtilitaire().comboboxCallback(colons, ""));
        titre.setButtonCell(new ComboboxUtilitaire().setButtonValue(colons, ""));
        titre.setItems(dataTitre);
        pays.clear();
        ville.clear();
        nom.clear();
        prenom.clear();
        fonction.clear();
        adresse.clear();
        codePostale.clear();
        standard.clear();
        bureau.clear();
        fax.clear();
        mail.clear();
        web.clear();
        autre.clear();
        this.saveDataContactButton.setDisable(false);
    }

    @FXML
    public void updateDataContactClicked(ActionEvent event) {

        Titre titreselected = (Titre) this.titre.getSelectionModel().getSelectedItem();
        
        try {

            Contact contact = new Contact();
            contact.setId(this.getContact().getId());
            contact.setNom(nom.getText());
            contact.setPrenom(prenom.getText());
            contact.setIdTitre(titreselected.getId());
            contact.setFonction(fonction.getText());
            contact.setAdresse(adresse.getText());
            contact.setCodePostal(101);
            contact.setVille(ville.getText());
            contact.setPays(pays.getText());
            contact.setStandard(standard.getText());
            contact.setBureau(bureau.getText());
            contact.setFax(fax.getText());
            contact.setEmail(mail.getText());
            contact.setTelex(telex.getText());
            contact.setMobile(mobile.getText());
            contact.setWeb(web.getText());
            contact.setMobile(mobile.getText());
            contact.setAutre(autre.getText());
            contact.setEnSommeil(enSommeil.isSelected());
            contact.setDateCreation(Calendar.getInstance().getTime());
            contact.setDateModification(new Date());
            contactService.update(contact);
            //this.saveDataContactButton.setDisable(false);
//            this.ppcController.tableaulisteContact();
            this.getPpcController().tableaulisteContact();
            this.clearinfo();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    public void saveDataContactClicked(ActionEvent event) {
        Titre titreselected = (Titre) this.titre.getSelectionModel().getSelectedItem();
        try {

            Contact contact = new Contact();
            contact.setNom(nom.getText());
            contact.setPrenom(prenom.getText());
            contact.setIdTitre(titreselected.getId());
            contact.setFonction(fonction.getText());
            contact.setAdresse(adresse.getText());
            contact.setCodePostal(101);
            contact.setVille(ville.getText());
            contact.setPays(pays.getText());
            contact.setStandard(standard.getText());
            contact.setBureau(bureau.getText());
            contact.setFax(fax.getText());
            contact.setEmail(mail.getText());
            contact.setTelex(telex.getText());
            contact.setMobile(mobile.getText());
            contact.setWeb(web.getText());
            contact.setMobile(mobile.getText());
            contact.setAutre(autre.getText());
            contact.setEnSommeil(enSommeil.isSelected());
            contact.setDateCreation(Calendar.getInstance().getTime());
            contact.setDateModification(null);
            contactService.SaveContact(contact);
            this.saveDataContactButton.setDisable(false);
            this.ppcController.tableaulisteContact();
            this.clearinfo();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    @FXML
    public void supprimerContactEvent(ActionEvent event) {
        try{
           
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Supprimer Contact");
            alert.setHeaderText("Confirmation de suppression");
            alert.setContentText("Confirmez vous la suppression du Contact");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Contact c = new Contact();
                c.setId(this.getContact().getId());
                bs.delete(c);
                this.getPpcController().tableaulisteContact();
            } else {
                alert.close();
            }
        }
        catch(ConstraintViolationException ex)
        {
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erreur de suppresion");
            alert.setContentText("Impossible de supprimer ce Contact. Il existe des références dans la base de données. Veuillez d'avord supprimer ces références");
            alert.show();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    public Titre getTitreTemp() {
        return titreTemp;
    }

    public void setTitreTemp(Titre titreTemp) {
        this.titreTemp = titreTemp;
    }

    public PanneauPrincipaleContactController getPpcController() {
        return ppcController;
    }

    public void setPpcController(PanneauPrincipaleContactController ppcController) {
        this.ppcController = ppcController;
    }
    
    
}
