package jun.truco.game;

import jun.truco.game.carddisplay.CardDisplay;
import jun.truco.game.carddisplay.CardDisplayFactory;
import jun.truco.model.CPU;
import jun.truco.model.Humano;
import jun.truco.model.Jogador;
import jun.truco.model.Mesa;

import java.util.List;
import java.util.Scanner;

public class GameConfigurator {

    private final Scanner scanner;
    private GameConfiguration gameConfiguration;

    public GameConfigurator(Scanner scanner) {
        this.scanner = scanner;
    }

    public GameConfiguration getGameConfiguration() {
        return gameConfiguration;
    }

    public void configureGame() {
        Jogador mainPlayer = configPlayer();
        List<Jogador> cpuPlayers = configCpuPlayers();
        gameConfiguration = new GameConfiguration(configCardDisplay(), configMesa(mainPlayer, cpuPlayers));
        scanner.nextLine();
    }

    private Jogador configPlayer() {
        System.out.print("Digite seu nome: ");
        String playerName = scanner.nextLine();
        return new Humano(playerName);
    }

    private CardDisplay configCardDisplay() {
        System.out.print("Deseja ver as cartas em ASCII? (sim/nao) ");
        Boolean showASCIIBoard = scanner.next().equalsIgnoreCase("sim");
        return CardDisplayFactory.create(showASCIIBoard);
    }

    private List<Jogador> configCpuPlayers() {
        System.out.print("Digite a quantindade de jogadores de 4 a 8: ");

        int numberOfCpuPlayers;
        do {
            while (!scanner.hasNextInt()) scanner.next();
            numberOfCpuPlayers = scanner.nextInt();
        } while (numberOfCpuPlayers < 4 || numberOfCpuPlayers > 8);

        PlayerGenerator playerGenerator = new PlayerGenerator();
        return playerGenerator.generatePlayers(numberOfCpuPlayers - 1);
    }

    private Mesa configMesa(Jogador mainPlayer, List<Jogador> cpuPlayers) {
        Mesa mesa = new Mesa(mainPlayer);
        cpuPlayers.forEach(player -> {
            if (player instanceof CPU) ((CPU) player).setMesa(mesa);
            mesa.addJogador(player);
        });
        return mesa;
    }
}

class GameConfiguration {
    private final CardDisplay cardDisplay;
    private final Mesa mesa;

    GameConfiguration(CardDisplay cardDisplay, Mesa mesa) {
        this.cardDisplay = cardDisplay;
        this.mesa = mesa;
    }

    public CardDisplay getCardDisplay() {
        return cardDisplay;
    }

    public Mesa getMesa() {
        return mesa;
    }
}