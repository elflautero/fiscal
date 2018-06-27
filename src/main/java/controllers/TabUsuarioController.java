package controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import dao.UsuarioDao;
import entity.Endereco;
import entity.Usuario;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import tabela.InterferenciaTabela;
import tabela.UsuarioTabela;

public class TabUsuarioController implements Initializable {
	
	//-- Strings --//
	String strPesquisa = "";
	
	@FXML Pane tabUsuario = new Pane();
	
	@FXML TextField tfUsNome;
	@FXML TextField tfUsCPFCNPJ;
	@FXML TextField tfUsEnd;
	@FXML TextField tfUsCEP;
	@FXML TextField tfUsCidade;
	@FXML TextField tfUsTel;
	@FXML TextField tfUsCel;
	@FXML TextField tfUsEmail;
	
	
	//-- Persistência --//
	
	@FXML Button btnUsNovo;
	@FXML Button btnUsSalvar;
	@FXML Button btnUsEditar;
	@FXML Button btnUsExcluir;
	@FXML Button btnUsCancelar;
	@FXML TextField tfUsPesquisar = new TextField();
	@FXML Button btnUsPesq;
	@FXML Button btnBucarEnd;
	
	@FXML CheckBox cbEndEmp;
	
	// --- objeto para passar os valor pelo MainControoler para outro controller --- //
	public Endereco eGeralUs;
	
	
	//-- TableView Endereço --//
			@FXML private javafx.scene.control.TableView<UsuarioTabela> tvListaUs;
			
			@FXML TableColumn<InterferenciaTabela, String> tcUsNome;
			@FXML TableColumn<InterferenciaTabela, String> tcUsCPFCNPJ;
			@FXML TableColumn<InterferenciaTabela, String> tcUsEndereco;
	
	public void cbEndEmpHab (ActionEvent event) {
		
		int count = 0;
		
		if (cbEndEmp.isSelected()) {
			count ++;
			try{tfUsEnd.setText(eGeralUs.getDesc_Endereco());}catch (Exception e) {tfUsEnd.setText("");};
			try{cbUsRA.setValue(eGeralUs.getRA_Endereco());}catch (Exception e) {cbUsRA.setValue("");};
			try{tfUsCEP.setText(eGeralUs.getCEP_Endereco());}catch (Exception e) {tfUsCEP.setText("");};
			try{tfUsCidade.setText(eGeralUs.getCid_Endereco());}catch (Exception e) {tfUsCidade.setText("");};
			try{cbUsUF.setValue(eGeralUs.getUF_Endereco());}catch (Exception e) {cbUsUF.setValue("");};
			
		}
		System.out.println("check box usuario - endereço: " + count);
	}
	public void btnUsNovoHab (ActionEvent event) {
		
		cbTipoPessoa.setValue(null);
		
		tfUsNome.setText("");
		tfUsCPFCNPJ.setText("");
		
		tfUsEnd.setText("");
		
		cbUsRA.setValue(null);
		
		tfUsCEP.setText("");
		tfUsCidade.setText("Brasília");
		
		cbUsUF.setValue("DF");
		
		tfUsTel.setText("");
		tfUsCel.setText("");
		tfUsEmail.setText("");
		
		cbTipoPessoa.setDisable(false);
		tfUsNome.setDisable(false);
		tfUsCPFCNPJ.setDisable(false);  //tfEndUF.setDisable(false);
		
		cbEndEmp.setDisable(false);
		
		tfUsEnd.setDisable(false);
		cbUsRA.setDisable(false);
		tfUsCEP.setDisable(false);
		tfUsCidade.setDisable(false);
		cbUsUF.setDisable(false);
		tfUsTel.setDisable(false);
		tfUsCel.setDisable(false);
		tfUsEmail.setDisable(false);
		
		btnUsSalvar.setDisable(false);
		btnUsEditar.setDisable(true);
		btnUsExcluir.setDisable(true);
		btnUsEditar.setDisable(true);
		
		
	}
	
