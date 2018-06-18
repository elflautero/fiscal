package controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import dao.DemandaDao;
import entity.Demanda;
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
import tabela.DemandaTabela;

public class TabEndBuscarDocController implements Initializable {
	
	// TABLE BUSCAR DENÚNCIA
		@FXML private TableView <DemandaTabela> tvListaDoc;
		
		@FXML private TableColumn<DemandaTabela, String> tcDocumento;
		@FXML private TableColumn<DemandaTabela, String> tcDocSEI;
		@FXML private TableColumn<DemandaTabela, String> tcProcSEI;
		
		
		@FXML Button btnPesqDoc = new Button();
		
		@FXML TextField tfPesquisar = new TextField();
		
		//variável geral para outros controllers
		public Demanda dGeralEnd;
		
		String strPesquisaDoc = "";
	
		
		public void btnPesqDocHab (ActionEvent event) {
			
			String srtPesquisaDoc = tfPesquisar.getText();
			
			listarDenuncias (srtPesquisaDoc); // chamar método listar denúncias //
			
			// Selecionar um documento pesquisado
			selecionarDenunciaDoc ();
			
		}
		
		// criar método para listar denúncias //
		public void listarDenuncias (String srtPesquisaDoc) {
			
			DemandaDao denunciaDao = new DemandaDao();
			List<Demanda> denunciaList = denunciaDao.listarDemandas(srtPesquisaDoc);
			ObservableList<DemandaTabela> obsListDenunciaTabela= FXCollections.observableArrayList();
			if (!obsListDenunciaTabela.isEmpty()) {
				obsListDenunciaTabela.clear();
			}
			for (Demanda denuncia : denunciaList) {
				DemandaTabela denTab = new DemandaTabela(
						denuncia.getDemID(),
						denuncia.getDemDocumento(),
						denuncia.getDemDocumentoSEI(),
						denuncia.getDemProcessoSEI(),
						denuncia.getDemDescricao(),
						denuncia.getDemDistribuicao(),
						denuncia.getDemRecebimento(),
						
						denuncia.getDemAtualizacao(),
						
						denuncia.getDemEnderecoFK()
						);
				
					obsListDenunciaTabela.add(denTab);
			}
			
			tcDocumento.setCellValueFactory(new PropertyValueFactory<DemandaTabela, String>("Doc_Denuncia")); 
	        tcDocSEI.setCellValueFactory(new PropertyValueFactory<DemandaTabela, String>("Doc_SEI_Denuncia")); 
	        tcProcSEI.setCellValueFactory(new PropertyValueFactory<DemandaTabela, String>("Proc_SEI_Denuncia")); 
	        
	        tvListaDoc.setItems(obsListDenunciaTabela); 
		}
		
		
		public void selecionarDenunciaDoc () {
			
			// TABLE VIEW SELECIONAR DOCUMENTO AO CLICAR NELE
			
			tvListaDoc.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
				public void changed(ObservableValue<?> observable , Object oldValue, Object newValue) {
																																							DemandaTabela denTab = (DemandaTabela) newValue;
				if (denTab == null) {
					
					//lblDoc.setText("Campo nulo!");
					
				} else {

					
					Demanda dGeral = new Demanda(denTab);
					
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
