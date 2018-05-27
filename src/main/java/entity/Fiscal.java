package entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Fiscal implements Serializable {

	
	private static final long serialVersionUID = 1L;

	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column 
	private int fis_Codido;
	
	@ManyToOne (fetch = FetchType.EAGER) 
	@JoinColumn (name = "fis_End_Codigo")
	private Endereco fis_End_Codigo;
	
	@Column (columnDefinition="varchar(60)")
	private String fis_Nome;
	
	@Column (columnDefinition="varchar(35)")
	private String fis_Cargo;
	
	@Column (columnDefinition="varchar(20)")
	private String fis_Matricula;
	
	@Column (columnDefinition="varchar(20)")
	private String fis_Senha;
	
	@Column (columnDefinition="int(1) default 0")
	private int fis_Situacao; // ativo ou inativo
	
}

/* BANCO DE DADOS
1 Cod_Fiscal int(11) AUTO_INCREMENT 

2 Nome_Fiscal varchar(50) 

3 Cargo_Fiscal varchar(25) 

4 Matricula_Fiscal varchar(15) 

5 Senha_Fiscal varchar(15) 

*/