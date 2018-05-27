package dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import entity.HibernateUtil;
import entity.Vistoria;

public class VistoriaDao {

public void salvarVistoria (Vistoria vis) {
		
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		s.save(vis);
		s.getTransaction().commit();
		s.close();
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Vistoria> listarVistoria (String strPesquisa) {
		
		List<Vistoria> list = new ArrayList<Vistoria>();
		
		Session s = HibernateUtil.getSessionFactory().openSession();
		
		s.beginTransaction();
		
		Criteria crit = s.createCriteria(Vistoria.class);
		crit.add(Restrictions.like("visIdentificacao", '%' + strPesquisa + '%'));
		list = crit.list();
		
		s.getTransaction().commit();
		s.close();
		return list;
	}
	
	public void removerVistoria(Integer id) {
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		Vistoria vis = (Vistoria) s.load(Vistoria.class, id);
		s.delete(vis);
		s.getTransaction().commit();
		s.close();
	}

	public void editarVistoria(Vistoria vis) {
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		s.update(vis);
		s.getTransaction().commit();
		s.close();
	}
	
	public void mergeVistoria(Vistoria vis) {
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		s.merge(vis);
		s.getTransaction().commit();
		s.close();
	}
	
	public void persistirVistoria(Vistoria vis) {
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		s.persist(vis);
		s.getTransaction().commit();
		s.close();
	}
	
	
}
