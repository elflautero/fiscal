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

import tabela.DenunciaTabela;

@Entity
public class Denuncia implements Serializable {

	private static final long serialVersionUID = -6469401230304931190L;

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column (name="denunciaID")
	private int denunciaID;
	
	// @Column (name="descricaoArtigo", columnDefinition="varchar(100)")
	
	@ManyToOne (fetch = FetchType.EAGER) 
	@JoinColumn (name = "enderecoFK")
	private Endereco denEnderecoFK;
	
	@Column (name="documento", columnDefinition="varchar(80)")
	private String denDocumento;
	
	@Column (name="documentoSEI", columnDefinition="varchar(25)")
	private String denDocumentoSEI;
	
	@Column (name="processoSEI", columnDefinition="varchar(25)")
	private String denProcessoSEI;
	
	@Column (name="descricao", columnDefinition="varchar(200)")
	private String denDescricao;
	
	@Column (name="dataDistribuicao", columnDefinition="varchar(15)")
	private String denDataDistribuicao;
	
	@Column (name="dataRecebimento", columnDefinition="varchar(15)")
	private String denDataRecebimento;
	
	
	//CONSTRUTOR PADRÃO
	public Denuncia () {
		
	}
	
	//CONSTRUTOR DE EDITAR DOCUMENTO
	public Denuncia(DenunciaTabela denunciaTabela) {
		
		this.denunciaID = denunciaTabela.getDenDenunciaID();
		this.denDocumento = denunciaTabela.getDenDocumento();
		this.denDocumentoSEI = denunciaTabela.getDenDecumentoSEI();
		this.denProcessoSEI = denunciaTabela.getDenProcessoSEI();
		this.denDescricao = denunciaTabela.getDenDescricao();
		this.denDataDistribuicao = denunciaTabela.getDenDataDistribuicao();
		this.denDataRecebimento = denunciaTabela.getDenDataRecebimento();
		
		this.denEnderecoFK = denunciaTabela.getDenEnderecoFK();
	}
	

			//-- foreign key --//
			public Endereco getDenEnderecoFK() {
				return denEnderecoFK;
			}
		
			public void setDenEnderecoFK(Endereco denEnderecoFK) {
				this.denEnderecoFK = denEnderecoFK;
			}

			public int getDenunciaID() {
				return denunciaID;
			}

			public void setDenunciaID(int denunciaID) {
				this.denunciaID = denunciaID;
			}

			public String getDenDocumento() {
				return denDocumento;
			}

			public void setDenDocumento(String denDocumento) {
				this.denDocumento = denDocumento;
			}

			public String getDenDocumentoSEI() {
				return denDocumentoSEI;
			}

			public void setDenDocumentoSEI(String denDocumentoSEI) {
				this.denDocumentoSEI = denDocumentoSEI;
			}

			public String getDenProcessoSEI() {
				return denProcessoSEI;
			}

			public void setDenProcessoSEI(String denProcessoSEI) {
				this.denProcessoSEI = denProcessoSEI;
			}

			public String getDenDescricao() {
				return denDescricao;
			}

			public void setDenDescricao(String denDescricao) {
				this.denDescricao = denDescricao;
			}

			public String getDenDataDistribuicao() {
				return denDataDistribuicao;
			}

			public void setDenDataDistribuicao(String denDataDistribuicao) {
				this.denDataDistribuicao = denDataDistribuicao;
			}

			public String getDenDataRecebimento() {
				return denDataRecebimento;
			}

			public void setDenDataRecebimento(String denDataRecebimento) {
				this.denDataRecebimento = denDataRecebimento;
			}
	
}