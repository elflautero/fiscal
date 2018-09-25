package fiscalizacao;

public class Outorga {

		private String interessado;
		private String tipoPoco;
		
		private String [] finalidade;
		private String [] subfinalidade;
		private Double [] demanda;
		private Double [] demandaIN;
		
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
		
		private String tipoOutorga; // outorga, registro, modificação etc
		
		private String subsistema;
		private Double profundidade;
		private Double nivelEstatico;
		private Double nivelDinamico;
		private Double vazaoMedia;
		private Double vazaoBombeamento;

		private Double vazaoExplotavel;
		private int numeroDePocos;
		private Double vazaoTotalOutorgavel;
		private Double porcentagemUtilizada;
		private Double volumeDisponivelAtual;
		private String volumeDiponivelSuficiente;
		
		
		
		// construtor padr�o
		public Outorga () { }
        
		/*
		// construtor completo
	    public Outorga (
	    		String interessado, String tipoPoco, String [] finalidade, String processo, 
	    		String cpfCNPJ, String bacia, String uh, Double lat, Double lng,
	    		int [] vazaoHora, int [] vazaoDia, int [] tempoCaptacao, String endereco, String tipoOutorga) {
	    	
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
	 		this.tipoOutorga = tipoOutorga; // tipo de outorga (outorga subterr�nea, outorga subterr�nea indeferimento, registro subterr�nea...)
	 		
	     }
	    */
	    
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
		
		public String getTipoOutorga() {
			return tipoOutorga;
		}


		public void setTipoOutorga(String tipoOutorga) {
			this.tipoOutorga = tipoOutorga;
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


		public Double[] getDemanda() {
			return demanda;
		}


		public void setDemanda(Double[] demanda) {
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
		
		public String getSubsistema() {
			return subsistema;
		}


		public void setSubsistema(String subsistema) {
			this.subsistema = subsistema;
		}


		public Double getProfundidade() {
			return profundidade;
		}


		public void setProfundidade(Double profundidade) {
			this.profundidade = profundidade;
		}


		public Double getNivelEstatico() {
			return nivelEstatico;
		}


		public void setNivelEstatico(Double nivelEstatico) {
			this.nivelEstatico = nivelEstatico;
		}


		public Double getNivelDinamico() {
			return nivelDinamico;
		}


		public void setNivelDinamico(Double nivelDinamico) {
			this.nivelDinamico = nivelDinamico;
		}


		public Double getVazaoMedia() {
			return vazaoMedia;
		}


		public void setVazaoMedia(Double vazaoMedia) {
			this.vazaoMedia = vazaoMedia;
		}


		public Double getVazaoBombeamento() {
			return vazaoBombeamento;
		}


		public void setVazaoBombeamento(Double vazaoBombeamento) {
			this.vazaoBombeamento = vazaoBombeamento;
		}


		


		public Double getVazaoExplotavel() {
			return vazaoExplotavel;
		}


		public void setVazaoExplotavel(Double vazaoExplotavel) {
			this.vazaoExplotavel = vazaoExplotavel;
		}


		public int getNumeroDePocos() {
			return numeroDePocos;
		}


		public void setNumeroDePocos(int numeroDePocos) {
			this.numeroDePocos = numeroDePocos;
		}


		public Double getVazaoTotalOutorgavel() {
			return vazaoTotalOutorgavel;
		}


		public void setVazaoTotalOutorgavel(Double vazaoTotalOutorgavel) {
			this.vazaoTotalOutorgavel = vazaoTotalOutorgavel;
		}


		public Double getPorcentagemUtilizada() {
			return porcentagemUtilizada;
		}


		public void setPorcentagemUtilizada(Double porcentagemUtilizada) {
			this.porcentagemUtilizada = porcentagemUtilizada;
		}


		public Double getVolumeDisponivelAtual() {
			return volumeDisponivelAtual;
		}


		public void setVolumeDisponivelAtual(Double volumeDisponivelAtual) {
			this.volumeDisponivelAtual = volumeDisponivelAtual;
		}


		public String getVolumeDiponivelSuficiente() {
			return volumeDiponivelSuficiente;
		}


		public void setVolumeDiponivelSuficiente(String volumeDiponivelSuficiente) {
			this.volumeDiponivelSuficiente = volumeDiponivelSuficiente;
		}


		public Double[] getDemandaIN() {
			return demandaIN;
		}


		public void setDemandaIN(Double[] demandaIN) {
			this.demandaIN = demandaIN;
		}

	
	    
	    
}
