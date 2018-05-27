package controllers;


import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import dao.DenunciaDao;
import entity.Denuncia;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import tabela.DenunciaTabela;


public class TabDenunciaController implements Initializable {
	
	// --- Controller Principal - MainController --- //
	@FXML private MainController main;
	
	@FXML AnchorPane tabDenuncia = new AnchorPane();
	
	@FXML TextField tfDocumento = new TextField();
	@FXML TextField tfDocSei = new TextField();
	@FXML TextField tfProcSei =  new TextField();
	@FXML TextField tfResDen = new TextField();
	@FXML TextField tfPesquisar = new TextField();
	
	// ----- Label - endereco da denuncia ------ //
	@FXML Label lblDenEnd = new Label ();

	@FXML Button btnNovo = new Button();
	@FXML Button btnSalvar = new Button();
	@FXML Button btnEditar = new Button();
	@FXML Button btnExcluir = new Button();
	@FXML Button btnCancelar = new Button();
	@FXML Button btnPesquisar = new Button();
	@FXML Button btnSair = new Button();
	
	// -- Tabela --  //
	@FXML private TableView <DenunciaTabela> tvLista;
	
	// -- Colunas -- //
	@FXML private TableColumn<DenunciaTabela, String> tcDocumento;
	@FXML private TableColumn<DenunciaTabela, String> tcDocSEI;
	@FXML private TableColumn<DenunciaTabela, String> tcProcSEI;
	
	// capturar denuncia para a TabEnderecoController
	private static Denuncia dGeral;
	
	// --- String para primeira pesquisa --- //
	String strPesquisa = "";
	
		
	// --- m�todo listarDenuncias --- //
	public void listarDenuncias (String strPesquisa) {
		
		// -- Conexão e pesquisa de den�ncias -- //
		DenunciaDao denunciaDao = new DenunciaDao();	//passar classe
		List<Denuncia> denunciaList = denunciaDao.listDenuncia(strPesquisa); //passar string de pesquisar
		ObservableList<DenunciaTabela> obsListDenunciaTabela = FXCollections.observableArrayList(); //chamar observable list e outra classe

		
		if (!obsListDenunciaTabela.isEmpty()) {
			obsListDenunciaTabela.clear();
		}
		for (Denuncia denuncia : denunciaList) {
			
			DenunciaTabela denTab = new DenunciaTabela(
					denuncia.getCod_Denuncia(), 
					denuncia.getDoc_Denuncia(),
					denuncia.getDoc_SEI_Denuncia(), 
					denuncia.getProc_SEI_Denuncia(),
					denuncia.getDesc_Denuncia(),
					// adiciona o objeto enderecoFK na DenunciaTabela
					denuncia.getEnderecoFK()
					);
			
				obsListDenunciaTabela.add(denTab);
		}
		
		tcDocumento.setCellValueFactory(new PropertyValueFactory<DenunciaTabela, String>("Doc_Denuncia")); 
        tcDocSEI.setCellValueFactory(new PropertyValueFactory<DenunciaTabela, String>("Doc_SEI_Denuncia")); 
        tcProcSEI.setCellValueFactory(new PropertyValueFactory<DenunciaTabela, String>("Proc_SEI_Denuncia")); 
        
        tvLista.setItems(obsListDenunciaTabela); 
	}
	
