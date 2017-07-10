/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers.user;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import modeles.intervenants.Intervenant;
import services.BaseService;
import services.IntervenantService;

/**
 * FXML Controller class
 *
 * @author RABENANTOANDRO
 */
public class FicheUserController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    Label nom;
    @FXML
    Label prenom;
    @FXML
    TextField login;
    @FXML
    PasswordField mdp;
    @FXML
    CheckBox administrateurCheckbox;
    private ConfigurationController config;
    private Intervenant intervenant;
    BaseService bs = new BaseService();

    public Intervenant getIntervenant() {
        return intervenant;
    }

    public void setIntervenant(Intervenant intervenant) {
        this.intervenant = intervenant;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void IntitialiseDataFicheUser() throws Exception {
        try {
            nom.setText(this.getIntervenant().getNom());
            prenom.setText(this.getIntervenant().getPrenom());
            login.setText(this.getIntervenant().getLogin());
            mdp.setText(this.getIntervenant().getMdp());
            System.out.println("huhu" + this.getIntervenant().getLogin());
            if (this.getIntervenant().getIdTypeUser() == 1) {
                administrateurCheckbox.setSelected(true);
            } else {
                administrateurCheckbox.setSelected(false);
            }
        } catch (Exception ex) {
            throw ex;
        }

    }

    public void checkifExist() throws Exception {
        try {
            IntervenantService intervenantService = new IntervenantService();
            Intervenant intervenant = new Intervenant();
            intervenant.setLogin(login.getText());
            intervenant.setMdp(mdp.getText());
            BaseService bs = new BaseService();
            List<Intervenant> listeInterv = intervenantService.findWhere(intervenant);
            if (listeInterv.size() > 0) {
                throw new Exception("Cet utilisateur existe déjà");
            }
        } catch (Exception ex) {
            throw ex;
        }

    }

    @FXML
    public void saveDataClicked(ActionEvent event) {
        try {

            Intervenant interv = new Intervenant();
            interv.setId(this.getIntervenant().getId());
            interv.setIdFonction(this.getIntervenant().getIdFonction());
            if (administrateurCheckbox.isSelected()) {
                interv.setIdTypeUser(1);
            } else {
                interv.setIdTypeUser(2);
            }
            interv.setNom(this.getIntervenant().getNom());
            interv.setPrenom(this.getIntervenant().getPrenom());
            interv.setGestionnaire(this.getIntervenant().getGestionnaire());
            interv.setDemandeur(this.getIntervenant().getDemandeur());
            interv.setComptetiers(this.getIntervenant().getComptetiers());
            interv.setAdresse(this.getIntervenant().getAdresse());
            interv.setPays(this.getIntervenant().getPays());
            interv.setCodepostal(this.getIntervenant().getCodepostal());
            interv.setVille(this.getIntervenant().getVille());
            interv.setBureau(this.getIntervenant().getBureau());
            interv.setDomicile(this.getIntervenant().getDomicile());
            interv.setFax(this.getIntervenant().getFax());
            interv.setEmail(this.getIntervenant().getEmail());
            interv.setMobile(this.getIntervenant().getMobile());
            interv.setWeb(this.getIntervenant().getWeb());
            interv.setToque(this.getIntervenant().getToque());
            interv.setSeuilbas(this.getIntervenant().getSeuilbas());
            interv.setSeuilintermediaire(this.getIntervenant().getSeuilintermediaire());
            interv.setSeuilhaut(this.getIntervenant().getSeuilhaut());
            interv.setSeuiljournalier(this.getIntervenant().getSeuiljournalier());
            interv.setSeuilhoraire(this.getIntervenant().getSeuilhoraire());
            interv.setTitulaire(this.getIntervenant().getTitulaire());
            interv.setRib(this.getIntervenant().getRib());
            interv.setDomiciliation(this.getIntervenant().getDomiciliation());
            interv.setIban(this.getIntervenant().getIban());
            interv.setBic(this.getIntervenant().getBic());
            interv.setCommentaire(this.getIntervenant().getCommentaire());
            interv.setLogin(this.login.getText());
            interv.setMdp(this.mdp.getText());
            interv.setCode(this.getIntervenant().getCode());
            interv.setIntervenant(this.getIntervenant().getIntervenant());
            interv.setContact(this.getIntervenant().getContact());
            interv.setClient(this.getIntervenant().getClient());
            interv.setDossier(this.getIntervenant().getDossier());
            interv.setFactures(this.getIntervenant().getFactures());
            interv.setFeuilledeTemps(this.getIntervenant().getFeuilledeTemps());
            interv.setPlanning(this.getIntervenant().getPlanning());
            interv.setUtilisateur(this.getIntervenant().getUtilisateur());
            this.checkifExist();
           
            bs.update(interv);
             getConfig().initialiseListViewUser();

        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Erreur");
            alert.setContentText(ex.getMessage());
            alert.show();
        }
    }

    public ConfigurationController getConfig() {
        return config;
    }

    public void setConfig(ConfigurationController config) {
        this.config = config;
    }

}
