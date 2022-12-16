package jun.truco.main;
import java.util.List;
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
		outros.GeraOutrosJogadores(m, scanner);

		do{
			m.prints_nova_rodada(m);

			int qtdJogadores = m.qtd_jogador();
			for(int x = 0; x < qtdJogadores; x++){
				Jogador jogador = m.nextJogadoresVida();
				if(m.getPartidas() == 0){
						//printar todas as cartas dos outros e a sua
						List<Carta> carta_jogador = jogador.listagem();
						Carta carta = carta_jogador.get(0);
						if(jogador instanceof Humano){
							teclado.primeira_rodada(jogador, scanner);
						}else{
							System.out.println(jogador.nome+" tem a carta: "+carta);
						}
					}else{
						if(jogador.getNome().equals(nome)){
							m.MostrarMao(jogador);					
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
					//int pontosTotalMesa = 0, pontosTotalPrecisaMesa = 0;
					for(Jogador jo : m.getJogadores()){
						//pontosTotalMesa += jo.getPontos();
						//pontosTotalPrecisaMesa += jo.getPontosPendente();
						System.out.println(jo.getNome()+ " Pontos Feito: "+jo.getPontos()+ " Precisa fazer: "+ jo.getPontosPendente());
					}
					//System.out.println("Total de pontos da mesa: "+pontosTotalMesa+" Total de pontos pendente da mesa: "+pontosTotalPrecisaMesa+"\n");
					
					while(m.getTurno().hasnext()){
					Jogador jogador = m.getTurno().next();

					if(jogador instanceof Humano){
						m.MostraMesa();
						m.MostrarMao(jogador);
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
