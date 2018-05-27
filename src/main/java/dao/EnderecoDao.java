package dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import entity.Endereco;
import entity.HibernateUtil;


public class EnderecoDao {

	
public void salvaEndereco (Endereco endereco) {
		
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		s.save(endereco);
		s.getTransaction().commit();
		s.close();
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Endereco> listEndereco(String strPesquisaEnd) {
	
		List<Endereco> list = new ArrayList<Endereco>();
		
		Session s = HibernateUtil.getSessionFactory().openSession();
		
		s.beginTransaction();
		
		Criteria crit = s.createCriteria(Endereco.class);
		crit.add(Restrictions.like("Desc_Endereco", '%' + strPesquisaEnd + '%')).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		list = crit.list();
		
		//.setResultTransformer(crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY));
		
		s.getTransaction().commit();
		s.close();
		return list;
	}
	
	public void removeEndereco(Integer id) {
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		Endereco e = (Endereco) s.load(Endereco.class, id);
		s.delete(e);
		s.getTransaction().commit();
		s.close();
	}

	public void editarEndereco(Endereco endereco) {
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		s.update(endereco);
		s.getTransaction().commit();
		s.close();
	}
	
	public void salvaEditar (Endereco endereco) {
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		s.saveOrUpdate(endereco);  // update(endereco);
		s.getTransaction().commit();
		s.close();
	}
	
	public void persistEnd (Endereco endereco) {
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		s.persist(endereco);
		s.getTransaction().commit();
		s.close();
	}
	
	public void mergeEnd (Endereco endereco) {
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		s.merge(endereco);
		s.getTransaction().commit();
		//s.flush(); // para retornar o id do objeto gravado
		s.close();
	}
}
