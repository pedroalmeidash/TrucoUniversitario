package jun.truco.game;

import jun.truco.model.*;

import java.util.List;
import java.util.Scanner;

public class Game {

    private final Scanner scanner = new Scanner(System.in);
    private CardDisplay cardDisplay;
    private Jogador mainPlayer;
    private List<Jogador> cpuPlayers;
    private Mesa mesa;

    public void start() {
        initPlayerConfigs();
        initMesa();

        LerTeclado teclado = new LerTeclado();

        do {
            mesa.startRound();

            int qtdJogadores = mesa.qtd_jogador();
            for (int x = 0; x < qtdJogadores; x++) {
                Jogador jogador = mesa.nextJogadoresVida();
                if (mesa.getPartidas() == 0) {
                    //printar todas as cartas dos outros e a sua
                    List<Carta> carta_jogador = jogador.listagem();
                    Carta carta = carta_jogador.get(0);
                    if (jogador instanceof Humano) {
                        teclado.primeira_rodada(jogador, scanner);
                    } else {
                        System.out.println(jogador.nome + " tem a carta: " + carta);
                    }
                } else {
                    if (jogador instanceof Humano) {
                        cardDisplay.showHand(jogador);
                        teclado.fazquantos(jogador, scanner);
                    } else {
                        CPU cpu = (CPU) jogador;
                        cpu.escolherPontosPendentes(mesa.getNumerosCartas());
                        System.out.println("jogador: " + jogador.getNome() + " Faz: " + jogador.getPontosPendente());
                    }
                }
            }

            mesa.ComecarPartida();
            while (mesa.hasRodada()) {
                System.out.println("*********************************");
                System.out.println("Rodada: " + mesa.getRodada() + "/" + mesa.getRodadasPorPartidas());

                System.out.println("\nJogadores Pontos:");
                //int pontosTotalMesa = 0, pontosTotalPrecisaMesa = 0;
                for (Jogador jo : mesa.getJogadores()) {
                    //pontosTotalMesa += jo.getPontos();
                    //pontosTotalPrecisaMesa += jo.getPontosPendente();
                    System.out.println(jo.getNome() + " Pontos Feitos: " + jo.getPontos() + " Precisa fazer: " + jo.getPontosPendente());
                }
                System.out.println();
                //System.out.println("Total de pontos da mesa: "+pontosTotalMesa+" Total de pontos pendente da mesa: "+pontosTotalPrecisaMesa+"\n");

                while (mesa.getTurno().hasnext()) {
                    Jogador jogador = mesa.getTurno().next();

                    if (jogador instanceof Humano) {
                        cardDisplay.showBoard(mesa);
                        cardDisplay.showHand(jogador);
                        teclado.escolher_cartajogar(mesa, scanner, jogador);
                    } else {
                        CPU cpu = (CPU) jogador;
                        Carta c = cpu.Jogar();
                        System.out.println("Jogador: " + jogador.getNome() + " jogou " + c.toString());
                        mesa.getForcaDasCartas().CartaJogada(cpu, c);
                    }
                }
                System.out.println("*********************************");
                if (mesa.getForcaDasCartas().getJogadorFez() == null) System.out.println("Empachado");
                else
                    System.out.println("Jogador " + mesa.getForcaDasCartas().getJogadorFez().getNome() + " ganhou o turno");

                mesa.limpaMesa();
            }
            System.out.println("------------------------------------------");
            System.out.println("Jogadores:");
            for (Jogador jo : mesa.getJogadores()) {
                String caiu = (jo.getVidas() <= 0) ? " Caiu" : "";
                System.out.println(jo.getNome() + " Vidas: " + jo.getVidas() + caiu);
            }
        } while (mesa.fimDoJogo());

        System.out.println("Jogador Venceu: " + mesa.getGanhador().getNome());
        System.out.println("Deseja iniciar uma nova partida? (sim/nao)");
        if (scanner.next().equalsIgnoreCase("sim")) reiniciar();
        else scanner.close();
    }

    private void initPlayerConfigs() {
        System.out.print("Digite seu nome: ");
        String playerName = scanner.nextLine();
        mainPlayer = new Humano(playerName);

        System.out.print("Deseja ver as cartas em ASCII? (sim/nao) ");
        Boolean showASCIIBoard = scanner.next().equalsIgnoreCase("sim");
        cardDisplay = CardDisplayFactory.create(showASCIIBoard);

        initCpuPlayers();
    }

    private void initCpuPlayers() {
        System.out.print("Digite a quantindade de jogadores de 4 a 8: ");

        int numberOfCpuPlayers;
        do {
            while (!scanner.hasNextInt()) scanner.next();
            numberOfCpuPlayers = scanner.nextInt();
        } while (numberOfCpuPlayers < 4 || numberOfCpuPlayers > 8);

        PlayerGenerator playerGenerator = new PlayerGenerator();
        cpuPlayers = playerGenerator.generatePlayers(numberOfCpuPlayers - 1);
        scanner.nextLine();
    }

    private void initMesa() {
        mesa = new Mesa(mainPlayer);
        cpuPlayers.forEach(player -> {
            if (player instanceof CPU) ((CPU) player).setMesa(mesa);
            mesa.addJogador(player);
        });
    }

    // TODO mover para a Main
    private void reiniciar() {
        // Chama a função main() novamente para reiniciar o jogo
        System.out.println(System.lineSeparator().repeat(100));
        start();
    }
}
