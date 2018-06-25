package dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;

import entity.Ato;
import entity.HibernateUtil;
import entity.Vistoria;


public class AtoDao {
	
public void salvarAto (Ato ato) {
		
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		s.save(ato);
		s.getTransaction().commit();
		s.close();
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Ato> listAto(String strPesquisa) {
		
		/*
		List<Ato> list = new ArrayList<Ato>();
		
		Session s = HibernateUtil.getSessionFactory().openSession();
		
		s.beginTransaction();
		
		Criteria crit = s.createCriteria(Ato.class);
		
		Criterion atoIden = Restrictions.like("atoIdentificacao", '%' + strPesquisa + '%');
		Criterion atoSei = Restrictions.like("atoSEI", '%' + strPesquisa + '%');
		
		LogicalExpression orExp = Restrictions.or(atoIden,atoSei);
		
		crit.add(orExp);
		
		list = crit.list();
		*/
		
		Session s = HibernateUtil.getSessionFactory().openSession();
		
		List<Ato> list = s.createQuery(
				"SELECT a FROM Ato AS a JOIN FETCH a.atoVisCodigoFK WHERE ( a.atoIdentificacao LIKE '%"+strPesquisa+"%' "
						+ "OR a.atoSEI LIKE '%"+strPesquisa+"%')"
				).list();
		
		s.beginTransaction();
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
