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
public class Superficial implements Serializable{

	private static final long serialVersionUID = -2898246100522194510L;

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column 
	private int sup_Codigo;
	
	//-- OneToOne superficial e interferência --//
			@OneToOne
			@JoinColumn (name = "super_Interferencia_Codigo")
			private Interferencia interf_SuperFK;
	
	@Column (columnDefinition="varchar(15)")
	private String sup_Captacao; // gravidade, bombeamento, outro
	
	@Column (columnDefinition="varchar(15)")
	private String sup_Local; //-- () canal () rio () reservatório () lago natural () nascente
	
	@Column (columnDefinition="varchar(30)")
	private String sup_Bomba; // marca
	
	@Column (columnDefinition="varchar(10)")
	private String sup_Potencia; // em cv - cavalos
	
	@Column (columnDefinition="varchar(10)")
	private String sup_Area; /// em ha - hectares
	
	@Column (columnDefinition="varchar(15)")
	private String sup_Data;  // data de operação
	
	@Column (columnDefinition="varchar(3)")
	private String sup_Caesb;  // tem caesb () sim () não
	
	@Column (columnDefinition="varchar(4)")
	private String sup_Tempo;  // tempo de captação (h/dia)
	
	
	
	
	//-- getters and setters --//

	public Interferencia getInterf_SuperFK() {
		return interf_SuperFK;
	}

	public void setInterf_SuperFK(Interferencia interf_SuperFK) {
		this.interf_SuperFK = interf_SuperFK;
	}

	public String getSup_Tempo() {
		return sup_Tempo;
	}

	public void setSup_Tempo(String sup_Tempo) {
		this.sup_Tempo = sup_Tempo;
	}

	public int getSup_Codigo() {
		return sup_Codigo;
	}

	public void setSup_Codigo(int sup_Codigo) {
		this.sup_Codigo = sup_Codigo;
	}

	public String getSup_Captacao() {
		return sup_Captacao;
	}

	public void setSup_Captacao(String sup_Captacao) {
		this.sup_Captacao = sup_Captacao;
	}

	public String getSup_Local() {
		return sup_Local;
	}

	public void setSup_Local(String sup_Local) {
		this.sup_Local = sup_Local;
	}

	public String getSup_Bomba() {
		return sup_Bomba;
	}

	public void setSup_Bomba(String sup_Bomba) {
		this.sup_Bomba = sup_Bomba;
	}

	public String getSup_Potencia() {
		return sup_Potencia;
	}

	public void setSup_Potencia(String sup_Potencia) {
		this.sup_Potencia = sup_Potencia;
	}

	public String getSup_Area() {
		return sup_Area;
	}

	public void setSup_Area(String sup_Area) {
		this.sup_Area = sup_Area;
	}

	public String getSup_Data() {
		return sup_Data;
	}

	public void setSup_Data(String sup_Data) {
		this.sup_Data = sup_Data;
	}

	public String getSup_Caesb() {
		return sup_Caesb;
	}

	public void setSup_Caesb(String sup_Caesb) {
		this.sup_Caesb = sup_Caesb;
	}
	
	
	
	
	
}
