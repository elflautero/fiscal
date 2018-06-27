package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import dao.EnderecoDao;
import entity.Demanda;
import entity.Endereco;
import fiscalizacao.GoogleMap;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import tabela.EnderecoTabela;

public class TabEnderecoController implements Initializable {

	
	// --- Controller Principal - MainController --- //
	@FXML private MainController main;
	
	@FXML Pane tabEndereco = new Pane();
	
	@FXML Button btnBuscarDoc = new Button();
	
	@FXML TextField tfEnd = new TextField();
	
	
	@FXML TextField tfEndCep = new TextField();
	@FXML TextField  tfEndCid = new TextField();
	
	
	@FXML public Label lblDoc; // publico para receber valor do MainController, metodo pegarDoc()

	@FXML TextField tfLinkEnd = new TextField();
	@FXML TextField tfEndLat = new TextField();
	@FXML TextField tfEndLon = new TextField();
	
	@FXML Button btnEndNovo = new Button();
	@FXML Button btnEndSalvar = new Button();
	@FXML Button btnEndEditar = new Button();
	@FXML Button btnEndExc = new Button();
	@FXML Button btnEndCan = new Button();
	@FXML Button btnEndPesq = new Button();
	
	@FXML Button btnEndMaps = new Button ();
	
	
	@FXML Button btnDenAtualizar = new Button();
	@FXML TextField tfEndPesq = new TextField();
	
	@FXML Button btnEndLatLon = new Button();
	
	@FXML AnchorPane aPaneEnd = new AnchorPane();
	
	//-- TableView endereco --//
	@FXML private TableView <EnderecoTabela> tvLista;
	
	@FXML TableColumn<EnderecoTabela, String> tcDesEnd;
	@FXML TableColumn<EnderecoTabela, String> tcEndRA;
	@FXML TableColumn<EnderecoTabela, String> tcEndCid;
	
	
	
	// capturar enderereco para a TabInterfController
	private static Endereco eGeral;
	
	//-- botao para chamar o mapa javascript --//
	@FXML Image imgGetCoord = new Image(TabEnderecoController.class.getResourceAsStream("/images/getCoord.png")); 
	@FXML Button btnCoord = new Button ();
	@FXML Image imgMap = new Image(TabEnderecoController.class.getResourceAsStream("/images/map.png"));
	
	@FXML Button btnEndCoord;
	@FXML Image imgEndCoord = new Image(TabVistoriaController.class.getResourceAsStream("/images/mapCoord.png"));

	@FXML Button btnEndCoordMap = new Button();
	@FXML Image imgEndCoordMap = new Image(TabVistoriaController.class.getResourceAsStream("/images/mapCoord.png"));
	
	GoogleMap google;
	public void btnEndCoordHab (ActionEvent event) {
	  adicMarcador();
	  
	}
	
	Double lat = -15.775073004902042;
	Double lng = -47.940351677729836;
	
