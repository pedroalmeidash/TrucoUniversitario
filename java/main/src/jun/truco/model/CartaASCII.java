package jun.truco.model;

public class CartaASCII {

    public static void exibirCartas(Carta[] cartas) {
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
        System.out.print(String.join("", linhas));
    }

    public static String getLinha(int linha, Carta carta) {
        String valor = carta.getValor() == 0 ? "A" : (carta.getValor() == 7 ? "Q" : (carta.getValor() == 8 ? "J" : (carta.getValor() == 9 ? "K" : String.valueOf(carta.getValor() + 1))));
        return switch (linha) {
            case 0 -> "┌————————— ┐  ";
            case 1 -> String.format("│%-2s        │  ", valor);
            case 2 -> "│          │  ";
            case 3 -> String.format("│   %s    │  ", getNaipeSymbol(carta));
            case 4 -> "│          │  ";
            case 5 -> "│          │  ";
            case 6 -> String.format("│       %s  │  ", valor);
            case 7 -> "└————————— ┘  ";
            default -> "";
        };
    }

    public static String getNaipeSymbol(Carta carta) {
        switch (carta.getNaipe()) {
            case Paus:
                return "♣";
            case Ouros:
                return "♦";
            case Espadas:
                return "♠";
            case Copas:
                return "♥";
            default:
                return "";
        }
    }
}
