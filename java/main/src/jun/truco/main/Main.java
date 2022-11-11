package jun.truco.main;
import java.util.Scanner;
import jun.truco.model.Baralho;
import jun.truco.model.CPU;

import jun.truco.model.Carta;
import jun.truco.model.Humano;
import jun.truco.model.Jogador;
import jun.truco.model.Lerteclado;
import jun.truco.model.Mesa;
import jun.truco.model.Outrosjogadores;

public class Main {	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		Lerteclado teclado = new Lerteclado();
		
		System.out.print("Digite seu nome: ");
		String nome = scanner.nextLine();

		Jogador j = new Humano(nome);
		Mesa m = new Mesa(j);

		Outrosjogadores outros = new Outrosjogadores();
		outros.primeira_rodada(m, scanner);
				
		do{
			System.out.println("------------------------------------------");
		    System.out.println("Partida "+(m.getPartidas()+1));
		   
			Baralho b = new Baralho();
			
			//System.out.println("Jogador "+m.getJogadorDaVez().getNome()+" vai embalharar!");
			m.getJogadorDaVez().embaralhar(b);
			
			System.out.println("Jogador "+m.getJogadorDaVez().getNome()+" vai dar as cartas!");
			m.DarAsCartas();
			
			b = new Baralho();
			m.getJogadorDaVez().embaralhar(b);
			m.vira(b.DarCarta());
		
			System.out.println("Manilha: "+m.getManilha());
		    
			for(Jogador jogador = m.nextJogadoresVida(); jogador != null; jogador = m.nextJogadoresVida()){
			if(m.getPartidas() == 0){
				if(jogador instanceof Humano){
					teclado.primeira_rodada(jogador, scanner);
				}
		    }else{
		    	if(jogador.getNome().equals(nome)){
		    		System.out.print("Mao: ");
		    		
						for(Carta c : jogador.getMao())
		    			System.out.print(c.toString()+", ");
							
							teclado.fazquantos(jogador, scanner);

		    		}	else{
		    		  	CPU cpu = (CPU)jogador;
		    		  	cpu.escolherPontosPendentes(m.getNumerosCartas());
						System.out.println("jogador: "+jogador.getNome()+ " Faz: "+jogador.getPontosPendente());
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

						System.out.print("Mesa: ");
						for(int x = 0; x < m.getMesa().length && m.getMesa()[x] != null;x++)System.out.print(m.getMesa()[x].toString()+", ");
						System.out.println("");
										
						System.out.print("Mao: ");
			    		for(Carta c : jogador.getMao())
			    			System.out.print(c.toString()+", ");

							teclado.escolher_cartajogar(m, scanner, jogador);

						
					}else{
						  CPU cpu = (CPU)jogador;
						  Carta c = cpu.Jogar();
						  System.out.println("Jogador: " + jogador.getNome()+" jogou "+c.toString());
						  m.getForcaDasCartas().CartaJogada(cpu, c);
					}					
				 }
					System.out.println("*********************************");
					if(m.getForcaDasCartas().getJogadorFez() == null)System.out.println("Empachado");
					else System.out.println("Jogador " +m.getForcaDasCartas().getJogadorFez().getNome()+ " ganhou o turno");

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
