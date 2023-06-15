package jun.truco.game;

import jun.truco.Main;
import jun.truco.model.Humano;
import jun.truco.model.Jogador;
import jun.truco.model.Mesa;

import java.util.Scanner;

public class PlayerActionReader {

    private final Scanner scanner;

    public PlayerActionReader(Scanner scanner) {
        this.scanner = scanner;
    }

    public void readPlayerWinningTurnsForRound(Humano player) {
        int numberOfTurns = player.getMao().size();
        int numberOfWinningTurns = 0;
        boolean isInvalidAnswer = true;
        do {
            System.out.println("Quantos turnos você faz? (Escolha um valor entre 0 e " + numberOfTurns + ")");
            if (!scanner.hasNextInt()) {
                scanner.next();
                continue;
            }
            numberOfWinningTurns = scanner.nextInt();
            isInvalidAnswer = !isValidNumberOfWinningTurns(numberOfTurns, numberOfWinningTurns);
        } while (isInvalidAnswer);

        player.setPontosPendente(numberOfWinningTurns);
    }

    private boolean isValidNumberOfWinningTurns(int numberOfTurns, int numberOfWinningTurns) {
        return numberOfWinningTurns >= 0 && numberOfWinningTurns <= numberOfTurns;
    }

    public CardPlayDecision readPlayerCardToPlay(Mesa mesa, Humano player) {
        int handSize = player.getMao().size();
        int chosenCardPosition = 0;
        boolean isInvalidAnswer = true;

        do {
            System.out.println("Qual carta jogar? (Escolha um numero de 1 a " + handSize + ")");
            System.out.println("Para desistir e comecar uma nova partida digite 'R'.");
            String answer = scanner.next();

            if (isInteger(answer)) {
                chosenCardPosition = Integer.parseInt(answer);
                isInvalidAnswer = !isValidCardPositionToPlay(handSize, chosenCardPosition);
            } else if (answer.equalsIgnoreCase("r")) {
                return CardPlayDecision.RETREAT;
            }
        } while (isInvalidAnswer);
        mesa.getForcaDasCartas().playCard(player, player.jogar(chosenCardPosition - 1));
        return CardPlayDecision.PLAY;
    }

    private boolean isValidCardPositionToPlay(int handSize, int chosenCardPosition) {
        return chosenCardPosition >= 1 && chosenCardPosition <= handSize;
    }

    private boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}

enum CardPlayDecision {
    PLAY, RETREAT;
}
