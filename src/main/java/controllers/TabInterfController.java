package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import dao.InterferenciaDao;
import entity.Endereco;
import entity.Interferencia;
import entity.Subterranea;
import entity.Superficial;
import fiscalizacao.GoogleMap;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import tabela.InterferenciaTabela;

public class TabInterfController implements Initializable {
	
	int tipoCaptacao = 3;
			
	TabSubterraneaController tabSubCon;
	
	TabSuperficialController tabSupCon;
	
	//-- String de pesquisa de enderecos --//
	String strPesquisa = "";
	
	InterferenciaTabela intTab;

	
	//-- coordenadas do mapa javascript --//
	public static String latDec;
	public static String lngDec;
		
	// --- Controller Principal - MainController --- //
	@FXML private MainController main;
	
	

    @FXML private Pane tabInterferencia;
    
    @FXML AnchorPane apIntInt = new AnchorPane();
    
   
 	@FXML Button btnLatLng = new Button();
 	@FXML Button  btnAtualizar = new Button();
	@FXML Button btnCapturar = new Button();
	@FXML Button btnNovo = new Button();
	@FXML Button btnSalvar = new Button();
	@FXML Button btnEditar = new Button();
	@FXML Button btnExcluir = new Button();
	@FXML Button btnCancelar = new Button();
	@FXML Button btnPesquisar = new Button();
	@FXML Button btnBuscarInt = new Button();
	
	//@FXML Label lblEnd = new  Label();
	@FXML public Label lblEnd; // publico para receber valor do MainController, metodo pegarEnd()
	
	//-- Tab interferencia latitude e longitude --//
	@FXML TextField tfIntLat = new TextField();
	@FXML TextField tfIntLon = new TextField();
	
	@FXML TextField tfLinkInt = new TextField();
	@FXML TextField tfPesquisar = new TextField();
	
	@FXML TextField tfUH;
	@FXML TextField tfCorpoHid;
	@FXML Image imgMap = new Image(TabEnderecoController.class.getResourceAsStream("/images/map.png")); 
	
	
	//-- chamar mapa --//
	@FXML Button btnIntMaps;
	
	// --- objeto para passar os valor pelo MainControoler para outro controller --- //
	public Endereco eGeralInt;
	
	@FXML Button btnEndCoord;
	@FXML Image imgEndCoord = new Image(TabVistoriaController.class.getResourceAsStream("/images/mapCoord.png"));

	@FXML Button btnEndCoordMap = new Button();
	@FXML Image imgEndCoordMap = new Image(TabVistoriaController.class.getResourceAsStream("/images/mapCoord.png"));

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
	
	ObservableList<InterferenciaTabela> obsList; 
	
	// --- método para listar interferencias --- //
 	public void listarInterferencias (String strPesquisa) {
 	
	 	// --- conexao - listar enderecos --- //
		InterferenciaDao interferenciaDao = new InterferenciaDao();
		List<Interferencia> interferenciaList = null;
		
		try {
				interferenciaList = interferenciaDao.listInterferencia(strPesquisa);
				obsList = FXCollections.observableArrayList();
			
				//System.out.println(interferenciaList.get(0).getInter_End_CodigoFK().getDesc_Endereco());
				//System.out.println(interferenciaList.get(0).getSub_Interferencia_Codigo().getSub_Caesb());
				
				if (!obsList.isEmpty()) {
					obsList.clear();
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
						
						obsList.add(intTab);
						
			 					
					}
						
					tvListaInt.setItems(obsList); 
			
			} catch (Exception e) {
				System.out.println("erro");
				Alert a = new Alert (Alert.AlertType.ERROR);
				a.setTitle("Alerta!!!");
				a.setContentText("Não há resultado para a consulta!!!" + " \n[" + e + "]");
				a.setHeaderText(null);
				a.show();
				e.printStackTrace();
				
				
				/*
								 * try {
				    userDao.save(user); //might throw exception
				} catch(ConstraintViolationException e) {
				    //Email Address already exists
				} catch(JDBCConnectionException e) {
				    //Lost the connection
				}
				and with the e.getCause() method you can retrieve the underlying SQLException and analyse it further:
				
				try {
				    userDao.save(user); //might throw exception
				} catch(JDBCException e) {
				    SQLException cause = (SQLException) e.getCause();
				    //evaluate cause and find out what was the problem
				    System.out.println(cause.getMessage());
}
				 */
			}
		
 	}
 	
