package entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import tabela.LegislacaoTabela;

@Entity
public class Legislacao implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column (name="legislacaoCodigo")
	private int legislacaoCodigo;
	
	@Column (name="descricaoArtigo", columnDefinition="varchar(100)")
	private String descArtigo;
	
	@Column (name="artigo", columnDefinition="varchar(1000)")
	private String legisArtigo;
	
		public Legislacao () {
			
		}
		//-- construtor  --//
		public Legislacao (LegislacaoTabela legTab) {
				
				this.legislacaoCodigo = legTab.getLegislacaoCodigo();
				this.descArtigo = legTab.getDescricaoArtigo();
				this.legisArtigo = legTab.getArtigo();
				
			}

	public int getLegislacaoCodigo() {
		return legislacaoCodigo;
	}

	public void setLegislacaoCodigo(int legislacaoCodigo) {
		this.legislacaoCodigo = legislacaoCodigo;
	}

	
	public String getDescArtigo() {
		return descArtigo;
	}
	public void setDescArtigo(String descArtigo) {
		this.descArtigo = descArtigo;
	}
	public String getLegisArtigo() {
		return legisArtigo;
	}

	public void setLegisArtigo(String legisArtigo) {
		this.legisArtigo = legisArtigo;
	}
	
	

}
