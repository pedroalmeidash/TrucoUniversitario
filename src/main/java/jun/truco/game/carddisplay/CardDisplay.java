package jun.truco.game.carddisplay;

import jun.truco.model.Carta;
import jun.truco.model.Jogador;
import jun.truco.model.Mesa;

public abstract class CardDisplay {

    public void showBoard(Mesa mesa) {
        System.out.print("Mesa: ");
        mostrarCartas(mesa.getMesa());
    }

    public void showHand(Jogador jogador) {
        System.out.print("Mao: ");
        Carta[] mao = jogador.getMao().toArray(new Carta[0]);
        mostrarCartas(mao);
    }

    public abstract void mostrarCartas(Carta[] cartas);
}