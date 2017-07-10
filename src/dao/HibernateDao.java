package dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import modeles.BaseModele;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import utilitaire.HibernateUtil;

public class HibernateDao extends BaseDao {

    private SessionFactory sessionFact;

    public HibernateDao() throws HibernateException {
        sessionFact = HibernateUtil.getSessionFactory();
    }
//    public HibernateDao(){
//        
//    }

    public SessionFactory getSessionFact() {
        return sessionFact;
    }

    public void setSessionFact(SessionFactory sf) {
        this.sessionFact = sf;
    }

    public void save(BaseModele b) throws Exception {
        Session sess = null;
        Transaction tr = null;
        try {
            sess = sessionFact.openSession();
            tr = sess.beginTransaction();
            this.save(b, sess);
            tr.commit();
        } catch (Exception e) {
            if (tr != null) {
                tr.rollback();
            }
            throw e;
        } finally {
            if (sess != null) {
                sess.close();
            }
        }

    }

    public void remove(BaseModele b) throws Exception {
        Transaction tr = null;
        Session sess = null;

        try {
            sess = sessionFact.openSession();
            tr = sess.beginTransaction();
            this.remove(b, sess);
            tr.commit();
        } catch (Exception ex) {
            if (tr != null) {
                tr.rollback();
            }
            throw ex;
        } finally {
            if (sess != null) {
                sess.close();
            }
        }
    }

    @Override
    public void remove(BaseModele b, Session sess) throws Exception {
        try {
            sess.delete(b);
        } catch (Exception ex) {

            throw ex;
        }
    }

    @Override
    public List<BaseModele> find(BaseModele b) throws Exception {
        Session sess = null;
        List<BaseModele> res = null;
        try {
            res = new ArrayList<BaseModele>();
            sess = getSessionFact().openSession();
            res = find(b, sess);
            return res;
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (sess != null) {
                sess.close();
            }
        }
    }

    @Override
    public List<BaseModele> find(BaseModele b, Session sess) throws Exception {
        List<BaseModele> res = null;
        try {
            res = new ArrayList<BaseModele>();
            res = sess.createQuery("FROM " + b.buildQueryFind()).list();
            return res;
        } catch (Exception ex) {
            throw ex;
        }
    }
    public List<BaseModele> findWhere(BaseModele b) throws Exception {
        Session sess = null;
        List<BaseModele> res = null;
        try {
            res = new ArrayList<BaseModele>();
            sess = getSessionFact().openSession();
            res = findWhere(b, sess);
            return res;
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (sess != null) {
                sess.close();
            }
        }
    }
    public List<BaseModele> findWhere(BaseModele b, Session sess) throws Exception {
        List<BaseModele> res = null;
        try {
            res = new ArrayList<BaseModele>();
            res = sess.createQuery("FROM " + b.buildQueryFindWhere()).list();
            return res;
        } catch (Exception ex) {
            throw ex;
        }
    }
    public List<BaseModele> findQuery(BaseModele b, Session sess, String q) throws Exception {
        List<BaseModele> res = null;
        try {
            res = new ArrayList<BaseModele>();
            res = sess.createQuery("FROM " + q).list();
            return res;
        } catch (Exception ex) {
            throw ex;
        }
    }
    @Override
    public void save(BaseModele b, Session session) throws Exception {
        try {
            session.save(b);
        } catch (Exception e) {
            throw e;
        }
    }

    public void update(BaseModele b) throws Exception {
        Session sess = null;
        Transaction tr = null;
        try {
            sess = sessionFact.openSession();
            sess.beginTransaction();

            this.update(b, sess);
            sess.getTransaction().commit();
        } catch (Exception e) {
            if (tr != null) {
                tr.rollback();
            }
            throw e;
        } finally {
            if (sess != null) {
                sess.close();
            }
        }

    }

    @Override
    public void update(BaseModele b, Session session) throws Exception {
        try {
            session.update(b);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public BaseModele findById(BaseModele b) throws Exception {
        // TODO Auto-generated method stub
        Session sess = null;
        try {
            sess = sessionFact.openSession();
            BaseModele modele = findById(b, sess);
            return modele;
        } catch (Exception e) {
            throw e;
        } finally {
            if (sess != null) {
                sess.close();
            }
        }
    }

    @Override
    public BaseModele findById(BaseModele b, Session session) throws Exception {
        return (BaseModele) session.load(b.getClass(), b.getId());
    }

    public Integer getNextVal(String sequence) throws Exception {
        Session sess = null;
        try {
            sess = sessionFact.openSession();
            return this.getNextVal(sequence, sess);
        } catch (Exception e) {
            throw e;
        } finally {
            if (sess != null) {
                sess.close();
            }
        }
    }

    public Integer getNextVal(String sequence, Session sess) {
        Query query
                = sess.createSQLQuery("select nextval('" + sequence + "') as num")
                .addScalar("num", Hibernate.INTEGER);

        return ((Integer) query.uniqueResult()).intValue();
    }

    public Integer getCurrVal(String sequence) throws Exception {
        Session sess = null;
        try {
            sess = sessionFact.openSession();
            return this.getCurrVal(sequence, sess);
        } catch (Exception e) {
            throw e;
        } finally {
            if (sess != null) {
                sess.close();
            }
        }
    }

    public Integer getCurrVal(String sequence, Session sess) {
        Query query
                = sess.createSQLQuery("select currval('" + sequence + "') as num")
                .addScalar("num", Hibernate.INTEGER);

        return ((Integer) query.uniqueResult()).intValue();
    }
}
