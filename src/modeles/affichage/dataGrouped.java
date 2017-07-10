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
public class dataGrouped {
    public Integer dossierId;
    public String dossierName;

    public Integer getDossierId() {
        return dossierId;
    }

    public dataGrouped() {
    }

    public dataGrouped(Integer dossierId, String dossierName) {
        this.dossierId = dossierId;
        this.dossierName = dossierName;
    }

    public void setDossierId(Integer dossierId) {
        this.dossierId = dossierId;
    }

    public String getDossierName() {
        return dossierName;
    }

    public void setDossierName(String dossierName) {
        this.dossierName = dossierName;
    }

   
    
}
