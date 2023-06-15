package jun.truco.game;

import jun.truco.game.carddisplay.CardDisplay;
import jun.truco.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {

    private final Scanner scanner = new Scanner(System.in);
    private final PlayerActionReader playerActionReader = new PlayerActionReader(scanner);
    private CardDisplay cardDisplay;
    private Mesa mesa;

    public GameResult start() {
        configGame();
        GameResult gameResult;
        try {
            do {
                mesa.prepareRound();
                setPlayersWinningRounds();
                mesa.startRound();
                playRound();
                displayRoundResults();
            } while (!mesa.hasGameFinished());
            gameResult = GameResult.FINISHED;
            displayGameResults();
        } catch (RetreatException re) {
            gameResult = GameResult.RETREAT;
        }
        return gameResult;
    }

    private void configGame() {
        GameConfigurator gameConfigurator = new GameConfigurator(scanner);
        gameConfigurator.configureGame();
        GameConfiguration configuration = gameConfigurator.getGameConfiguration();
        cardDisplay = configuration.getCardDisplay();
        mesa = configuration.getMesa();
    }

    private void setPlayersWinningRounds() {
        int numberOfAlivePlayers = mesa.numberOfAlivePlayers();
        List<Jogador> roundPlayers = new ArrayList<>();

        for (int x = 0; x < numberOfAlivePlayers; x++) {
            roundPlayers.add(mesa.nextPlayer());
        }

        for (Jogador player : roundPlayers) {
            if (player instanceof Humano) {
                setMainPlayerWinningRounds((Humano) player);
            } else if (player instanceof CPU) {
                setCpuWinningRounds((CPU) player);
            }
        }
    }

    private void setMainPlayerWinningRounds(Humano mainPlayer) {
        cardDisplay.showHand(mainPlayer);
        playerActionReader.readPlayerWinningTurnsForRound(mainPlayer);
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

    private void playRound() throws RetreatException {
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

    private void playNextPlayerCard() throws RetreatException {
        Jogador jogador = mesa.getTurno().nextPlayerTurn();
        if (jogador instanceof Humano) {
            cardDisplay.showBoard(mesa);
            cardDisplay.showHand(jogador);
            CardPlayDecision decision = playerActionReader.readPlayerCardToPlay(mesa, (Humano) jogador);
            if (decision == CardPlayDecision.RETREAT) throw new RetreatException();
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

    private static class RetreatException extends Exception {
    }

    public enum GameResult {
        FINISHED, RETREAT;
    }
}