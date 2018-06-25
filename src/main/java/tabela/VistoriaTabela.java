package tabela;

import java.util.List;

import entity.Ato;
import entity.Endereco;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class VistoriaTabela {
	
	private final SimpleIntegerProperty visCodigo;

	private final SimpleObjectProperty<Endereco> visEndCodigoFK;
	
	private final SimpleStringProperty visObjeto;
	private final SimpleStringProperty visApresentacao;
	private final SimpleStringProperty visRelato;
	private final SimpleStringProperty visRecomendacoes;
	
	private final SimpleStringProperty visInfracoes;
	private final SimpleStringProperty visPenalidades;
	private final SimpleStringProperty visAtenuantes;
	private final SimpleStringProperty visAgravantes;
	
	private final SimpleStringProperty visIdentificacao;
	private final SimpleStringProperty visSEI;
	private final SimpleStringProperty visDataFiscalizacao;
	private final SimpleStringProperty visDataCriacao;
	
	private final SimpleListProperty<Ato> visListAtos; // listar os atos
	

	public VistoriaTabela (
			
			int visCodigo,
			Endereco visEndCodigoFK,
			String visObjeto,
			String visApresentacao,
			String visRelato,
			String visRecomendacoes,
			
			String visInfracoes,
			String visPenalidades,
			String visAtenuantes,
			String visAgravantes,
			
			String visIdentificacao,
			String visSEI,
			String visDataFiscalizacao,
			String visDataCriacao,
			
			List <Ato> visListAtos
			
			) {
		
			super();
			
			this.visCodigo = new SimpleIntegerProperty(visCodigo);
			
			this.visEndCodigoFK = new SimpleObjectProperty <> (visEndCodigoFK);
			
			this.visObjeto = new SimpleStringProperty(visObjeto);
			this.visApresentacao = new SimpleStringProperty(visApresentacao);
			this.visRelato = new SimpleStringProperty(visRelato);
			this.visRecomendacoes = new SimpleStringProperty(visRecomendacoes);
			this.visInfracoes = new SimpleStringProperty(visInfracoes);
			this.visPenalidades = new SimpleStringProperty(visPenalidades);
			this.visAtenuantes = new SimpleStringProperty(visAtenuantes);
			this.visAgravantes = new  SimpleStringProperty(visAgravantes);
			this.visIdentificacao = new SimpleStringProperty(visIdentificacao);
			this.visSEI = new SimpleStringProperty(visSEI);
			this.visDataCriacao = new SimpleStringProperty(visDataCriacao);
			this.visDataFiscalizacao = new SimpleStringProperty (visDataFiscalizacao);
			
			this.visListAtos = new SimpleListProperty<Ato>();
	}
	
	public int getVisCodigo () {
		return visCodigo.get();
	}
	
		//-- objecto endereço --//
		public Endereco getVisEndCodigoFK () { 
			return visEndCodigoFK.get();
		}
		
	public String getVisObjeto () {
		return visObjeto.get();
	}
	
	public String getVisApresentacao () {
		return visApresentacao.get();
	}

	public String getVisRelato () {
		return visRelato.get();
	}
	
	public String getVisRecomendacoes () {
		return visRecomendacoes.get();
	}
	
	public String getVisInfracoes () {
		return visInfracoes.get();
	}
	public String getVisPenalidades () {
		return visPenalidades.get();
	}
	public String getVisAtenuantes () {
		return visAtenuantes.get();
	}
	public String getVisAgravantes () {
		return visAgravantes.get();
	}
	
	public String getVisDataFiscalizacao () {
		return visDataFiscalizacao.get();
	}
	
	public String getVisDataCriacao () {
		return visDataCriacao.get();
	}
	
	public String getVisIdentificacao () {
		return visIdentificacao.get();
	}
	
	public String getVisSEI() {
		return visSEI.get();
	}
	
	public SimpleListProperty<Ato> getListAtos(){
		return visListAtos;
	}
}
