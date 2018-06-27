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
import javax.persistence.OneToMany;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import tabela.EnderecoTabela;

@Entity
public class Endereco implements Serializable{

	private static final long serialVersionUID = -4904823824516549278L;

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column 
	private int Cod_Endereco;
	
	
	@Column (columnDefinition="varchar(90)")
	private String Desc_Endereco;
	
	@Column (columnDefinition="varchar(20)")
	private String RA_Endereco;
	
	@Column (columnDefinition="varchar(20)")
	private String CEP_Endereco;
	
	@Column (columnDefinition="varchar(20)")
	private String Cid_Endereco;
	
	@Column (columnDefinition="varchar(2)")
	private String UF_Endereco;
	
	@Column
	private Double Lat_Endereco;
	
	@Column
	private Double Lon_Endereco;
	
	//-- Lista de enderecos vinculados --//
	@OneToMany (mappedBy = "demEnderecoFK", cascade = CascadeType.MERGE,
			fetch = FetchType.LAZY, targetEntity = Demanda.class)
	@Fetch(FetchMode.SUBSELECT) 
	private List<Demanda> demandas = new ArrayList<Demanda>();
	
		//-- Lista de interferencias vinculadas --//
		@OneToMany (mappedBy = "inter_End_CodigoFK", cascade = CascadeType.MERGE,
				fetch = FetchType.LAZY, targetEntity = Interferencia.class)
		@Fetch(FetchMode.SUBSELECT) 
		private List<Interferencia> interferencias = new ArrayList<Interferencia>();
	
				//-- Lista de usuários vinculados --//
				@OneToMany (mappedBy = "usEndCodigoFK", cascade = CascadeType.MERGE,
						fetch = FetchType.LAZY, targetEntity = Usuario.class)
				@Fetch(FetchMode.SUBSELECT)
				private List<Usuario> usuarios = new ArrayList<Usuario>();
		
						//-- Lista de fiscais vinculados --//
						@OneToMany (mappedBy = "fis_End_Codigo", cascade = CascadeType.MERGE,
								fetch = FetchType.LAZY, targetEntity = Fiscal.class)
						@Fetch(FetchMode.SUBSELECT) 
						private List<Fiscal> fiscais = new ArrayList<Fiscal>();
				
								//-- Lista de vistorias vinculados --//
								@OneToMany (mappedBy = "visEndCodigoFK", cascade = CascadeType.MERGE,
										fetch = FetchType.LAZY, targetEntity = Vistoria.class)
								@Fetch(FetchMode.SUBSELECT) 
								private List<Vistoria> vistorias = new ArrayList<Vistoria>();
		
				
	
	//-- Construtor padrão -- //
	public Endereco () {
		
	}
	
	//-- Construtor --//
	public Endereco (EnderecoTabela endTab) {
		
		this.Cod_Endereco = endTab.getCod_Endereco();
		this.Desc_Endereco = endTab.getDesc_Endereco();
		this.RA_Endereco = endTab.getRA_Endereco();
		this.CEP_Endereco = endTab.getCEP_Endereco();
		this.Cid_Endereco = endTab.getCid_Endereco();
		this.UF_Endereco = endTab.getUF_Endereco();
		
		this.Lat_Endereco = endTab.getLat_Endereco();
		this.Lon_Endereco = endTab.getLon_Endereco();
	}
	
	// -- GETTERS AND SETTERS - //
	
	
	//-- get e set Latitude --//
	public Double getLat_Endereco() {
		return Lat_Endereco;
	}

	public void setLat_Endereco(Double lat_Endereco) {
		Lat_Endereco = lat_Endereco;
	}

	
	//-- get e set Longitude --//
	public Double getLon_Endereco() {
		return Lon_Endereco;
	}

	public void setLon_Endereco(Double lon_Endereco) {
		Lon_Endereco = lon_Endereco;
	}

	
	//-- get e set OneToMany List Demandas --//
	public List<Demanda> getListDemandas() {
		return demandas;
	}

	public void setListDemandas(List<Demanda> demandas) {
		this.demandas = demandas;
	}
	
		//-- get e set OneToMany List Interferencia --//
			public List<Interferencia> getListInterferencias() {
				return interferencias;
			}
	
			public void setListInterferencias(List<Interferencia> interferencias) {
				this.interferencias = interferencias;
			}
	
				//-- get e set OneToMany List Usuarios --//
				public List<Usuario> getListUsuarios() {
					return usuarios;
				}
				
				public void setListUsuarios(List<Usuario> usuarios) {
					this.usuarios = usuarios;
				}
				
					//-- get e set OneToMany List Fiscais --//
					public List<Fiscal> getListFiscais() {
						return fiscais;
					}
					
					public void setListFiscais(List<Fiscal> fiscais) {
						this.fiscais = fiscais;
					}
					
						//-- get e set OneToMany List Vistorias --//
						public List<Vistoria> getListVistorias() {
							return vistorias;
						}
						
						public void setListVistorias(List<Vistoria> vistorias) {
							this.vistorias = vistorias;
						}
				
		
	public int getCod_Endereco() {
		return Cod_Endereco;
	}

	public void setCod_Endereco(int cod_Endereco) {
		Cod_Endereco = cod_Endereco;
	}

	public String getDesc_Endereco() {
		return Desc_Endereco;
	}

	public void setDesc_Endereco(String desc_Endereco) {
		Desc_Endereco = desc_Endereco;
	}

	public String getRA_Endereco() {
		return RA_Endereco;
	}

	public void setRA_Endereco(String rA_Endereco) {
		RA_Endereco = rA_Endereco;
	}

	public String getCEP_Endereco() {
		return CEP_Endereco;
	}

	public void setCEP_Endereco(String cEP_Endereco) {
		CEP_Endereco = cEP_Endereco;
	}

	public String getCid_Endereco() {
		return Cid_Endereco;
	}

	public void setCid_Endereco(String cid_Endereco) {
		Cid_Endereco = cid_Endereco;
	}

	public String getUF_Endereco() {
		return UF_Endereco;
	}

	public void setUF_Endereco(String uF_Endereco) {
		UF_Endereco = uF_Endereco;
	}
	
}