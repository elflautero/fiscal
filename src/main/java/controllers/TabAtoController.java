package controllers;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;
import java.util.ResourceBundle;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import dao.AtoDao;
import entity.Ato;
import entity.Endereco;
import entity.HibernateUtil;
import entity.Vistoria;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.StringConverter;
import tabela.AtoTabela;

public class TabAtoController implements Initializable{
	
	String strPesquisa = "";
	
	AtoTabela atoTab;
	
	@FXML	Pane tabAto = new Pane ();
	
	@FXML Button btnNovo;
	@FXML Button btnSalvar;
	@FXML Button btnEditar;
	@FXML Button btnExcluir;
	@FXML Button btnCancelar;
	@FXML Button btnPesquisar;
	
	@FXML TextField tfAto;
	@FXML TextField tfAtoSEI;
	
	@FXML DatePicker dpDataFiscalizacao;
	@FXML DatePicker dpDataCriacaoAto;
	
	@FXML Button bntCaracterizacao;
	@FXML Button btnGerarAto;
	
	Ato atoGeral;
	
			// TableView Endereço //
			@FXML private TableView <AtoTabela> tvAto;
			
			@FXML TableColumn<AtoTabela, String> tcTipo;
			@FXML TableColumn<AtoTabela, String> tcNumero;
			@FXML TableColumn<AtoTabela, String> tcSEI;
		
		
	@FXML TextField tfPesquisar;
	
	@FXML
	ChoiceBox<String> cbAtoTipo = new ChoiceBox<String>();
		ObservableList<String> olAtoTipo = FXCollections
			.observableArrayList(
					"Termo de Notificação", 
					"Auto de Infração",
					"Auto de Infração de Multa");
	
	@FXML public Label lblVisAto; // público para receber valor do MainController, método pegarEnd()
	
	//  objeto para passar os valor pelo MainControoler para outro controller //
	public Vistoria visGeral;
	
	HTMLEditor htmlCaracterizar;
  	@FXML Pane paneCaracterizar;
	
	//Locale.setDefault(new Locale("pt", "BR"));
	
