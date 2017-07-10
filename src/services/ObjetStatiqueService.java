/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.util.ArrayList;
import java.util.List;
import modeles.BaseModele;
import modeles.evenement.ChronoEvtThread;
import modeles.evenement.ThreadsChrono;
import modeles.parametres.DefaultDir;
import modeles.parametres.Fonction;
import modeles.parametres.Juridiction;
import modeles.parametres.ModeFacturation;
import modeles.parametres.Nature;
import modeles.parametres.Titre;
import modeles.parametres.TypeFacture;
import modeles.parametres.TypeProcedure;
import modeles.parametres.TypeTarifEvt;
import modeles.parametres.TypeUser;
import statiques.ObjetStatique;

/**
 *
 * @author Misaina
 */
public class ObjetStatiqueService {

    public void loadAll() throws Exception {
        ThreadsChrono.setListeEvt(new ArrayList<ChronoEvtThread>());
        BaseService bs = new BaseService();
        //titres
        List<BaseModele> l = bs.find(new Titre());
        List<Titre> titres = new ArrayList<Titre>();
        for (BaseModele b : l) {
            titres.add((Titre) b);
        }
        ObjetStatique.setTitres(titres);
        //typeFacture
        List<BaseModele> t = bs.find(new TypeFacture());
        List<TypeFacture> tf = new ArrayList<TypeFacture>();
        for (BaseModele b : t) {
            tf.add((TypeFacture) b);
        }
        ObjetStatique.setTypeFacture(tf);
        //modeFacturation
        List<BaseModele> m = bs.find(new ModeFacturation());
        List<ModeFacturation> mf = new ArrayList<ModeFacturation>();
        for (BaseModele b : m) {
            mf.add((ModeFacturation) b);
        }
        ObjetStatique.setModeFacturation(mf);
        //periodicite
        String[] p = new String[3];
        p[0] = "Annuelle";
        p[1] = "Mensuelle";
        p[2] = "Trimestrielle";
        ObjetStatique.setPeriodicite(p);
        //langue
        String[] lg = new String[2];
        lg[0] = "Malgache";
        lg[1] = "Francais";
        ObjetStatique.setLangue(lg);

        //juridiction
        List<BaseModele> jj = bs.find(new Juridiction());
        List<Juridiction> jur = new ArrayList<Juridiction>();
        for (BaseModele q : jj) {
            jur.add((Juridiction) q);
        }
        ObjetStatique.setJuridictions(jur);

        //nature
        List<BaseModele> na = bs.find(new Nature());
        List<Nature> naa = new ArrayList<Nature>();
        for (BaseModele q : na) {
            naa.add((Nature) q);
        }
        ObjetStatique.setNatures(naa);

        //fonction
        List<BaseModele> fo = bs.find(new Fonction());
        List<Fonction> fsp = new ArrayList<Fonction>();
        for (BaseModele q : fo) {
            fsp.add((Fonction) q);
        }
        ObjetStatique.setFonctions(fsp);

        //typeuser
        List<BaseModele> tu = bs.find(new TypeUser());
        List<TypeUser> tus = new ArrayList<TypeUser>();
        for (BaseModele q : tu) {
            tus.add((TypeUser) q);
        }
        ObjetStatique.setTypeUsers(tus);

        //typetarifevt
        List<BaseModele> te = bs.find(new TypeTarifEvt());
        List<TypeTarifEvt> tev = new ArrayList<TypeTarifEvt>();
        for (BaseModele q : te) {
            tev.add((TypeTarifEvt) q);
        }
        ObjetStatique.setTypeTarifEvt(tev);
        
        //defaultdir
        List<BaseModele> ddir = bs.find(new DefaultDir());
        List<DefaultDir> listDefDir = new ArrayList<DefaultDir>();
        for (BaseModele q : ddir) {
            listDefDir.add((DefaultDir) q);
        }
        ObjetStatique.setDefaultDir(listDefDir);

        //typeprocedure
        List<BaseModele> tp = bs.find(new TypeProcedure());
        List<TypeProcedure> tpp = new ArrayList<TypeProcedure>();
        for (BaseModele q : tp) {
            tpp.add((TypeProcedure) q);
        }
        ObjetStatique.setTypeProcedure(tpp);
        //droit utilisateur
        String[] du = new String[2];
        du[0] = "lecture/ecriture";
        du[1] = "aucun";
        ObjetStatique.setDroitUtilisateur(du);
        //transport
        String[] tr = new String[3];
        tr[0] = "Aerien";
        tr[1] = "Voiture";
        tr[2] = "Train";
        ObjetStatique.setTransport(tr);

        //Unite rappel
        String[] rp = new String[4];
        rp[0] = "Minutes";
        rp[1] = "Heures";
        rp[2] = "Jours";
        rp[3] = "Semaines";
        ObjetStatique.setUniteRappel(rp);
    }

}
