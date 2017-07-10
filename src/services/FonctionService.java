/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.util.ArrayList;
import java.util.List;
import modeles.BaseModele;
import modeles.intervenants.FonctionTarifEvt;
import modeles.intervenants.FonctionTarifEvtLibelle;
import modeles.intervenants.TarifNormaux;
import modeles.parametres.Fonction;

/**
 *
 * @author RABENANTOANDRO
 */
public class FonctionService extends BaseService{
     public List<Fonction> findFonction(Fonction  f) throws Exception {
        List<Fonction> res = new ArrayList<Fonction>();
        for (BaseModele b : this.getDao().find(f)) {
            res.add((Fonction) b);
        }
        return res;
    }
    public List<FonctionTarifEvtLibelle> findFonctionTarifEvtLibelle(FonctionTarifEvtLibelle  f) throws Exception {
        List<FonctionTarifEvtLibelle> res = new ArrayList<FonctionTarifEvtLibelle>();
        for (BaseModele b : this.getDao().find(f)) {
            res.add((FonctionTarifEvtLibelle) b);
        }
        return res;
    }
}
