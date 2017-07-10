/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers;

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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import modeles.evenement.EvtDossierLibelle;
import modeles.facturation.FactureLibelle;
import modeles.intervenants.Intervenant;
import modeles.parametres.UserSession;
import services.BaseService;
import services.IntervenantService;
import statiques.StageStatique;
import ui.controllers.user.ConfigurationController;
import utilitaire.UiUtil;

/**
 * FXML Controller class
 *
 * @author Misaina
 */
public class PrimaryStageController implements Initializable {

    @FXML
    private Pane panneauHaut;
    @FXML
    private Pane panneauPrincipal;
    @FXML
    private Pane panneauBas;
    @FXML
    private ImageView logoFenetre;
    @FXML
    private VBox vbox;
    @FXML
    private Label titreFenetre;
    @FXML
    private Label descriptionFenetre;
    @FXML
    private Label descFenetre;
    @FXML
    private Label textBas;
    @FXML
    private TextField essai;
    @FXML
    private AnchorPane scene;
    @FXML
    private Button clientButton;
    @FXML
    private Button IntervenantButton;
    @FXML
    private Button dossierButton;
    @FXML
    private Button ContactButton;
    @FXML
    private Button feuilleTemps;
    @FXML
    private Button factureButton;
    @FXML
    private Button planning;
    @FXML
    private Button utilisateur;
    @FXML
    private MenuItem menuItemUtilisateur;
    @FXML
    private Label nomuser;
    @FXML
    private TableView tabTaches;
    @FXML
    private TableColumn tacheCol;
    @FXML
    private TableColumn obsCol;
    @FXML
    private TableColumn dossCol;

    private static Stage stg;
    public static Stage tempStage = null;

    private PrimaryStageController psController;
    IntervenantService intervenantService = new IntervenantService();
    List<Intervenant> listeInterv = null;
    private Stage st;
    private Stage currentStage;

    public Stage getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(Stage currentStage) {
        this.currentStage = currentStage;
    }

    public static Stage getStg() {
        return stg;
    }

    public static void setStg(Stage stg) {
        PrimaryStageController.stg = stg;
    }

    public Stage getSt() {
        return st;
    }

    public void setSt(Stage st) {
        this.st = st;
    }

    public PrimaryStageController getPsController() {
        return psController;
    }

