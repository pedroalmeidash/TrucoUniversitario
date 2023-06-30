package jun.truco.game;

import jun.truco.model.CPU;
import jun.truco.model.Jogador;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlayerGenerator {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    List<String> playersName = Arrays.asList(
            ANSI_RED +"Osvaldo" + ANSI_RESET,
            ANSI_GREEN + "Mario" + ANSI_RESET,
            ANSI_YELLOW + "Alberto" + ANSI_RESET,
            ANSI_BLUE + "Renato" + ANSI_RESET,
            ANSI_PURPLE + "Leticia" + ANSI_RESET,
            ANSI_CYAN + "Samuel" + ANSI_RESET,
            ANSI_WHITE + "Lorenzo" + ANSI_RESET
    );

    public List<Jogador> generatePlayers(int numberOfPlayers) {
        List<Jogador> cpuPlayers = new ArrayList<>();

        for (int playerPosition = 0; playerPosition < numberOfPlayers; playerPosition++) {
            cpuPlayers.add(new CPU(playersName.get(playerPosition)));
        }

        return cpuPlayers;
    }
}
