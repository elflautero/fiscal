package controllers;

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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
	
	@FXML Image imgAto = new Image(TabAtoController.class.getResourceAsStream("/images/ato32.png"));
	
	
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
  	
  	int u = 0;
  	@FXML
	ChoiceBox<String> cbUsuario = new ChoiceBox<String>();
		ObservableList<String> olUsuario = FXCollections
			.observableArrayList("0" , "1", "2", "3", "4");
		
	
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
		
		htmlCaracterizar.setHtmlText("<p><font face='Times New Roman'> </font></p>");
		abrirEditorHTML();
		
		btnNovo.setDisable(true);
		btnSalvar.setDisable(false);
		
		btnEditar.setDisable(true);
		btnExcluir.setDisable(true);
		
	}

	public void btnSalvarHab (ActionEvent event) {
		
		obsList = FXCollections.observableArrayList();
		
		if (visGeral == null) {
			
			Alert a = new Alert (Alert.AlertType.ERROR);
			a.setTitle("Alerta!!!");
			a.setContentText("Vistoria não selecionada!!!");
			a.setHeaderText(null);
			a.show();
			
		} else {
			
				if (cbAtoTipo.getValue() == null  ||
						tfAto.getText().isEmpty() ) 
				{
					
					Alert a = new Alert (Alert.AlertType.ERROR);
					a.setTitle("Alerta!!!");
					a.setContentText("Informe: Tipo de Ato, Número do Ato!!!");
					a.setHeaderText(null);
					a.show();
					
				} else {
				
				Ato ato = new Ato();
				
				ato.setAtoTipo(cbAtoTipo.getValue());
				ato.setAtoIdentificacao(tfAto.getText());
				ato.setAtoSEI(tfAtoSEI.getText());
				
				if (dpDataFiscalizacao.getValue() == null) {
					ato.setAtoDataFiscalizacao(null);}
					else {
						ato.setAtoDataFiscalizacao(dpDataFiscalizacao.getEditor().getText()); // DATA
					}
				
				if (dpDataCriacaoAto.getValue() == null) {
					ato.setAtoDataCriacao(null);}
					else {
						ato.setAtoDataCriacao(dpDataCriacaoAto.getEditor().getText()); // DATA
					}
				
				ato.setAtoCaracterizacao(htmlCaracterizar.getHtmlText());
				
				ato.setAtoVisCodigoFK(visGeral);
				
				AtoDao atoDao = new  AtoDao();
				
				atoDao.mergeAto(ato);
				
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
						
				obsList.add(atoTab);
				tvAto.setItems(obsList);
					
				selecionarAto();
				
				atoGeral = ato;
				
				modularBotoes();
				fecharEditorHTML();
				
				Alert a = new Alert (Alert.AlertType.INFORMATION);
				a.setTitle("Parabéns!!!");
				a.setContentText("Cadastro salvo com sucesso!!!");
				a.setHeaderText(null);
				a.show();
		}
		}
	}
	
	public void btnEditarHab (ActionEvent event) {
		
		if (cbAtoTipo.isDisable()) {
			
			cbAtoTipo.setDisable(false);
			tfAto.setDisable(false);
			tfAtoSEI.setDisable(false);
			
			dpDataFiscalizacao.setDisable(false);
			dpDataCriacaoAto.setDisable(false);
			
			abrirEditorHTML();
			
			
		} else {
			
			if (cbAtoTipo.getValue() == null  ||
					tfAto.getText().isEmpty() ) 
			{
				
				Alert a = new Alert (Alert.AlertType.ERROR);
				a.setTitle("Alerta!!!");
				a.setContentText("Informe: Tipo de Ato, Número do Ato!!!");
				a.setHeaderText(null);
				a.show();
				
			} else {
			
			AtoTabela atoTab = tvAto.getSelectionModel().getSelectedItem();
			
			Ato ato = new Ato(atoTab);
			
			ato.setAtoTipo(cbAtoTipo.getValue());
			ato.setAtoIdentificacao(tfAto.getText());
			ato.setAtoSEI(tfAtoSEI.getText());
			
			if (dpDataFiscalizacao.getValue() == null) {
				ato.setAtoDataFiscalizacao(null);}
				else {
					ato.setAtoDataFiscalizacao(dpDataFiscalizacao.getEditor().getText()); // DATA
				}
			
			if (dpDataCriacaoAto.getValue() == null) {
				ato.setAtoDataCriacao(null);}
				else {
					ato.setAtoDataCriacao(dpDataCriacaoAto.getEditor().getText()); // DATA
				}

			ato.setAtoCaracterizacao(htmlCaracterizar.getHtmlText());
			
			AtoDao atoDao = new AtoDao();
			
			atoDao.mergeAto(ato);
			
			obsList.remove(atoTab);
			
			atoTab = new AtoTabela(
					
					ato.getAtoCodigo(),
					ato.getAtoVisCodigoFK(),
					ato.getAtoTipo(),
					ato.getAtoIdentificacao(),
					ato.getAtoSEI(),
					ato.getAtoCaracterizacao(),
					ato.getAtoDataFiscalizacao(),
					ato.getAtoDataCriacao()
					
					);
					
			obsList.add(atoTab);
			tvAto.setItems(obsList);
			
			selecionarAto();
			
			modularBotoes();
			fecharEditorHTML();
			
			atoGeral = ato;
			
			Alert a = new Alert (Alert.AlertType.INFORMATION);
			a.setTitle("Parabéns!!!");
			a.setContentText("Cadastro editado com sucesso!!!");
			a.setHeaderText(null);
			a.show();
			
			}
			
		}
		
	}

	public void btnExcluirHab (ActionEvent event) {
		
		// consegui deletar uma vistoria sem deletar auto de infração, dando problema na fk foreign key, que ficou no ato
	
		AtoTabela atoTab = tvAto.getSelectionModel().getSelectedItem();
		
		AtoDao atoDao = new AtoDao();
		
		atoDao.removerAto(atoTab.getAtoCodigo());
		
		obsList.remove(atoTab);
		tvAto.setItems(obsList);
		
		selecionarAto();
		
		modularBotoes();
		fecharEditorHTML();
		
	}
	
	public void btnCancelarHab (ActionEvent event) {
		
		modularBotoes();
		fecharEditorHTML();
	}

	public void btnPesquisarHab (ActionEvent event) {
		
		strPesquisa = tfPesquisar.getText();
		
		listarAtos(strPesquisa);
		selecionarAto ();
		
		modularBotoes();
		fecharEditorHTML();
	
	}
	
	public void modularBotoes () {
		
		cbAtoTipo.setDisable(true);
		tfAto.setDisable(true);
		tfAtoSEI.setDisable(true);
		
		dpDataFiscalizacao.setDisable(true);
		dpDataCriacaoAto.setDisable(true);
		
		btnSalvar.setDisable(true);
		btnEditar.setDisable(true);
		btnExcluir.setDisable(true);
		 
		btnNovo.setDisable(false);
	}
	
	WebView webTermo;
	WebEngine engTermo;
	
	WebView webAuto;
	WebEngine engAuto;
	
	// INITIALIZE //
	public void initialize(URL url, ResourceBundle rb) {
		
		tcTipo.setCellValueFactory(new PropertyValueFactory<AtoTabela, String>("atoTipo")); 
		tcNumero.setCellValueFactory(new PropertyValueFactory<AtoTabela, String>("atoIdentificacao")); 
		tcSEI.setCellValueFactory(new PropertyValueFactory<AtoTabela, String>("atoSEI")); 
		
		tfPesquisar.setOnKeyReleased(event -> {
	  		  if (event.getCode() == KeyCode.ENTER){
	  		     btnPesquisar.fire();
	  		  }
	  		});
		
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
		
		
		
		
		// -- inicitalizar o mapa -- //
				Platform.runLater(() ->{
					
				webTermo = new WebView();
				engTermo = webTermo.getEngine();
				engTermo.load(getClass().getResource("/html/termoNotificacao.html").toExternalForm()); 
				

				webAuto = new WebView();
				engAuto = webAuto.getEngine();
				engAuto.load(getClass().getResource("/html/autoInfracao.html").toExternalForm()); 
				
				});
				
				
		
		modularBotoes();
		
		// -- inicitalizar a caracterizacao -- //
		Platform.runLater(() ->{
		
			caracterizarHTML();
			fecharEditorHTML();
		
		});
		
		btnGerarAto.setGraphic(new ImageView(imgAto));
		
		cbUsuario.setValue("0");
		cbUsuario.setItems(olUsuario);
		
	}
	
	ObservableList<AtoTabela> obsList;
	
	//  metodo para listar interferencias  //
 	public void listarAtos (String strPesquisaAto) {
 	
	 	// --- conexão - listar endereços --- //
		AtoDao atoDao = new AtoDao();
		List<Ato> atoList = atoDao.listAto(strPesquisaAto);
		obsList = FXCollections.observableArrayList();
		
		
		if (!obsList.isEmpty()) {
			obsList.clear();
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
				
			obsList.add(atoTab);
			
		}
		
		tvAto.setItems(obsList);
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
 				
 				
 				if (atoTab.getAtoDataFiscalizacao() == null) {
 					dpDataFiscalizacao.getEditor().clear();
	 				} else {
	 					dpDataFiscalizacao.getEditor().setText(atoTab.getAtoDataFiscalizacao());
	 				}
 				
 				if (atoTab.getAtoDataCriacao() == null) {
 					dpDataCriacaoAto.getEditor().clear();
	 				} else {
	 					dpDataCriacaoAto.getEditor().setText(atoTab.getAtoDataCriacao());
	 				}
 				
 				
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
	
	String htmlTermo = "";
	String htmlAuto = "";
	
	public void btnGerarAtoHab (ActionEvent event) {
		
		u = Integer.parseInt(cbUsuario.getValue());
		
		String infracoes = visGeral.getVisInfracoes();
		
		
		// INFRAÇÕES //
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
		
		if (tx.getStatus().equals(TransactionStatus.ACTIVE)) { 
		    tx.commit();
		}
		
		session.close();
		
		
		if (cbAtoTipo.getValue().equals("Termo de Notificação")) {
			
			
			
			htmlTermo = (String) engTermo.executeScript("document.documentElement.outerHTML"); 
	      
			Document docHtml = null;
			
			docHtml = Jsoup.parse(htmlTermo, "UTF-8");  // retirei o  .clone()
			
			// termo de notificação //
			docHtml.select("terNum").prepend(atoGeral.getAtoIdentificacao());
			docHtml.select("terSei").prepend(atoGeral.getAtoSEI());
			
			try {docHtml.select("terUsNome").prepend(endereco.getListUsuarios().get(u).getUsNome());}
			
				catch (Exception e) {
					Alert a = new Alert (Alert.AlertType.ERROR);
					a.setTitle("Alerta!!!");
					a.setContentText("Não há usuário cadastrado!!!");
					a.setHeaderText(null);
					a.show();
					
				};
				
			docHtml.select("terUsCPF").prepend(endereco.getListUsuarios().get(u).getUsCPFCNPJ());
			docHtml.select("terUsEnd").prepend(endereco.getListUsuarios().get(u).getUsDescricaoEnd());
			try {docHtml.select("terUsRA").prepend(endereco.getListUsuarios().get(u).getUsRA());} catch (Exception e) {docHtml.select("EndEmpRA").prepend("");};
			docHtml.select("terUsCep").prepend(endereco.getListUsuarios().get(u).getUsCEP());
			docHtml.select("terUsCid").prepend(endereco.getListUsuarios().get(u).getUsCidade());
			try {docHtml.select("terUsUF").prepend(endereco.getListUsuarios().get(u).getUsEstado());} catch (Exception e) {docHtml.select("terUsUF").prepend("");};
			docHtml.select("terUsTel").prepend(endereco.getListUsuarios().get(u).getUsTelefone());
			docHtml.select("terUsCel").prepend(endereco.getListUsuarios().get(u).getUsCelular());
			docHtml.select("terUsEmail").prepend(endereco.getListUsuarios().get(u).getUsEmail());
			
			
			// endereco do empreedimento //      
			docHtml.select("EndEmpDes").prepend(endereco.getDesc_Endereco());
			try {docHtml.select("EndEmpRA").prepend(endereco.getRA_Endereco());} catch (Exception e) {docHtml.select("EndEmpRA").prepend("");};
			docHtml.select("EndEmpCep").prepend(endereco.getCEP_Endereco());
			docHtml.select("EndEmpCid").prepend(endereco.getCid_Endereco());
			try{docHtml.select("EndEmpUF").prepend(endereco.getUF_Endereco());} catch (Exception e) {docHtml.select("EndEmpUF").prepend("");};
			
			try {docHtml.select("terDataFis").prepend(atoGeral.getAtoDataFiscalizacao());} catch (Exception e) {docHtml.select("terDataFis").prepend("");};
			
			docHtml.select("terCarac").prepend(atoGeral.getAtoCaracterizacao());
			
			if (infracoes != null) {
				
				infrArray = infracoes.split("");
			
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
			stage.setWidth(1150);
			stage.setHeight(750);
	        stage.setScene(scene);
	        stage.setMaximized(false);
	        stage.setResizable(false);
	        
	        stage.show();
	        
	        TabNavegadorController.html = html;
			
		}
	      
		if (cbAtoTipo.getValue().equals("Auto de Infração") || cbAtoTipo.getValue().equals("Auto de Infração de Multa") ) {
			
			
			htmlAuto = (String) engAuto.executeScript("document.documentElement.outerHTML"); 
			
			Document docHtml = null;
			
			docHtml = Jsoup.parse(htmlAuto, "UTF-8");  // retirei o  .clone()
				
			// auto de infração //
			docHtml.select("autoNum").prepend(atoGeral.getAtoIdentificacao());
			docHtml.select("autoSEI").prepend(atoGeral.getAtoSEI());
			
			try {docHtml.select("autoUs").prepend(endereco.getListUsuarios().get(u).getUsNome());}
				catch (Exception e) {
					Alert a = new Alert (Alert.AlertType.ERROR);
					a.setTitle("Alerta!!!");
					a.setContentText("Não há usuário cadastrado!!!");
					a.setHeaderText(null);
					a.show();
					
				};
			
			docHtml.select("autoUsCPF").prepend(endereco.getListUsuarios().get(u).getUsCPFCNPJ());
			docHtml.select("autoUsEnd").prepend(endereco.getListUsuarios().get(u).getUsDescricaoEnd());
			                             
			try {docHtml.select("autoUsRA").prepend(endereco.getListUsuarios().get(u).getUsRA());} catch (Exception e){docHtml.select("autoUsRA").prepend("");};
			docHtml.select("autoUsCEP").prepend(endereco.getListUsuarios().get(u).getUsCEP());
			docHtml.select("autoUsCid").prepend(endereco.getListUsuarios().get(u).getUsCidade());
			try {docHtml.select("autoUsUF").prepend(endereco.getListUsuarios().get(u).getUsEstado());} catch (Exception e) {docHtml.select("autoUsUF").prepend("");};
			docHtml.select("autoUsTel").prepend(endereco.getListUsuarios().get(u).getUsTelefone());
			docHtml.select("autoUsCel").prepend(endereco.getListUsuarios().get(u).getUsCelular());
			docHtml.select("autoUsEmail").prepend(endereco.getListUsuarios().get(u).getUsEmail());
			
			//dataFis termoNot 
			
			docHtml.select("EndEmpDes").prepend(endereco.getDesc_Endereco());
			try {docHtml.select("EndEmpRA").prepend(endereco.getRA_Endereco());} catch (Exception e) {docHtml.select("EndEmpRA").prepend("");};
			docHtml.select("EndEmpCep").prepend(endereco.getCEP_Endereco());
			docHtml.select("EndEmpCid").prepend(endereco.getCid_Endereco());
			try {docHtml.select("EndEmpUF").prepend(endereco.getUF_Endereco());} catch (Exception e) {docHtml.select("EndEmpUF").prepend("");};
			
			// caracterização //
			docHtml.select("autoCarac").prepend(atoGeral.getAtoCaracterizacao());
			
			if (infracoes != null) {
				
					infrArray = infracoes.split("");
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
			}
			
			String atenuantes = visGeral.getVisAtenuantes();
			
			System.out.println("atenuantes " + atenuantes);
			
			if (atenuantes != null) {
				
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
			}
			
			
			String agravantes = visGeral.getVisAgravantes();
			System.out.println("string agravantes " + agravantes);
			
			if (agravantes != null) {
				
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
			
			}
			
			docHtml.select("autoReco").append(visGeral.getVisRecomendacoes());
			
			if (cbAtoTipo.getValue().equals("Auto de Infração")) {
				
				docHtml.select("autoAdMul").prepend("ADVERT&Ecirc;NCIA");
			}
			if (cbAtoTipo.getValue().equals("Auto de Infração de Multa")) {
				docHtml.select("autoAdMul").prepend("MULTA");
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
			stage.setWidth(1150);
			stage.setHeight(750);
	        stage.setScene(scene);
	        stage.setMaximized(false);
	        stage.setResizable(false);
	        
	        stage.show();
	        
	        TabNavegadorController.html = html;
				
		}
		
	}
	
	public void fecharEditorHTML (){
		htmlCaracterizar.setDisable(true);
	}
	public void abrirEditorHTML (){
		htmlCaracterizar.setDisable(false);
	}
}


/*
//-- se não ler, tenta por aqui --//
eng.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>(){ 

        public void changed(final ObservableValue<? extends Worker.State> observableValue, 

                            final Worker.State oldState, 
                            final Worker.State newState) 
        
        { 
        	if (newState == Worker.State.SUCCEEDED){  
        		htmlAuto = (String) eng.executeScript("document.documentElement.outerHTML"); 
        		
        	} 
        	
       } 
}); 
*/


//File file = null;
//file = new File (TabAtoController.class.getResource("/html/termoNotificacao.html").toExternalForm());

/*
engTermo.load(getClass().getResource("/html/termoNotificacao.html").toExternalForm()); 

engTermo.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>(){ 

        public void changed(final ObservableValue<? extends Worker.State> observableValue, 

                            final Worker.State oldState, 
                            final Worker.State newState) 

        { 
        	if (newState == Worker.State.SUCCEEDED){  
        		
        		htmlTermo = (String) engTermo.executeScript("document.documentElement.outerHTML"); 
        		
        	} 
        	
       } 
}); 
*/


/*
File file = null;

try {
	file = new File (TabAtoController.class.getResource("/html/autoInfracao.html").toURI());
	
} catch (URISyntaxException e) {
	System.out.println("erro na leitura do relatório.html" );
	e.printStackTrace();
}
*/

//engAuto.load(getClass().getResource("/html/autoInfracao.html").toExternalForm()); 

// capturar o html //

