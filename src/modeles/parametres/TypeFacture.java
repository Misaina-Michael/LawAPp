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
public class TypeFacture extends BaseModele{
    private String libelle;
    private String modeleWord;
    
    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getModeleWord() {
        return modeleWord;
    }

    public void setModeleWord(String modeleWord) {
        this.modeleWord = modeleWord;
    }
    public String toString(){
        return getLibelle();
    }
}