		//private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		DateTimeFormatter formatter = new DateTimeFormatterBuilder()
				.parseCaseInsensitive()
				.append(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
				.toFormatter();
	
	//  Controller Principal - MainController  //
	@FXML private MainController main;
	
	// MAIN CONTROLLER //
	public void init(MainController mainController) {
			main = mainController;
	}
	
	
	public void bntCaracterizacaoHab (ActionEvent event) {
		
		String caracterizar = "Consta em nosso cadastro o processo nº XXXXXXXXX/2018 - SRH referente à captação de água "
				+ "subterrânea em empreendimento de vossa senhoria. Reiteramos por meio "
				+ "deste AUTO DE INFRAÇÃO DE ADVERTÊNCIA, que constitui obrigação do outorgado a instalação de equipamento "
				+ "de medição para monitoramento contínuo da vazão captada e o envio mensal da leitura da vazão à ADASA. "
				+ "A utilização de recursos hídricos em desacordo com a outorga constitui infração prevista no inciso III, "
				+ "do artigo 12, da Resolução/ADASA n° 163/2006, conforme abaixo descriminadas.";
		
		htmlCaracterizar.setHtmlText(caracterizar);
	}
	
	
	
	public void btnNovoHab (ActionEvent event) {
		
		cbAtoTipo.setDisable(false);
		tfAto.setDisable(false);
		tfAtoSEI.setDisable(false);
		dpDataFiscalizacao.setDisable(false);
		dpDataCriacaoAto.setDisable(false);
		
		cbAtoTipo.setValue(null);
		tfAto.setText("");
		tfAtoSEI.setText("");
		dpDataFiscalizacao.setValue(null);
		dpDataCriacaoAto.setValue(null);
		
		htmlCaracterizar.setHtmlText("");
		
		btnNovo.setDisable(true);
		btnSalvar.setDisable(false);
		
		btnEditar.setDisable(true);
		btnExcluir.setDisable(true);
		
	}

	public void btnSalvarHab (ActionEvent event) {
		
		
			Ato ato = new Ato();
			
			ato.setAtoTipo(cbAtoTipo.getValue());
			ato.setAtoIdentificacao(tfAto.getText());
			ato.setAtoSEI(tfAtoSEI.getText());
			
			ato.setAtoDataFiscalizacao(formatter.format(dpDataFiscalizacao.getValue())); // DATA						
			ato.setAtoDataCriacao(formatter.format(dpDataCriacaoAto.getValue())); // DATA
			
			ato.setAtoCaracterizacao(htmlCaracterizar.getHtmlText());
			
			ato.setAtoVisCodigoFK(visGeral);
			
			AtoDao atoDao = new  AtoDao();
			
			atoDao.mergeAto(ato);
			
			listarAtos (strPesquisa);
			selecionarAto();
			
			atoGeral = ato;
			
			modularBotoes();
		}
	

	public void btnEditarHab (ActionEvent event) {
		
		if (cbAtoTipo.isDisable()) {
			
			cbAtoTipo.setDisable(false);
			tfAto.setDisable(false);
			tfAtoSEI.setDisable(false);
			
			dpDataFiscalizacao.setDisable(false);
			dpDataCriacaoAto.setDisable(false);
			
			htmlCaracterizar.setDisable(false);
			
			
		} else {
			
			AtoTabela atoTab = tvAto.getSelectionModel().getSelectedItem();
			
			Ato ato = new Ato(atoTab);
			
			ato.setAtoTipo(cbAtoTipo.getValue());
			ato.setAtoIdentificacao(tfAto.getText());
			ato.setAtoSEI(tfAtoSEI.getText());
			
			ato.setAtoDataFiscalizacao(formatter.format(dpDataFiscalizacao.getValue()));
			ato.setAtoDataCriacao(formatter.format(dpDataCriacaoAto.getValue()));

			ato.setAtoCaracterizacao(htmlCaracterizar.getHtmlText());
			
			AtoDao atoDao = new AtoDao();
			
			atoDao.mergeAto(ato);
			
			listarAtos(strPesquisa);
			selecionarAto();
			modularBotoes();
			
			atoGeral = ato;
			
		}
		
	}

	public void btnExcluirHab (ActionEvent event) {
		
		// consegui deletar uma vistoria sem deletar auto de infração, dando problema na fk foreign key, que ficou no ato
	
		AtoTabela atoTab = tvAto.getSelectionModel().getSelectedItem();
		
		AtoDao atoDao = new AtoDao();
		
		atoDao.removerAto(atoTab.getAtoCodigo());
		
		listarAtos(strPesquisa);
		selecionarAto();
		modularBotoes();
		
	}
	
	public void btnCancelarHab (ActionEvent event) {
		
		modularBotoes();
	}

	public void btnPesquisarHab (ActionEvent event) {
		
		strPesquisa = tfPesquisar.getText();
		
		listarAtos(strPesquisa);
		selecionarAto ();
	
	}
	
	public void modularBotoes () {
		
		cbAtoTipo.setDisable(true);
		tfAto.setDisable(true);
		tfAtoSEI.setDisable(true);
		
		dpDataFiscalizacao.setDisable(true);
		dpDataCriacaoAto.setDisable(true);
		
		//htmlCaracterizar.setDisable(true);
		
		btnSalvar.setDisable(true);
		btnEditar.setDisable(true);
		btnExcluir.setDisable(true);
		 
		btnNovo.setDisable(false);
	}
	
	// INITIALIZE //
	public void initialize(URL url, ResourceBundle rb) {
		cbAtoTipo.setItems(olAtoTipo);
		
		dpDataFiscalizacao.setConverter(new StringConverter<LocalDate>() {
			
			@Override
			public String toString(LocalDate t) {
				if (t != null) {
					return formatter.format(t);
				}
				return null;
			}
			
			@Override
			public LocalDate fromString(String string) {
				if (string != null && !string.trim().isEmpty()) {
					return LocalDate.parse(string, formatter);
				}
				return null;
			}

		});
		
		modularBotoes();
		
		// -- inicitalizar a caracterizacao -- //
		Platform.runLater(() ->{
		caracterizarHTML();  
		
		});
		
		
	}
	
	//TabVistoriaController tabVis;
	//@FXML Pane paneTipoAto;
	
	//  metodo para listar interferencias  //
 	public void listarAtos (String strPesquisaAto) {
 	
	 	// --- conexão - listar endereços --- //
		AtoDao atoDao = new AtoDao();
		List<Ato> atoList = atoDao.listAto(strPesquisaAto);
		ObservableList<AtoTabela> olAto = FXCollections.observableArrayList();
		
		
		if (!olAto.isEmpty()) {
			olAto.clear();
		}
		
			for (Ato ato : atoList) {
				
				AtoTabela atoTab = new AtoTabela(
					
				ato.getAtoCodigo(),
				ato.getAtoVisCodigoFK(),  // tipo identificacao sei cara infr atenu agrav datafis datacri
				ato.getAtoTipo(),
				ato.getAtoIdentificacao(),
				ato.getAtoSEI(),
				ato.getAtoCaracterizacao(),
				ato.getAtoDataFiscalizacao(),
				ato.getAtoDataCriacao()
				
				);
				
				
			olAto.add(atoTab);
			
		}
		
		tcTipo.setCellValueFactory(new PropertyValueFactory<AtoTabela, String>("atoTipo")); 
		tcNumero.setCellValueFactory(new PropertyValueFactory<AtoTabela, String>("atoIdentificacao")); 
		tcSEI.setCellValueFactory(new PropertyValueFactory<AtoTabela, String>("atoSEI")); 
		
		tvAto.setItems(olAto);
 	}
		
 	// método selecionar interferência //
  	public void selecionarAto () {
 	
 		tvAto.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
 			
 			public void changed(ObservableValue<?> observable , Object oldValue, Object newValue) {
 			
 			atoTab = (AtoTabela) newValue;
 			
 			if (atoTab == null) {
 				
 				btnNovo.setDisable(true);
 				btnSalvar.setDisable(true);
 				btnEditar.setDisable(false);
 				btnExcluir.setDisable(false);
 				btnCancelar.setDisable(false);
 				
 			} else {

 				// -- preencher os campos -- //
 				cbAtoTipo.setValue(atoTab.getAtoTipo());
 				tfAto.setText(atoTab.getAtoIdentificacao());
 				tfAtoSEI.setText(atoTab.getAtoSEI());
 				
 				dpDataFiscalizacao.setValue(LocalDate.parse(atoTab.getAtoDataFiscalizacao(), formatter));
 				dpDataCriacaoAto.setValue(LocalDate.parse(atoTab.getAtoDataCriacao(), formatter));
 				
 				htmlCaracterizar.setHtmlText(atoTab.getAtoCaracterizacao());
 					
 				//-- mudar a vistoria de acordo com a seleçao --//
				visGeral = atoTab.getAtoVistoriaFK();
				lblVisAto.setText(visGeral.getVisIdentificacao() + " |  SEI: "  + visGeral.getVisSEI());
				
				//-- modular botoes --//
				btnNovo.setDisable(true);
				btnSalvar.setDisable(true);
				btnEditar.setDisable(false);
				btnExcluir.setDisable(false);
				btnCancelar.setDisable(false);
				
				Ato ato = new Ato(atoTab);
				
				atoGeral = ato;
				
 				}
 			}
 		});
  	}
  	
  	public void caracterizarHTML () {
  		
		htmlCaracterizar = new HTMLEditor();
		
		htmlCaracterizar.setPrefSize(800, 200);
			
		htmlCaracterizar.setOnKeyPressed(event -> {
			    if (event.getCode() == KeyCode.SPACE  
			            || event.getCode() == KeyCode.TAB ) {
			        // Consume Event before Bubbling Phase, -> otherwise Scrollpane scrolls
			        event.consume();
			    }
			});
		htmlCaracterizar.setHtmlText("<p><font face='Times New Roman'> </font></p>");
			
			StackPane rootCaracterizacao = new StackPane();
			rootCaracterizacao.getChildren().add(htmlCaracterizar);
			paneCaracterizar.getChildren().add(htmlCaracterizar);
	    
  	}
  	
  	
	
  	String infraIncisos [];
	String atenIncisos [];
	String agraIncisos [];
	String penaIncisos [];
	String infrArray [];
	String agraArray [];
	String atenArray[];
	
	public void btnGerarAtoHab (ActionEvent event) {
		
		String ifracoes = visGeral.getVisInfracoes();
		
		infrArray = ifracoes.split("");
		
		infraIncisos = new String [7];
		
		
		infraIncisos [0] = "<p>I - derivar ou utilizar recursos hídricos para qualquer finalidade, sem a respectiva " + 
				"outorga de direito de uso;</p>";
		
		
		infraIncisos [1] = "<p>II - implantar ou iniciar a implantação de empreendimento que exija derivação ou " + 
				"utilização de recursos hídricos, superficiais ou subterrâneos que implique alterações no regime," + 
				"quantidade ou qualidade dos mesmos, sem a autorização dos órgãos ou entidades competentes;</p>";
		
		infraIncisos [2] = "<p>III - utilizar-se de recursos hídricos ou executar obras ou serviços relacionados com os " + 
				"mesmos em desacordo com as condições estabelecidas na outorga;</p>";
		
		infraIncisos [3] = "<p>IV - perfurar poços para extração de água subterrânea ou operá-los sem a devida " + 
				"autorização; </p>";
		
		infraIncisos [4] = "<p>V - fraudar as medições dos volumes d’água utilizados ou declarar valores diferentes " + 
				"dos medidos; </p>";
		
		infraIncisos [5] = "<p>VI - infringir normas estabelecidas nos regulamentos da legislação vigente e " + 
				"superveniente e nos regulamentos administrativos, inclusive em resoluções, instruções e procedimentos " + 
				"fixados pelos órgãos ou entidades competentes; </p>";
		
		infraIncisos [6] = "<p>VII - obstar ou dificultar a ação fiscalizadora das autoridades competentes, no exercício " + 
				"de suas funções;</p>";
		
		
		
		
		// inicializar o usuario //
				Session session = HibernateUtil.getSessionFactory().openSession();
				Transaction tx = session.beginTransaction();
				Endereco endereco = (Endereco) session.get(Endereco.class, visGeral.getVisEndCodigoFK().getCod_Endereco());
				
				// inicializar as listas //
				Hibernate.initialize(endereco.getListUsuarios());
				//Hibernate.initialize(endereco.getListInterferencias());
				
				if (tx.getStatus().equals(TransactionStatus.ACTIVE)) { 
				    tx.commit();
				}
				
				System.out.println("usuario para imprimir relatorio " + endereco.getListUsuarios().get(0).getUsNome());
				
				session.close();
		
		
		if (cbAtoTipo.getValue().equals("Termo de Notificação")) {
			
			File file = null;
			
			file = new File (TabAtoController.class.getResource("/html/termoNotificacao.html").getFile());
			
			Document docHtml = null;
			
			try {
				docHtml = Jsoup.parse(file, "UTF-8");  // retirei o  .clone()
				
			} catch (IOException e1) {
				System.out.println("Erro na leitura no parse Jsoup!!!");
				e1.printStackTrace();
			}
			
			
			// ato //
			docHtml.select("terNum").prepend(atoGeral.getAtoIdentificacao());
			docHtml.select("terSei").prepend(atoGeral.getAtoSEI());
			
			docHtml.select("terUsNome").prepend(endereco.getListUsuarios().get(0).getUsNome());
			docHtml.select("terUsCPF").prepend(endereco.getListUsuarios().get(0).getUsCPFCNPJ());
			docHtml.select("terUsEnd").prepend(endereco.getListUsuarios().get(0).getUsDescricaoEnd());
			docHtml.select("terUsCep").prepend(endereco.getListUsuarios().get(0).getUsCEP());
			docHtml.select("terUsCid").prepend(endereco.getListUsuarios().get(0).getUsCidade());
			docHtml.select("terUsUF").prepend(endereco.getListUsuarios().get(0).getUsEstado());
			docHtml.select("terUsTel").prepend(endereco.getListUsuarios().get(0).getUsTelefone());
			docHtml.select("terUsCel").prepend(endereco.getListUsuarios().get(0).getUsCelular());
			docHtml.select("terUsEmail").prepend(endereco.getListUsuarios().get(0).getUsEmail());
			
			
			// endereco do empreedimento //      
			docHtml.select("EndEmpDes").prepend(endereco.getDesc_Endereco());
			docHtml.select("EndEmpRA").prepend(endereco.getRA_Endereco());
			docHtml.select("EndEmpCep").prepend(endereco.getCEP_Endereco());
			docHtml.select("EndEmpCid").prepend(endereco.getCid_Endereco());
			docHtml.select("EndEmpUF").prepend(endereco.getUF_Endereco());
			
			docHtml.select("terDataFis").prepend(atoGeral.getAtoDataFiscalizacao());
			
			docHtml.select("terCarac").prepend(atoGeral.getAtoCaracterizacao());
			
			
			//-- infrações --//
			for (int i = 0; i<infrArray.length; i++) {
				
				if (infrArray[i].equals("1") ) {
					docHtml.select("infraDescInc").append(infraIncisos[0]);
					docHtml.select("infraNumInc").append("<p>I</p>");
					docHtml.select("infraValor").append("<p>Infração LEVE: Multa no valor base de R$ 400,00 (quatrocentos reais)</p>");
					docHtml.select("infraAlinea").append("<p>I, Alíneas a e b1</p>");
				}
				if (infrArray[i].equals("2") ) {
					docHtml.select("infraDescInc").append(infraIncisos[1]);
					docHtml.select("infraNumInc").append("<p>II</p>");
					docHtml.select("infraValor").append("<p>Infração LEVE: Multa no valor base de R$ 1.000,00 (um mil reais)</p>");
					docHtml.select("infraAlinea").append("<p>II, Alíneas a e b1</p>");
				}
				if (infrArray[i].equals("3")  ) {
					docHtml.select("infraDescInc").append(infraIncisos[2]);
					docHtml.select("infraNumInc").append("<p>III</p>");
					docHtml.select("infraValor").append("<p>Infração GRAVE: Multa no valor base de R$ 10.001 (dez mil e um reais)</p>");
					docHtml.select("infraAlinea").append("<p>III, Alíneas a e b1</p>");
				}
				if (infrArray[i].equals("4") ) {
					docHtml.select("infraDescInc").append(infraIncisos[3]);
					docHtml.select("infraNumInc").append("<p>IV</p>");
					docHtml.select("infraValor").append("<p>Infração LEVE: Multa no valor base de R$ 1.000,00 (um mil reais)</p>");
					docHtml.select("infraAlinea").append("<p>IV, Alíneas a e b1</p>");
				}
				if (infrArray[i].equals("5")  ) {
					docHtml.select("infraDescInc").append(infraIncisos[4]);
					docHtml.select("infraNumInc").append("<p>V</p>");
					docHtml.select("infraValor").append("<p>Infração GRAVE: Multa no valor base de R$ 10.001 (dez mil e um reais)</p>");
					docHtml.select("infraAlinea").append("<p>V, Alíneas a e b1</p>");
				}
				
				if (infrArray[i].equals("6") ) {
					docHtml.select("infraDescInc").append(infraIncisos[5]);
					docHtml.select("infraNumInc").append("<p>VI</p>");
					docHtml.select("infraValor").append("<p>Infração GRAVE: Multa no valor base de R$ 10.001 (dez mil e um reais)</p>");
					docHtml.select("infraAlinea").append("<p>VI, Alíneas a e b1</p>");
				}
				if (infrArray[i].equals("7")  ) {
					docHtml.select("infraDescInc").append(infraIncisos[6]);
					docHtml.select("infraNumInc").append("<p>VII</p>");
					docHtml.select("infraValor").append("<p>Infração LEVE: Multa no valor base de R$ 600,00 (seiscentos reais)</p>");
					docHtml.select("infraAlinea").append("<p>VII, Alíneas a e b1</p>");
				}
			}
			
			docHtml.select("terRecom").append(visGeral.getVisRecomendacoes());
			
			
			String html = new String ();
			
			html = docHtml.toString();
			
			html = html.replace("\"", "'");
			html = html.replace("\n", "");
			
			html =  "\"" + html + "\"";
			
			//-- webview do relatório --//
			
			WebView browser = new WebView();
			WebEngine webEngine = browser.getEngine();
			webEngine.loadContent(html);
			
			Scene scene = new Scene(browser);
			
			Stage stage = new Stage(StageStyle.UTILITY);
			stage.setWidth(1250);
			stage.setHeight(750);
	        stage.setScene(scene);
	        stage.setMaximized(false);
	        stage.setResizable(false);
	        
	        stage.show();
	        
	        TabNavegadorController.html = html;
			
		}
	      
		if (cbAtoTipo.getValue().equals("Auto de Infração") || cbAtoTipo.getValue().equals("Auto de Infração de Multa") ) {
			
			File file = null;
			
			try {
				file = new File (TabAtoController.class.getResource("/html/autoInfracao.html").toURI());
				
			} catch (URISyntaxException e) {
				System.out.println("erro na leitura do relatório.html" );
				e.printStackTrace();
			}
			
			Document docHtml = null;
			
			try {
				docHtml = Jsoup.parse(file, "UTF-8");  // retirei o  .clone()
				
			} catch (IOException e1) {
				System.out.println("Erro na leitura no parse Jsoup!!!");
				e1.printStackTrace();
			}
			
			
			
			docHtml.select("autoNum").prepend(atoGeral.getAtoIdentificacao());
			docHtml.select("autoSEI").prepend(atoGeral.getAtoSEI());
			
			docHtml.select("autoUs").prepend(endereco.getListUsuarios().get(0).getUsNome());
			docHtml.select("autoUsCPF").prepend(endereco.getListUsuarios().get(0).getUsCPFCNPJ());
			docHtml.select("autoUsEnd").prepend(endereco.getListUsuarios().get(0).getUsDescricaoEnd());
			docHtml.select("autoUsRA").prepend(endereco.getListUsuarios().get(0).getUsRA());
			docHtml.select("autoUsCEP").prepend(endereco.getListUsuarios().get(0).getUsCEP());
			docHtml.select("autoUsCid").prepend(endereco.getListUsuarios().get(0).getUsCidade());
			docHtml.select("autoUsUF").prepend(endereco.getListUsuarios().get(0).getUsEstado());
			docHtml.select("autoUsTel").prepend(endereco.getListUsuarios().get(0).getUsTelefone());
			docHtml.select("autoUsCel").prepend(endereco.getListUsuarios().get(0).getUsCelular());
			docHtml.select("autoUsEmail").prepend(endereco.getListUsuarios().get(0).getUsEmail());
			
			//dataFis termoNot 
			
			docHtml.select("EndEmpDes").prepend(endereco.getDesc_Endereco());
			docHtml.select("EndEmpRA").prepend(endereco.getRA_Endereco());
			docHtml.select("EndEmpCep").prepend(endereco.getCEP_Endereco());
			docHtml.select("EndEmpCid").prepend(endereco.getCid_Endereco());
			docHtml.select("EndEmpUF").prepend(endereco.getUF_Endereco());
			
			// caracterização //
			docHtml.select("autoCarac").prepend(atoGeral.getAtoCaracterizacao());
			
			
			//-- penalidades --//
			for (int i = 0; i<infrArray.length; i++) {
				
				if (infrArray[i].equals("1") ) {
					docHtml.select("infraDescInc").append(infraIncisos[0]);
					docHtml.select("penaDescInci").append("<p>Infração LEVE: Multa no valor base de R$ 400,00 (quatrocentos reais) a  R$ 10.000,00 (dez mil reais).</p><p>Art. 14, Inciso I</p>");
				}
				if (infrArray[i].equals("2") ) {
					docHtml.select("infraDescInc").append(infraIncisos[1]);
					docHtml.select("penaDescInci").append("<p>Infração LEVE: Multa no valor base de R$ R$ 1.000,00 (um mil reais) a   R$ 10.000,00 (dez mil reais).</p><p>Art. 14, Inciso II</p>");
				}
				if (infrArray[i].equals("3")  ) {
					docHtml.select("infraDescInc").append(infraIncisos[2]);
					docHtml.select("penaDescInci").append("<p>Infração GRAVE: Multa no valor base de R$ 10.001 (dez mil e um reais) a R$ 30.000,00 (trinta mil reais).</p><p>Art. 14, Inciso III</p>");
				}
				if (infrArray[i].equals("4") ) {
					docHtml.select("infraDescInc").append(infraIncisos[3]);
					docHtml.select("penaDescInci").append("<p>Infração LEVE: Multa no valor base de R$ 1.000,00 (um mil reais)R$  a 10.000,00 (dez mil reais).</p><p>Art. 14, Inciso IV</p>");
				}
				if (infrArray[i].equals("5")  ) {
					docHtml.select("infraDescInc").append(infraIncisos[4]);
					docHtml.select("penaDescInci").append("<p>Infração GRAVE: Multa no valor base de 10.001 (dez mil e um reais) a 25.000,00 (vinte e cinco mil reais).</p><p>Art. 14, Inciso V</p>");
				}
				
				if (infrArray[i].equals("6") ) {
					docHtml.select("infraDescInc").append(infraIncisos[5]);
					docHtml.select("penaDescInci").append("<p>Infração GRAVE: Multa no valor base de R$ 10.001 (dez mil e um reais) a 30.000,00 (trinta mil reais).</p><p>Art. 14, Inciso VI</p>");
				}
				if (infrArray[i].equals("7")  ) {
					docHtml.select("infraDescInc").append(infraIncisos[6]);
					docHtml.select("penaDescInci").append("<p>Infração LEVE: Multa no valor base de R$ 600,00 (seiscentos reais) a R$ 10.000,00 (dez mil reais).</p><p>Art. 14, Inciso VII</p>");
				}
			}
			
			String atenuantes = visGeral.getVisAtenuantes();
			
			atenArray = atenuantes.split("");
			
			atenIncisos = new String [12];
			
			//-- atenuantes --//
			atenIncisos = new String [9];
			
			
			atenIncisos [0] = "<p>I - baixo grau de instrução ou escolaridade do usuário dos recursos hídricos;</p>";
			
			atenIncisos [1] = "<p>II - arrependimento do usuário, manifestado pela espontânea reparação do dano ou pela " + 
					"mitigação significativa da degradação causada aos recursos hídricos;</p>";
			
			atenIncisos [2] = "<p>III - comunicação prévia, pelo usuário, de perigo iminente de degradação dos recursos " + 
					"hídricos; </p>";
			
			atenIncisos [3] = "<p>IV - oficialização do comprometimento do usuário em sanar as irregularidades e reparar " + 
					"os danos delas decorrentes;</p>";
			
			atenIncisos [4] = "<p>V - colaboração explícita com a fiscalização;</p>";
			
			atenIncisos [5] = "<p>VI - tratando-se de usuário não outorgado, haver espontaneamente procurado a Agência " + 
					"para regularização do uso dos recursos hídricos; </p>";
			
			atenIncisos [6] = "<p>VII - atendimento a todas as recomendações e exigências, nos prazos fixados pela " + 
					"Agência; </p>";
			
			atenIncisos [7] = "<p>VIII - reconstituição dos recursos hídricos degradados ou sua recomposição na forma " + 
					"exigida; </p>";
			
			atenIncisos [8] = "<p>IX - não ter sido autuado por infração nos últimos 5 (cinco) anos anteriores ao fato.</p>";
			
			
			
			//;<aten></p> agra
			
			//-- atenuantes --//
			for (int i = 0; i<atenArray.length; i++) {
				
				if (atenArray[i].equals("1") ) {
					docHtml.select("aten").append(atenIncisos[0]);
				}
				if (atenArray[i].equals("2") ) {
					docHtml.select("aten").append(atenIncisos[1]);
				}
				if (atenArray[i].equals("3")  ) {
					docHtml.select("aten").append(atenIncisos[2]);
				}
				if (atenArray[i].equals("4") ) {
					docHtml.select("aten").append(atenIncisos[3]);
				}
				if (atenArray[i].equals("5")  ) {
					docHtml.select("aten").append(atenIncisos[4]);
				}
				if (atenArray[i].equals("6") ) {
					docHtml.select("aten").append(atenIncisos[5]);
				}
				if (atenArray[i].equals("7")  ) {
					docHtml.select("aten").append(atenIncisos[6]);
				}
				if (atenArray[i].equals("8")  ) {
					docHtml.select("aten").append(atenIncisos[7]);
				}
				if (atenArray[i].equals("9")  ) {
					docHtml.select("aten").append(atenIncisos[8]);
				}
				
			}

			
			String agravantes = visGeral.getVisAgravantes();
			
			agraArray = agravantes.split("");
			
			agraIncisos = new String [12];
			
			//-- agravantes --//
			agraIncisos = new String [12];
			
			agraIncisos [0] = "<p>a) para obter vantagem pecuniária;</p>";
			
			agraIncisos [1] = "<p>b) mediante coação de outrem para a sua execução material;</p>";
			
			agraIncisos [2] = "<p>c) com implicações graves à saúde pública ou ao meio ambiente, em especial aos " + 
					"recursos hídricos;</p>";
			
			agraIncisos [3] = "<p>d) que atinja áreas de unidades de conservação ou áreas sujeitas, por ato do Poder " + 
					"Público, a regime especial de uso;</p>";
			
			agraIncisos [4] = "<p>e) que atinja áreas urbanas ou quaisquer assentamentos humanos;</p>";
			
			agraIncisos [5] = "<p>f) em época de racionamento do uso de água ou em condições sazonais adversas ao seu " + 
					"uso;</p>";
			
			agraIncisos [6] = "<p>g) mediante fraude ou abuso de confiança;</p>";
			
			agraIncisos [7] = "<p>h) mediante abuso do direito de uso do recurso hídrico;</p>";
			
			agraIncisos [8] = "<p>i) em favor do interesse de pessoa jurídica mantida total ou parcialmente por recursos " + 
					"públicos ou beneficiada por incentivos fiscais;</p>";
			
			agraIncisos [9] = "<p>j) sem proceder à reparação integral dos danos causados;</p>";
			
			agraIncisos [10] = "<p>k) que tenha sido facilitada por funcionário público no exercício de suas funções;</p>";
			
			agraIncisos [11] = "<p>l) mediante fraude documental;</p>";
			
			//tem que colocar um  if
			for (int i = 0; i<agraArray.length; i++) {
				if (agraArray[i].equals("a") ) {
					docHtml.select("agra").append(agraIncisos[0]);
				}
				if (agraArray[i].equals("b") ) {
					docHtml.select("agra").append(agraIncisos[1]);
				}
				if (agraArray[i].equals("c")  ) {
					docHtml.select("agra").append(agraIncisos[2]);
				}
				if (agraArray[i].equals("d") ) {
					docHtml.select("agra").append(agraIncisos[3]);
				}
				if (agraArray[i].equals("e")  ) {
					docHtml.select("agra").append(agraIncisos[4]);
				}
				if (agraArray[i].equals("f") ) {
					docHtml.select("agra").append(agraIncisos[5]);
				}
				if (agraArray[i].equals("g")  ) {
					docHtml.select("agra").append(agraIncisos[6]);
				}
				if (agraArray[i].equals("h")  ) {
					docHtml.select("agra").append(agraIncisos[7]);
				}
				if (agraArray[i].equals("i")  ) {
					docHtml.select("agra").append(agraIncisos[8]);
				}
				if (agraArray[i].equals("j")  ) {
					docHtml.select("agra").append(agraIncisos[9]);
				}
				if (agraArray[i].equals("k")  ) {
					docHtml.select("agra").append(agraIncisos[10]);
				}
				if (agraArray[i].equals("l")  ) {
					docHtml.select("agra").append(agraIncisos[11]);
				}
			}
			
			
			String html = new String ();
			
			html = docHtml.toString();
			
			html = html.replace("\"", "'");
			html = html.replace("\n", "");
			
			html =  "\"" + html + "\"";
			
			//-- webview do relatório --//
			
			WebView browser = new WebView();
			WebEngine webEngine = browser.getEngine();
			webEngine.loadContent(html);
			
			Scene scene = new Scene(browser);
			
			Stage stage = new Stage(StageStyle.UTILITY);
			stage.setWidth(1250);
			stage.setHeight(750);
	        stage.setScene(scene);
	        stage.setMaximized(false);
	        stage.setResizable(false);
	        
	        stage.show();
	        
	        TabNavegadorController.html = html;
			
			
			
		}
		
	}
}


