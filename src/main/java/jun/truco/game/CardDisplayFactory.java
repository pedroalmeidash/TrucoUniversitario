package jun.truco.game;

import model.ASCIICardDisplay;
import model.TextCardDisplay;

public class CardDisplayFactory {

    public static CardDisplay create(Boolean showASCIIBoard) {
        if (showASCIIBoard) {
            return new ASCIICardDisplay();
        } else {
            return new TextCardDisplay();
        }
    }
}
