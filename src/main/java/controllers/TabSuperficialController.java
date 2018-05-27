package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import entity.Superficial;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class TabSuperficialController implements Initializable{
	
	Superficial superGeral = new Superficial();
	
	public Superficial obterSuperficial () {
		
		
		superGeral.setSup_Local(cbCaptacao.getValue());		// sup_Local; //-- () canal () rio () reservatório () lago natural () nascente
		superGeral.setSup_Captacao(cbFormaCaptacao.getValue()); // sup_Captacao; // gravidade, bombeamento, outro
		superGeral.setSup_Bomba(tfMarcaBomba.getText()); // marca da bomba
		superGeral.setSup_Potencia(tfPotenciaBomba.getText()); // potência da bomba
		superGeral.setSup_Tempo(tfTempoBomba.getText()); // tempo de captação
		superGeral.setSup_Area(tfArea.getText()); // área da propriedade
		superGeral.setSup_Caesb(cbCaesb.getValue()); // caesb
		
		
	return superGeral;
	
	};
	
	public void imprimirSuperficial (Superficial sup) {
		
		 cbCaptacao.setValue(sup.getSup_Local());
		 cbFormaCaptacao.setValue(sup.getSup_Captacao());
		 
		 tfMarcaBomba.setText(sup.getSup_Bomba());
		 tfPotenciaBomba.setText(sup.getSup_Potencia());
		 tfTempoBomba.setText(sup.getSup_Tempo());
		 
		 tfArea.setText(sup.getSup_Area());
		 
		 cbCaesb.setValue(sup.getSup_Caesb());
		 
		 // falta data
		 
	}
	
	@FXML TextField tfMarcaBomba = new TextField();
	@FXML TextField tfPotenciaBomba = new TextField();
	@FXML TextField tfTempoBomba = new TextField();
	@FXML TextField tfArea = new  TextField();
	
	@FXML Pane tabSuperficial;
	
	@FXML
	ChoiceBox<String> cbCaesb = new ChoiceBox<String>();
		ObservableList<String> olCaesb = FXCollections
			.observableArrayList(
					"Sim" , 
					"Não"
					); 
		
		@FXML
		ChoiceBox<String> cbCaptacao = new ChoiceBox<String>();
			ObservableList<String> olCaptacao = FXCollections
				.observableArrayList(
						"Canal" , 
						"Rio",
						"Reservatório",
						"Lago Natural",
						"Nascente",
						"Outro"
						
						); 

			@FXML
			ChoiceBox<String> cbFormaCaptacao = new ChoiceBox<String>();
				ObservableList<String> olFormaCaptacao = FXCollections
					.observableArrayList(
							"Gravidade" , 
							"Bombeamento",
							"Outro"
							); 
			

	Image imgSuper = new Image(getClass().getResourceAsStream("../images/superficial.png"));
	
//  JavaFX Application Thread" java.lang.NullPointerException: Input stream must not be null //
	
			// JavaFX Application Thread" java.lang.NullPointerException: Input stream must not be null
			
			//imagem
			
	@FXML ImageView	iVewSuper = new ImageView();
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
		cbCaesb.setItems(olCaesb);
		cbCaptacao.setItems(olCaptacao);
		cbFormaCaptacao.setItems(olFormaCaptacao);
		
		iVewSuper.setImage(imgSuper);
		
		System.out.println("TabSuperficial chamado!");
		
	}
	 
		

}
