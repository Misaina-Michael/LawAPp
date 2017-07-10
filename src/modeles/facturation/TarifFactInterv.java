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
public class TarifFactInterv extends BaseModele{
    private Integer idIntervenant;
    private Integer idFacture;
    private Float totalHt;

    public Integer getIdIntervenant() {
        return idIntervenant;
    }

    public void setIdIntervenant(Integer idIntervenant) {
        this.idIntervenant = idIntervenant;
    }

    public Integer getIdFacture() {
        return idFacture;
    }

    public void setIdFacture(Integer idFacture) {
        this.idFacture = idFacture;
    }

    public Float getTotalHt() {
        return totalHt;
    }

    public void setTotalHt(Float totalHt) {
        this.totalHt = totalHt;
    }
    
}
