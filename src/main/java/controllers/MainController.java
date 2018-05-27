package controllers;

import entity.Denuncia;
import entity.Endereco;
import entity.Vistoria;
import javafx.fxml.FXML;

public class MainController {
	
	 	@FXML private TabDenunciaController tabDenunciaController;
	    @FXML private TabInterfController tabInterferenciaController;
	    @FXML private TabUsuarioController tabUsuarioController;
	    @FXML private TabEnderecoController tabEnderecoController;
	    @FXML private TabNavegadorController tabNavegadorController;
	    @FXML private TabAtoController tabAtoController;
	    @FXML private TabVistoriaController tabVistoriaController;
	   
	    

	    @FXML 
	    private void initialize() {
	    	
	       System.out.println("Application Started!");
	       
	       tabDenunciaController.init(this);
	       tabEnderecoController.init(this);
	       tabInterferenciaController.init(this);
	       tabAtoController.init(this);
	       tabVistoriaController.init(this);
	       
	       
	       
	       
	    }

	    // mudei voi para retornar  Denuncia
		public void pegarDoc(Denuncia dGeral) {
			
			//-- para a tab endereço --//
			tabEnderecoController.lblDoc.setText(dGeral.getDoc_Denuncia() + "  |  SEI nº: " + dGeral.getDoc_SEI_Denuncia());
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
