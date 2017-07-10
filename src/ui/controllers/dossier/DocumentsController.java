/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers.dossier;

import java.awt.Desktop;
import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modeles.dossiers.DocumentModele;
import modeles.dossiers.DossierLibelle;
import statiques.StageStatique;
import utilitaire.ConstanteDirectory;
import utilitaire.Util;

/**
 * FXML Controller class
 *
 * @author Misaina
 */
public class DocumentsController implements Initializable {

    @FXML
    private TableView documents;
    @FXML
    private TableColumn nomCol;
    @FXML
    private TableColumn dateCol;
    @FXML
    private ListView tiroirsView;
    @FXML
    private Label nbDocLabel;
    @FXML
    private AnchorPane pa;

    private DossierLibelle dossierLib;
    private String pathDossier;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void initTiroirs() {
        try {
            this.setPathDossier(ConstanteDirectory.getDefaultDirectoryServer() + dossierLib.getNumeroDossier());
            File dirDossier = new File(this.getPathDossier());
            File[] listDirectory = dirDossier.listFiles();
            String[] nomDossier = new String[listDirectory.length];
            for (int i = 0; i < listDirectory.length; i++) {
                nomDossier[i] = listDirectory[i].getName();
            }
            ObservableList<String> data;
            data = FXCollections.observableArrayList();
            data.setAll(nomDossier);
            tiroirsView.setItems(data);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void filesTiroir(String path) {

        File dirDossier = new File(path);
        File[] listFiles = dirDossier.listFiles();

        List<DocumentModele> listeDocument = new ArrayList<DocumentModele>(listFiles.length);
        for (int i = 0; i < listFiles.length; i++) {
            DocumentModele dm = new DocumentModele();
            dm.setNom(listFiles[i].getName());
            dm.setDateModif(new Date(listFiles[i].lastModified()));
            listeDocument.add(dm);
        }
        nbDocLabel.setText(listeDocument.size() + " document(s)");
        nomCol.setCellValueFactory(new PropertyValueFactory<DocumentModele, String>("nom"));
        dateCol.setCellValueFactory(new PropertyValueFactory<DocumentModele, Date>("dateModif"));
        ObservableList<DocumentModele> data;
        data = FXCollections.observableArrayList();
        data.setAll(listeDocument);
        documents.setItems(data);
        dateCol.setCellFactory(column -> {
            return new TableCell<DocumentModele, Date>() {
                protected void updateItem(Date item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null) {
                        setText(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(item));
                    }
                }
            };
        });
    }

    public void tiroirClicked(MouseEvent me) {
        try {
            filesTiroir(this.getPathDossier() + "/" + tiroirsView.getSelectionModel().getSelectedItem());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void viewFile(MouseEvent ae) {
        try {
            if (ae.getClickCount() == 2) {
                DocumentModele dmSel = (DocumentModele) documents.getSelectionModel().getSelectedItem();
                File file = new File(this.getPathDossier() + "/" + tiroirsView.getSelectionModel().getSelectedItem() + "/" + dmSel.getNom());
                Desktop desktop = Desktop.getDesktop();
                desktop.open(file);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void addFile(ActionEvent ae) {
        try {
            FileChooser fileChooser = new FileChooser();

            File file = fileChooser.showOpenDialog(StageStatique.getStage1());
            if (file != null) {
                Path fileToCpy = Paths.get(file.getAbsolutePath());
                Path fileCopy = Paths.get(this.getPathDossier() + "/" + tiroirsView.getSelectionModel().getSelectedItem() + "/" + file.getName());
                Path newFile = Files.copy(fileToCpy, fileCopy);
                filesTiroir(this.getPathDossier() + "/" + tiroirsView.getSelectionModel().getSelectedItem());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void deleteFile(ActionEvent ae) {
        try {
            DocumentModele dmSel = (DocumentModele) documents.getSelectionModel().getSelectedItem();
            Files.delete(Paths.get(this.getPathDossier() + "/" + tiroirsView.getSelectionModel().getSelectedItem() + "/" + dmSel.getNom()));
            filesTiroir(this.getPathDossier() + "/" + tiroirsView.getSelectionModel().getSelectedItem());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Ajout d'un nouveau tiroir au dossier
     *
     * @param ae FireMan_18
     */
    public void addDir(ActionEvent ae) {
        try {
            Stage s = new Stage();
            AddDirController addDirController = null;
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/dossier/AddDir.fxml"));
                Parent root = (Pane) loader.load();
                addDirController = loader.<AddDirController>getController();                
                addDirController.setDossier(dossierLib);
                addDirController.stage = s;
                addDirController.documentsController = this;
                Scene sc = new Scene(root);
                s.setScene(sc);
                StageStatique.setStage3(s);
                s.initOwner(StageStatique.getStage2());
                s.initModality(Modality.WINDOW_MODAL);
                s.showAndWait();
                
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Suppression d'un tiroir du dossier et de son contenu
     *
     * @param ae
     */
    public void delDir(ActionEvent ae) {
        try {
            Util util = new Util();
            File dir = new File(this.getPathDossier() + "/" + tiroirsView.getSelectionModel().getSelectedItem());
            util.recursifDelete(dir);
            initTiroirs();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public DossierLibelle getDossierLib() {
        return dossierLib;
    }

    public void setDossierLib(DossierLibelle dossierLib) {
        this.dossierLib = dossierLib;
    }

    public String getPathDossier() {
        return pathDossier;
    }

    public void setPathDossier(String pathDossier) {
        this.pathDossier = pathDossier;
    }

}
