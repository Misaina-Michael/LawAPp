/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statiques;

import java.util.List;
import modeles.parametres.DefaultDir;
import modeles.parametres.Fonction;
import modeles.parametres.Juridiction;
import modeles.parametres.ModeFacturation;
import modeles.parametres.Nature;
import modeles.parametres.Pays;
import modeles.parametres.Titre;
import modeles.parametres.TypeFacture;
import modeles.parametres.TypeProcedure;
import modeles.parametres.TypeTarifEvt;
import modeles.parametres.TypeUser;

/**
 *
 * @author Misaina
 */
public class ObjetStatique {
    private static List<Titre> titres;
   
    private static List<Pays> pays;
    private static List<Nature> natures;
    private static List<Fonction> fonctions;
    private static List<TypeUser> typeUsers;
    private static List<TypeTarifEvt> typeTarifEvt;
    private static List<Juridiction> juridictions;
    private static List<TypeFacture> typeFacture;
    private static List<ModeFacturation> modeFacturation;
    private static List<TypeProcedure> typeProcedure;
    private static List<DefaultDir> defaultDir;
    
    private static String[] periodicite;
    private static String[] langue;
    private static String[] transport;
    private static String[] uniteRappel;
    private static String[] droitUtilisateur;

    public static String[] getDroitUtilisateur() {
        return droitUtilisateur;
    }

    public static void setDroitUtilisateur(String[] droitUtilisateur) {
        ObjetStatique.droitUtilisateur = droitUtilisateur;
    }

   
    
    public static List<Titre> getTitres() {
        
        return titres;
    }

    public static void setTitres(List<Titre> titres) {
        ObjetStatique.titres = titres;
    }

    public static List<Pays> getPays() {
        return pays;
    }

    public static void setPays(List<Pays> pays) {
        ObjetStatique.pays = pays;
    }

    public static List<TypeFacture> getTypeFacture() {
        return typeFacture;
    }

    public static void setTypeFacture(List<TypeFacture> typeFacture) {
        ObjetStatique.typeFacture = typeFacture;
    }

    public static List<ModeFacturation> getModeFacturation() {
        return modeFacturation;
    }

    public static void setModeFacturation(List<ModeFacturation> modeFacturation) {
        ObjetStatique.modeFacturation = modeFacturation;
    }

    public static String[] getPeriodicite() {
        return periodicite;
    }

    public static void setPeriodicite(String[] periodicite) {
        ObjetStatique.periodicite = periodicite;
    }

    public static String[] getLangue() {
        return langue;
    }

    public static void setLangue(String[] langue) {
        ObjetStatique.langue = langue;
    }

    public static List<Nature> getNatures() {
        return natures;
    }

    public static void setNatures(List<Nature> natures) {
        ObjetStatique.natures = natures;
    }

    public static List<Juridiction> getJuridictions() {
        return juridictions;
    }

    public static void setJuridictions(List<Juridiction> juridictions) {
        ObjetStatique.juridictions = juridictions;
    }

    public static List<Fonction> getFonctions() {
        return fonctions;
    }

    public static void setFonctions(List<Fonction> fonctions) {
        ObjetStatique.fonctions = fonctions;
    }

    public static List<TypeUser> getTypeUsers() {
        return typeUsers;
    }

    public static void setTypeUsers(List<TypeUser> typeUsers) {
        ObjetStatique.typeUsers = typeUsers;
    }

    public static List<TypeTarifEvt> getTypeTarifEvt() {
        return typeTarifEvt;
    }

    public static void setTypeTarifEvt(List<TypeTarifEvt> typeTarifEvt) {
        ObjetStatique.typeTarifEvt = typeTarifEvt;
    }

    public static List<TypeProcedure> getTypeProcedure() {
        return typeProcedure;
    }

    public static void setTypeProcedure(List<TypeProcedure> typeProcedure) {
        ObjetStatique.typeProcedure = typeProcedure;
    }

    public static String[] getTransport() {
        return transport;
    }

    public static void setTransport(String[] transport) {
        ObjetStatique.transport = transport;
    }

    public static String[] getUniteRappel() {
        return uniteRappel;
    }

    public static void setUniteRappel(String[] uniteRappel) {
        ObjetStatique.uniteRappel = uniteRappel;
    }

    public static List<DefaultDir> getDefaultDir() {
        return defaultDir;
    }

    public static void setDefaultDir(List<DefaultDir> defaultDir) {
        ObjetStatique.defaultDir = defaultDir;
    }
}
