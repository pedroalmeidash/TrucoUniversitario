package jun.truco.game;

import jun.truco.game.CardDisplay;
import jun.truco.model.Carta;

public class TextCardDisplay extends CardDisplay {

    @Override
    public void mostrarCartas(Carta[] cartas) {
        for (Carta carta : cartas) {
            if (carta != null) {
                System.out.print(carta + ", ");
            }
        }
        System.out.println();
    }
}