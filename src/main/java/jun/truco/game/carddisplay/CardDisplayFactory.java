package jun.truco.game.carddisplay;

public class CardDisplayFactory {

    public static CardDisplay create(Boolean showASCIIBoard) {
        if (showASCIIBoard) {
            return new ASCIICardDisplay();
        } else {
            return new TextCardDisplay();
        }
    }
}
