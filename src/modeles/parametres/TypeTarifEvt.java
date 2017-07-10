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
public class TypeTarifEvt extends BaseModele{
    private String libelle;

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
    public String toString()
    {
        return libelle;
    }
}
