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
public class TarifFactIntervttarLibelle extends BaseModele{
    private Integer idTarifFactInterv;
    private Integer idTypeTarif;
    private Float mt;
    private Float totalHt;
    private String typeTarif;
    private String nomInterv;
    private String prenomInterv;
    private Integer idIntervenant;
    private Integer idFacture;

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

    public Float getTotalHt() {
        return totalHt;
    }

    public void setTotalHt(Float totalHt) {
        this.totalHt = totalHt;
    }

    public String getTypeTarif() {
        return typeTarif;
    }

    public void setTypeTarif(String typeTarif) {
        this.typeTarif = typeTarif;
    }

    public String getNomInterv() {
        return nomInterv;
    }

    public void setNomInterv(String nomInterv) {
        this.nomInterv = nomInterv;
    }

    public String getPrenomInterv() {
        return prenomInterv;
    }

    public void setPrenomInterv(String prenomInterv) {
        this.prenomInterv = prenomInterv;
    }

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
    public String toString()
    {
        return nomInterv+" "+prenomInterv;
    }
}
