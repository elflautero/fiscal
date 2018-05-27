package dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import entity.HibernateUtil;
import entity.Subterranea;
import entity.Superficial;

public class SuperficialDao {
	
public void salvaSuperficial (Superficial superficial) {
		
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		s.save(superficial);
		s.getTransaction().commit();
		s.close();
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Superficial> listSuperficial(String strSupPesquisa) {
		List<Superficial> list = new ArrayList<Superficial>();
		
		Session s = HibernateUtil.getSessionFactory().openSession();
		
		s.beginTransaction();
		
		Criteria crit = s.createCriteria(Superficial.class);
		crit.add(Restrictions.like("sup_Codigo", '%' + strSupPesquisa + '%'));
		list = crit.list();
		
		s.getTransaction().commit();
		s.close();
		return list;
	}
	
	public void removeSuperficial(Integer id) {
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		Subterranea sup = (Subterranea) s.load(Subterranea.class, id);
		s.delete(sup);
		s.getTransaction().commit();
		s.close();
	}

	public void editarSuperficial(Superficial superficial) {
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		s.update(superficial);
		s.getTransaction().commit();
		s.close();
	}
	
	public void mergeSuperficial(Superficial superficial) {
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		s.merge(superficial);
		s.getTransaction().commit();
		s.close();
	}
	
	public void persistSuperficial(Superficial superficial) {
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		s.persist(superficial);
		s.getTransaction().commit();
		s.close();
	}

}
