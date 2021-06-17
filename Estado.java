
public class Estado {

	public String nomeEstado;		//Nome do estado
	public String le;				//O que esta lendo na fita
	public String escreve;			//O que escreve na fita
	public String movimento;		//e = Esquerda | d = Direita | i = Fica_Parado
	public String proximo;			//Qual o proximo estado
	public boolean chamadaDeFuncao;	//Se eh uma chamada de funcao
	public String nomeDaFuncao;		//Caso seja uma chamada de funcao, o nome dela
	boolean breakpoint;				// caso a linha tenha um breakpoint
	
	
	
	public Estado(){
		
	}
	
	public String getNomeEstado() {
		return nomeEstado;
	}
	public void setNomeEstado(String nomeEstado) {
		this.nomeEstado = nomeEstado;
	}
	public String getLe() {
		return le;
	}
	public void setLe(String le) {
		this.le = le;
	}
	public String getEscreve() {
		return escreve;
	}
	public void setEscreve(String escreve) {
		this.escreve = escreve;
	}
	public String getMovimento() {
		return movimento;
	}
	public void setMovimento(String movimento) {
		this.movimento = movimento;
	}
	public String getProximo() {
		return proximo;
	}
	public void setProximo(String proximo) {
		this.proximo = proximo;
	}
	public boolean isChamadaDeFuncao() {
		return chamadaDeFuncao;
	}
	public void setChamadaDeFuncao(boolean chamadaDeFuncao) {
		this.chamadaDeFuncao = chamadaDeFuncao;
	}
	public String getNomeDaFuncao() {
		return nomeDaFuncao;
	}
	public void setNomeDaFuncao(String nomeDaFuncao) {
		this.nomeDaFuncao = nomeDaFuncao;
	}
	
	public boolean isBreakpoint() {
		return breakpoint;
	}

	public void setBreakpoint(boolean breakpoint) {
		this.breakpoint = breakpoint;
	}

	@Override
	public String toString() {
		return "Estado [nomeEstado=" + nomeEstado + ", le=" + le + ", escreve=" + escreve + ", movimento=" + movimento
				+ ", proximo=" + proximo + ", chamadaDeFuncao=" + chamadaDeFuncao + ", nomeDaFuncao=" + nomeDaFuncao
				+ ", breakpoint=" + breakpoint + "]";
	}


	
	
	
	
	
	
}
