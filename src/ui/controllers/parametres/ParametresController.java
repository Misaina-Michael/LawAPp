/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers.parametres;

import java.net.URL;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modeles.evenement.EvtTarif;
import modeles.evenement.EvtTarifLibelle;
import modeles.intervenants.FonctionTarifEvt;
import modeles.intervenants.FonctionTarifEvtLibelle;
import modeles.parametres.DefaultDir;
import modeles.parametres.Fonction;
import modeles.parametres.Juridiction;
import modeles.parametres.ModeFacturation;
import modeles.parametres.Nature;
import modeles.parametres.Titre;
import modeles.parametres.TypeFacture;
import modeles.parametres.TypeProcedure;
import modeles.parametres.TypeTarifEvt;
import org.hibernate.exception.ConstraintViolationException;
import services.BaseService;
import services.EvenementService;
import services.FonctionService;
import statiques.StageStatique;
import ui.controllers.ListeGeneriqueController;
import ui.controllers.crud.ListeController;
import utilitaire.ListViewUtil;

/**
 * FXML Controller class
 *
 * @author Misaina
 */
public class ParametresController implements Initializable {

    EvenementService evtServ = new EvenementService();
    BaseService bs = new BaseService();
    @FXML
    private Pane paneProcedure;
    @FXML
    private Pane paneTypeFacture;
    @FXML
    private Pane paneFonction;
    @FXML
    private Pane panTitre;
    @FXML
    private Pane panDefDir;
    @FXML
    private Pane panJuridiction;
    @FXML
    private Pane paneSection;
    @FXML
    private Pane panModeFacturation;

    @FXML
    private Pane paneNature;
    @FXML
    private TableView intervenantFonctionTableview;
    @FXML
    private TableView tableCotation;
    @FXML
    private TableColumn fonctionColumn;
    @FXML
    private TableColumn intervenantColumn;
    @FXML
    private ListView listeTarifNormauxFonctions;
    @FXML
    private ListeGeneriqueController listeEvtTarifsController;
    @FXML
    private Button supprimerBoutton;
    @FXML
    private TableColumn typeCotation;
    @FXML
    private TableColumn libelleCotation;
    @FXML
    private TableColumn codeCotation;
    @FXML
    private TableColumn dureeCotation;
    @FXML
    private TableColumn numeroCotation;
    @FXML
    private TableColumn montantCotation;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            this.fonctionIntervenant();
            this.tableSelectionFonction();
            this.initialiseTableViewCotation();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void fonctionIntervenant() throws Exception {

        fonctionColumn.setCellValueFactory(new PropertyValueFactory<>("libelle"));
        FonctionService fs = new FonctionService();
        List<Fonction> fi = fs.findFonction(new Fonction());

        ObservableList<Fonction> data = FXCollections.observableArrayList();
        for (int compteur1 = 0; compteur1 < fi.size(); compteur1++) {
            data.add(fi.get(compteur1));
        }
        intervenantFonctionTableview.getColumns().clear();
        intervenantFonctionTableview.setItems(data);
        intervenantFonctionTableview.getColumns().addAll(fonctionColumn);

    }

