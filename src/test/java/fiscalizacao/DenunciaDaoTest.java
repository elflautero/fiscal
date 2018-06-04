package fiscalizacao;

import java.io.File;
import java.util.List;
import java.util.regex.Pattern;

import org.junit.Test;

import dao.DenunciaDao;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Callback;

public class DenunciaDaoTest {
	/*
	Button btnCoord = new Button();
	
	@Test
	public void test() {
		
		try {
			
		
		
		GoogleMap google = new GoogleMap();
		
		Group group = new Group();
		group.getChildren().addAll(google, btnCoord);
		
		Scene scene = new Scene(group);
		
		btnCoord.setLayoutY(8);
		btnCoord.setLayoutX(502);
		btnCoord.setText("Coord");
		//btnCoord.setGraphic(new ImageView(imgGetCoord));
		
		btnCoord.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	
            	//tfEndLat.setText(latDec);
        		//tfEndLon.setText(lngDec);
            	String endMap = "";
        		//-- sugestão de endereço, cep etc --//
        		String end[] = endMap.split(Pattern.quote(","));
        		
        		ObservableList<String> documentos = FXCollections.observableArrayList(end);
    			ListView<String> listView = new ListView<String>(documentos);
    			TableColumn<List, String> tc = new TableColumn<List, String> ("Documentos");
    			
    			tc.setCellValueFactory(new Callback<CellDataFeatures<List, String>, ObservableValue<String>>() {
    				
    			     public ObservableValue<String> call(CellDataFeatures<List, String> p) {
    			 
    			         return new SimpleStringProperty(p.getValue().toString());
    			     }
    			 });

    				Scene scene = new Scene(listView);
    				Stage stage = new Stage(); // StageStyle.UTILITY - tirei para ver como fica, se aparece o minimizar
    				stage.setWidth(400);
    				stage.setHeight(300);
    			    stage.setScene(scene);
    			    stage.setMaximized(false);
    			    stage.setResizable(false);
    			    stage.setX(1030.0);
    			    stage.setY(550.0);
    			   
    			    stage.setAlwaysOnTop(true); 
    			    stage.show();
    			
    			    //--  https://docs.oracle.com/javafx/2/ui_controls/ListViewSample.java.html  --// 
    			    listView.getSelectionModel().selectedItemProperty().addListener(
                    new ChangeListener<String>() {
                        public void changed(ObservableValue<? extends String> 
                        ov, String old_val, String new_val) {
                      
                             Clipboard clip = Clipboard.getSystemClipboard();
                             ClipboardContent conteudo = new ClipboardContent();
                             conteudo.putString(new_val);
                             clip.setContent(conteudo);
                        }
                    });
    			    //-- fim - sugestão de endereço, cep etc --//
        		
            }
        });

		
		Stage stage = new Stage(); // StageStyle.UTILITY - tirei para ver como fica, se aparece o minimizar
		stage.setWidth(1250);
		stage.setHeight(750);
        stage.setScene(scene);
        stage.setMaximized(false);
        stage.setResizable(false);
        stage.show();
        
	
		
	} catch (Exception e) {
		e.printStackTrace();
		System.out.println("erro " + e);
	}
		
	}	
	
	*/
	

}
