package controllers;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ResourceBundle;

import entity.Subterranea;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.util.StringConverter;

public class TabSubterraneaController implements Initializable {
	
	Image imgSub = new Image(TabSubterraneaController.class.getResourceAsStream("/images/subterranea.png"));
	@FXML ImageView	iVewSubt = new ImageView();
	
	@FXML
	TextField tfVazao = new TextField();
	@FXML
	TextField tfEstatico = new TextField();
	@FXML
	TextField tfDinamico = new TextField();
	@FXML
	TextField tfProfundidade = new TextField();
	
	
	@FXML public DatePicker dpDataSubterranea = new DatePicker();
	
	public Subterranea sGeral = new Subterranea();
	
	public Subterranea obterSubterranea () {
		
		sGeral.setSub_Poco(cbTipoCaptacao.getValue());
		sGeral.setSub_Sistema((cbSubSis.getValue()));
		sGeral.setSub_Vazao(tfVazao.getText());
		sGeral.setSub_Estatico(tfEstatico.getText());
		sGeral.setSub_Dinamico(tfDinamico.getText());
		sGeral.setSub_Profundidade(tfProfundidade.getText());
		sGeral.setSub_Caesb(cbSubCaesb.getValue());
		
		if (dpDataSubterranea.getValue() == null) {
			sGeral.setSub_Data(null);}
		else {
		sGeral.setSub_Data(formatter.format(dpDataSubterranea.getValue()));
		}
		
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
		if (sub.getSub_Data() == null) {
			dpDataSubterranea.getEditor().clear();
		} else {
		dpDataSubterranea.setValue((LocalDate.parse(sub.getSub_Data(), formatter)));
		}
		
	}
	
	DateTimeFormatter formatter = new DateTimeFormatterBuilder()
			.parseCaseInsensitive()
			.append(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
			.toFormatter();
	
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
		
		dpDataSubterranea.setConverter(new StringConverter<LocalDate>() {
			
			@Override
			public String toString(LocalDate t) {
				if (t != null) {
					return formatter.format(t);
				}
				return null;
			}
			
			@Override
			public LocalDate fromString(String string) {
				if (string != null && !string.trim().isEmpty()) {
					return LocalDate.parse(string, formatter);
				}
				return null;
			}

		});
		
		System.out.println("TabSubterranea inicializada!");
		
		tfVazao.lengthProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                if (newValue.intValue() > oldValue.intValue()) {
                    // Check if the new character is greater than LIMIT
                    if (tfVazao.getText().length() >= 5) {

                        // if it's 11th character then just setText to previous
                        // one
                    	tfVazao.setText(tfVazao.getText().substring(0, 5));
                    }
                }
            }
        });
		
		tfEstatico.lengthProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                if (newValue.intValue() > oldValue.intValue()) {
                    // Check if the new character is greater than LIMIT
                    if (tfEstatico.getText().length() >= 5) {

                        // if it's 11th character then just setText to previous
                        // one
                    	tfEstatico.setText(tfEstatico.getText().substring(0, 5));
                    }
                }
            }
        });
		
		tfDinamico.lengthProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                if (newValue.intValue() > oldValue.intValue()) {
                    // Check if the new character is greater than LIMIT
                    if (tfDinamico.getText().length() >= 5) {

                        // if it's 11th character then just setText to previous
                        // one
                    	tfDinamico.setText(tfDinamico.getText().substring(0, 5));
                    }
                }
            }
        });
		
		tfProfundidade.lengthProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                if (newValue.intValue() > oldValue.intValue()) {
                    // Check if the new character is greater than LIMIT
                    if (tfProfundidade.getText().length() >= 5) {

                        // if it's 11th character then just setText to previous
                        // one
                    	tfProfundidade.setText(tfProfundidade.getText().substring(0, 5));
                    }
                }
            }
        });
		
	}

}
