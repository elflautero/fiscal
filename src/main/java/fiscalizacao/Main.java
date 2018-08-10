package fiscalizacao;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {
	
	public Parent rootNode;

	public static void main(final String[] args) {
		
        Application.launch(args);
        
    }
   
    public void init() throws Exception {
    
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Main.fxml"));
    rootNode = fxmlLoader.load();
    
    }

    public void start(Stage stage) throws Exception {
    	
        stage.setScene(new Scene(rootNode));
        
        // para o programa já  abrir de acordo com a dimensão da tela do computador
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        
        stage.setX(primaryScreenBounds.getMinX());
        stage.setY(primaryScreenBounds.getMinY());
        stage.setWidth(primaryScreenBounds.getWidth());
        stage.setHeight(primaryScreenBounds.getHeight());
        
        
        // teste no notebook
        //stage.setWidth(1366);
        //stage.setHeight(768);
        
        // limites de tamanho do stage
        stage.setMinHeight(768);
        stage.setMinWidth(1366);
        stage.setMaxHeight(1080);
        stage.setMaxWidth(1920);
        
        stage.show();
    }

}
