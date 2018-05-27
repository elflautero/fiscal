package entity;

import java.io.Serializable;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Subterranea implements Serializable {
	
	private static final long serialVersionUID = 8669422759075749625L;

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column 
	private int sub_Codigo;
	
		//-- OneToOne subterrâneo e interferência --//
		@OneToOne 
		@JoinColumn (name = "sub_Interferencia_Codigo")
		private Interferencia interf_SubFK;
	
	@Column (columnDefinition="varchar (10)")
	private String sub_Poco;

	@Column (columnDefinition="varchar(3)")
	private String sub_Caesb;  // tem caesb () sim () não
	
	@Column (columnDefinition="varchar(10)")
	private String sub_Sistema;
	
	@Column (columnDefinition="varchar(5)")
	private String sub_Estatico;  // em metros
	
	@Column (columnDefinition="varchar(5)")
	private String sub_Dinamico;  // em metros
	
	@Column (columnDefinition="varchar(5)")
	private String sub_Vazao;  // em l/h - litros por hora
	
	@Column (columnDefinition="varchar(5)")
	private String sub_Profundidade;  // em metros
	
	@Column (columnDefinition="varchar (20)")
	private String sub_Data;

	//-- getters and setters --//
	
	public Subterranea () {
		
	}
	

	public int getSub_Codigo() {
		return sub_Codigo;
	}

	public void setSub_Codigo(int sub_Codigo) {
		this.sub_Codigo = sub_Codigo;
	}

	public Interferencia getInterf_SubFK() {
		return interf_SubFK;
	}

	public void setInterf_SubFK(Interferencia interf_SubFK) {
		this.interf_SubFK = interf_SubFK;
	}

	public String getSub_Poco() {
		return sub_Poco;
	}

	public void setSub_Poco(String sub_Poco) {
		this.sub_Poco = sub_Poco;
	}

	public String getSub_Caesb() {
		return sub_Caesb;
	}

	public void setSub_Caesb(String sub_Caesb) {
		this.sub_Caesb = sub_Caesb;
	}

	public String getSub_Sistema() {
		return sub_Sistema;
	}

	public void setSub_Sistema(String sub_Sistema) {
		this.sub_Sistema = sub_Sistema;
	}

	public String getSub_Estatico() {
		return sub_Estatico;
	}

	public void setSub_Estatico(String sub_Estatico) {
		this.sub_Estatico = sub_Estatico;
	}

	public String getSub_Dinamico() {
		return sub_Dinamico;
	}

	public void setSub_Dinamico(String sub_Dinamico) {
		this.sub_Dinamico = sub_Dinamico;
	}

	public String getSub_Vazao() {
		return sub_Vazao;
	}

	public void setSub_Vazao(String sub_Vazao) {
		this.sub_Vazao = sub_Vazao;
	}

	public String getSub_Profundidade() {
		return sub_Profundidade;
	}

	public void setSub_Profundidade(String sub_Profundidade) {
		this.sub_Profundidade = sub_Profundidade;
	}

	public String getSub_Data() {
		return sub_Data;
	}

	public void setSub_Data(String sub_Data) {
		this.sub_Data = sub_Data;
	}

}
