package controllers;

import java.io.File;
import java.io.IOException;
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

import dao.VistoriaDao;
import entity.Endereco;
import entity.HibernateUtil;
import entity.Vistoria;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.StringConverter;
import tabela.VistoriaTabela;

public class TabVistoriaController implements Initializable{
	
	@FXML Pane tabVistoria = new Pane();
	
	String infraIncisos [];
	String atenIncisos [];
	String agraIncisos [];
	String penaIncisos [];

	@FXML Button btnNovo;
	@FXML Button btnSalvar;
	@FXML Button btnEditar;
	@FXML Button btnExcluir;
	@FXML Button btnCancelar;
	@FXML Button btnPesquisar;
	
	@FXML TextField tfAto;
	@FXML TextField tfAtoSEI;
	
	@FXML TextField tfPesquisar;
	
	@FXML DatePicker dpDataFiscalizacao;
	@FXML DatePicker dpDataCriacaoAto;
	
	@FXML Button btnIfracoes;
	@FXML Button btnPenalidades;
	@FXML Button btnAtenuantes;
	@FXML Button btnAgravantes;
	
	@FXML Button btnAjudaRelatorio;
	@FXML Button btnRecomendacoes;
	
	
	DateTimeFormatter formatter = new DateTimeFormatterBuilder()
			.parseCaseInsensitive()
			.append(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
			.toFormatter();
			
	
	
	// TableView Endereço //
			@FXML private TableView <VistoriaTabela> tvVistoria;
			
			@FXML TableColumn<VistoriaTabela, String> tcNumero;
			@FXML TableColumn<VistoriaTabela, String> tcSEI;
			@FXML TableColumn<VistoriaTabela, String> tcData;
	
	@FXML TextArea taObjeto;
	@FXML TextArea taApresentacao;
	@FXML TextArea taRelato;
	@FXML TextArea taRecomendacoes;
	
	@FXML Button btnPesquisarObjeto;
	@FXML Button  btnPesquisarApresentacao;
	@FXML Button btnPesquisarRelato;
	@FXML Button btnRelatorio;
	
	
	@FXML CheckBox checkInfra1;
	@FXML CheckBox checkInfra2;
	@FXML CheckBox checkInfra3;
	@FXML CheckBox checkInfra4;
	@FXML CheckBox checkInfra5;
	@FXML CheckBox checkInfra6;
	@FXML CheckBox checkInfra7;
	
	@FXML CheckBox checkPena1;
	@FXML CheckBox checkPena2;
	@FXML CheckBox checkPena3;
	@FXML CheckBox checkPena4;
	@FXML CheckBox checkPena5;
	@FXML CheckBox checkPena6;
	@FXML CheckBox checkPena7;
	
	@FXML CheckBox checkAten1;
	@FXML CheckBox checkAten2;
	@FXML CheckBox checkAten3;
	@FXML CheckBox checkAten4;
	@FXML CheckBox checkAten5;
	@FXML CheckBox checkAten6;
	@FXML CheckBox checkAten7;
	@FXML CheckBox checkAten8;
	@FXML CheckBox checkAten9;
	
	@FXML CheckBox checkAgra1;
	@FXML CheckBox checkAgra2;
	@FXML CheckBox checkAgra3;
	@FXML CheckBox checkAgra4;
	@FXML CheckBox checkAgra5;
	@FXML CheckBox checkAgra6;
	@FXML CheckBox checkAgra7;
	@FXML CheckBox checkAgra8;
	@FXML CheckBox checkAgra9;
	@FXML CheckBox checkAgra10;
	@FXML CheckBox checkAgra11;
	@FXML CheckBox checkAgra12;
	
	
	String strInfracoes;
	String strPenalidades;
	String strAgravantes;
	String strAtenuantes;
	
	String strPesquisa = "";

	HTMLEditor htmlObjeto;
	HTMLEditor htmlApresentacao; // = new HTMLEditor();
	HTMLEditor htmlRelato; //  = new HTMLEditor();
	HTMLEditor htmlRecomendacao; //   = new HTMLEditor();

	//-- pane para os editores html --//
	@FXML Pane paneObjeto; // = new Pane();
	@FXML Pane paneApresentacao; // = new Pane();
	@FXML Pane paneRelato; // = new Pane();
	@FXML Pane paneRecomendacao; // = new Pane();
	
	
	
	//-- passar vistoria para o maincontroller --//
	public Vistoria visGeral;
	
	public String obterIfracoes () {
		
		return strInfracoes;
	}
	
	int tipoAto = 2;
	
	public void checkInfraHab (ActionEvent event) {
		
		int count = 0;
		String strCheckInfra = "";
		
		if (checkInfra1.isSelected()) {
			count ++;
			strCheckInfra += "1";
		}
		if (checkInfra2.isSelected()) {
			count ++;
			strCheckInfra += "2";
		}
		if (checkInfra3.isSelected()) {
			count ++;
			strCheckInfra += "3";
		}
		if (checkInfra4.isSelected()) {
			count ++;
			strCheckInfra += "4";
		}
		if (checkInfra5.isSelected()) {
			count ++;
			strCheckInfra += "5";
		
		}
		if (checkInfra6.isSelected()) {
			strCheckInfra += "6";
			count ++;
		}
		if (checkInfra7.isSelected()) {
			strCheckInfra += "7";
			count ++;
		}
		
		strInfracoes = strCheckInfra;
		
		System.out.println("contador de cliques infração " + count);
		System.out.println("infrações " + strInfracoes);
		
	}
	
	public void checkPenaHab (ActionEvent event) {
		int count = 0;
		String strCheckAten = "";
		
		if (checkPena1.isSelected()) {
			count ++;
			strCheckAten += "1";
		}
		if (checkPena2.isSelected()) {
			count ++;
			strCheckAten += "2";
		}
		if (checkPena3.isSelected()) {
			count ++;
			strCheckAten += "3";
		}
		if (checkPena4.isSelected()) {
			count ++;
			strCheckAten += "4";
		}
		if (checkPena5.isSelected()) {
			count ++;
			strCheckAten += "5";
		
		}
		if (checkPena6.isSelected()) {
			strCheckAten += "6";
			count ++;
		}
		if (checkPena7.isSelected()) {
			strCheckAten += "7";
			count ++;
		}
		
		strPenalidades = strCheckAten;
		
		System.out.println("contador de penalidades " + count);
		System.out.println("penalidades " + strPenalidades);
		
	}
	
	public void checkAtenHab (ActionEvent event) {
		int count = 0;
		String strCheckAten = "";
		
		if (checkAten1.isSelected()) {
			count ++;
			strCheckAten += "1";
		}
		if (checkAten2.isSelected()) {
			count ++;
			strCheckAten += "2";
		}
		if (checkAten3.isSelected()) {
			count ++;
			strCheckAten += "3";
		}
		if (checkAten4.isSelected()) {
			count ++;
			strCheckAten += "4";
		}
		if (checkAten5.isSelected()) {
			count ++;
			strCheckAten += "5";
		
		}
		if (checkAten6.isSelected()) {
			strCheckAten += "6";
			count ++;
		}
		if (checkAten7.isSelected()) {
			strCheckAten += "7";
			count ++;
		}
		if (checkAten8.isSelected()) {
			strCheckAten += "8";
			count ++;
		}
		if (checkAten9.isSelected()) {
			strCheckAten += "9";
			count ++;
		}
		
		strAtenuantes = strCheckAten;
		
		System.out.println("contador de atenuantes " + count);
		System.out.println("atenuantes selecionados " + strAtenuantes);
		
	}
	
	public void checkAgraHab (ActionEvent event) {
		int count = 0;
		String strCheckAgra = "";
		
		if (checkAgra1.isSelected()) {
			count ++;
			strCheckAgra += "a";
		}
		if (checkAgra2.isSelected()) {
			count ++;
			strCheckAgra += "b";
		}
		if (checkAgra3.isSelected()) {
			count ++;
			strCheckAgra += "c";
		}
		if (checkAgra4.isSelected()) {
			count ++;
			strCheckAgra += "d";
		}
		if (checkAgra5.isSelected()) {
			count ++;
			strCheckAgra += "e";
		
		}
		if (checkAgra6.isSelected()) {
			strCheckAgra += "f";
			count ++;
		}
		if (checkAgra7.isSelected()) {
			strCheckAgra += "g";
			count ++;
		}
		
		if (checkAgra8.isSelected()) {
			strCheckAgra += "h";
			count ++;
		}
		
		if (checkAgra9.isSelected()) {
			strCheckAgra += "i";
			count ++;
		}
		if (checkAgra10.isSelected()) {
			strCheckAgra += "j";
			count ++;
		}
		if (checkAgra11.isSelected()) {
			strCheckAgra += "k";
			count ++;
		}
		if (checkAgra12.isSelected()) {
			strCheckAgra += "l";
			count ++;
		}
		
		strAgravantes = strCheckAgra;
		
		System.out.println("contador de agravantes " + count);
		System.out.println("agravantes " + strAgravantes);
		
	}
	
	
	
	public void btnNovoHab (ActionEvent event) {
		
		tfAto.setText(null);
		tfAtoSEI.setText(null);
		dpDataFiscalizacao.getEditor().clear(); // limpar datepicker
		dpDataCriacaoAto.getEditor().clear();
		
		htmlObjeto.setHtmlText("");
		htmlApresentacao.setHtmlText("");
		htmlRelato.setHtmlText("");
		htmlRecomendacao.setHtmlText("");
		
		tfAto.setDisable(false);
		tfAtoSEI.setDisable(false);
		dpDataFiscalizacao.setDisable(false);
		dpDataCriacaoAto.setDisable(false);
		
		htmlObjeto.setDisable(false);
		htmlApresentacao.setDisable(false);
		htmlRelato.setDisable(false);
		htmlRecomendacao.setDisable(false);
		
		btnNovo.setDisable(true);
		btnSalvar.setDisable(false);
		
		btnEditar.setDisable(true);
		btnExcluir.setDisable(true);
		
	}
	
	public void btnSalvarHab (ActionEvent event) {
		
		Vistoria vis = new Vistoria();
		
			vis.setVisIdentificacao(tfAto.getText());
			vis.setVisSEI(tfAtoSEI.getText());
			
			vis.setVisDataFiscalizacao(formatter.format(dpDataFiscalizacao.getValue()));
			vis.setVisDataCriacao(formatter.format(dpDataCriacaoAto.getValue()));
			
			vis.setVisInfracoes(strInfracoes);
			vis.setVisPenalidades(strPenalidades);
			vis.setVisAgravantes(strAgravantes);
			vis.setVisAtenuantes(strAtenuantes);
			
			vis.setVisObjeto(htmlObjeto.getHtmlText());
			vis.setVisApresentacao(htmlApresentacao.getHtmlText());
			vis.setVisRelato(htmlRelato.getHtmlText());
			vis.setVisRecomendacoes(htmlRecomendacao.getHtmlText());
			
			
			vis.setVisEndCodigoFK(eGeralVis);
			
			VistoriaDao visDao = new VistoriaDao();
			
			visDao.salvarVistoria(vis);
			visDao.mergeVistoria(vis);
			
			listarVistorias(strPesquisa);
			selecionarVistoria();
			
			//-- Alerta de interferência editada --//
			Alert vSalva = new Alert (Alert.AlertType.CONFIRMATION);
			vSalva.setTitle("Parabéns!");
			vSalva.setContentText("vistoria salva com sucesso!");
			vSalva.setHeaderText(null);
			vSalva.show();
			
			
			//-- número da vistoria para a tabela atos --//
			
			visGeral = vis;
			main.pegarVistoria(vis);
			
		
	}
	
	public void btnEditarHab (ActionEvent event) {
		
		if (tfAto.isDisable()) {
			
			tfAto.setDisable(false);
			tfAtoSEI.setDisable(false);
			dpDataFiscalizacao.setDisable(false);
			dpDataCriacaoAto.setDisable(false);
			
			htmlObjeto.setDisable(false);
			htmlApresentacao.setDisable(false);
			htmlRelato.setDisable(false);
			htmlRecomendacao.setDisable(false);
			
		} else {
		
			try {
			
			VistoriaTabela visTab = tvVistoria.getSelectionModel().getSelectedItem();
			
			Vistoria vis = new Vistoria(visTab);
			
				//-- capturar endereço relacionado --//
				vis.setVisEndCodigoFK(eGeralVis);
			
				//-- capturar tela --//
				vis.setVisIdentificacao(tfAto.getText());
				vis.setVisSEI(tfAtoSEI.getText());
				
				vis.setVisDataFiscalizacao(formatter.format(dpDataFiscalizacao.getValue()));  // está dando erro temporal nullpoint
				vis.setVisDataCriacao(formatter.format(dpDataCriacaoAto.getValue()));
				
				vis.setVisInfracoes(strInfracoes);
				vis.setVisPenalidades(strPenalidades);
				vis.setVisAgravantes(strAgravantes);
				vis.setVisAtenuantes(strAtenuantes);
				
				vis.setVisObjeto(htmlObjeto.getHtmlText());
				vis.setVisApresentacao(htmlApresentacao.getHtmlText());
				vis.setVisRelato(htmlRelato.getHtmlText());
				vis.setVisRecomendacoes(htmlRecomendacao.getHtmlText());
				
						VistoriaDao visDao = new VistoriaDao();
						
						visDao.mergeVistoria(vis);
						

						listarVistorias(strPesquisa);
						selecionarVistoria();
						modularBotoes();
						
						//-- Alerta de interferência editada --//
						Alert vMerge = new Alert (Alert.AlertType.CONFIRMATION);
						vMerge.setTitle("Parabéns!");
						vMerge.setContentText("vistoria editada!");
						vMerge.setHeaderText(null);
						vMerge.show();
						
						visGeral = vis;
						main.pegarVistoria(vis);
			
			
					} catch (Exception e) {
						
						System.out.println("Erro ao editar: " + e);
						
						//-- Alerta de interferência editada --//
						Alert vMerge = new Alert (Alert.AlertType.ERROR);
						vMerge.setTitle("Atenção!");
						vMerge.setContentText("erro ao editar vistoria!");
						vMerge.setHeaderText(null);
						vMerge.show();
						
					}
			
			
			
		}
		
	}

	public void btnExcluirHab (ActionEvent event) {
		
		try {
		
			VistoriaTabela visTab = tvVistoria.getSelectionModel().getSelectedItem();
			
			VistoriaDao visDao = new VistoriaDao();
			
			visDao.removerVistoria(visTab.getVisCodigo());
			
			listarVistorias(strPesquisa);
			selecionarVistoria();
			modularBotoes();
			
			//-- Alerta de interferência editada --//
			Alert vRemover = new Alert (Alert.AlertType.CONFIRMATION);
			vRemover.setTitle("Parabéns!");
			vRemover.setContentText("vistoria excluída!");
			vRemover.setHeaderText(null);
			vRemover.show();
		
				} catch (Exception e) {
					
					System.out.println("Erro ao editar: " + e);
					
					//-- Alerta de interferência editada --//
					Alert vRemover = new Alert (Alert.AlertType.ERROR);
					vRemover.setTitle("Atenção!");
					vRemover.setContentText("erro ao excluir vistoria!");
					vRemover.setHeaderText(null);
					vRemover.show();
				}
	
		
	
	}
	
	public void btnCancelarHab (ActionEvent event) {
		
		modularBotoes();
	}
	
	public void btnPesquisarHab (ActionEvent event) {
		
		strPesquisa = tfPesquisar.getText();
		listarVistorias(strPesquisa);
		selecionarVistoria();
		
	}
	
	
	public void btnPesqObjHab (ActionEvent event) {
		
		//e se a vistoria for salva pela primeira vez? Ainda  não há as informaçoes visGeral
				
		String objeto = "<p>Em atendimento ao " + visGeral.getVisEndCodigoFK().getListDenuncias().get(0).getDoc_Denuncia() + 
				", foi realizada vistoria no dia " + visGeral.getVisDataFiscalizacao() + ", para verifica&ccedil;&atilde;o de " +
				visGeral.getVisEndCodigoFK().getListDenuncias().get(0).getDesc_Denuncia() + ", no endereço: " + 
				visGeral.getVisEndCodigoFK().getDesc_Endereco() + ".</p>";
		
		htmlObjeto.setHtmlText(objeto);
		
	}
	
	public void btnPesApHab (ActionEvent event) {
		
		String apresentacao = "A vistoria ocorreu em " + visGeral.getVisDataFiscalizacao() + ", por volta das HORA, "
				+ "e contou com a presen&ccedil;a do(s) t&eacute;cnico(s) "
				+ "TECNICO e do respons&aacute;vel pela propriedade " + eGeralVis.getListUsuarios().get(0).getUsNome() + ".";
		
		htmlApresentacao.setHtmlText(apresentacao);
		
	}

	public void btnAjudaRelatorioHab (ActionEvent event) {
	
	}
	
	
	String htmlVistoria = "";
	
	public void btnRelatorioHab (ActionEvent event) {
		
		File file = null;
		
		file = new File (TabVistoriaController.class.getResource("/html/relatorioVistoria.html").getFile());
		
		Document docHtml = null;
		
		try {
			docHtml = Jsoup.parse(file, "UTF-8");  // retirei o  .clone()
			
			// file:\C:\Users\fabricio\eclipse-workspace\fiscalizacao\target\classes\html\relatorioVistoria.html 
			// (A sintaxe do nome do arquivo, do nome do diretório ou do rótulo do volume está incorreta)
			
		} catch (IOException e1) {
			System.out.println("Erro na leitura no parse Jsoup!!!");
			e1.printStackTrace();
		}
		  
	      
		// inicializar o usuario //
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		Endereco endereco = (Endereco) session.get(Endereco.class, eGeralVis.getCod_Endereco());
		
		// inicializar as listas //
		Hibernate.initialize(endereco.getListUsuarios());
		Hibernate.initialize(endereco.getListInterferencias());
		
		if (tx.getStatus().equals(TransactionStatus.ACTIVE)) { 
		    tx.commit();
		}
		
		System.out.println("usuario para imprimir relatorio " + endereco.getListUsuarios().get(0).getUsNome());
		
		session.close();
		
		//-- preencher a vistoria --//
		docHtml.select("nRela").prepend(visGeral.getVisIdentificacao());
		docHtml.select("nRelSEI").prepend(visGeral.getVisSEI());
		
		if (endereco.getListUsuarios() != null) { // não está funcionando... melhorar
			
			//-- usuario atraves do endereco --//
			docHtml.select("nomeUs").prepend(endereco.getListUsuarios().get(0).getUsNome());
			docHtml.select("cpfUs").prepend(endereco.getListUsuarios().get(0).getUsCPFCNPJ());
			docHtml.select("endUs").prepend(endereco.getListUsuarios().get(0).getUsDescricaoEnd()); 
			
			if (endereco.getListUsuarios().get(0).getUsRA() != null) { //se o combox diferente de null, não acontece o mesmo no textfield
				docHtml.select("raUs").prepend(endereco.getListUsuarios().get(0).getUsRA());
			}
			
			// endereco do usuario atraves do endereco //
			docHtml.select("cepUs").prepend(endereco.getListUsuarios().get(0).getUsCEP());
			docHtml.select("cidUs").prepend(endereco.getListUsuarios().get(0).getUsCidade());
			docHtml.select("ufUs").prepend(endereco.getListUsuarios().get(0).getUsEstado());
			docHtml.select("telUs").prepend(endereco.getListUsuarios().get(0).getUsTelefone());
			docHtml.select("celUs").prepend(endereco.getListUsuarios().get(0).getUsCelular());
			docHtml.select("emailUs").prepend(endereco.getListUsuarios().get(0).getUsEmail());
			
			    
			// endereco do empreedimento //  
			 
			docHtml.select("EndEmpDes").prepend(endereco.getDesc_Endereco());
			docHtml.select("EndEmpRA").prepend(endereco.getRA_Endereco());
			docHtml.select("EndEmpCep").prepend(endereco.getCEP_Endereco());
			docHtml.select("EndEmpCid").prepend(endereco.getCid_Endereco());
			docHtml.select("EndEmpUF").prepend(endereco.getUF_Endereco());
			
		}
		
		//-- latitude e longitude do endereço --//
		docHtml.select("latEnd").prepend(endereco.getLat_Endereco().toString());
		docHtml.select("lngEnd").prepend(endereco.getLon_Endereco().toString());
		 
		//-- objecto, apresentação, relato e recomendações --//
		docHtml.select("objVis").prepend(visGeral.getVisObjeto());
		docHtml.select("apreVis").prepend(visGeral.getVisApresentacao());
		docHtml.select("relVis").prepend(visGeral.getVisRelato());
		docHtml.select("recVis").prepend(visGeral.getVisRecomendacoes());
		
		// <br><p>Tipo: Superficial, Bacia: Paranoá, UH: 23, Corpo Hídrico: rio das pedras, Coordenadas: -15, -48</p><br>
		//interEnd
		
		if (endereco.getListInterferencias() != null) {
			
			docHtml.select("interEnd").prepend(
					"<p>Tipo: " + endereco.getListInterferencias().get(0).getInter_Tipo()
				+ 	", Bacia: " + endereco.getListInterferencias().get(0).getInter_Bacia()
				+ 	"</p>"
				);
		}
	
		
		//-- infrações --//
		for (int i = 0; i<infrArray.length; i++) {
			
			if (infrArray[i].equals("1") ) {
				docHtml.select("infrRel").append(infraIncisos[0]);
			}
			if (infrArray[i].equals("2") ) {
				docHtml.select("infrRel").append(infraIncisos[1]);
			}
			if (infrArray[i].equals("3")  ) {
				docHtml.select("infrRel").append(infraIncisos[2]);
			}
			if (infrArray[i].equals("4") ) {
				docHtml.select("infrRel").append(infraIncisos[3]);
			}
			if (infrArray[i].equals("5")  ) {
				docHtml.select("infrRel").append(infraIncisos[4]);
			}
			if (infrArray[i].equals("6") ) {
				docHtml.select("infrRel").append(infraIncisos[5]);
			}
			if (infrArray[i].equals("7")  ) {
				docHtml.select("infrRel").append(infraIncisos[6]);
			}
			
		}
		
		//-- atenuantes --//
		for (int i = 0; i<atenArray.length; i++) {
			
			if (atenArray[i].equals("1") ) {
				docHtml.select("atenRel").append(atenIncisos[0]);
			}
			if (atenArray[i].equals("2") ) {
				docHtml.select("atenRel").append(atenIncisos[1]);
			}
			if (atenArray[i].equals("3")  ) {
				docHtml.select("atenRel").append(atenIncisos[2]);
			}
			if (atenArray[i].equals("4") ) {
				docHtml.select("atenRel").append(atenIncisos[3]);
			}
			if (atenArray[i].equals("5")  ) {
				docHtml.select("atenRel").append(atenIncisos[4]);
			}
			if (atenArray[i].equals("6") ) {
				docHtml.select("atenRel").append(atenIncisos[5]);
			}
			if (atenArray[i].equals("7")  ) {
				docHtml.select("atenRel").append(atenIncisos[6]);
			}
			if (atenArray[i].equals("8")  ) {
				docHtml.select("atenRel").append(atenIncisos[7]);
			}
			if (atenArray[i].equals("9")  ) {
				docHtml.select("atenRel").append(atenIncisos[8]);
			}
			
		}

		//tem que colocar um  if
		for (int i = 0; i<agraArray.length; i++) {
			if (agraArray[i].equals("a") ) {
				docHtml.select("agraRel").append(agraIncisos[0]);
			}
			if (agraArray[i].equals("b") ) {
				docHtml.select("agraRel").append(agraIncisos[1]);
			}
			if (agraArray[i].equals("c")  ) {
				docHtml.select("agraRel").append(agraIncisos[2]);
			}
			if (agraArray[i].equals("d") ) {
				docHtml.select("agraRel").append(agraIncisos[3]);
			}
			if (agraArray[i].equals("e")  ) {
				docHtml.select("agraRel").append(agraIncisos[4]);
			}
			if (agraArray[i].equals("f") ) {
				docHtml.select("agraRel").append(agraIncisos[5]);
			}
			if (agraArray[i].equals("g")  ) {
				docHtml.select("agraRel").append(agraIncisos[6]);
			}
			if (agraArray[i].equals("h")  ) {
				docHtml.select("agraRel").append(agraIncisos[7]);
			}
			if (agraArray[i].equals("i")  ) {
				docHtml.select("agraRel").append(agraIncisos[8]);
			}
			if (agraArray[i].equals("j")  ) {
				docHtml.select("agraRel").append(agraIncisos[9]);
			}
			if (agraArray[i].equals("k")  ) {
				docHtml.select("agraRel").append(agraIncisos[10]);
			}
			if (agraArray[i].equals("l")  ) {
				docHtml.select("agraRel").append(agraIncisos[11]);
			}
					
		}	// com if, mais um }
		
		
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
	
	public void btnRecomendacoesHab (ActionEvent event) {
	
	String recomendacao = "Solicitar, no prazo de 60 (sessenta) dias contados a partir do recebimento do Termo de Notificação, a outorga de "
			+ "direito de usos de recursos hídricos, de acordo com a Resolução/Adasa n° 350, de 23 de junho de 2006.";

	htmlRecomendacao.setHtmlText(recomendacao);

	}
	
	//-- INITIALIZE --//
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
		modularBotoes();
		
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
		
		dpDataCriacaoAto.setConverter(new StringConverter<LocalDate>() {
			
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
		
		dpDataFiscalizacao.setOnAction((ActionEvent event) -> {
			
		}
				
		);
		
		
		ObterArtigos ();
		
		// -- inicitalizar o mapa -- //
				Platform.runLater(() ->{
				relatarHTML();  
				
				});
		
	}
	
	public void relatarHTML () {
	
		htmlObjeto = new HTMLEditor();
		
			htmlObjeto.setPrefSize(800, 200);
			
			htmlObjeto.setOnKeyPressed(event -> {
			    if (event.getCode() == KeyCode.SPACE  
			            || event.getCode() == KeyCode.TAB ) {
			        // Consume Event before Bubbling Phase, -> otherwise Scrollpane scrolls
			        event.consume();
			    }
			});
			htmlObjeto.setHtmlText("<p><font face='Times New Roman'> </font></p>");
			
			StackPane root = new StackPane();
			root.getChildren().add(htmlObjeto);
			paneObjeto.getChildren().add(htmlObjeto);
	    
	   
		htmlApresentacao  = new HTMLEditor();
		
			htmlApresentacao.setPrefSize(800, 200);
			
			htmlApresentacao.setOnKeyPressed(event -> {
			    if (event.getCode() == KeyCode.SPACE  
			            || event.getCode() == KeyCode.TAB ) {
			        // Consume Event before Bubbling Phase, -> otherwise Scrollpane scrolls
			        event.consume();
			    }
			});
			htmlApresentacao.setHtmlText("<p><font face='Times New Roman'> </font></p>");
			
			StackPane rootAp = new StackPane();
			rootAp.getChildren().add(htmlApresentacao);
		    paneApresentacao.getChildren().add(htmlApresentacao);
	    
	    
		htmlRelato  = new HTMLEditor();
		
			htmlRelato.setPrefSize(800, 773);
			
			htmlRelato.setOnKeyPressed(event -> {
			    if (event.getCode() == KeyCode.SPACE  
			            || event.getCode() == KeyCode.TAB ) {
			        // Consume Event before Bubbling Phase, -> otherwise Scrollpane scrolls
			        event.consume();
			    }
			});
			htmlRelato.setHtmlText("<p><font face='Times New Roman'> </font></p>");
			
			
			StackPane rootRel = new StackPane();
			rootRel.getChildren().add(htmlRelato);
			paneRelato.getChildren().add(htmlRelato);
			
		    
		htmlRecomendacao  = new HTMLEditor();
		
			htmlRecomendacao.setPrefSize(800, 200);
			
			htmlRecomendacao.setOnKeyPressed(event -> {
			    if (event.getCode() == KeyCode.SPACE  
			            || event.getCode() == KeyCode.TAB ) {
			        // Consume Event before Bubbling Phase, -> otherwise Scrollpane scrolls
			        event.consume();
			    }
			});
			htmlRecomendacao.setHtmlText("<p><font face='Times New Roman'> </font></p>");
			
			StackPane rootReco = new StackPane();
			rootReco.getChildren().add(htmlRecomendacao);
			paneRecomendacao.getChildren().add(htmlRecomendacao);

	}
	
	
	@FXML public Label lblVisEnd = new Label(); // público para receber valor do MainController, método pegarEnd()
	
	//  objeto para passar os valor pelo MainControoler para outro controller //
	public Endereco eGeralVis;
	
	//  Controller Principal - MainController  //
	@FXML private MainController main;
	
	// MAIN CONTROLLER //
	public void init(MainController mainController) {
			main = mainController;
	}
	
	public void listarVistorias (String strPesquisa) {
 		
	 	// --- conexão - listar endereços --- //
		VistoriaDao visDao = new VistoriaDao();
		List<Vistoria> visList = visDao.listarVistoria(strPesquisa);
		ObservableList<VistoriaTabela> oListVis = FXCollections.observableArrayList();
		
		
		if (!oListVis.isEmpty()) {
			oListVis.clear();
		}
		
			for (Vistoria vis : visList) {
				
			VistoriaTabela visTab = new VistoriaTabela(
					
				vis.getVisCodigo(),
				vis.getVisEndCodigoFK(),
				vis.getVisObjeto(),
				vis.getVisApresentacao(),
				vis.getVisRelato(),
				vis.getVisRecomendacoes(),
				vis.getVisInfracoes(),
				vis.getVisPenalidades(),
				vis.getVisAtenuantes(),
				vis.getVisAgravantes(),
				vis.getVisIdentificacao(),
				vis.getVisSEI(),
				vis.getVisDataFiscalizacao(),
				vis.getVisDataCriacao(),
				vis.getVisListAtos()
				
				);
				
				
				oListVis.add(visTab);
						
		}
			
			
			tcNumero.setCellValueFactory(new PropertyValueFactory<VistoriaTabela, String>("visIdentificacao")); 
			//tcEndRA.setCellValueFactory(new PropertyValueFactory<EnderecoTabela, String>("RA_Endereco")); 
			//tcEndCid.setCellValueFactory(new PropertyValueFactory<EnderecoTabela, String>("CEP_Endereco")); 
			
			tvVistoria.setItems(oListVis);
			
	 	}
	
	String infrArray [];
	String agraArray [];
	String atenArray [];
	
	// método selecionar vistoria -- //
	 	public void selecionarVistoria () {
		
			tvVistoria.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
				public void changed(ObservableValue<?> observable , Object oldValue, Object newValue) {
				
				VistoriaTabela visTab = (VistoriaTabela) newValue;
				
				if (visTab == null) {
					
					btnNovo.setDisable(true);
					btnSalvar.setDisable(true);
					
					btnEditar.setDisable(false);
					btnExcluir.setDisable(false);
					btnCancelar.setDisable(false);
					
				} else {

					tfAto.setText(visTab.getVisIdentificacao());
					tfAtoSEI.setText(visTab.getVisSei());
					
					dpDataFiscalizacao.setValue(LocalDate.parse(visTab.getVisDataFiscalizacao(), formatter));
					dpDataCriacaoAto.setValue(LocalDate.parse(visTab.getVisDataCriacao(), formatter));
						
					modularCheckBox ();
					
					String infr =  visTab.getVisInfracoes();
					String pena = visTab.getVisPenalidades();
					String agra = visTab.getVisAgravantes();
					String aten = visTab.getVisAtenuantes();
					
					System.out.println(htmlObjeto.getHtmlText());
					
					//-- infrações --//
					if (infr != null) {
						
						infrArray = infr.split("");
						
						//System.out.println("valor string infra auto selecionadas  " + infr);
						
						
						for (int i = 0; i<infrArray.length; i++) {
							if (infrArray[i].equals("1") ) {
								checkInfra1.setSelected(true);
								
							}
							if (infrArray[i].equals("2") ) {
								checkInfra2.setSelected(true);
								
							}
							if (infrArray[i].equals("3")  ) {
								checkInfra3.setSelected(true);
								
								
							}
							if (infrArray[i].equals("4") ) {
								checkInfra4.setSelected(true);
								
								
							}
							if (infrArray[i].equals("5")  ) {
								checkInfra5.setSelected(true);
								
								
							}
							if (infrArray[i].equals("6") ) {
								checkInfra6.setSelected(true);
								
								
							}
							if (infrArray[i].equals("7")  ) {
								checkInfra7.setSelected(true);
								
							}
							
							
							checkInfraHab(null);
						}}
					

									//-- penalidades --//
									if (pena != null) {
										
										String penaArray [] = pena.split("");
										
										//System.out.println("valor string pena auto selecionadas: " + pena);
										
										
										for (int i = 0; i<penaArray.length; i++) {
											
											if (penaArray[i].equals("1") ) {
												checkPena1.setSelected(true);
												
											}
											if (penaArray[i].equals("2") ) {
												checkPena2.setSelected(true);
												
											}
											if (penaArray[i].equals("3")  ) {
												checkPena3.setSelected(true);
												
											}
											if (penaArray[i].equals("4") ) {
												checkPena4.setSelected(true);
												checkPena4.selectedProperty().addListener(new ChangeListener<Boolean>() {
													public void changed (ObservableValue <? extends Boolean> ov, Boolean old_val, Boolean new_val)
													{
														checkPenaHab(null);
													}
												});
											}
											if (penaArray[i].equals("5")  ) {
												checkPena5.setSelected(true);
												
											}
											if (penaArray[i].equals("6") ) {
												checkPena6.setSelected(true);
												
											}
											if (penaArray[i].equals("7")  ) {
												checkPena7.setSelected(true);
												
											}
											
											//System.out.println(i + " veja as penalidades array selecionadas" + penaArray[i]);
											checkPenaHab(null);
										}}
									
							//-- atenuantes --//
							if (aten != null) {
								
								atenArray = aten.split("");
								
								//System.out.println("valor string atenuantes auto selecionadas" + aten);
								
								for (int i = 0; i<atenArray.length; i++) {
								
									if (atenArray[i].equals("1") ) {
										checkAten1.setSelected(true);
										
									}
									if (atenArray[i].equals("2") ) {
										checkAten2.setSelected(true);
										
									}
									if (atenArray[i].equals("3")  ) {
										checkAten3.setSelected(true);
										
									}
									if (atenArray[i].equals("4") ) {
										checkAten4.setSelected(true);
										
									}
									if (atenArray[i].equals("5")  ) {
										checkAten5.setSelected(true);
										
									}
									if (atenArray[i].equals("6") ) {
										checkAten6.setSelected(true);
										
									}
									if (atenArray[i].equals("7")  ) {
										checkAten7.setSelected(true);
										
									}
									if (atenArray[i].equals("8")  ) {
										checkAten8.setSelected(true);
										
									}
									if (atenArray[i].equals("9")  ) {
										checkAten9.setSelected(true);
										
									}
									
									checkAtenHab(null);
								}}
							
											//-- agravantes --//
											if (agra != null) {
												
												agraArray = agra.split("");
													
												for (int i = 0; i<agraArray.length; i++) {
												
													if (agraArray[i].equals("a") ) {
														checkAgra1.setSelected(true);
														
													}
													if (agraArray[i].equals("b") ) {
														checkAgra2.setSelected(true);
														
													}
													if (agraArray[i].equals("c")  ) {
														checkAgra3.setSelected(true);
														
													}
													if (agraArray[i].equals("d") ) {
														checkAgra4.setSelected(true);
														
													}
													if (agraArray[i].equals("e")  ) {
														checkAgra5.setSelected(true);
														
													}
													
													if (agraArray[i].equals("f") ) {
														checkAgra6.setSelected(true);
														
													}
													if (agraArray[i].equals("g")  ) {
														checkAgra7.setSelected(true);
														
													}
													
													if (agraArray[i].equals("h")  ) {
														checkAgra8.setSelected(true);
														
													}
													
													if (agraArray[i].equals("i")  ) {
														checkAgra9.setSelected(true);
														
													}
													if (agraArray[i].equals("j")  ) {
														checkAgra10.setSelected(true);
														
													}
													if (agraArray[i].equals("k")  ) {
														checkAgra11.setSelected(true);
														
													}
													if (agraArray[i].equals("l")  ) {
														checkAgra12.setSelected(true);
														
													}
													
													checkAgraHab(null);
												}}
							
					
					
					htmlObjeto.setHtmlText(visTab.getVisObjeto());
					htmlApresentacao.setHtmlText(visTab.getVisApresentacao());
					htmlRelato.setHtmlText(visTab.getVisRelato());
					htmlRecomendacao.setHtmlText(visTab.getVisRecomendacoes());
					
					
					//-- pegar a vistoria selecionada --//
					Vistoria visG = new Vistoria(visTab);
					visGeral = visG;
					main.pegarVistoria(visGeral);
					
					//-- mudar o endereço de acordo com a seleção --//
					eGeralVis = visTab.getVisEndCodigoFK();
					lblVisEnd.setText(eGeralVis.getDesc_Endereco() + " |  RA: "  + eGeralVis.getRA_Endereco() );
					
					//-- modular botões --//
					btnNovo.setDisable(true);
					btnSalvar.setDisable(true);
					btnEditar.setDisable(false);
					btnExcluir.setDisable(false);
					btnCancelar.setDisable(false);
					
				}
				}
			});
		}
	 	
	 public void modularBotoes () {
		 
		 tfAto.setDisable(true);
		 tfAtoSEI.setDisable(true);
		 dpDataFiscalizacao.setDisable(true);
		 dpDataCriacaoAto.setDisable(true);
		 
		 btnSalvar.setDisable(true);
		 btnEditar.setDisable(true);
		 btnExcluir.setDisable(true);
		 
		 btnNovo.setDisable(false);
	 }
	 
	 public void modularCheckBox () {
		 
		 checkInfra1.setSelected(false);
		 checkInfra2.setSelected(false);
		 checkInfra3.setSelected(false);
		 checkInfra4.setSelected(false);
		 checkInfra5.setSelected(false);
		 checkInfra6.setSelected(false);
		 checkInfra7.setSelected(false);
		 
		 checkPena1.setSelected(false);
		 checkPena2.setSelected(false);
		 checkPena3.setSelected(false);
		 checkPena4.setSelected(false);
		 checkPena5.setSelected(false);
		 checkPena6.setSelected(false);
		 checkPena7.setSelected(false);
		 
		 checkAten1.setSelected(false);
		 checkAten2.setSelected(false);
		 checkAten3.setSelected(false);
		 checkAten4.setSelected(false);
		 checkAten5.setSelected(false);
		 checkAten6.setSelected(false);
		 checkAten7.setSelected(false);
		 checkAten8.setSelected(false);
		 checkAten9.setSelected(false);
		 
		 checkAgra1.setSelected(false);
		 checkAgra2.setSelected(false);
		 checkAgra3.setSelected(false);
		 checkAgra4.setSelected(false);
		 checkAgra5.setSelected(false);
		 checkAgra6.setSelected(false);
		 checkAgra7.setSelected(false);
		 checkAgra8.setSelected(false);
		 checkAgra9.setSelected(false);
		 checkAgra10.setSelected(false);
		 checkAgra11.setSelected(false);
		 checkAgra12.setSelected(false);
	 }
	 
	 public void btnInfracoesHab (ActionEvent event) {
		 
		 ObservableList<String> documentos = FXCollections.observableArrayList(infraIncisos);
			ListView<String> listView = new ListView<String>(documentos);
			TableColumn<List, String> tc = new TableColumn<List, String> ("Documentos");
			
			tc.setCellValueFactory(new Callback<CellDataFeatures<List, String>, ObservableValue<String>>() {
				
			     public ObservableValue<String> call(CellDataFeatures<List, String> p) {
			 
			         return new SimpleStringProperty(p.getValue().toString());
			     }
			 });
			 

				//TableView tv = new TableView(listView);
				
				Scene scene = new Scene(listView);
				Stage stage = new Stage(); // StageStyle.UTILITY - tirei para ver como fica, se aparece o minimizar
				stage.setWidth(964);
				stage.setHeight(185);
			    stage.setScene(scene);
			    stage.setMaximized(false);
			    stage.setResizable(false);
			    stage.setX(425.0);
			    stage.setY(410.0);
			   
			    stage.setAlwaysOnTop(true); 
			    stage.show();
			
			    //--  https://docs.oracle.com/javafx/2/ui_controls/ListViewSample.java.html  --// 
			    listView.getSelectionModel().selectedItemProperty().addListener(
             new ChangeListener<String>() {
                 public void changed(ObservableValue<? extends String> 
                 ov, String old_val, String new_val) {
               
                      Clipboard clip = Clipboard.getSystemClipboard();
                      ClipboardContent conteudo = new ClipboardContent();
                      conteudo.putString(new_val);
                      clip.setContent(conteudo);
                 }
             });
             
		 
	 }
	 public void btnPenalidadesHab (ActionEvent event) {
		 
		 
	 }
	 public void btnAtenuantesHab (ActionEvent event) {
		 
		 ObservableList<String> documentos = FXCollections.observableArrayList(atenIncisos);
			ListView<String> listView = new ListView<String>(documentos);
			TableColumn<List, String> tc = new TableColumn<List, String> ("Documentos");
			
			tc.setCellValueFactory(new Callback<CellDataFeatures<List, String>, ObservableValue<String>>() {
				
			     public ObservableValue<String> call(CellDataFeatures<List, String> p) {
			 
			         return new SimpleStringProperty(p.getValue().toString());
			     }
			 });

				//TableView tv = new TableView(listView);
				
				Scene scene = new Scene(listView);
				Stage stage = new Stage(); // StageStyle.UTILITY - tirei para ver como fica, se aparece o minimizar
				stage.setWidth(964);
				stage.setHeight(185);
			    stage.setScene(scene);
			    stage.setMaximized(false);
			    stage.setResizable(false);
			    stage.setX(425.0);
			    stage.setY(410.0);
			   
			    stage.setAlwaysOnTop(true); 
			    stage.show();
			
			    //--  https://docs.oracle.com/javafx/2/ui_controls/ListViewSample.java.html  --// 
			    listView.getSelectionModel().selectedItemProperty().addListener(
          new ChangeListener<String>() {
              public void changed(ObservableValue<? extends String> 
              ov, String old_val, String new_val) {
            
                   Clipboard clip = Clipboard.getSystemClipboard();
                   ClipboardContent conteudo = new ClipboardContent();
                   conteudo.putString(new_val);
                   clip.setContent(conteudo);
              }
          });
          
         
	 
	 
	 }
	 public void btnAgravantesHab (ActionEvent event) {
		
		 
		 ObservableList<String> documentos = FXCollections.observableArrayList(agraIncisos);
			ListView<String> listView = new ListView<String>(documentos);
			TableColumn<List, String> tc = new TableColumn<List, String> ("Documentos");
			
			tc.setCellValueFactory(new Callback<CellDataFeatures<List, String>, ObservableValue<String>>() {
				
			     public ObservableValue<String> call(CellDataFeatures<List, String> p) {
			 
			         return new SimpleStringProperty(p.getValue().toString());
			     }
			 });

				//TableView tv = new TableView(listView);
				
				Scene scene = new Scene(listView);
				Stage stage = new Stage(); // StageStyle.UTILITY - tirei para ver como fica, se aparece o minimizar
				stage.setWidth(964);
				stage.setHeight(185);
			    stage.setScene(scene);
			    stage.setMaximized(false);
			    stage.setResizable(false);
			    stage.setX(425.0);
			    stage.setY(410.0);
			   
			    stage.setAlwaysOnTop(true); 
			    stage.show();
			
			    //--  https://docs.oracle.com/javafx/2/ui_controls/ListViewSample.java.html  --// 
			    listView.getSelectionModel().selectedItemProperty().addListener(
			    		new ChangeListener<String>() {
              public void changed(ObservableValue<? extends String> 
              ov, String old_val, String new_val) {
            
                   Clipboard clip = Clipboard.getSystemClipboard();
                   ClipboardContent conteudo = new ClipboardContent();
                   conteudo.putString(new_val);
                   clip.setContent(conteudo);
              }
          });
         
	 }
	 
	 public void ObterArtigos () {
		 
		 	//--  --//
			infraIncisos = new String [8];
			
		
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
		 
		 
	 }
	
}


/* para ler o html, mas não precisa mais...
WebView webView = new WebView();
   
WebEngine eng = webView.getEngine();

eng.load(getClass().getResource("/html/relatoriovistoria.html").toExternalForm());

eng.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>()

    {
        public void changed(final ObservableValue<? extends Worker.State> observableValue,
                            final Worker.State oldState,
                            final Worker.State newState)
        {
        	
            if (newState == Worker.State.SUCCEEDED)
            {
            	htmlVistoria = (String) eng.executeScript("document.documentElement.outerHTML");  
            }
            
        }
    });

*/

