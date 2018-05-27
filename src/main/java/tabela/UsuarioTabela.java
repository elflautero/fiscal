package tabela;

import entity.Endereco;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class UsuarioTabela {
	
	private final SimpleIntegerProperty usCodigo;
	private final SimpleStringProperty usTipo;
	private final SimpleStringProperty usNome;
	private final SimpleStringProperty usCPFCNPJ;
	private final SimpleStringProperty usDescricaoEnd;
	private final SimpleStringProperty usRA;
	private final SimpleStringProperty usCidade;
	private final SimpleStringProperty usEstado;
	private final SimpleStringProperty usCEP;
	private final SimpleStringProperty usTelefone;
	private final SimpleStringProperty usCelular;
	private final SimpleStringProperty usEmail;
	
	private SimpleObjectProperty <Endereco>  enderecoUsObjetoFK;
	
	public UsuarioTabela (
			
		int usCodigo,
		
		String usTipo,
		String usNome,
		String usCPFCNPJ,
		String usDescricaoEnd,
		String usRA,
		String usCidade,
		String usEstado,
		String usCEP,
		String usTelefone,
		String usCelular,
		String usEmail,
		
		Endereco enderecoUsObjetoFK ) {
	
		super();
		
		this.usCodigo = new SimpleIntegerProperty(usCodigo);
		this.usTipo = new SimpleStringProperty(usTipo);
		this.usNome = new SimpleStringProperty(usNome);
		this.usCPFCNPJ= new SimpleStringProperty(usCPFCNPJ);
		this.usDescricaoEnd = new SimpleStringProperty(usDescricaoEnd);
		this.usRA = new SimpleStringProperty(usRA);
		this.usCidade = new SimpleStringProperty(usCidade);
		this.usEstado = new SimpleStringProperty(usEstado);
		this.usCEP = new SimpleStringProperty(usCEP);
		this.usTelefone = new SimpleStringProperty(usTelefone);
		this.usCelular = new SimpleStringProperty(usCelular);
		this.usEmail = new SimpleStringProperty(usEmail);
		
		this.enderecoUsObjetoFK = new SimpleObjectProperty<>(enderecoUsObjetoFK);
		
		
		}
		
		
	public int getUsCodigo () {
		return usCodigo.get();
	}
	
	public String getUsTipo () {
		return usTipo.get();
	}
	
	public String getUsNome () {
		return usNome.get();
	}
	
	public String getUsCPFCNPJ () {
		return usCPFCNPJ.get();
	}
	
	public String getUsDescricaoEnd () {
		return usDescricaoEnd.get();
	}
	
	public String getUsRA () {
		return usRA.get();
	}
	
	public String getUsCidade () {
		return usCidade.get();
	}
	
	public String getUsEstado () {
		return usEstado.get();
	}
	
	public String getUsCEP () {
		return usCEP.get();
	}
	
	public String getUsTelefone () {
		return usTelefone.get();
	}
	
	public String getUsCelular () {
		return usCelular.get();
	}
	public String getUsEmail () {
		return usEmail.get();
	}
	
	//-- objeto foreign  key --//
	public Endereco getEnderecoUsObjetoTabelaFK () {
		return enderecoUsObjetoFK.get();
	}
	
}
