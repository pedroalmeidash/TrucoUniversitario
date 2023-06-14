package jun.truco.game;

import jun.truco.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {

    private final Scanner scanner = new Scanner(System.in);
    private final LerTeclado teclado = new LerTeclado();
    private CardDisplay cardDisplay;
    private Jogador mainPlayer;
    private List<Jogador> cpuPlayers;
    private Mesa mesa;

    public void start() {
        initPlayerConfigs();
        initMesa();

        do {
            mesa.prepareRound();
            setPlayersWinningRounds();
            mesa.startRound();
            playRound();
            displayRoundResults();
        } while (!mesa.hasGameFinished());

        displayGameResults();
        scanner.close();
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

    private void setPlayersWinningRounds() {
        int numberOfAlivePlayers = mesa.numberOfAlivePlayers();
        List<Jogador> roundPlayers = new ArrayList<>();

        for (int x = 0; x < numberOfAlivePlayers; x++) {
            roundPlayers.add(mesa.nextPlayer());
        }

        for (Jogador player : roundPlayers) {
            if (player instanceof Humano) {
                setMainPlayerWinningRounds(player);
            } else if (player instanceof CPU) {
                setCpuWinningRounds((CPU) player);
            }
        }
    }

    private void setMainPlayerWinningRounds(Jogador mainPlayer) {
        if (mesa.isFirstRound()) {
            teclado.primeira_rodada(mainPlayer, scanner);
        } else {
            cardDisplay.showHand(mainPlayer);
            teclado.fazquantos(mainPlayer, scanner);
        }
    }

    private void setCpuWinningRounds(CPU cpuPlayer) {
        if (mesa.isFirstRound()) {
            Carta card = cpuPlayer.listagem().get(0);
            System.out.println(cpuPlayer.nome + " tem a carta: " + card);
        } else {
            cpuPlayer.escolherPontosPendentes(mesa.getNumerosCartas());
            System.out.println("jogador: " + cpuPlayer.getNome() + " Faz: " + cpuPlayer.getPontosPendente());
        }
    }
    
    private void playRound() {
        while (mesa.hasRodada()) {
            displayPlayersRoundStats();
            while (mesa.getTurno().hasMoreTurns()) {
                playNextPlayerCard();
            }
            System.out.println("*********************************");
            if (mesa.getForcaDasCartas().getJogadorFez() == null) System.out.println("Empachado");
            else
                System.out.println("Jogador " + mesa.getForcaDasCartas().getJogadorFez().getNome() + " ganhou o turno");

            mesa.limpaMesa();
        }
    }

    private void displayPlayersRoundStats() {
        System.out.println("*********************************");
        System.out.println("Rodada: " + mesa.getRodada() + "/" + mesa.getRodadasPorPartidas());

        System.out.println();
        System.out.println("Jogadores Pontos:");
        for (Jogador player : mesa.getJogadores()) {
            System.out.println(player.getPointsMadeAndPendingPointsFormattedText());
        }
        System.out.println();
    }

    private void playNextPlayerCard() {
        Jogador jogador = mesa.getTurno().nextPlayerTurn();
        if (jogador instanceof Humano) {
            cardDisplay.showBoard(mesa);
            cardDisplay.showHand(jogador);
            teclado.chooseCardToPlay(mesa, scanner, jogador);
        } else {
            CPU cpu = (CPU) jogador;
            Carta c = cpu.Jogar();
            System.out.println("Jogador: " + jogador.getNome() + " jogou " + c.toString());
            mesa.getForcaDasCartas().playCard(cpu, c);
        }
    }

    private void displayRoundResults() {
        System.out.println("------------------------------------------");
        System.out.println("Jogadores:");
        for (Jogador player : mesa.getJogadores()) {
            String fell = (player.getVidas() <= 0) ? " Caiu" : "";
            System.out.println(player.getNome() + " Vidas: " + player.getVidas() + fell);
        }
    }

    private void displayGameResults() {
        System.out.println("Jogador Venceu: " + mesa.getGanhador().getNome());
        System.out.println("Deseja iniciar uma nova partida? (sim/nao)");
    }
}
