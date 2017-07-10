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
public class ModeFacturation extends BaseModele{
    private String libelle;
    private Float taux;

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Float getTaux() {
        return taux;
    }

    public void setTaux(Float taux) {
        this.taux = taux;
    }
    
    public String toString()
    {
        return getLibelle();
    }
}