	// adicMarcador(); 
	public void adicMarcador () {
		
		System.out.println("latitude endereço " + eGeral.getLat_Endereco() + " e " + eGeral.getLon_Endereco());
		
    	if (eGeral.getLat_Endereco() != null  && eGeral.getLon_Endereco() != null ) {
        	lat = Double.parseDouble(eGeral.getLat_Endereco().toString());
    		lng = Double.parseDouble(eGeral.getLon_Endereco().toString());
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
	
	//-- coordenadas do mapa javascript --//
	public static String latDec = "-15";
	public static String lngDec = "-47";
	public static String endMap = "";
	
	
	//-- combobox -Regiao Administrativa --//	
		@FXML
		ChoiceBox<String> cbEndRA = new ChoiceBox<String>();
			ObservableList<String> olEndRA = FXCollections
				.observableArrayList(
						
						"Águas Claras",
						"Brasília",
						"Brazlândia",
						"Candangolândia",
						"Ceilândia",
						"Cruzeiro",
						"Fercal",
						"Gama",
						"Guará",
						"Itapoã",
						"Jardim Botânico",
						"Lago Norte",
						"Lago Sul",
						"Núcleo Bandeirante",
						"Paranoá",
						"Park Way",
						"Planaltina",
						"Recanto das Emas",
						"Riacho Fundo II",
						"Riacho Fundo",
						"Samambaia",
						"Santa Maria",
						"São Sebastião",
						"SCIA",
						"SIA",
						"Sobradinho II",
						"Sobradinho	",
						"Sudoeste/Octogonal",
						"Taguatinga	",
						"Varjão	",
						"Vicente Pires"
						
						); 	
	
			//-- combobox - unidade federal --//
			@FXML
			ChoiceBox<String> cbEndUF = new ChoiceBox<String>();
				ObservableList<String> olEndUF = FXCollections
					.observableArrayList("DF" , "GO", "Outro");

	
	@FXML Label lblEndereco = new Label();
	
	//-- string para chamar as coordenadas corretas do mapa --//
	String strHTMLMap;
		
	// --- objeto para passar os valor pelo MainControoler para outro controller --- //
	public Demanda dGeralEnd;
	
	//-- String de pesquisa de enderecos --//
	String strPesquisaEnd = "";
	
	ObservableList<EnderecoTabela> obsList;
	
 	// --- metodo para listar endereco --- //
 	public void listarEnderecos (String strPesquisa) {
 		
 	// --- conexao - listar enderecos --- //
	EnderecoDao enderecoDao = new EnderecoDao();
	List<Endereco> enderecoList = enderecoDao.listEndereco(strPesquisaEnd);
	obsList = FXCollections.observableArrayList();
	
	
	if (!obsList.isEmpty()) {
		obsList.clear();
	}
	
		for (Endereco endereco : enderecoList) {
			
		EnderecoTabela endTab = new EnderecoTabela(
				endereco.getCod_Endereco(),
				endereco.getDesc_Endereco(),
				endereco.getRA_Endereco(),
				endereco.getCEP_Endereco(), 
				endereco.getCid_Endereco(),
				endereco.getUF_Endereco(),
				endereco.getLat_Endereco(),
				endereco.getLon_Endereco(),
				endereco.getListDemandas()
				);
			
			obsList.add(endTab);
				
	}
		
		tvLista.setItems(obsList); 
	
 	}
 	
 	// método selecionar endereço -- //
 	public void selecionarEndereco () {
	
		tvLista.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
			public void changed(ObservableValue<?> observable , Object oldValue, Object newValue) {
			
			EnderecoTabela endTab = (EnderecoTabela) newValue;
			
			if (endTab == null) {
				
				tfEnd.setText("");
				
				tfEndCep.setText("");
				tfEndCid.setText("");
				//tfEndUF.setText("");
				tfEndLat.setText("");
				tfEndLon.setText("");
				
				btnEndNovo.setDisable(true);
				btnEndSalvar.setDisable(true);
				btnEndEditar.setDisable(false);
				btnEndExc.setDisable(false);
				btnEndCan.setDisable(false);
				
			} else {

				// -- preencher os campos -- //
				tfEnd.setText(endTab.getDesc_Endereco());
				cbEndRA.setValue(endTab.getRA_Endereco());  //tfEndRA.setText(endTab.getRA_Endereco());
				tfEndCep.setText(endTab.getCEP_Endereco());
				tfEndCid.setText(endTab.getCid_Endereco());
				cbEndUF.setValue(endTab.getUF_Endereco());  //tfEndUF.setText(endTab.getUF_Endereco());
				tfEndLat.setText(endTab.getLat_Endereco().toString());
				tfEndLon.setText(endTab.getLon_Endereco().toString());
				
				// -- habilitar e desabilitar botoes -- //
				btnEndNovo.setDisable(true);
				btnEndSalvar.setDisable(true);
				btnEndEditar.setDisable(false);
				btnEndExc.setDisable(false);
				btnEndCan.setDisable(false);
				
				eGeral = new Endereco(endTab);
				
				main.pegarEnd(eGeral);
				
				Double lat = Double.parseDouble(tfEndLat.getText());
				Double  lng = Double.parseDouble(tfEndLon.getText() );
				
				if (wv1 == null) {
					
					String strMarcador = "" +
	                        "window.lat = " + lat + ";" +
	                        "window.lon = " + lng + ";" +
	                        "document.goToLocation(window.lat, window.lon);";
					
					abrirMapa(strMarcador);
					
				} else
				{
					webEng.executeScript("" +
	                        "window.lat = " + lat + ";" +
	                        "window.lon = " + lng + ";" +
	                        "document.goToLocation(window.lat, window.lon);"
	                    );
			
				}
				
			}
			}
		});
	}
 	
