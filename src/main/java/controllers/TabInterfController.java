package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import dao.InterferenciaDao;
import dao.SubterraneaDao;
import dao.SuperficialDao;
import entity.Endereco;
import entity.Interferencia;
import entity.Subterranea;
import entity.Superficial;
import fiscalizacao.GoogleMap;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import tabela.InterferenciaTabela;

public class TabInterfController implements Initializable {
	
	int tipoCaptacao = 3;
			
	TabSubterraneaController tabSubCon;
	
	TabSuperficialController tabSupCon;
	
	//-- String de pesquisa de enderecos --//
	String strPesquisaInterferencia = "";
	
	InterferenciaTabela intTab;
	
	/*
	  vazao litros dia tem que mudar, no superficial sao litros por segundo/dia.
	  
	   		Talvez colocar:
	   		 	horas de captacao (converter para segundos):
	   		
	  			vazaoo diaria:
	  			
	  			e converter caso o teccnico  coloque  no formato de agua superficial ou subterraneo
	 */
	
	//-- coordenadas do mapa javascript --//
	public static String latDec;
	public static String lngDec;
		
	// --- Controller Principal - MainController --- //
	@FXML private MainController main;
	
	@FXML Pane paneTipoInterferencia = new Pane();

    @FXML private Pane tabInterferencia;
    
    @FXML AnchorPane aPaneInt = new AnchorPane();
    
   
 	@FXML Button btnIntLatLon = new Button();
 	@FXML Button btnIntAtualizar = new Button();
	@FXML Button btnCapturar = new Button();
	@FXML Button btnIntNovo = new Button();
	@FXML Button btnIntSalvar = new Button();
	@FXML Button btnIntEdit = new Button();
	@FXML Button btnIntExc = new Button();
	@FXML Button btnIntCan = new Button();
	@FXML Button btnIntPesq = new Button();
	@FXML Button btnBuscarInt = new Button();
	
	//@FXML Label lblEnd = new  Label();
	@FXML public Label lblEnd; // publico para receber valor do MainController, metodo pegarEnd()
	
	//-- Tab interferencia latitude e longitude --//
	@FXML TextField tfIntLat = new TextField();
	@FXML TextField tfIntLon = new TextField();
	
	@FXML TextField tfLinkInt = new TextField();
	@FXML TextField tfIntPesq = new TextField();
	
	@FXML TextField tfUH;
	@FXML TextField tfCorpoHid;
	
	
	//-- chamar mapa --//
	@FXML Button btnIntMaps;
	
	//-- trazer a coordenada do mapa --//
	//@FXML Image imgGetCoord = new Image(getClass().getResourceAsStream("../images/getCoord.png"));
	@FXML Button btnCoord = new Button();
	
	// --- objeto para passar os valor pelo MainControoler para outro controller --- //
	public Endereco eGeralInt;
	
	
	//-- TableView Endereco --//
		@FXML private TableView <InterferenciaTabela> tvListaInt;
		
		@FXML TableColumn<InterferenciaTabela, String> tcDescEndInt;
		@FXML TableColumn<InterferenciaTabela, String> tcIntCorpoHidrico;
		@FXML TableColumn<InterferenciaTabela, String> tcIntUH;
	
		
		
		//-- combobox - tipo interferencia --//
		@FXML
		ChoiceBox<String> cbTipoInt = new ChoiceBox<String>();
			ObservableList<String> olTipoInt = FXCollections
				.observableArrayList(
						"Subterrânea" , 
						"Superficial",
						"Canal",
						"Caminhão Pipa",
						"Lançamento de Águas Pluviais",
						"Lançamento de Efluentes",
						"Barragem",
						"Outros"
						
						
						); 
			
