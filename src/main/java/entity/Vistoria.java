package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import tabela.VistoriaTabela;

@Entity
public class Vistoria implements Serializable{

	private static final long serialVersionUID = -8551878034448323133L;

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column (name="vis_codigo")
	private int visCodigo;
	
		//-- endereço --//
		@ManyToOne (fetch = FetchType.EAGER) 
		@JoinColumn (name = "vis_end_codigo")
		private Endereco visEndCodigoFK;
		
			//-- Lista de atos vinculados --//
			@OneToMany (mappedBy = "atoVisCodigoFK", cascade = CascadeType.MERGE,
					fetch = FetchType.EAGER, targetEntity = Ato.class)
			@Fetch(FetchMode.SUBSELECT) 
			private List<Ato> visListAtos = new ArrayList<Ato>();
	
	@Column (name="vis_identificacao", columnDefinition="varchar(20)") // 15/2018
	private String visIdentificacao;
	
	@Column (name="vis_SEI", columnDefinition="varchar(20)") // 3561241
	private String visSEI;
			
	@Column (name="vis_infracoes", columnDefinition="varchar(20)") // vai ficar assim no  banco, incisos: 1,2,3,4,5,6,7,8,9
	private String visInfracoes;

	@Column (name="vis_penalidades", columnDefinition="varchar(20)") // vai ficar assim no  banco, incisos: 1,2,3,4,5,6,7,8,9
	private String visPenalidades;
	
	@Column (name="vis_atenuantes", columnDefinition="varchar(20)")
	private String visAtenuantes;
	
	@Column (name="vis_agravantes", columnDefinition="varchar(20)")
	private String visAgravantes;
	
	@Column (name="vis_objecto", columnDefinition="varchar(600)")
	private String visObjeto;
	
	@Column (name="vis_apresentacao", columnDefinition="varchar(600)")
	private String visApresentacao;
	
	@Column (name="vis_relato", columnDefinition="varchar(20000)")
	private String visRelato;
	
	@Column (name="vis_recomendacoes", columnDefinition="varchar(600)")
	private String visRecomendacoes;
	
	
	@Column (name="vis_Data_Fiscalizacao", columnDefinition="varchar(15)")
	private String visDataFiscalizacao;
	
	@Column (name="vis_Data_Criacao", columnDefinition="varchar(15)")
	private String visDataCriacao;
	
	//-- construtor padrão --//
	public Vistoria (VistoriaTabela visTab) {
		
		this.visCodigo = visTab.getVisCodigo();
		this.visEndCodigoFK = visTab.getVisEndCodigoFK();
		this.visListAtos = visTab.getListAtos();
		this.visIdentificacao = visTab.getVisIdentificacao();
		this.visSEI = visTab.getVisSei();
		this.visInfracoes = visTab.getVisInfracoes();
		this.visPenalidades = visTab.getVisPenalidades();
		this.visAtenuantes = visTab.getVisAtenuantes();
		this.visAgravantes = visTab.getVisAgravantes();
		this.visObjeto = visTab.getVisObjeto();
		this.visApresentacao = visTab.getVisApresentacao();
		this.visRelato = visTab.getVisRelato();
		this.visRecomendacoes = visTab.getVisRecomendacoes();
		this.visDataFiscalizacao = visTab.getVisDataFiscalizacao();
		this.visDataCriacao = visTab.getVisDataCriacao();
		
	}
	
	//-- construtor vistoria tabela --//
	
	public Vistoria () {
		
	}
	// getters e setters //
	
	public String getVisIdentificacao() {
		return visIdentificacao;
	}

	public void setVisIdentificacao(String visIdentificacao) {
		this.visIdentificacao = visIdentificacao;
	}

	public String getVisSEI() {
		return visSEI;
	}

	public void setVisSEI(String visSEI) {
		this.visSEI = visSEI;
	}

	public int getVisCodigo() {
		return visCodigo;
	}

	public void setVisCodigo(int visCodigo) {
		this.visCodigo = visCodigo;
	}


	public String getVisObjeto() {
		return visObjeto;
	}

	public void setVisObjeto(String visObjeto) {
		this.visObjeto = visObjeto;
	}

	public String getVisApresentacao() {
		return visApresentacao;
	}

	public void setVisApresentacao(String visApresentacao) {
		this.visApresentacao = visApresentacao;
	}

	public String getVisRelato() {
		return visRelato;
	}

	public void setVisRelato(String visRelato) {
		this.visRelato = visRelato;
	}

	public String getVisRecomendacoes() {
		return visRecomendacoes;
	}

	public void setVisRecomendacoes(String visRecomendacoes) {
		this.visRecomendacoes = visRecomendacoes;
	}
	
	public String getVisInfracoes() {
		return visInfracoes;
	}

	public void setVisInfracoes(String visInfracoes) {
		this.visInfracoes = visInfracoes;
	}

	public String getVisPenalidades() {
		return visPenalidades;
	}

	public void setVisPenalidades(String atoPenalidades) {
		this.visPenalidades = atoPenalidades;
	}

	public String getVisAtenuantes() {
		return visAtenuantes;
	}

	public void setVisAtenuantes(String visAtenuantes) {
		this.visAtenuantes = visAtenuantes;
	}

	public String getVisAgravantes() {
		return visAgravantes;
	}

	public void setVisAgravantes(String visAgravantes) {
		this.visAgravantes = visAgravantes;
	}
	
	public Endereco getVisEndCodigoFK() {
		return visEndCodigoFK;
	}

	public void setVisEndCodigoFK(Endereco visEndCodigoFK) {
		this.visEndCodigoFK = visEndCodigoFK;
	}

	public List<Ato> getVisListAtos() {
		return visListAtos;
	}

	public void setVisListAtos(List<Ato> atos) {
		this.visListAtos = atos;
	}

	public String getVisDataFiscalizacao() {
		return visDataFiscalizacao;
	}

	public void setVisDataFiscalizacao(String visDataFiscalizacao) {
		this.visDataFiscalizacao = visDataFiscalizacao;
	}

	public String getVisDataCriacao() {
		return visDataCriacao;
	}

	public void setVisDataCriacao(String visDataCriacao) {
		this.visDataCriacao = visDataCriacao;
	}
	
	
	
}
