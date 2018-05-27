package fiscalizacao;

//import org.omg.IOP.TAG_INTERNET_IOP;

import controllers.TabEnderecoController;
import controllers.TabInterfController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.scene.Parent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

public class GoogleMap extends Parent {
	
	
	TabEnderecoController tabEnd = new TabEnderecoController();
	
	// --- Controller Principal - MainController --- //
	//@FXML private MainController main;


		// método  de chamada initMap com o mapa e webview //
		public GoogleMap() {
	        initMap();
	        initCommunication();
	        getChildren().add(webView);
	        //setMarkerPosition(0,0);
	        //setMapCenter(0, 0);
	        //switchHybrid();
	}

	    // iniciação do webview e mapa html javascript //
	    void initMap()
	    {
	        webView = new WebView();
	        webEngine = webView.getEngine();
	        webView.setPrefWidth(1250);
	        webView.setPrefHeight(710);
	        webEngine.load(getClass().getResource("/html/map.html").toExternalForm());
	        
	        ready = false;
	        webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>()
	        {
	            public void changed(final ObservableValue<? extends Worker.State> observableValue,
	                                final Worker.State oldState,
	                                final Worker.State newState)
	            {
	            	
	                if (newState == Worker.State.SUCCEEDED)
	                {
	                    ready = true;
	                   
	                }
	                
	                int count = 0; 
	                count ++;
	                System.out.println(count + " initMap funcionando");
	            }
	        });
	    }

	    // método de comunicação com o webEngine //  MUDEI TIRANDO O PUBLIC PARA VER SE MELHORA
	    void initCommunication() {
	        webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>()
	        {
	            public void changed(final ObservableValue<? extends Worker.State> observableValue,
	                                final Worker.State oldState,
	                                final Worker.State newState)
	            {
	                if (newState == Worker.State.SUCCEEDED)
	                {
	                    doc = (JSObject) webEngine.executeScript("window");
	                    doc.setMember("app", GoogleMap.this);
	                }
	                int countC = 0; 
	                countC ++;
	                System.out.println(countC + " initComunicantion funcionando");
	            }
	        });
	    } 
	    
	    
	    
	    private void invokeJS(final String str) {
	    	
	    	System.out.println("invokeJs");
	    	
	    	System.out.println("invoke variável " + str);
	    	
	        if(ready) {
	        	
	            doc.eval(str);
	            System.out.println("ready " + str);
	        }
	        else {
	        	System.out.println("não ready " + str);
	            webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>()
	            {
	                @Override
	                public void changed(final ObservableValue<? extends Worker.State> observableValue,
	                                    final Worker.State oldState,
	                                    final Worker.State newState)
	                {
	                    if (newState == Worker.State.SUCCEEDED)
	                    {
	                        doc.eval(str);
	                        
	                    }
	                    int countInv = 0; 
		                countInv ++;
		                System.out.println(countInv  + " invokeJS funcionando");
	                }
	            });
	            
	        }
	    }
	    
	    
	    
	    
	    
	    /*
	     Now we will write the methods of class GoogleMap responsible for registration of the output agent, reception of event 
	     from javasript a code and a call of the output agent:
	     Methods of class GoogleMap
	     */
	    
	    /*
	    public void setOnMapLatLngChanged(EventHandler<MapEvent> eventHandler) {
	        onMapLatLngChanged = eventHandler;
	    }
	    */
	    
	    ///
	    ///

	    // Tentar junto com o método pegar as doubles de coordenadas
	
	   
	    public void handle(double lat, double lng, String endMap) {
	    	
	    	System.out.println("método handle chamado: " + lat + " e " + lng + "e endereço: " + endMap);
	    	
	    	TabEnderecoController.latDec = Double.toString(lat);
			TabEnderecoController.lngDec = Double.toString(lng);
			TabEnderecoController.endMap = endMap;
			
		    TabInterfController.latDec = Double.toString(lat);
		    TabInterfController.lngDec = Double.toString(lng);
		    
		    //tabEnd.printCoord(lat, lng, endMap);
	    	/*
	    	TabEnderecoController tabEndCont = new TabEnderecoController();
	    	
	    	tabEndCont.setLatLng(lat, lng, address);
	    	 
	    	TabInterfController tabIntCont = new TabInterfController();
	    	 
	    	tabIntCont.setLatLng(lat, lng);
	    	*/
	    	
	    	//tabEndCont.clickButton();
	    	
	    	
	    	
	    	/*
	        if(onMapLatLngChanged != null) {
	            MapEvent event = new MapEvent(this, lat, lng);
	            onMapLatLngChanged.handle(event);
	            
	            int countInv = 0; 
                countInv ++;
                System.out.println(countInv  + " app Handler");
	        }
	        */
	        
	        
	    }

	    
	    public void setMarkerPosition(double lat, double lng) {
	    	
	    	System.out.println("começo do setMarker " + lat + lng);
	    	
	        String sLat = Double.toString(lat);
	        String sLng = Double.toString(lng);
	        
	        System.out.println(lat);
	        
	        invokeJS("setMarkerPosition(" + sLat + ", " + sLng + ")");
	        System.out.println("String " + "setMarkerPosition(" + sLat + ", " + sLng + ")");
	        
	    }

	    public void setMapCenter(double lat, double lng) {
	        String sLat = Double.toString(lat);
	        String sLng = Double.toString(lng);
	        invokeJS("setMapCenter(" + sLat + ", " + sLng + ")");
	    }

	    public void switchSatellite() {
	        invokeJS("switchSatellite()");
	    }

	    public void switchRoadmap() {
	        invokeJS("switchRoadmap()");
	    }

	    public void switchHybrid() {
	        invokeJS("switchHybrid()");
	    }

	    public void switchTerrain() {
	        invokeJS("switchTerrain()");
	    }

	    public void startJumping() {
	        invokeJS("startJumping()");
	    }

	    public void stopJumping() {
	        invokeJS("stopJumping()");
	    }
	    
	    

	    public void setHeight(double h) {
	        webView.setPrefHeight(h);
	    }

	    public void setWidth(double w) {
	        webView.setPrefWidth(w);
	    }
	    
	    
	/*
	    public ReadOnlyDoubleProperty widthProperty() {
	        return webView.widthProperty();
	    }
	*/
	    //private EventHandler<MapEvent> onMapLatLngChanged;
	    
	    // Agora vamos escrever um método de classe GoogleMap para uma chamada de funções javascript a partir de java um código:
	    	// invokeJS (str final String)
	    
	    private JSObject doc;
	    //private EventHandler<MapEvent> onMapLatLngChanged;
	    private WebView webView;
	    private WebEngine webEngine;
	    private boolean ready;
	  
}
