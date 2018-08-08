package controllers;

import java.io.IOException;

import entity.Demanda;
import entity.Endereco;
import entity.Vistoria;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainController {
	
	 	@FXML private TabDemandaController tabDemandaController;
	    @FXML private TabInterfController tabInterferenciaController;
	    @FXML private TabUsuarioController tabUsuarioController;
	    @FXML private TabEnderecoController tabEnderecoController;
	    @FXML private TabNavegadorController tabNavegadorController;
	    @FXML private TabAtoController tabAtoController;
	    @FXML private TabVistoriaController tabVistoriaController;
	   
	    @FXML Image imgGetLaw = new Image(MainController.class.getResourceAsStream("/images/law.png")); 
	    @FXML Image imgGetHome = new Image(MainController.class.getResourceAsStream("/images/home.png")); 
	    
	    LegislacaoController legCon;
	    
	    @FXML Button btnLegislacao;
	    @FXML Button btnHome;
	    
	    public void btnLegislacaoHab (ActionEvent event) {
	    	
	    	
	    	Pane legPane = new Pane();
	    	legCon = new LegislacaoController();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TabLegislacao.fxml"));
			loader.setRoot(legPane);
			loader.setController(legCon);
			
			try {
				loader.load();
			} catch (IOException e) {
				System.out.println("erro leitura do pane - chamada legislação");
				e.printStackTrace();
			}
			
			Scene scene = new Scene(legPane);
			Stage stage = new Stage(); // StageStyle.UTILITY - tirei para ver como fica, se aparece o minimizar
			stage.setWidth(960);
			stage.setHeight(600);
	        stage.setScene(scene);
	        stage.setMaximized(false);
	        stage.setResizable(false);
	        stage.setAlwaysOnTop(true); 
	        stage.show();
	    	
	    	
	    	
	    }
	    public void btnHomeHab (ActionEvent event) {
	    	
	    }
	    
	    @FXML TabPane tpMain;
	    @FXML MenuBar menuBar;
	    @FXML Pane paneButtons;
	    
	    @FXML 
	    private void initialize() {
	    	
	       System.out.println("Application Started!");
	       
	       tabDemandaController.init(this);
	       tabEnderecoController.init(this);
	       tabInterferenciaController.init(this);
	       tabAtoController.init(this);
	       tabVistoriaController.init(this);
	       
	       btnLegislacao.setGraphic(new ImageView(imgGetLaw));
	       btnHome.setGraphic(new ImageView(imgGetHome));
	       
	       // 2 padronizar a apresentação  dos objetos de acordo com a tela do computador
	       AnchorPane.setTopAnchor(menuBar, 0.0);
	       AnchorPane.setLeftAnchor(menuBar, 0.0);
	       AnchorPane.setRightAnchor(menuBar, 0.0);
	       
	       AnchorPane.setTopAnchor(tpMain, 20.0);
	       AnchorPane.setLeftAnchor(tpMain, 0.0);
	       AnchorPane.setRightAnchor(tpMain, 160.0);
	       
	       AnchorPane.setTopAnchor(paneButtons, 50.0);
	       AnchorPane.setRightAnchor(paneButtons, 5.0);
	       AnchorPane.setBottomAnchor(paneButtons, 50.0);
	       
	       tpMain.widthProperty().addListener(new ChangeListener<Number>() {
	    	   
			    @Override 
			    public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
			    	
			    	System.out.println("tpMain, valor width " + newValue);
			    	// navegador
			    	tabNavegadorController.redimWei(newValue);
			    	tabNavegadorController.redimHei(newValue);
				    // denuncia
			    	tabDemandaController.redimWei(newValue);
			    	tabDemandaController.redimHei(newValue);
			    	// endereco
			    	tabEnderecoController.redimWei(newValue);
			    	tabEnderecoController.redimHei(newValue);
			    	// interferencia
			    	tabInterferenciaController.redimWei(newValue);
			    	tabInterferenciaController.redimHei(newValue);
			    	// usuario
			    	tabUsuarioController.redimWei(newValue);
			    	tabUsuarioController.redimHei(newValue);
			    	// vistoria
			    	tabVistoriaController.redimWei(newValue);
			    	tabVistoriaController.redimHei(newValue);
			    	//ato
			    	tabAtoController.redimWei(newValue);
			    	tabAtoController.redimHei(newValue);
			    	
			    }
			});
	        
	    }

	    // mudei voi para retornar  Denuncia
		public void pegarDoc(Demanda dGeral) {
			
			//-- para a tab endereço --//
			tabEnderecoController.lblDoc.setText(dGeral.getDemDocumento()+ "  |  SEI nº: " + dGeral.getDemDocumentoSEI());
			tabEnderecoController.dGeralEnd = dGeral;
		}
		
		public void pegarEnd (Endereco eGeral) {
			
			//-- para a tab interferência --//
			tabInterferenciaController.lblEnd.setText(eGeral.getDesc_Endereco() + " |  RA: "  + eGeral.getRA_Endereco() );
			tabInterferenciaController.eGeralInt = eGeral;
			
			// para a tab usuário --//
			tabUsuarioController.lblEndUsuario.setText(eGeral.getDesc_Endereco() + " |  RA: "  + eGeral.getRA_Endereco() );
			tabUsuarioController.eGeralUs  = eGeral;
			
			//-- para tab ato --//
			tabVistoriaController.lblVisEnd.setText(eGeral.getDesc_Endereco() + " |  RA: "  + eGeral.getRA_Endereco() );
			tabVistoriaController.eGeralVis = eGeral;
		}
		
		public void pegarVistoria (Vistoria vGeral) {
			
			tabAtoController.lblVisAto.setText(vGeral.getVisIdentificacao() + " |  SEI:  "  + vGeral.getVisSEI());
			tabAtoController.visGeral = vGeral;
		}
		
}
