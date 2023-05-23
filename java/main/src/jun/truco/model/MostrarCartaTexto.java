package jun.truco.model;

public class MostrarCartaTexto extends MostrarCarta {

    @Override
    public void mostrarCartas(Carta[] cartas) {
        for (Carta carta : cartas) {
            if (carta != null) {
                System.out.print(carta.toString() + ", ");
            }
        }
        System.out.println("");
    }
}