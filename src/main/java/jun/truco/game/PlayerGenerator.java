package jun.truco.game;

import jun.truco.model.CPU;
import jun.truco.model.Jogador;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlayerGenerator {

    List<String> playersName = Arrays.asList(
            "Osvaldo",
            "Mario",
            "Alberto",
            "Renato",
            "Leticia",
            "Samuel",
            "Lorenzo"
    );

    public List<Jogador> generatePlayers(int numberOfPlayers) {
        List<Jogador> cpuPlayers = new ArrayList<>();

        for (int playerPosition = 0; playerPosition < numberOfPlayers; playerPosition++) {
            cpuPlayers.add(new CPU(playersName.get(playerPosition)));
        }

        return cpuPlayers;
    }
}