	// -- selecionar denúncia -- //
	public void selecionarDenuncia () {
		
		// TableView - selecionar denúncia ao clicar -- //
		tvLista.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
			public void changed(ObservableValue<?> observable , Object oldValue, Object newValue) {
			
			DenunciaTabela denTab = (DenunciaTabela) newValue;
				
			if (denTab == null) {
				
				tfDocumento.setText("");
				tfDocSei.setText("");
				tfProcSei.setText("");
				tfResDen.setText("");
				
				btnNovo.setDisable(true);
				btnSalvar.setDisable(true);
				btnEditar.setDisable(false);
				btnExcluir.setDisable(false);
				btnCancelar.setDisable(false);
				
			} else {

				// preencher os campos
				tfDocumento.setText(denTab.getDoc_Denuncia());
				tfDocSei.setText(denTab.getDoc_SEI_Denuncia());
				tfProcSei.setText(denTab.getProc_SEI_Denuncia());
				tfResDen.setText(denTab.getDesc_Denuncia());
				
				
				if (denTab.getenderecoObjetoTabelaFK() != null) {
					
					lblDenEnd.setText(denTab.getenderecoObjetoTabelaFK().getDesc_Endereco() + ", " + denTab.getenderecoObjetoTabelaFK().getRA_Endereco());
					lblDenEnd.setTextFill(Color.BLACK);
				} else {
					lblDenEnd.setText("Sem endereço cadastrado!");
					lblDenEnd.setTextFill(Color.RED);
					
				}
				
				Denuncia dGeral = new Denuncia(denTab);

				main.pegarDoc(dGeral);
				
				// habilitar e desabilitar botões
				btnNovo.setDisable(true);
				btnSalvar.setDisable(true);
				btnEditar.setDisable(false);
				btnExcluir.setDisable(false);
				btnCancelar.setDisable(false);
			}
			}
		});
	}
	
	// --  botão novo -- //
	public void btnNovoHabilitar (ActionEvent event) {
		
		tfDocumento.setText("");
		tfDocSei.setText("");
		tfProcSei.setText("");
		tfResDen.setText("");
		
		tfDocumento.setDisable(false);
		tfDocSei.setDisable(false);
		tfProcSei.setDisable(false);
		tfResDen.setDisable(false);
		
		btnSalvar.setDisable(false);
		btnEditar.setDisable(true);
		btnExcluir.setDisable(true);
		btnNovo.setDisable(true);
	}
	
	// -- botão salvar -- //
	public void btnSalvarSalvar (ActionEvent event) {
		
		try {
			
			Denuncia denuncia = new Denuncia();
			
			denuncia.setDoc_Denuncia(tfDocumento.getText()); 
			denuncia.setProc_SEI_Denuncia(tfProcSei.getText());
			denuncia.setDoc_SEI_Denuncia(tfDocSei.getText()); 
			denuncia.setDesc_Denuncia(tfResDen.getText());
			
			DenunciaDao dao = new DenunciaDao();
			
			dao.salvaDenuncia(denuncia);
			
			// pegar o valor, levar para o MainController  e depois para o label lblDoc no EnderecoController
			dGeral = denuncia;
			main.pegarDoc(dGeral);
			
			listarDenuncias(strPesquisa);
			selecionarDenuncia();
		
			modularBotoesInicial ();
			
			//-- Alerta de denúncia salva --//
			Alert aSalvo = new Alert (Alert.AlertType.CONFIRMATION);
			aSalvo.setTitle("Parabéns!");
			aSalvo.setContentText("A denúncia salva com sucesso!");
			aSalvo.setHeaderText(null);
			aSalvo.show();
				
			
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

	// -- botão editar -- //
	public void btnEditarHabilitar (ActionEvent event) {
			
		if (tfDocumento.isDisable()) {
			
			tfDocumento.setDisable(false);
			tfDocumento.setDisable(false);
			tfDocSei.setDisable(false);
			tfProcSei.setDisable(false);
			tfResDen.setDisable(false);
			
		} else {
			
			DenunciaTabela denunciaTabelaEditar = tvLista.getSelectionModel().getSelectedItem();
			
			Denuncia denunciaEditar = new Denuncia(denunciaTabelaEditar);
			
			denunciaEditar.setDoc_Denuncia(tfDocumento.getText());
			denunciaEditar.setDoc_SEI_Denuncia(tfDocSei.getText());
			denunciaEditar.setProc_SEI_Denuncia(tfProcSei.getText());
			denunciaEditar.setDesc_Denuncia(tfResDen.getText());
			
			DenunciaDao dDao = new DenunciaDao();
			
			dDao.mergeDenuncia(denunciaEditar);
			
			// pegar o valor, levar para o MainController  e depois para o label lblDoc no EnderecoController
			dGeral = denunciaEditar;
			main.pegarDoc(dGeral);
			
			listarDenuncias(strPesquisa); // acho que precisa de um  parametro strpesqusia aqui 
			selecionarDenuncia();
			
			modularBotoesInicial (); 
				
			}
	}
	
	// -- botão excluir -- //
	public void btnExcluirHabilitar (ActionEvent event) {
	
		DenunciaTabela denunciaExcluir = tvLista.getSelectionModel().getSelectedItem();
		
		int id = denunciaExcluir.getCod_Denuncia(); // buscar id para deletar
		
		DenunciaDao dDao = new DenunciaDao();
		
		dDao.removeDenuncia(id);
		
		listarDenuncias(strPesquisa);
		selecionarDenuncia();
		
		modularBotoesInicial (); 		
	}
			
	// -- botão cancelar -- //
	public void btnCancelarHabilitar (ActionEvent event) {
			
		modularBotoesInicial();
	}
		
	// -- botão pesquisar denúncia -- //
	public void btnPesquisarHabilitar (ActionEvent event) {
		
		strPesquisa = (String) tfPesquisar.getText();
		
		listarDenuncias(strPesquisa);
		selecionarDenuncia();
		
		modularBotoesInicial (); 
		
	}
	
	// -- INITIALIZE -- //
	public void initialize(URL url, ResourceBundle rb) {
		
        // --- habilitar e desabilitar botões ---- //
		modularBotoesInicial();
		
	}
		
	// -- método habilitar e desabilitar botões -- //
	private void modularBotoesInicial () {
			
			tfDocumento.setDisable(true);
			tfDocumento.setDisable(true);
			tfDocSei.setDisable(true);
			tfProcSei.setDisable(true);
			tfResDen.setDisable(true);
			btnSalvar.setDisable(true);
			btnEditar.setDisable(true);
			btnExcluir.setDisable(true);
			btnNovo.setDisable(false);
			
	}
	
	// -- Main initialize para transmitir variáveis para outros controllers -- //
	public void init(MainController mainController) {
			main = mainController;
	}

}

