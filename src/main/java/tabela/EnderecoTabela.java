package tabela;

import java.util.List;

import entity.Demanda;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;

public class EnderecoTabela {
	
	private final SimpleIntegerProperty Cod_Endereco;
	
	private final SimpleStringProperty Desc_Endereco;
	private final SimpleStringProperty RA_Endereco;
	private final SimpleStringProperty CEP_Endereco;
	private final SimpleStringProperty Cid_Endereco;
	private final SimpleStringProperty UF_Endereco;
	private final SimpleDoubleProperty Lat_Endereco;
	private final SimpleDoubleProperty Lon_Endereco;
	private final SimpleListProperty<Demanda> denuncias;
	
	// coloquei  o set<Endereco>denuncias para atender o observable value
	
	public EnderecoTabela (int Cod_Endereco, String Desc_Endereco, String RA_Endereco,String CEP_Endereco,
			String Cid_Endereco, String UF_Endereco, Double Lat_Endereco, Double Lon_Endereco, List <Demanda> denuncias ) {
		
		super();
		
		this.Cod_Endereco = new SimpleIntegerProperty(Cod_Endereco);
		this.Desc_Endereco = new SimpleStringProperty(Desc_Endereco);
		this.RA_Endereco = new SimpleStringProperty(RA_Endereco);
		this.CEP_Endereco = new SimpleStringProperty(CEP_Endereco);	
		this.Cid_Endereco = new SimpleStringProperty(Cid_Endereco);
		this.UF_Endereco = new SimpleStringProperty(UF_Endereco);
		this.Lat_Endereco = new SimpleDoubleProperty(Lat_Endereco);
		this.Lon_Endereco = new SimpleDoubleProperty(Lon_Endereco);
		this.denuncias = new SimpleListProperty<Demanda>();
	}
	
	public int getCod_Endereco() {
		return Cod_Endereco.get();
	}

	public String getDesc_Endereco() {
		return Desc_Endereco.get();
	}

	public String getRA_Endereco() {
		return RA_Endereco.get();
	}

	public String getCEP_Endereco() {
		return CEP_Endereco.get();
	}

	public String getCid_Endereco() {
		return Cid_Endereco.get();
	}

	public String getUF_Endereco() {
		return UF_Endereco.get();
	}
	
	public Double getLat_Endereco () {
		return Lat_Endereco.get();
	}
	
	public Double getLon_Endereco () {
		return Lon_Endereco.get();
	}

	public SimpleListProperty<Demanda> getListTabelaEnderecoDenuncias(){
		return denuncias;
	}
	
}
