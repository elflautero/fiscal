package dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import entity.Demanda;
import entity.HibernateUtil;
import entity.Subterranea;

public class SubterraneaDao {
	
public void salvaSubterranea (Subterranea subterranea) {
		
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		s.save(subterranea);
		s.getTransaction().commit();
		s.close();
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Subterranea> listSubterranea(int i) {
		
		List<Subterranea> list = new ArrayList<Subterranea>();
		
		Session s = HibernateUtil.getSessionFactory().openSession();
		
		s.beginTransaction();
		
		Criteria crit = s.createCriteria(Demanda.class);
		crit.add(Restrictions.like("sub_Interferencia_Codigo", i));
		list = crit.list();
		
		s.getTransaction().commit();
		s.close();
		return list;
	}
	
	public void removeSubterranea(Integer id) {
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		Subterranea sub = (Subterranea) s.load(Subterranea.class, id);
		s.delete(sub);
		s.getTransaction().commit();
		s.close();
	}

	public void editarSubterranea(Subterranea subterranea) {
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		s.update(subterranea);
		s.getTransaction().commit();
		s.close();
	}
	
	public void mergeSubterranea(Subterranea subterranea) {
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		s.merge(subterranea);
		s.getTransaction().commit();
		s.close();
	}
	
	public void persistSubterranea(Subterranea subterranea) {
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		s.persist(subterranea);
		s.getTransaction().commit();
		s.close();
	}
	
}
