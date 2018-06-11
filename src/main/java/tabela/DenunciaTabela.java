package tabela;


import entity.Endereco;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class DenunciaTabela {
	
	private final SimpleIntegerProperty denunciaID;
	private final SimpleStringProperty denDocumento;
	private final SimpleStringProperty denDocumentoSEI;
	private final SimpleStringProperty denProcesso;
	private final SimpleStringProperty denDescricao;
	private final SimpleStringProperty denDataDistribuicao;
	private final SimpleStringProperty denDataRecebimento;
	
	private SimpleObjectProperty<Endereco> denEnderecoFK;
	
	//-- CONSTRUTOR --// 
	
	public DenunciaTabela (
			
			int denunciaID, 
			
			String denDocumento, 
			String denDocumentoSEI, 
			String denProcesso, 
			String denDescricao, 
			String denDataDistribuicao, 
			String denDataRecebimento, 
			
			Endereco denEnderecoFK) {
		
		super();
		
		this.denunciaID = new SimpleIntegerProperty(denunciaID);
		this.denDocumento = new SimpleStringProperty(denDocumento);
		this.denDocumentoSEI = new SimpleStringProperty(denDocumentoSEI);
		this.denProcesso = new SimpleStringProperty(denProcesso);
		this.denDescricao = new SimpleStringProperty(denDescricao);
		this.denDataDistribuicao = new SimpleStringProperty(denDataDistribuicao);
		this.denDataRecebimento = new SimpleStringProperty(denDataRecebimento);
		
		this.denEnderecoFK = new SimpleObjectProperty<>(denEnderecoFK);
		
	}
	
	public int getDenDenunciaID () {
		return denunciaID.get();  // esse get é para pegar apenas o que queremos do dado, senão ele retornaria uma string longa... 
	}
	public String getDenDocumento () {
		return denDocumento.get();
	}
	public String getDenDecumentoSEI () {
		return denDocumentoSEI.get();
	}
	public String getDenProcessoSEI () {
		return denProcesso.get();
	}
	public String getDenDescricao () {
		return denDescricao.get();
	}
	public String getDenDataDistribuicao () {
		return denDataDistribuicao.get();
	}
	public String getDenDataRecebimento () {
		return denDataRecebimento.get();
	}
	// objeto - FK //
	public Endereco getDenEnderecoFK () {
		return denEnderecoFK.get();
	}
	
}