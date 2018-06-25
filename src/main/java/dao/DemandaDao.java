package dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;

import entity.Demanda;
import entity.HibernateUtil;
import entity.Usuario;


public class DemandaDao {
	
public void salvarDemanda (Demanda demanda) {
		
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		s.save(demanda);
		s.getTransaction().commit();
		s.close();
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Demanda> listarDemandas(String strPesquisa) {
		
		//List<Demanda> list = new ArrayList<Demanda>();
		
		Session s = HibernateUtil.getSessionFactory().openSession();
		
		List<Demanda> list = s.createQuery(
				"SELECT d FROM Demanda AS d LEFT OUTER JOIN FETCH d.demEnderecoFK WHERE (d.demDocumento LIKE '%"+strPesquisa+"%' "
						+ "OR d.demDocumentoSEI LIKE '%"+strPesquisa+"%' OR d.demProcessoSEI LIKE '%"+strPesquisa+"%')"
				).list();
		
		s.beginTransaction();
		
		/*
		Criteria crit = s.createCriteria(Demanda.class);
		
		Criterion demDoc = Restrictions.like("demDocumento", '%' + strPesquisa + '%');
		Criterion demDocSei = Restrictions.like("demDocumentoSEI", '%' + strPesquisa + '%');
		Criterion demProcSei = Restrictions.like("demProcessoSEI", '%' + strPesquisa + '%');
		
		Disjunction orExp = Restrictions.or(demDoc,demProcSei, demDocSei);
		
		crit.add(orExp);
		
		//crit.add(Restrictions.like("demDocumento", '%' + strPesquisa + '%'));
		list = crit.list();4
		*/
		
		// SQL list = s.createSQLQuery("SELECT * FROM Demanda WHERE Documento_Denuncia LIKE '%strPesquisa%'").list();
		//list = s.createQuery("from Demanda d where d.Documento_Denuncia= : strPesquisa").setString("strPesquisa",strPesquisa).list();
		
		s.getTransaction().commit();
		s.close();
		return list;
	}
	
	public void removerDemanda(Integer id) {
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		Demanda c = (Demanda) s.load(Demanda.class, id);
		s.delete(c);
		s.getTransaction().commit();
		s.close();
	}

	public void editarDemanda(Demanda demanda) {
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		s.update(demanda);
		s.getTransaction().commit();
		s.close();
	}
	
	public void mergeDemanda(Demanda demanda) {
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		s.merge(demanda);
		s.getTransaction().commit();
		s.close();
	}
	
	public void persistDemanda(Demanda demanda) {
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		s.persist(demanda);
		s.getTransaction().commit();
		s.close();
	}

}
