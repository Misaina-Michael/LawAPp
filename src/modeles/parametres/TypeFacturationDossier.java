/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeles.parametres;

import modeles.BaseModele;

/**
 *
 * @author Misaina
 */
public class TypeFacturationDossier extends BaseModele{
    
   public Float tauxTva;   
   public Float mtForfait;   
   public Float tauxMode;   
   public Integer echeance;
   public Integer idTypeFacture;
   public Integer idDossier;
   public Integer idMode;
   
   public java.lang.String compteComptable;
   public java.lang.String compteTiers;
   public java.lang.String categorieComptable;
   public java.lang.String langue;
   public java.lang.String typeFacture;
   public java.lang.String periodicite;

    public Float getTauxTva() {
        return tauxTva;
    }

    public void setTauxTva(Float tauxTva) {
        this.tauxTva = tauxTva;
    }

    public Integer getEcheance() {
        return echeance;
    }

    public void setEcheance(Integer echeance) {
        this.echeance = echeance;
    }

    public Integer getIdTypeFacture() {
        return idTypeFacture;
    }

    public void setIdTypeFacture(Integer idTypeFacture) {
        this.idTypeFacture = idTypeFacture;
    }

    public Integer getIdDossier() {
        return idDossier;
    }

    public void setIdDossier(Integer idDossier) {
        this.idDossier = idDossier;
    }

   
    public String getCompteComptable() {
        return compteComptable;
    }

    public void setCompteComptable(String compteComptable) {
        this.compteComptable = compteComptable;
    }

    public String getCompteTiers() {
        return compteTiers;
    }

    public void setCompteTiers(String compteTiers) {
        this.compteTiers = compteTiers;
    }

    public String getCategorieComptable() {
        return categorieComptable;
    }

    public void setCategorieComptable(String categorieComptable) {
        this.categorieComptable = categorieComptable;
    }

    public String getLangue() {
        return langue;
    }

    public void setLangue(String langue) {
        this.langue = langue;
    }

    public String getTypeFacture() {
        return typeFacture;
    }

    public void setTypeFacture(String typeFacture) {
        this.typeFacture = typeFacture;
    }

    public String getPeriodicite() {
        return periodicite;
    }

    public void setPeriodicite(String periodicite) {
        this.periodicite = periodicite;
    }

    public Float getMtForfait() {
        return mtForfait;
    }

    public void setMtForfait(Float mtForfait) {
        this.mtForfait = mtForfait;
    }

    public Float getTauxMode() {
        return tauxMode;
    }

    public void setTauxMode(Float tauxMode) {
        this.tauxMode = tauxMode;
    }

    public Integer getIdMode() {
        return idMode;
    }

    public void setIdMode(Integer idMode) {
        this.idMode = idMode;
    }

   
    
}
