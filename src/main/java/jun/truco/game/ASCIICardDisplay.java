package jun.truco.game;

import jun.truco.game.CardDisplay;
import jun.truco.model.Carta;

public class ASCIICardDisplay extends CardDisplay {

    @Override
    public void mostrarCartas(Carta[] cartas) {
        String[] linhas = new String[8];
        for (int i = 0; i < 8; i++) {
            linhas[i] = "";
            for (Carta carta : cartas) {
                // TODO carta não deve ser nula
                if (carta != null) {
                    linhas[i] += getLinha(i, carta);
                }
            }
            linhas[i] += "\n";
        }
        System.out.println();
        System.out.println(String.join("", linhas));
    }

    private String getLinha(int linha, Carta carta) {
        String valor = carta.getValor() == 0 ? "A" : (carta.getValor() == 7 ? "Q" : (carta.getValor() == 8 ? "J" : (carta.getValor() == 9 ? "K" : String.valueOf(carta.getValor() + 1))));
        switch (linha) {
            case 0: return "┌—————————┐  ";
            case 1: return String.format("│%s        │  ", valor);
            case 2:
            case 4:
            case 5: return "│         │  ";
            case 3: return String.format("│    %s    │  ", getNaipeSymbol(carta));
            case 6: return String.format("│        %s│  ", valor);
            case 7: return "└—————————┘  ";
            default: return "";
        }
    }

    // TODO retornar cartas coloridas
    private String getNaipeSymbol(Carta carta) {
        switch (carta.getNaipe()) {
            case PAUS: return "♣";
            case OUROS: return "♦";
            case ESPADAS: return "♠";
            case COPAS: return "♥";
            default: return "";
        }
    }
}