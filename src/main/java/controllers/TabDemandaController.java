package controllers;


import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

import dao.DemandaDao;
import entity.Demanda;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import tabela.DemandaTabela;


public class TabDemandaController implements Initializable {
	
	// --- Controller Principal - MainController --- //
	@FXML private MainController main;
	
	@FXML AnchorPane apDemanda = new AnchorPane();
	
	@FXML TextField tfDocumento = new TextField();
	@FXML TextField tfDocSei = new TextField();
	@FXML TextField tfProcSei =  new TextField();
	@FXML TextField tfResDen = new TextField();
	@FXML TextField tfPesquisar = new TextField();
	
	// ----- Label - endereco da demanda ------ //
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
	@FXML private TableView <DemandaTabela> tvLista;
	
	// -- Colunas -- //
	@FXML private TableColumn<DemandaTabela, String> tcDocumento;
	@FXML private TableColumn<DemandaTabela, String> tcDocSEI;
	@FXML private TableColumn<DemandaTabela, String> tcProcSEI;
	
	@FXML DatePicker dpDoc;
	
	@FXML Label lblDataAtualizacao;
	
	// capturar demanda para a TabEnderecoController
	private static Demanda dGeral;
	
	// --- String para primeira pesquisa --- //
	String strPesquisa = "";
	

