package dao;

import java.util.List;

import modeles.BaseModele;
import org.hibernate.Session;

public abstract class BaseDao {
	public abstract void save(BaseModele b) throws Exception;
	public abstract void save(BaseModele b, Session session) throws Exception;
	public abstract void update(BaseModele b) throws Exception;
        public abstract void update(BaseModele b, Session session) throws Exception;
	
	public abstract List<BaseModele> find(BaseModele b) throws Exception;
        public abstract List<BaseModele> find(BaseModele b, Session session) throws Exception;
        
        public abstract List<BaseModele> findWhere(BaseModele b) throws Exception;
        public abstract List<BaseModele> findWhere(BaseModele b, Session session) throws Exception;
        
        public abstract BaseModele findById(BaseModele b) throws Exception;
        public abstract BaseModele findById(BaseModele b, Session session) throws Exception;

	public abstract void remove(BaseModele b) throws Exception;
	public abstract void remove(BaseModele b, Session session) throws Exception;
}
