package controllers;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;
import java.util.ResourceBundle;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.JDBCConnectionException;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import dao.VistoriaDao;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
import tabela.VistoriaTabela;

public class TabVistoriaController implements Initializable{
	
	@FXML Pane tabVistoria = new Pane();
	RelatosController relCon;
	
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
	
	
	
	@FXML Image imgRelatorio = new Image(TabVistoriaController.class.getResourceAsStream("/images/report32.png"));
		
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
		
		htmlObjeto.setHtmlText("<p><font face='Times New Roman'> </font></p>");
		htmlApresentacao.setHtmlText("<p><font face='Times New Roman'> </font></p>");
		htmlRelato.setHtmlText("<p><font face='Times New Roman'> </font></p>");
		htmlRecomendacao.setHtmlText("<p><font face='Times New Roman'> </font></p>");
		
		tfAto.setDisable(false);
		tfAtoSEI.setDisable(false);
		dpDataFiscalizacao.setDisable(false);
		dpDataCriacaoAto.setDisable(false);
		
		btnNovo.setDisable(true);
		btnSalvar.setDisable(false);
		
		btnEditar.setDisable(true);
		btnExcluir.setDisable(true);
		
		abrirEditorHTML();
		abrirCheckBox();
		LimparCheckBox();
		
	}
	
	public void btnSalvarHab (ActionEvent event) {
		
		obsList = FXCollections.observableArrayList();
		
		if (eGeralVis == null) {
			
			Alert aLat = new Alert (Alert.AlertType.ERROR);
			aLat.setTitle("Alerta!!!");
			aLat.setContentText("Endereço NÃO selecionado!!!");
			aLat.setHeaderText(null);
			aLat.show();
			
		} else {
			
			if (dpDataFiscalizacao.getValue() == null  ||
					dpDataCriacaoAto.getValue() == null  ||
						tfAto.getText().isEmpty()  ||
						tfAtoSEI.getText().isEmpty()
					
					) {
				
				Alert aLat = new Alert (Alert.AlertType.ERROR);
				aLat.setTitle("Alerta!!!");
				aLat.setContentText("Informe: Número do Ato, Data da fiscalização e Data de Criação!!!");
				aLat.setHeaderText(null);
				aLat.show();
				
			} else {
		
					Vistoria vis = new Vistoria();
					
						vis.setVisIdentificacao(tfAto.getText());
						vis.setVisSEI(tfAtoSEI.getText());
						
						/*
						if (dpDataFiscalizacao.getValue() == null) {
							vis.setVisDataFiscalizacao(null);}
						else {
							vis.setVisDataFiscalizacao(formatter.format(dpDataFiscalizacao.getValue()));
						}
						
						if (dpDataCriacaoAto.getValue() == null) {
							vis.setVisDataCriacao(null);}
						else {
							vis.setVisDataCriacao(formatter.format(dpDataCriacaoAto.getValue()));
						}
						*/
						
						if (dpDataFiscalizacao.getValue() == null) {
							vis.setVisDataFiscalizacao(null);}
						else {
							vis.setVisDataFiscalizacao(dpDataFiscalizacao.getEditor().getText());
						}
						
						if (dpDataCriacaoAto.getValue() == null) {
							vis.setVisDataCriacao(null);}
						else {
							vis.setVisDataCriacao(dpDataCriacaoAto.getEditor().getText());
						}
						
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
						obsList.add(visTab);
						tvVistoria.setItems(obsList);
						
						selecionarVistoria();
						modularBotoes();
						fecharEditorHTML();
						
						
						//-- Alerta de interferência editada --//
						Alert a = new Alert (Alert.AlertType.INFORMATION);
						a.setTitle("Parabéns!");
						a.setContentText("Cadastro salvo com sucesso!!!");
						a.setHeaderText(null);
						a.show();
						
						
						//-- número da vistoria para a tabela atos --//
						
						visGeral = vis;
						main.pegarVistoria(vis);
		}
		}
		
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
			
			abrirEditorHTML();
			abrirCheckBox();
			
		} else {
			
			if (dpDataFiscalizacao == null  ||
					dpDataCriacaoAto == null
					
					) {
				
				Alert aLat = new Alert (Alert.AlertType.ERROR);
				aLat.setTitle("Alerta!!!");
				aLat.setContentText("Informe: Data da Fiscalização e Data de Criação do Ato!!!");
				aLat.setHeaderText(null);
				aLat.show();
				
			} else {
		
				try {
				
				VistoriaTabela visTab = tvVistoria.getSelectionModel().getSelectedItem();
				
				Vistoria vis = new Vistoria(visTab);
				
					//-- capturar endereço relacionado --//
					vis.setVisEndCodigoFK(eGeralVis);
				
					//-- capturar tela --//
					vis.setVisIdentificacao(tfAto.getText());
					vis.setVisSEI(tfAtoSEI.getText());
						
					if (dpDataFiscalizacao.getValue() == null) {
						vis.setVisDataFiscalizacao(null);}
					else {
						vis.setVisDataFiscalizacao(dpDataFiscalizacao.getEditor().getText());
					}
					
					if (dpDataCriacaoAto.getValue() == null) {
						vis.setVisDataCriacao(null);}
					else {
						vis.setVisDataCriacao(dpDataCriacaoAto.getEditor().getText());
					}
					
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
							
							obsList.remove(visTab);
							
							visTab = new VistoriaTabela(
									
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
									
							obsList.add(visTab);
							tvVistoria.setItems(obsList);
									
							selecionarVistoria();
							
							modularBotoes();
							fecharEditorHTML();
							
							//-- Alerta de interferência editada --//
							Alert a = new Alert (Alert.AlertType.INFORMATION);
							a.setTitle("Parabéns!");
							a.setContentText("Vistoria editada!");
							a.setHeaderText(null);
							a.show();
							
							visGeral = vis;
							main.pegarVistoria(vis);
				
				
						} catch (Exception e) {
							
							System.out.println("Erro ao editar: " + e);
							
							//-- Alerta de interferência editada --//
							Alert a = new Alert (Alert.AlertType.ERROR);
							a.setTitle("Atenção!"); 
							a.setContentText(e.toString());
							a.setHeaderText("Erro ao editar vistoria!");
							a.show();
							
						}
			
			}
			
		}
		
	}

	public void btnExcluirHab (ActionEvent event) {
		
		try {
		
			VistoriaTabela visTab = tvVistoria.getSelectionModel().getSelectedItem();
			VistoriaDao visDao = new VistoriaDao();
			
				try {
					
					visDao.removerVistoria(visTab.getVisCodigo());
					obsList.remove(visTab);
					tvVistoria.setItems(obsList);
					
					selecionarVistoria();
					
					modularBotoes();
					fecharEditorHTML();
					
					//-- Alerta de interferência editada --//
					Alert a = new Alert (Alert.AlertType.INFORMATION);
					a.setTitle("Parabéns!");
					a.setContentText("Cadastro excluído!");
					a.setHeaderText(null);
					a.show();
					
					}	catch (JDBCConnectionException eJDBC) {
							
							Alert a = new Alert (Alert.AlertType.ERROR);
							a.setTitle("Atenção!");
							a.setContentText("erro ao excluir o cadastro!!!");
							a.setHeaderText("jdbc" + eJDBC.toString());
							a.show();
					}
				
			}	catch (Exception e) {
					
				Alert a = new Alert (Alert.AlertType.ERROR);
				a.setTitle("Atenção!");
				a.setContentText("erro ao excluir o cadastro!!!");
				a.setHeaderText(e.toString());
				a.show();
			}
			
}

	
	public void btnCancelarHab (ActionEvent event) {
		
		modularBotoes();
		fecharEditorHTML();
		
	}
	
	public void btnPesquisarHab (ActionEvent event) {
		try {
		strPesquisa = tfPesquisar.getText();
		listarVistorias(strPesquisa);
		selecionarVistoria();
		fecharEditorHTML();
		modularBotoes();
		
		
		}
		
			catch (Exception e) {
				Alert aLat = new Alert (Alert.AlertType.ERROR);
				aLat.setTitle("Alerta!!!");
				aLat.setContentText("Erro de conexão!!!" + "[ " + e + " ]");
				aLat.setHeaderText(null);
				aLat.show();
			}
		
	}
	
	String strData;
	String strEndereco;
	
	public void btnPesqObjHab (ActionEvent event) {
		
		try {
			strData = formatter.format(dpDataFiscalizacao.getValue());
		} catch (Exception e) {
			strData = "DATA";
		}
		
		
		try {
			strEndereco = eGeralVis.getDesc_Endereco();
		} catch (Exception e) {
			strEndereco = "ENDEREÇO";
		}
		
		//e se a vistoria for salva pela primeira vez? Ainda  não há as informaçoes visGeral
				
		String objeto = "<p>Em atendimento ao MEMORANDO... foi realizada vistoria no dia "
				+ strData 
				+ ", para verifica&ccedil;&atilde;o de DESCRIÇÃO DENÚNCIA, no endereço: " + strEndereco + ".";
				;
		
		htmlObjeto.setHtmlText(objeto);
		
	}
	
	public void btnPesApHab (ActionEvent event) {
		
		String apresentacao = "A vistoria ocorreu em " + strData + ", por volta das HORAS, "
				+ "e contou com a presen&ccedil;a do(s) t&eacute;cnico(s) "
				+ "TECNICO e do respons&aacute;vel pela propriedade USUÁRIO.";
		
		htmlApresentacao.setHtmlText(apresentacao);
		
	}

	public void btnAjudaRelatorioHab (ActionEvent event) {
	
		Pane legPane = new Pane();
    	relCon = new RelatosController();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TabRelatos.fxml"));
		loader.setRoot(legPane);
		loader.setController(relCon);
		
		try {
			loader.load();
		} catch (IOException e) {
			System.out.println("erro leitura do pane - chamada legislação");
			e.printStackTrace();
		}
		
		Scene scene = new Scene(legPane);
		Stage stage = new Stage(); // StageStyle.UTILITY - tirei para ver como fica, se aparece o minimizar
		stage.setWidth(960);
		stage.setHeight(600);
        stage.setScene(scene);
        stage.setMaximized(false);
        stage.setResizable(false);
        stage.setAlwaysOnTop(true); 
        stage.show();
	}
	
	
	String htmlVistoria = "";
	String htmlRel = "";
	
	public void btnRelatorioHab (ActionEvent event) {
		
		
		htmlRel = (String) engVistoria.executeScript("document.documentElement.outerHTML");
		
		Document docHtml = null;
		
		docHtml = Jsoup.parse(htmlRel, "UTF-8").clone();
		
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
		
		//System.out.println("usuario para imprimir relatorio abaixo: ");
		//System.out.println(endereco.getListUsuarios().get(0).getUsNome());
		
		session.close();
		
		//-- preencher a vistoria --//
		docHtml.select("nRela").prepend(visGeral.getVisIdentificacao());
		try {docHtml.select("nRelSEI").prepend(visGeral.getVisSEI());} catch (Exception e) {docHtml.select("nRelSEI").prepend("");};
	
		if (endereco.getListUsuarios() != null) {
			
			//-- usuario atraves do endereco --//
			
			try {docHtml.select("nomeUs").prepend(endereco.getListUsuarios().get(0).getUsNome());}
			
				catch (Exception e) {
					Alert a = new Alert (Alert.AlertType.ERROR);
					a.setTitle("Alerta!!!");
					a.setContentText("Não há usuário cadastrado!!!");
					a.setHeaderText(null);
					a.show();
					
				};
			
			
			docHtml.select("cpfUs").prepend(endereco.getListUsuarios().get(0).getUsCPFCNPJ());
			docHtml.select("endUs").prepend(endereco.getListUsuarios().get(0).getUsDescricaoEnd());
			
			try {docHtml.select("raUs").prepend(endereco.getListUsuarios().get(0).getUsRA());}catch (Exception e) {docHtml.select("raUs").prepend("");};
			
			// endereco do usuario atraves do endereco //
			docHtml.select("cepUs").prepend(endereco.getListUsuarios().get(0).getUsCEP());
			docHtml.select("cidUs").prepend(endereco.getListUsuarios().get(0).getUsCidade());
			try {docHtml.select("ufUs").prepend(endereco.getListUsuarios().get(0).getUsEstado());}catch (Exception e){docHtml.select("ufUs").prepend("");};
			docHtml.select("telUs").prepend(endereco.getListUsuarios().get(0).getUsTelefone());
			docHtml.select("celUs").prepend(endereco.getListUsuarios().get(0).getUsCelular());
			docHtml.select("emailUs").prepend(endereco.getListUsuarios().get(0).getUsEmail());
			
			// catch (Exception e){};
			// endereco do empreedimento //  
			 
			docHtml.select("EndEmpDes").prepend(endereco.getDesc_Endereco());
			try { docHtml.select("EndEmpRA").prepend(endereco.getRA_Endereco());} catch (Exception e){docHtml.select("EndEmpRA").prepend("");};
			docHtml.select("EndEmpCep").prepend(endereco.getCEP_Endereco());
			docHtml.select("EndEmpCid").prepend(endereco.getCid_Endereco());
			try { docHtml.select("EndEmpUF").prepend(endereco.getUF_Endereco());} catch (Exception e){docHtml.select("EndEmpUF").prepend("");};
			
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
			
			//System.out.println("quantidade de interferencias " + endereco.getListInterferencias().size());
			
			for (int i  = 0; i< endereco.getListInterferencias().size(); i++) {
				
				String strTipo, strBacia, strUH,strCorpo, strLat, strLng;
				
				try{strTipo = endereco.getListInterferencias().get(i).getInter_Tipo();
					}catch(Exception e) {strTipo = "";};
					
						if (endereco.getListInterferencias().get(i).getInter_Bacia() != null) {
							strBacia = endereco.getListInterferencias().get(i).getInter_Bacia();
							} else {
							strBacia = "";	
							};
						
							try{strUH = endereco.getListInterferencias().get(i).getInter_UH();
								}catch(Exception e) {strUH = "";};
								
									try{strCorpo = endereco.getListInterferencias().get(i).getInter_Corpo_Hidrico();
										}catch(Exception e) {strCorpo = "";};
										
											try{strLat = endereco.getListInterferencias().get(i).getInter_Lat().toString();
												}catch(Exception e) {strLat = "";};
												try{strLng = endereco.getListInterferencias().get(i).getInter_Lng().toString();
													}catch(Exception e) {strLng = "";};
					
				
			
				docHtml.select("interEnd").prepend(
							"<ul>"
						+	"<li>Tipo: " + strTipo
						+ 	", Bacia: " + strBacia
						+ 	", UH: " + strUH
						+ 	", Corpo Hídrico: " + strCorpo
						+	"<ul>"
						+	"<li>Coordenadas: " + strLat + "," + strLng + "</li>"
						+	"</ul>"
						+		"</li>"
						+			"</ul>"
					);
					
			}
			
			
		}
	
		//-- infrações --//
		for (int i = 0; i<infrArray.length; i++) {
			
			if (infrArray[i].equals("1") ) {
				docHtml.select("infrRel").append("<p>" + infraIncisos[0]);
			}
			if (infrArray[i].equals("2") ) {
				docHtml.select("infrRel").append("<p>" + infraIncisos[1]);
			}
			if (infrArray[i].equals("3")  ) {
				docHtml.select("infrRel").append("<p>" + infraIncisos[2]);
			}
			if (infrArray[i].equals("4") ) {
				docHtml.select("infrRel").append("<p>" + infraIncisos[3]);
			}
			if (infrArray[i].equals("5")  ) {
				docHtml.select("infrRel").append("<p>" + infraIncisos[4]);
			}
			if (infrArray[i].equals("6") ) {
				docHtml.select("infrRel").append("<p>" + infraIncisos[5]);
			}
			if (infrArray[i].equals("7")  ) {
				docHtml.select("infrRel").append("<p>" + infraIncisos[6]);
			}
			
		}


		
		//-- atenuantes --//
		for (int i = 0; i<atenArray.length; i++) {
			
			if (atenArray[i].equals("1") ) {
				docHtml.select("atenRel").append("<p>" + atenIncisos[0]);
			}
			if (atenArray[i].equals("2") ) {
				docHtml.select("atenRel").append("<p>" + atenIncisos[1]);
			}
			if (atenArray[i].equals("3")  ) {
				docHtml.select("atenRel").append("<p>" + atenIncisos[2]);
			}
			if (atenArray[i].equals("4") ) {
				docHtml.select("atenRel").append("<p>" + atenIncisos[3]);
			}
			if (atenArray[i].equals("5")  ) {
				docHtml.select("atenRel").append("<p>" + atenIncisos[4]);
			}
			if (atenArray[i].equals("6") ) {
				docHtml.select("atenRel").append("<p>" + atenIncisos[5]);
			}
			if (atenArray[i].equals("7")  ) {
				docHtml.select("atenRel").append("<p>" + atenIncisos[6]);
			}
			if (atenArray[i].equals("8")  ) {
				docHtml.select("atenRel").append("<p>" + atenIncisos[7]);
			}
			if (atenArray[i].equals("9")  ) {
				docHtml.select("atenRel").append("<p>" + atenIncisos[8]);
			}
			
		}

		
		//tem que colocar um  if
		for (int i = 0; i<agraArray.length; i++) {
			if (agraArray[i].equals("a") ) {
				docHtml.select("agraRel").append("<p>" + agraIncisos[0]);
			}
			if (agraArray[i].equals("b") ) {
				docHtml.select("agraRel").append("<p>" + agraIncisos[1]);
			}
			if (agraArray[i].equals("c")  ) {
				docHtml.select("agraRel").append("<p>" + agraIncisos[2]);
			}
			if (agraArray[i].equals("d") ) {
				docHtml.select("agraRel").append("<p>" + agraIncisos[3]);
			}
			if (agraArray[i].equals("e")  ) {
				docHtml.select("agraRel").append("<p>" + agraIncisos[4]);
			}
			if (agraArray[i].equals("f") ) {
				docHtml.select("agraRel").append("<p>" + agraIncisos[5]);
			}
			if (agraArray[i].equals("g")  ) {
				docHtml.select("agraRel").append("<p>" + agraIncisos[6]);
			}
			if (agraArray[i].equals("h")  ) {
				docHtml.select("agraRel").append("<p>" + agraIncisos[7]);
			}
			if (agraArray[i].equals("i")  ) {
				docHtml.select("agraRel").append("<p>" + agraIncisos[8]);
			}
			if (agraArray[i].equals("j")  ) {
				docHtml.select("agraRel").append("<p>" + agraIncisos[9]);
			}
			if (agraArray[i].equals("k")  ) {
				docHtml.select("agraRel").append("<p>" + agraIncisos[10]);
			}
			if (agraArray[i].equals("l")  ) {
				docHtml.select("agraRel").append("<p>" + agraIncisos[11]);
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
		stage.setWidth(1150);
		stage.setHeight(750);
        stage.setScene(scene);
        stage.setMaximized(false);
        stage.setResizable(false);
        
        stage.show();
        
        TabNavegadorController.html = html;
        TabNavegadorController.numIframe = 1;
		
	}
	
	public void btnRecomendacoesHab (ActionEvent event) {
	
	String recomendacao = "Solicitar, no prazo de 60 (sessenta) dias contados a partir do recebimento do Termo de Notificação, a outorga de "
			+ "direito de usos de recursos hídricos, de acordo com a Resolução/Adasa n° 350, de 23 de junho de 2006.";

	htmlRecomendacao.setHtmlText(recomendacao);

	}
	
	WebView webVistoria;
	WebEngine engVistoria;
		
	//-- INITIALIZE --//
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
		tcNumero.setCellValueFactory(new PropertyValueFactory<VistoriaTabela, String>("visIdentificacao"));  // visIdentificacao
		tcSEI.setCellValueFactory(new PropertyValueFactory<VistoriaTabela, String>("visSEI"));  
		tcData.setCellValueFactory(new PropertyValueFactory<VistoriaTabela, String>("visDataFiscalizacao")); 
		
		tfPesquisar.setOnKeyReleased(event -> {
	  		  if (event.getCode() == KeyCode.ENTER){
	  		     btnPesquisar.fire();
	  		  }
	  		});
	        
		
		modularBotoes();
		
		/*
		dpDataFiscalizacao.setConverter(new StringConverter<LocalDate>() {
			
			@Override
			public String toString(LocalDate t) {
				if (t != null) {
					return formatter.format(t);
				}
				return "";
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
				return "";
			}
			
			@Override
			public LocalDate fromString(String string) {
				if (string != null && !string.trim().isEmpty()) {
					return LocalDate.parse(string, formatter);
				}
				return null;
			}

		});
		*/
		
		/*
		dpDataFiscalizacao.setOnAction((ActionEvent event) -> {
			
		}
				
		);
		*/
		
		
		ObterArtigos ();
		
		// -- inicitalizar o mapa -- //
				Platform.runLater(() ->{
				relatarHTML();
				fecharEditorHTML();
				
				});
				
		btnRelatorio.setGraphic(new ImageView(imgRelatorio));
		
		// -- inicitalizar o mapa -- //
		Platform.runLater(() ->{
			webVistoria = new WebView();
			engVistoria = webVistoria.getEngine();
			engVistoria.load(getClass().getResource("/html/relatorioVistoria.html").toExternalForm()); 
		 
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
		
			htmlRelato.setPrefSize(800, 673);
			
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
	
	ObservableList<VistoriaTabela> obsList;
	
	public void listarVistorias (String strPesquisa) {
 		
	 	// --- conexão - listar endereços --- //
		VistoriaDao visDao = new VistoriaDao();
		List<Vistoria> visList = visDao.listarVistoria(strPesquisa);
		obsList = FXCollections.observableArrayList();
		
		
		if (!obsList.isEmpty()) {
			obsList.clear();
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
				
				
				obsList.add(visTab);
				
					
		}
		
			tvVistoria.setItems(obsList);
			
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
					tfAtoSEI.setText(visTab.getVisSEI());
					
					if (visTab.getVisDataFiscalizacao() == null) {
						dpDataFiscalizacao.getEditor().clear();
		 				} else {
		 					dpDataFiscalizacao.getEditor().setText(visTab.getVisDataFiscalizacao());
		 				}
	 				
	 				if (visTab.getVisDataCriacao() == null) {
	 					dpDataCriacaoAto.getEditor().clear();
		 				} else {
		 					dpDataCriacaoAto.getEditor().setText(visTab.getVisDataCriacao());
		 				}
	 				
	 				//dpDataFiscalizacao.setValue(LocalDate.parse(visTab.getVisDataFiscalizacao(), formatter));
					
					String infr =  visTab.getVisInfracoes();
					String pena = visTab.getVisPenalidades();
					String agra = visTab.getVisAgravantes();
					String aten = visTab.getVisAtenuantes();
					
					LimparCheckBox();
					
					//System.out.println(htmlObjeto.getHtmlText());
					
					//-- infrações --//
					if (infr != null) {
						
						infrArray = infr.split("");
							
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
	 	
	 	
	 public void fecharEditorHTML () {
		 htmlObjeto.setDisable(true);
		 htmlApresentacao.setDisable(true);
		 htmlRelato.setDisable(true);
		 htmlRecomendacao.setDisable(true);
	 }
	 
	 public void abrirEditorHTML () {
		 htmlObjeto.setDisable(false);
		 htmlApresentacao.setDisable(false);
		 htmlRelato.setDisable(false);
		 htmlRecomendacao.setDisable(false);
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
		 
		 fecharCheckBox ();
	 }
	 
	 public void fecharCheckBox () {
		 
		 checkInfra1.setDisable(true);
		 checkInfra2.setDisable(true);
		 checkInfra3.setDisable(true);
		 checkInfra4.setDisable(true);
		 checkInfra5.setDisable(true);
		 checkInfra6.setDisable(true);
		 checkInfra7.setDisable(true);
		 
		 checkPena1.setDisable(true);
		 checkPena2.setDisable(true);
		 checkPena3.setDisable(true);
		 checkPena4.setDisable(true);
		 checkPena5.setDisable(true);
		 checkPena6.setDisable(true);
		 checkPena7.setDisable(true);
		 
		 checkAten1.setDisable(true);
		 checkAten2.setDisable(true);
		 checkAten3.setDisable(true);
		 checkAten4.setDisable(true);
		 checkAten5.setDisable(true);
		 checkAten6.setDisable(true);
		 checkAten7.setDisable(true);
		 checkAten8.setDisable(true);
		 checkAten9.setDisable(true);
		 
		 checkAgra1.setDisable(true);
		 checkAgra2.setDisable(true);
		 checkAgra3.setDisable(true);
		 checkAgra4.setDisable(true);
		 checkAgra5.setDisable(true);
		 checkAgra6.setDisable(true);
		 checkAgra7.setDisable(true);
		 checkAgra8.setDisable(true);
		 checkAgra9.setDisable(true);
		 checkAgra10.setDisable(true);
		 checkAgra11.setDisable(true);
		 checkAgra12.setDisable(true);
		 
	 }
	 
	 public void abrirCheckBox () {
		 
		 checkInfra1.setDisable(false);
		 checkInfra2.setDisable(false);
		 checkInfra3.setDisable(false);
		 checkInfra4.setDisable(false);
		 checkInfra5.setDisable(false);
		 checkInfra6.setDisable(false);
		 checkInfra7.setDisable(false);
		 
		 checkPena1.setDisable(false);
		 checkPena2.setDisable(false);
		 checkPena3.setDisable(false);
		 checkPena4.setDisable(false);
		 checkPena5.setDisable(false);
		 checkPena6.setDisable(false);
		 checkPena7.setDisable(false);
		 
		 checkAten1.setDisable(false);
		 checkAten2.setDisable(false);
		 checkAten3.setDisable(false);
		 checkAten4.setDisable(false);
		 checkAten5.setDisable(false);
		 checkAten6.setDisable(false);
		 checkAten7.setDisable(false);
		 checkAten8.setDisable(false);
		 checkAten9.setDisable(false);
		 
		 checkAgra1.setDisable(false);
		 checkAgra2.setDisable(false);
		 checkAgra3.setDisable(false);
		 checkAgra4.setDisable(false);
		 checkAgra5.setDisable(false);
		 checkAgra6.setDisable(false);
		 checkAgra7.setDisable(false);
		 checkAgra8.setDisable(false);
		 checkAgra9.setDisable(false);
		 checkAgra10.setDisable(false);
		 checkAgra11.setDisable(false);
		 checkAgra12.setDisable(false);
		 
	 }

	 public void LimparCheckBox () {
		 
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
			
				Scene scene = new Scene(listView);
				Stage stage = new Stage(); // StageStyle.UTILITY - tirei para ver como fica, se aparece o minimizar
				stage.setWidth(964);
				stage.setHeight(210);
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
	                      conteudo.putHtml(new_val);
	                      String artigo = (String) conteudo.getString();
	                      conteudo.putString(artigo);
	                      clip.setContent(conteudo);
	                      
	                 }
             });
             
		 
	 }
	 public void btnPenalidadesHab (ActionEvent event) {
		 
		 ObservableList<String> documentos = FXCollections.observableArrayList(penaIncisos);
			ListView<String> listView = new ListView<String>(documentos);
			
				Scene scene = new Scene(listView);
				Stage stage = new Stage(); // StageStyle.UTILITY - tirei para ver como fica, se aparece o minimizar
				stage.setWidth(964);
				stage.setHeight(210);
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
                   conteudo.putHtml(new_val);
                   String artigo = (String) conteudo.getString();
                   conteudo.putString(artigo);
                   clip.setContent(conteudo);
                   
              }
          });
	 }
	 
	 public void btnAtenuantesHab (ActionEvent event) {
		 
		 ObservableList<String> documentos = FXCollections.observableArrayList(atenIncisos);
			ListView<String> listView = new ListView<String>(documentos);
			
				Scene scene = new Scene(listView);
				Stage stage = new Stage(); // StageStyle.UTILITY - tirei para ver como fica, se aparece o minimizar
				stage.setWidth(964);
				stage.setHeight(250);
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
                  conteudo.putHtml(new_val);
                  String artigo = (String) conteudo.getString();
                  conteudo.putString(artigo);
                  clip.setContent(conteudo);
              }
          });
          
         
	 
	 
	 }
	 public void btnAgravantesHab (ActionEvent event) {
		
		 
		 ObservableList<String> documentos = FXCollections.observableArrayList(agraIncisos);
			ListView<String> listView = new ListView<String>(documentos);
			
				
				Scene scene = new Scene(listView);
				Stage stage = new Stage(); // StageStyle.UTILITY - tirei para ver como fica, se aparece o minimizar
				stage.setWidth(964);
				stage.setHeight(310);
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
                  conteudo.putHtml(new_val);
                  String artigo = (String) conteudo.getString();
                  conteudo.putString(artigo);
                  clip.setContent(conteudo);
            	  
              }
          });
         
	 }
	 
	 public void ObterArtigos () {
		 
		 	//-- infrações  --//
			infraIncisos = new String [7];
			
		
			infraIncisos [0] = "I - derivar ou utilizar recursos hídricos para qualquer finalidade, sem a respectiva " + 
					"outorga de direito de uso;";
			
			
			infraIncisos [1] = "II - implantar ou iniciar a implantação de empreendimento que exija derivação ou " + 
					"utilização de recursos hídricos, superficiais ou subterrâneos que implique alterações no regime," + 
					"quantidade ou qualidade dos mesmos, sem a autorização dos órgãos ou entidades competentes;";
			
			infraIncisos [2] = "III - utilizar-se de recursos hídricos ou executar obras ou serviços relacionados com os " + 
					"mesmos em desacordo com as condições estabelecidas na outorga;";
			
			infraIncisos [3] = "IV - perfurar poços para extração de água subterrânea ou operá-los sem a devida " + 
					"autorização;";
			
			infraIncisos [4] = "V - fraudar as medições dos volumes d’água utilizados ou declarar valores diferentes " + 
					"dos medidos;";
			
			infraIncisos [5] = "VI - infringir normas estabelecidas nos regulamentos da legislação vigente e " + 
					"superveniente e nos regulamentos administrativos, inclusive em resoluções, instruções e procedimentos " + 
					"fixados pelos órgãos ou entidades competentes;";
			
			infraIncisos [6] = "VII - obstar ou dificultar a ação fiscalizadora das autoridades competentes, no exercício " + 
					"de suas funções;";
			
			penaIncisos = new String [7];
			
			penaIncisos [0] = "Infração LEVE: Multa no valor base de R$ 400,00 (quatrocentos reais)";
					
			penaIncisos [1] = "Infração LEVE: Multa no valor base de R$ 1.000,00 (um mil reais)";
			
			penaIncisos [2] = "Infração GRAVE: Multa no valor base de R$ 10.001 (dez mil e um reais)";
			
			penaIncisos [3] = "Infração LEVE: Multa no valor base de R$ 1.000,00 (um mil reais)";
			
			penaIncisos [4] = "Infração GRAVE: Multa no valor base de R$ 10.001 (dez mil e um reais)";
											
			penaIncisos [5] = "Infração GRAVE: Multa no valor base de R$ 10.001 (dez mil e um reais)";
													
			penaIncisos [6] = "Infração LEVE: Multa no valor base de R$ 600,00 (seiscentos reais)";
			
			//-- atenuantes --//
			atenIncisos = new String [9];
			
			
			atenIncisos [0] = "I - baixo grau de instrução ou escolaridade do usuário dos recursos hídricos;";
			
			atenIncisos [1] = "II - arrependimento do usuário, manifestado pela espontânea reparação do dano ou pela " + 
					"mitigação significativa da degradação causada aos recursos hídricos;";
			
			atenIncisos [2] = "III - comunicação prévia, pelo usuário, de perigo iminente de degradação dos recursos " + 
					"hídricos;";
			
			atenIncisos [3] = "IV - oficialização do comprometimento do usuário em sanar as irregularidades e reparar " + 
					"os danos delas decorrentes;";
			
			atenIncisos [4] = "V - colaboração explícita com a fiscalização;";
			
			atenIncisos [5] = "VI - tratando-se de usuário não outorgado, haver espontaneamente procurado a Agência " + 
					"para regularização do uso dos recursos hídricos;";
			
			atenIncisos [6] = "VII - atendimento a todas as recomendações e exigências, nos prazos fixados pela " + 
					"Agência;";
			
			atenIncisos [7] = "VIII - reconstituição dos recursos hídricos degradados ou sua recomposição na forma " + 
					"exigida;";
			
			atenIncisos [8] = "IX - não ter sido autuado por infração nos últimos 5 (cinco) anos anteriores ao fato.";
			
			
			//-- agravantes --//
			agraIncisos = new String [12];
			
			agraIncisos [0] = "a) para obter vantagem pecuniária;";
			
			agraIncisos [1] = "b) mediante coação de outrem para a sua execução material;";
			
			agraIncisos [2] = "c) com implicações graves à saúde pública ou ao meio ambiente, em especial aos " + 
					"recursos hídricos;";
			
			agraIncisos [3] = "d) que atinja áreas de unidades de conservação ou áreas sujeitas, por ato do Poder " + 
					"Público, a regime especial de uso;";
			
			agraIncisos [4] = "e) que atinja áreas urbanas ou quaisquer assentamentos humanos;";
			
			agraIncisos [5] = "f) em época de racionamento do uso de água ou em condições sazonais adversas ao seu " + 
					"uso;";
			
			agraIncisos [6] = "g) mediante fraude ou abuso de confiança;";
			
			agraIncisos [7] = "h) mediante abuso do direito de uso do recurso hídrico;";
			
			agraIncisos [8] = "i) em favor do interesse de pessoa jurídica mantida total ou parcialmente por recursos " + 
					"públicos ou beneficiada por incentivos fiscais;";
			
			agraIncisos [9] = "j) sem proceder à reparação integral dos danos causados;";
			
			agraIncisos [10] = "k) que tenha sido facilitada por funcionário público no exercício de suas funções;";
			
			agraIncisos [11] = "l) mediante fraude documental;";
		 
		 
	 }
	
}



//File file = null;
		//file = new File (TabVistoriaController.class.getResource("/html/relatorioVistoria.html").getFile());
		
		/*
		WebView webView = new WebView(); 
		WebEngine eng = webView.getEngine(); 
		eng.load(getClass().getResource("/html/relatorioVistoria.html").toExternalForm()); 
		*/
		
		/*
      eng.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>(){ 

              public void changed(final ObservableValue<? extends Worker.State> observableValue, 

                                  final Worker.State oldState, 
                                  final Worker.State newState) 

              { 
              	if (newState == Worker.State.SUCCEEDED){  
              		htmlRel = (String) eng.executeScript("document.documentElement.outerHTML"); 
              		
              	} 
              	
             } 
      }); 
      */
		