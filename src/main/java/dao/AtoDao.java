package dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import entity.Ato;
import entity.HibernateUtil;


public class AtoDao {
	
public void salvarAto (Ato ato) {
		
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		s.save(ato);
		s.getTransaction().commit();
		s.close();
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Ato> listAto(String strPesquisaAto) {
		
		List<Ato> list = new ArrayList<Ato>();
		
		Session s = HibernateUtil.getSessionFactory().openSession();
		
		s.beginTransaction();
		
		Criteria crit = s.createCriteria(Ato.class);
		crit.add(Restrictions.like("atoSEI", '%' + strPesquisaAto + '%'));
		list = crit.list();
			
		s.getTransaction().commit();
		s.close();
		return list;
	}
	
	public void removerAto(Integer id) {
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		Ato i = (Ato) s.load(Ato.class, id);
		s.delete(i);
		s.getTransaction().commit();
		s.close();
	}

	public void editarAto(Ato ato) {
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		s.update(ato);
		s.getTransaction().commit();
		s.close();
	}
	
	public void mergeAto(Ato ato) {
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		s.merge(ato);
		s.getTransaction().commit();
		s.close();
	}
	
	public void persistAto(Ato ato) {
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		s.persist(ato);
		s.getTransaction().commit();
		s.close();
	}
	
}
