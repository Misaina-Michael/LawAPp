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
public class ConstanteDirectory {
   public static String defaultDirectoryServer="\\\\SERVEUR-PC\\law-app-repertories\\docs\\dossiers\\";
    //public static String defaultDirectoryServer="D:\\docs\\dossiers\\";
    //public static String defaultDirectoryServer="D:\\Misaina\\work\\smrhr\\lawapp\\docs\\dossiers\\";
//    private static String defaultDirectoryServer="\\\\RABENANTOANDRO\\docs\\dossiers\\";
//    private static String defaultDirectoryServer="\\\\USER-PC\\logicieldata\\docs\\dossiers\\";

    public static String getDefaultDirectoryServer() {
        return defaultDirectoryServer;
    }

    public static void setDefaultDirectoryServer(String defaultDirectoryServer) {
        ConstanteDirectory.defaultDirectoryServer = defaultDirectoryServer;
    }
    
}
