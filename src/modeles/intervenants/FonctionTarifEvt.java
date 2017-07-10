/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeles.intervenants;

import modeles.BaseModele;

/**
 *
 * @author RABENANTOANDRO
 */
public class FonctionTarifEvt extends BaseModele {
    private Integer idFonction;
    private Integer idEvtTarif;

    public FonctionTarifEvt(Integer idFonction, Integer idEvtTarif) {
        this.idFonction = idFonction;
        this.idEvtTarif = idEvtTarif;
    }

    public FonctionTarifEvt() {
    }

    public Integer getIdFonction() {
        return idFonction;
    }

    public void setIdFonction(Integer idFonction) {
        this.idFonction = idFonction;
    }

    public Integer getIdEvtTarif() {
        return idEvtTarif;
    }

    public void setIdEvtTarif(Integer idEvtTarif) {
        this.idEvtTarif = idEvtTarif;
    }
    
}
