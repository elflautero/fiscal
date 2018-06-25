package dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import entity.Demanda;
import entity.HibernateUtil;
import entity.Interferencia;
import entity.Subterranea;

public class InterferenciaDao {

	
public void salvaInterferencia (Interferencia interferencia) {
		
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		s.save(interferencia);
		s.getTransaction().commit();
		s.close();
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Interferencia> listInterferencia(String strPesquisa) throws Exception {
		
		
		/*
		List<Interferencia> list = new ArrayList<Interferencia>();
		
		Session s = HibernateUtil.getSessionFactory().openSession();
		
		s.beginTransaction();
		
		Criteria crit = s.createCriteria(Interferencia.class);
		crit.add(Restrictions.like("inter_Desc_Endereco", '%' + strPesquisaInterferencia + '%'));
		list = crit.list();
		*/
		
		//JOIN FETCH i.sub_Interferencia_Codigo JOIN FETCH i.super_Interferencia_Codigo 
		// sub_Interferencia_Codigo
		//super_Interferencia_Codigo
		//inter_End_CodigoFK
		
		Session s = HibernateUtil.getSessionFactory().openSession();
		
		s.beginTransaction();
		
		List<Interferencia> list = new ArrayList<Interferencia>();
		
		/*
		List<Interferencia> listSub = s.createQuery(
				"SELECT i FROM Interferencia AS i "
				+ "JOIN FETCH i.inter_End_CodigoFK JOIN FETCH i.sub_Interferencia_Codigo "
				+ "WHERE ( i.inter_Desc_Endereco LIKE '%"+strPesquisa+"%' "
						+ "OR i.inter_Tipo LIKE '%"+strPesquisa+"%')"
				).list();
		
		List<Interferencia> listSup = s.createQuery(
				"SELECT i FROM Interferencia AS i "
				+ "JOIN FETCH i.inter_End_CodigoFK JOIN FETCH i.super_Interferencia_Codigo "
				+ "WHERE ( i.inter_Desc_Endereco LIKE '%"+strPesquisa+"%' "
						+ "OR i.inter_Tipo LIKE '%"+strPesquisa+"%')"
				).list();
				*/
		//try {
		List<Interferencia> listOutros = s.createQuery(
					"SELECT i FROM Interferencia AS i "
				+ 	"JOIN FETCH i.inter_End_CodigoFK "
				+	"LEFT OUTER JOIN FETCH i.super_Interferencia_Codigo "		
				+	"LEFT OUTER JOIN FETCH i.sub_Interferencia_Codigo "
				+ 	"WHERE ( i.inter_Desc_Endereco LIKE '%"+strPesquisa+"%' OR i.inter_Tipo LIKE '%"+strPesquisa+"%')"
				).list();
		/*} catch (Exception e) {
			
		}*/
		
		//JOIN FETCH i.inter_End_CodigoFK 
		//list.addAll(listSub);
		//list.addAll(listSup);
		list.addAll(listOutros);
		
		s.getTransaction().commit();
		s.close();
		return list;
	}
	
	public void removeInterferencia(Integer id) {
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		Interferencia i = (Interferencia) s.load(Interferencia.class, id);
		s.delete(i);
		s.getTransaction().commit();
		s.close();
	}

	public void editarInterferencia(Interferencia interferencia) {
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		s.update(interferencia);
		s.getTransaction().commit();
		s.close();
	}
	
	public void mergeInterferencia(Interferencia interferencia) {
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		s.merge(interferencia);
		s.getTransaction().commit();
		s.close();
	}
	
	public void persistInterferencia(Interferencia interferencia) {
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		s.persist(interferencia);
		s.getTransaction().commit();
		s.close();
	}
	
	
	// listar subterranea
	
	@SuppressWarnings("unchecked")
	public List<Subterranea> listSubterranea(String strSubPesquisa) {
		
		List<Subterranea> list = new ArrayList<Subterranea>();
		
		Session s = HibernateUtil.getSessionFactory().openSession();
		
		s.beginTransaction();
		
		Criteria crit = s.createCriteria(Demanda.class);
		crit.add(Restrictions.like("sub_Interferencia_Codigo", strSubPesquisa));
		list = crit.list();
		
		s.getTransaction().commit();
		s.close();
		return list;
	}
	
}
