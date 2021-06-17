import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class LeArquivo {
	
	public static HashMap<String, Bloco> blocos = new HashMap<String, Bloco>();
	private static Bloco blocoTemp;
	private static Estado estadoTemp;
	
	public static void carregaArquivo(String nomeDoArquivo) {
		try {
			String path = System.getProperty("user.dir");
			File arquivo1 = new File(path + "/" +nomeDoArquivo);
			FileReader arq = new FileReader(arquivo1);
			BufferedReader arquivo = new BufferedReader(arq);
			try {
				String nomeBloco = "";
				String iniciaBloco = "";
				
				boolean lendondoBloco = false;
				while (arquivo.ready()) {
					String linha = arquivo.readLine().trim();
					String[] vetor = linha.split(" ");
					//Caso a linha seja um comentario ou uma linha em branco nao faz nada
					if (!vetor[0].startsWith(";")) {
						//INICIO====Leitura de um BLOCO de funcao
						if((vetor[0].startsWith("bloco")) && (!lendondoBloco)){
							nomeBloco = vetor[1];
							iniciaBloco = vetor[2];
							blocoTemp = new Bloco(nomeBloco, iniciaBloco);
							lendondoBloco = true;							
						}
						else if(lendondoBloco){
							if(vetor[0].startsWith("fim")){
								salvaBloco(nomeBloco, blocoTemp);
								lendondoBloco = false;
							}
							else{
								salvaEstado(vetor);
							}
						}
						//FIM======Leitura de um BLOCO de funcao
					}
				}
				arquivo.close();
			} catch (IOException e) {
				//e.printStackTrace();
				System.out.println("O arquivo de entrada nao esta no padrao esperado!");
			}

		} catch (FileNotFoundException e) {
			System.out.println("Arquivo nao encontrado!");
			// e.printStackTrace();
		}
	}
	
	/*
	 * SALVANDO ESTADOS TEMPORARIAMENTE
	 */
	private static void salvaEstado(String[] vetor){
		boolean breakpoint = false;
		if(vetor.length > 3){
			if(vetor[3].startsWith("!")){
				breakpoint = true;
			}
			if(vetor.length >= 7){
				if(vetor[6].startsWith("!")){
					breakpoint = true;
				}
			}
			//Checagem de chamada de funcao com um comentario na frente
			//Caso seja uma chamada de funcao e tenha o comentario entra no ELSE
			if(!vetor[3].startsWith(";")){
				novoEstado(vetor, false, breakpoint);
			}
			else{
				novoEstado(vetor, true, breakpoint);
			}
		}
		else if(vetor.length == 3){
			novoEstado(vetor, true, breakpoint);
		}
	}
	
	/*
	 * NOVO ESTADO
	 */
	private static void novoEstado(String vetor[], boolean chamadaDeFuncao, boolean breakpoint){
		estadoTemp = new Estado();
		if(!chamadaDeFuncao){
			estadoTemp.nomeEstado = vetor[0];
			estadoTemp.le = vetor[1];
			estadoTemp.escreve = vetor[3];
			estadoTemp.movimento = vetor[4];
			estadoTemp.proximo = vetor[5];
			estadoTemp.chamadaDeFuncao = false;
			estadoTemp.nomeDaFuncao = "";
			estadoTemp.breakpoint = breakpoint;
			blocoTemp.listaDeEstado.add(estadoTemp);
		}
		else{
			estadoTemp.nomeEstado = vetor[0];
			estadoTemp.le = "";
			estadoTemp.escreve = "";
			estadoTemp.movimento = "";
			estadoTemp.proximo = vetor[2];
			estadoTemp.chamadaDeFuncao = true;
			estadoTemp.nomeDaFuncao = vetor[1];
			estadoTemp.breakpoint = breakpoint;
			blocoTemp.listaDeEstado.add(estadoTemp);
		}
	}

	/*
	 * SALVANDO BLOCOS
	 */
	private static void salvaBloco(String nomeBloco, Bloco blocoTemp){
		blocos.put(nomeBloco, blocoTemp);
		//System.out.println(blocoTemp.toString());
		blocoTemp = null;
	}

}