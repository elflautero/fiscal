package fiscalizacao;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class LeitorExcel {
	
	String endereco;

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	

	
	public ObservableList<Outorga> getListaOutorgas () {
		
		ObservableList<Outorga> obsListaOutorgas;
		
		obsListaOutorgas = FXCollections.observableArrayList();
		
		Outorga outorga;
		
		// leitor //
		FileInputStream arquivo = null;
				
			try {
				
				arquivo = new FileInputStream(new File(endereco));
				
			} catch (FileNotFoundException e1) {
				
				e1.printStackTrace();
			}
				 
	        // arquivo lido //
	        XSSFWorkbook workbook = null;
	        
						try {
							
							workbook = new XSSFWorkbook(arquivo);
							
						} catch (IOException e1) {
							
							e1.printStackTrace();
							System.out.println(" erro workbook " + e1);
						}
						
	        // tabela no excel //
	        XSSFSheet sheet = workbook.getSheet("ANÁLISE");
	        
	        Iterator<Row> rowIterator = sheet.iterator();
	            
	        while (rowIterator.hasNext()) { // iterar linhas
	        	
	            Row row = rowIterator.next();
	            
	            Iterator<Cell> cellIterator = row.cellIterator(); // iterar celula

		            outorga = new Outorga();
		            
		            String[] finalidades = new String [5];
		            String[] subFinalidades = new String [5];
		            int[] demandas = new int [5];
		            
		            int [] vazaoLH = new int [12];
		            int [] vazaoLD = new int [12];
		            int [] vazaoTC = new int [12];
		            
		            // preencher a outorga
		            while (cellIterator.hasNext()) { // celula
		            	
		               Cell cell = cellIterator.next();
		               
		               switch (cell.getColumnIndex()) {
		              
		              
		               
		               case 0: // processo
		            	   
		            	   try {
		            		   outorga.setProcesso(cell.getStringCellValue());;}
		            	   catch (Exception e) 
		            	   		{ outorga.setProcesso(null);}
		            	   break;
		            	   
		               case 4: // tipo (registro, outorga, oficio
		            	   
		            	   try {
		            		   outorga.setTipo(cell.getStringCellValue());}
		            	   catch (Exception e) 
		            	   		{ outorga.setTipo(null);;}
		            	   break;
		            	   
		               case 5: // subsistema
		            	   
		            	   try {
		            		   outorga.setSubsistema(cell.getStringCellValue());}
		            	   catch (Exception e) 
		            	   		{ outorga.setSubsistema(null);}
		            	   break;
		            	   
		               case 6: // interessado
		            	   
		            	   try {
		            		   outorga.setInteressado(cell.getStringCellValue());}
		            	   catch (Exception e) 
		            	   		{ outorga.setInteressado(null);}
		            	   break;
		            	   
		               	case 7: // cpf cnpj
		            	   
		            	   try {
		            		   outorga.setCpfCNPJ(cell.getStringCellValue());}
		            	   catch (Exception e) 
		            	   		{ outorga.setCpfCNPJ(null);}
		            	   break;
		            	   
						case 8: // endereco
							   
							   try {
								   outorga.setEndereco(cell.getStringCellValue());}
							   catch (Exception e) 
							   		{ outorga.setEndereco(null);}
							   break;
							   
						case 10: // utm norte
							   
							   try {
								   outorga.setLat(Double.parseDouble(cell.getStringCellValue()));}
							   catch (Exception e) 
							   		{ outorga.setLat(null);}
							   break;
							   
						case 11: //  utm leste
							   
							   try {
								   outorga.setLng(Double.parseDouble(cell.getStringCellValue()));}
							   catch (Exception e) 
							   		{ outorga.setLng(null);}
							   break;
		            	   
		               case 12: // tipo po�o 
		            	   
		            	   try {
		            		   outorga.setTipoPoco(cell.getStringCellValue());}
		            	   catch (Exception e) {outorga.setTipoPoco(null);}
		            	  
		            	   break;
		            	   
		               case 13: // vazao medida
		            	   
		            	   try {
		            		   outorga.setVazaoMedia((int) cell.getNumericCellValue());}
		            	   catch (Exception e) {outorga.setVazaoMedia(0);}
		            	  
		            	   break;
		            	   
		               case 14: // vazao bombeamento
		            	   
		            	   try {
		            		   outorga.setVazaoBombeamento((int) cell.getNumericCellValue());}
		            	   catch (Exception e) {outorga.setVazaoBombeamento(0);}
		            	  
		            	   break;
		               
		               case 15: // profundidade
		            	   
		            	   try {
		            		   outorga.setProfundidade((int) cell.getNumericCellValue());}
		            	   catch (Exception e) {outorga.setProfundidade(0);}
		            	  
		            	   break;
		                   
		               case 16: // nivel estatico
		            	   
		            	   try {
		            		   outorga.setNivelEstatico((int) cell.getNumericCellValue());}
		            	   catch (Exception e) {outorga.setNivelEstatico(0);}
		            	  
		            	   break;
		            	   
		               case 17: // nivel dinamico
		            	   
		            	   try {
		            		   outorga.setNivelDinamico((int) cell.getNumericCellValue());}
		            	   catch (Exception e) {outorga.setNivelDinamico(0);}
		            	  
		            	   break;
		            	   
		            	   // ARRAY FINALIDADE //
		               case 19:  // finalidade1
		            	   
		            	   finalidades [0]  = (String) cell.getStringCellValue();
		            	   break;
		            	   
		               case 23: // finalidade2
		            	   
		            	   finalidades [1]  = (String) cell.getStringCellValue();
		            	   break;  
		            	   
						case 27: // finalidade2
						            	   
		            	   finalidades [2]  = (String) cell.getStringCellValue();
		            	   break; 
						            	   
						case 31: // finalidade2
							   
						   finalidades [3]  = (String) cell.getStringCellValue();
						   break; 
							   
						case 35: // finalidade2
							   
						   finalidades [4]  = (String) cell.getStringCellValue();
						   outorga.setFinalidade(finalidades);
							   break; 
							   
						// ARRAY SUB FINALIDADE
							   
						case 20:  // sub finalidade1
			            	   
			            	   subFinalidades [0]  = cell.getStringCellValue();
			            	   
			            	   
			            	   break;
			            	   
			               case 24: 
			            	   
			            	   subFinalidades [1]  = cell.getStringCellValue();
			            	   //System.out.println("sub fin 1 " + subFinalidades [0] + " e " + subFinalidades [1]);
			            	   break;  
			            	   
							case 28:
							            	   
								subFinalidades [2]  = cell.getStringCellValue();
			            	   break; 
							            	   
							case 32: 
								   
								subFinalidades [3]  = cell.getStringCellValue();
							   break; 
								   
							case 36: 
								   
								subFinalidades [4]  = cell.getStringCellValue();
								
								
							   outorga.setSubfinalidade(subFinalidades);
							   
								   break; 
								   
							// DEMANDA //   
							case 21:  // demandas
				            	   
								
				            	   try {demandas [0]  = (int) cell.getNumericCellValue();}
				            	   	catch (Exception e) {
				            	   		demandas[0] = 0;
				            	   	}
				            	   break;
				            	   
							case 25: // finalidade2
				            	   
								try {demandas [1]  = (int) cell.getNumericCellValue();}
			            	   	catch (Exception e) {
			            	   		demandas[1] = 0;
			            	   	} 
								 break;
				            	   
							case 29: // finalidade2
								            	   
								try {demandas [2]  = (int) cell.getNumericCellValue();}
			            	   	catch (Exception e) {
			            	   		demandas[2] = 0;
			            	   	}
				            	   break; 
								            	   
							case 33: // finalidade2
									   
								try {demandas [3]  = (int) cell.getNumericCellValue();}
			            	   	catch (Exception e) {
			            	   		demandas[3] = 0;
			            	   	}
								   break; 
									   
							case 37: // finalidade2
									   
								try {demandas [4]  = (int) cell.getNumericCellValue();}
			            	   	catch (Exception e) {
			            	   		demandas[4] = 0;
			            	   	}
									
									
								outorga.setDemanda(demandas);
								   
								break; 
								
						case 89: // vazao explotavel
					            	   
					            	   try {
					            		   outorga.setVazaoExplotavel((int) cell.getNumericCellValue());}
					            	   catch (Exception e) {
					            		   outorga.setVazaoExplotavel(0);}
					            	   break;
					            	   
						case 90: // numero pocos
			            	   
			            	   try {
			            		   outorga.setNumeroDePocos((int) cell.getNumericCellValue());}
			            	   catch (Exception e) {
			            		   outorga.setNumeroDePocos(0);}
			            	   break;
			            	   
						case 91: // vazao explotavel
			            	   
			            	   try {
			            		   outorga.setVazaoTotalOutorgavel((int) cell.getNumericCellValue());}
			            	   catch (Exception e) {
			            		   outorga.setVazaoTotalOutorgavel(0);}
			            	   break;
			            	   
						case 92: // vazao explotavel
			            	   
			            	   try {
			            		   outorga.setPorcentagemUtilizada((int) cell.getNumericCellValue());}
			            	   catch (Exception e) {
			            		   outorga.setPorcentagemUtilizada(0);}
			            	   break;
			            	   
						case 93: // vazao explotavel
			            	   
			            	   try {
			            		   outorga.setVolumeDiponivelSuficiente((int) cell.getNumericCellValue());}
			            	   catch (Exception e) {
			            		   outorga.setVolumeDiponivelSuficiente(0);}
			            	   break;
			            	   
						case 94: // vazao explotavel
			            	   
			            	   try {
			            		   outorga.setVolumeDiponivelSuficiente((int) cell.getNumericCellValue());}
			            	   catch (Exception e) {
			            		   outorga.setVolumeDiponivelSuficiente(0);}
			            	   break;
			            	     
						case 97: // bacia 
			            	   
			            	   try {
			            		   outorga.setBacia(cell.getStringCellValue());}
			            	   catch (Exception e) {
			            		   outorga.setBacia(null);}
			            	   break;
			            	   
						case 98: // uh unidade hidrogr�fica
			            	   
			            	   try {
			            		   outorga.setUh(cell.getStringCellValue());}
			            	   catch (Exception e) {
			            		   outorga.setUh(null);}
			            	   break;
		            	   
		               } // fim switch
		               
		               
		               // vaz�o litros por hora
		               if (cell.getColumnIndex() >= 39 && cell.getColumnIndex() <= 50) {
		            	   
		         	 	  
		         	 	  switch (cell.getCellType()) {
		         	 	  
		         	       case Cell.CELL_TYPE_NUMERIC:
		         	    	   
		         	           vazaoLH [cell.getColumnIndex() - 39 ] = (int) cell.getNumericCellValue();
		         	           break;
		         	           
		         	       case Cell.CELL_TYPE_STRING:
		         	
		         	           vazaoLH [cell.getColumnIndex() - 39 ] = 0;
		         	           break;
		         	 	  }
		         	 	  outorga.setVazaoHora(vazaoLH);
		         	   }
		               
		               // vaz�o litros por dia //
		               if (cell.getColumnIndex() >= 51 && cell.getColumnIndex() <= 62) {
		            	   
		          	 	  
		          	 	  switch (cell.getCellType()) {
		          	 	  
		          	       case Cell.CELL_TYPE_NUMERIC:
		          	    	   
		          	           vazaoLD [cell.getColumnIndex() - 51 ] = (int) cell.getNumericCellValue();
		          	           break;
		          	           
		          	       case Cell.CELL_TYPE_STRING:
		          	
		          	    	 vazaoLD [cell.getColumnIndex() - 51 ] = 0;
		          	           break;
		          	 	  }
		          	 	  outorga.setVazaoDia(vazaoLD);
		          	   }
		               
		               
		               // tempo de capta��o //
		               if (cell.getColumnIndex() >= 63 && cell.getColumnIndex() <= 74) {
		            	   
		          	 	  
		          	 	  switch (cell.getCellType()) {
		          	 	  
		          	       case Cell.CELL_TYPE_NUMERIC:
		          	    	   
		          	           vazaoTC [cell.getColumnIndex() - 63 ] = (int) cell.getNumericCellValue();
		          	           break;
		          	           
		          	       case Cell.CELL_TYPE_STRING:
		          	
		          	    	 vazaoTC [cell.getColumnIndex() - 63 ] = 0;
		          	           break;
		          	 	  }
		          	 	  outorga.setTempoCaptacao(vazaoTC);
		          	   }
		               
		              
		            } // fimsegundo while - c�lulas
		                  
			         try {
			        	 
						arquivo.close();
						
				         	} catch (IOException e) {
				         		e.printStackTrace(); 
				         		System.out.println("o aplicativo fechou errado");
				         		
							} // fim try
			         
			         
			         // para tirar as  linhas sem dados da tableview //
			         if (outorga.getInteressado() != null && !outorga.getInteressado().isEmpty()) {
			        	 obsListaOutorgas.add(outorga);
			         }
			       
			     } // fim primeiro while - linhas
		        
		return obsListaOutorgas;
		
	}
}
