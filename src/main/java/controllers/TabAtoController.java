package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dao.AtoDao;
import entity.Ato;
import entity.Vistoria;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
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
	
	@FXML TextArea taCaracterizacao;
	
	@FXML DatePicker dpDataFiscalizacao;
	@FXML DatePicker dpDataCriacaoAto;
	
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
					"Auto de Infração de Advertência",
					"Auto de Infração de Multa");
	
	@FXML public Label lblVisAto; // público para receber valor do MainController, método pegarEnd()
	
	//  objeto para passar os valor pelo MainControoler para outro controller //
	public Vistoria visGeral;
	
	
	
	//  Controller Principal - MainController  //
	@FXML private MainController main;
	
	// MAIN CONTROLLER //
	public void init(MainController mainController) {
			main = mainController;
	}
	
	
	
	public void btnNovoHab (ActionEvent event) {
		
		cbAtoTipo.setDisable(false);
		tfAto.setDisable(false);
		tfAtoSEI.setDisable(false);
		dpDataFiscalizacao.setDisable(false);
		dpDataCriacaoAto.setDisable(false);
		taCaracterizacao.setDisable(false);
		
		cbAtoTipo.setValue(null);
		tfAto.setText("");
		tfAtoSEI.setText("");
		dpDataFiscalizacao.setValue(null);
		dpDataCriacaoAto.setValue(null);
		taCaracterizacao.setText("");
		
	}

	public void btnSalvarHab (ActionEvent event) {
		
		
			Ato ato = new Ato();
			
			ato.setAtoTipo(cbAtoTipo.getValue());
			ato.setAtoIdentificacao(tfAto.getText());
			ato.setAtoSEI(tfAtoSEI.getText());
			//datas//
			ato.setAtoDataFiscalizacao(dpDataFiscalizacao.getValue().toString()); // DATA
			ato.setAtoDataCriacao(dpDataCriacaoAto.getValue().toString()); // DATA
			
			ato.setAtoVisCodigoFK(visGeral);
			
			AtoDao atoDao = new  AtoDao();
			
			atoDao.mergeAto(ato);
			
			listarAtos (strPesquisa);
			selecionarAto();
			
			// modular botões
		}
	

	public void btnEditarHab (ActionEvent event) {
		
	}

	public void btnExcluirHab (ActionEvent event) {
	
	}
	
	public void btnCancelarHab (ActionEvent event) {
		
	}

	public void btnPesquisarHab (ActionEvent event) {
		
		listarAtos (strPesquisa);
		selecionarAto ();
	
	}
	
	public void initialize(URL url, ResourceBundle rb) {
		cbAtoTipo.setItems(olAtoTipo);
		
		
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
		
	}
	
	TabVistoriaController tabVis;
	
	@FXML Pane paneTipoAto;
	
	// método abrir tab Vistoria, Termo etc //
	public void abrirTabs (String newString) throws IOException {
		
		if (newString.equals("Relatório de Vistoria")) {
			
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
	
	//  método para listar interferencias  //
 	public void listarAtos (String strPesquisaAto) {
 	
	 	// --- conexão - listar endereços --- //
		//AtoDao atoDao = new AtoDao();
		//List<Ato> atoList = atoDao.listAto(strPesquisaAto);
		ObservableList<AtoTabela> olAto = FXCollections.observableArrayList();
		
		
		if (!olAto.isEmpty()) {
			olAto.clear();
		}
		
		/*
			for (Ato ato : atoList) {
				
				/*
				AtoTabela atoTab = new AtoTabela(
					
				ato.getAtoCodigo(),
				//ato.getAtoEndCodigo(),
				
				//ato.getAtoVistoriaFK(),  // tipo identificacao sei cara infr atenu agrav datafis datacri
				ato.getAtoTipo(),
				ato.getAtoIdentificacao(),
				ato.getAtoSEI(),
				ato.getAtoCaracterizacao(),
				ato.getAtoDataFiscalizacao(),
				ato.getAtoDataCriacao()
				
				);
				*/
			
			olAto.add(atoTab);
			
		}
		/*
		tcTipo.setCellValueFactory(new PropertyValueFactory<AtoTabela, String>("atoTipo")); 
		tcNumero.setCellValueFactory(new PropertyValueFactory<AtoTabela, String>("atoIdentificacao")); 
		tcSEI.setCellValueFactory(new PropertyValueFactory<AtoTabela, String>("atoSEI")); 
		
		tvAto.setItems(olAto);
		*/
		
 	//}
 	
 	// método selecionar interferência //
  	public void selecionarAto () {
 	
 		tvAto.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
 			public void changed(ObservableValue<?> observable , Object oldValue, Object newValue) {
 			
 			atoTab = (AtoTabela) newValue;
 			
 			if (atoTab == null) {
 				
 				/*
 				btnIntNovo.setDisable(true);
 				btnIntSalvar.setDisable(true);
 				btnIntEdit.setDisable(false);
 				btnIntExc.setDisable(false);
 				btnIntCan.setDisable(false);
 				*/
 				System.out.println("valor nulo!");
 				
 			} else {

 				// -- preencher os campos -- //
 				cbAtoTipo.setValue(atoTab.getAtoTipo());
 				tfAto.setText(atoTab.getAtoIdentificacao());
 				tfAtoSEI.setText(atoTab.getAtoSEI());
 				//data
 				//infrações
 				
 				
 				//eGeralUs = atoTab.getAtoEndCodigo();
 				//System.out.println("endereço do ato: " + atoTab.getAtoEndCodigo());
 				
 				// label  - imprimir  o endereço relacionado //
 				//lblUsEnd.setText(eGeralUs.getDesc_Endereco() + " |  RA: "  + eGeralUs.getRA_Endereco() );
 				
 				//String tipoInt = intTab.getInter_Tipo();  // saber o tipo de interferência
 				
 				}
 			}
 		});
  	}
  	


}

