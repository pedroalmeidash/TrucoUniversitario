package jun.truco.model;

import main.CardDisplay;

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