import java.util.HashSet;
import java.util.Set;

public class Bloco {

	public String nomeBloco;
	public Set<Estado> listaDeEstado;
	public String inicial;
	
	public Bloco(String nomeBloco, String inicial) {
		this.nomeBloco = nomeBloco; //identificador do bloco
		this.listaDeEstado = new HashSet<Estado>();
		this.inicial = inicial; //estado inicial do bloco
	}

	public String getNomeBloco() {
		return nomeBloco;
	}

	public void setNomeBloco(String nomeBloco) {
		this.nomeBloco = nomeBloco;
	}

	public HashSet<Estado> getListaDeEstado() {
		return (HashSet<Estado>) listaDeEstado;
	}

	public void setListaDeEstado(HashSet<Estado> listaDeEstado) {
		this.listaDeEstado = listaDeEstado;
	}

	public String getInicial() {
		return inicial;
	}


	public void setInicial(String inicial) {
		this.inicial = inicial;
	}
	
	@Override
	public String toString() {
		return "Bloco [nomeBloco=" + nomeBloco + ", listaDeEstado=" + listaDeEstado + ", inicial=" + inicial + "]";
	}
	
	
}
