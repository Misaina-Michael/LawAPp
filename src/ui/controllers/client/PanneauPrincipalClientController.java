/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers.client;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modeles.clients.Client;
import modeles.parametres.TypeFacturationClient;
import org.hibernate.exception.ConstraintViolationException;
import services.BaseService;
import services.ClientService;
import statiques.StageStatique;
import utilitaire.ListViewUtil;
import utilitaire.ReportUtil;
import utilitaire.UiUtil;

/**
 * FXML Controller class
 *
 * @author Misaina
 */
public class PanneauPrincipalClientController implements Initializable {

    private List<Client> liste=null;
    private ClientService clientServ;
    private Client clSelected;
    @FXML private ListView listeClientView;
    @FXML private Pane panPrincipalClient;
    @FXML private TextField zoneRecherche;
    @FXML private Button ajoutButton;
    
    private Button menuActive;
    /**
     * 
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
     
        try
        {
            setMenuActive(ajoutButton);
            clientServ=new ClientService();
            liste=new ArrayList<Client>();
            liste=clientServ.findClient(new Client());
            initializeListeClient();
            createClient();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        
    }    
    
    public void initializeListeClient() throws Exception
    {
        
        try
        {
            
            ObservableList<Client> data;
            data =FXCollections.observableArrayList();
            data.setAll(liste);
            listeClientView.setItems(data);
            
//            ajouter les noms d'attributs à afficher
            String[] nomCols=new String[1];
            nomCols[0]="nom";
            listeClientView.setCellFactory(new ListViewUtil().buildCellFactory(nomCols," "));
//            ajouter listener
            
            listeClientView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Client>() {
                public void changed(ObservableValue<? extends Client> observable,Client oldValue, Client newValue) {
                    try
                    {
                        if(newValue!=null)
                        {
                            panPrincipalClient.getChildren().clear();
                            Client cl=new Client();
                            cl.setId(newValue.getId());
                            clSelected=(Client)clientServ.findById(cl);

                            FXMLLoader loader=new FXMLLoader(getClass().getResource("/ui/fxml/client/FormulaireClient.fxml"));
                            Parent root = (Pane)loader.load();
                            FormulaireClientController ficheCtrl=loader.<FormulaireClientController>getController();
                            ficheCtrl.setAction("update");
                            ficheCtrl.setClient(clSelected);
                            ficheCtrl.initializeChamp();
                            panPrincipalClient.getChildren().add(root);
                        }
                        
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
            throw ex;
        }
    }
    
    public void rechercher()
    {
        try
        {
            liste.clear();
            Client c=new Client();
            c.setNom(zoneRecherche.getText());
            liste=clientServ.findClient(c);
            initializeListeClient();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        
    }

    public ClientService getClientServ() {
        return clientServ;
    }

    public void setClientServ(ClientService clientServ) {
        this.clientServ = clientServ;
    }

    public void onSelectionChanged(){
        System.out.println("change");
    }
    public void create(ActionEvent ae){
        try
        {
            changeMenuActive((Button)ae.getSource());
            createClient() ;
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void createClient() 
    {
 
        try
        {
            
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/ui/fxml/client/FormulaireClient.fxml"));
            Parent root = (Pane)loader.load();
            FormulaireClientController formCtrl=loader.<FormulaireClientController>getController();
            Client cl=new Client();
            cl.setId(1);
            cl.initValues();
            cl.setNom("(Nouveau)");

            formCtrl.setClient(cl);
            formCtrl.setAction("create");
            formCtrl.initializeChamp();
            formCtrl.setPanPrClCtrl(this);
            panPrincipalClient.getChildren().clear();
            panPrincipalClient.getChildren().add(root);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        
    }

    public void buttonAction(ActionEvent ae)
    {
        String buttonId=((Button)ae.getSource()).getId();
        UiUtil uitil=new UiUtil();
        
        try {
            changeMenuActive((Button)ae.getSource());
            switch (buttonId){
                case "ficheButton":
                    panPrincipalClient.getChildren().clear();
                    Client cl=new Client();
                    cl.setId(((Client)listeClientView.getSelectionModel().getSelectedItem()).getId());
                    clSelected=(Client)clientServ.findById(cl);

                    FXMLLoader loaderFiche=new FXMLLoader(getClass().getResource("/ui/fxml/client/FormulaireClient.fxml"));
                    Parent rootFiche = (Pane)loaderFiche.load();
                    FormulaireClientController ficheCtrl=loaderFiche.<FormulaireClientController>getController();
                    ficheCtrl.setClient(clSelected);
                    ficheCtrl.initializeChamp();
                    panPrincipalClient.getChildren().add(rootFiche);
                    break;
                case "facturationButton":
                    Stage s=new Stage();
                    FXMLLoader loader=new FXMLLoader(getClass().getResource("/ui/fxml/client/FacturationClient.fxml"));
                    Parent root = (AnchorPane)loader.load();
                    FacturationClientController factCtrl=loader.<FacturationClientController>getController();
                    factCtrl.setClient(clSelected);
                    TypeFacturationClient tf=new TypeFacturationClient();
                    tf.setIdClient(clSelected.getId());
                    BaseService bs=new BaseService();
                    factCtrl.setTypeFacturation((TypeFacturationClient)bs.find(tf).get(0));
                    factCtrl.setStage(s);
                    factCtrl.initializeChamp();
                    Scene scene = new Scene(root);
                    s.setScene(scene);
                    StageStatique.setStage2(s);
                    s.initOwner(StageStatique.getStage1());
                    s.initModality(Modality.WINDOW_MODAL); 
                    s.showAndWait();
                    break;
                default:
                    System.out.println("default");
            }
        } 
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void contactButton(ActionEvent ae)
    {
        try
        {
            changeMenuActive((Button)ae.getSource());
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/ui/fxml/client/ContactClient.fxml"));
            Parent root = (Pane)loader.load();
            ContactClientController ctrl=loader.<ContactClientController>getController();
            ctrl.setClient(clSelected);
            ctrl.initializeListe();
            panPrincipalClient.getChildren().clear();
            panPrincipalClient.getChildren().add(root);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public List<Client> getListe() {
        return liste;
    }

    public void setListe(List<Client> liste) {
        this.liste = liste;
    }
    public void delete(ActionEvent event)
    {
        BaseService bs=null;
        try{
            bs=new BaseService();
            Client clSelected=new Client();
            clSelected.setId(((Client)listeClientView.getSelectionModel().getSelectedItem()).getId());
            clSelected=(Client)bs.findById(clSelected);
            bs.delete(clSelected);
            initializeListeClient();
        }
        catch(ConstraintViolationException ex)
        {
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erreur de suppresion");
            alert.setContentText("Impossible de supprimer ce client. Il existe des références dans la base de données. Veuillez d'avord supprimer ces références");
            alert.show();
        }
        catch(Exception ex)
        {
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erreur de suppresion");
            alert.show();
        }
    }
    public void print(ActionEvent ae)
    {
        ReportUtil reportUtil=null;
        try
        {
            ClientService service=new ClientService();
//            System.out.println("iddddd "+((Client)listeClientView.getSelectionModel().getSelectedItem()).getId());
            Map<String, Object> map=service.produceMapFicheClient(this.clSelected.getId());
            reportUtil=new ReportUtil();
            String pathReport=ReportUtil.pathDocs+"/report/client-fiche";
            reportUtil.showViewer(map, pathReport);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        
    }
    public void changeMenuActive(Button button)
    {
        menuActive.getStyleClass().remove("default-active");
        button.getStyleClass().add("default-active");
        setMenuActive(button);
    }

    public Button getMenuActive() {
        return menuActive;
    }

    public void setMenuActive(Button menuActive) {
        this.menuActive = menuActive;
    }
    
    
    
}

