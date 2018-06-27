package dao;

import java.util.List;

import org.hibernate.Session;

import entity.Ato;
import entity.HibernateUtil;


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
		
		Session s = HibernateUtil.getSessionFactory().openSession();
		
		s.beginTransaction();
		
		List<Ato> list = s.createQuery(
				"SELECT a FROM Ato AS a "
				+ "JOIN FETCH a.atoVisCodigoFK av "
				+ "JOIN FETCH av.visEndCodigoFK "
				+ "WHERE ( a.atoIdentificacao LIKE '%"+strPesquisa+"%' "
						+ "OR a.atoSEI LIKE '%"+strPesquisa+"%') "
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



/*
 List<Ato> list = new ArrayList<Ato>();
Criteria crit = s.createCriteria(Ato.class);

Criterion atoIden = Restrictions.like("atoIdentificacao", '%' + strPesquisa + '%');
Criterion atoSei = Restrictions.like("atoSEI", '%' + strPesquisa + '%');

LogicalExpression orExp = Restrictions.or(atoIden,atoSei);

crit.add(orExp);

list = crit.list();


Session s = HibernateUtil.getSessionFactory().openSession();
*/
/* NÃO DÁ ERRO, MAS NÃO SEI COMO ACESSAR O ENDEREÇO
"SELECT a FROM Ato AS a JOIN FETCH a.atoVisCodigoFK JOIN FETCH Endereco e ON e.Cod_Endereco = a.atoVisCodigoFK.visEndCodigoFK "
		+ "WHERE ( a.atoIdentificacao LIKE '%"+strPesquisa+"%' "
				+ "OR a.atoSEI LIKE '%"+strPesquisa+"%') "
				+ ""
		).list(); 
*/

/* WHERE CORRETO
+ "WHERE ( a.atoIdentificacao LIKE '%"+strPesquisa+"%' "
+ "OR a.atoSEI LIKE '%"+strPesquisa+"%')"
).list();
	*/

/*
List<Ato> list = s.createQuery(
		"SELECT a FROM Ato AS a "
		+ "JOIN FETCH a.atoVisCodigoFK av "
		+ "JOIN FETCH av.visEndCodigoFK "
		+ "WHERE ( a.atoIdentificacao LIKE '%"+strPesquisa+"%' "
				+ "OR a.atoSEI LIKE '%"+strPesquisa+"%') "
				+ ""
		).list();
		
		List<Ato> list = s.createQuery(
		"SELECT a FROM Ato AS a "
		+ "JOIN FETCH a.atoVisCodigoFK av "
		+ "JOIN FETCH av.visEndCodigoFK ae "
		+ "JOIN ae.usuarios "
		+ "JOIN av.visListAtos "
		+ "WHERE ( a.atoIdentificacao LIKE '%"+strPesquisa+"%' "
				+ "OR a.atoSEI LIKE '%"+strPesquisa+"%') "
		).list();
*/

