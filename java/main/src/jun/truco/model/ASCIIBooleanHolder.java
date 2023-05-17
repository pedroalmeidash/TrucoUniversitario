package jun.truco.model;

public class ASCIIBooleanHolder {
    private static boolean showASCII;

    public static boolean isToShowASCII() {
        return showASCII;
    }

    public static void setShowASCII(boolean valor) {
        showASCII = valor;
    }
}
