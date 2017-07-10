/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dao.HibernateDao;
import java.util.List;
import modeles.BaseModele;
import org.hibernate.Session;
import utilitaire.HibernateUtil;

/**
 *
 * @author Misaina
 */
public class BaseService {
    private HibernateDao dao;
    public BaseService() {
        dao=new HibernateDao();
    }

   
    public HibernateDao getDao() {
        return this.dao;
    }

    public void setDao(HibernateDao dao) {
        this.dao = dao;
    }

    public void save(BaseModele b) throws Exception {
//        HibernateUtil hibernateUtil=null;
//        try{
//            hibernateUtil=new HibernateUtil();
//            HibernateDao hd=new HibernateDao();
//            hd.setSessionFact(hibernateUtil.buildSessionFactory());
//            this.setDao(hd);
            getDao().save(b);
//        }
//        catch(Exception ex)
//        {
//            throw ex;
//        }
//        finally{
//            if(this.getDao().getSessionFact()!=null) this.getDao().getSessionFact().close();
//        }
        
    }
    public void save(BaseModele b, Session sess) throws Exception {
//        HibernateUtil hibernateUtil=null;
//        try{
//            hibernateUtil=new HibernateUtil();
//            HibernateDao hd=new HibernateDao();
////            hd.setSessionFact(hibernateUtil.buildSessionFactory());
//            this.setDao(hd);
            getDao().save(b,sess);
//        }
//        catch(Exception ex)
//        {
//            throw ex;
//        }
//        finally{
//            if(this.getDao().getSessionFact()!=null) this.getDao().getSessionFact().close();
//        }
    }
    public void update(BaseModele b) throws Exception {
            getDao().update(b);
    }
    public void update(BaseModele b, Session sess) throws Exception {
            getDao().update(b, sess);
    }
    public void delete(BaseModele b) throws Exception {
            getDao().remove(b);
    }
    public List<BaseModele> find(BaseModele b) throws Exception {
        return getDao().find(b);
    }
    public List<BaseModele> find(BaseModele b, Session sess) throws Exception {
        return getDao().find(b, sess);
    }
    public BaseModele findById(BaseModele b) throws Exception {
        return getDao().findById(b);
    }
    public BaseModele findById(BaseModele b, Session sess) throws Exception {
        return getDao().findById(b, sess);
    }
    
    
    public Integer getCurrVal(String seq) throws Exception
    {
        return getDao().getCurrVal(seq);
    }
    public Integer getNextVal(String seq) throws Exception
    {
        return getDao().getNextVal(seq);
    }
}