 	// -- botao novo - - //
	public void btnEndNovoHab (ActionEvent event) {
		
		tfEnd.setText("");
		
		cbEndRA.setValue(null);
		
		tfEndCep.setText("");
		tfEndCid.setText("Brasília");
		
		cbEndUF.setValue("DF");
		
		tfLinkEnd.setText("");
		tfEndLat.setText("");
		tfEndLon.setText("");
		
		
		tfEnd.setDisable(false);
		cbEndRA.setDisable(false);
		
		
		tfEndCep.setDisable(false);
		tfEndCid.setDisable(false);
		cbEndUF.setDisable(false);
		tfEndLat.setDisable(false);
		tfEndLon.setDisable(false);
		tfLinkEnd.setDisable(false);
		
		btnEndSalvar.setDisable(false);
		btnEndEditar.setDisable(true);
		btnEndExc.setDisable(true);
		btnEndEditar.setDisable(true);
		
	}
	
	
	// --  botao salvar -- //
	public void btnSalvarHab (ActionEvent event) {
		
		obsList = FXCollections.observableArrayList();
			
		if (tfEndLat.getText().isEmpty() || 
				tfEndLon.getText().isEmpty()) {
			
			Alert a = new Alert (Alert.AlertType.ERROR);
			a.setTitle("Alerta!!!");
			a.setContentText("Coordenadas inválidas!!!");
			a.setHeaderText(null);
			a.show();
			
		} 
		
		else if (dGeralEnd == null) {
			
			Alert aLat = new Alert (Alert.AlertType.ERROR);
			aLat.setTitle("Alerta!!!");
			aLat.setContentText("Documento relacionado não selecionado!!!");
			aLat.setHeaderText(null);
			aLat.show();
		}
		
			else {
			
				if (tfEnd.getText().isEmpty()) {
					
					Alert a = new Alert (Alert.AlertType.ERROR);
					a.setTitle("Alerta!!!");
					a.setContentText("Informe: Endereço do Empreendimento!!!");
					a.setHeaderText(null);
					a.show();
					
				} else {
					
						Endereco endereco = new Endereco();
							
							endereco.setDesc_Endereco(tfEnd.getText());
							endereco.setRA_Endereco(cbEndRA.getValue());    //endereco.setRA_Endereco(tfEndRA.getText());
							endereco.setCEP_Endereco(tfEndCep.getText());
							endereco.setCid_Endereco(tfEndCid.getText());
							endereco.setUF_Endereco(cbEndUF.getValue());  //endereco.setUF_Endereco(tfEndUF.getText());
							
							try {
							endereco.setLat_Endereco(Double.parseDouble(tfEndLat.getText()));
							endereco.setLon_Endereco(Double.parseDouble(tfEndLon.getText()));
							
						
									Demanda demanda = new Demanda();
									
										demanda = dGeralEnd;
										demanda.setDemEnderecoFK(endereco);
										endereco.getListDemandas().add(dGeralEnd);
									
									EnderecoDao enderecoDao = new EnderecoDao();
									
										enderecoDao.salvaEndereco(endereco);
										enderecoDao.mergeEnd(endereco);
										
										// pegar o valor, levar para o MainController  e depois para o label lblEnd no InterfController
										eGeral = endereco;
										main.pegarEnd(eGeral);
										
										//-- modular botoes--//
										modularBotoesInicial ();
										
										EnderecoTabela endTab = new EnderecoTabela(
												endereco.getCod_Endereco(),
												endereco.getDesc_Endereco(),
												endereco.getRA_Endereco(),
												endereco.getCEP_Endereco(), 
												endereco.getCid_Endereco(),
												endereco.getUF_Endereco(),
												endereco.getLat_Endereco(),
												endereco.getLon_Endereco(),
												endereco.getListDemandas()
												);
											
										
										obsList.add(endTab);
										
										tvLista.setItems(obsList); 
										
										selecionarEndereco();
										
										Alert a = new Alert (Alert.AlertType.INFORMATION);
										a.setTitle("Parabéns!!!");
										a.setContentText("Cadastro salvo com sucesso!!!");
										a.setHeaderText(null);
										a.show();
								} 
							
								catch (Exception e) {
									
									Alert a = new Alert (Alert.AlertType.ERROR);
									a.setTitle("Atenção!!!");
									a.setContentText("Formato das coordenadas incorreto!!!" + "[ " + e + " ]");
									a.setHeaderText(null);
									a.show();
								}
				}
			
		}
			
	}
	
