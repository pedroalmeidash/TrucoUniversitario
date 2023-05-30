package jun.truco.model;

public class MostrarCartaASCII extends MostrarCarta {

    @Override
    public void mostrarCartas(Carta[] cartas) {
        String[] linhas = new String[8];
        int countCard = 0;
        for (int i = 0; i < 8; i++) {
            linhas[i] = "";
            for (Carta carta : cartas) {
                if (carta != null) {
                    if (i != 3) {
                        linhas[i] += getLinha(i, carta);
                    } else if (countCard < 2) {
                        linhas[i] += getLinha(i, carta);
                        countCard++;
                    } else {
                        linhas[i] += String.format("│   %s     │  ", getNaipeSymbol(carta));
                        countCard = 0;
                    }
                }
            }
            linhas[i] += "\n";
        }
        System.out.println("");
        System.out.println(String.join("", linhas));
    }

    public static String getLinha(int linha, Carta carta) {
        String valor = carta.getValor() == 0 ? "A" : (carta.getValor() == 7 ? "Q" : (carta.getValor() == 8 ? "J" : (carta.getValor() == 9 ? "K" : String.valueOf(carta.getValor() + 1))));
        switch (linha) {
            case 0: return "┌————————— ┐  ";
            case 1: return String.format("│%-2s        │  ", valor);
            case 2:
            case 4:
            case 5: return "│          │  ";
            case 3: return String.format("│   %s    │  ", getNaipeSymbol(carta));
            case 6: return String.format("│       %s  │  ", valor);
            case 7: return "└————————— ┘  ";
            default: return "";
        }
    }

    public static String getNaipeSymbol(Carta carta) {
        switch (carta.getNaipe()) {
            case Paus: return "♣";
            case Ouros: return "♦";
            case Espadas: return "♠";
            case Copas: return "♥";
            default: return "";
        }
    }
}