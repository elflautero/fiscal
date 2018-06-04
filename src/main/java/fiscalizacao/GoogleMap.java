package fiscalizacao;


import controllers.TabEnderecoController;
import controllers.TabInterfController;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSException;
import netscape.javascript.JSObject;


public class GoogleMap extends Parent {
	
	
	TabEnderecoController tabEnd = new TabEnderecoController();
	
		// método  de chamada initMap com o mapa e webview //
		public GoogleMap() {
	        initMap();
	        initCommunication();
	        getChildren().add(webView);
	        
	       // setMarkerPosition(0,0);
	       // setMapCenter(0, 0);
	       // switchHybrid();
	        
	}

	    // iniciação do webview e mapa html javascript //
	    void initMap()
	    {
	        webView = new WebView();
	        webEngine = webView.getEngine();
	        webView.setPrefWidth(1250);
	        webView.setPrefHeight(710);
	        webEngine.load(getClass().getResource("/html/map.html").toExternalForm()); // originalMap
	        
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
	                System.out.println(" initMap funcionando");
	            }
	        });
	    }

	    // método de comunicação com o webEngine //
	    private void initCommunication() {
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
	               
	                System.out.println(" initComunicantion funcionando");
	            }
	        });
	    } 
	   
	    private void invokeJS(final String str) {
	    	/*
	    	if (ready == true) {
	    		System.out.println("ready true");
	    	}
	    	else { 
	    		System.out.println("ready false");
	    	}
	    	*/
	    	
	        if(ready) {
	        	try {
	            doc.eval(str);
	             }
	        	catch (JSException js){ 
	            System.out.println("não ready execao de leitura javascript " + js);
	        	}
	        }
	        else {
	        	
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
	                   
		                System.out.println(" invokeJS funcionando");
	                }
	            });
	            
	        }
	    }
	
	    public void setOnMapLatLngChanged(EventHandler<MapEvent> eventHandler) {
	        onMapLatLngChanged = eventHandler;
	    }
	    
	    
	    public void handle(double lat, double lng, String endMap) {
	    	
	    	System.out.println("método handle chamado: " + lat + " e " + lng + "e endereço: " + endMap);
	    	
	    	TabEnderecoController.latDec = Double.toString(lat);
			TabEnderecoController.lngDec = Double.toString(lng);
			TabEnderecoController.endMap = endMap;
			
		    TabInterfController.latDec = Double.toString(lat);
		    TabInterfController.lngDec = Double.toString(lng);
		    
		    
	        if(onMapLatLngChanged != null) {
	            MapEvent event = new MapEvent(this, lat, lng);
	            onMapLatLngChanged.handle(event);
	           
	        }
	        
	    }

	    
	    public void setMarkerPosition(double lat, double lng) {
	    	
	        String sLat = Double.toString(lat);
	        String sLng = Double.toString(lng);
	  
	        invokeJS("setMarkerPosition(" + sLat + ", " + sLng + ")");
	       
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
	  
	    public ReadOnlyDoubleProperty widthProperty() {
	        return webView.widthProperty();
	    }
	    
	    private JSObject doc;
	    private EventHandler<MapEvent> onMapLatLngChanged;
	    private WebView webView;
	    private WebEngine webEngine;
	    public boolean ready;
	    
}