	public void btnUsSalvarHab (ActionEvent event) {
		
		obsList = FXCollections.observableArrayList();
		
		if (eGeralUs == null) {
			
			Alert aLat = new Alert (Alert.AlertType.ERROR);
			aLat.setTitle("Alerta!!!");
			aLat.setContentText("Endereço relacionado ao usuário não selecionado!!!");
			aLat.setHeaderText(null);
			aLat.show();
			
		} else {
			
				if (cbTipoPessoa.getValue() == null  ||
						tfUsNome.getText().isEmpty()
						
						) {
					
					Alert a = new Alert (Alert.AlertType.ERROR);
					a.setTitle("Alerta!!!");
					a.setContentText("Informe: Tipo e Nome do Usuário!!!");
					a.setHeaderText(null);
					a.show();
					
				} else {
		
						Usuario usuario = new  Usuario ();
						
							usuario.setUsTipo(cbTipoPessoa.getValue());
							usuario.setUsNome(tfUsNome.getText());
							usuario.setUsCPFCNPJ(tfUsCPFCNPJ.getText()); 
							usuario.setUsDescricaoEnd(tfUsEnd.getText());
							usuario.setUsRA(cbUsRA.getValue());
							usuario.setUsCidade(tfUsCidade.getText());
							usuario.setUsEstado(cbUsUF.getValue());
							usuario.setUsCEP(tfUsCEP.getText());
							usuario.setUsTelefone(tfUsTel.getText());
							usuario.setUsCelular(tfUsCel.getText());
							usuario.setUsEmail(tfUsEmail.getText());
							
						
						Endereco endereco = new Endereco();
						
							endereco = eGeralUs;
							
							endereco.getListUsuarios().add(usuario);
							
							usuario.setUsEndCodigoFK(endereco);
							
						UsuarioDao  usDao = new UsuarioDao();
						
							usDao.salvaUsuario(usuario);
							usDao.mergeUsuario(usuario);
						
						UsuarioTabela usTab = new UsuarioTabela(
								
								usuario.getUsCodigo(),
								usuario.getUsTipo(),
								usuario.getUsNome(),
								usuario.getUsCPFCNPJ(),
								usuario.getUsDescricaoEnd(),
								usuario.getUsRA(),
								usuario.getUsCidade(),
								usuario.getUsEstado(),
								usuario.getUsCEP(),
								usuario.getUsTelefone(),
								usuario.getUsCelular(),
								usuario.getUsEmail(),
								
								usuario.getUsEndCodigoFK()
								
								);
						
						obsList.add(usTab);
						
						tvListaUs.setItems(obsList);
						
						//-- selecionar --//
						selecionarUsuario();
						
						modularBotoesInicial();
						
						Alert a = new Alert (Alert.AlertType.INFORMATION);
						a.setTitle("Parabéns!!!");
						a.setContentText("Informe: Cadastro salvo com sucesso!!!");
						a.setHeaderText(null);
						a.show();
						
				}
		}
			
	}
	
