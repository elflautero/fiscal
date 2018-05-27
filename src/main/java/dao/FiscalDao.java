package dao;

import org.hibernate.Session;

import entity.Fiscal;
import entity.HibernateUtil;


public class FiscalDao {

	
	public void addFiscal (Fiscal fiscal) {
		try {
		
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		s.save(fiscal);
		s.getTransaction().commit(); 
		s.close();
		} catch (Throwable ex) {
			ex.printStackTrace();
			throw new ExceptionInInitializerError(ex);
			
		}
	
	}
}