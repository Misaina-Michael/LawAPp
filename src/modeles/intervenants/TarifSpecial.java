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
public class TarifSpecial extends BaseModele{
    private Integer idIntervenant;
    private String libelle;
    private Float montant;
    private java.sql.Time duree;

    public TarifSpecial() {
    }

    public Integer getIdIntervenant() {
        return idIntervenant;
    }

    public void setIdIntervenant(Integer idIntervenant) {
        this.idIntervenant = idIntervenant;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Float getMontant() {
        return montant;
    }

    public void setMontant(Float montant) {
        this.montant = montant;
    }

    public Time getDuree() {
        return duree;
    }

    public void setDuree(Time duree) {
        this.duree = duree;
    }
    
    
    
}
