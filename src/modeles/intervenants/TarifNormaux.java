/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeles.intervenants;

import java.sql.Time;
import modeles.BaseModele;

/**
 *
 * @author RABENANTOANDRO
 */
public class TarifNormaux extends BaseModele {

    private Integer idintervenant;
    private Integer idtarifinterv;
    private Integer idevttarif;
    private Float taux;
    private Float mt;
    private String libelle;
    private String code;
    private java.sql.Time duree;
    private Float mtevt;
    private Integer idfonction;
    private String libelleTypeTarif;
    private String type;

    public TarifNormaux() {
    }

    public TarifNormaux(Integer idintervenant, Integer idtarifinterv, Integer idevttarif, Float taux, Float mt, String libelle, String code, Time duree, Float mtevt, Integer idfonction, String libelleTypeTarif, String type) {
        this.idintervenant = idintervenant;
        this.idtarifinterv = idtarifinterv;
        this.idevttarif = idevttarif;
        this.taux = taux;
        this.mt = mt;
        this.libelle = libelle;
        this.code = code;
        this.duree = duree;
        this.mtevt = mtevt;
        this.idfonction = idfonction;
        this.libelleTypeTarif = libelleTypeTarif;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getIdintervenant() {
        return idintervenant;
    }

    public void setIdintervenant(Integer idintervenant) {
        this.idintervenant = idintervenant;
    }

    public Integer getIdtarifinterv() {
        return idtarifinterv;
    }

    public void setIdtarifinterv(Integer idtarifinterv) {
        this.idtarifinterv = idtarifinterv;
    }

    public Integer getIdevttarif() {
        return idevttarif;
    }

    public void setIdevttarif(Integer idevttarif) {
        this.idevttarif = idevttarif;
    }

    public Float getTaux() {
        return taux;
    }

    public void setTaux(Float taux) {
        this.taux = taux;
    }

    public Float getMt() {
        return mt;
    }

    public void setMt(Float mt) {
        this.mt = mt;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Time getDuree() {
        return duree;
    }

    public void setDuree(Time duree) {
        this.duree = duree;
    }

    public Float getMtevt() {
        return mtevt;
    }

    public void setMtevt(Float mtevt) {
        this.mtevt = mtevt;
    }

    public Integer getIdfonction() {
        return idfonction;
    }

    public void setIdfonction(Integer idfonction) {
        this.idfonction = idfonction;
    }

    public String getLibelleTypeTarif() {
        return libelleTypeTarif;
    }

    public void setLibelleTypeTarif(String libelleTypeTarif) {
        this.libelleTypeTarif = libelleTypeTarif;
    }

}