	public void btnUsEditarHab (ActionEvent event) {
		
		if (cbTipoPessoa.isDisable()) {
			
			cbTipoPessoa.setDisable(false);
			tfUsNome.setDisable(false);
			
			cbEndEmp.setDisable(false);
			
			tfUsCPFCNPJ.setDisable(false);
			tfUsEnd.setDisable(false);
			cbUsRA.setDisable(false);
			tfUsCEP.setDisable(false);
			tfUsCidade.setDisable(false);
			cbUsUF.setDisable(false);
			tfUsTel.setDisable(false);
			tfUsCel.setDisable(false);
			tfUsEmail.setDisable(false);
			
			btnUsSalvar.setDisable(true);
			btnUsEditar.setDisable(false);
			btnUsExcluir.setDisable(true);
			btnUsCancelar.setDisable(false);
			
		}
		
		else {
			
			if (cbTipoPessoa.getValue() == null  ||
					tfUsNome.getText().isEmpty()
					
					) {
				
				Alert a = new Alert (Alert.AlertType.ERROR);
				a.setTitle("Alerta!!!");
				a.setContentText("Informe: Tipo e Nome do Usuário!!!");
				a.setHeaderText(null);
				a.show();
				
			} else {
	
			//*  //eGeralUs
			UsuarioTabela usTab = tvListaUs.getSelectionModel().getSelectedItem(); 
			
			Usuario usuario = new Usuario(usTab);
			
			// -- preencher os campos -- //
			usuario.setUsTipo(cbTipoPessoa.getValue()); 
			usuario.setUsNome(tfUsNome.getText());
			usuario.setUsCPFCNPJ(tfUsCPFCNPJ.getText());
			usuario.setUsDescricaoEnd(tfUsEnd.getText()); 
			
			usuario.setUsRA(cbUsRA.getValue()); 
			
			usuario.setUsCEP(tfUsCEP.getText()); 
			usuario.setUsCidade(tfUsCidade.getText()); 
			
			usuario.setUsEstado(cbUsUF.getValue()); 
			
			usuario.setUsTelefone(tfUsTel.getText());
			usuario.setUsCelular(tfUsCel.getText());
			usuario.setUsEmail(tfUsEmail.getText());
			
			usuario.setUsEndCodigoFK(eGeralUs);
			
			UsuarioDao usDao = new UsuarioDao();
			
			usDao.mergeUsuario(usuario);
			
			obsList.remove(usTab);
			
			usTab = new UsuarioTabela(
					
					usuario.getUsCodigo(),
					usuario.getUsTipo(),
					usuario.getUsNome(),
					usuario.getUsCPFCNPJ(),
					usuario.getUsDescricaoEnd(),
					usuario.getUsRA(),
					usuario.getUsCidade(),
					usuario.getUsEstado(),
					usuario.getUsCEP(),
					usuario.getUsTelefone(),
					usuario.getUsCelular(),
					usuario.getUsEmail(),
					
					usuario.getUsEndCodigoFK()
					
					);
			
			obsList.add(usTab);
			
			tvListaUs.setItems(obsList);
			
			//-- selecionar --//
			selecionarUsuario();
			
			modularBotoesInicial();
			
			Alert a = new Alert (Alert.AlertType.INFORMATION);
			a.setTitle("Parabéns!!!");
			a.setContentText("Informe: Cadastro editado com sucesso!!!");
			a.setHeaderText(null);
			a.show();
			
			
			}
			
		}
		
	}
	
	public void btnUsExcluirHab (ActionEvent event) {
		
		try {
			//-- capturar usuário selecionado --//
			UsuarioTabela usTab = tvListaUs.getSelectionModel().getSelectedItem(); 
			//-- prencher tabela usuario --//
			Usuario usEx = new Usuario(usTab);
			
			UsuarioDao usExDao = new UsuarioDao();
			
			usExDao.removeUsuario(usEx.getUsCodigo());
			
			obsList.remove(usTab);
			
			//-- selecionar --//
			selecionarUsuario();
			modularBotoesInicial();
		
				Alert a = new Alert (Alert.AlertType.INFORMATION);
				a.setTitle("Parabéns!!!");
				a.setContentText("Cadastro excluído com sucesso!!!");
				a.setHeaderText(null);
				a.show();
	
		}	catch (Exception e) {
		
				Alert a = new Alert (Alert.AlertType.ERROR);
				a.setTitle("Alerta!!!");
				a.setContentText(e.toString());
				a.setHeaderText("Erro ao escluir o cadastro!!!");
				a.show();
				};
		
	}
	
