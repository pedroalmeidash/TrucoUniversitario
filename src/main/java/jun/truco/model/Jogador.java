package jun.truco.model;

import java.util.ArrayList;
import java.util.List;


public abstract class Jogador {
    public String nome;
    protected int vidas = 5;
    protected int pontos;
    protected int pontosPendente;
    protected List<Carta> mao = new ArrayList<Carta>();

    public List<Carta> getMao() {
        return mao;
    }

    public Jogador(String nome) {
        this.setNome(nome);
    }

    public List<Carta> listagem() {
        return mao;
    }

    public void receberCarta(Carta c) {
        mao.add(c);
    }

    public int quantidadeDeCartas() {
        return mao.size();
    }

    public void embaralhar(Baralho b) {
        b.embaralhar();
    }

    public void DarAsCartas(Baralho b, Jogador jogador, int quantidade) {
        for (int x = 0; x < quantidade; x++) {
            jogador.receberCarta(b.DarCarta());
        }
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getPontos() {
        return pontos;
    }

    public void setPontos(int pontos) {
        this.pontos = pontos;
    }

    public int getVidas() {
        return vidas;
    }

    public void setVidas(int vidas) {
        this.vidas = vidas;
    }

    public int getPontosPendente() {
        return pontosPendente;
    }

    public void setPontosPendente(int pontosPendente) {
        this.pontosPendente = pontosPendente;
    }

    public String getPointsMadeAndPendingPointsFormattedText() {
        return getNome() + " Pontos Feitos: " + getPontos() + " Precisa fazer: " + getPontosPendente();
    }
}
