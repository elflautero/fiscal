package controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import dao.DenunciaDao;
import entity.Denuncia;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import tabela.DenunciaTabela;

public class TabEndBuscarDocController implements Initializable {
	
	// TABLE BUSCAR DENÚNCIA
		@FXML private TableView <DenunciaTabela> tvListaDoc;
		
		@FXML private TableColumn<DenunciaTabela, String> tcDocumento;
		@FXML private TableColumn<DenunciaTabela, String> tcDocSEI;
		@FXML private TableColumn<DenunciaTabela, String> tcProcSEI;
		
		
		@FXML Button btnPesqDoc = new Button();
		
		@FXML TextField tfPesquisar = new TextField();
		
		//variável geral para outros controllers
		public Denuncia dGeralEnd;
		
		String strPesquisaDoc = "";
	
		
		public void btnPesqDocHab (ActionEvent event) {
			
			String srtPesquisaDoc = tfPesquisar.getText();
			
			listarDenuncias (srtPesquisaDoc); // chamar método listar denúncias //
			
			// Selecionar um documento pesquisado
			selecionarDenunciaDoc ();
			
		}
		
		// criar método para listar denúncias //
		public void listarDenuncias (String srtPesquisaDoc) {
			
			DenunciaDao denunciaDao = new DenunciaDao();
			List<Denuncia> denunciaList = denunciaDao.listDenuncia(srtPesquisaDoc);
			ObservableList<DenunciaTabela> obsListDenunciaTabela= FXCollections.observableArrayList();
			if (!obsListDenunciaTabela.isEmpty()) {
				obsListDenunciaTabela.clear();
			}
			for (Denuncia denuncia : denunciaList) {
				DenunciaTabela denTab = new DenunciaTabela(
						denuncia.getCod_Denuncia(), 
						denuncia.getDoc_Denuncia(),
						denuncia.getDoc_SEI_Denuncia(), 
						denuncia.getProc_SEI_Denuncia(),
						denuncia.getDesc_Denuncia(),
						//adicionado o objeto  endereçoFK, 
						denuncia.getEnderecoFK()
						);
				
					obsListDenunciaTabela.add(denTab);
			}
			
			tcDocumento.setCellValueFactory(new PropertyValueFactory<DenunciaTabela, String>("Doc_Denuncia")); 
	        tcDocSEI.setCellValueFactory(new PropertyValueFactory<DenunciaTabela, String>("Doc_SEI_Denuncia")); 
	        tcProcSEI.setCellValueFactory(new PropertyValueFactory<DenunciaTabela, String>("Proc_SEI_Denuncia")); 
	        
	        tvListaDoc.setItems(obsListDenunciaTabela); 
		}
		
		
		public void selecionarDenunciaDoc () {
			
			// TABLE VIEW SELECIONAR DOCUMENTO AO CLICAR NELE
			
			tvListaDoc.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
				public void changed(ObservableValue<?> observable , Object oldValue, Object newValue) {
																																							DenunciaTabela denTab = (DenunciaTabela) newValue;
				if (denTab == null) {
					
					//lblDoc.setText("Campo nulo!");
					
				} else {

					
					Denuncia dGeral = new Denuncia(denTab);
					
					dGeralEnd = dGeral;
					//lblDoc.setText(dGeralEnd.getDoc_Denuncia() + "  |  SEI nº: " + dGeralEnd.getDoc_SEI_Denuncia());
					
				}
				}
			});
		}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
		
	}

}
