package entity;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	
	private static SessionFactory factory = null;
	
	private static Configuration conf;
	
	private static SessionFactory buildSessionFactory () {
		try {
		conf = new Configuration();
		conf.configure("/Hibernate.cfg.xml"); // /fiscalizacao/src/main/resources/Hibernate.cfg.xml 
		// /resources/hibernate.cfg.xml  original: hibernate.cfg.xml
		
			System.out.println("Configurou!");
		
		factory = conf.buildSessionFactory();
		
			System.out.println("Construiu a f�brica de sess�es");
		
		return factory;
		
		} catch (Throwable ex) {
			ex.printStackTrace();
			throw new ExceptionInInitializerError(ex);
		}
	}
	
	public static SessionFactory getSessionFactory () {
		if (factory == null) 
			factory = buildSessionFactory();
		
		return factory;
	}
}