 	// metodo selecionar interferencia -- //
 	public void selecionarInterferencia () {
	
 		tvListaInt.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
			
			public void changed(ObservableValue<?> observable , Object oldValue, Object newValue) {
			
			intTab = (InterferenciaTabela) newValue;
			
			if (intTab == null) {
				
				btnNovo.setDisable(true);
				btnSalvar.setDisable(true);
				
				btnEditar.setDisable(false);
				btnExcluir.setDisable(false);
				btnCancelar.setDisable(false);
				
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
				
				//System.out.println("FK " + intTab.getEnderecoInterferenciaObjetoTabelaFK());
				
				//Double lat = Double.parseDouble(tfIntLat.getText());
				//Double  lng = Double.parseDouble(tfIntLon.getText() );
				
				if (wv1 != null) {
					
					String endCoord = "" + intTab.getEnderecoInterferenciaObjetoTabelaFK().getLat_Endereco() + ","
									+ intTab.getEnderecoInterferenciaObjetoTabelaFK().getLon_Endereco();
					
					String intCoord = "" + intTab.getInter_Lat() +  "," + intTab.getInter_Lng();
					
					//System.out.println(endCoord);
					//System.out.println(intCoord);
					
					webEng.executeScript("" +
	                        "window.coordenadas = [" 
	                        + 	"'" + endCoord + "'," 
	                        +	"'" + intCoord + "'" 
	                        + "];"
	                        + "document.buscarCoordenadas(coordenadas);"
	                        + " map.setCenter({lat: " 
	                        + intTab.getEnderecoInterferenciaObjetoTabelaFK().getLat_Endereco()
	                        + ", lng: "  
	                        + intTab.getEnderecoInterferenciaObjetoTabelaFK().getLon_Endereco()
	                        + "});"
	                    );
	                    
					
					/*
					webEng.executeScript("" +
	                        "window.lat = " + lat + ";" +
	                        "window.lon = " + lng + ";" +
	                        "document.goToLocation(window.lat, window.lon);"
	                    );
	                    */
					
				}
				
				btnNovo.setDisable(true);
				btnSalvar.setDisable(true);
				btnEditar.setDisable(false);
				btnExcluir.setDisable(false);
				btnCancelar.setDisable(false);
				
				}
			}
		});
	}
	
	public void btnAtualizarHab (ActionEvent event) {
		
	}
	
	public void btnNovoHab (ActionEvent event) {
		
		
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
		
		btnLatLng.setDisable(false);
		btnAtualizar.setDisable(false);
		
		btnNovo.setDisable(true);
		btnSalvar.setDisable(false);
		
		btnEditar.setDisable(true);
		btnExcluir.setDisable(true);
		
		tfPesquisar.setDisable(false);
		
		btnPesquisar.setDisable(false);
		
		//-- choice box --//
		cbTipoInt.setItems(olTipoInt);
		
		cbBacia.setItems(olBacia);
		
		cbSituacao.setValue("Inativa");
		cbSituacao.setItems(olSituacao);
		
	}
	
	//-- botao salvar --//
	public void btnSalvarHab (ActionEvent event) {
		
		obsList = FXCollections.observableArrayList();
		
	
		if (tfIntLat.getText().isEmpty()|| tfIntLon.getText().isEmpty()) {
			
			Alert a = new Alert (Alert.AlertType.ERROR);
			a.setTitle("Alerta!!!");
			a.setContentText("Coordenadas inválidas!!!");
			a.setHeaderText(null);
			a.show();
		}
			else if (eGeralInt == null) {
				
				Alert a = new Alert (Alert.AlertType.ERROR);
				a.setTitle("Alerta!!!");
				a.setContentText("Endereço relacionado não selecionado!!!");
				a.setHeaderText(null);
				a.show();
			
				} else {
				
						if (tipoCaptacao == 1) {
							
								if (tabSubCon.obterSubterranea().getSub_Poco() == null ||
										tabSubCon.obterSubterranea().getSub_Caesb() == null ||
												tabSubCon.obterSubterranea().getSub_Sistema() == null
										) {
									
									Alert aLat = new Alert (Alert.AlertType.ERROR);
									aLat.setTitle("Alerta!!!");
									aLat.setContentText("Informe: Tipo de Captação (), Área é atendida pela Caesb() e Subsistema()!!!");
									aLat.setHeaderText(null);
									aLat.show();
									
								} else {
							
									Interferencia interferencia = new Interferencia();
									
										interferencia.setInter_Tipo(cbTipoInt.getValue());
										interferencia.setInter_Bacia(cbBacia.getValue());
										interferencia.setInter_UH(tfUH.getText());
										interferencia.setInter_Corpo_Hidrico(tfCorpoHid.getText());
										interferencia.setInter_Situacao(cbSituacao.getValue());
										interferencia.setInter_Desc_Endereco(eGeralInt.getDesc_Endereco());
										interferencia.setInter_Lat(Double.parseDouble(tfIntLat.getText()));
										interferencia.setInter_Lng(Double.parseDouble(tfIntLon.getText()));
									
										interferencia.setInter_End_CodigoFK(eGeralInt);
										
									Subterranea sub = new Subterranea ();
									
										sub.setInterf_SubFK(interferencia);
										
										sub.setSub_Poco(tabSubCon.obterSubterranea().getSub_Poco());
										sub.setSub_Caesb(tabSubCon.obterSubterranea().getSub_Caesb());
										sub.setSub_Sistema(tabSubCon.obterSubterranea().getSub_Sistema());
										sub.setSub_Estatico(tabSubCon.obterSubterranea().getSub_Estatico());
										sub.setSub_Dinamico(tabSubCon.obterSubterranea().getSub_Dinamico());
										sub.setSub_Vazao(tabSubCon.obterSubterranea().getSub_Vazao());
										sub.setSub_Profundidade(tabSubCon.obterSubterranea().getSub_Profundidade());
										sub.setSub_Data(tabSubCon.obterSubterranea().getSub_Data());
										
										interferencia.setSub_Interferencia_Codigo(sub);
										
										InterferenciaDao interferenciaDao = new InterferenciaDao ();
										interferenciaDao.salvaInterferencia(interferencia);	
							
									
									
									// mostrar a interferencia na tvList
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
										
										
										obsList.add(intTab);
										
										tvListaInt.setItems(obsList); 
										
									
									selecionarInterferencia ();
									
									modularBotoes ();
									
			
									//-- Alerta de endereco salvo --//
									Alert a = new Alert (Alert.AlertType.INFORMATION);
									a.setTitle("Parabéns!");
									a.setContentText("Interferência salva com sucesso!");
									a.setHeaderText(null);
									a.show();
									
									}
									
								} // fim subterranea
						
						
						if (tipoCaptacao == 2) {
							
								if (tabSupCon.obterSuperficial().getSup_Local() == null  ||
										tabSupCon.obterSuperficial().getSup_Caesb() == null
										
										) {
									
									Alert aLat = new Alert (Alert.AlertType.ERROR);
									aLat.setTitle("Alerta!!!");
									aLat.setContentText("Informe o Local de Captação e se há Caesb!!!");
									aLat.setHeaderText(null);
									aLat.show();
									
								} else {
							
										Interferencia interferencia = new Interferencia();
										
											interferencia.setInter_Tipo(cbTipoInt.getValue());
											interferencia.setInter_Bacia(cbBacia.getValue());
											interferencia.setInter_UH(tfUH.getText());
											interferencia.setInter_Corpo_Hidrico(tfCorpoHid.getText());
											interferencia.setInter_Situacao(cbSituacao.getValue());
											interferencia.setInter_Desc_Endereco(eGeralInt.getDesc_Endereco());
											interferencia.setInter_Lat(Double.parseDouble(tfIntLat.getText()));
											interferencia.setInter_Lng(Double.parseDouble(tfIntLon.getText()));
										
											interferencia.setInter_End_CodigoFK(eGeralInt);
											
										Superficial sup = new Superficial ();
										
											sup.setInterf_SuperFK(interferencia);
											
											sup.setSup_Local(tabSupCon.obterSuperficial().getSup_Local());
											sup.setSup_Captacao(tabSupCon.obterSuperficial().getSup_Captacao());
											sup.setSup_Bomba(tabSupCon.obterSuperficial().getSup_Bomba());
											sup.setSup_Potencia(tabSupCon.obterSuperficial().getSup_Bomba());
											sup.setSup_Tempo(tabSupCon.obterSuperficial().getSup_Tempo());
											sup.setSup_Area(tabSupCon.obterSuperficial().getSup_Area());
											sup.setSup_Caesb(tabSupCon.obterSuperficial().getSup_Caesb());
											sup.setSup_Data(tabSupCon.obterSuperficial().getSup_Data());
											
											interferencia.setSuper_Interferencia_Codigo(sup);
											
										InterferenciaDao interferenciaDao = new InterferenciaDao ();
										interferenciaDao.salvaInterferencia(interferencia);
										
										interferencia.setSuper_Interferencia_Codigo(sup);
										
										// mostrar a interferencia na tvList
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
											
										
										obsList.add(intTab);
										
											
										tvListaInt.setItems(obsList); 
										
										selecionarInterferencia ();
										
										modularBotoes ();
										
										//-- Alerta de endereco salvo --//
										Alert a = new Alert (Alert.AlertType.INFORMATION);
										a.setTitle("Parabéns!");
										a.setContentText("Interferência salva com sucesso!");
										a.setHeaderText(null);
										a.show();
										
										}
									
										
									} // fim superficial //
						
							if (tipoCaptacao == 3) {  // salvar outras interferencias que naoo superficial ou subterranea
							
										Interferencia interferencia = new Interferencia();
									
											interferencia.setInter_Tipo(cbTipoInt.getValue());
											interferencia.setInter_Bacia(cbBacia.getValue());
											interferencia.setInter_UH(tfUH.getText());
											interferencia.setInter_Corpo_Hidrico(tfCorpoHid.getText());
											interferencia.setInter_Situacao(cbSituacao.getValue());
											interferencia.setInter_Desc_Endereco(eGeralInt.getDesc_Endereco());
											interferencia.setInter_Lat(Double.parseDouble(tfIntLat.getText()));
											interferencia.setInter_Lng(Double.parseDouble(tfIntLon.getText()));
										
										
											interferencia.setInter_End_CodigoFK(eGeralInt);
											
											InterferenciaDao interferenciaDao = new InterferenciaDao ();
											
											interferenciaDao.salvaInterferencia(interferencia);
											
											
										// mostrar a interferencia na tvList
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
												
									
											obsList.add(intTab);
											
											tvListaInt.setItems(obsList); 
									
											selecionarInterferencia ();
									
											modularBotoes ();
							
	
											//-- Alerta de endereco salvo --//
											Alert a = new Alert (Alert.AlertType.INFORMATION);
											a.setTitle("Parabéns!");
											a.setContentText("Interferência salva com sucesso!");
											a.setHeaderText(null);
											a.show();
							
								
					} // fim superficial //
				
				}
		
		}
	
	//-- botao editar --//
	public void btnEditarHab (ActionEvent event) {
		
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
			btnLatLng.setDisable(false);
			btnAtualizar.setDisable(false);
			
		}
		
		else if (tfIntLat.getText().isEmpty() || tfIntLon.getText().isEmpty()) {
				
				Alert aLat = new Alert (Alert.AlertType.ERROR);
				aLat.setTitle("Alerta!!!");
				aLat.setContentText("Coordenadas inválidas!!!");
				aLat.setHeaderText(null);
				aLat.show();
				
			} else {
			
					String strEditar = intTab.getInter_Tipo();
			
						if (strEditar.equals("Subterrânea")) {
							
							
									if (tabSubCon.obterSubterranea().getSub_Poco() == null ||
											tabSubCon.obterSubterranea().getSub_Caesb() == null ||
													tabSubCon.obterSubterranea().getSub_Sistema() == null
											) {
										
										Alert aLat = new Alert (Alert.AlertType.ERROR);
										aLat.setTitle("Alerta!!!");
										aLat.setContentText("Informe: Tipo de Captação (), Área é atendida pela Caesb() e Subsistema()!!!");
										aLat.setHeaderText(null);
										aLat.show();
										
									} else {
										
									InterferenciaTabela intTab = tvListaInt.getSelectionModel().getSelectedItem(); //será¡ que precisa desse cÃ³digo?, verificar... Sim por causa do construtor da interferencia
									
									Interferencia interferencia = new Interferencia(intTab);
									
									interferencia.setInter_Tipo(cbTipoInt.getValue());
									interferencia.setInter_Bacia(cbBacia.getValue());
									interferencia.setInter_UH(tfUH.getText());
									interferencia.setInter_Corpo_Hidrico(tfCorpoHid.getText());
									interferencia.setInter_Situacao(cbSituacao.getValue());
									
									interferencia.setInter_Lat(Double.parseDouble(tfIntLat.getText()));
									interferencia.setInter_Lng(Double.parseDouble(tfIntLon.getText()));
									
									interferencia.setInter_Desc_Endereco(eGeralInt.getDesc_Endereco());  // trazer a descrição do endereço de acordo com o endereço selecionado
									interferencia.setInter_End_CodigoFK(eGeralInt);  // trazer a foreing key do endereço selecionado na tableview ou escolhido na tab endereço
									
									
									Subterranea sub = new Subterranea ();
									
										sub.setSub_Codigo(intTab.getInterSub().getSub_Codigo()); // para vir a chave primaria da subterranea
										
										sub.setSub_Poco(tabSubCon.obterSubterranea().getSub_Poco());
										sub.setSub_Caesb(tabSubCon.obterSubterranea().getSub_Caesb());
										sub.setSub_Sistema(tabSubCon.obterSubterranea().getSub_Sistema());
										sub.setSub_Estatico(tabSubCon.obterSubterranea().getSub_Estatico());
										sub.setSub_Dinamico(tabSubCon.obterSubterranea().getSub_Dinamico());
										sub.setSub_Vazao(tabSubCon.obterSubterranea().getSub_Vazao());
										sub.setSub_Profundidade(tabSubCon.obterSubterranea().getSub_Profundidade());
										sub.setSub_Data(tabSubCon.obterSubterranea().getSub_Data());
									
										sub.setInterf_SubFK(interferencia);
										
									interferencia.setSub_Interferencia_Codigo(sub);
									
									InterferenciaDao interferenciaDao = new InterferenciaDao ();
									interferenciaDao.mergeInterferencia(interferencia);
									
									// mostrar a interferencia na tvList //
									obsList.remove(intTab);
									
									intTab = new InterferenciaTabela(
											
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
										
										obsList.add(intTab);
										
										//System.out.println("sub data " + intTab.getInterSub().getSub_Data());
										
										tvListaInt.setItems(obsList);
										
										selecionarInterferencia ();
										
										modularBotoes ();
										
										//-- Alerta de interferência editada --//
										Alert a = new Alert (Alert.AlertType.INFORMATION);
										a.setTitle("Parabéns!");
										a.setContentText("Interferência editada!");
										a.setHeaderText(null);
										a.show();
									
							}}
			
			
							if (strEditar.equals("Superficial") || strEditar.equals("Canal")) {
								
								if (tabSupCon.obterSuperficial().getSup_Local() == null  ||
										tabSupCon.obterSuperficial().getSup_Caesb() == null
										
										) {
									
									Alert aLat = new Alert (Alert.AlertType.ERROR);
									aLat.setTitle("Alerta!!!");
									aLat.setContentText("Informe o Local de Captação e se há Caesb!!!");
									aLat.setHeaderText(null);
									aLat.show();
									
								} else {
								
								InterferenciaTabela intTab = tvListaInt.getSelectionModel().getSelectedItem(); //serÃ¡ que precisa desse cÃ³digo?, verificar... Sim por causa do construtor da interferencia
								
									Interferencia interferencia = new Interferencia(intTab);
									
									interferencia.setInter_Tipo(cbTipoInt.getValue());
									interferencia.setInter_Bacia(cbBacia.getValue());
									interferencia.setInter_UH(tfUH.getText());
									interferencia.setInter_Corpo_Hidrico(tfCorpoHid.getText());
									interferencia.setInter_Situacao(cbSituacao.getValue());
									
									interferencia.setInter_Lat(Double.parseDouble(tfIntLat.getText()));
									interferencia.setInter_Lng(Double.parseDouble(tfIntLon.getText()));
									
									interferencia.setInter_Desc_Endereco(eGeralInt.getDesc_Endereco());  // trazer a descrição do endereço de acordo com o endereço selecionado
									interferencia.setInter_End_CodigoFK(eGeralInt);  // trazer a foreing key do endereço selecionado na tableview ou escolhido na tab endereço
									
								Superficial sup = new Superficial();
								
									sup.setInterf_SuperFK(interferencia);
									
									sup.setSup_Codigo(intTab.getInterSup().getSup_Codigo()); // obter a chave primÃ¡ria
									
									sup.setSup_Local(tabSupCon.obterSuperficial().getSup_Local());
									sup.setSup_Captacao(tabSupCon.obterSuperficial().getSup_Captacao());
									sup.setSup_Bomba(tabSupCon.obterSuperficial().getSup_Bomba());
									sup.setSup_Potencia(tabSupCon.obterSuperficial().getSup_Potencia());
									sup.setSup_Tempo(tabSupCon.obterSuperficial().getSup_Tempo());
									
									sup.setSup_Area(tabSupCon.obterSuperficial().getSup_Area());
									sup.setSup_Caesb(tabSupCon.obterSuperficial().getSup_Caesb());
									sup.setSup_Data(tabSupCon.obterSuperficial().getSup_Data());
							
								
								interferencia.setSuper_Interferencia_Codigo(sup);
								
								InterferenciaDao interferenciaDao = new InterferenciaDao ();
								
								interferenciaDao.mergeInterferencia(interferencia);
								
								// mostrar a interferencia na tvList
								obsList.remove(intTab);
								
								intTab = new InterferenciaTabela(
										
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
									
									obsList.add(intTab);
									tvListaInt.setItems(obsList);
									
									selecionarInterferencia ();
									
									modularBotoes ();
								
								}}
			
								if (strEditar.equals("Caminhão Pipa") 					|| 
										strEditar.equals("Lançamento de Águas Pluviais")	|| 
											strEditar.equals("Lançamento de Efluentes")			|| 
												strEditar.equals("Barragem")						||
													strEditar.equals("Outros")	)	
									{
								
									
									InterferenciaTabela intTab = tvListaInt.getSelectionModel().getSelectedItem(); //serÃ¡ que precisa desse cÃ³digo?, verificar... Sim por causa do construtor da interferencia
									
									Interferencia interferencia = new Interferencia(intTab);
									
									interferencia.setInter_Tipo(cbTipoInt.getValue());
									interferencia.setInter_Bacia(cbBacia.getValue());
									interferencia.setInter_UH(tfUH.getText());
									interferencia.setInter_Corpo_Hidrico(tfCorpoHid.getText());
									interferencia.setInter_Situacao(cbSituacao.getValue());
									
									interferencia.setInter_Lat(Double.parseDouble(tfIntLat.getText()));
									interferencia.setInter_Lng(Double.parseDouble(tfIntLon.getText()));
									
									interferencia.setInter_Desc_Endereco(eGeralInt.getDesc_Endereco());  // trazer a descrição do endereço de acordo com o endereço selecionado
									interferencia.setInter_End_CodigoFK(eGeralInt);  // trazer a foreing key do endereço selecionado na tableview ou escolhido na tab endereço
								
									InterferenciaDao interferenciaDao = new InterferenciaDao ();
									
									interferenciaDao.mergeInterferencia(interferencia);
									
									// mostrar a interferencia na tvList
									obsList.remove(intTab);
									
									intTab = new InterferenciaTabela(
											
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
										
										obsList.add(intTab);
										tvListaInt.setItems(obsList);
										
									selecionarInterferencia ();
									
									modularBotoes ();
									
									}
									

			}
			
		
	}
	
	//-- botao excluir --//
	public void btnExcluirHab (ActionEvent event) {
		
		try {
		
			InterferenciaTabela intTab = tvListaInt.getSelectionModel().getSelectedItem();
			
			InterferenciaDao interferenciaDao = new InterferenciaDao ();
			
			interferenciaDao.removeInterferencia(intTab.getInter_Codigo());
			
			// remover a interferencia da lista (tvListInt)
			obsList.remove(intTab);
			
			selecionarInterferencia ();
			
			modularBotoes ();
			
			
				Alert a = new Alert (Alert.AlertType.INFORMATION);
				a.setTitle("Parabéns!");
				a.setContentText("Cadastro excluído!");
				a.setHeaderText(null);
				a.show();
		}
		catch (Exception e) {
			
				Alert a = new Alert (Alert.AlertType.ERROR);
				a.setTitle("Atenção!!!");
				a.setContentText("Erro ao excluir o cadastro!!!");
				a.setHeaderText(e.toString());
				a.show();
		}
		
	}
	
	//-- botao cancelar --//
	public void btnCancelarHab (ActionEvent event) {
		
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
	public void btnPesquisarHab (ActionEvent event) {
		
		strPesquisa = tfPesquisar.getText();
		
		listarInterferencias(strPesquisa);
		
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
	
	public void btnLatLngHab (ActionEvent event) {
			
		String linkCoord = (tfLinkInt.getText());
		
		int latIni= linkCoord.indexOf("@");
		String lat = linkCoord.substring(latIni);
		
		int latF = lat.indexOf(",");
		String latitude = lat.substring(1, latF);
		
		String longitude = lat.substring(latF + 1, latF + latitude.length());
		
		tfIntLat.setText(latitude);
		tfIntLon.setText(longitude);
		
	}
	
	//-- método atualizar latitude e longitude --//
	
	public void setLatLng (double lat, double lng) {
		
		latDec = Double.toString(lat);
		lngDec = Double.toString(lng);
			
			
	}
	//-- trazer a coordenada do mapa --//
	@FXML Image imgGetCoord = new Image(TabEnderecoController.class.getResourceAsStream("/images/getCoord.png")); 
	@FXML Button btnCoord = new Button ();
	
	GoogleMap google;
	public void btnEndCoordHab (ActionEvent event) {
	  adicMarcador();
	  
	}
	
	Double lat = -15.775073004902042;
	Double lng = -47.940351677729836;
	
	// adicMarcador(); 
	public void adicMarcador () {
		
		System.out.println("latitude endereço " + eGeralInt.getLat_Endereco() + " e " + eGeralInt.getLon_Endereco());
		
    	if (eGeralInt.getLat_Endereco() != null  && eGeralInt.getLon_Endereco() != null ) {
        	lat = Double.parseDouble(eGeralInt.getLat_Endereco().toString());
    		lng = Double.parseDouble(eGeralInt.getLon_Endereco().toString());
    	}
    		try {
    		google.setMarkerPosition(lat, lng);
    		google.setMapCenter(lat, lng);
    		}catch (Exception e) {
	    		Alert a = new Alert (Alert.AlertType.ERROR);
				a.setTitle("Alerta!!!");
				a.setContentText("Mapa não inicializado!!! " + e);
				a.setHeaderText(null);
				a.show();
    		}
	
    	
	}
		
	//-- buscador de endereços e coordenadas --//
	public void btnIntMapsHab (ActionEvent event) throws IOException {

		google = new GoogleMap();
		
		Group group = new Group();
		group.getChildren().addAll(google, btnCoord, btnEndCoordMap);
		
		Scene scene = new Scene(group);
		
		Tooltip ttbtnCoord  = new Tooltip("Capturar a coordenada do mapa e enviar para o cadastro. "
	    		+ "\n * Também abrirá uma janela com o endereço sugerido pelo GoogleMaps "
	    		+ "\n         para a coordenada selecionada.");
		ttbtnCoord.setFont(new Font("Arial", 13));;
		
		 Tooltip ttbtnEndMapCoord = new Tooltip("Localizar o endereço cadastrado no mapa"
		    		+ "\n * Se ainda não houver endereço cadastrado, o ponto será as coordenadas da Adasa.");
		 ttbtnEndMapCoord.setFont(new Font("Arial", 13));
		
		 
		btnCoord.setLayoutY(8);
		btnCoord.setLayoutX(502);
		btnCoord.setGraphic(new ImageView(imgGetCoord));
		
		btnEndCoordMap.setLayoutY(8.5);
		btnEndCoordMap.setLayoutX(620);
		btnEndCoordMap.setGraphic(new ImageView(imgEndCoordMap));
		
			btnCoord.setOnAction(new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent e) {
	            	tfIntLat.setText(latDec);
	        		tfIntLon.setText(lngDec);
	        		
	            }
	        });
				btnCoord.setOnMouseEntered(new EventHandler<Event>() {
					
					@Override
					public void handle(Event event) {
						
						btnCoord.setTooltip(
								ttbtnCoord
							);
						}
					});
		
						btnEndCoordMap.setOnAction(new EventHandler<ActionEvent>() {
				            @Override public void handle(ActionEvent e) {
				            	adicMarcador();
				            	
				            }
				        });
								btnEndCoordMap.setOnMouseEntered(new EventHandler<Event>() {
				
									@Override
									public void handle(Event event) {
										
										btnEndCoordMap.setTooltip(
												ttbtnEndMapCoord
											);
										
									}
								});
	    
		Stage stage = new Stage(StageStyle.UTILITY);
		stage.setWidth(1250);
		stage.setHeight(750);
        stage.setScene(scene);
        stage.setMaximized(false);
        stage.setResizable(false);
        
        stage.show();
       
	}
	
	// métodos de remimensionar as tabs //
		public void redimWid (Number newValue) {
			apInter.setMinWidth((double) newValue);
		}
		public void redimHei (Number newValue) {
			apInter.setMinHeight((double) newValue);
		}

		
		@FXML ScrollPane spInter;
		@FXML AnchorPane apInter;
		
		@FXML Pane pInterForm;
		@FXML Pane pInterTipo;
		@FXML Pane pInterMap;
		@FXML Pane pInterferencia;
		
		
		
	//-- INITIALIZE --//
	public void initialize(URL url, ResourceBundle rb) {
		
		tcDescEndInt.setCellValueFactory(new PropertyValueFactory<InterferenciaTabela, String>("inter_Desc_Endereco")); 
		tcIntCorpoHidrico.setCellValueFactory(new PropertyValueFactory<InterferenciaTabela, String>("inter_Corpo_Hidrico")); 
		tcIntUH.setCellValueFactory(new PropertyValueFactory<InterferenciaTabela, String>("inter_UH"));
		
		tfPesquisar.setOnKeyReleased(event -> {
	  		  if (event.getCode() == KeyCode.ENTER){
	  		     btnPesquisar.fire();
	  		  }
	  		});
	        
		
		cbTipoInt.setItems(olTipoInt);
		
		cbBacia.setItems(olBacia);
		
		cbSituacao.setValue("Ativa");
		cbSituacao.setItems(olSituacao);
		
		//selecionarInterferencia ();
		
		//-- Selecionar a tabela de acordo com o tipo de captacaoo --//
		cbTipoInt.getSelectionModel().selectedItemProperty().addListener( 
				(ObservableValue<? extends String> observable, String oldString, String newString) -> 
				
			{
				try {
					
					//System.out.println("nova string selecionada no  initialize " + newString);
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
		
		btnIntMaps.setGraphic(new ImageView(imgMap));
		btnEndCoord.setGraphic(new ImageView(imgEndCoord));
		
		
		modularBotoes ();
		
		/*
		AnchorPane.setTopAnchor(spInter, 0.0);
	    AnchorPane.setRightAnchor(spInter, 0.0);
	    AnchorPane.setLeftAnchor(spInter, 0.0);
	    AnchorPane.setBottomAnchor(spInter, 30.0);
	   */
	   
	    apInter.widthProperty().addListener((obs, oldVal, newVal) -> {
	    	
	    	
	    	Double widNewVal = Math.pow((Double) newVal/70, (Double) newVal/1000);
	    	// panes com os formulários e mapas
	        AnchorPane.setLeftAnchor(pInterferencia, widNewVal);
	     // redimensionamento scroolPane
	     	spInter.setMinWidth((Double)newVal);
	     	spInter.setMaxWidth((Double)newVal);
	        
	        // para o scrool pane funcionar com a tela pequena e assim poder rolar...
		    apIntInt.setMinHeight(1200.0);
		    
		    apIntInt.setMinWidth(spInter.getWidth());
		    
	        
	    });
	    
	    apInter.heightProperty().addListener((obs, oldVal, newVal) -> {
	    	
		       spInter.setMinHeight((Double)newVal - 30);
		       spInter.setMaxHeight((Double)newVal - 30);
		       
		    });
	    
	    
	    /*
	    apInter.widthProperty().addListener((obs, oldVal, newVal) -> {
	    	
	    	spInter.setMinWidth((Double) newVal);
	    	spInter.setMaxWidth((Double) newVal);
	    	
	    	apIntInt.setMinWidth((Double) newVal);
	    	apIntInt.setMaxWidth((Double) newVal);
	    	
	    	
	    	System.out.println("anchor pane inter  width " + newVal);
	    });
	    
	    apInter.heightProperty().addListener((obs, oldVal, newVal) -> {
	    	
	    	Double altura = (Double) newVal - 28;
	    	
	    	spInter.setMinHeight((Double) altura);
	    	spInter.setMaxHeight((Double) altura);
	    	
	    	apIntInt.setMinHeight((Double) newVal);
	    	apIntInt.setMaxHeight((Double) newVal);
	    	
	    	System.out.println("anchor pane inter  heigth " + newVal);
	    	System.out.println("valor da altura " + altura);
	    	
	    });
	    */
	    
	    
	    
	    
	   
	    
	   
	    
	    //apIntInt.setMinHeight(spInter.getPrefHeight() + 300);
	   
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
		btnLatLng.setDisable(true);
		btnAtualizar.setDisable(true);
		
		btnSalvar.setDisable(true);
		btnEditar.setDisable(true);
		btnExcluir.setDisable(true);
		
		//tfIntPesq.setDisable(true);
		
		//btnIntPesq.setDisable(true); // acho que nÃ£o precisa entrar desabilitado
		
		btnNovo.setDisable(false);
	}

	//-- MAIN CONTROLLER --//
	public void init(MainController mainController) {
		main = mainController;
	}
	
	public void abrirTabs (String newString) throws IOException {
		
		if (newString == "Superficial") {
			
			pInterTipo.getChildren().clear();
			
			Pane tabSupPane = new Pane();
			
			tabSupCon = new TabSuperficialController();
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TabSuperficial.fxml"));
			loader.setRoot(tabSupPane);
			loader.setController(tabSupCon);
			loader.load();
			
			pInterTipo.getChildren().add(tabSupPane);
			
			// -- escolher tipo de captacao --//
			tipoCaptacao = 2;
			
			
		}
		

		if (newString == "Canal") {
			
			pInterTipo.getChildren().clear();
			
			Pane tabSupPane = new Pane();
			
			tabSupCon = new TabSuperficialController();
			
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TabSuperficial.fxml"));
			loader.setRoot(tabSupPane);
			loader.setController(tabSupCon);
			loader.load();
			
			pInterTipo.getChildren().add(tabSupPane);
			
			// -- escolher tipo de captação --//
			tipoCaptacao = 2;
			
			
		}
		
		if (newString == "Subterrânea") {
			
			pInterTipo.getChildren().clear();
			
			Pane tabSubPane = new Pane();
			
			//TabSubController tc = new TabSubController();
			
			tabSubCon = new TabSubterraneaController();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TabSubterranea.fxml"));
			loader.setRoot(tabSubPane);
			loader.setController(tabSubCon);
			loader.load();
			pInterTipo.getChildren().add(tabSubPane);
			// -- escolher tipo de captação --//
			tipoCaptacao = 1;
			
		}
		
		
		if (newString.equals("Caminhão Pipa") 					|| 
				newString.equals("Lançamento de Águas Pluviais")	|| 
					newString.equals("Lançamento de Efluentes")			|| 
						newString.equals("Barragem")						||
							newString.equals("Outros")					||
								newString.equals("") 	)	// or null para nao dar null  point ...
		{
			
			pInterTipo.getChildren().clear();
			// -- escolher tipo de captação --//
			tipoCaptacao = 3;
		}
		
	}
	
	@FXML CheckBox checkMap;
	

	public void checkMapHab (ActionEvent event) {
		
		int count = 0;
		
		if (checkMap.isSelected()) {
			count ++;
			abrirMapa("");
		}
		else {
			pInterMap.getChildren().clear();
		}
		
		System.out.println("checkbox" + count);
	}
	
	
	WebView wv1;
	WebEngine webEng;
	BorderPane root;

	 public void abrirMapa (String strMarcador) {
		
	/*
		File file = null;
		file = new File (TabEnderecoController.class.getResource("/html/enderecoMap.html").getFile());
		
		Document docHtml = null;
		
		try {
			docHtml = Jsoup.parse(file, "UTF-8");  // retirei o  .clone()
	
		} catch (IOException e1) {
			System.out.println("Erro na leitura no parse Jsoup!!!");
			e1.printStackTrace();
		}
		
		String strHTMLMap = docHtml.toString();
		*/
		
		wv1 = new WebView();
		webEng = wv1.getEngine();
		webEng.load(getClass().getResource("/html/enderecoMap.html").toExternalForm());
		
		wv1.setPrefSize(887,485);
		wv1.getEngine();
	
		//root = new BorderPane();
		//root.setCenter(wv1);
		//root.setPrefSize(887, 418);
		//root.setLayoutY(425);
		//root.setLayoutX(125);
		
		//apIntInt.getChildren().add(root);
		
		pInterMap.getChildren().add(wv1);
	
		webEng.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (Worker.State.SUCCEEDED.equals(newValue)) {
            	webEng.executeScript(strMarcador);
            }
        });
		
	}

}
	




	