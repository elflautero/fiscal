package fiscalizacao;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DocumentosOutorga {
	
	String htmlRel;
	
		public String getHtmlRel() {
			return htmlRel;
		}
	
		public void setHtmlRel(String htmlRel) {
			this.htmlRel = htmlRel;
		}

	public String criarDocumento (Outorga outorga) {
			
			Document docHtml = null;
			
			docHtml = Jsoup.parse(htmlRel, "UTF-8").clone();
			
			//-- INTERESSADO , TIPO , FINALIDADES --//
			try { docHtml.select("interessado").prepend(outorga.getInteressado());} catch (Exception e) {docHtml.select("interessado").prepend("");};
			try { docHtml.select("tipoPoco").prepend(outorga.getTipoPoco());} catch (Exception e) {docHtml.select("tipoPoco").prepend("");};
			
			try { docHtml.select("finalidade1").prepend(outorga.getFinalidade()[0]);} catch (Exception e) {docHtml.select("finalidade1").prepend("");};
			try { docHtml.select("finalidade2").prepend(outorga.getFinalidade()[1]);} catch (Exception e) {docHtml.select("finalidade2").prepend("");};
			try { docHtml.select("finalidade3").prepend(outorga.getFinalidade()[2]);} catch (Exception e) {docHtml.select("finalidade3").prepend("");};
			try { docHtml.select("finalidade4").prepend(outorga.getFinalidade()[3]);} catch (Exception e) {docHtml.select("finalidade4").prepend("");};
			try { docHtml.select("finalidade5").prepend(outorga.getFinalidade()[4]);} catch (Exception e) {docHtml.select("finalidade5").prepend("");};
			
			try { docHtml.select("processo").prepend(outorga.getProcesso());} catch (Exception e) {docHtml.select("processo").prepend("");};
			
			
			try { docHtml.select("cpfCNPJ").prepend(outorga.getCpfCNPJ());} catch (Exception e) {docHtml.select("cpfCNPJ").prepend("");};
			try { docHtml.select("endereco").prepend(outorga.getEndereco());} catch (Exception e) {docHtml.select("endereco").prepend("");};
			
			try { docHtml.select("baciaHid").prepend(outorga.getBacia());} catch (Exception e) {docHtml.select("baciaHid").prepend("");};
			try { docHtml.select("baciaUH").prepend(outorga.getUh());} catch (Exception e) {docHtml.select("baciaUH").prepend("");};
			
			// coordenadas
			try { docHtml.select("pontoCapLat").prepend(outorga.getLat().toString());} catch (Exception e) {docHtml.select("pontoCapLat").prepend("");};
			try { docHtml.select("pontoCapLng").prepend(outorga.getLng().toString());} catch (Exception e) {docHtml.select("pontoCapLng").prepend("");};
			
			
			// VAZ�O LITROS HORA
			try { docHtml.select("vazJanLH").prepend(String.valueOf(outorga.getVazaoHora()[0]));} catch (Exception e) {docHtml.select("vazJanLH").prepend("");};
			try { docHtml.select("vazFevLH").prepend(String.valueOf(outorga.getVazaoHora()[1]));} catch (Exception e) {docHtml.select("vazFevLH").prepend("");};
			try { docHtml.select("vazMarLH").prepend(String.valueOf(outorga.getVazaoHora()[2]));} catch (Exception e) {docHtml.select("vazMarLH").prepend("");};
			try { docHtml.select("varAbrLH").prepend(String.valueOf(outorga.getVazaoHora()[3]));} catch (Exception e) {docHtml.select("varAbrLH").prepend("");};
			try { docHtml.select("vazMaiLH").prepend(String.valueOf(outorga.getVazaoHora()[4]));} catch (Exception e) {docHtml.select("vazMaiLH").prepend("");};
			try { docHtml.select("vazJunLH").prepend(String.valueOf(outorga.getVazaoHora()[5]));} catch (Exception e) {docHtml.select("vazJunLH").prepend("");};
			
			try { docHtml.select("vazJulLH").prepend(String.valueOf(outorga.getVazaoHora()[6]));} catch (Exception e) {docHtml.select("vazJulLH").prepend("");};
			try { docHtml.select("vazAgoLH").prepend(String.valueOf(outorga.getVazaoHora()[7]));} catch (Exception e) {docHtml.select("vazAgoLH").prepend("");};
			try { docHtml.select("vazSetLH").prepend(String.valueOf(outorga.getVazaoHora()[8]));} catch (Exception e) {docHtml.select("vazSetLH").prepend("");};
			try { docHtml.select("vazOutLH").prepend(String.valueOf(outorga.getVazaoHora()[9]));} catch (Exception e) {docHtml.select("vazOutLH").prepend("");};
			try { docHtml.select("vazNovLH").prepend(String.valueOf(outorga.getVazaoHora()[10]));} catch (Exception e) {docHtml.select("vazNovLH").prepend("");};
			try { docHtml.select("vazDezLH").prepend(String.valueOf(outorga.getVazaoHora()[11]));} catch (Exception e) {docHtml.select("vazDezLH").prepend("");};
			
			// TEMPO DE CAPTA��O
			try { docHtml.select("tJAN").prepend(String.valueOf(outorga.getTempoCaptacao()[0]));} catch (Exception e) {docHtml.select("tJAN").prepend("");};
			try { docHtml.select("tFEV").prepend(String.valueOf(outorga.getTempoCaptacao()[1]));} catch (Exception e) {docHtml.select("tFEV").prepend("");};
			try { docHtml.select("tMAR").prepend(String.valueOf(outorga.getTempoCaptacao()[2]));} catch (Exception e) {docHtml.select("tMAR").prepend("");};
			try { docHtml.select("tABR").prepend(String.valueOf(outorga.getTempoCaptacao()[3]));} catch (Exception e) {docHtml.select("tABR").prepend("");};
			try { docHtml.select("tMAI").prepend(String.valueOf(outorga.getTempoCaptacao()[4]));} catch (Exception e) {docHtml.select("tMAI").prepend("");};
			try { docHtml.select("tJUN").prepend(String.valueOf(outorga.getTempoCaptacao()[5]));} catch (Exception e) {docHtml.select("tJUN").prepend("");};
			
			try { docHtml.select("tJUL").prepend(String.valueOf(outorga.getTempoCaptacao()[6]));} catch (Exception e) {docHtml.select("tJUL").prepend("");};
			try { docHtml.select("tAGO").prepend(String.valueOf(outorga.getTempoCaptacao()[7]));} catch (Exception e) {docHtml.select("tAGO").prepend("");};
			try { docHtml.select("tSET").prepend(String.valueOf(outorga.getTempoCaptacao()[8]));} catch (Exception e) {docHtml.select("tSET").prepend("");};
			try { docHtml.select("tOUT").prepend(String.valueOf(outorga.getTempoCaptacao()[9]));} catch (Exception e) {docHtml.select("tOUT").prepend("");};
			try { docHtml.select("tNOV").prepend(String.valueOf(outorga.getTempoCaptacao()[10]));} catch (Exception e) {docHtml.select("tNOV").prepend("");};
			try { docHtml.select("tDEZ").prepend(String.valueOf(outorga.getTempoCaptacao()[11]));} catch (Exception e) {docHtml.select("tDEZ").prepend("");};
			
			
			// VAZ�O LITROS DIA
			try { docHtml.select("vazJanLD").prepend(String.valueOf(outorga.getVazaoDia()[0]));} catch (Exception e) {docHtml.select("vazJanLD").prepend("");};
			try { docHtml.select("vazFevLD").prepend(String.valueOf(outorga.getVazaoDia()[1]));} catch (Exception e) {docHtml.select("vazFevLD").prepend("");};
			try { docHtml.select("vazMarLD").prepend(String.valueOf(outorga.getVazaoDia()[2]));} catch (Exception e) {docHtml.select("vazMarLD").prepend("");};
			try { docHtml.select("varAbrLD").prepend(String.valueOf(outorga.getVazaoDia()[3]));} catch (Exception e) {docHtml.select("varAbrLD").prepend("");};
			try { docHtml.select("vazMaiLD").prepend(String.valueOf(outorga.getVazaoDia()[4]));} catch (Exception e) {docHtml.select("vazMaiLD").prepend("");};
			try { docHtml.select("vazJunLD").prepend(String.valueOf(outorga.getVazaoDia()[5]));} catch (Exception e) {docHtml.select("vazJunLD").prepend("");};
			
			try { docHtml.select("vazJulLD").prepend(String.valueOf(outorga.getVazaoDia()[6]));} catch (Exception e) {docHtml.select("vazJulLD").prepend("");};
			try { docHtml.select("vazAgoLD").prepend(String.valueOf(outorga.getVazaoDia()[7]));} catch (Exception e) {docHtml.select("vazAgoLD").prepend("");};
			try { docHtml.select("vazSetLD").prepend(String.valueOf(outorga.getVazaoDia()[8]));} catch (Exception e) {docHtml.select("vazSetLD").prepend("");};
			try { docHtml.select("vazOutLD").prepend(String.valueOf(outorga.getVazaoDia()[9]));} catch (Exception e) {docHtml.select("vazOutLD").prepend("");};
			try { docHtml.select("vazNovLD").prepend(String.valueOf(outorga.getVazaoDia()[10]));} catch (Exception e) {docHtml.select("vazNovLD").prepend("");};
			try { docHtml.select("vazDezLD").prepend(String.valueOf(outorga.getVazaoDia()[11]));} catch (Exception e) {docHtml.select("vazDezLD").prepend("");};
			
			try { docHtml.select("subsistema").prepend(String.valueOf(outorga.getSubsistema()));} catch (Exception e) {docHtml.select("subsistema").prepend("");};
			try { docHtml.select("vazaoMedia").prepend(String.valueOf(outorga.getVazaoMedia()));} catch (Exception e) {docHtml.select("vazaoMedia").prepend("");};
			try { docHtml.select("vazaoBombeamento").prepend(String.valueOf(outorga.getVazaoBombeamento()));} catch (Exception e) {docHtml.select("vazaoBombeamento").prepend("");};
			try { docHtml.select("profundidade").prepend((String.valueOf(outorga.getProfundidade())));} catch (Exception e) {docHtml.select("profundidade").prepend("");};
			try { docHtml.select("nivelEstatico").prepend((String.valueOf(outorga.getNivelEstatico())));} catch (Exception e) {docHtml.select("nivelEstatico").prepend("");};
			try { docHtml.select("nivelDinamico").prepend((String.valueOf(outorga.getNivelDinamico())));} catch (Exception e) {docHtml.select("nivelDinamico").prepend("");};
			
			
			try { docHtml.select("subfinalidade1").prepend(outorga.getSubfinalidade()[0]);} catch (Exception e) {docHtml.select("subfinalidade1").prepend("");};
			try { docHtml.select("subfinalidade2").prepend(outorga.getSubfinalidade()[1]);} catch (Exception e) {docHtml.select("subfinalidade2").prepend("");};
			try { docHtml.select("subfinalidade3").prepend(outorga.getSubfinalidade()[2]);} catch (Exception e) {docHtml.select("subfinalidade3").prepend("");};
			try { docHtml.select("subfinalidade4").prepend(outorga.getSubfinalidade()[3]);} catch (Exception e) {docHtml.select("subfinalidade4").prepend("");};
			try { docHtml.select("subfinalidade5").prepend(outorga.getSubfinalidade()[4]);} catch (Exception e) {docHtml.select("subfinalidade5").prepend("");};
			
			try { docHtml.select("demanda1").prepend(String.valueOf(outorga.getDemanda()[0]));} catch (Exception e) {docHtml.select("demanda1").prepend("");};
			try { docHtml.select("demanda2").prepend(String.valueOf(outorga.getDemanda()[1]));} catch (Exception e) {docHtml.select("demanda2").prepend("");};
			try { docHtml.select("demanda3").prepend(String.valueOf(outorga.getDemanda()[2]));} catch (Exception e) {docHtml.select("demanda3").prepend("");};
			try { docHtml.select("demanda4").prepend(String.valueOf(outorga.getDemanda()[3]));} catch (Exception e) {docHtml.select("demanda4").prepend("");};
			try { docHtml.select("demanda5").prepend(String.valueOf(outorga.getDemanda()[4]));} catch (Exception e) {docHtml.select("demanda5").prepend("");};
			
			try { docHtml.select("vazaoExplotavel").prepend(String.valueOf(outorga.getVazaoExplotavel()));} catch (Exception e) {docHtml.select("vazaoExplotavel").prepend("");};
			try { docHtml.select("numeroDePocos").prepend(String.valueOf(outorga.getNumeroDePocos()));} catch (Exception e) {docHtml.select("numeroDePocos").prepend("");};
			try { docHtml.select("vazaoTotalOutorgavel").prepend(String.valueOf(outorga.getVazaoTotalOutorgavel()));} catch (Exception e) {docHtml.select("vazaoTotalOutorgavel").prepend("");};
			try { docHtml.select("porcentagemUtilizada").prepend(String.valueOf(outorga.getPorcentagemUtilizada()));} catch (Exception e) {docHtml.select("porcentagemUtilizada").prepend("");};
			try { docHtml.select("volumeDisponivelAtual").prepend(String.valueOf(outorga.getVolumeDisponivelAtual()));} catch (Exception e) {docHtml.select("volumeDisponivelAtual").prepend("");};
			try { docHtml.select("volumeDiponivelSuficiente").prepend(String.valueOf(outorga.getVolumeDiponivelSuficiente()));} catch (Exception e) {docHtml.select("volumeDiponivelSuficiente").prepend("");};
		
			
			
			
			String html = new String ();
			
			html = docHtml.toString();
			
			html = html.replace("\"", "'");
			html = html.replace("\n", "");
			
			html =  "\"" + html + "\"";
			
			//-- webview do relat�rio --//
			/*
			WebView browser = new WebView();
			WebEngine webEngine = browser.getEngine();
			webEngine.loadContent(html);
			
			Scene scene = new Scene(browser);
			
			Stage stage = new Stage(StageStyle.UTILITY);
			stage.setWidth(1000);
			stage.setHeight(650);
		    stage.setScene(scene);
		    stage.setMaximized(false);
		    stage.setResizable(false);
		    
		    stage.show();
		    */
		    
			return html;
		    
		   // //TabNavegadorController.html = html;
		   // TabNavegadorController.numIframe = 1;
	
		}
	
}
