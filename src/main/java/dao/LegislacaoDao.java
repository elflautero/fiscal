package dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import entity.HibernateUtil;
import entity.Legislacao;

public class LegislacaoDao {
	
		
	public void salvarLegislacao (Legislacao leg) {
			
			Session s = HibernateUtil.getSessionFactory().openSession();
			s.beginTransaction();
			s.save(leg);
			s.getTransaction().commit();
			s.close();
			
		}
		
		@SuppressWarnings("unchecked")
		public List<Legislacao> listLegislacao(String strPesquisa) {
			
			List<Legislacao> list = new ArrayList<Legislacao>();
			
			Session s = HibernateUtil.getSessionFactory().openSession();
			
			s.beginTransaction();
			
			Criteria crit = s.createCriteria(Legislacao.class);
			crit.add(Restrictions.like("legisArtigo", '%' + strPesquisa + '%'));
			list = crit.list();
				
			s.getTransaction().commit();
			s.close();
			return list;
		}
		
		public void removerLegislacao(Integer id) {
			Session s = HibernateUtil.getSessionFactory().openSession();
			s.beginTransaction();
			Legislacao i = (Legislacao) s.load(Legislacao.class, id);
			s.delete(i);
			s.getTransaction().commit();
			s.close();
		}

		public void editarLegislacao(Legislacao leg) {
			Session s = HibernateUtil.getSessionFactory().openSession();
			s.beginTransaction();
			s.update(leg);
			s.getTransaction().commit();
			s.close();
		}
		
		public void mergeLegislacao(Legislacao leg) {
			Session s = HibernateUtil.getSessionFactory().openSession();
			s.beginTransaction();
			s.merge(leg);
			s.getTransaction().commit();
			s.close();
		}
		
		public void persistirLegislacao(Legislacao leg) {
			Session s = HibernateUtil.getSessionFactory().openSession();
			s.beginTransaction();
			s.persist(leg);
			s.getTransaction().commit();
			s.close();
		}
	

}