								@FXML
								ChoiceBox<String> cbBacia = new ChoiceBox<String>();
									ObservableList<String> olBacia = FXCollections
										.observableArrayList(
												"Corumbá",
												"Descoberto",
												"Maranhão",
												"Paranoá",
												"Preto",
												"São Bartolomeu",
												"São Marcos"
												); 
									
									
											@FXML
											ChoiceBox<String> cbSituacao = new ChoiceBox<String>();
												ObservableList<String> olSituacao = FXCollections
													.observableArrayList(
															"Ativa",
															"Inativa"
															
															); 
									
								
								
								
	// --- método para listar interferencias --- //
 	public void listarInterferencias (String strPesquisaInterferencia) {
 	
 	// --- conexao - listar enderecos --- //
	InterferenciaDao interferenciaDao = new InterferenciaDao();
	List<Interferencia> interferenciaList = interferenciaDao.listInterferencia(strPesquisaInterferencia);
	ObservableList<InterferenciaTabela> obsListInterferenciaTabela = FXCollections.observableArrayList();
	
	
	if (!obsListInterferenciaTabela.isEmpty()) {
		obsListInterferenciaTabela.clear();
	}
	
		for (Interferencia interferencia : interferenciaList) {
			
			InterferenciaTabela intTab = new InterferenciaTabela(
				
				interferencia.getInter_Codigo(),
				interferencia.getInter_Tipo(),
				interferencia.getInter_Bacia(),
				interferencia.getInter_UH(),
				interferencia.getInter_Corpo_Hidrico(),
				interferencia.getInter_Lat(),
				interferencia.getInter_Lng(),
				interferencia.getInter_Situacao(),
				interferencia.getInter_Desc_Endereco(),
				
				//-- foreign key --//
				interferencia.getInter_End_CodigoFK(),
				
				interferencia.getSub_Interferencia_Codigo(),
				
				interferencia.getSuper_Interferencia_Codigo()
				
				
				);
			
			obsListInterferenciaTabela.add(intTab);
			
 					
		}
		
		tcDescEndInt.setCellValueFactory(new PropertyValueFactory<InterferenciaTabela, String>("inter_Desc_Endereco")); 
		tcIntCorpoHidrico.setCellValueFactory(new PropertyValueFactory<InterferenciaTabela, String>("inter_Corpo_Hidrico")); 
		tcIntUH.setCellValueFactory(new PropertyValueFactory<InterferenciaTabela, String>("inter_UH")); 
		
		tvListaInt.setItems(obsListInterferenciaTabela); 
		
 	}
 	
