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
        System.out.print("Digite seu nome para comecar o jogo ou digite tutorial para entender as regras: ");
        String playerName = scanner.nextLine();
        if( playerName.equalsIgnoreCase("tutorial")){
            System.out.print("\nO fodinha é um jogo que pode ser jogado com 4 a 8 jogadores. \n Cada jogador começa o jogo com 5 vidas e vai perdendo cada uma conforme erra o palpite nas rodadas. \n A cada rodada, a quantidade de cartas que cada jogador recebe vai aumentando. \n O objetivo do jogador é adivinhar quantas jogadas ele vai fazer na rodada. \n Se o jogador errar o palpite, perde vidas. \n Se o jogador acreditar que sua carta da rodada é maior que as outras, ele ganha a rodada. \n Se o jogador não tiver a maior carta, deve adivinhar quantas rodadas ele vai fazer. \n Na primeira partida, você não consegue ver a carta que você tem na mão. \n O baralho e o valor das cartas são semelhantes ao utilizado para jogar truco. Primeiro, uma pessoa embaralha as cartas e faz uma de vira, que é a manilha .\n Após isso, cada jogador precisa adivinhar quantas cartas vai fazer na rodada. \n Em seguida, todos os jogadores jogam uma carta, seguindo sempre a direita de quem embaralhou. \n Após todos jogarem, aparece o resultado de quem ganhou a rodada. Se empatar, o jogador que jogou na rodada anterior jogará novamente. \n Quem ganhar a rodada, inicia jogando a próxima. \n Quando todos os jogadores jogarem suas cartas em um determinado turno, a quantidade de vidas de cada um é atualizada. \n O jogador que ficar com a maior quantidade de vidas será o vencedor. \n\n");
            System.out.print("Digite seu nome para comecar o jogo: ");
            playerName = scanner.nextLine();
        }
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