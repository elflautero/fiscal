package dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import entity.Denuncia;
import entity.HibernateUtil;


public class DenunciaDao {
	
public void salvaDenuncia (Denuncia denuncia) {
		
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		s.save(denuncia);
		s.getTransaction().commit();
		s.close();
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Denuncia> listDenuncia(String strPesquisa) {
		List<Denuncia> list = new ArrayList<Denuncia>();
		
		Session s = HibernateUtil.getSessionFactory().openSession();
		
		s.beginTransaction();
		
		Criteria crit = s.createCriteria(Denuncia.class);
		crit.add(Restrictions.like("denDocumento", '%' + strPesquisa + '%'));
		list = crit.list();
		// SQL list = s.createSQLQuery("SELECT * FROM Denuncia WHERE Documento_Denuncia LIKE '%strPesquisa%'").list();
		//list = s.createQuery("from Denuncia d where d.Documento_Denuncia= : strPesquisa").setString("strPesquisa",strPesquisa).list();
		
		s.getTransaction().commit();
		s.close();
		return list;
	}
	
	public void removeDenuncia(Integer id) {
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		Denuncia c = (Denuncia) s.load(Denuncia.class, id);
		s.delete(c);
		s.getTransaction().commit();
		s.close();
	}

	public void editarDenuncia(Denuncia denuncia) {
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		s.update(denuncia);
		s.getTransaction().commit();
		s.close();
	}
	
	public void mergeDenuncia(Denuncia denuncia) {
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		s.merge(denuncia);
		s.getTransaction().commit();
		s.close();
	}
	
	public void persistDenuncia(Denuncia denuncia) {
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		s.persist(denuncia);
		s.getTransaction().commit();
		s.close();
	}

}