    public void setPsController(PrimaryStageController psController) {
        this.psController = psController;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("____________________première lancement de l'application____________________");
        try {
            //PrimaryStageController.tempStage.close();
            if (PrimaryStageController.tempStage != null) {
                PrimaryStageController.tempStage.close();
            }
            if (UserSession.getIntervenantUserSession().getClient().equalsIgnoreCase("aucun")) {
                clientButton.setDisable(true);
            }
            if (UserSession.getIntervenantUserSession().getIntervenant().equalsIgnoreCase("aucun")) {
                IntervenantButton.setDisable(true);
            }
            if (UserSession.getIntervenantUserSession().getDossier().equalsIgnoreCase("aucun")) {
                dossierButton.setDisable(true);
            }
            if (UserSession.getIntervenantUserSession().getContact().equalsIgnoreCase("aucun")) {
                ContactButton.setDisable(true);
            }
            if (UserSession.getIntervenantUserSession().getFeuilledeTemps().equalsIgnoreCase("aucun")) {
                feuilleTemps.setDisable(true);
            }
            if (UserSession.getIntervenantUserSession().getFactures().equalsIgnoreCase("aucun")) {
                factureButton.setDisable(true);
            }
            if (UserSession.getIntervenantUserSession().getPlanning().equalsIgnoreCase("aucun")) {
                planning.setDisable(true);
            }
            if (UserSession.getIntervenantUserSession().getUtilisateur().equalsIgnoreCase("aucun")) {
                utilisateur.setDisable(true);
            }
            Image test = new Image(getClass().getResourceAsStream("social-1.png"));
            nomuser.setText(UserSession.getIntervenantUserSession().getNom() + " " + UserSession.getIntervenantUserSession().getPrenom());
            fillTachesNow();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @FXML
    public void menuClicked(ActionEvent event) {
        String buttonId = ((Button) event.getSource()).getId();
        UiUtil uitil = new UiUtil();

        try {
            switch (buttonId) {
                case "clientButton":
                    uitil.changePanneauPrincipal(scene, "/ui/fxml/client/PanneauPrincipalClient.fxml");
                    break;
                case "dossierButton":
                    uitil.changePanneauPrincipal(scene, "/ui/fxml/dossier/Liste.fxml");
                    break;
                case "ContactButton":
                    uitil.changePanneauPrincipal(scene, "/ui/fxml/contact/contactAccueil.fxml");
                    break;
                case "IntervenantButton":
                    uitil.changePanneauPrincipal(scene, "/ui/fxml/intervenant/IntervenantAccueil.fxml");
                    break;
                case "factureButton":
                    uitil.changePanneauPrincipal(scene, "/ui/fxml/facturation/ListeFacture.fxml");
                    break;
                case "feuilleTemps":
                    uitil.changePanneauPrincipal(scene, "/ui/fxml/feuilletemps/FeuilleTempsAccueil.fxml");
                    break;

                case "planning":
                    uitil.changePanneauPrincipal(scene, "/ui/fxml/planning/planningGenerale.fxml");
                    break;

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void fermer(ActionEvent ae) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ui/fxml/PrimaryStage.fxml"));
            vbox.getChildren().clear();
            vbox.getChildren().add(root);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private JasperPrint fillReport(String reportPath, List dataSource, Map param) throws JRException {
        if (dataSource == null || dataSource.isEmpty()) {
            return JasperFillManager.fillReport(reportPath, param, new JREmptyDataSource());
        } else {
            JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(dataSource);
            return JasperFillManager.fillReport(reportPath, param, beanCollectionDataSource);
        }
    }

    public void parametresMenu(ActionEvent ae) {
        Stage s = null;
        try {
            s = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/parametres/Parametres.fxml"));
            Parent root = (AnchorPane) loader.load();
//            ParametresController parametreCtrl=loader.<ParametresController>getController();
            Scene scene = new Scene(root);
            s.setScene(scene);
            StageStatique.setStage2(s);
            s.initOwner(StageStatique.getStage1());
            s.initModality(Modality.WINDOW_MODAL);
            s.showAndWait();
            //s.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    public void configUserMenu(ActionEvent event) {

        try {

            PrimaryStageController ps = this;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/user/Configuration.fxml"));
            Parent root = (Pane) loader.load();
            ConfigurationController configurationController = loader.<ConfigurationController>getController();
            //configurationController.initialiseListViewUser();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Utilisateurs");
            stage.show();
            stage.setOnHiding(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    try {
                        //Integer idencourstemp = UserSession.getIntervenantUserSession().getId();
                        IntervenantService intervenantService = new IntervenantService();
                        Intervenant intervenant = new Intervenant();
                        intervenant.setLogin(UserSession.getIntervenantUserSession().getLogin());
                        intervenant.setMdp(UserSession.getIntervenantUserSession().getMdp());
                        List<Intervenant> listeInterv = intervenantService.findWhere(intervenant);
                        UserSession.setIntervenantUserSession(listeInterv.get(0));
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/PrimaryStage.fxml"));
                        Parent root = (Pane) loader.load();
                        //PrimaryStageController primaryController = loader.<PrimaryStageController>getController();
                        Scene scene = new Scene(root);
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        PrimaryStageController.tempStage = stage;
                        PrimaryStageController.getStg().close();
                        stage.show();
                        System.out.println(/*idencourstemp + */"Test Alpha - " + listeInterv.get(0).getNom() + " - " + listeInterv.get(0).getPrenom() + " -- " + listeInterv.size() + " -" + listeInterv.get(0).getId());
                        System.out.println("La fenetre se ferme ");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void fillTachesNow() {
        BaseService bs = null;
        try {
            bs = new BaseService();
            dossCol.setCellValueFactory(new PropertyValueFactory<EvtDossierLibelle, String>("vnomdossier"));

            tacheCol.setCellValueFactory(new PropertyValueFactory<EvtDossierLibelle, String>("libelle"));
            obsCol.setCellValueFactory(new PropertyValueFactory<EvtDossierLibelle, String>("nomDemandeur"));

            EvtDossierLibelle evl = new EvtDossierLibelle();
            evl.setIdIntervenant(UserSession.getIntervenantUserSession().getId());
            evl.setDaty(new Date());
            evl.setAfacturer(true);
            List<EvtDossierLibelle> liste = (List<EvtDossierLibelle>) (List<?>) bs.find(evl);
            ObservableList<EvtDossierLibelle> data;
            data = FXCollections.observableArrayList();
            data.setAll(liste);
            tabTaches.setItems(data);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