	// -- botao editar -- //
	public void btnEndEditarHab (ActionEvent event) {
		
		
		if (tfEnd.isDisable()) {
			
			tfEnd.setDisable(false);
			cbEndRA.setDisable(false);  //tfEndRA.setDisable(false);
			tfEndCep.setDisable(false);
			tfEndCid.setDisable(false);
			cbEndUF.setDisable(false);  //tfEndUF.setDisable(false);
			tfEndLat.setDisable(false);
			tfEndLon.setDisable(false);
			tfLinkEnd.setDisable(false);
				
		} else {
			
			if (tfEndLat.getText().isEmpty()|| tfEndLon.getText().isEmpty() ) {
				
				Alert a = new Alert (Alert.AlertType.ERROR);
				a.setTitle("Alerta!!!");
				a.setContentText("Coordenadas inválidas!!!");
				a.setHeaderText(null);
				a.show();
				
			} 
			
			else if (dGeralEnd == null) {
				
				Alert aLat = new Alert (Alert.AlertType.ERROR);
				aLat.setTitle("Alerta!!!");
				aLat.setContentText("Documento relacionado não selecionado!!!");
				aLat.setHeaderText(null);
				aLat.show();
			}
			
			else {
		
					EnderecoTabela endTab = tvLista.getSelectionModel().getSelectedItem();
					Endereco endereco = new Endereco(endTab);
					
					endereco.setDesc_Endereco(tfEnd.getText());
					endereco.setRA_Endereco(cbEndRA.getValue());   //endereco.setRA_Endereco(tfEndRA.getText());
					endereco.setCEP_Endereco(tfEndCep.getText());
					endereco.setCid_Endereco(tfEndCid.getText());
					endereco.setUF_Endereco(cbEndUF.getValue());  //endereco.setUF_Endereco(tfEndUF.getText());
					endereco.setLat_Endereco(Double.parseDouble(tfEndLat.getText()));
					endereco.setLon_Endereco(Double.parseDouble(tfEndLon.getText()));
				
					
					Demanda demanda = new Demanda();
					
					demanda = dGeralEnd;
					demanda.setDemEnderecoFK(endereco);
					
					endereco.getListDemandas().add(dGeralEnd);
					
					EnderecoDao enderecoDao = new EnderecoDao();
				
					enderecoDao.mergeEnd(endereco);
					
					
					// pegar o valor, levar para o MainController  e depois para o label lblEnd no InterfController
					eGeral = endereco;
					main.pegarEnd(eGeral);
					
					// atualizar dados na tabela
					obsList.remove(endTab);
								
					endTab = new EnderecoTabela(
							endereco.getCod_Endereco(),
							endereco.getDesc_Endereco(),
							endereco.getRA_Endereco(),
							endereco.getCEP_Endereco(), 
							endereco.getCid_Endereco(),
							endereco.getUF_Endereco(),
							endereco.getLat_Endereco(),
							endereco.getLon_Endereco(),
							endereco.getListDemandas()
							);
						
						
					obsList.add(endTab);
					tvLista.setItems(obsList);
					
					modularBotoesInicial (); 
					
					Alert a = new Alert (Alert.AlertType.INFORMATION);
					a.setTitle("Parabéns!!!");
					a.setContentText("Cadastro editado com sucesso!!!");
					a.setHeaderText(null);
					a.show();
					
			}
		
		}	
	}
	
