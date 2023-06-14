package jun.truco.model;

public class Turno {

    private Jogador[] jogadores;
    private int posicao;


    public Turno(Jogador[] j) {
        this.jogadores = j;
        posicao = 0;
    }

    public Jogador vezJogador() {
        if (posicao < jogadores.length) {
            Jogador j = jogadores[posicao];
            posicao++;
            return j;
        }
        return null;
    }

    public boolean hasMoreTurns() {
        return posicao < jogadores.length;
    }

    public Jogador nextPlayerTurn() {
        if (posicao >= jogadores.length) return null;
        Jogador j = jogadores[posicao];
        posicao++;
        return j;
    }

    public Jogador[] getJogador() {
        return jogadores;
    }

    public int getPosicao() {
        return posicao;
    }
}
