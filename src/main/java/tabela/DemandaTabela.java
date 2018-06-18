package tabela;


import java.time.LocalDateTime;

import entity.Endereco;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class DemandaTabela {
	
	private final SimpleIntegerProperty demID;
	private final SimpleStringProperty demDocumento;
	private final SimpleStringProperty demDocumentoSEI;
	private final SimpleStringProperty demProcessoSEI;
	private final SimpleStringProperty demDescricao;
	private final SimpleStringProperty demDistribuicao;
	private final SimpleStringProperty demRecebimento;
	
	private final LocalDateTime demAtualizacao;
	
	private SimpleObjectProperty<Endereco> demEnderecoFK;
	
	//-- CONSTRUTOR --// 
	public DemandaTabela (
			
			int demID, 
			
			String demDocumento, 
			String demDocumentoSEI, 
			String demProcessoSEI, 
			String demDescricao, 
			String demDistribuicao, 
			String demRecebimento, 
			
			LocalDateTime demAtualizacao,
			
			Endereco demEnderecoFK) {
		
		super();
		
		this.demID = new SimpleIntegerProperty(demID);
		this.demDocumento = new SimpleStringProperty(demDocumento);
		this.demDocumentoSEI = new SimpleStringProperty(demDocumentoSEI);
		this.demProcessoSEI = new SimpleStringProperty(demProcessoSEI);
		this.demDescricao = new SimpleStringProperty(demDescricao);
		this.demDistribuicao = new SimpleStringProperty(demDistribuicao);
		this.demRecebimento = new SimpleStringProperty(demRecebimento);
			
		this.demAtualizacao = demAtualizacao;
		
		this.demEnderecoFK = new SimpleObjectProperty<>(demEnderecoFK);
		
	}
	

	public int getDemID () {
		return demID.get(); 
	}
	public String getDemDocumento () {
		return demDocumento.get();
	}
	public String getDemDocumentoSEI () {
		return demDocumentoSEI.get();
	}
	public String getDemProcessoSEI () {
		return demProcessoSEI.get();
	}
	public String getDemDescricao () {
		return demDescricao.get();
	}
	public String getDemDistribuicao () {
		return demDistribuicao.get();
	}
	public String getDemRecebimento () {
		return demRecebimento.get();
	}
	
	public LocalDateTime getDemAtualizacao() {
		return demAtualizacao;
	}
	
	// objeto - FK //
	public Endereco getDemEnderecoFK () {
		return demEnderecoFK.get();
	}
	
}