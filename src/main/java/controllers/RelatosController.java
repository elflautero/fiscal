package controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import dao.VistoriaDao;
import entity.Vistoria;
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
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.web.HTMLEditor;
import tabela.VistoriaTabela;
 
public class RelatosController implements Initializable{
	
	@FXML Button btnNovo;
	@FXML Button btnSalvar;
	@FXML Button btnEditar;
	@FXML Button btnExcluir;
	@FXML Button btnCancelar;
	@FXML Button btnPesquisar;
	
	@FXML HTMLEditor htmlRelato;
	@FXML Pane paneRelato;
	@FXML TextField tfPesquisar;
	
	// TableView Endereço //
				@FXML private TableView <VistoriaTabela> tvVistoria;
				
				@FXML TableColumn<VistoriaTabela, String> tcNumero;
				@FXML TableColumn<VistoriaTabela, String> tcSEI;
				@FXML TableColumn<VistoriaTabela, String> tcData;
	
	String strPesquisa = "";

public void btnPesquisarHab (ActionEvent event) {
	
	strPesquisa = tfPesquisar.getText();
	listarVistorias(strPesquisa);
	selecionarVistoria();
	
}
	

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
		tfPesquisar.setOnKeyReleased(event -> {
	  		  if (event.getCode() == KeyCode.ENTER){
	  		     btnPesquisar.fire();
	  		  }
	  		});
	
	}
	
	public void listarVistorias (String strPesquisa) {
 		
	 	// --- conexão - listar endereços --- //
		VistoriaDao visDao = new VistoriaDao();
		List<Vistoria> visList = visDao.listarVistoria(strPesquisa);
		ObservableList<VistoriaTabela> oListVis = FXCollections.observableArrayList();
		
		
		if (!oListVis.isEmpty()) {
			oListVis.clear();
		}
		
			for (Vistoria vis : visList) {
				
			VistoriaTabela visTab = new VistoriaTabela(
					
				vis.getVisCodigo(),
				vis.getVisEndCodigoFK(),
				vis.getVisObjeto(),
				vis.getVisApresentacao(),
				vis.getVisRelato(),
				vis.getVisRecomendacoes(),
				vis.getVisInfracoes(),
				vis.getVisPenalidades(),
				vis.getVisAtenuantes(),
				vis.getVisAgravantes(),
				vis.getVisIdentificacao(),
				vis.getVisSEI(),
				vis.getVisDataFiscalizacao(),
				vis.getVisDataCriacao(),
				vis.getVisListAtos()
				
				);
				
				
				oListVis.add(visTab);
						
		}
			
			
			tcNumero.setCellValueFactory(new PropertyValueFactory<VistoriaTabela, String>("visIdentificacao")); 
			//tcEndRA.setCellValueFactory(new PropertyValueFactory<EnderecoTabela, String>("RA_Endereco")); 
			//tcEndCid.setCellValueFactory(new PropertyValueFactory<EnderecoTabela, String>("CEP_Endereco")); 
			
			tvVistoria.setItems(oListVis);
			
	 	}
	
	//método selecionar vistoria -- //
	public void selecionarVistoria () {
			
				tvVistoria.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
					public void changed(ObservableValue<?> observable , Object oldValue, Object newValue) {
					
					VistoriaTabela visTab = (VistoriaTabela) newValue;
					
					if (visTab == null) {
						
					} else {
	
						htmlRelato.setHtmlText(visTab.getVisRelato());
						
					}
					}
				});
			}
	
	}
