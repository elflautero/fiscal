package controllers;

import java.awt.List;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.text.TableView;

import fiscalizacao.DocumentosOutorga;
import fiscalizacao.LeitorExcel;
import fiscalizacao.Outorga;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.web.PopupFeatures;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import netscape.javascript.JSException;


public class TabNavegadorController implements Initializable{
	
	//-- Link de entrada do navegador WebView --//
	String link = "https://www.w3schools.com/howto/howto_js_popup.asp";
			
			// "https://www.w3schools.com/howto/howto_js_popup.asp";
			//"https://sei.df.gov.br/sip/login.php?sigla_orgao_sistema=GDF&sigla_sistema=SEI";
	
	int contDocSei;
	//-- Array de Strings - Documentos Capturados --//
	String[] docsSei; // = new String [contDocSei];
	
	public static String strHTML;
	
	// array list para a captura de dados dos documentos --//
	ArrayList<String> docsList = new ArrayList<String>();
		
	@FXML Pane pBrowser;
	@FXML Pane pBtnLef;
	@FXML Pane pBtnRig;
	
	@FXML AnchorPane apNavegador = new AnchorPane();
	
	@FXML Button btnSei = new Button();
	
	@FXML Button btnSeiTrein = new Button();
	
	@FXML Button btnCapturarDocs = new Button();
	@FXML Button btnMostrarDocs = new Button();
	
	@FXML Button btnGoogle = new Button();
	@FXML Button btnWebBrowser = new Button();
	
	@FXML Image imgGoogle = new Image(TabNavegadorController.class.getResourceAsStream("/images/google.png"));
	@FXML Image imgSei = new Image(TabNavegadorController.class.getResourceAsStream("/images/seiOriginal.png"));
	@FXML Image imgSeiTrein = new Image(TabNavegadorController.class.getResourceAsStream("/images/seiTreinOriginal.png"));
	@FXML Image imgCapturar = new Image(TabNavegadorController.class.getResourceAsStream("/images/doc24.png"));
	@FXML Image imgMostrar = new Image(TabNavegadorController.class.getResourceAsStream("/images/showDocs24.png"));
	@FXML Image imgWeb = new Image(TabNavegadorController.class.getResourceAsStream("/images/webBrowser.png"));
	Image imgExcel = new Image(TabNavegadorController.class.getResourceAsStream("/images/imgExcel.png"));
	Image imgDoc = new Image(TabNavegadorController.class.getResourceAsStream("/images/imgDoc.png"));
	Image imgAnexo = new Image(TabNavegadorController.class.getResourceAsStream("/images/imgAnexo.png"));
	
	WebView wv1;
	
	
	
	// numero do iframe para inserir o relatorio, TN ou AIA
	public static int numIframe;
	
	// table view de documentos capturados do sei
	ObservableList<String []> numDosObservable;
	
	TableView tv;
	
	WebView wv2;
	
	//-- botão google --//
	public void btnWebBrowserHab (ActionEvent event) {
		
		navegarWeb();
	}
		
	
	
	//-- botão google --//
	public void btnGoogleHab (ActionEvent event) {
		
		link = "https://www.google.com.br/search?q=sei+gdf&oq=sei+gdf+&aqs=chrome..69i57j69i60l3j0l2.4027j0j4&sourceid=chrome&ie=UTF-8";
		wv1.getEngine().load(link);
		
		//navegarWeb();
		System.out.println("btn google clicado");
		
	}
	
