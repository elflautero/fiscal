package tabela;

import entity.Endereco;
import entity.Subterranea;
import entity.Superficial;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;


public class InterferenciaTabela {
	
	private final SimpleIntegerProperty inter_Codigo;
	private final SimpleStringProperty inter_Tipo;
	private final SimpleStringProperty inter_Bacia;
	private final SimpleStringProperty inter_UH;
	
	private final SimpleStringProperty inter_Corpo_Hidrico;
	private final SimpleDoubleProperty inter_Lat;
	private final SimpleDoubleProperty inter_Lng;
	private final SimpleStringProperty inter_Situacao;
	private final SimpleStringProperty inter_Desc_Endereco;
	
	private SimpleObjectProperty<Endereco> enderecoInterferenciaObjetoFK;
	
	private SimpleObjectProperty<Subterranea> interSub;
	
	private SimpleObjectProperty<Superficial> interSup;
	
	public InterferenciaTabela (
			
			int inter_Codigo, 
			String inter_Tipo,
			String inter_Bacia, 
			String inter_UH, 
			String inter_CorpoHidrico,  
			Double inter_Lat, 
			Double inter_Lng,
			String inter_Situacao, 
			String inter_Desc_Endereco,
			
			Endereco enderecoInterferenciaObjetoFK,
			
			Subterranea interSub,
			
			Superficial interSup
													) {
		
		super();
		
		this.inter_Codigo = new SimpleIntegerProperty(inter_Codigo);
		this.inter_Tipo = new SimpleStringProperty(inter_Tipo);
		this.inter_Bacia = new SimpleStringProperty(inter_Bacia);
		this.inter_UH = new SimpleStringProperty(inter_UH);
		
		this.inter_Corpo_Hidrico = new SimpleStringProperty(inter_CorpoHidrico);
		this.inter_Lat = new SimpleDoubleProperty(inter_Lat);
		this.inter_Lng = new SimpleDoubleProperty(inter_Lng);
		this.inter_Situacao = new SimpleStringProperty(inter_Situacao);
		this.inter_Desc_Endereco = new SimpleStringProperty(inter_Desc_Endereco);
		
		this.enderecoInterferenciaObjetoFK = new SimpleObjectProperty<>(enderecoInterferenciaObjetoFK);
		
		this.interSub = new SimpleObjectProperty<>(interSub);
		
		this.interSup = new SimpleObjectProperty<>(interSup);
		
	}
	
	public int getInter_Codigo () {
		return inter_Codigo.get();
	}
	
	public String getInter_Tipo () {
		return inter_Tipo.get();
	}
	
	public String getInter_Bacia () {
		return inter_Bacia.get();
	}
	
	public String getInter_UH () {
		return inter_UH.get();
	}
	public String getInter_Corpo_Hidrico () {
		return inter_Corpo_Hidrico.get();
	}
	
	public Double getInter_Lat() {
		return inter_Lat.get();
	}
	
	public Double getInter_Lng () {
		return inter_Lng.get();
	}
	
	public String getInter_Situacao () {
		return inter_Situacao.get();
	}
	
	public String getInter_Desc_Endereco () {
		return inter_Desc_Endereco.get();
	}
	
	public Endereco getEnderecoInterferenciaObjetoTabelaFK () {
		return enderecoInterferenciaObjetoFK.get();
	}
	
	public Subterranea getInterSub () { 
		return interSub.get();
	}
	
	public Superficial getInterSup () { 
		return interSup.get();
	}
	
}