	public void btnUsCancelarHab (ActionEvent event) {
		
		modularBotoesInicial ();
		
		cbTipoPessoa.setValue(null);
		
		tfUsNome.setText("");
		tfUsCPFCNPJ.setText("");
		tfUsEnd.setText("");
		
		cbUsRA.setValue(null);
		
		tfUsCEP.setText("");
		tfUsCidade.setText("");
		
		cbUsUF.setValue(null);
		
		tfUsTel.setText("");
		tfUsCel.setText("");
		tfUsEmail.setText("");
		
	}
	
	//-- botão pesquisar usuário --//
	public void btnUsPesqHab (ActionEvent event) {
		
		strPesquisa = tfUsPesquisar.getText();
		
		listarUsuarios (strPesquisa);
		
		selecionarUsuario ();
		
		modularBotoesInicial();
		
	}


	//-- combobox - tipo de pessoa --//
	@FXML
	ChoiceBox<String> cbTipoPessoa = new ChoiceBox<String>();
		ObservableList<String> olTipoPessoa = FXCollections
			.observableArrayList("Física" , "Jurídica"); // box - seleção pessoa físcia ou jurídica
		
	//-- combobox - Região Administrativa --//	
	@FXML
	ChoiceBox<String> cbUsRA = new ChoiceBox<String>();
		ObservableList<String> olUsRA = FXCollections
			.observableArrayList(
					

					"Águas Claras",
					"Brasília",
					"Brazlândia",
					"Candangolândia",
					"Ceilândia",
					"Cruzeiro",
					"Fercal",
					"Gama",
					"Guará",
					"Itapoã",
					"Jardim Botânico",
					"Lago Norte",
					"Lago Sul",
					"Núcleo Bandeirante",
					"Paranoá",
					"Park Way",
					"Planaltina",
					"Recanto das Emas",
					"Riacho Fundo II",
					"Riacho Fundo",
					"Samambaia",
					"Santa Maria",
					"São Sebastião",
					"SCIA",
					"SIA",
					"Sobradinho II",
					"Sobradinho	",
					"Sudoeste/Octogonal",
					"Taguatinga	",
					"Varjão	",
					"Vicente Pires"
					
					); 	
		
		//-- combobox - unidade federal --//
		@FXML
		ChoiceBox<String> cbUsUF = new ChoiceBox<String>();
			ObservableList<String> olUsDF = FXCollections
				.observableArrayList("DF" , "GO", "Outro"); // box - seleção pessoa físcia ou jurídica

		//-- receber o endereço --//
	public Label lblEndUsuario;
	
