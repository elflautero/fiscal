package entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import tabela.InterferenciaTabela;

@Entity
public class Interferencia implements Serializable {

	
	private static final long serialVersionUID = -7007101881780321473L;

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column 
	private int inter_Codigo;
	
	//-- RELACIONAMENTO ENDEREÇO --//
	@ManyToOne (fetch = FetchType.EAGER) 
	@JoinColumn (name = "inter_End_Codigo")
	private Endereco inter_End_CodigoFK;
	
	//-- RELACIONAMENTO SUBTERRÂNEA --//
			@OneToOne (cascade = CascadeType.ALL, mappedBy = "interf_SubFK")
			//@Fetch(FetchMode.JOIN)
			//
			private Subterranea sub_Interferencia_Codigo;
			
			
			public Subterranea getSub_Interferencia_Codigo() {
				return sub_Interferencia_Codigo;
			}

			public void setSub_Interferencia_Codigo(Subterranea sub_Interferencia_Codigo) {
				this.sub_Interferencia_Codigo = sub_Interferencia_Codigo;
			}

			
			//-- RELACIONAMENTO SUPERFICIAL --//
			@OneToOne (cascade = CascadeType.ALL, mappedBy = "interf_SuperFK")
			//@Fetch(FetchMode.JOIN)
			private Superficial super_Interferencia_Codigo;
		
			public Superficial getSuper_Interferencia_Codigo() {
				return super_Interferencia_Codigo;
			}

			public void setSuper_Interferencia_Codigo(Superficial super_Interferencia_Codigo) {
				this.super_Interferencia_Codigo = super_Interferencia_Codigo;
			}

	
	@Column (columnDefinition="varchar(30)")
	private String inter_Tipo;
	
	@Column (columnDefinition="varchar (20)")
	private String inter_Bacia;
	
	@Column (columnDefinition="varchar (2)")
	private String inter_UH;

	@Column (columnDefinition="varchar (50)")
	private String inter_Corpo_Hidrico;
	
	@Column
	private Double inter_Lat;
	
	@Column
	private Double inter_Lng;
	
	@Column (columnDefinition="varchar (20)")
	private String inter_Situacao;
	
	@Column (columnDefinition="varchar (90)")
	private String inter_Desc_Endereco;
	
	
	//CONSTRUTOR PADRÃO
		public Interferencia () {
			
		}
		
		//CONSTRUTOR DE EDITAR DOCUMENTO
		public Interferencia (InterferenciaTabela intTab) {
			
			this.inter_Codigo = intTab.getInter_Codigo();
			this.inter_Tipo = intTab.getInter_Tipo();
			this.inter_Bacia = intTab.getInter_Bacia();
			this.inter_UH = intTab.getInter_UH();
			this.inter_Corpo_Hidrico = intTab.getInter_Corpo_Hidrico();
			this.inter_Lat = intTab.getInter_Lat();
			this.inter_Lat = intTab.getInter_Lng();
			this.inter_Situacao = intTab.getInter_Situacao();
			this.inter_Desc_Endereco = intTab.getInter_Desc_Endereco();
			
			this.inter_End_CodigoFK = intTab.getEnderecoInterferenciaObjetoTabelaFK();
			
			this.sub_Interferencia_Codigo = intTab.getInterSub();
			
		}
	
	//-- GETTERS AND SETTERS --//
		public int getInter_Codigo() {
			return inter_Codigo;
		}

		public void setInter_Codigo(int inter_Codigo) {
			this.inter_Codigo = inter_Codigo;
		}

				//-- foreign key --//
				public Endereco getInter_End_CodigoFK() {
					return inter_End_CodigoFK;
				}
		
				public void setInter_End_CodigoFK(Endereco inter_End_Codigo) {
					this.inter_End_CodigoFK = inter_End_Codigo;
				}

		public String getInter_Tipo() {
			return inter_Tipo;
		}

		public void setInter_Tipo(String inter_Tipo) {
			this.inter_Tipo = inter_Tipo;
		}

		

		public String getInter_Bacia() {
			return inter_Bacia;
		}

		public void setInter_Bacia(String inter_Bacia) {
			this.inter_Bacia = inter_Bacia;
		}

		public String getInter_UH() {
			return inter_UH;
		}

		public void setInter_UH(String inter_UH) {
			this.inter_UH = inter_UH;
		}

		public String getInter_Corpo_Hidrico() {
			return inter_Corpo_Hidrico;
		}

		public void setInter_Corpo_Hidrico(String inter_Corpo_Hidrico) {
			this.inter_Corpo_Hidrico = inter_Corpo_Hidrico;
		}

		
		public Double getInter_Lat() {
			return inter_Lat;
		}

		public void setInter_Lat(Double inter_Lat) {
			this.inter_Lat = inter_Lat;
		}

		public Double getInter_Lng() {
			return inter_Lng;
		}

		public void setInter_Lng(Double inter_Lng) {
			this.inter_Lng = inter_Lng;
		}

		public String getInter_Situacao() {
			return inter_Situacao;
		}

		public void setInter_Situacao(String inter_Situacao) {
			this.inter_Situacao = inter_Situacao;
		}

		public String getInter_Desc_Endereco() {
			return inter_Desc_Endereco;
		}

		public void setInter_Desc_Endereco(String inter_Desc_Endereco) {
			this.inter_Desc_Endereco = inter_Desc_Endereco;
		}
		
		
}