	// formatação de datas //
	DateTimeFormatter formatter = new DateTimeFormatterBuilder()
			.parseCaseInsensitive()
			.append(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
			.toFormatter();
	
	DateTimeFormatter formatterDT = new DateTimeFormatterBuilder()
			.parseCaseInsensitive()
			.append(DateTimeFormatter.ofPattern("dd/MM/yyyy H:mm:ss"))
			.toFormatter();
		
	ObservableList<DemandaTabela> obsList;
	
	// --- método listar demandas --- //
	public void listarDemandas(String strPesquisa) {
		
		// -- Conexão e pesquisa de den�ncias -- //
		DemandaDao demandaDao = new DemandaDao();	//passar classe
		List<Demanda> demandaList = demandaDao.listarDemandas(strPesquisa); //passar string de pesquisar
		obsList = FXCollections.observableArrayList(); //chamar observable list e outra classe

		
		if (!obsList.isEmpty()) {
			obsList.clear();
		}
		for (Demanda demanda : demandaList) {
			
			DemandaTabela denTab = new DemandaTabela(
					demanda.getDemID(),
					demanda.getDemDocumento(),
					demanda.getDemDocumentoSEI(),
					demanda.getDemProcessoSEI(),
					demanda.getDemDescricao(),
					demanda.getDemDistribuicao(),
					demanda.getDemRecebimento(),
					
					demanda.getDemAtualizacao(),
					
					demanda.getDemEnderecoFK()
					);
			
				obsList.add(denTab);
				
		}
		
		// para trazer o resultado por id (do maior para o menor) //
		Comparator<DemandaTabela> comparar = Comparator.comparing(DemandaTabela::getDemID);
		obsList.sort(comparar.reversed());
		
        tvLista.setItems(obsList); 
        
	}
	
	// -- selecionar denúncia -- //
	public void selecionarDemanda () {
		
		// TableView - selecionar denúncia ao clicar //
		tvLista.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
			public void changed(ObservableValue<?> observable , Object oldValue, Object newValue) {
			
			DemandaTabela denTab = (DemandaTabela) newValue;
				
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

				// preencher os campos //
				tfDocumento.setText(denTab.getDemDocumento());
				tfDocSei.setText(denTab.getDemDocumentoSEI());
				tfProcSei.setText(denTab.getDemProcessoSEI());
				
				if (denTab.getDemDistribuicao() == null) {
					dpDataDistribuicao.getEditor().clear();
	 				} else {
	 					dpDataDistribuicao.getEditor().setText(denTab.getDemDistribuicao());
	 				}
 				
 				if (denTab.getDemRecebimento() == null) {
 					dpDataRecebimento.getEditor().clear();
	 				} else {
	 					dpDataRecebimento.getEditor().setText(denTab.getDemRecebimento());
	 				}
 				
 				
 				
				tfResDen.setText(denTab.getDemDescricao());
				
				// endereço relacionado //
				if (denTab.getDemEnderecoFK() != null) {
					lblDenEnd.setText(denTab.getDemEnderecoFK().getDesc_Endereco() + ", " + denTab.getDemEnderecoFK().getRA_Endereco());
					lblDenEnd.setTextFill(Color.BLACK);
				} else {
					lblDenEnd.setText("Sem endereço cadastrado!");
					lblDenEnd.setTextFill(Color.RED);	
				}
				
				// data de autalização //
				try {lblDataAtualizacao.setText("Data de Atualização: " + formatterDT.format(denTab.getDemAtualizacao()));
						lblDataAtualizacao.setTextFill(Color.BLACK);
				}catch (Exception e) {lblDataAtualizacao.setText("Não há data de atualização!");
						lblDataAtualizacao.setTextFill(Color.RED);}
				
				Demanda dGeral = new Demanda(denTab);

				main.pegarDoc(dGeral);
				
				// copiar número sei da demanda ao selecionar //
				Clipboard clip = Clipboard.getSystemClipboard();
                ClipboardContent conteudo = new ClipboardContent();
                conteudo.putString(denTab.getDemDocumentoSEI());
                clip.setContent(conteudo);
				
				// habilitar e desabilitar botões //
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
		
        obsList = FXCollections.observableArrayList();
        
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
			
					Demanda demanda = new Demanda();
					
					demanda.setDemDocumento(tfDocumento.getText()); 
					
					demanda.setDemDocumentoSEI(tfDocSei.getText()); 
					demanda.setDemProcessoSEI(tfProcSei.getText());
					
					/*
					if (dpDataDistribuicao.getValue() == null) {
						demanda.setDemDistribuicao(null);}
					else {
						demanda.setDemDistribuicao(formatter.format(dpDataDistribuicao.getValue()));
						}
											
						if (dpDataRecebimento.getValue() == null) {
						demanda.setDemRecebimento(null);}
					else {
						demanda.setDemRecebimento(formatter.format(dpDataRecebimento.getValue()));
						}
						*/
					
					if (dpDataDistribuicao.getValue() == null) {
						demanda.setDemDistribuicao(null);}
						else {
							demanda.setDemDistribuicao(dpDataDistribuicao.getEditor().getText());
							}
											
					if (dpDataRecebimento.getValue() == null) {
					demanda.setDemRecebimento(null);}
						else {
							demanda.setDemRecebimento(dpDataRecebimento.getEditor().getText());
							}
						
					demanda.setDemDescricao(tfResDen.getText());
					
					demanda.setDemAtualizacao((LocalDateTime.now()));
					
					DemandaDao dao = new DemandaDao();
					
					dao.salvarDemanda(demanda);
					
					// pegar o valor, levar para o MainController  e depois para o label lblDoc no EnderecoController
					dGeral = demanda;
					main.pegarDoc(dGeral);
						
					DemandaTabela denTab = new DemandaTabela(
							demanda.getDemID(),
							demanda.getDemDocumento(),
							demanda.getDemDocumentoSEI(),
							demanda.getDemProcessoSEI(),
							demanda.getDemDescricao(),
							demanda.getDemDistribuicao(),
							demanda.getDemRecebimento(),
							
							demanda.getDemAtualizacao(),
							
							demanda.getDemEnderecoFK()
							);
					
					obsList.add(denTab);
					
					tvLista.setItems(obsList);
						
					selecionarDemanda();
				
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
			
						DemandaTabela denTab = tvLista.getSelectionModel().getSelectedItem();
						
						Demanda demanda = new Demanda(denTab);
						
						demanda.setDemDocumento(tfDocumento.getText());
						demanda.setDemDocumentoSEI(tfDocSei.getText());
						demanda.setDemProcessoSEI(tfProcSei.getText());
						
						
						if (dpDataDistribuicao.getValue() == null) {
							demanda.setDemDistribuicao(null);}
							else {
								demanda.setDemDistribuicao(dpDataDistribuicao.getEditor().getText());
								}
												
						if (dpDataRecebimento.getValue() == null) {
						demanda.setDemRecebimento(null);}
							else {
								demanda.setDemRecebimento(dpDataRecebimento.getEditor().getText());
								}
							
						demanda.setDemDescricao(tfResDen.getText());
						 
						DemandaDao dDao = new DemandaDao();
						
						dDao.mergeDemanda(demanda);
							
						// pegar o valor, levar para o MainController  e depois para o label lblDoc no EnderecoController
						dGeral = demanda;
						main.pegarDoc(dGeral);
						
						
						
						// atualizar os dados na tabela
						obsList.remove(denTab);
						
						denTab = new DemandaTabela(
								demanda.getDemID(),
								demanda.getDemDocumento(),
								demanda.getDemDocumentoSEI(),
								demanda.getDemProcessoSEI(),
								demanda.getDemDescricao(),
								demanda.getDemDistribuicao(),
								demanda.getDemRecebimento(),
								
								demanda.getDemAtualizacao(),
								
								demanda.getDemEnderecoFK()
								);
						
						obsList.addAll(denTab);
						
						// para trazer o resultado por id (do maior para o menor) //
						Comparator<DemandaTabela> comparar = Comparator.comparing(DemandaTabela::getDemID);
						obsList.sort(comparar.reversed());
						
						System.out.println("bntEditar - Data da atualização " + denTab.getDemID());
						
						tvLista.setItems(obsList);
						
						selecionarDemanda();
						
						modularBotoesInicial ();
						
						//System.out.println("data e hora : " + demanda.getDataHoraAtualizacao());
						
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
	
		try {
			
			DemandaTabela denTab = tvLista.getSelectionModel().getSelectedItem();
			
			int id = denTab.getDemID(); // buscar id para deletar
			
			DemandaDao dDao = new DemandaDao();
			
			dDao.removerDemanda(id);
			
			obsList.remove(denTab);
			
			selecionarDemanda();
			
			modularBotoesInicial (); 
			
				Alert a = new Alert (Alert.AlertType.INFORMATION);
				a.setTitle("Parabéns!!!");
				a.setContentText("Cadastro excluído com sucesso!!!");
				a.setHeaderText(null);
				a.show();
		
			}
		
			catch (Exception e) {
				
				Alert a = new Alert (Alert.AlertType.ERROR);
				a.setTitle("Alerta!!!");
				a.setContentText("Erro ao excluir o cadastro!!!");
				a.setHeaderText(e.toString());
				a.show();
			}
				
	}

			
	// -- botão cancelar -- //
	public void btnCancelarHabilitar (ActionEvent event) {
			
		modularBotoesInicial();
	}
	
	
	
	
	// -- botão pesquisar denúncia -- //
	public void btnPesquisarHabilitar (ActionEvent event) {
		
		strPesquisa = (String) tfPesquisar.getText();
		
		listarDemandas(strPesquisa);
		selecionarDemanda();
		
		modularBotoesInicial (); 
		
	}
	
	
	// métodos de remimensionar as tabs //
	public void redimWid (Number newValue) {
				apDemanda.setMinWidth((double) newValue);
			}
	public void redimHei (Number newValue) {
				apDemanda.setMinHeight((double) newValue);;
			}
			
	
	@FXML ScrollPane spDemanda;
	@FXML AnchorPane apDemInt;
	@FXML Pane pDemanda;
	@FXML BorderPane bpDemanda;
	
	
	// -- INITIALIZE -- //
	public void initialize(URL url, ResourceBundle rb) {
		
		tcDocumento.setCellValueFactory(new PropertyValueFactory<DemandaTabela, String>("demDocumento")); 
        tcDocSEI.setCellValueFactory(new PropertyValueFactory<DemandaTabela, String>("demDocumentoSEI")); 
        tcProcSEI.setCellValueFactory(new PropertyValueFactory<DemandaTabela, String>("demProcessoSEI")); 
		
        
        tfPesquisar.setOnKeyReleased(event -> {
  		  if (event.getCode() == KeyCode.ENTER){
  		     btnPesquisar.fire();
  		  }
  		});
        
        // --- habilitar e desabilitar botões ---- //
		modularBotoesInicial();
		
		// redimensionamento da tab ato
		AnchorPane.setTopAnchor(spDemanda, 0.0);
	    AnchorPane.setLeftAnchor(spDemanda, 0.0);
		AnchorPane.setRightAnchor(spDemanda, 0.0);
	    AnchorPane.setBottomAnchor(spDemanda, 35.0);
	    
	    apDemInt.setMaxHeight(1200);
	    apDemInt.setMinHeight(1200);
	    
	
		apDemanda.widthProperty().addListener((obs, oldVal, newVal) -> {
	    	
			bpDemanda.setMaxWidth((Double) newVal);
			bpDemanda.setMinWidth((Double) newVal);
			
	    });
		
		
		
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

