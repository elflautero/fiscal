package tabela;

import entity.Vistoria;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class AtoTabela {
	
	private final SimpleIntegerProperty atoCodigo;

	private final SimpleObjectProperty<Vistoria>atoVistoriaFK;
	
	private final SimpleStringProperty atoTipo; // atoTipo
	private final SimpleStringProperty atoIdentificacao; // atoIdentificacao
	private final SimpleStringProperty atoSEI; // atoSEI
	private final SimpleStringProperty atoCaracterizacao;
	private final SimpleStringProperty atoDataFiscalizacao;
	private final SimpleStringProperty atoDataCriacao;
	
	public AtoTabela (
			
			int atoCodigo,
			
			Vistoria atoVistoriaFK,
			
			String atoTipo,
			String atoIdentificacao,
			String atoSEI,
			String atoCaracterizacao,
			String atoDataFiscalizacao,
			String atoDataCriacao
			
			) {
		
		super();
		
		this.atoCodigo = new SimpleIntegerProperty(atoCodigo);
		
		this.atoVistoriaFK = new SimpleObjectProperty<>(atoVistoriaFK);
		
		this.atoTipo = new SimpleStringProperty(atoTipo);
		this.atoIdentificacao = new SimpleStringProperty(atoIdentificacao);
		this.atoSEI = new SimpleStringProperty(atoSEI);
		this.atoCaracterizacao = new SimpleStringProperty(atoCaracterizacao);
		
		this.atoDataFiscalizacao = new SimpleStringProperty(atoDataFiscalizacao);
		this.atoDataCriacao = new SimpleStringProperty(atoDataCriacao);
		
	}
	
	public int getAtoCodigo () {
		return atoCodigo.get();
	}
	
	public Vistoria getAtoVistoriaFK () {
		return atoVistoriaFK.get();
	}
	
	public String getAtoTipo () {
		return atoTipo.get();
	}
	
	public String getAtoIdentificacao () {
		return atoIdentificacao.get();
	}
	
	public String getAtoSEI () {
		return atoSEI.get();
	}
	
	public String getAtoCaracterizacao () {
		return atoCaracterizacao.get();
	}
	
	
	public String getAtoDataFiscalizacao () {
		return atoDataFiscalizacao.get();
	}
	
	public String getAtoDataCriacao () {
		return atoDataCriacao.get();
	}
}