	//-- botão sei teinamento --//
	public void btnSeiTreinHab (ActionEvent event) {
		
		link = "http://treinamento3.sei.df.gov.br/sip/login.php?sigla_orgao_sistema=GDF&sigla_sistema=SEI";
		wv1.getEngine().load(link);
		
		System.out.println("btn sei treinamento clicado");
		
	}
	//-- botão sei --//
	public void btnSeiHab (ActionEvent event) {
			
			link = "https://sei.df.gov.br/sip/login.php?sigla_orgao_sistema=GDF&sigla_sistema=SEI";
			wv1.getEngine().load(link);
			
			System.out.println("btn sei treinamento clicado");
			
		}
	
	
	//-- botão capturar --//
	public void btnCapturarDocsHab (ActionEvent event) {
		
			// adicionar exececoes https://github.com/Microsoft/ClearScript/issues/16
		
			wv1.getEngine().executeScript(
					
			"var doc;"

			+ 	"$(function() {"

			+	"$( '#ifrArvore' ).load(function(){"
			 
			//+	"alert('iframe carregado');"
			        
			//+	"alert($(this).contents().find('span'));"
			        
			+	"$(this).contents().find( 'span' ).css( 'background-color', '#BADA55' );"
				
			+	"doc = ($(this).contents().find('span'));" 
				
			//+	"alert(doc);"
			  
			+	"for (var i = 0; i < doc.length; i++) {"
			
			//+	"alert (doc[i].textContent);"  
				
			+	"}"
			  
			+   "});"

			+ 	"});"
			
			);
			
	}	
		
