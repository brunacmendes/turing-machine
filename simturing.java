import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;


public class simturing {
	
	public String bloco; //identificador do bloco em execucao
	public String id_estado; //identificador do estado atual
	public Estado estado; //estado atual
	public String fita_esquerda; //os 10 caracteres (ou espacos) mais proximos do cabecote a esquerda
	public String cabecote; //3 caracteres. delimitador da esquerda, simbolo na fita, delimitador da esquerda
	public String fita_direita; //os 10 caracteres (ou espacos) mais proximos do cabecote a direita
	
	public boolean acabou = false;
	
	public Pilha<String> pilha; //para empilhar o bloco e o proximo estado 
	private Scanner ler;
	
	private void imprimirSaida (String palavra, char cabecote_inicio, char cabecote_fim, Bloco bloco_executando, int i){
		cabecote = new StringBuilder().append(cabecote_inicio).append(palavra.charAt(i)).append(cabecote_fim)
				.toString();
		fita_esquerda = palavra.substring(0, i);
		fita_direita = palavra.substring(i + 1);
		String normaliza_id_estado = "0000";
		normaliza_id_estado = normaliza_id_estado.substring(0, normaliza_id_estado.length()-id_estado.length())+id_estado;
		String saida = bloco_executando.nomeBloco + "." + normaliza_id_estado + ": " + fita_esquerda + cabecote + fita_direita;
		String frase = preecheCom(".",50);
		frase = frase.substring(0, frase.length()-saida.length())+saida;
		System.out.println(frase);
	}
	
	public void executar (String palavra, int step, char cabecote_inicio, char cabecote_fim, boolean resume){
		
		pilha = new Pilha<String>();
		bloco = "main";
		Bloco bloco_executando = LeArquivo.blocos.get(bloco);
		id_estado = bloco_executando.getInicial();
		int step_inicial = step;
		
		int i = 10; // posicao do cabecote na palavra, 10 marca o inicio da
					// palavra
		
		while ((acabou == false) && (step!=0)){
			
			if (id_estado.equals("pare")){
				acabou = true;
				step = 0;
				imprimirSaida(palavra, cabecote_inicio, cabecote_fim, bloco_executando, i);
			}
			else {
				
				if (resume == false){
				
					imprimirSaida(palavra, cabecote_inicio, cabecote_fim, bloco_executando, i);
					step--;
				}
				//identificar qual estado esa atualmente
				estado = new Estado();
				boolean achou = false;
				Set<Estado> estados_do_bloco = bloco_executando.listaDeEstado;
				Iterator<Estado> estadosAsIterator = estados_do_bloco.iterator();
				while (estadosAsIterator.hasNext()) {
					Estado it = estadosAsIterator.next();
					if (it.getLe()!=""){
						if ((Integer.parseInt(it.getNomeEstado()) == Integer.parseInt(id_estado))
								&& (it.getLe().charAt(0) == palavra.charAt(i))) {
							achou = true;
							estado = it;
							break;
						}
					}
				}
				if (achou == false){
					estados_do_bloco = bloco_executando.listaDeEstado;
					estadosAsIterator = estados_do_bloco.iterator();
					while (estadosAsIterator.hasNext()) {
						Estado it = estadosAsIterator.next();
						if ((Integer.parseInt(it.getNomeEstado()) == Integer.parseInt(id_estado))
								&& (it.isChamadaDeFuncao())) {
							achou = true;
							estado = it;
							break;
						}
					}
				}
				if (achou == false){
					estados_do_bloco = bloco_executando.listaDeEstado;
					estadosAsIterator = estados_do_bloco.iterator();
					while (estadosAsIterator.hasNext()) {
						Estado it = estadosAsIterator.next();
						if ((Integer.parseInt(it.getNomeEstado()) == Integer.parseInt(id_estado))
							&& (it.getLe().equals("*"))) {
							achou = true;
							estado = it;
							break;
						}
					}
				
				}
				
				if (!estado.isChamadaDeFuncao()) {

						if (!estado.getEscreve().equals("*"))
						{
							palavra = palavra.substring(0, i)+estado.getEscreve()+palavra.substring(i+1);
						}
											
						if (estado.getMovimento().equals("d")) {
							i++;
							cabecote = new StringBuilder().append(cabecote_inicio).append(palavra.charAt(i))
									.append(cabecote_fim).toString();
							fita_esquerda = palavra.substring(0, i);
							fita_direita = palavra.substring(i + 1);
							
							
						} else if (estado.getMovimento().equals("e")) {
							i--;
							cabecote = new StringBuilder().append(cabecote_inicio).append(palavra.charAt(i))
									.append(cabecote_fim).toString();
							fita_esquerda = palavra.substring(0, i);
							fita_direita = palavra.substring(i + 1);
							
							
						}

						id_estado = estado.getProximo(); // estado  recebe o proximo

						if (id_estado.equals("retorne")) { //desempilhar
							String volta = pilha.pop();
							bloco_executando = LeArquivo.blocos.get(volta); 
							pilha.remove();
							id_estado = pilha.pop(); // estado atual recebe proximo estado	
							pilha.remove();
						}
						
						if (estado.breakpoint)
						{
							//imprimirSaida(palavra, cabecote_inicio, cabecote_fim, bloco_executando, i);
							System.out.printf("Programa pausado! \n");
							step = 0;
						}

				} else { // se for chamada de funcao, coloca na pilha e muda de bloco
					  pilha.insere(estado.getProximo());
					  pilha.insere(bloco_executando.getNomeBloco());
					  
					  bloco_executando =  LeArquivo.blocos.get(estado.nomeDaFuncao);
					  id_estado = bloco_executando.getInicial();
				}
				
				if (step == 0){
					
					String opcao;
				 	System.out.printf("Escolha opcao (-r ,-v,-s ou -s <n>): \n");
							
					ler = new Scanner(System.in);
					opcao = ler.nextLine();
					String[] vetor = opcao.split(" ");
					
					if (vetor[0].equals("-r")){
						step = -1;
						resume = true;
					}
					else if (vetor[0].equals("-v")){
						step = -1;
					}
					else if ((opcao.equals("")) || (opcao.equals("-s"))) {
						step = step_inicial;
					}
					else {
						try{
							if ((vetor[0].equals("-s")) && (vetor[1] != null)) {
								step = Integer.parseInt(vetor[1]);
								step_inicial = Integer.parseInt(vetor[1]);
							}
						}	
						catch (Exception e){
							System.out.printf("Opcao invalida! \n");
						}
					}
					
				}
				
			}

		}
		
		
	}

