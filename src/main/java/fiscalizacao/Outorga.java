package fiscalizacao;

public class Outorga {

		private String interessado;
		private String tipoPoco;
		
		private String [] finalidade;
		private String [] subfinalidade;
		private int [] demanda;
		
		private String processo;
		private String cpfCNPJ;
		private String bacia;
		private String uh;
		private Double lat;
		private Double lng;
		private String endereco;
		
		
		private int [] vazaoHora;
		private int [] vazaoDia;
		private int [] tempoCaptacao;
		
		private String tipo;
		
		private String subsistema;
		private int profundidade;
		private int nivelEstatico;
		private int nivelDinamico;
		private int vazaoMedia;
		private int vazaoBombeamento;

		private int vazaoExplotavel;
		private int numeroDePocos;
		private int vazaoTotalOutorgavel;
		private int porcentagemUtilizada;
		private int volumeDisponivelAtual;
		private int volumeDiponivelSuficiente;
		
		
		
		// construtor padr�o
		public Outorga () { }
        
		
		// construtor completo
	    public Outorga (
	    		String interessado, String tipoPoco, String [] finalidade, String processo, 
	    		String cpfCNPJ, String bacia, String uh, Double lat, Double lng,
	    		int [] vazaoHora, int [] vazaoDia, int [] tempoCaptacao, String endereco, String tipo) {
	    	
	        super();
	         
	        this.interessado = interessado;
	 		this.tipoPoco = tipoPoco;
	 		this.finalidade = finalidade;
	 		this.processo = processo;	 		
	 		this.cpfCNPJ = cpfCNPJ;
	 		this.bacia = bacia;
	 		this.uh = uh;
	 		this.lat = lat;
	 		this.lng = lng;
	 		
	 		this.vazaoHora = vazaoHora;
	 		this.vazaoDia = vazaoDia;
	 		this.tempoCaptacao = tempoCaptacao;
	 		this.endereco = endereco;
	 		this.tipo = tipo; // tipo de outorga (outorga subterr�nea, outorga subterr�nea indeferimento, registro subterr�nea...)
	 		
	     }
	    
	    
		public String getEndereco() {
			return endereco;
		}


		public void setEndereco(String endereco) {
			this.endereco = endereco;
		}


		public String getInteressado() {
			return interessado;
		}

		public void setInteressado(String interessado) {
			this.interessado = interessado;
		}

		public String getTipoPoco() {
			return tipoPoco;
		}

		public void setTipoPoco(String tipoPoco) {
			this.tipoPoco = tipoPoco;
		}

		public String[] getFinalidade() {
			return finalidade;
		}

		public void setFinalidade(String[] finalidade) {
			this.finalidade = finalidade;
		}
		
		
		public String[] getSubfinalidade() {
			return subfinalidade;
		}


		public void setSubfinalidade(String[] subfinalidade) {
			this.subfinalidade = subfinalidade;
		}


		public int[] getDemanda() {
			return demanda;
		}


		public void setDemanda(int [] demanda) {
			this.demanda = demanda;
		}


		public String getProcesso() {
			return processo;
		}

		public void setProcesso(String processo) {
			this.processo = processo;
		}

		public String getCpfCNPJ() {
			return cpfCNPJ;
		}

		public void setCpfCNPJ(String cpfCNPJ) {
			this.cpfCNPJ = cpfCNPJ;
		}

		public String getBacia() {
			return bacia;
		}

		public void setBacia(String bacia) {
			this.bacia = bacia;
		}

		public String getUh() {
			return uh;
		}

		public void setUh(String uh) {
			this.uh = uh;
		}

		public Double getLat() {
			return lat;
		}

		public void setLat(Double lat) {
			this.lat = lat;
		}

		public Double getLng() {
			return lng;
		}

		public void setLng(Double lng) {
			this.lng = lng;
		}

		public int[] getVazaoHora() {
			return vazaoHora;
		}

		public void setVazaoHora(int[] vazaoHora) {
			this.vazaoHora = vazaoHora;
		}

		public int[] getVazaoDia() {
			return vazaoDia;
		}

		public void setVazaoDia(int[] vazaoDia) {
			this.vazaoDia = vazaoDia;
		}

		public int[] getTempoCaptacao() {
			return tempoCaptacao;
		}

		public void setTempoCaptacao(int[] tempoCaptacao) {
			this.tempoCaptacao = tempoCaptacao;
		}
		
		public String getTipo() {
			return tipo;
		}


		public void setTipo(String tipo) {
			this.tipo = tipo;
		}


		public String getSubsistema() {
			return subsistema;
		}


		public void setSubsistema(String subsistema) {
			this.subsistema = subsistema;
		}


		public int getProfundidade() {
			return profundidade;
		}


		public void setProfundidade(int profundidade) {
			this.profundidade = profundidade;
		}


		public int getNivelEstatico() {
			return nivelEstatico;
		}


		public void setNivelEstatico(int nivelEstatico) {
			this.nivelEstatico = nivelEstatico;
		}


		public int getNivelDinamico() {
			return nivelDinamico;
		}


		public void setNivelDinamico(int nivelDinamico) {
			this.nivelDinamico = nivelDinamico;
		}


		public int getVazaoMedia() {
			return vazaoMedia;
		}


		public void setVazaoMedia(int vazaoMedia) {
			this.vazaoMedia = vazaoMedia;
		}


		public int getVazaoBombeamento() {
			return vazaoBombeamento;
		}


		public void setVazaoBombeamento(int vazaoBombeamento) {
			this.vazaoBombeamento = vazaoBombeamento;
		}


		


		public int getVazaoExplotavel() {
			return vazaoExplotavel;
		}


		public void setVazaoExplotavel(int vazaoExplotavel) {
			this.vazaoExplotavel = vazaoExplotavel;
		}


		public int getNumeroDePocos() {
			return numeroDePocos;
		}


		public void setNumeroDePocos(int numeroDePocos) {
			this.numeroDePocos = numeroDePocos;
		}


		public int getVazaoTotalOutorgavel() {
			return vazaoTotalOutorgavel;
		}


		public void setVazaoTotalOutorgavel(int vazaoTotalOutorgavel) {
			this.vazaoTotalOutorgavel = vazaoTotalOutorgavel;
		}


		public int getPorcentagemUtilizada() {
			return porcentagemUtilizada;
		}


		public void setPorcentagemUtilizada(int porcentagemUtilizada) {
			this.porcentagemUtilizada = porcentagemUtilizada;
		}


		public int getVolumeDisponivelAtual() {
			return volumeDisponivelAtual;
		}


		public void setVolumeDisponivelAtual(int volumeDisponivelAtual) {
			this.volumeDisponivelAtual = volumeDisponivelAtual;
		}


		public int getVolumeDiponivelSuficiente() {
			return volumeDiponivelSuficiente;
		}


		public void setVolumeDiponivelSuficiente(int volumeDiponivelSuficiente) {
			this.volumeDiponivelSuficiente = volumeDiponivelSuficiente;
		}



	
	    
	    
}
