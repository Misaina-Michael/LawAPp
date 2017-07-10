/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeles.parametres;

import modeles.BaseModele;
import modeles.intervenants.Intervenant;

/**
 *
 * @author RABENANTOANDRO
 */
public class UserSession extends BaseModele {
    
    private static Intervenant intervenantUserSession = null;

    public static Intervenant getIntervenantUserSession() {
        return intervenantUserSession;
    }

    public static void setIntervenantUserSession(Intervenant intervenantUserSession) {
        UserSession.intervenantUserSession = intervenantUserSession;
    }
        
}
