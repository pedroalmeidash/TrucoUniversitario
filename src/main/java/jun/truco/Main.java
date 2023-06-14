package jun.truco;

import jun.truco.game.Game;

import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean keepPlaying;
        do {
            Game game = new Game();
            game.start();
            keepPlaying = playAgain();
            if (keepPlaying) clearTerminal();
        } while (keepPlaying);
    }

    private static boolean playAgain() {
        return scanner.next().equalsIgnoreCase("sim");
    }

    public static void restart() {
        clearTerminal();
        main(new String[0]);
    }

    private static void clearTerminal() {
        System.out.println(System.lineSeparator().repeat(100));
    }
}
