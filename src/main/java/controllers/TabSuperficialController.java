package controllers;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ResourceBundle;

import entity.Superficial;
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

public class TabSuperficialController implements Initializable{
	
	@FXML TextField tfMarcaBomba = new TextField();
	@FXML TextField tfPotenciaBomba = new TextField();
	@FXML TextField tfTempoBomba = new TextField();
	@FXML TextField tfArea = new  TextField();
	
	@FXML DatePicker dpDataOperacao;
	
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
			
	@FXML ImageView	iVewSuper = new ImageView();
	Image imgSuper = new Image(TabSuperficialController.class.getResourceAsStream("/images/superficial.png"));
	
	Superficial superGeral = new Superficial();
	
	public Superficial obterSuperficial () {
		
		
		superGeral.setSup_Local(cbCaptacao.getValue());		// sup_Local; //-- () canal () rio () reservatório () lago natural () nascente
		superGeral.setSup_Captacao(cbFormaCaptacao.getValue()); // sup_Captacao; // gravidade, bombeamento, outro
		superGeral.setSup_Bomba(tfMarcaBomba.getText()); // marca da bomba
		superGeral.setSup_Potencia(tfPotenciaBomba.getText()); // potência da bomba
		superGeral.setSup_Tempo(tfTempoBomba.getText()); // tempo de captação
		superGeral.setSup_Area(tfArea.getText()); // área da propriedade
		superGeral.setSup_Caesb(cbCaesb.getValue()); // caesb
		if (dpDataOperacao.getValue() == null) {
			superGeral.setSup_Data(null);
		} else {
			superGeral.setSup_Data(formatter.format(dpDataOperacao.getValue()));
		}
		
		
	return superGeral;
	
	};
	
	public void imprimirSuperficial (Superficial sup) {
		
		 try {cbCaptacao.setValue(sup.getSup_Local());} catch (Exception e) {cbCaptacao.setValue("");}
		 try {cbFormaCaptacao.setValue(sup.getSup_Captacao());} catch (Exception e) {cbFormaCaptacao.setValue("");};
		 
		 tfMarcaBomba.setText(sup.getSup_Bomba());
		 tfPotenciaBomba.setText(sup.getSup_Potencia());
		 tfTempoBomba.setText(sup.getSup_Tempo());
		 
		 tfArea.setText(sup.getSup_Area());
		 
		 cbCaesb.setValue(sup.getSup_Caesb());
		 if (sup.getSup_Data() == null) {
			 dpDataOperacao.getEditor().clear();
		 }else {
			 dpDataOperacao.setValue((LocalDate.parse(sup.getSup_Data(), formatter)));
		 }
		 
	}
	
	
	DateTimeFormatter formatter = new DateTimeFormatterBuilder()
			.parseCaseInsensitive()
			.append(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
			.toFormatter();
	
	
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
		cbCaesb.setItems(olCaesb);
		cbCaptacao.setItems(olCaptacao);
		cbFormaCaptacao.setItems(olFormaCaptacao);
		
		iVewSuper.setImage(imgSuper);
		
		dpDataOperacao.setConverter(new StringConverter<LocalDate>() {
			
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
		
		System.out.println("TabSuperficial chamado!");
		
		
		
		tfPotenciaBomba.lengthProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                if (newValue.intValue() > oldValue.intValue()) {
                    // Check if the new character is greater than LIMIT
                    if (tfPotenciaBomba.getText().length() >= 5) {

                        // if it's 11th character then just setText to previous
                        // one
                    	tfPotenciaBomba.setText(tfPotenciaBomba.getText().substring(0, 5));
                    }
                }
            }
        });
		
		tfTempoBomba.lengthProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                if (newValue.intValue() > oldValue.intValue()) {
                    // Check if the new character is greater than LIMIT
                    if (tfTempoBomba.getText().length() >= 5) {

                        // if it's 11th character then just setText to previous
                        // one
                    	tfTempoBomba.setText(tfTempoBomba.getText().substring(0, 5));
                    }
                }
            }
        });
		
		tfArea.lengthProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                if (newValue.intValue() > oldValue.intValue()) {
                    // Check if the new character is greater than LIMIT
                    if (tfArea.getText().length() >= 5) {

                        // if it's 11th character then just setText to previous
                        // one
                    	tfArea.setText(tfArea.getText().substring(0, 5));
                    }
                }
            }
        });
		
		
	}

}
