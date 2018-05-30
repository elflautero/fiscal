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
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
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
	
	@FXML AnchorPane tabNavegador = new AnchorPane();
	
	@FXML
	Button btnSEI = new Button();
	@FXML
	Button btnCapturarDocs = new Button();
	@FXML
	Button btnMostrarDocs = new Button();
	@FXML
	Button btnGo = new Button();
	
	@FXML
	Button btnGoogle = new Button();
	
	WebView wv1;
	
	// table view de documentos capturados do sei
	ObservableList<String []> numDosObservable;
	
	TableView tv;
	
	WebView wv2;
	
	
	//-- botÃ£o Google --//
	public void btnGoogleHab (ActionEvent event) {
		
		link = "https://www.google.com.br/search?q=googlr&oq=googlr&aqs=chrome..69i57j0l5.2588j0j7&sourceid=chrome&ie=UTF-8";
		navegarWeb();
		System.out.println("btn google clicado");
		
	}
	
	//-- botÃ£o SEI --//
	public void btnSEIHab (ActionEvent event) {
		
		link = "http://treinamento3.sei.df.gov.br/sip/login.php?sigla_orgao_sistema=GDF&sigla_sistema=SEI";
		navegarWeb();
		System.out.println("btn sei treinamento clicado");
		
	}
	
	
	//-- botÃ£o capturar --//
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
		
			
	
	public void btnMostrarDocsHab (ActionEvent event ) {

		// adicionar exceção https://github.com/Microsoft/ClearScript/issues/16
		
		contDocSei = (int) wv1.getEngine().executeScript("doc.length"); // quantidade de docs capturados
		
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
	
	
	//-- botÃ£o Go - atualizar navegador --//
	public void bntGoHab (ActionEvent event) {
		
		link = "https://www.google.com.br/search?q=googlr&oq=googlr&aqs=chrome..69i57j0l5.2588j0j7&sourceid=chrome&ie=UTF-8";
		navegarWeb();
		
		System.out.println("BotÃ£o go clicado!");
	}
	
	
	
	
	//-- mÃ©todo navegar --//
	public void navegarWeb() {
		
		wv1 = new WebView();
		wv1.setPrefSize(1117.0,571.0);
		//wv1.setLayoutY(78);
		
		
		wv1.getEngine().setCreatePopupHandler(new Callback<PopupFeatures, WebEngine>() {

		    public WebEngine call(PopupFeatures p) {
		    	
			    	wv2 = new WebView(); 
					wv2.setPrefHeight(645.0); // wv2, 1140.0, 650.0
		            wv2.setPrefWidth(1140.0);
		            wv2.setLayoutY(35);
		            
		    		Button btnIframe = new Button ("Incluir");
		    	
					btnIframe.setOnAction(new EventHandler<ActionEvent>() {
			            @Override public void handle(ActionEvent e) {
			            	
			            	/*
			            	
			            	File file = new File("../FiscalizacaoSRH/src/main/resources/html/autodeinfracao.html");
			    			
			        		Document docHtml = null;
			        		
							try {
								docHtml = Jsoup.parse(file, "UTF-8");  // retirei o  .clone()
							} catch (IOException e1) {
								System.out.println("Erro na leitura do documento e Jsoup!!!");
								e1.printStackTrace();
							}
			        	
			        		docHtml.select("nomeus").prepend("FabrÃ­cio JosÃ© Barrozo");
			        	
							
			        		String html = docHtml.toString();
			        		
			        		html = html.replace("\"", "'");
			        		html = html.replace("\n", "");
			        		
			        		html =  "\"" + html + "\"";
			        		
			        		*/
			            	
			        			//-- imprimir o relatório ou tn no editor do SEI --//
								wv2.getEngine().executeScript(
				            			"var x = document.getElementsByTagName('iframe')[1];"
				            			+ "var y = x.contentDocument;" 
										+ "y.body.innerHTML = " + html + ";"
				            			);
								
			            }
			        });
					
					btnIframe.setLayoutY(8);
					btnIframe.setLayoutX(502);
			
		            Group group = new Group();
					group.getChildren().addAll(btnIframe, wv2);
		            
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
	

	//-- INITIALIZE --//
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
		// -- inicitalizar o mapa -- //
		Platform.runLater(() ->{
		navegarWeb();  
		});
		
	}

}



/*
 
 Object html = (Object) wv1.getEngine().executeScript("document.getElementsByTagName('a')[" + i + "].innerHTML");
			Object htmlOuter = (Object) wv1.getEngine().executeScript("document.getElementsByTagName('a')[" + i + "].outerHTML");
			
			Object htmlSpan  = (Object) wv1.getEngine().executeScript("document.getElementsByTagName('span')[" + i + "].innerHTML");
			Object htmlSpanOuter = (Object) wv1.getEngine().executeScript("document.getElementsByTagName('span')[" + i + "].outerHTML");
					//  [" + i + "].innerHTML"
			//String htmlOuter = (String) wv1.getEngine().executeScript("document.getElementsById('*')[" + i + "].outerHTML");
					
					//"for (var i = 0; i<5; i++){document.getElementsByTagName('span')[i].innerHTML;}");
			System.out.println("inner: " + html);
			System.out.println("outer: " + htmlOuter);
			
			System.out.println("innerSpan: " + htmlSpan);
			System.out.println("outerSpan: " + htmlSpanOuter);
 
 */


/*
 
 
 "var doc;"
					
					+ 	"$(function() {"

					+	"$( '#ifrArvore' ).load(function(){"
					        
					+ 	"alert('iframe carregado');"
					
					+   "alert($(this).contents().find('span'));"
					 
					+	"$(this).contents().find( 'span' ).css( 'background-color', '#BADA55' );"
						
					+	"doc = ($(this).contents().find('span'));"
						
					+	"alert(doc);"
					
					+	"for (var i = 0; i < doc.length; i++) {"
					
					+	"alert (doc[i].textContent);"   
						
					+		"}"
						
					+   "});"

					+   "});"
 
 
 */





//link = "http://gis.adasa.df.gov.br/portal/home/index.html";
//navegarWeb();
//System.out.println(wv1.getEngine().getDocument());


//for (int i = 0; i < 10; i++ ) {


	//List<?> varOne = (List<?>) wv1.getEngine().executeScript("document.getElementsByTagName('*')[0]");

	/*
	wv1.getEngine().executeScript("var pro = document.querySelectorAll('span');" +
			"		for (var y=0; y<pro.length;y++){ " + 
			"		var one = pro[y].innerHTML; "      + 
			"		console.log (one);" + 
			"		console.log (y);"   +
					"}" 
			
			
			);*/




//}

	/*
	 
	 var el = document.getElementsByTagName('*')[0];

	console.log(el);

	el.addEventListener('click', function() {
	alert(el.innerHTML);
	});
	 
	 */
	
	/* NÃƒO DEU CERTO  
	String varOne = (String) wv1.getEngine().executeScript("var el = document.getElementsByTagName('*')[" + i + "].addEventListener('mouseover', function(event) { console.log(event.target.innerHTML); });");
	String varTwo = (String) wv1.getEngine().executeScript("el");
	System.out.println("one: " + varOne);
	
	String varThree = (String)wv1.getEngine().executeScript("event.target.innerHTML");
	
	System.out.println("two: " + varTwo);

	System.out.println("three: " + varThree);
	*/
	
	
	
	/* FUNCIONANDO BEM, PARA VER SE UMA VARIÃ�VEL JS PODE SER CHAMADA...
	String[] span = new String [10];
	
	span [i] = (String) wv1.getEngine().executeScript("document.getElementsByTagName('span')[" + i + "].innerHTML;" 
			+ "var oneJS = document.getElementsByTagName('span')[0].innerHTML");
	
	System.out.println("String " + i + " = " + span [i]);
	
	String zero = (String) wv1.getEngine().executeScript("oneJS");
	
	System.out.println("String zero: " + zero);;
	*/
//}


//outerHTML nÃ£o pega o que eu quero
		
		//"

//



//wv1.getEngine().executeScript("alert('Quantidade de querys encontradas abaixo:')");
//wv1.getEngine().executeScript("alert (pro.lenght)");

/*
int count = (int) wv1.getEngine().executeScript("pro.length");

for (int i = 0; i<count; i++) {
	
	String html = (String) wv1.getEngine().executeScript("pro[ " + i + "].innerHTML;");
	
	System.out.println(html);
}
*/






/*
// -- ConexÃ£o e pesquisa de denÃºncias -- //
private DenunciaDao denunciaDao = new DenunciaDao();	//passar classe
private List<Denuncia> denunciaList; // = denunciaDao.listDenuncia(strPesquisa); //passar string de pesquisar
private ObservableList<DenunciaTabela> obsListDenunciaTabela = FXCollections.observableArrayList(); //chamar observable list e outra classe

// --- mÃ©todo listarDenuncias --- //
public void listarDenuncias () {

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
			// adicionao  o ojeto enderecoFK na DenunciaTabela
			denuncia.getEnderecoFK()
			);
	
		obsListDenunciaTabela.add(denTab);
}

tcDocumento.setCellValueFactory(new PropertyValueFactory<DenunciaTabela, String>("Doc_Denuncia")); 
tcDocSEI.setCellValueFactory(new PropertyValueFactory<DenunciaTabela, String>("Doc_SEI_Denuncia")); 
tcProcSEI.setCellValueFactory(new PropertyValueFactory<DenunciaTabela, String>("Proc_SEI_Denuncia")); 

tvLista.setItems(obsListDenunciaTabela); 
	 */
	
	/*
	// -- Tabela --  //
	@FXML private TableView <DenunciaTabela> tvLista;
	
	// -- Colunas -- //
	@FXML private TableColumn<DenunciaTabela, String> tcDocumento;
	@FXML private TableColumn<DenunciaTabela, String> tcDocSEI;
	@FXML private TableColumn<DenunciaTabela, String> tcProcSEI;
	*/

	
	
	
			/*
			 
			 StackPane root = new StackPane();
			
			ObservableList<String[]> data = FXCollections.observableArrayList();
			data.addAll(Arrays.asList(docsSei));
			//data.remove(0);//remove titles from data
			TableView<String[]> table = new TableView<>();
			
			for (int a = 0; a < docsSei[0].length; a++) {
				TableColumn tc = new TableColumn(docsSei [0][a]);
				final int colNo = a;
				tc.setCellValueFactory(new Callback<CellDataFeatures<String[], String>, ObservableValue<String>() {
			 
					@Override
					public ObservableValue<String> call(CellDataFeatures<String[], String> p) {
						return new SimpleStringProperty((p.getValue()[colNo]));
					}

			});
			
				tc.setPrefWidth(90);
				table.getColumns().add(tc);
			}
			
			Stage stage = new Stage(); // StageStyle.UTILITY - tirei para ver como fica, se aparece o minimizar
			Scene scene = new Scene(table);
			stage.setWidth(1250);
			stage.setHeight(750);
	        stage.setScene(scene);
	        stage.setMaximized(false);
	        stage.setResizable(false);
			 
			 
			 
			 
			 */
			
			/*
			table.setItems(data);
			root.getChildren().add(table);
			stage.setScene(new Scene(root, 300, 250));
			stage.show();
			
			*/
	
			
			/*
			List<String[]> numDocsList;
			ObservableList<String []> numDocsObservable = FXCollections.observableArrayList();
			if (!numDocsObservable.isEmpty()) {
				numDocsObservable.clear();
			}
			
			numDocsObservable.addAll(docsSei);
			
			TableColumn<Integer, Integer> docsTabCol = new TableColumn ();
			
			docsTabCol.setCellValueFactory(new PropertyValueFactory<DenunciaTabela, String>(docsSei [0][0]));
			
			//tcDocumento.setCellValueFactory(new PropertyValueFactory<DenunciaTabela, String>("Doc_Denuncia")); 
			
			/*
			ObservableList<String> numDosObservable = FXCollections.observableArrayList(docsSei);
			TableView<String []> tvNumerosDocs = new TableView<String []>();
			
			for (int z = 0; z < docsSei.length; z++) {
				TableColumn tc = new TableColumn<docsSei [z]>();
			}
			
			*/


/*
			
	
			            		
			            		
			            		
			            		///
			            		  
		            //btnIframe.setLayoutY(8);
					//btnIframe.setLayoutX(502);
					//btnIframe.setGraphic(new ImageView(imgGetCoord));
					
		            
					
			            		
			            				*/
			
/*
public String pegarBuf () {
	
	String FILENAME = "../FiscalizacaoSRH/src/main/resources/html/autodeinfracao.html";
	
		// "../FiscalizacaoSRH/src/main/resources/html/autodeinfracao.html"
	
		// "../json/src/main/resources/html/relatorio.html"

	BufferedReader br = null;
	FileReader fr = null;
	String leitorRelatorio = null;

	try {

	fr = new FileReader(FILENAME);
	br = new BufferedReader(fr);

	while ((leitorRelatorio = br.readLine()) != null) {
		System.out.println(leitorRelatorio);
	
	}

	} catch (IOException e) {

		e.printStackTrace();

	} finally {

			try {

				if (br != null)
					br.close();

				if (fr != null)
					fr.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}
	
	System.out.println(leitorRelatorio);
	return leitorRelatorio;	
	
	}

*/

/*
 String FILENAME = "../FiscalizacaoSRH/src/main/resources/html/autodeinfracao.html";
			            	BufferedReader br = null;
				    		FileReader fr = null;
				    		String leitorRelatorio = null;
				    		
				    		StringBuilder strBuilder = new StringBuilder();
			            	
				    		try {

				    			fr = new FileReader(FILENAME);
				    			br = new BufferedReader(fr);
				    			
				    			
				    			// passa linha por linha do relatorio e adiciona uma por uma na strBuilder
				    			while ((leitorRelatorio = br.readLine()) != null) {
				    				strBuilder.append(leitorRelatorio);
				    			}

				    			} catch (IOException eIO) {

				    			eIO.printStackTrace();
	
				    			}
				    		
					    		leitorRelatorio = " \" " + strBuilder + " \" " ;  // adiciona aspas duplas no html final
 
 
 
 */
			
	
			

