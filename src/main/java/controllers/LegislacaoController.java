package controllers;

import java.net.URL;
import java.text.Format;
import java.util.List;
import java.util.ResourceBundle;

import dao.LegislacaoDao;
import entity.Legislacao;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.web.HTMLEditor;
import tabela.LegislacaoTabela;


public class LegislacaoController implements Initializable {
	
	@FXML Button btnNovo;
	@FXML Button btnSalvar;
	@FXML Button btnEditar;
	@FXML Button btnExcluir;
	@FXML Button btnCancelar;
	@FXML Button btnPesquisar;
	
	@FXML TextField tfDescricaoArtigo;
	
	@FXML TextField tfPesquisar;
	
	//-- TableView Endereco --//
			@FXML private TableView <LegislacaoTabela> tvLista;
			
			@FXML TableColumn<LegislacaoTabela, String> tcArtigo;
	
	@FXML HTMLEditor htmlLeg;
	
	LegislacaoTabela legTab;
	
	Legislacao legGeral = new Legislacao();
	
	String strPesquisa = "";
	
	public void btnNovoHab (ActionEvent event) {
		
		htmlLeg.setHtmlText("");
		
		btnNovo.setDisable(true);
		btnSalvar.setDisable(false);
		
		btnEditar.setDisable(true);
		btnExcluir.setDisable(true);
	
	}
	
	
	public void btnSalvarHab (ActionEvent event) {
		
		try {
			
			Legislacao leg = new Legislacao();
			
			leg.setLegisArtigo(htmlLeg.getHtmlText());
			leg.setDescArtigo(tfDescricaoArtigo.getText());
			
			LegislacaoDao legDao = new LegislacaoDao();
			
			legDao.salvarLegislacao(leg);//-- Alerta de denúncia salva --//
			
			// legislacao geral //
			legGeral = leg;
			
			
			Alert aSalvo = new Alert (Alert.AlertType.CONFIRMATION);
			aSalvo.setTitle("Parabéns!");
			aSalvo.setContentText("Artigo salvo com sucesso!");
			aSalvo.setHeaderText(null);
			aSalvo.show();
			
			listarLegislacao(strPesquisa);
			selecionarLegislacao();
			modularBotoes();
				
			
		} catch (Exception ex) {
			
			System.out.println("Erro: " + ex);
			ex.printStackTrace();
			
			//-- Alerta de denúncia salva --//
			Alert aMy = new Alert (Alert.AlertType.ERROR);
			aMy.setTitle("Alerta!!!");
			aMy.setContentText("erro na conexão, tente novamente!");
			aMy.setHeaderText(null);
			aMy.show();
		}
	}
	
	public void btnEditarHab (ActionEvent event) {
		
		LegislacaoTabela legTab = tvLista.getSelectionModel().getSelectedItem();
		
		Legislacao leg = new Legislacao(legTab);
		
		leg.setLegisArtigo(htmlLeg.getHtmlText());
		leg.setDescArtigo(tfDescricaoArtigo.getText());
		
		LegislacaoDao legDao = new LegislacaoDao();
		
		legDao.mergeLegislacao(leg);
		
		listarLegislacao(strPesquisa);
		selecionarLegislacao();
		modularBotoes();
		
		legGeral = leg;
		
	}
	
	public void btnExcluirHab (ActionEvent event) {
		
		LegislacaoTabela legTab = tvLista.getSelectionModel().getSelectedItem();
		
		LegislacaoDao legDao = new LegislacaoDao();
		
		legDao.removerLegislacao(legTab.getLegislacaoCodigo());
		
		listarLegislacao(strPesquisa);
		selecionarLegislacao();
		modularBotoes();
		
	}
	
	public void btnCancelarHab (ActionEvent event) {
		 
		modularBotoes();
	}
	
	
	public void btnPesquisarHab (ActionEvent event) {
		strPesquisa = tfPesquisar.getText();
		
		listarLegislacao(strPesquisa);
		selecionarLegislacao();
		
	}
	
	
	// INITIALIZE //
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
	
	}
	
	
//  metodo para listar interferencias  //
 	public void listarLegislacao (String strPesquisa) {
 	
	 	// --- conexão - listar endereços --- //
		LegislacaoDao legDao = new LegislacaoDao();
		List<Legislacao> legList = legDao.listLegislacao(strPesquisa);
		ObservableList<LegislacaoTabela> olLeg = FXCollections.observableArrayList();
		
		
		if (!olLeg.isEmpty()) {
			olLeg.clear();
		}
		
			for (Legislacao leg : legList) {
				
				LegislacaoTabela legTab = new LegislacaoTabela(
					
				leg.getLegislacaoCodigo(),
				leg.getDescArtigo(),
				leg.getLegisArtigo()
				
				);
				
			olLeg.add(legTab);
			
		}
		
		tcArtigo.setCellValueFactory(new PropertyValueFactory<LegislacaoTabela, String>("descricaoArtigo")); 
		tvLista.setItems(olLeg);
 	}
		
 	// método selecionar interferência //
  	public void selecionarLegislacao () {
 	
 		tvLista.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
 			
 			public void changed(ObservableValue<?> observable , Object oldValue, Object newValue) {
 			
 			legTab = (LegislacaoTabela) newValue;
 			
 			if (legTab == null) {
 				
 				btnNovo.setDisable(true);
 				btnSalvar.setDisable(true);
 				btnEditar.setDisable(false);
 				btnExcluir.setDisable(false);
 				btnCancelar.setDisable(false);
 				
 			} else {

 				// -- preencher os campos -- //
 				tfDescricaoArtigo.setText(legTab.getDescricaoArtigo());
 				htmlLeg.setHtmlText(legTab.getArtigo());
				
				//-- modular botoes --//
				btnNovo.setDisable(true);
				btnSalvar.setDisable(true);
				btnEditar.setDisable(false);
				btnExcluir.setDisable(false);
				btnCancelar.setDisable(false);
				
				Legislacao leg = new Legislacao(legTab);
				
				legGeral = leg;
				
				/*
				Clipboard clip = Clipboard.getSystemClipboard();
                ClipboardContent conteudo = new ClipboardContent();
                
                String html = legTab.getArtigo();
                html = (String) clip.getContent(DataFormat.PLAIN_TEXT);
                
                conteudo.putString(html);
                clip.setContent(conteudo);
                */
				
				Clipboard clip = Clipboard.getSystemClipboard();
                ClipboardContent conteudo = new ClipboardContent();
                conteudo.putHtml(htmlLeg.getHtmlText());
                String artigo = (String) conteudo.getString();
                conteudo.putString(artigo);
                clip.setContent(conteudo);
				
 				}
 			}
 		});
 		
 		
  	}
  	
  	public void modularBotoes () {
		
		btnSalvar.setDisable(true);
		btnEditar.setDisable(true);
		btnExcluir.setDisable(true);
		 
		btnNovo.setDisable(false);
	}

}
