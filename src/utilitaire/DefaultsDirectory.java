/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitaire;

/**
 *
 * @author Misaina
 */
public enum DefaultsDirectory {
    
    ACTES("Actes"),
    CORRESPONDANCES("Correspondances"), 
    DIRES("Dires"), 
    HONORAIRES("Honoraires"), 
    NOTESDESYNTHESE("Notes de synthese"), 
    PIECES("Pieces"),
    FACTURES("Factures");

    private String name = "";     
    DefaultsDirectory(String name){    
        this.name = name;
    }  
    public String toString(){    
        return name;
    }
    
}
