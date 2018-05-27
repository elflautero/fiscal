package entity;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.web.PopupFeatures;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

public class Navegador extends Application{
	
	WebView navegador;
	String linkSEI;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
	
	    navegador.getEngine().setCreatePopupHandler(new Callback<PopupFeatures, WebEngine>() {

	        public WebEngine call(PopupFeatures p) {
	            Stage stage = new Stage(StageStyle.UTILITY);
	            WebView wv2 = new WebView();
	            stage.setScene(new Scene(wv2, 550, 700));
	            stage.show();
	            return wv2.getEngine();
	        }
	    });


	    StackPane root = new StackPane();
	    root.getChildren().add(navegador);

	    Scene scene = new Scene(root, 622, 883);

	    primaryStage.setScene(scene);
	    primaryStage.show();
	    navegador.getEngine().load(linkSEI);
		
	}
	
	public WebEngine navegarWeb () {
		return navegador.getEngine();
	}
	
	public Navegador (WebView wv, String linkSEI) {
		this.navegador = wv;
		this.linkSEI =  linkSEI;
	}
	
	public Navegador () {
		
	}
	

}