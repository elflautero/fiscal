package controllers;

import java.awt.List;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.text.TableView;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
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
import javafx.scene.web.PopupFeatures;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Callback;


public class TabNavegadorController implements Initializable{
	
	//-- Link de entrada do navegador WebView --//
	String link = "https://sei.df.gov.br/sip/login.php?sigla_orgao_sistema=GDF&sigla_sistema=SEI";
	
	int contDocSei;
	//-- Array de Strings - Documentos Capturados --//
	String[] docsSei; // = new String [contDocSei];
	
	static String html;
	
	// array list para a captura de dados dos documentos --//
	ArrayList<String> docsList = new ArrayList<String>();
		
	@FXML Pane pBrowser;
	@FXML Pane pNavBotoes1;
	@FXML Pane pNavBotoes2;
	
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
	
	WebView wv1;
	
	// numero do iframe para inserir o relatorio, TN ou AIA
	static int numIframe;
	
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
	
	
	//-- mÃ©todo navegar --//
	public void navegarWeb() {
		
		wv1 = new WebView();
		//wv1.setPrefSize(1117.0,627.0);
		
		// redimensionar navegador de acordo com a tela
		wv1.minWidthProperty().bind(apNavegador.widthProperty().subtract(10));
		wv1.minHeightProperty().bind(apNavegador.heightProperty().subtract(800));
		 
		//wv1.setLayoutY(78);
		pBrowser.getChildren().add(wv1);
		
		
		wv1.getEngine().setCreatePopupHandler(new Callback<PopupFeatures, WebEngine>() {

		    public WebEngine call(PopupFeatures p) {
		    	
			    	wv2 = new WebView(); 
					wv2.setPrefHeight(645.0);
		            wv2.setPrefWidth(1140.0);
		            wv2.setLayoutY(35);
		            
		    		Button btnIframe = new Button ("Incluir");
		    		
		    		System.out.println("numero  iframe " + numIframe);
		    	
					btnIframe.setOnAction(new EventHandler<ActionEvent>() {
			            @Override public void handle(ActionEvent e) {
			            	
			        			//-- imprimir o relatório ou tn no editor do SEI --//
								wv2.getEngine().executeScript(
				            			"var x = document.getElementsByTagName('iframe')["+numIframe+"];"
				            			+ "var y = x.contentDocument;" 
										+ "y.body.innerHTML = " + html + ";"
				            			);
								
			            }
			        });
					
					
					ChoiceBox<String> cbNumIframe = new ChoiceBox<String>();
					ObservableList<String> olNumIframe = FXCollections
						.observableArrayList(
								"0",
								"1",
								"2",
								"3"
								
								);
					// evento captura numero do choice box
					cbNumIframe.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
					      @Override
					      public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
					    	  numIframe = (Integer) number2;
					    	  System.out.println("ol num iframe : " + numIframe);
					      }
					    });
					
					cbNumIframe.setItems(olNumIframe);
					cbNumIframe.setValue(String.valueOf(numIframe));
					
					cbNumIframe.setLayoutX(460);
					cbNumIframe.setLayoutY(8);
					
					btnIframe.setLayoutX(502);
					btnIframe.setLayoutY(8);
			
		            Group group = new Group();
					group.getChildren().addAll(btnIframe, wv2, cbNumIframe);
		            
					Stage stage = new Stage(); // StageStyle.UTILITY
					
		            stage.setScene(new Scene(group));
		            
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
		public void redimWei (Number newValue) {
			apNavegador.setMinWidth((double) newValue);
		}
		public void redimHei (Number newValue) {
			apNavegador.setMinHeight((double) newValue);;
		}

	@FXML ScrollPane spNavegador;
	
	//-- INITIALIZE --//
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
		// -- inicitalizar o mapa -- //
		Platform.runLater(() ->{
		navegarWeb();  
		});
		
		AnchorPane.setTopAnchor(spNavegador, 0.0);
	    AnchorPane.setRightAnchor(spNavegador, 0.0);
	    AnchorPane.setLeftAnchor(spNavegador, 4.5);
	    AnchorPane.setBottomAnchor(spNavegador, 0.0);
	    
	    AnchorPane.setLeftAnchor(pNavBotoes1, 11.0);
	    AnchorPane.setRightAnchor(pNavBotoes2, 0.0);
	    
		btnGoogle.setGraphic(new ImageView(imgGoogle));
		btnCapturarDocs.setGraphic(new ImageView(imgCapturar));
		btnMostrarDocs.setGraphic(new ImageView(imgMostrar));
		btnSei.setGraphic(new ImageView(imgSei));
		btnSeiTrein.setGraphic(new ImageView(imgSeiTrein));
		btnWebBrowser.setGraphic(new ImageView(imgWeb));
	}

}
