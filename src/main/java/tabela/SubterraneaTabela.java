package tabela;

import entity.Interferencia;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class SubterraneaTabela {
	
	private final SimpleIntegerProperty sub_Codigo;
	private final SimpleStringProperty sub_Poco;
	private final SimpleStringProperty sub_Caesb;
	private final SimpleStringProperty sub_Sistema;
	private final SimpleStringProperty sub_Estatico;
	private final SimpleStringProperty sub_Dinamico;
	private final SimpleStringProperty sub_Vazao;
	private final SimpleStringProperty sub_Profundidade;
	private final SimpleStringProperty sub_Data;
	
	private SimpleObjectProperty<Interferencia> interferenciaFK;
	
	
	
	public SubterraneaTabela (
			
			int sub_Codigo,
			String sub_Poco,
			String sub_Caesb,
			String sub_Sistema,
			String sub_Estatico,
			String sub_Dinamico,
			String sub_Vazao,
			String sub_Profundidade,
			String sub_Data,
			Interferencia interferenciaFK
			
			) {
		
		super();
		
		this.sub_Codigo = new SimpleIntegerProperty(sub_Codigo);
		this.sub_Poco = new SimpleStringProperty(sub_Poco);
		this.sub_Caesb = new SimpleStringProperty(sub_Caesb);
		this.sub_Sistema = new SimpleStringProperty(sub_Sistema);
		this.sub_Estatico = new SimpleStringProperty(sub_Estatico);
		this.sub_Dinamico = new SimpleStringProperty(sub_Dinamico);
		this.sub_Vazao = new SimpleStringProperty(sub_Vazao);
		this.sub_Profundidade = new SimpleStringProperty(sub_Profundidade);
		this.sub_Data = new SimpleStringProperty(sub_Data);
		
		this.interferenciaFK = new SimpleObjectProperty<>(interferenciaFK);
		
	}

	public int getSub_Codigo() {
		return sub_Codigo.get();
	}

	public String getSub_Poco() {
		return sub_Poco.get();
	}

	public String getSub_Caesb() {
		return sub_Caesb.get();
	}

	public String getSub_Sistema() {
		return sub_Sistema.get();
	}

	public String getSub_Estatico() {
		return sub_Estatico.get();
	}

	public String getSub_Dinamico() {
		return sub_Dinamico.get();
	}

	public String getSub_Vazao() {
		return sub_Vazao.get();
	}

	public String getSub_Profundidade() {
		return sub_Profundidade.get();
	}

	public String getSub_Data() {
		return sub_Data.get();
	}
	
	public Interferencia getInterferenciaFK () {
		return interferenciaFK.get();
	}

}
