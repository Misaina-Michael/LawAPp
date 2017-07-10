/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers.intervenant;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modeles.intervenants.Intervenant;
import modeles.parametres.Fonction;
import org.hibernate.exception.ConstraintViolationException;
import services.BaseService;
import services.IntervenantService;
import statiques.StageStatique;

/**
 * FXML Controller class
 *
 * @author RABENANTOANDRO
 */
public class FicheIntervenantController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private Intervenant intervenant;
    private IntervenantAccueilController intervController;
    BaseService bs = new BaseService();
    @FXML
    private Label nom;
    @FXML
    private Button updateIntervenant;
    @FXML
    private Label prenom;
    @FXML
    private Label adresse;
    @FXML
    private Label pays;
    @FXML
    private Label ville;
    @FXML
    private Label fonction;
    @FXML
    private Label compteTiers;
    @FXML
    private Label bureau;
    @FXML
    private Label domicile;
    @FXML
    private Label fax;
    @FXML
    private Label email;
    @FXML
    private Label mobile;
    @FXML
    private Label commentaire;
    @FXML
    private Label seuilbas;
    @FXML
    private Label seuilinterm;
    @FXML
    private Label seuilhaut;
    @FXML
    private Label seuiljournalier;
    @FXML
    private Label seuilhoraire;
    @FXML
    private Label bic;
    @FXML
    private Label rib;
    @FXML
    private Label iban;

    public Intervenant getIntervenant() {
        return intervenant;
    }

    public void setIntervenant(Intervenant intervenant) {
        this.intervenant = intervenant;
    }

    public IntervenantAccueilController getIntervController() {
        return intervController;
    }

    public void setIntervController(IntervenantAccueilController intervController) {
        this.intervController = intervController;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void intialiseInfoIntervenant() {
        try {
            nom.setText(intervenant.getNom());
            prenom.setText(intervenant.getPrenom());

            Fonction fct = new Fonction();
            fct.setId(intervenant.getIdFonction());
            Fonction f = (Fonction) bs.findById(fct);
            fonction.setText(f.getLibelle());
            adresse.setText(intervenant.getAdresse());
            pays.setText(intervenant.getPays());
            ville.setText(intervenant.getVille());
            compteTiers.setText(intervenant.getComptetiers());
            bureau.setText(intervenant.getBureau());
            domicile.setText(intervenant.getDomicile());
            fax.setText(intervenant.getFax());
            email.setText(intervenant.getEmail());
            mobile.setText(intervenant.getMobile());
            commentaire.setText(intervenant.getCommentaire());
            if (intervenant.getSeuilbas() == null) {
                seuilbas.setText("0");
            } else {
                seuilbas.setText(intervenant.getSeuilbas().toString());
            }
            if (intervenant.getSeuilintermediaire() == null) {
                seuilinterm.setText("0");
            } else {
                seuilinterm.setText(intervenant.getSeuilintermediaire().toString());
            }
            if (intervenant.getSeuilhaut() == null) {
                seuilhaut.setText("0");
            } else {
                seuilhaut.setText(intervenant.getSeuilhaut().toString());
            }
            if (intervenant.getSeuiljournalier() == null) {
                seuiljournalier.setText("0");
            } else {
                seuiljournalier.setText(intervenant.getSeuiljournalier().toString());
            }
            if (intervenant.getSeuilhoraire() == null) {
                seuilhoraire.setText("0");
            } else {
                seuilhoraire.setText(intervenant.getSeuilhoraire().toString());
            }

            bic.setText(intervenant.getBic());
            rib.setText(intervenant.getRib());
            iban.setText(intervenant.getIban());

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @FXML
    public void supprimerButtonAction(ActionEvent event) {
        try {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Supprimer intervenant");
            alert.setHeaderText("Confirmation de suppression");
            alert.setContentText("Confirmez vous la suppression de l' intervenant?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Intervenant x = new Intervenant();
                x.setId(this.getIntervenant().getId());
                IntervenantService intervenantService = new IntervenantService();
                intervenantService.delete(x);
                this.getIntervController().initialiseListeViewIntervenant();
            } else {
                alert.close();
            }
        } 
        catch(ConstraintViolationException ex)
        {
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erreur de suppresion");
            alert.setContentText("Impossible de supprimer cet intervenant. Il existe des références dans la base de données. Veuillez d'avord supprimer ces références");
            alert.show();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @FXML
    public void updateIntervenantClicked(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/intervenant/UpdateIntervenantFormulaire.fxml"));
            Parent root = (Pane) loader.load();
            UpdateIntervenantFormulaireController formulaireupdate = loader.<UpdateIntervenantFormulaireController>getController();
            formulaireupdate.setIntervenant(this.getIntervenant());
            formulaireupdate.intializeComboboxContact();
            formulaireupdate.setAccueilContr(this.getIntervController());

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Modification Intervenant");
            stage.setScene(scene);
            formulaireupdate.setStage(stage);
            StageStatique.setStage2(stage);
            stage.initOwner(StageStatique.getStage1());
            stage.initModality(Modality.WINDOW_MODAL); 
            stage.showAndWait();
            //stage.show();
            System.out.println("UpdateIntervenantFormulaireController");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
