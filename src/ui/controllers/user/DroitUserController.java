/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers.user;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import modeles.intervenants.Intervenant;
import modeles.parametres.Fonction;
import modeles.parametres.Titre;
import services.IntervenantService;
import statiques.ObjetStatique;
import utilitaire.ComboboxUtilitaire;

/**
 * FXML Controller class
 *
 * @author RABENANTOANDRO
 */
public class DroitUserController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    ComboBox contact;
    @FXML
    ComboBox client;
    @FXML
    ComboBox intervenant;
    @FXML
    ComboBox dossier;
    @FXML
    ComboBox utilisateur;
    @FXML
    ComboBox planning;
    @FXML
    ComboBox factures;
    @FXML
    ComboBox feuilledetemps;
    @FXML
    Button saveDroitButton;
    private Intervenant intervenantDroit;
    private ConfigurationController configController;

    public ConfigurationController getConfigController() {
        return configController;
    }

    public void setConfigController(ConfigurationController configController) {
        this.configController = configController;
    }

    public Intervenant getIntervenantDroit() {
        return intervenantDroit;
    }

    public void setIntervenantDroit(Intervenant intervenantDroit) {
        this.intervenantDroit = intervenantDroit;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void initialiseDroitsUser() {
        ObservableList<String> dataTitre;
        dataTitre = FXCollections.observableArrayList();
        dataTitre.setAll(ObjetStatique.getDroitUtilisateur());
        contact.setItems(dataTitre);
        contact.setValue(this.getIntervenantDroit().getContact());

        ObservableList<String> dataTitreclient;
        dataTitreclient = FXCollections.observableArrayList();
        dataTitreclient.setAll(ObjetStatique.getDroitUtilisateur());
        client.setItems(dataTitre);
        client.setValue(this.getIntervenantDroit().getClient());

        ObservableList<String> dataTitreintervenant;
        dataTitreintervenant = FXCollections.observableArrayList();
        dataTitreintervenant.setAll(ObjetStatique.getDroitUtilisateur());
        intervenant.setItems(dataTitre);
        intervenant.setValue(this.getIntervenantDroit().getIntervenant());

        ObservableList<String> dataTitredossier;
        dataTitredossier = FXCollections.observableArrayList();
        dataTitredossier.setAll(ObjetStatique.getDroitUtilisateur());
        dossier.setItems(dataTitre);
        dossier.setValue(this.getIntervenantDroit().getDossier());

        ObservableList<String> dataTitreutilisateur;
        dataTitreutilisateur = FXCollections.observableArrayList();
        dataTitreutilisateur.setAll(ObjetStatique.getDroitUtilisateur());
        utilisateur.setItems(dataTitre);
        utilisateur.setValue(this.getIntervenantDroit().getUtilisateur());

        ObservableList<String> dataTitreplanning;
        dataTitreplanning = FXCollections.observableArrayList();
        dataTitreplanning.setAll(ObjetStatique.getDroitUtilisateur());
        planning.setItems(dataTitre);
        planning.setValue(this.getIntervenantDroit().getPlanning());
        
        ObservableList<String> dataTitrefactures;
        dataTitrefactures = FXCollections.observableArrayList();
        dataTitrefactures.setAll(ObjetStatique.getDroitUtilisateur());
        factures.setItems(dataTitre);
        factures.setValue(this.getIntervenantDroit().getFactures());
        
         ObservableList<String> dataTitrefeuilledetemps;
        dataTitrefeuilledetemps = FXCollections.observableArrayList();
        dataTitrefeuilledetemps.setAll(ObjetStatique.getDroitUtilisateur());
        feuilledetemps.setItems(dataTitre);
        feuilledetemps.setValue(this.getIntervenantDroit().getFactures());

    }

    @FXML
    public void saveDroitClick(ActionEvent event) {
        //(String) this.fonction.getSelectionModel().getSelectedItem()
        try {
            Intervenant t = new Intervenant();
            t.setId(this.getIntervenantDroit().getId());
            t.setAdresse(this.getIntervenantDroit().getAdresse());
            t.setBic(this.getIntervenantDroit().getBic());
            t.setBureau(this.getIntervenantDroit().getBureau());
            t.setClient((String) this.client.getSelectionModel().getSelectedItem());
            t.setCode(this.getIntervenantDroit().getCode());
            t.setCodepostal(this.getIntervenantDroit().getCodepostal());
            t.setCommentaire(this.getIntervenantDroit().getCommentaire());
            t.setComptetiers(this.getIntervenantDroit().getComptetiers());
            t.setContact((String) this.contact.getSelectionModel().getSelectedItem());
            t.setDemandeur(this.getIntervenantDroit().getDemandeur());
            t.setDomicile(this.getIntervenantDroit().getDomicile());
            t.setDomiciliation(this.getIntervenantDroit().getDomiciliation());
            t.setDossier((String) this.dossier.getSelectionModel().getSelectedItem());
            t.setEmail(this.getIntervenantDroit().getEmail());
            t.setFax(this.getIntervenantDroit().getFax());
            t.setGestionnaire(this.getIntervenantDroit().getGestionnaire());
            t.setIban(this.getIntervenantDroit().getIban());
            t.setIdFonction(this.getIntervenantDroit().getIdFonction());
            t.setIdTypeUser(this.getIntervenantDroit().getIdTypeUser());
            t.setIntervenant((String) this.intervenant.getSelectionModel().getSelectedItem());
            t.setLogin(this.getIntervenantDroit().getLogin());
            t.setMdp(this.getIntervenantDroit().getMdp());
            t.setMobile(this.getIntervenantDroit().getMobile());
            t.setNom(this.getIntervenantDroit().getNom());
            t.setPays(this.getIntervenantDroit().getPays());
            t.setPrenom(this.getIntervenantDroit().getPrenom());
            t.setRib(this.getIntervenantDroit().getRib());
            t.setSeuilbas(this.getIntervenantDroit().getSeuilbas());
            t.setSeuilhaut(this.getIntervenantDroit().getSeuilhaut());
            t.setSeuilhoraire(this.getIntervenantDroit().getSeuilhoraire());
            t.setSeuilintermediaire(this.getIntervenantDroit().getSeuilintermediaire());
            t.setSeuiljournalier(this.getIntervenantDroit().getSeuiljournalier());
            t.setTitulaire(this.getIntervenantDroit().getTitulaire());
            t.setToque(this.getIntervenantDroit().getToque());
            t.setVille(this.getIntervenantDroit().getVille());
            t.setWeb(this.getIntervenantDroit().getWeb());
            t.setUtilisateur((String) this.utilisateur.getSelectionModel().getSelectedItem());
            t.setPlanning((String) this.planning.getSelectionModel().getSelectedItem());
            t.setFeuilledeTemps((String) this.feuilledetemps.getSelectionModel().getSelectedItem());
            t.setFactures((String) this.factures.getSelectionModel().getSelectedItem());

            IntervenantService is = new IntervenantService();
            is.updateIntervenant(t);
            this.getConfigController().initialiseListViewUser();
            contact.setValue(t.getContact());
            client.setValue(t.getClient());
            intervenant.setValue(t.getIntervenant());
            dossier.setValue(t.getDossier());
            utilisateur.setValue(t.getUtilisateur());
            planning.setValue(t.getPlanning());
            feuilledetemps.setValue(t.getFeuilledeTemps());
            factures.setValue(t.getFactures());

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}