/*
 
 
 //-- Selecionar a tabela de acordo com o tipo de captação --//
		cbAtoTipo.getSelectionModel().selectedItemProperty().addListener( 
						(ObservableValue<? extends String> observable, String oldString, String newString) -> 
						
						{
							try {
								abrirTabs(newString);
							} catch (IOException e) {
								System.out.println("erro na chamada do método abrir tab: " + e);
								e.printStackTrace();
							}
						} 
						
						);
						
 */

/*
 
 // método abrir tab Vistoria, Termo etc //
	public void abrirTabs (String newString) throws IOException {
		
		if (newString.equals("Termo de Notificação")) {
			
			paneTipoAto.getChildren().clear();
			
			Pane paneVistoria = new Pane();
			
			tabVis = new TabVistoriaController();
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TabVistoria.fxml"));
			loader.setRoot(paneTipoAto);
			loader.setController(tabVis);
			loader.load();
			
			paneTipoAto.getChildren().add(paneVistoria);
			//tipoAto = 1;
			
		}
		
		if (newString.equals( "Termo de Notificação") 					|| 
				newString.equals("Auto de Infração de Advertência")	|| 
					newString.equals("Auto de Infração de Multa")	
						)	
		{
			paneTipoAto.getChildren().clear();
		}
		
		if (newString.equals(null)) {
			paneTipoAto.getChildren().clear();
		}
		
	}
	
	*/
