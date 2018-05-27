package tabela;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class SuperficialTabela {
	
	private final SimpleIntegerProperty sup_Codigo;
	private final SimpleStringProperty sup_Captacao;
	private final SimpleStringProperty sup_Local;
	private final SimpleStringProperty sup_Bomba;
	private final SimpleStringProperty sup_Potencia;
	private final SimpleStringProperty sup_Area;
	private final SimpleStringProperty sup_Data;
	private final SimpleStringProperty sup_Caesb;
	
	public SuperficialTabela (
			
			int sup_Codigo,
			String sup_Captacao,
			String sup_Local,
			String sup_Bomba,
			String sup_Potencia,
			String sup_Area,
			String sup_Data,
			String sup_Caesb
			
			){
		
		super();
		
		this.sup_Codigo = new SimpleIntegerProperty(sup_Codigo);
		this.sup_Captacao = new SimpleStringProperty(sup_Captacao);
		this.sup_Local = new SimpleStringProperty(sup_Local);
		this.sup_Bomba = new SimpleStringProperty(sup_Bomba);
		this.sup_Potencia = new SimpleStringProperty(sup_Potencia);
		this.sup_Area = new SimpleStringProperty(sup_Area);
		this.sup_Data = new SimpleStringProperty(sup_Data);
		this.sup_Caesb = new SimpleStringProperty(sup_Caesb);
		
		
	}

	//-- getters --//
	public int getSup_Codigo() {
		return sup_Codigo.get();
	}

	public String getSup_Local() {
		return sup_Local.get();
	}

	public String getSup_Captacao() {
		return sup_Captacao.get();
	}

	public String getSup_Bomba() {
		return sup_Bomba.get();
	}

	public String getSup_Potencia() {
		return sup_Potencia.get();
	}

	public String getSup_Area() {
		return sup_Area.get();
	}
	
	public String getSup_Data() {
		return sup_Data.get();
	}

	public String getSup_Caesb() {
		return sup_Caesb.get();
	}
	
	
}
