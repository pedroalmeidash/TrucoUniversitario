package model;

public abstract class MostrarCarta {

//    public static MostrarCarta getMostrador(boolean opcao) {
//        return opcao ? new MostrarCartaASCII() : new MostrarCartaTexto();
//    }

    private static boolean isToshowASCII;

    public static void setShowASCII(boolean opcao) {
        isToshowASCII = opcao;
    }

    public static void mostrarMesa(Mesa mesa) {
        System.out.print("Mesa: ");
        MostrarCarta mostrador = isToshowASCII ? new MostrarCartaASCII() : new MostrarCartaTexto();
        mostrador.mostrarCartas(mesa.getMesa());
    }

    public static void mostrarMao(Jogador jogador) {
        System.out.print("Mao: ");
        Carta[] mao = jogador.getMao().toArray(new Carta[0]);
        MostrarCarta mostrador = isToshowASCII ? new MostrarCartaASCII() : new MostrarCartaTexto();
        mostrador.mostrarCartas(mao);
    }

    public abstract void mostrarCartas(Carta[] cartas);

}