package tabela;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class LegislacaoTabela {
	
	private final SimpleIntegerProperty legislacaoCodigo;
	private final SimpleStringProperty descricaoArtigo;
	private final SimpleStringProperty artigo;
	
	public LegislacaoTabela (
			int legislacaoCodigo,
			String descricaoArtigo,
			String artigo
			) {
		
		super();
		
		// mapear a entidade no hibernate xfg
		
		this.legislacaoCodigo = new SimpleIntegerProperty(legislacaoCodigo);
		this.descricaoArtigo = new SimpleStringProperty(descricaoArtigo);
		this.artigo = new SimpleStringProperty(artigo);
		
	}
	
	public int getLegislacaoCodigo () {
		return legislacaoCodigo.get();
	}
	
	public String getDescricaoArtigo () {
		return descricaoArtigo.get();
	}
	
	public String getArtigo () {
		return artigo.get();
	}

}
