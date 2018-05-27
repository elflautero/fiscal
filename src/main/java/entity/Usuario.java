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

import tabela.UsuarioTabela;

@Entity
public class Usuario implements Serializable {

	private static final long serialVersionUID = 3804791055346363735L;
	
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column (name = "us_codigo")
	private int usCodigo;
	
	@ManyToOne (fetch = FetchType.EAGER) 
	@JoinColumn (name = "us_end_codigoFK")
	private Endereco usEndCodigoFK;
	
	@Column (name = "us_tipo", columnDefinition="varchar(8)")
	private String usTipo;
	
	@Column (name = "us_nome", columnDefinition="varchar(80)")
	private String usNome;
	
	@Column (name = "us_cpfcnpj", columnDefinition="varchar(20)")
	private String usCPFCNPJ;
	
	@Column (name = "us_descricao_end", columnDefinition="varchar (80)")
	private String usDescricaoEnd;
	
	@Column (name = "us_ra", columnDefinition="varchar (20)")
	private String usRA;
	
	@Column (name = "us_cidade", columnDefinition="varchar (20)")
	private String usCidade;
	
	@Column (name = "us_estado", columnDefinition="varchar (2)")
	private String usEstado;
	

	@Column (name = "us_cep", columnDefinition="varchar (20)")
	private String usCEP;
	
	@Column (name = "us_telefone", columnDefinition="varchar (20)")
	private String usTelefone;
	
	@Column (name = "us_celular", columnDefinition="varchar (20)")
	private String usCelular;
	
	@Column (name = "us_email", columnDefinition="varchar (70)")
	private String usEmail;
	
	//-- construtor padrão --//
	
	public Usuario () {
		
	}
	
	//-- Construtor --//
	
	public Usuario (UsuarioTabela usTab) {
		
		this.usCodigo = usTab.getUsCodigo();
		this.usTipo = usTab.getUsTipo();
		this.usNome = usTab.getUsNome();
		this.usCPFCNPJ = usTab.getUsCPFCNPJ();
		this.usDescricaoEnd = usTab.getUsDescricaoEnd();
		this.usRA = usTab.getUsRA();
		this.usCidade = usTab.getUsCidade();
		this.usCEP = usTab.getUsCEP();
		this.usTelefone = usTab.getUsTelefone();
		this.usCelular = usTab.getUsCelular();
		this.usEmail = usTab.getUsEmail();
		
		this.usEndCodigoFK = usTab.getEnderecoUsObjetoTabelaFK();
		
	}
	
	//-- getters and setters --//

	public int getUsCodigo() {
		return usCodigo;
	}

	public void setUsCodigo(int usCodigo) {
		this.usCodigo = usCodigo;
	}

	public Endereco getUsEndCodigoFK() {
		return usEndCodigoFK;
	}

	public void setUsEndCodigoFK(Endereco usEndCodigoFK) {
		this.usEndCodigoFK = usEndCodigoFK;
	}

	public String getUsTipo() {
		return usTipo;
	}

	public void setUsTipo(String usTipo) {
		this.usTipo = usTipo;
	}

	public String getUsNome() {
		return usNome;
	}

	public void setUsNome(String usNome) {
		this.usNome = usNome;
	}

	public String getUsCPFCNPJ() {
		return usCPFCNPJ;
	}

	public void setUsCPFCNPJ(String usCPFCNPJ) {
		this.usCPFCNPJ = usCPFCNPJ;
	}

	public String getUsDescricaoEnd() {
		return usDescricaoEnd;
	}

	public void setUsDescricaoEnd(String usDecricaoEnd) {
		this.usDescricaoEnd = usDecricaoEnd;
	}

	public String getUsRA() {
		return usRA;
	}

	public void setUsRA(String usRA) {
		this.usRA = usRA;
	}

	public String getUsCidade() {
		return usCidade;
	}

	public void setUsCidade(String usCidade) {
		this.usCidade = usCidade;
	}
	
	//Estado
	public String getUsEstado() {
		return usEstado;
	}

	public void setUsEstado(String usEstado) {
		this.usEstado = usEstado;
	}

	public String getUsCEP() {
		return usCEP;
	}

	public void setUsCEP(String usCEP) {
		this.usCEP = usCEP;
	}

	public String getUsTelefone() {
		return usTelefone;
	}

	public void setUsTelefone(String usTelefone) {
		this.usTelefone = usTelefone;
	}

	public String getUsCelular() {
		return usCelular;
	}

	public void setUsCelular(String usCelular) {
		this.usCelular = usCelular;
	}

	public String getUsEmail() {
		return usEmail;
	}

	public void setUsEmail(String usEmail) {
		this.usEmail = usEmail;
	}


}