	// botão mostrar //
	public void btnMostrarDocsHab (ActionEvent event ) {

		// adicionar exceção https://github.com/Microsoft/ClearScript/issues/16
		
		contDocSei = (int) wv1.getEngine().executeScript("doc.length"); // quantidade de docs capturados
		
		System.out.println("valor de contDocSei " + contDocSei);
		
		docsSei = new String [contDocSei]; // para limitar a quantidade de strings na array
		
		
			for (int i = 0; i < contDocSei; i++ ) {
				
				String strDocSei = (String) wv1.getEngine().executeScript("doc[" + i + "].textContent");
			
				docsSei [i] = strDocSei;
				
			}
			
			// serve para que?
			for (int z = 0; z < contDocSei; z++) {
				docsList.add(docsSei[z]); 
			}

	
			ObservableList<String> documentos = FXCollections.observableArrayList(docsSei);
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
				stage.setWidth(400);
				stage.setHeight(300);
			    stage.setScene(scene);
			    stage.setMaximized(false);
			    stage.setResizable(false);
			    stage.setTitle("Documentos SEI");
			    stage.getIcons().add(new Image("/images/docCap.png"));
			    					
			    System.out.println();
			    System.out.println(apNavegador.getWidth());
			    
			    stage.setAlwaysOnTop(true); // sempre por cima das outras telas
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
	
		//importações para a outorga//
	
			ObservableList<Outorga> obsList;
			
			LeitorExcel leitorExcel = new LeitorExcel();
			
			ComboBox<Outorga> cbOutorga;
			ComboBox<String> cbParecerOutorga;
			
			ObservableList<String> cbParecerOutorgaOpcoes;
			
			Boolean b = false;
			
			Button btnAxexo;
			Button btnExcel;
			Button btnTabela;
			
			Pane pane;
			Pane pTabela;

			Outorga outorga;
			
			
			WebView webOutorgas;
			WebEngine engOutorga;
			
			String strHTMLRel;
			String strParecerDespacho = "PARECER";
			
			//xxxxxxxxxxxxxxxxxxxxx//
			
	
	//-- metodo navegar --//
	public void navegarWeb() {
		
		
		wv1 = new WebView();
		
		// redimensionar navegador de acordo com a tela
		wv1.minWidthProperty().bind(apNavegador.widthProperty().subtract(35));
		wv1.minHeightProperty().bind(apNavegador.heightProperty().subtract(150));
		
		pBrowser.getChildren().add(wv1);
		
		btnAxexo = new  Button();
		btnAxexo.setGraphic(new ImageView(imgAnexo));
		
		btnAxexo.setMinHeight(25);
		btnAxexo.setMaxHeight(25);
		
		btnAxexo.setMaxWidth(30);
		btnAxexo.setMinWidth(30);
		
		btnAxexo.setLayoutX(841);
		
		
		
		// -------- //
		btnExcel = new Button();
		btnExcel.setGraphic(new ImageView(imgExcel));
		btnExcel.setLayoutX(222);
		btnExcel.setMinHeight(25);
		
		
		/*
		// -------- //
		btnTabela = new Button("...");
		btnTabela.setLayoutX(310);
		*/
		
		cbParecerOutorgaOpcoes = FXCollections.observableArrayList(
    	        "PARECER",
    	        "DESPACHOS"
    	    ); 
		
		cbParecerOutorga = new ComboBox<>(cbParecerOutorgaOpcoes);
		
		cbParecerOutorga.setLayoutX(255);
		cbParecerOutorga.setMinWidth(110);
		cbParecerOutorga.setMaxWidth(110);
		cbParecerOutorga.setValue("PARECER");
		
		cbOutorga = new ComboBox<>();
		
		cbOutorga.setLayoutX(366);
		cbOutorga.setMinWidth(400);
		cbOutorga.setMaxWidth(400);
		
		
		
		wv1.getEngine().setCreatePopupHandler(new Callback<PopupFeatures, WebEngine>() {

		    public WebEngine call(PopupFeatures p) {
		    	
			    	wv2 = new WebView(); 
					wv2.setPrefHeight(645.0);
		            wv2.setPrefWidth(1140.0);
		            wv2.setLayoutY(35);
		            
		    		Button btnIframe = new Button ();
		    		btnIframe.setMinHeight(25);
		    		
		    		btnIframe.setLayoutX(808);
					btnIframe.setGraphic(new ImageView(imgDoc));
					
		    		
		    		System.out.println("numero  iframe " + numIframe);
		    		
		    		ChoiceBox<String> cbNumIframe = new ChoiceBox<String>();
					ObservableList<String> olNumIframe = FXCollections
						.observableArrayList(
								"0",
								"1",
								"2",
								"3"
								
								);
					
					cbNumIframe.setItems(olNumIframe);
					cbNumIframe.setValue(String.valueOf(numIframe));
					
					cbNumIframe.setLayoutX(767);
					
			
		            Group group = new Group();
					group.getChildren().addAll(btnIframe, wv2, cbNumIframe, btnAxexo, btnExcel, cbOutorga, cbParecerOutorga);
		    	
					// incluir documento no sei //
					btnIframe.setOnAction(new EventHandler<ActionEvent>() {
			            @Override public void handle(ActionEvent e) {
			            	
			        			//-- imprimir o relatório ou tn no editor do SEI --//
								wv2.getEngine().executeScript(
				            			"var x = document.getElementsByTagName('iframe')["+numIframe+"];"
				            			+ "var y = x.contentDocument;" 
										+ "y.body.innerHTML = " + strHTML + ";"
				            			);
								
			            }
			        });
					
					// ação do botão para receber o excel
					btnAxexo.setOnAction(new EventHandler<ActionEvent>() {
					    public void handle(ActionEvent e) {
					    	
					    	//webOutorgas = new WebView();
				        	//engOutorga = webOutorgas.getEngine();
					    	
					    	try {
				        	
					    			engOutorga.load(getClass().getResource("/html/outorgaSubterraneaAnexo.html").toExternalForm());
					    		}
					        	catch (Exception e1) {
					        		
			                		Alert a = new Alert (Alert.AlertType.ERROR);
			    					a.setTitle("Alerta!!!");
			    					a.setContentText("Documento não encontrado!!! ");
			    					System.out.println("erro btnAnexo - linha 410");
			    					e1.printStackTrace();
			    					
			    					a.setHeaderText(null);
			    					a.show();
			                	}
				        	
				        	engOutorga.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>()
					        {
					            public void changed(final ObservableValue<? extends Worker.State> observableValue,
					                                final Worker.State oldState,
					                                final Worker.State newState)
					            {
					                if (newState == Worker.State.SUCCEEDED)
					                {
					                  
					                	// capturar o html
					                	String strHTMLAnexo = (String) engOutorga.executeScript("document.documentElement.outerHTML");
					                
					                	// chama o construtor para modificações no html
					                	DocumentosOutorga docOut = new DocumentosOutorga();
								    	docOut.setHtmlRel(strHTMLAnexo);
								    	
								    	// retorna o html modificado
								    	strHTMLAnexo = docOut.criarDocumento(outorga);
						                
								    	// captura o html existente na parte específica do depacho
								    	try {
								    		
						                wv2.getEngine().executeScript(
												
						                		"var z = y.body.getElementsByTagName('a')[0].innerHTML;"
												
						            			);
						                
										    	}
								                	catch (JSException js) {
								                		Alert a = new Alert (Alert.AlertType.ERROR);
								    					a.setTitle("Alerta!!!");
								    					a.setContentText("Tabela não encontrada no despacho!!! ");
								    					
								    					js.printStackTrace();
								    					
								    					a.setHeaderText(null);
								    					a.show();
								                	}
								    	
								    	
						                // transforma a variável javascript em variável java
						                String z = (String) wv2.getEngine().executeScript("z");
						                
						                // junta o que foi capturado no despacho com o html modificado
						                String a = z + "<p>" + strHTMLAnexo;
						                
						                // retirar os dois pontos " e espaço que atrapalham no javascript
						                
						                a = a.replace("\"", "'");
						    			a = a.replace("\n", "");
						    			
						    			a =  "\"" + a + "\"";
						    			
						    			// captura o iframe específico e adiciona tudo
						                wv2.getEngine().executeScript(
										"var x = document.getElementsByTagName('iframe')["+numIframe+"];"
				            			+ "var y = x.contentDocument;"
										+ "y.body.getElementsByTagName('a')[0].innerHTML = " + a + ";"
				            			);
						                
					                }}
					        }); // fim webengine
					    	
					
					    }
					}); // fim btnAnexo
					 
					
					
					
					// evento captura numero do choice box
					cbNumIframe.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
					      @Override
					      public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
					    	  numIframe = (Integer) number2;
					    	  System.out.println("ol num iframe : " + numIframe);
					      }
					    });
					
					// ação do botão para receber o excel
					btnExcel.setOnAction(new EventHandler<ActionEvent>() {
						
					    public void handle(ActionEvent e) {
					    	
					    	
					    	try {
						    	// para escolher o arquivo  no computador
						    	FileChooser fileChooser = new FileChooser();
						        fileChooser.setTitle("Selecione a planilha");
						        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XLS", "*.xls", "*.xlsm","*.xlsx"));        
						        File file = fileChooser.showOpenDialog(null);
						        
						        //abrirTabela();
						       
						        leitorExcel.setEndereco(file.toString());
						        
						        obsList = leitorExcel.getListaOutorgas();
						       // tvLista.setItems(obsList);
						        
						        cbOutorga.setItems(obsList);
						        
						        cbParecerOutorga.valueProperty().addListener(new ChangeListener<String>() {
						            @Override 
						            public void changed(ObservableValue<? extends String> ov, String oldValue, String newValue) {  
						            	
						                strParecerDespacho = newValue;       
						            }    
						        });
						        
						        // converter o objeto outorga e uma string com o nome, tipo de outorga etc
						        cbOutorga.setConverter(new StringConverter<Outorga>() {
									
									public String toString(Outorga o) {
										
										return o.getTipo() + "  |  " + o.getInteressado() + "  |  " + o.getTipoPoco() + "  |  " + o.getEndereco() ;
									}
									
									public Outorga fromString(String string) {
										
										return null;
									}
								});
								
						        
						        cbOutorga.setCellFactory(new Callback<ListView<Outorga>,ListCell<Outorga>>(){
						            @Override
						            public ListCell<Outorga> call(ListView<Outorga> l){
						                return new ListCell<Outorga>(){
						                    @Override
						                    protected void updateItem(Outorga o, boolean empty) {
						                    	
						                        super.updateItem(o, empty);
						                        
						                        if (o != null) {
						                        	
					                                setText(o.getTipo() + "  |  " + o.getInteressado() + "  |  " + o.getTipoPoco() + "  |  " + o.getEndereco());
					                                
					                                switch (o.getTipo()) {
								        			
							        				case "OUTORGA SUBTERRÂNEA": 
							        					setTextFill(Color.BLUE);
							        					break;
							        					
							        				case "OUTORGA SUBTERRÂNEA TRANSFERÊNCIA": 
							        					setTextFill(Color.BROWN);
							        					break;
							        					
							        				case "OUTORGA SUBTERRÂNEA INDEFERIMENTO": 
							        					setTextFill(Color.BLUEVIOLET);
							        					break;
						        						
							        				case "OUTORGA SUBTERRÂNEA MODIFICAÇÃO": 
							        					setTextFill(Color.GREEN);
							        					break;
						        						
							        				case "OUTORGA SUBTERRÂNEA RENOVAÇÃO": 
							        					setTextFill(Color.DARKSLATEGREY);
							        					break;
						        						
							        				case "REGISTRO SUBTERRÂNEA": 
							        					setTextFill(Color.DARKCYAN);
							        					break;
						        						
							        				case "REGISTRO SUBTERRÂNEA MODIFICAÇÃO": 
							        					setTextFill(Color.DARKGOLDENROD);
							        					break;
						        						
							        				case "REGISTRO SUBTERRÂNEA TRANSFERÊNCIA": 
							        					setTextFill(Color.DARKSLATEBLUE);
							        					break;	
							        				
					                                }
						                        }
					                            
						                    }
						                } ;
						            }
						        });
						        
						        // capturar o usuário escolhido no combobox
						        cbOutorga.valueProperty().addListener(new ChangeListener<Outorga>() {
						            
									@Override
									public void changed(ObservableValue<? extends Outorga> arg, Outorga oldOut, Outorga newOut) {

										webOutorgas = new WebView();
					        			engOutorga = webOutorgas.getEngine();
					        			
					        			String tipo = newOut.getTipo();
					        			outorga = newOut;
					        			
					        			
					        			if (strParecerDespacho.equals("PARECER")) {
					        				
					        				engOutorga.load(getClass().getResource("/html/parecerOutorgaSubterranea.html").toExternalForm());
					        				
					        			} else {
					        			
							        			switch (tipo) {
							        			
							        				case "OUTORGA SUBTERRÂNEA": 
							        					engOutorga.load(getClass().getResource("/html/outorgaSubterranea.html").toExternalForm());
							        					break;
							        					
							        					
							        				case "OUTORGA SUBTERRÂNEA TRANSFERÊNCIA": 
							        					engOutorga.load(getClass().getResource("/html/outorgaSubterraneaTransferencia.html").toExternalForm());
						        						break;
						        						
							        				case "OUTORGA SUBTERRÂNEA INDEFERIMENTO": 
							        					engOutorga.load(getClass().getResource("/html/outorgaSubterraneaIndeferimento.html").toExternalForm());
						        						break;
						        						
							        				case "OUTORGA SUBTERRÂNEA MODIFICAÇÃO": 
							        					engOutorga.load(getClass().getResource("/html/outorgaSubterraneoModificacao.html").toExternalForm());
						        						break;
						        						
							        				case "OUTORGA SUBTERRÂNEA RENOVAÇÃO": 
							        					engOutorga.load(getClass().getResource("/html/outorgaSubterraneaRenovacao.html").toExternalForm());
						        						break;
						        						
							        				case "REGISTRO SUBTERRÂNEA": 
							        					engOutorga.load(getClass().getResource("/html/registroSubterranea.html").toExternalForm());
						        						break;
						        						
							        				case "REGISTRO SUBTERRÂNEA MODIFICAÇÃO": 
							        					engOutorga.load(getClass().getResource("/html/registroSubterraneaModificacao.html").toExternalForm());
						        						break;
						        						
							        				case "REGISTRO SUBTERRÂNEA TRANSFERÊNCIA": 
							        					engOutorga.load(getClass().getResource("/html/registroSubterraneaTransferencia.html").toExternalForm());
						        						break;

							        			} //fim switch
						        		
					        			} //fim else
					        			
					        			
										engOutorga.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>()
								        {
								            public void changed(final ObservableValue<? extends Worker.State> observableValue,
								                                final Worker.State oldState,
								                                final Worker.State newState)
								            {
								                if (newState == Worker.State.SUCCEEDED)
								                {
								                  
								                	strHTMLRel = (String) engOutorga.executeScript("document.documentElement.outerHTML");
								                
								                	DocumentosOutorga docOut = new DocumentosOutorga();
											    	docOut.setHtmlRel(strHTMLRel);
											    
											    	strHTML = docOut.criarDocumento(newOut);
											    	cbNumIframe.setValue("2");
											    	
								                }
								               
								            }
								        });
										
									}
						        });
						          
						    	}
					    	
					    		catch (Exception ex) {
					    			System.out.println("a ação foi abortada " + ex);
					    			
					    		} // fim do try catch
					    }
					    
					    
				    	
					}); // fim do evento btnExcel
					
