package entity;


import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import tabela.DemandaTabela;

@Entity
public class Demanda implements Serializable {

	private static final long serialVersionUID = -6469401230304931190L;

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column (name="dem_id")
	private int demID;
	
	// @Column (name="descricaoArtigo", columnDefinition="varchar(100)")
	
	@ManyToOne (fetch = FetchType.LAZY) 
	@JoinColumn (name = "enderecoFK") // MUDAR PARA demEnderecoFK nas próximas criações de tabelas...
	private Endereco demEnderecoFK;
	
	@Column (name="dem_documento", columnDefinition="varchar(80)")
	private String demDocumento;
	
	@Column (name="dem_documento_sei", columnDefinition="varchar(25)")
	private String demDocumentoSEI; 
				// demDocumentoSEI
	
	@Column (name="dem_processo_sei", columnDefinition="varchar(25)")
	private String demProcessoSEI;
	
	@Column (name="dem_descricao", columnDefinition="varchar(200)")
	private String demDescricao;
	
	@Column (name="dem_distribuicao", columnDefinition="varchar(15)")
	private String demDistribuicao;
	
	@Column (name="dem_recebimento", columnDefinition="varchar(15)")
	private String demRecebimento;
	
	@Column (name="dem_atualizacao")
	private LocalDateTime demAtualizacao;
	
	
	//CONSTRUTOR PADRÃO
	public Demanda () {
		
	}
	
	//CONSTRUTOR DE EDITAR DOCUMENTO
	public Demanda(DemandaTabela denTab) {
		
		this.demID = denTab.getDemID();
		this.demDocumento = denTab.getDemDocumentoSEI();
		this.demDocumentoSEI = denTab.getDemDocumentoSEI();
		this.demProcessoSEI = denTab.getDemProcessoSEI();
		this.demDescricao = denTab.getDemDescricao();
		this.demDistribuicao = denTab.getDemDistribuicao();
		this.demRecebimento = denTab.getDemRecebimento();
		
		this.demAtualizacao = (LocalDateTime.now());
		
		this.demEnderecoFK = denTab.getDemEnderecoFK();
		
		
	}

	public int getDemID() {
		return demID;
	}

	public void setDemID(int demID) {
		this.demID = demID;
	}

	public Endereco getDemEnderecoFK() {
		return demEnderecoFK;
	}

	public void setDemEnderecoFK(Endereco demEnderecoFK) {
		this.demEnderecoFK = demEnderecoFK;
	}

	public String getDemDocumento() {
		return demDocumento;
	}

	public void setDemDocumento(String demDocumento) {
		this.demDocumento = demDocumento;
	}

	public String getDemDocumentoSEI() {
		return demDocumentoSEI;
	}

	public void setDemDocumentoSEI(String demDocumentoSEI) {
		this.demDocumentoSEI = demDocumentoSEI;
	}

	public String getDemProcessoSEI() {
		return demProcessoSEI;
	}

	public void setDemProcessoSEI(String demProcessoSEI) {
		this.demProcessoSEI = demProcessoSEI;
	}

	public String getDemDescricao() {
		return demDescricao;
	}

	public void setDemDescricao(String demDescricao) {
		this.demDescricao = demDescricao;
	}

	public String getDemDistribuicao() {
		return demDistribuicao;
	}

	public void setDemDistribuicao(String demDistribuicao) {
		this.demDistribuicao = demDistribuicao;
	}

	public String getDemRecebimento() {
		return demRecebimento;
	}

	public void setDemRecebimento(String demRecebimento) {
		this.demRecebimento = demRecebimento;
	}

	public LocalDateTime getDemAtualizacao() {
		return demAtualizacao;
	}

	public void setDemAtualizacao(LocalDateTime demAtualizacao) {
		this.demAtualizacao = demAtualizacao;
	}
	
	
		
			
}