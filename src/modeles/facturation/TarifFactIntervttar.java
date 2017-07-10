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
public class TarifFactIntervttar extends BaseModele{
    private Integer idTarifFactInterv;
    private Integer idTypeTarif;
    private Float mt;

    public Integer getIdTarifFactInterv() {
        return idTarifFactInterv;
    }

    public void setIdTarifFactInterv(Integer idTarifFactInterv) {
        this.idTarifFactInterv = idTarifFactInterv;
    }

    public Integer getIdTypeTarif() {
        return idTypeTarif;
    }

    public void setIdTypeTarif(Integer idTypeTarif) {
        this.idTypeTarif = idTypeTarif;
    }

    public Float getMt() {
        return mt;
    }

    public void setMt(Float mt) {
        this.mt = mt;
    }
    
}
