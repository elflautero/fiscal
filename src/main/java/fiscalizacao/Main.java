package fiscalizacao;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
        
        stage.setMaximized(false);
        stage.setResizable(false);
        
        stage.show();
    }

}
