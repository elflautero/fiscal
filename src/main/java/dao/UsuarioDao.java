package dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import entity.HibernateUtil;
import entity.Usuario;

public class UsuarioDao {
	
	
public void salvaUsuario (Usuario usuario) {
		
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		s.save(usuario);
		s.getTransaction().commit();
		s.close();
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Usuario> listUsuario(String strPesquisaUsuario) {
		
		List<Usuario> list = new ArrayList<Usuario>();
		
		Session s = HibernateUtil.getSessionFactory().openSession();
		
		s.beginTransaction();
		
		Criteria crit = s.createCriteria(Usuario.class);
		crit.add(Restrictions.like("usNome", '%' + strPesquisaUsuario + '%'));
		list = crit.list();
			
		s.getTransaction().commit();
		s.close();
		return list;
	}
	
	public void removeUsuario(Integer id) {
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		Usuario u = (Usuario) s.load(Usuario.class, id);
		s.delete(u);
		s.getTransaction().commit();
		s.close();
	}

	public void editarUsurario(Usuario usuario) {
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		s.update(usuario);
		s.getTransaction().commit();
		s.close();
	}
	
	public void mergeUsuario(Usuario usuario) {
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		s.merge(usuario);
		s.getTransaction().commit();
		s.close();
	}
	
	public void persistUsuario(Usuario usuario) {
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		s.persist(usuario);
		s.getTransaction().commit();
		s.close();
	}

}