	// -- botao excluir -- //
	public void btnEndExcHab (ActionEvent event) {
		
		EnderecoTabela endTab = tvLista.getSelectionModel().getSelectedItem();
		
		int id = endTab.getCod_Endereco();
		
		EnderecoDao enderecoDao = new EnderecoDao();
		
		try {
			
			enderecoDao.removeEndereco(id);
			
			obsList.remove(endTab);
			tvLista.setItems(obsList);
			
			// não precisa do selecionar enderecos? //
			
			modularBotoesInicial();
			
			Alert a = new Alert (Alert.AlertType.INFORMATION);
			a.setTitle("Parabéns!!!");
			a.setContentText("Cadastro deletado com sucesso!!!");
			a.setHeaderText(null);
			a.show();
			
		}
		catch (Exception e) {
			//-- Alerta de demandas salva --//
			Alert a = new Alert (Alert.AlertType.ERROR);
			a.setTitle("Alerta!!!");
			a.setContentText("Há denúncia associada a este endereço!" + "[ " + e + " ]");
			a.setHeaderText(null);
			a.show();
			
		}
		
	}
	
	//-- botao cancelar --//
	public void btnEndCanHab (ActionEvent event) {

		modularBotoesInicial ();
		
		tfEnd.setText("");
		
		cbEndRA.setValue(null);
		
		tfEndCep.setText("");
		
		cbEndUF.setValue(null);
		
		tfLinkEnd.setText("");
		tfEndLat.setText("");
		tfEndLon.setText("");
		
	}
	
	//-- botao pesquisar endereco --//
	public void btnEndPesqHab (ActionEvent event) {
		
		strPesquisaEnd = tfEndPesq.getText();
		
		//enderecoList = enderecoDao.listEndereco();
			// nÃ£o entendi este endereÃ§o list aqui
		
		listarEnderecos (strPesquisaEnd);
		
		modularBotoesInicial (); 
	
	}
	
	//-- botao obter coordenadas do link --//
	public void  btnEndLatLonHab (ActionEvent event) {
		
		if (tfLinkEnd.getText().isEmpty()) {
			
			Alert aLink = new Alert (Alert.AlertType.ERROR);
			aLink.setTitle("Alerta!!!");
			aLink.setContentText("Não há link associado à  solicitação!");
			aLink.setHeaderText(null);
			aLink.show();
			
		} else {
			
			String linkEndCoord = (tfLinkEnd.getText());
			
			int latIni= linkEndCoord.indexOf("@");
			String lat = linkEndCoord.substring(latIni);
			
			int latF = lat.indexOf(",");
			String latitude = lat.substring(1, latF);
			
			String longitude = lat.substring(latF + 1, latF + latitude.length());
			
			 /*
			 tirei o +1 para resolver o aparecimento da virgula neste link
			 https://www.google.com.br/maps/place/Brazl%C3%A2ndia,+Bras%C3%ADlia+-+DF/@-15.8422254,-48.097489,10364m/data=!3m1!1e3!4m5!3m4!1s0x935bb399f0e712b7:0xe5dd05c541a49871!8m2!3d-15.6701849!4d-48.200585
			 String longitude = lat.substring(latF + 1, latF + 1 + latitude.length());
			 */
			
			tfEndLat.setText(latitude);
			tfEndLon.setText(longitude);
			
		}
	}
	