 	// metodo selecionar interferencia -- //
 	public void selecionarInterferencia () {
	
		tvListaInt.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
			public void changed(ObservableValue<?> observable , Object oldValue, Object newValue) {
			
			intTab = (InterferenciaTabela) newValue;
			
			if (intTab == null) {
				
				btnIntNovo.setDisable(true);
				btnIntSalvar.setDisable(true);
				
				btnIntEdit.setDisable(false);
				btnIntExc.setDisable(false);
				btnIntCan.setDisable(false);
				
			} else {

				// -- preencher os campos -- //
				cbTipoInt.setValue(intTab.getInter_Tipo());
				cbBacia.setValue(intTab.getInter_Bacia());
				tfUH.setText(intTab.getInter_UH());
				tfCorpoHid.setText(intTab.getInter_Corpo_Hidrico());
				cbSituacao.setValue(intTab.getInter_Situacao());
				
				// latitude e longitude
				tfIntLat.setText(intTab.getInter_Lat().toString());
				tfIntLon.setText(intTab.getInter_Lng().toString());
				
				// mudar o endereco da interferencia de acordo com a selecao do usuario
				eGeralInt = intTab.getEnderecoInterferenciaObjetoTabelaFK();
				
				lblEnd.setText(eGeralInt.getDesc_Endereco()  + " |  RA: "  + eGeralInt.getRA_Endereco());
				
				String tipoInt = intTab.getInter_Tipo();
				
				if (tipoInt.equals("Subterrânea")) {
					
					tabSubCon.imprimirSubterranea(intTab.getInterSub());
					
				}
				
				if (tipoInt.equals("Superficial") || tipoInt.equals("Canal")) {
					
					tabSupCon.imprimirSuperficial(intTab.getInterSup());
					
				}
				
				btnIntNovo.setDisable(true);
				btnIntSalvar.setDisable(true);
				btnIntEdit.setDisable(false);
				btnIntExc.setDisable(false);
				btnIntCan.setDisable(false);
				
				}
			}
		});
	}
	
	public void btnBuscarIntHab (ActionEvent event) {

		
	}
 		
	public void btnLatLongHab (ActionEvent event) {
		
	}
	
	public void btnIntAtualizarHab (ActionEvent event) {
		
	}
	public void btnIntNovoHab (ActionEvent event) {
		
		
		cbTipoInt.setDisable(false);
		
		cbBacia.setDisable(false);
		
		
		tfUH.setText("");
		tfUH.setDisable(false);
		
		tfCorpoHid.setText("");
		tfCorpoHid.setDisable(false);
		
		
		cbSituacao.setDisable(false);
		
		tfLinkInt.setText("");
		tfLinkInt.setDisable(false);
		
		tfIntLat.setText("");
		tfIntLat.setDisable(false);
		
		tfIntLon.setText("");
		tfIntLon.setDisable(false);
		
		btnIntLatLon.setDisable(false);
		btnIntAtualizar.setDisable(false);
		
		btnIntNovo.setDisable(true);
		btnIntSalvar.setDisable(false);
		
		btnIntEdit.setDisable(true);
		btnIntExc.setDisable(true);
		
		tfIntPesq.setDisable(false);
		
		btnIntPesq.setDisable(false);
		
		//-- choice box --//
		cbTipoInt.setItems(olTipoInt);
		
		cbBacia.setItems(olBacia);
		
		cbSituacao.setValue("Inativa");
		cbSituacao.setItems(olSituacao);
		
		
		
		
	}
	
	//-- botao salvar --//
	public void btnIntSalvarHab (ActionEvent event) {
		
		if (tipoCaptacao == 1) {
			
			Interferencia interferencia = new Interferencia();
			
				interferencia.setInter_Tipo(cbTipoInt.getValue().toString());
				interferencia.setInter_Bacia(cbBacia.getValue().toString());
				interferencia.setInter_UH(tfUH.getText());
				interferencia.setInter_Corpo_Hidrico(tfCorpoHid.getText());
				interferencia.setInter_Situacao(cbSituacao.getValue().toString());
				interferencia.setInter_Desc_Endereco(eGeralInt.getDesc_Endereco());
				interferencia.setInter_Lat(Double.parseDouble(tfIntLat.getText()));
				interferencia.setInter_Lng(Double.parseDouble(tfIntLon.getText()));
			
			Endereco endereco = new Endereco(); // ?
				
				endereco = eGeralInt;// ??
				
				endereco.getListInterferencias().add(interferencia);  // precisa disso?
				
				interferencia.setInter_End_CodigoFK(endereco);
				
			InterferenciaDao interferenciaDao = new InterferenciaDao ();
				
				interferenciaDao.salvaInterferencia(interferencia);
				interferenciaDao.mergeInterferencia(interferencia);
				
			Subterranea sub = new Subterranea ();
			
				sub.setInterf_SubFK(interferencia);
				
				sub.setSub_Poco(tabSubCon.obterSubterranea().getSub_Poco());
				sub.setSub_Caesb(tabSubCon.obterSubterranea().getSub_Caesb());
				sub.setSub_Sistema(tabSubCon.obterSubterranea().getSub_Sistema());
				sub.setSub_Estatico(tabSubCon.obterSubterranea().getSub_Estatico());
				sub.setSub_Dinamico(tabSubCon.obterSubterranea().getSub_Dinamico());
				sub.setSub_Vazao(tabSubCon.obterSubterranea().getSub_Vazao());
				sub.setSub_Profundidade(tabSubCon.obterSubterranea().getSub_Profundidade());
				
			SubterraneaDao sDao = new SubterraneaDao();
				
				
			sDao.mergeSubterranea(sub);
			
			listarInterferencias (strPesquisaInterferencia);
			
			selecionarInterferencia ();
			
			modularBotoes ();
			
		} // fim subterranea
		
		
		if (tipoCaptacao == 2) {
			
			Interferencia interferencia = new Interferencia();
			
				interferencia.setInter_Tipo(cbTipoInt.getValue().toString());
				interferencia.setInter_Bacia(cbBacia.getValue().toString());
				interferencia.setInter_UH(tfUH.getText());
				interferencia.setInter_Corpo_Hidrico(tfCorpoHid.getText());
				interferencia.setInter_Situacao(cbSituacao.getValue().toString());
				interferencia.setInter_Desc_Endereco(eGeralInt.getDesc_Endereco());
				interferencia.setInter_Lat(Double.parseDouble(tfIntLat.getText()));
				interferencia.setInter_Lng(Double.parseDouble(tfIntLon.getText()));
			
			Endereco endereco = new Endereco();
				
				endereco = eGeralInt;
				endereco.getListInterferencias().add(interferencia);
				interferencia.setInter_End_CodigoFK(endereco);
				InterferenciaDao interferenciaDao = new InterferenciaDao ();
				
				interferenciaDao.salvaInterferencia(interferencia);
				interferenciaDao.mergeInterferencia(interferencia);
				
				
			Superficial sup = new Superficial ();
			
				sup.setInterf_SuperFK(interferencia);
				
				sup.setSup_Local(tabSupCon.obterSuperficial().getSup_Local());
				sup.setSup_Captacao(tabSupCon.obterSuperficial().getSup_Captacao());
				sup.setSup_Bomba(tabSupCon.obterSuperficial().getSup_Bomba());
				sup.setSup_Potencia(tabSupCon.obterSuperficial().getSup_Bomba());
				sup.setSup_Tempo(tabSupCon.obterSuperficial().getSup_Tempo());
				sup.setSup_Area(tabSupCon.obterSuperficial().getSup_Area());
				sup.setSup_Caesb(tabSupCon.obterSuperficial().getSup_Caesb());
				
			SuperficialDao supDao = new SuperficialDao();
			
			supDao.mergeSuperficial(sup);
			
			listarInterferencias (strPesquisaInterferencia);
			
			selecionarInterferencia ();
			
			modularBotoes ();
			
				
			} // fim superficial //
		
			if (tipoCaptacao == 3) {  // salvar outras interferencias que naoo superficial ou subterranea
			
			Interferencia interferencia = new Interferencia();
			
				interferencia.setInter_Tipo(cbTipoInt.getValue().toString());
				interferencia.setInter_Bacia(cbBacia.getValue().toString());
				interferencia.setInter_UH(tfUH.getText());
				interferencia.setInter_Corpo_Hidrico(tfCorpoHid.getText());
				interferencia.setInter_Situacao(cbSituacao.getValue().toString());
				interferencia.setInter_Desc_Endereco(eGeralInt.getDesc_Endereco());
				interferencia.setInter_Lat(Double.parseDouble(tfIntLat.getText()));
				interferencia.setInter_Lng(Double.parseDouble(tfIntLon.getText()));
			
			Endereco endereco = new Endereco();
				
				endereco = eGeralInt;
				endereco.getListInterferencias().add(interferencia);
				interferencia.setInter_End_CodigoFK(endereco);
				InterferenciaDao interferenciaDao = new InterferenciaDao ();
				
				interferenciaDao.salvaInterferencia(interferencia);
				interferenciaDao.mergeInterferencia(interferencia);
				
			listarInterferencias (strPesquisaInterferencia);
			
			selecionarInterferencia ();
			
			modularBotoes ();
			
				
			} // fim superficial //
		
			
			//-- Alerta de endereco salvo --//
			Alert aSalvo = new Alert (Alert.AlertType.CONFIRMATION);
			aSalvo.setTitle("Parabéns!");
			aSalvo.setContentText("Interferência salva com sucesso!");
			aSalvo.setHeaderText(null);
			aSalvo.show();
			
		
		
	}
	
	//-- botao editar --//
	public void btnIntEditHab (ActionEvent event) {
		
		// ver excecao de querer editar sem esconlher o endereco da interferencia...
		
		if (cbTipoInt.isDisable()) {
			
			cbTipoInt.setDisable(false);
			cbBacia.setDisable(false);
			
			tfUH.setDisable(false);
			tfCorpoHid.setDisable(false);
			
			cbSituacao.setDisable(false);
			
			tfLinkInt.setDisable(false);
			
			tfIntLat.setDisable(false);
			tfIntLon.setDisable(false);
			btnIntLatLon.setDisable(false);
			btnIntAtualizar.setDisable(false);
			
		}
		
		else {
			
			String strEditar = intTab.getInter_Tipo();
			
			if (strEditar.equals("Subterrânea")) {
				
				InterferenciaTabela intTabEditar = tvListaInt.getSelectionModel().getSelectedItem(); //serÃ¡ que precisa desse cÃ³digo?, verificar... Sim por causa do construtor da interferencia
				
				Interferencia intEditar = new Interferencia(intTabEditar);
				
					intEditar.setInter_Tipo(cbTipoInt.getValue().toString());
					intEditar.setInter_Bacia(cbBacia.getValue().toString());
					intEditar.setInter_UH(tfUH.getText());
					intEditar.setInter_Corpo_Hidrico(tfCorpoHid.getText());
					intEditar.setInter_Situacao(cbSituacao.getValue().toString());
					
					intEditar.setInter_Lat(Double.parseDouble(tfIntLat.getText()));
					intEditar.setInter_Lng(Double.parseDouble(tfIntLon.getText()));
					
					intEditar.setInter_Desc_Endereco(eGeralInt.getDesc_Endereco());  // trazer a descriÃ§Ã£o do endereÃ§o de acordo com o endereÃ§o selecionado
					intEditar.setInter_End_CodigoFK(eGeralInt);  // trazer a foreing key do endereÃ§o selecionado na tableview ou escolhido na tab endereÃ§o
				
					System.out.println("ao editar, valor do intEditar cÃ³digo do endereÃ§o: " + intEditar.getInter_End_CodigoFK().getCod_Endereco());
					
				InterferenciaDao interferenciaDao = new InterferenciaDao ();
				
				interferenciaDao.mergeInterferencia(intEditar);
				
				Subterranea sub = new Subterranea ();
				
					sub.setInterf_SubFK(intEditar);
				
					sub.setSub_Codigo(intTab.getInterSub().getSub_Codigo()); // para vir a chave primaria da subterranea
					
					sub.setSub_Poco(tabSubCon.obterSubterranea().getSub_Poco());
					sub.setSub_Caesb(tabSubCon.obterSubterranea().getSub_Caesb());
					sub.setSub_Sistema(tabSubCon.obterSubterranea().getSub_Sistema());
					sub.setSub_Estatico(tabSubCon.obterSubterranea().getSub_Estatico());
					sub.setSub_Dinamico(tabSubCon.obterSubterranea().getSub_Dinamico());
					sub.setSub_Vazao(tabSubCon.obterSubterranea().getSub_Vazao());
					sub.setSub_Profundidade(tabSubCon.obterSubterranea().getSub_Profundidade());
					
				SubterraneaDao sDao = new SubterraneaDao();
					
				sDao.mergeSubterranea(sub);
				
				listarInterferencias (strPesquisaInterferencia);
				selecionarInterferencia ();
				
				modularBotoes ();
				
				
				//-- Alerta de interferÃªncia editada --//
				Alert aSalvo = new Alert (Alert.AlertType.CONFIRMATION);
				aSalvo.setTitle("ParabÃ©ns!");
				aSalvo.setContentText("InterferÃªncia editada!");
				aSalvo.setHeaderText(null);
				aSalvo.show();
			
			}
			
			if (strEditar.equals("Superficial")) {
				
				InterferenciaTabela intTabEditar = tvListaInt.getSelectionModel().getSelectedItem(); //serÃ¡ que precisa desse cÃ³digo?, verificar... Sim por causa do construtor da interferencia
				
				Interferencia intEditar = new Interferencia(intTabEditar);
				
				intEditar.setInter_Tipo(cbTipoInt.getValue().toString());
				intEditar.setInter_Bacia(cbBacia.getValue().toString());
				intEditar.setInter_UH(tfUH.getText());
				intEditar.setInter_Corpo_Hidrico(tfCorpoHid.getText());
				intEditar.setInter_Situacao(cbSituacao.getValue().toString());
				
				intEditar.setInter_Lat(Double.parseDouble(tfIntLat.getText()));
				intEditar.setInter_Lng(Double.parseDouble(tfIntLon.getText()));
				
				intEditar.setInter_Desc_Endereco(eGeralInt.getDesc_Endereco());  // trazer a descriÃ§Ã£o do endereÃ§o de acordo com o endereÃ§o selecionado
				intEditar.setInter_End_CodigoFK(eGeralInt);  // trazer a foreing key do endereÃ§o selecionado na tableview ou escolhido na tab endereÃ§o
			
				System.out.println("ao editar, valor do intEditar cÃ³digo do endereÃ§o: " + intEditar.getInter_End_CodigoFK().getCod_Endereco());
				
				InterferenciaDao interferenciaDao = new InterferenciaDao ();
				
				interferenciaDao.mergeInterferencia(intEditar);
			
				Superficial sup = new Superficial();
				
				sup.setInterf_SuperFK(intEditar);
				
				sup.setSup_Codigo(intTab.getInterSup().getSup_Codigo()); // obter a chave primÃ¡ria
				
				sup.setSup_Local(tabSupCon.obterSuperficial().getSup_Local());
				sup.setSup_Captacao(tabSupCon.obterSuperficial().getSup_Captacao());
				sup.setSup_Bomba(tabSupCon.obterSuperficial().getSup_Bomba());
				sup.setSup_Potencia(tabSupCon.obterSuperficial().getSup_Potencia());
				sup.setSup_Tempo(tabSupCon.obterSuperficial().getSup_Tempo());
				
				sup.setSup_Area(tabSupCon.obterSuperficial().getSup_Area());
				sup.setSup_Caesb(tabSupCon.obterSuperficial().getSup_Caesb());
				
				//falta data
				
				SuperficialDao supDao = new SuperficialDao();
				
				supDao.mergeSuperficial(sup);
				
				listarInterferencias (strPesquisaInterferencia);
				selecionarInterferencia ();
				
				modularBotoes ();
				
				
			
			}
			
			if (strEditar.equals("Caminhão Pipa") 					|| 
					strEditar.equals("Lançamento de Águas Pluviais")	|| 
						strEditar.equals("Lançamento de Efluentes")			|| 
							strEditar.equals("Barragem")						||
								strEditar.equals("Outros")	)	
			{
			
				InterferenciaTabela intTabEditar = tvListaInt.getSelectionModel().getSelectedItem(); //serÃ¡ que precisa desse cÃ³digo?, verificar... Sim por causa do construtor da interferencia
				
				Interferencia intEditar = new Interferencia(intTabEditar);
				
				intEditar.setInter_Tipo(cbTipoInt.getValue().toString());
				intEditar.setInter_Bacia(cbBacia.getValue().toString());
				intEditar.setInter_UH(tfUH.getText());
				intEditar.setInter_Corpo_Hidrico(tfCorpoHid.getText());
				intEditar.setInter_Situacao(cbSituacao.getValue().toString());
				
				intEditar.setInter_Lat(Double.parseDouble(tfIntLat.getText()));
				intEditar.setInter_Lng(Double.parseDouble(tfIntLon.getText()));
				
				intEditar.setInter_Desc_Endereco(eGeralInt.getDesc_Endereco());  // trazer a descriÃ§Ã£o do endereÃ§o de acordo com o endereÃ§o selecionado
				intEditar.setInter_End_CodigoFK(eGeralInt);  // trazer a foreing key do endereÃ§o selecionado na tableview ou escolhido na tab endereÃ§o
			
				InterferenciaDao interferenciaDao = new InterferenciaDao ();
				
				interferenciaDao.mergeInterferencia(intEditar);
				
				listarInterferencias (strPesquisaInterferencia);
				selecionarInterferencia ();
				
				modularBotoes ();
				
			}

		}
		
	}
	
	
	//-- botao excluir --//
	public void btnIntExcHab (ActionEvent event) {
		
			InterferenciaTabela intTabEditar = tvListaInt.getSelectionModel().getSelectedItem();
			
			InterferenciaDao interferenciaDao = new InterferenciaDao ();
			
			interferenciaDao.removeInterferencia(intTabEditar.getInter_Codigo());
			
			listarInterferencias(strPesquisaInterferencia);
			selecionarInterferencia ();
			
			modularBotoes ();
			
			//-- Alerta de endereco salvo --//
			Alert aSalvo = new Alert (Alert.AlertType.CONFIRMATION);
			aSalvo.setTitle("Parabéns!");
			aSalvo.setContentText("Interferência excluída!");
			aSalvo.setHeaderText(null);
			aSalvo.show();
		
	}
	
	//-- botÃ£o cancelar --//
	public void btnIntCanHab (ActionEvent event) {
		
		modularBotoes ();
		
		cbTipoInt.getSelectionModel().clearSelection();  // DEU NULL POINT AO CANCELAR E TENTAR SALVAR NOVA  INTERFERENCIA
		cbBacia.setValue(null);
		tfUH.setText("");
		tfCorpoHid.setText("");
		cbSituacao.setValue("inativa");
		tfLinkInt.setText("");
		tfIntLat.setText("");
		tfIntLon.setText("");
	
	}
	
	
	//-- botao pesquisar interferÃªncia --//
	public void btnIntPesqHab (ActionEvent event) {
		
		strPesquisaInterferencia = tfIntPesq.getText();
		
		listarInterferencias(strPesquisaInterferencia);
		
		selecionarInterferencia ();
		
		modularBotoes ();
		
		cbTipoInt.getSelectionModel().clearSelection();
		cbBacia.setValue(null);
		tfUH.setText("");
		tfCorpoHid.setText("");
		cbSituacao.setValue("inativa");
		tfLinkInt.setText("");
		tfIntLat.setText("");
		tfIntLon.setText("");
		
	}

	public void btnCapturarCroqui (ActionEvent event) {
	
	
	}
	
	public void btnEndLatLonHab (ActionEvent event) {
			
		String linkCoord = (tfLinkInt.getText());
		
		int latIni= linkCoord.indexOf("@");
		String lat = linkCoord.substring(latIni);
		
		int latF = lat.indexOf(",");
		String latitude = lat.substring(1, latF);
		
		String longitude = lat.substring(latF + 1, latF + latitude.length());
		
		tfIntLat.setText(latitude);
		tfIntLon.setText(longitude);
		
	}
	
	//-- mÃ©todo atualizar latitude e longitude --//
	
	public void setLatLng (double lat, double lng) {
		
		latDec = Double.toString(lat);
		lngDec = Double.toString(lng);
			
			
	}

	//-- buscador de endereÃ§os e coordenadas --//
	public void btnIntMapsHab (ActionEvent event) throws IOException {

		GoogleMap google = new GoogleMap();
		
		Group group = new Group();
		group.getChildren().addAll(google, btnCoord);
		
		Scene scene = new Scene(group);
		
		btnCoord.setLayoutY(8);
		btnCoord.setLayoutX(502);
		//btnCoord.setGraphic(new ImageView(imgGetCoord));
		btnCoord.setText("coord");
		
		btnCoord.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	tfIntLat.setText(latDec);
        		tfIntLon.setText(lngDec);
        		
            }
        });
	    
		Stage stage = new Stage(StageStyle.UTILITY);
		stage.setWidth(1250);
		stage.setHeight(750);
        stage.setScene(scene);
        stage.setMaximized(false);
        stage.setResizable(false);
        
        stage.show();
        
        /* para ao fechar o mapa capturar as coordenadas, mas resolvi tirar 
         
        stage.setOnCloseRequest(new EventHandler<javafx.stage.WindowEvent>() {
            public void handle(javafx.stage.WindowEvent event) {
            	//lblEndereco.setText(endMap);
            	tfIntLat.setText(latDec);
            	tfIntLon.setText(lngDec);
                System.out.println("latitude e longitude da interferÃªncia captados");
            }
        });
        */
	}
	
	
	//-- INITIALIZE --//
	public void initialize(URL url, ResourceBundle rb) {
		
		
		cbTipoInt.setItems(olTipoInt);
		
		cbBacia.setItems(olBacia);
		
		cbSituacao.setValue("Inativa");
		cbSituacao.setItems(olSituacao);
		
		//selecionarInterferencia ();
		
		//-- Selecionar a tabela de acordo com o tipo de captacaoo --//
		cbTipoInt.getSelectionModel().selectedItemProperty().addListener( 
				(ObservableValue<? extends String> observable, String oldString, String newString) -> 
				
				{
					try {
						
						System.out.println("nova string selecionada no  initialize " + newString);
						if (newString == null)
							abrirTabs("");
						else {
							abrirTabs(newString); 
						}
						
					} catch (IOException e) {
						
						e.printStackTrace();
					}
				} 
				
				);
		
		modularBotoes ();
		
	}
	
	public void modularBotoes () {
		
		cbTipoInt.setDisable(true);
		cbBacia.setDisable(true);
		
		tfUH.setDisable(true);
		tfCorpoHid.setDisable(true);
		
		cbSituacao.setDisable(true);
		
		tfLinkInt.setDisable(true);
		
		tfIntLat.setDisable(true);
		tfIntLon.setDisable(true);
		btnIntLatLon.setDisable(true);
		btnIntAtualizar.setDisable(true);
		
		btnIntSalvar.setDisable(true);
		btnIntEdit.setDisable(true);
		btnIntExc.setDisable(true);
		
		//tfIntPesq.setDisable(true);
		
		//btnIntPesq.setDisable(true); // acho que nÃ£o precisa entrar desabilitado
		
		btnIntNovo.setDisable(false);
	}

	//-- MAIN CONTROLLER --//
	public void init(MainController mainController) {
		main = mainController;
	}
	
	public void abrirTabs (String newString) throws IOException {
		
		if (newString == "Superficial") {
			
			paneTipoInterferencia.getChildren().clear();
			
			Pane tabSupPane = new Pane();
			
			tabSupCon = new TabSuperficialController();
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TabSuperficial.fxml"));
			loader.setRoot(tabSupPane);
			loader.setController(tabSupCon);
			loader.load();
			
			paneTipoInterferencia.getChildren().add(tabSupPane);
			
			// -- escolher tipo de captacao --//
			tipoCaptacao = 2;
			
			
		}
		

		if (newString == "Canal") {
			
			paneTipoInterferencia.getChildren().clear();
			
			Pane tabSupPane = new Pane();
			
			tabSupCon = new TabSuperficialController();
			
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TabSuperficial.fxml"));
			loader.setRoot(tabSupPane);
			loader.setController(tabSupCon);
			loader.load();
			
			paneTipoInterferencia.getChildren().add(tabSupPane);
			
			// -- escolher tipo de captaÃ§Ã£o --//
			tipoCaptacao = 2;
			
			
		}
		
		if (newString == "Subterrânea") {
			
			paneTipoInterferencia.getChildren().clear();
			
			Pane tabSubPane = new Pane();
			
			//TabSubController tc = new TabSubController();
			
			tabSubCon = new TabSubterraneaController();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TabSubterranea.fxml"));
			loader.setRoot(tabSubPane);
			loader.setController(tabSubCon);
			loader.load();
			paneTipoInterferencia.getChildren().add(tabSubPane);
			// -- escolher tipo de captaÃ§Ã£o --//
			tipoCaptacao = 1;
			
			
		
		}
		
		// ainda está dando null ao clicar em cancelar  e ao fazer nova pesquisa MELHOROU COLOCANDO "", MAS NO
		// CHOICE BOX AINDA ESTÁ APARECENDO A ESCOLHA ANTERIOR, TIPO 'SUBTERRÂNEA'
		
		if (newString.equals("Caminhão Pipa") 					|| 
				newString.equals("Lançamento de Águas Pluviais")	|| 
					newString.equals("Lançamento de Efluentes")			|| 
						newString.equals("Barragem")						||
							newString.equals("Outros")					||
								newString.equals("") 	)	// or null para nao dar null  point ...
		{
			System.out.println("if caminhão, superficial etc e null" + newString);
			paneTipoInterferencia.getChildren().clear();
		}
		
	}
	
}



	