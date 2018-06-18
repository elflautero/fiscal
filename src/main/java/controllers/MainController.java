package controllers;

import java.io.IOException;

import entity.Demanda;
import entity.Endereco;
import entity.Vistoria;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
