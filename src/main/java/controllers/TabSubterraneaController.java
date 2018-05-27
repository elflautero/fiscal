package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import entity.Subterranea;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class TabSubterraneaController implements Initializable {
	
	Subterranea sGeral = new Subterranea();
	
	public Subterranea obterSubterranea () {
		
		sGeral.setSub_Poco(cbTipoCaptacao.getValue());
		sGeral.setSub_Sistema((cbSubSis.getValue()));
		sGeral.setSub_Vazao(tfVazao.getText());
		sGeral.setSub_Estatico(tfEstatico.getText());
		sGeral.setSub_Dinamico(tfDinamico.getText());
		sGeral.setSub_Profundidade(tfProfundidade.getText());
		sGeral.setSub_Caesb(cbSubCaesb.getValue());
		
		return sGeral;
	
	}
	
	public void imprimirSubterranea (Subterranea sub) {
		
		cbTipoCaptacao.setValue(sub.getSub_Poco());
		cbSubSis.setValue(sub.getSub_Sistema());
		
		tfVazao.setText(sub.getSub_Vazao());
		tfEstatico.setText(sub.getSub_Estatico());
		tfDinamico.setText(sub.getSub_Dinamico());
		tfProfundidade.setText(sub.getSub_Profundidade());
		
		cbSubCaesb.setValue(sub.getSub_Caesb());
		
		// falta a data
		
	}
	
	@FXML private MainController main;
	
	@FXML Pane tabSubterranea = new Pane ();
	
	@FXML
	ChoiceBox<String> cbSubSis = new ChoiceBox<String>();
		ObservableList<String> olSubSis = FXCollections
			.observableArrayList(
					
					"R3/Q3",
					"R4",
					"A",
					"PPC",
					"S/A",
					"Araxá",
					"Bambuí",
					"F",
					"F/Q/M",
					"P1",
					"P2",
					"P3",
					"P4"
					
					); 
		
	
		@FXML
		ChoiceBox<String> cbTipoCaptacao = new ChoiceBox<String>();
			ObservableList<String> olTipoCaptacao = FXCollections
				.observableArrayList(
						"Tubular", 
						"Manual"
						
						); 
			
			@FXML
			ChoiceBox<String> cbSubCaesb = new ChoiceBox<String>();
				ObservableList<String> olSubCaesb = FXCollections
					.observableArrayList(
							"Sim", 
							"Não"
							); 
		
	

	Image imgSub = new Image(getClass().getResourceAsStream("../images/subterranea.png"));
	@FXML ImageView	iVewSubt = new ImageView();
	
	@FXML
	TextField tfVazao = new TextField();
	@FXML
	TextField tfEstatico = new TextField();
	@FXML
	TextField tfDinamico = new TextField();
	@FXML
	TextField tfProfundidade = new TextField();
	
	
	@FXML DatePicker dpDataSubterranea = new DatePicker();
	
	
	//-- Main Controller --//
	public void init(MainController mainController) {
		main = mainController;
		
	}

	//-- INITIALIZE --//
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
		
		cbTipoCaptacao.setItems(olTipoCaptacao);
		cbSubCaesb.setItems(olSubCaesb);
	
		cbSubSis.setItems(olSubSis);
		
		
		iVewSubt.setImage(imgSub);
		
		System.out.println("TabSubterranea inicializada!");
		
	}

}