					Stage stage = new Stage();
					
		            stage.setScene(new Scene(group));
		            
		            stage.setMaximized(false);
			        stage.setResizable(false);
		            stage.show();
		            
		            return wv2.getEngine();
		      
		        }
		    
		    });
		

		//-- para imprimir no console java os alerts do javascript --//
		wv1.getEngine().setOnAlert(new EventHandler<WebEvent<String>>() {
		    @Override 
		    public void handle(WebEvent<String> event) {
		    	
		        System.out.println(event.getData());
		        
		        //-- Alerta de senha ou usuário errado --//
				Alert aLogin = new Alert (Alert.AlertType.ERROR);
				
					aLogin.setTitle("Atenção!!!");
					aLogin.setContentText(event.getData().toString());
					aLogin.setHeaderText(null);
					aLogin.show();
		    }
		});  
		
		    StackPane root = new StackPane();
		   
		    root.getChildren().add(wv1);
		    
		    //-- habilitar javascript --//
		    wv1.getEngine().setJavaScriptEnabled(true);
		    
		    wv1.getEngine().load(link);
		    
		    //tabNavegador.getChildren().add(wv1);
		    pBrowser.getChildren().add(wv1);
	}
	
	
	// métodos de remimensionar as tabs //
		public void redimWid (Number newValue) {
					apNavegador.setMinWidth((double) newValue);
				}
		public void redimHei (Number newValue) {
					apNavegador.setMinHeight((double) newValue);;
				}

	@FXML ScrollPane spNavegador;
	@FXML AnchorPane apBrowser;
	
	//-- INITIALIZE --//
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
		// -- inicitalizar o mapa -- //
		Platform.runLater(() ->{
			navegarWeb(); 
		});
		
		
		AnchorPane.setTopAnchor(spNavegador, 0.0);
	    AnchorPane.setRightAnchor(spNavegador, 0.0);
	    AnchorPane.setLeftAnchor(spNavegador, 0.0);
	    AnchorPane.setBottomAnchor(spNavegador, 35.0);
	    
	    spNavegador.heightProperty().addListener((observable, oldValue, newValue) -> {
           //System.out.println("altura do spNavegador " + newValue);
           
           apBrowser.setMinHeight((Double)newValue -1);
           apBrowser.setMaxHeight((Double)newValue -1);
        });
	    
	    
		btnGoogle.setGraphic(new ImageView(imgGoogle));
		btnCapturarDocs.setGraphic(new ImageView(imgCapturar));
		btnMostrarDocs.setGraphic(new ImageView(imgMostrar));
		btnSei.setGraphic(new ImageView(imgSei));
		btnSeiTrein.setGraphic(new ImageView(imgSeiTrein));
		btnWebBrowser.setGraphic(new ImageView(imgWeb));
	}
	
	
}