	// html do mapa com as coordenadas do endereco //
	String htmlEndereco = "";
	
	
	
	//-- Botao pesquisar documento na TabEndereco --//
	public void btnBuscarDocHab (ActionEvent event) {
		
	}
	
	//GoogleMap google;
	
	//-- buscador de enderecos e coordenadas --//
		public void btnEndMapsHab (ActionEvent event) throws IOException {
			
			google = new GoogleMap();
			
			Group group = new Group();
			group.getChildren().addAll(google, btnCoord, btnEndCoordMap);
			
			Scene scene = new Scene(group);
			
			btnCoord.setLayoutY(8);
			btnCoord.setLayoutX(502);
			btnCoord.setGraphic(new ImageView(imgGetCoord));
			
			btnEndCoordMap.setLayoutY(8.5);
			btnEndCoordMap.setLayoutX(620);
			btnEndCoordMap.setGraphic(new ImageView(imgEndCoordMap));
			
			btnCoord.setOnAction(new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent e) {
	            	
	            	tfEndLat.setText(latDec);
	        		tfEndLon.setText(lngDec);
	        		
	        		//-- sugestão de endereço, cep etc --//
	        		String end[] = endMap.split(Pattern.quote(","));
	        		
	        		ObservableList<String> documentos = FXCollections.observableArrayList(end);
	    			ListView<String> listView = new ListView<String>(documentos);
	    			
	    				Scene scene = new Scene(listView);
	    				Stage stage = new Stage(); // StageStyle.UTILITY - tirei para ver como fica, se aparece o minimizar
	    				stage.setWidth(400);
	    				stage.setHeight(300);
	    			    stage.setScene(scene);
	    			    stage.setMaximized(false);
	    			    stage.setResizable(false);
	    			    stage.setX(1030.0);
	    			    stage.setY(550.0);
	    			   
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
	        	});
	
			 btnEndCoordMap.setOnAction(new EventHandler<ActionEvent>() {
		            @Override public void handle(ActionEvent e) {
		            	adicMarcador();
		            	
		           }
		     });
			 