	ObservableList<UsuarioTabela> obsList;
	//-- método listar usuários --//
	public void listarUsuarios (String strPesquisa) {
		
		UsuarioDao usDao = new UsuarioDao();
		List<Usuario> usuarioList = usDao.listUsuario(strPesquisa);
		obsList = FXCollections.observableArrayList();
		
		
		if (!obsList.isEmpty()) {
			obsList.clear();
		}
		
			for (Usuario usuario : usuarioList) {
				
				UsuarioTabela usTab = new UsuarioTabela(
						
						usuario.getUsCodigo(),
						usuario.getUsTipo(),
						usuario.getUsNome(),
						usuario.getUsCPFCNPJ(),
						usuario.getUsDescricaoEnd(),
						usuario.getUsRA(),
						usuario.getUsCidade(),
						usuario.getUsEstado(),
						usuario.getUsCEP(),
						usuario.getUsTelefone(),
						usuario.getUsCelular(),
						usuario.getUsEmail(),
						
						usuario.getUsEndCodigoFK()
						
						);
				
				obsList.add(usTab);
					
				}
				
				tvListaUs.setItems(obsList);
	}
		
		
	public void selecionarUsuario () {
		
		tvListaUs.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
			public void changed(ObservableValue<?> observable , Object oldValue, Object newValue) {
				
				UsuarioTabela usTab = (UsuarioTabela) newValue;
				
				if (usTab == null) {
					
					cbTipoPessoa.setValue(null);
					
					tfUsNome.setText("");
					tfUsCPFCNPJ.setText("");
					tfUsEnd.setText("");
					
					cbUsRA.setValue(null);
					
					tfUsCEP.setText("");
					tfUsCidade.setText("");
					
					cbUsUF.setValue(null);
					
					tfUsTel.setText("");
					tfUsCel.setText("");
					tfUsEmail.setText("");
					
					btnUsNovo.setDisable(true);
					btnUsSalvar.setDisable(true);
					btnUsEditar.setDisable(false);
					btnUsExcluir.setDisable(false);
					btnUsCancelar.setDisable(false);
					
				} else {

					// -- preencher os campos -- //
					cbTipoPessoa.setValue(usTab.getUsTipo());
					
					tfUsNome.setText(usTab.getUsNome());
					tfUsCPFCNPJ.setText(usTab.getUsCPFCNPJ());
					tfUsEnd.setText(usTab.getUsDescricaoEnd());
					
					cbUsRA.setValue(usTab.getUsRA());
					
					tfUsCEP.setText(usTab.getUsCEP());
					tfUsCidade.setText(usTab.getUsCidade());
					
					cbUsUF.setValue(usTab.getUsEstado());
					
					tfUsTel.setText(usTab.getUsTelefone());
					tfUsCel.setText(usTab.getUsCelular());
					tfUsEmail.setText(usTab.getUsEmail());
					
					//-- mudar endereço relacionado à denúncia --//
					
					eGeralUs = usTab.getEnderecoUsObjetoTabelaFK();
					lblEndUsuario.setText(eGeralUs.getDesc_Endereco()  + " |  RA: "  +  eGeralUs.getRA_Endereco());
					
					
					// -- habilitar e desabilitar botões -- //
					btnUsNovo.setDisable(true);
					btnUsSalvar.setDisable(true);
					btnUsEditar.setDisable(false);
					btnUsExcluir.setDisable(false);
					btnUsCancelar.setDisable(false);
					
					
				}
				}
			});
	}
		
	//-- INITIALIZE --//
	public void initialize(URL url, ResourceBundle rb) {
		
		tcUsNome.setCellValueFactory(new PropertyValueFactory<InterferenciaTabela, String>("usNome"));
		tcUsCPFCNPJ.setCellValueFactory(new PropertyValueFactory<InterferenciaTabela, String>("usCPFCNPJ"));
		tcUsEndereco.setCellValueFactory(new PropertyValueFactory<InterferenciaTabela, String>("usDescricaoEnd"));
		
		tfUsPesquisar.setOnKeyReleased(event -> {
	  		  if (event.getCode() == KeyCode.ENTER){
	  		     btnUsPesq.fire();
	  		  }
	  		});
	        

		cbTipoPessoa.setValue("Física");
		cbTipoPessoa.setItems(olTipoPessoa);
		
		//cbUsRA.setValue("Brasília");
		cbUsRA.setItems(olUsRA);
		
		cbUsUF.setValue("DF");
		cbUsUF.setItems(olUsDF);
		
		modularBotoesInicial ();
		
	}
	
	//-- Modular os botões na iniciação do programa --//
	private void modularBotoesInicial () {
		
		cbTipoPessoa.setDisable(true);
		tfUsNome.setDisable(true); 
		tfUsCPFCNPJ.setDisable(true);
		
		cbEndEmp.setDisable(true);
		
		tfUsEnd.setDisable(true);
		
		cbUsRA.setDisable(true); 
		
		tfUsCEP.setDisable(true);
		tfUsCidade.setDisable(true);
		
		cbUsUF.setDisable(true);
		
		tfUsTel.setDisable(true);
		tfUsCel.setDisable(true);
		tfUsEmail.setDisable(true);
		
		
		btnUsSalvar.setDisable(true);
		btnUsEditar.setDisable(true);
		btnUsExcluir.setDisable(true);
		btnUsNovo.setDisable(false);
		
	}
}










