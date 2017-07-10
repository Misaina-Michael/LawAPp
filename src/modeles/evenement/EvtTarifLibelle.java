/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeles.evenement;

import java.sql.Time;
import modeles.BaseModele;

/**
 *
 * @author RABENANTOANDRO
 */
public class EvtTarifLibelle extends BaseModele {

    private Integer idTypeTarif;
    private String libelleTypeTarif;
    private String libelle;
    private String code;
    private String numero;
    private Time duree;
    private Float montant;
    private String type;

    public EvtTarifLibelle() {
    }
    
    public EvtTarifLibelle(Integer idTypeTarif, String libelleTypeTarif, String libelle, String code, String numero, Time duree, Float montant) {
        this.idTypeTarif = idTypeTarif;
        this.libelleTypeTarif = libelleTypeTarif;
        this.libelle = libelle;
        this.code = code;
        this.numero = numero;
        this.duree = duree;
        this.montant = montant;
    }

    public Integer getIdTypeTarif() {
        return idTypeTarif;
    }

    public void setIdTypeTarif(Integer idTypeTarif) {
        this.idTypeTarif = idTypeTarif;
    }

    public String getLibelleTypeTarif() {
        return libelleTypeTarif;
    }

    public void setLibelleTypeTarif(String libelleTypeTarif) {
        this.libelleTypeTarif = libelleTypeTarif;
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

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Time getDuree() {
        return duree;
    }

    public void setDuree(Time duree) {
        this.duree = duree;
    }

    public Float getMontant() {
        return montant;
    }

    public void setMontant(Float montant) {
        this.montant = montant;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
}