    public void ajouterTarifEvent(ActionEvent event) {
        Stage stage = new Stage();
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/ListeGenerique.fxml"));
            Parent root = (Pane) loader.load();
            listeEvtTarifsController = loader.<ListeGeneriqueController>getController();
            EvtTarif crit = new EvtTarif();
            crit.setType("tn");
            listeEvtTarifsController.setBase(crit);
            listeEvtTarifsController.getValider().setVisible(false);
            listeEvtTarifsController.getCustomValid().setVisible(true);
            String[] cols = new String[]{"libelle"};
            listeEvtTarifsController.setCols(cols);
            listeEvtTarifsController.getCustomValid().setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                    System.out.println("ok");
                    BaseService bs = null;
                    try {
                        bs = new BaseService();
                        listeEvtTarifsController.getItemSelected();
                        listeEvtTarifsController.validAction(event);
                        EvtTarif evtTar = (EvtTarif) listeEvtTarifsController.getItemSelected();
                        Fonction fonct = (Fonction) intervenantFonctionTableview.getSelectionModel().getSelectedItem();
                        FonctionTarifEvt res = new FonctionTarifEvt();
                        res.setIdFonction(fonct.getId());
                        res.setIdEvtTarif(evtTar.getId());

                        bs.save(res);
                        initializeListViewEvenements();
                        //initApporteur();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
            listeEvtTarifsController.setChamp(new TextField());
            listeEvtTarifsController.initializeListe();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            listeEvtTarifsController.setStage(stage);
            StageStatique.setStage3(stage);
            stage.initOwner(StageStatique.getStage2());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.showAndWait();
            //s.show();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @FXML
    public void supprimerBouttonEvent(ActionEvent event) throws Exception {
        try {
            BaseService bs = new BaseService();
            FonctionTarifEvtLibelle evt = (FonctionTarifEvtLibelle) listeTarifNormauxFonctions.getSelectionModel().getSelectedItem();
            FonctionTarifEvt fte = new FonctionTarifEvt();
            fte.setId(evt.getId());
            bs.delete(fte);
            initializeListViewEvenements();
        } catch (ConstraintViolationException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erreur de suppresion");
            alert.setContentText("Impossible de supprimer ce Contact. Il est actif dans le logiciel . Veuillez d'avord supprimer ses références");
            alert.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void initializeListViewEvenements() throws Exception {
        try {
            Fonction f = (Fonction) intervenantFonctionTableview.getSelectionModel().getSelectedItem();
            FonctionService fs = new FonctionService();
            FonctionTarifEvtLibelle ft = new FonctionTarifEvtLibelle();
            ft.setIdFonction(f.getId());
            List<FonctionTarifEvtLibelle> liste = fs.findFonctionTarifEvtLibelle(ft);
            ObservableList<FonctionTarifEvtLibelle> data;
            data = FXCollections.observableArrayList();
            data.setAll(liste);
            listeTarifNormauxFonctions.setItems(data);
            String[] nomCols = new String[1];
            nomCols[0] = "libelle";
            listeTarifNormauxFonctions.setCellFactory(new ListViewUtil().buildCellFactory(nomCols, " "));
        } catch (Exception ex) {
            throw ex;
        }

    }

    public void tableSelectionFonction() {
        intervenantFonctionTableview.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                try {
                    if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                        initializeListViewEvenements();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public void initialiseTableViewCotation() throws Exception {
        try {
            typeCotation.setCellValueFactory(new PropertyValueFactory<>("libelleTypeTarif"));
            libelleCotation.setCellValueFactory(new PropertyValueFactory<>("libelle"));
            codeCotation.setCellValueFactory(new PropertyValueFactory<>("code"));
            dureeCotation.setCellValueFactory(new PropertyValueFactory<>("duree"));
            dureeCotation.setCellFactory(column -> {
                return new TableCell<EvtTarifLibelle, Time>() {
                    protected void updateItem(Time item, boolean empty) {
                        super.updateItem(item, empty);
                        setAlignment(Pos.CENTER);
                        if (item != null) {
                            setText(new SimpleDateFormat("HH:mm").format(item));
                        }
                    }
                };
            });
            numeroCotation.setCellValueFactory(new PropertyValueFactory<>("numero"));
            montantCotation.setCellValueFactory(new PropertyValueFactory<>("montant"));
            EvtTarifLibelle evttariflib = new EvtTarifLibelle();
            evttariflib.setType("tn");
            List<EvtTarifLibelle> evtTarif = evtServ.findEvtTarifLibelle(evttariflib);
            ObservableList<EvtTarifLibelle> data = FXCollections.observableArrayList();
            for (int compteur1 = 0; compteur1 < evtTarif.size(); compteur1++) {
                data.add(evtTarif.get(compteur1));

            }

            tableCotation.getColumns().clear();
            tableCotation.setItems(data);
            tableCotation.getColumns().addAll(typeCotation, libelleCotation, codeCotation, dureeCotation, numeroCotation, montantCotation);

        } catch (Exception ex) {
            throw ex;
        }
    }

    @FXML
    public void ajouterButtonAction(ActionEvent event) throws Exception {
        Stage st = new Stage();
        st.setTitle("Cotation");

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/parametres/CotationFormulaire.fxml"));
            Parent root = (Pane) loader.load();
            CotationFormulaireController detailstarif = loader.<CotationFormulaireController>getController();
            detailstarif.intializeComboboxContact();
            Scene scene = new Scene(root);
            detailstarif.setStg(st);
            detailstarif.setParamContr(this);
            st.setScene(scene);
            StageStatique.setStage3(st);
            st.initOwner(StageStatique.getStage2());
            st.initModality(Modality.WINDOW_MODAL);
            st.showAndWait();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    public void modifierAction(ActionEvent event) throws Exception {
        Stage st = new Stage();
        st.setTitle("Cotation Update");

        try {
            EvtTarifLibelle evtTarif = (EvtTarifLibelle) tableCotation.getSelectionModel().getSelectedItem();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/parametres/CotationUpdate.fxml"));
            Parent root = (Pane) loader.load();
            CotationUpdateController detailstarif = loader.<CotationUpdateController>getController();
            detailstarif.setEvtTarif(evtTarif);
            detailstarif.intializeCombobox();
            detailstarif.setParamContr(this);
            Scene scene = new Scene(root);
            detailstarif.setStg(st);
            st.setScene(scene);
            StageStatique.setStage3(st);
            st.initOwner(StageStatique.getStage2());
            st.initModality(Modality.WINDOW_MODAL);
            st.showAndWait();
            //st.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    public void supprimerAction(ActionEvent event) throws Exception {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Message Utilisateur");
            alert.setHeaderText("Suppression de cotation");
            alert.setContentText("Confirmez vous la suppression de la cotation?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                EvtTarifLibelle evtTarif = (EvtTarifLibelle) tableCotation.getSelectionModel().getSelectedItem();
                EvtTarif x = new EvtTarif();
                x.setId(evtTarif.getId());
                bs.delete(x);
                this.initialiseTableViewCotation();
            } else {
                alert.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void procedure() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/crud/Liste.fxml"));
            Parent root = (Pane) loader.load();
            ListeController listeCtrl = loader.<ListeController>getController();
            TypeProcedure mod = new TypeProcedure();
            mod.setId(1);
            //mod.initValues();
            listeCtrl.setFieldsToShow(new String[]{"libelle"});
            listeCtrl.setTitreLabel("Types");
            listeCtrl.setModele(mod);
            listeCtrl.getTableView().setPrefWidth(300);
            listeCtrl.init(mod);
            paneProcedure.getChildren().add(root);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void dossier() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/crud/Liste.fxml"));
            Parent root = (Pane) loader.load();
            ListeController listeCtrl = loader.<ListeController>getController();
            DefaultDir mod = new DefaultDir();
            mod.setId(1);
            mod.initValues();
            listeCtrl.setFieldsToShow(new String[]{"nom"});
            listeCtrl.setTitreLabel("Tiroirs par défaut des dossiers");
            listeCtrl.setModele(mod);
            listeCtrl.getTableView().setPrefWidth(300);
            listeCtrl.init(mod);
            panDefDir.getChildren().add(root);

            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/ui/fxml/crud/Liste.fxml"));
            Parent root1 = (Pane) loader1.load();
            ListeController listeNatureCtrl = loader1.<ListeController>getController();
            Nature nature = new Nature();
            nature.setId(1);
            nature.initValues();
            listeNatureCtrl.setFieldsToShow(new String[]{"libelle"});
            listeNatureCtrl.setTitreLabel("Nature");
            listeNatureCtrl.setModele(nature);
            listeNatureCtrl.getTableView().setPrefWidth(300);
            listeNatureCtrl.init(nature);
//            listeNatureCtrl.disableActions();
            paneNature.getChildren().add(root1);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void onSelectFonction() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/crud/Liste.fxml"));
            Parent root = (Pane) loader.load();
            ListeController listeCtrl = loader.<ListeController>getController();
            Fonction mod = new Fonction();
            mod.initValues();
            mod.setId(1);
            mod.setClient("aucun");
            mod.setIntervenant("aucun");
            mod.setContact("aucun");
            mod.setDossier("aucun");
            mod.setFactures("aucun");
            mod.setFeuilledeTemps("aucun");
            mod.setPlanning("aucun");
            mod.setUtilisateur("aucun");

            listeCtrl.setFieldsToShow(new String[]{"libelle"});
            listeCtrl.setTitreLabel("Fonction");
            listeCtrl.setModele(mod);
            listeCtrl.getTableView().setPrefWidth(300);
            listeCtrl.init(mod);
            paneFonction.getChildren().add(root);

        } catch (ConstraintViolationException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erreur de suppresion");
            alert.setContentText("Impossible de supprimer cette fonction. Il existe des références dans la base de données. Veuillez d'avord supprimer ces références");
            alert.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void facture() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/crud/Liste.fxml"));
            Parent root = (Pane) loader.load();
            ListeController listeSectionCtrl = loader.<ListeController>getController();
            TypeTarifEvt section = new TypeTarifEvt();
            section.setId(1);
            section.initValues();
            listeSectionCtrl.setFieldsToShow(new String[]{"libelle"});
            listeSectionCtrl.setTitreLabel("Sections");
            listeSectionCtrl.setModele(section);
            listeSectionCtrl.getTableView().setPrefWidth(300);
            listeSectionCtrl.init(section);
            paneSection.getChildren().add(root);

            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/ui/fxml/crud/Liste.fxml"));
            Parent root1 = (Pane) loader1.load();
            ListeController listeTypeFactCtrl = loader1.<ListeController>getController();
            TypeFacture typeFact = new TypeFacture();
            typeFact.setId(1);
            typeFact.initValues();
            listeTypeFactCtrl.setFieldsToShow(new String[]{"libelle"});
            listeTypeFactCtrl.setTitreLabel("Modèles de facture");
            listeTypeFactCtrl.setModele(typeFact);
            listeTypeFactCtrl.getTableView().setPrefWidth(300);
            listeTypeFactCtrl.init(typeFact);
            listeTypeFactCtrl.disableActions();
            paneTypeFacture.getChildren().add(root1);

            FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/ui/fxml/crud/Liste.fxml"));
            Parent root2 = (Pane) loader2.load();
            ListeController listeModeFactCtrl = loader2.<ListeController>getController();
            ModeFacturation modFact = new ModeFacturation();
            modFact.setId(1);
            modFact.initValues();
            listeModeFactCtrl.setFieldsToShow(new String[]{"libelle", "taux"});
            listeModeFactCtrl.setTitreLabel("Mode de facturation");
            listeModeFactCtrl.setModele(modFact);
            listeModeFactCtrl.getTableView().setPrefWidth(300);
            listeModeFactCtrl.init(modFact);

            panModeFacturation.getChildren().add(root2);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void onSelectTitre() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/crud/Liste.fxml"));
            Parent root = (Pane) loader.load();
            ListeController listeSectionCtrl = loader.<ListeController>getController();
            Juridiction section = new Juridiction();
            section.setId(1);
            section.initValues();
            listeSectionCtrl.setFieldsToShow(new String[]{"libelle", "code"});
            listeSectionCtrl.setTitreLabel("Juridiction");
            listeSectionCtrl.setModele(section);
            listeSectionCtrl.getTableView().setPrefWidth(300);
            listeSectionCtrl.init(section);
            panJuridiction.getChildren().add(root);

            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/ui/fxml/crud/Liste.fxml"));
            Parent root1 = (Pane) loader1.load();
            ListeController listeTypeFactCtrl = loader1.<ListeController>getController();
            Titre typeFact = new Titre();
            typeFact.setId(1);
            typeFact.initValues();
            listeTypeFactCtrl.setFieldsToShow(new String[]{"libelle"});
            listeTypeFactCtrl.setTitreLabel("Titre");
            listeTypeFactCtrl.setModele(typeFact);
            listeTypeFactCtrl.getTableView().setPrefWidth(300);
            listeTypeFactCtrl.init(typeFact);
            panTitre.getChildren().add(root1);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