	public static void main(String[] args) {
		String nomeDoArquivo = "";
		String palavra = "";
		String opcao = "";
		int step = -1;
		String cabecote = "";
		char cabecote_inicio = '('; //se o cabecote nao for informado, usa esse como default
		char cabecote_fim = ')';
		
		if (args.length > 0 ) {
     
			if (args[0].equals("-head")){
			cabecote  = args[1];
			opcao = args[2];
				if (opcao.equals("-s")|| opcao.equals("-step")){
					step = Integer.parseInt(args[3]);
					nomeDoArquivo = args[4];
				}
				else {
					nomeDoArquivo = args[3];
				}
			
			cabecote_inicio = cabecote.charAt(0);
			cabecote_fim = cabecote.charAt(1);
			}
			else {
				opcao = args[0];
				if (opcao.equals("-s") || opcao.equals("-step")){
					step = Integer.parseInt(args[1]);
					nomeDoArquivo = args[2];
				}
				else {
					nomeDoArquivo = args[1];
				}
				
			}
		}
		else {
			System.out.printf("Informe os parametros!\n");
		}
		
		
		LeArquivo.carregaArquivo(nomeDoArquivo);
		
	
		simturing principal = new simturing();
		LeArquivo.carregaArquivo(nomeDoArquivo);
		
		System.out.printf("\n Simulador de Maquina de Turing ver 1.0 \n"
				+ "Desenvolvido como trabalho pratico para a disciplina de Teoria da Computacao \n"
				+ "autores Bruna Cristina e Luis Fernando , IFMG, 2018.\n\n");
		
		System.out.printf("Forneca a palavra inicial: ");
		
		Scanner ler = new Scanner(System.in);
		palavra = ler.nextLine();
		
		String word = "__________" + palavra + "__________";
		
		try{
			if (opcao.equals("-r") || opcao.equals("-resume")){
				principal.executar(word, -1, cabecote_inicio, cabecote_fim, true);
			}
			else if (opcao.equals("-v") || opcao.equals("-verbose")){
				principal.executar(word, -1, cabecote_inicio, cabecote_fim, false);
			}
			else if (opcao.equals("-s") || opcao.equals("-step")){
				principal.executar(word,step, cabecote_inicio, cabecote_fim, false);
			}
		}catch (Exception e){
			System.out.printf("Palavra Invalida!\n");
		}
		
		//try{
			//principal.executar("__________aba__________", 12, cabecote_inicio, cabecote_fim, false);
		//}
		//catch (Exception e){
			//System.out.printf("Palavra Invalida!\n");
		//}
		
	}
	
	private String preecheCom(String caracter, int quantidade){
		String frase = "";
		for (int i = 0; i < quantidade; i++) {
			frase = frase + caracter;
		}
		return frase;
	}
	
}
