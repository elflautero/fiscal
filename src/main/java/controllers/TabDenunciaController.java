package controllers;


import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
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
import javafx.scene.control.DatePicker;
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
	
	@FXML DatePicker dpDataDistribuicao = new DatePicker();
	@FXML DatePicker dpDataRecebimento = new DatePicker();
	
	// -- Tabela --  //
	@FXML private TableView <DenunciaTabela> tvLista;
	
	// -- Colunas -- //
	@FXML private TableColumn<DenunciaTabela, String> tcDocumento;
	@FXML private TableColumn<DenunciaTabela, String> tcDocSEI;
	@FXML private TableColumn<DenunciaTabela, String> tcProcSEI;
	
	@FXML DatePicker dpDoc;
	
	// capturar denuncia para a TabEnderecoController
	private static Denuncia dGeral;
	
	// --- String para primeira pesquisa --- //
	String strPesquisa = "";
	

	// formatação de datas //
	DateTimeFormatter formatter = new DateTimeFormatterBuilder()
			.parseCaseInsensitive()
			.append(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
			.toFormatter();
		
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
					denuncia.getDenunciaID(), 
					denuncia.getDenDocumento(),
					denuncia.getDenDocumentoSEI(), 
					denuncia.getDenProcessoSEI(),
					denuncia.getDenDescricao(),
					denuncia.getDenDataDistribuicao(),
					denuncia.getDenDataRecebimento(),
					
					denuncia.getDenEnderecoFK()
					);
			
				obsListDenunciaTabela.add(denTab);
		}
		
		tcDocumento.setCellValueFactory(new PropertyValueFactory<DenunciaTabela, String>("denDocumento")); 
        tcDocSEI.setCellValueFactory(new PropertyValueFactory<DenunciaTabela, String>("denDocumentoSEI")); 
        tcProcSEI.setCellValueFactory(new PropertyValueFactory<DenunciaTabela, String>("denProcessoSEI")); 
        
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
				
				dpDataRecebimento.getEditor().clear();
				dpDataDistribuicao.getEditor().clear();
				
				tfResDen.setText("");
				
				btnNovo.setDisable(true);
				btnSalvar.setDisable(true);
				btnEditar.setDisable(false);
				btnExcluir.setDisable(false);
				btnCancelar.setDisable(false);
				
				
			} else {

				// preencher os campos
				tfDocumento.setText(denTab.getDenDocumento());
				tfDocSei.setText(denTab.getDenDecumentoSEI());
				tfProcSei.setText(denTab.getDenProcessoSEI());
				
				if (denTab.getDenDataDistribuicao() == null) {
					dpDataDistribuicao.getEditor().clear();
 				} else {
 					dpDataDistribuicao.setValue(LocalDate.parse(denTab.getDenDataDistribuicao(), formatter));
 				}
 				
 				if (denTab.getDenDataRecebimento() == null) {
 					dpDataRecebimento.getEditor().clear();
 				} else {
 					dpDataRecebimento.setValue((LocalDate.parse(denTab.getDenDataRecebimento(), formatter)));
 				}
 				
 				System.out.println("selecionando denuncias, datas: ");
 				System.out.println(denTab.getDenDataDistribuicao());
 				System.out.println(denTab.getDenDataRecebimento());
 				
 				
 				
				tfResDen.setText(denTab.getDenDescricao());
				
				
				if (denTab.getDenEnderecoFK() != null) {
					
					lblDenEnd.setText(denTab.getDenEnderecoFK().getDesc_Endereco() + ", " + denTab.getDenEnderecoFK().getRA_Endereco());
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
		
		dpDataDistribuicao.getEditor().clear();
		dpDataRecebimento.getEditor().clear();
		
		tfResDen.setText("");
		
		dpDataDistribuicao.setDisable(false);
		dpDataRecebimento.setDisable(false);
		
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
			
			if (tfDocSei.getText().isEmpty()  ||
				tfProcSei.getText().isEmpty()	) 
			{
				
				Alert a = new Alert (Alert.AlertType.ERROR);
				a.setTitle("Alerta!!!");
				a.setContentText("Informe: Documento, Processo SEI!!!");
				a.setHeaderText(null);
				a.show();
				
			} else {
			
					Denuncia denuncia = new Denuncia();
					
					denuncia.setDenDocumento(tfDocumento.getText()); 
					
					denuncia.setDenDocumentoSEI(tfDocSei.getText()); 
					denuncia.setDenProcessoSEI(tfProcSei.getText());
					
					if (dpDataDistribuicao.getValue() == null) {
						denuncia.setDenDataDistribuicao(null);}
					else {
						denuncia.setDenDataDistribuicao(formatter.format(dpDataDistribuicao.getValue()));
						}
											
						if (dpDataRecebimento.getValue() == null) {
						denuncia.setDenDataRecebimento(null);}
					else {
						denuncia.setDenDataRecebimento(formatter.format(dpDataRecebimento.getValue()));
						}

					denuncia.setDenDescricao(tfResDen.getText());
					
					DenunciaDao dao = new DenunciaDao();
					
					dao.salvaDenuncia(denuncia);
					
					// pegar o valor, levar para o MainController  e depois para o label lblDoc no EnderecoController
					dGeral = denuncia;
					main.pegarDoc(dGeral);
					
					listarDenuncias(strPesquisa);
					selecionarDenuncia();
				
					modularBotoesInicial ();
					
					//-- Alerta de denúncia salva --//
					Alert a = new Alert (Alert.AlertType.INFORMATION);
					a.setTitle("Parabéns!!!");
					a.setContentText("Cadastro salvo com sucesso!!!");
					a.setHeaderText(null);
					a.show();
					
					}
			
		} catch (Exception ex) {
			
			System.out.println("Erro: " + ex);
			ex.printStackTrace();
			
			//-- Alerta de denúncia salva --//
			Alert a = new Alert (Alert.AlertType.ERROR);
			a.setTitle("Alerta!!!");
			a.setContentText("erro na conexão, tente novamente!");
			a.setHeaderText(null);
			a.show();
		}
		
	}

	// -- botão editar -- //
	public void btnEditarHabilitar (ActionEvent event) {
			
		if (tfDocumento.isDisable()) {
			
			tfDocumento.setDisable(false);
			tfDocumento.setDisable(false);
			tfDocSei.setDisable(false);
			tfProcSei.setDisable(false);
			
			dpDataDistribuicao.setDisable(false);
			dpDataRecebimento.setDisable(false);
			
			tfResDen.setDisable(false);
			
		} else {
			
			if (tfDocSei.getText().isEmpty() ||
					tfProcSei.getText().isEmpty()) 
				{
					
					Alert a = new Alert (Alert.AlertType.ERROR);
					a.setTitle("Alerta!!!");
					a.setContentText("Informe: Documento, Processo SEI!!!");
					a.setHeaderText(null);
					a.show();
					
				} else {
			
						DenunciaTabela denunciaTabelaEditar = tvLista.getSelectionModel().getSelectedItem();
						
						Denuncia denuncia = new Denuncia(denunciaTabelaEditar);
						
						denuncia.setDenDocumento(tfDocumento.getText());
						denuncia.setDenDocumentoSEI(tfDocSei.getText());
						denuncia.setDenProcessoSEI(tfProcSei.getText());
						
						
						if (dpDataDistribuicao.getValue() == null) {
							denuncia.setDenDataDistribuicao(null);}
						else {
							denuncia.setDenDataDistribuicao(formatter.format(dpDataDistribuicao.getValue()));
							}
												
							if (dpDataRecebimento.getValue() == null) {
							denuncia.setDenDataRecebimento(null);}
						else {
							denuncia.setDenDataRecebimento(formatter.format(dpDataRecebimento.getValue()));
							}
							
						denuncia.setDenDescricao(tfResDen.getText());
						
						DenunciaDao dDao = new DenunciaDao();
						
						dDao.mergeDenuncia(denuncia);
						
						// pegar o valor, levar para o MainController  e depois para o label lblDoc no EnderecoController
						dGeral = denuncia;
						main.pegarDoc(dGeral);
						
						listarDenuncias(strPesquisa); // acho que precisa de um  parametro strpesqusia aqui 
						selecionarDenuncia();
						
						modularBotoesInicial ();
						
						Alert a = new Alert (Alert.AlertType.INFORMATION);
						a.setTitle("Parabéns!!!");
						a.setContentText("Cadastro editado com sucesso!!!");
						a.setHeaderText(null);
						a.show();
				}
				
			}
	}
	
	// -- botão excluir -- //
	public void btnExcluirHabilitar (ActionEvent event) {
	
		DenunciaTabela denunciaExcluir = tvLista.getSelectionModel().getSelectedItem();
		
		int id = denunciaExcluir.getDenDenunciaID(); // buscar id para deletar
		
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
			tfDocSei.setDisable(true);
			tfProcSei.setDisable(true);
			
			dpDataDistribuicao.setDisable(true);
			dpDataRecebimento.setDisable(true);
			
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

