/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeles.evenement;

import java.util.List;

/**
 *
 * @author Misaina
 */
public class ThreadsChrono {
    private static List<ChronoEvtThread> listeEvt;

    public static List<ChronoEvtThread> getListeEvt() {
        return listeEvt;
    }

    public static void setListeEvt(List<ChronoEvtThread> listeEvt) {
        ThreadsChrono.listeEvt = listeEvt;
    }
    
}
