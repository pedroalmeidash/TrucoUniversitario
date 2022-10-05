package jun.truco.main;


import java.util.Scanner;
import jun.truco.model.Baralho;
import jun.truco.model.CPU;
import jun.truco.model.CPUNormal;
import jun.truco.model.Carta;
import jun.truco.model.Humano;
import jun.truco.model.Jogador;
import jun.truco.model.Mesa;

public class Main {

	
	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("Digite seu nome: ");
		String nome = scanner.nextLine();
		
		System.out.print("Digite a quantindade de jogadores  > 4 < 8: ");
		int qtdJogadores = 0;
		do{
			while(!scanner.hasNextInt()) scanner.next();
			qtdJogadores = scanner.nextInt();
		}while(qtdJogadores < 4|| qtdJogadores > 8);
		
		Jogador j = new Humano(nome);
		
		Mesa m = new Mesa(j);
		for(int x = 1; x < qtdJogadores; x++){
			m.addJogador(new CPUNormal("Renato"+ x,m));
		}
		
		scanner.nextLine();
		do{
			 System.out.println("------------------------------------------");
		    System.out.println("Partida "+(m.getPartidas()+1));
		   
			Baralho b = new Baralho();
			
			System.out.println("Jogador "+m.getJogadorDaVez().getNome()+" vai embalharar!");
			m.getJogadorDaVez().embaralhar(b);
			
			System.out.println("Jogador "+m.getJogadorDaVez().getNome()+" vai dar as cartas!");
			m.DarAsCartas();
			
			b = new Baralho();
			m.getJogadorDaVez().embaralhar(b);
			m.vira(b.DarCarta());
		
			System.out.println("Jogador "+m.getJogadorDaVez().getNome()+" Virou "+m.getManilha() +" de manilha");
		    
			for(Jogador jPontos = m.nextJogadoresVida(); jPontos != null; jPontos = m.nextJogadoresVida()){
			if(m.getPartidas() == 0){
				if(jPontos instanceof Humano){
					Boolean validador = false;
					String resposta = "";						
					System.out.println("Deseja ir? ('sim' ou 'nao')");
					while(!validador){ //faz while ate que resposta informada seja uma das 2 seguintes "sim" ou "nao"
						while(!scanner.hasNextLine()) scanner.next(); //so ira fazer as demais validacoes caso seja um string
						resposta = scanner.nextLine();
						System.out.println(resposta);
						if((resposta.equals("sim") || resposta.equals("nao"))){
							validador = true;
							jPontos.setPontosPendente((resposta == "sim") ? 1 : 0); //caso faz entao pendente ira receber +1
						}else{
							System.out.println("Resposta invalida, Deseja ir? ('sim' ou 'nao')");
						}
					} 
				}
		    }else{
		    	if(jPontos.getNome().equals(nome)){
		    		System.out.print("Mao: ");
		    		for(Carta c : jPontos.getMao())
		    			System.out.print(c.toString()+", ");
		    		
		    		System.out.println("\nFaz quanto?");
					while(!scanner.hasNextInt()) scanner.next();
	    			int pendente = scanner.nextInt();
	    			scanner.nextLine();
	    			jPontos.setPontosPendente(pendente);
		    	  }	else{
		    		  	CPU cpu = (CPU)jPontos;
		    		  	cpu.escolherPontosPendentes(m.getNumerosCartas());
						System.out.println("jogador: "+jPontos.getNome()+ " Faz: "+jPontos.getPontosPendente());
		    		}
		    	}
		    		
		    	
			}
			m.ComecarPartida();
				while(m.hasRodada()){
					System.out.println("*********************************");
					System.out.println("Rodada: "+m.getRodada()+"/"+m.getRodadasPorPartidas());
					
					System.out.println("\nJogadores Pontos:");
					int pontosTotalMesa = 0, pontosTotalPrecisaMesa = 0;
					for(Jogador jo : m.getJogadores()){
						pontosTotalMesa += jo.getPontos();
						pontosTotalPrecisaMesa += jo.getPontosPendente();
						System.out.println(jo.getNome()+ " Pontos Feito: "+jo.getPontos()+ " Precisa fazer: "+ jo.getPontosPendente());
					}
					System.out.println("Total de pontos da mesa: "+pontosTotalMesa+" Total de pontos pendente da mesa: "+pontosTotalPrecisaMesa+"\n");
					
					while(m.getTurno().hasnext()){
					Jogador jogador = m.getTurno().next();
					
					if(jogador instanceof Humano){
						Humano hum = (Humano)jogador;
						System.out.print("Mao: ");
			    		for(Carta c : jogador.getMao())
			    			System.out.print(c.toString()+", ");
			    		
			    		System.out.println("\nqual carta jogar?");
						while(!scanner.hasNextInt()) scanner.next();
		    			int carta = scanner.nextInt();
		    			scanner.nextLine();
		    		    m.getForcaDasCartas().CartaJogada(hum, hum.jogar(carta));
					}else{
						  CPU cpu = (CPU)jogador;
						  Carta c = cpu.Jogar();
						  System.out.println("Jogador: " + jogador.getNome()+" jogou "+c.toString());
						  m.getForcaDasCartas().CartaJogada(cpu, c);
					}
					
					
					System.out.print("Mesa: ");
					for(int x = 0; x < m.getMesa().length && m.getMesa()[x] != null;x++)System.out.print(m.getMesa()[x].toString()+", ");
					System.out.println("");
				 }
					System.out.println("*********************************");
					if(m.getForcaDasCartas().getJogadorFez() == null)System.out.println("Empachado");
					else System.out.println("Jogador ganhou o turno: "+m.getForcaDasCartas().getJogadorFez().getNome());
					
					m.limpaMesa();
				}
				System.out.println("------------------------------------------");
				System.out.println("Jogadores:");
				for(Jogador jo : m.getJogadores()){
					String caiu = (jo.getVidas()<=0)?" Caiu": "";
					System.out.println(jo.getNome()+ " Vidas: "+jo.getVidas()+caiu);
					}
				
		}while(m.fimDoJogo());
		
		System.out.println("Jogador Venceu: "+m.getGanhador().getNome());
		scanner.close();
	}
	
}
