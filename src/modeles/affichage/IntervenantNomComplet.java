/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeles.affichage;

/**
 *
 * @author RABENANTOANDRO
 */
public class IntervenantNomComplet {
    private Integer idFonction;
    private String nom ;
    private String prenom;
    private String nomComplet;
    private String libelle;

    public IntervenantNomComplet(Integer idFonction, String nom, String prenom, String nomComplet, String libelle) {
        this.idFonction = idFonction;
        this.nom = nom;
        this.prenom = prenom;
        this.nomComplet = nomComplet;
        this.libelle = libelle;
    }

    public IntervenantNomComplet() {
    }
    
    public Integer getIdFonction() {
        return idFonction;
    }

    public void setIdFonction(Integer idFonction) {
        this.idFonction = idFonction;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNomComplet() {
        return nomComplet;
    }

    public void setNomComplet(String nomComplet) {
        this.nomComplet = nomComplet;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
    
}
