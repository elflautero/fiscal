package fiscalizacao;

import org.junit.Test;

public class TabVistoriaTeste {

	@Test
	public void test() {
		
		
	}
}



// XXXXXXXXXXXXXXXXX   funcionando, mas não no arquivo jar compilado
/*
			File file = null;
			try {
				file = new File (getClass().getResource("/html/relatoriovistoria.html").toURI());
			} catch (URISyntaxException e) {
				
				e.printStackTrace();
			}
			
			// caminho  do : /fiscalizacao/src/main/resources/html/relatoriovistoria.html
				
			//como funciona no WebEngine: .load(getClass().getResource("/html/map.html")
			
			Document docHtml = null;
			
			try {
				docHtml = Jsoup.parse(file, "UTF-8");  // retirei o  .clone()
				
				System.out.println(docHtml);
			} catch (IOException e1) {
				System.out.println("Erro na leitura do documento e Jsoup!!!");
				e1.printStackTrace();
			}
			*/
  // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
