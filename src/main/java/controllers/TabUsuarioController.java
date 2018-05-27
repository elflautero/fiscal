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
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import tabela.InterferenciaTabela;
import tabela.UsuarioTabela;

public class TabUsuarioController implements Initializable {
	
	//-- Strings --//
	String strPesquisaUsuario = "";
	
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
	
	// --- objeto para passar os valor pelo MainControoler para outro controller --- //
	public Endereco eGeralUs;
	
	
	//-- TableView Endereço --//
			@FXML private javafx.scene.control.TableView<UsuarioTabela> tvListaUs;
			
			@FXML TableColumn<InterferenciaTabela, String> tcUsNome;
			@FXML TableColumn<InterferenciaTabela, String> tcUsCPFCNPJ;
			@FXML TableColumn<InterferenciaTabela, String> tcUsEndereco;
	
	
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
		
		//-- listar --//
		listarUsuarios(strPesquisaUsuario);
		//-- selecionar --//
		selecionarUsuario();
		
		modularBotoesInicial();
			
	}
	
	public void btnUsEditarHab (ActionEvent event) {
		
		if (cbTipoPessoa.isDisable()) {
			
			cbTipoPessoa.setDisable(false);
			tfUsNome.setDisable(false);
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
			//*  //eGeralUs
			UsuarioTabela usTabEditar = tvListaUs.getSelectionModel().getSelectedItem(); 
			
			Usuario us = new Usuario(usTabEditar);
			
			// -- preencher os campos -- //
			us.setUsTipo(cbTipoPessoa.getValue()); 
			us.setUsNome(tfUsNome.getText());
			us.setUsCPFCNPJ(tfUsCPFCNPJ.getText());
			us.setUsDescricaoEnd(tfUsEnd.getText()); 
			
			us.setUsRA(cbUsRA.getValue()); 
			
			us.setUsCEP(tfUsCEP.getText()); 
			us.setUsCidade(tfUsCidade.getText()); 
			
			us.setUsEstado(cbUsUF.getValue()); 
			
			us.setUsTelefone(tfUsTel.getText());
			us.setUsCelular(tfUsCel.getText());
			us.setUsEmail(tfUsEmail.getText());
			
			us.setUsEndCodigoFK(eGeralUs);
			
			UsuarioDao usDao = new UsuarioDao();
			
			usDao.mergeUsuario(us);
			
			//-- listar --//
			listarUsuarios(strPesquisaUsuario);
			//-- selecionar --//
			selecionarUsuario();
			
			modularBotoesInicial();
			
			
		}
		
	}
	
	public void btnUsExcluirHab (ActionEvent event) {
		
		//-- capturar usuário selecionado --//
		UsuarioTabela usExTab = tvListaUs.getSelectionModel().getSelectedItem(); 
		//-- prencher tabela usuario --//
		Usuario usEx = new Usuario(usExTab);
		
		UsuarioDao usExDao = new UsuarioDao();
		
		usExDao.removeUsuario(usEx.getUsCodigo());
		
		//-- listar --//
		listarUsuarios(strPesquisaUsuario);
		//-- selecionar --//
		selecionarUsuario();
		
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
		
		strPesquisaUsuario = tfUsPesquisar.getText();
		
		listarUsuarios (strPesquisaUsuario);
		
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
					
					"Brasília",
					"Gama",
					"Taguatinga",
					"Brazlândia",
					"Sobradinho",
					"Planaltina",
					"Paranoá",
					"Núcleo Bandeirante",
					"Ceilândia",
					"Guará",
					"Cruzeiro",
					"Samambaia",
					"Santa Maria",
					"São Sebastião",
					"Recanto das Emas",
					"Lago Sul",
					"Riacho Fundo",
					"Lago Norte",
					"Candangolândia",
					"Águas Claras",
					"Riacho Fundo II",
					"Sudoeste/Octogonal",
					"Varjão",
					"Park Way",
					"SCIA",
					"Sobradinho II",
					"Jardim Botânico",
					"Itapoã",
					"SIA",
					"Vicente Pires",
					"Fercal"); 	
		
		//-- combobox - unidade federal --//
		@FXML
		ChoiceBox<String> cbUsUF = new ChoiceBox<String>();
			ObservableList<String> olUsDF = FXCollections
				.observableArrayList("DF" , "GO", "Outro"); // box - seleção pessoa físcia ou jurídica

		//-- receber o endereço --//
	public Label lblEndUsuario;
	
	
	//-- método listar usuários --//
	public void listarUsuarios (String strPesquisa) {
		
		UsuarioDao usDao = new UsuarioDao();
		List<Usuario> usuarioList = usDao.listUsuario(strPesquisaUsuario);
		ObservableList<UsuarioTabela> olUsuarioTabela = FXCollections.observableArrayList();
		
		
		if (!olUsuarioTabela.isEmpty()) {
			olUsuarioTabela.clear();
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
				
				olUsuarioTabela.add(usTab);
					
				}
				
				tcUsNome.setCellValueFactory(new PropertyValueFactory<InterferenciaTabela, String>("usNome"));
				tcUsCPFCNPJ.setCellValueFactory(new PropertyValueFactory<InterferenciaTabela, String>("usCPFCNPJ"));
				tcUsEndereco.setCellValueFactory(new PropertyValueFactory<InterferenciaTabela, String>("usDescricaoEnd"));
				
				tvListaUs.setItems(olUsuarioTabela);
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










