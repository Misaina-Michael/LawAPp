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
public class IntervenantFonctionLibelle extends BaseModele {
    
    private Integer idFonction;
    private String nom;
    private String prenom;
    private String libelle;
    
    public IntervenantFonctionLibelle() {
    }

    public IntervenantFonctionLibelle(Integer idFonction, String nom, String prenom, String libelle) {
        this.idFonction = idFonction;
        this.nom = nom;
        this.prenom = prenom;
        this.libelle = libelle;
    }

   
    
    public void setIdFonction(Integer idFonction) {
        this.idFonction = idFonction;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Integer getIdFonction() {
        return idFonction;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getLibelle() {
        return libelle;
    }

    
  
    

   
    
    
}
