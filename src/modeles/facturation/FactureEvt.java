/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeles.facturation;

import modeles.BaseModele;

/**
 *
 * @author Misaina
 */
public class FactureEvt extends BaseModele{
    private Integer idFacture;
    private Integer idEvtDossier;

    public Integer getIdFacture() {
        return idFacture;
    }

    public void setIdFacture(Integer idFacture) {
        this.idFacture = idFacture;
    }

    public Integer getIdEvtDossier() {
        return idEvtDossier;
    }

    public void setIdEvtDossier(Integer idEvtDossier) {
        this.idEvtDossier = idEvtDossier;
    }
    
    
}
