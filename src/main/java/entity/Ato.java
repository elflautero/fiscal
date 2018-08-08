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

import tabela.AtoTabela;


@Entity
public class Ato implements Serializable{

	
	private static final long serialVersionUID = -3230783682956535548L;
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column (name="ato_codigo")
	private int atoCodigo;
	
		//-- vistoria --//
		@ManyToOne (fetch = FetchType.LAZY) 
		@JoinColumn (name = "ato_vis_codigoFK")
		private Vistoria atoVisCodigoFK;
	
	@Column (name="ato_tipo", columnDefinition="varchar(40)")  // auto de infração de advertência, relatório de vistoria
	private String atoTipo;
	
	@Column (name="ato_identificacao", columnDefinition="varchar(20)") // 15/2018
	private String atoIdentificacao;
	
	@Column (name="ato_SEI", columnDefinition="varchar(20)") // 3561241
	private String atoSEI;
	
	@Column (name="ato_caracaterizacao", columnDefinition="varchar(2000)") // texto
	private String atoCaracterizacao;
	
	@Column (name="ato_data_fiscalizacao", columnDefinition="varchar(20)")
	private String atoDataFiscalizacao;
	
	@Column (name="ato_data_criacao", columnDefinition="varchar(20)")
	private String atoDataCriacao;
	
	// construtor padrão //

	public Ato () {
		
	}
	
	//-- construtor  --//
	public Ato (AtoTabela atoTab) {
			
			this.atoCodigo = atoTab.getAtoCodigo();
			this.atoVisCodigoFK = atoTab.getAtoVistoriaFK();
			this.atoTipo = atoTab.getAtoTipo();
			this.atoIdentificacao = atoTab.getAtoIdentificacao();
			this.atoSEI = atoTab.getAtoSEI();
			this.atoCaracterizacao = atoTab.getAtoCaracterizacao();
			this.atoDataFiscalizacao = atoTab.getAtoDataFiscalizacao();
			this.atoDataCriacao = atoTab.getAtoDataCriacao();
			
		}
	
	//-- getters and setters --//
	public int getAtoCodigo() {
		return atoCodigo;
	}

	public String getAtoTipo() {
		return atoTipo;
	}

	public String getAtoIdentificacao() {
		return atoIdentificacao;
	}

	public String getAtoSEI() {
		return atoSEI;
	}

	public String getAtoCaracterizacao() {
		return atoCaracterizacao;
	}

	public String getAtoDataFiscalizacao() {
		return atoDataFiscalizacao;
	}

	public String getAtoDataCriacao() {
		return atoDataCriacao;
	}

	public void setAtoCodigo(int atoCodigo) {
		this.atoCodigo = atoCodigo;
	}

	
	public void setAtoTipo(String atoTipo) {
		this.atoTipo = atoTipo;
	}

	public void setAtoIdentificacao(String atoIdentificacao) {
		this.atoIdentificacao = atoIdentificacao;
	}

	public void setAtoSEI(String atoSEI) {
		this.atoSEI = atoSEI;
	}

	public void setAtoCaracterizacao(String atoCaracterizacao) {
		this.atoCaracterizacao = atoCaracterizacao;
	}

	public void setAtoDataFiscalizacao(String atoDataFiscalizacao) {
		this.atoDataFiscalizacao = atoDataFiscalizacao;
	}

	public void setAtoDataCriacao(String atoDataCriacao) {
		this.atoDataCriacao = atoDataCriacao;
	}

	public Vistoria getAtoVisCodigoFK() {
		return atoVisCodigoFK;
	}

	public void setAtoVisCodigoFK(Vistoria visGeral) {
		this.atoVisCodigoFK = visGeral;
	}

}