			Stage stage = new Stage(); // StageStyle.UTILITY - tirei para ver como fica, se aparece o minimizar
			stage.setWidth(1250);
			stage.setHeight(750);
	        stage.setScene(scene);
	        stage.setMaximized(false);
	        stage.setResizable(false);
	        stage.show();
	        
		}
		
		
	//-- INITIALIZE --//
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
		tcDesEnd.setCellValueFactory(new PropertyValueFactory<EnderecoTabela, String>("Desc_Endereco")); 
		tcEndRA.setCellValueFactory(new PropertyValueFactory<EnderecoTabela, String>("RA_Endereco")); 
		tcEndCid.setCellValueFactory(new PropertyValueFactory<EnderecoTabela, String>("CEP_Endereco")); 
		
		tfEndPesq.setOnKeyReleased(event -> {
	  		  if (event.getCode() == KeyCode.ENTER){
	  		     btnEndPesq.fire();
	  		  }
	  		});
	        
		//-- Modular a forma de abrir dos botÃµes --//
		modularBotoesInicial ();
		//-- Selecionar endereco --//
		selecionarEndereco ();
		
		cbEndRA.setItems(olEndRA);
		
		cbEndUF.setValue("DF");
		cbEndUF.setItems(olEndUF);
		
		btnEndMaps.setGraphic(new ImageView(imgMap));
		//btnMapCoord.setGraphic(new ImageView(imgMapCoord));
		
		btnEndCoord.setGraphic(new ImageView(imgEndCoord));
	}

	//-- Modular os botoes na inicializacao do programa --//
	private void modularBotoesInicial () {
		
		tfEnd.setDisable(true);
		cbEndRA.setDisable(true);  //tfEndRA.setDisable(true);
		tfEndCep.setDisable(true);
		tfEndCid.setDisable(true);
		cbEndUF.setDisable(true);  //tfEndUF.setDisable(true);
		tfEndLat.setDisable(true);
		tfEndLon.setDisable(true);
		tfLinkEnd.setDisable(true);
		tfLinkEnd.setText("");
		
		btnEndSalvar.setDisable(true);
		btnEndEditar.setDisable(true);
		btnEndExc.setDisable(true);
		btnEndNovo.setDisable(false);
		
	}
	
	WebView wv1;
	WebEngine webEng;
	
	
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
			
		//docHtml.select("script").prepend("var uluru = {lat: " + latMap + ", lng: " + lonMap + "};");
	
		strHTMLMap = docHtml.toString();
		*/

		wv1 = new WebView();
		webEng = wv1.getEngine();
		webEng.load(getClass().getResource("/html/enderecoMap.html").toExternalForm());
			
		wv1.setPrefSize(700,500);
		wv1.getEngine();
	
		BorderPane root = new BorderPane();
		root.setCenter(wv1);
		root.setPrefSize(887, 420);
		root.setLayoutY(410);
		root.setLayoutX(127);
		
		aPaneEnd.getChildren().add(root);
	
		webEng.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (Worker.State.SUCCEEDED.equals(newValue)) {
            	webEng.executeScript(strMarcador);
            }
        });
		
	}
	
	//-- Metodo getWebView para o mapa do endereco --//
	public void getWvEndMap () {
		
		wv1 = new WebView();
		webEng = wv1.getEngine();
		webEng.loadContent(strHTMLMap);
		
		wv1.setPrefSize(700,500);
		wv1.getEngine();
	
		BorderPane root = new BorderPane();
		root.setCenter(wv1);
		root.setPrefSize(700, 420);
		root.setLayoutY(395);
		root.setLayoutX(220);
		
		aPaneEnd.getChildren().add(root);
	
		webEng.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (Worker.State.SUCCEEDED.equals(newValue)) {
            	
            }
        });
		
	}
	
	//-- metodo atualizar latitude e longitude --//
	
	public void setLatLng (double lat, double lng, String address) {
		
		latDec = Double.toString(lat);
		lngDec = Double.toString(lng);
		
		try {
			
			lblEndereco.setText(address.toString());
		}
		
		 catch (Exception e) {
			 e.printStackTrace();
		 }
			
	}
	
	//-- Main Controller --//
	public void init(MainController mainController) {
		main = mainController;
	}

	
}


/* retirei pois consegui forma melhor de chamar as coordenadas dos pontos no mapa 
 
 //-- Botao atualizar mapa de acordo com as coordenadas --//
	public void btnEndAtualizarHab (ActionEvent event) throws IOException, ScriptException {
		
		
		
		String latMap = tfEndLat.getText();
		String lonMap = tfEndLon.getText();
		
		File file = null;
		
	
		file = new File (TabEnderecoController.class.getResource("/html/enderecoMap.html").getFile());
		
		
		
		Document docHtml = null;
		
		try {
			docHtml = Jsoup.parse(file, "UTF-8");  // retirei o  .clone()
		
			
		} catch (IOException e1) {
			System.out.println("Erro na leitura no parse Jsoup!!!");
			e1.printStackTrace();
		}
			
		docHtml.select("script").prepend("var uluru = {lat: " + latMap + ", lng: " + lonMap + "};");
	
		strHTMLMap = docHtml.toString();
		
		getWvEndMap ();
		
		//strLinkMap = docHtml.toString();
		
		//getWvEndMap ();
		
	}
 */

