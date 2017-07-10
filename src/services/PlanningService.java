/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import modeles.BaseModele;
import modeles.planning.Planning;
import modeles.planning.PlanningLibelle;
import modeles.planning.ProcedureLibelle;
import statiques.ObjetStatique;

/**
 *
 * @author Misaina
 */
public class PlanningService extends BaseService{
    public void createPlanning(Planning pl) throws Exception 
    {
        try
        {
            //non planifiée
            if(!pl.getPlanifiee())
            {
                pl.setDatePlanning(new Date());
                pl.setRappel(0);
                pl.setHeureDebut(new Time(0,0,0));
                pl.setHeureFin(new Time(0,0,0));
                pl.setIdEvt(0);
                pl.setIdIntervenant(0);
                pl.setUniteRappel("Minutes");
                pl.setIdGestionnaire(0);
                pl.setIdJur(ObjetStatique.getJuridictions().get(0).getId());
                pl.setLibelle("");
                pl.setTransport("Voiture");
                pl.setVille("");
            }
            save(pl);
        }
        catch(Exception ex)
        {
            throw ex;
        }
    }
            
    public List<Planning> find(Planning intr) throws Exception
    {
        List<Planning> res=null;
        try
        {
            res=new ArrayList<Planning>();
            List<BaseModele> bm=this.getDao().find(intr);
            for(BaseModele b:bm)
            {
                res.add((Planning)b);
            }
            return res;
        }
        catch(Exception ex)
        {
            throw ex;
        }
    }
    public List<ProcedureLibelle> findProcedure(ProcedureLibelle intr) throws Exception
    {
        List<ProcedureLibelle> res=null;
        try
        {
            res=new ArrayList<ProcedureLibelle>();
            List<BaseModele> bm=this.getDao().find(intr);
            
            for(BaseModele b:bm)
            {
                res.add((ProcedureLibelle)b);
            }
            return res;
        }
        catch(Exception ex)
        {
            throw ex;
        }
    }
    public List<PlanningLibelle> findPlanning(PlanningLibelle intr) throws Exception
    {
        List<PlanningLibelle> res=null;
        try
        {
            res=new ArrayList<PlanningLibelle>();
            List<BaseModele> bm=this.getDao().find(intr);
            
            for(BaseModele b:bm)
            {
                res.add((PlanningLibelle)b);
            }
            return res;
        }
        catch(Exception ex)
        {
            throw ex;
        }
    }
}
