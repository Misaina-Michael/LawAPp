/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeles.affichage;

/**
 *
 * @author RABENANTOANDRO
 */
public class DossierEventAffichage {
    public Integer iddoss;
    public String nomDoss;
    public String DateDossier;

    public DossierEventAffichage() {
    }

    public DossierEventAffichage(Integer iddoss, String nomDoss, String DateDossier) {
        this.iddoss = iddoss;
        this.nomDoss = nomDoss;
        this.DateDossier = DateDossier;
    }

    public Integer getIddoss() {
        return iddoss;
    }

    public void setIddoss(Integer iddoss) {
        this.iddoss = iddoss;
    }

    public String getNomDoss() {
        return nomDoss;
    }

    public void setNomDoss(String nomDoss) {
        this.nomDoss = nomDoss;
    }

    public String getDateDossier() {
        return DateDossier;
    }

    public void setDateDossier(String DateDossier) {
        this.DateDossier = DateDossier;
    }
    
    
}
