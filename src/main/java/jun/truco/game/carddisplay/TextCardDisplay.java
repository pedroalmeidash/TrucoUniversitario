package jun.truco.game.carddisplay;

import jun.truco.game.carddisplay.CardDisplay;
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