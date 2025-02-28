package jun.truco.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Mesa {

    private int manilha;
    private Carta[] mesa;
    private int numerosCartas = 1;
    private int rodadasPorPartidas = 1;
    private final List<Jogador> jogadores = new ArrayList<Jogador>();
    private Turno turno;
    private int JogadorQueComecaPartida = 0;
    private int partidas = 0;
    private int rodada = 0;
    private int maximoDeNumerosDeCarta;

    private ForcaDasCartas forcaDasCartas;

    public List<Jogador> getJogadores() {
        return jogadores;
    }

    public Mesa(Jogador... j) {
        jogadores.addAll(Arrays.asList(j));

        mesa = new Carta[jogadores.size()];
        turno = new Turno(factoryTurno(JogadorQueComecaPartida));
        if (jogadores.size() > 0) calcMaximoNumeroDeCartas();
    }

    public Jogador getJogadorDaVez() {
        return jogadores.get(JogadorQueComecaPartida);
    }

    public Carta[] getMesa() {
        return mesa;
    }

    public void limpaMesa() {
        mesa = new Carta[jogadores.size()];
        forcaDasCartas.setMesa(mesa);
        forcaDasCartas.clear();
    }

    public void addJogador(Jogador... j) {
        jogadores.addAll(Arrays.asList(j));

        mesa = new Carta[jogadores.size()];
        turno = new Turno(factoryTurno(JogadorQueComecaPartida));
        calcMaximoNumeroDeCartas();
    }

    public void DarAsCartas() {

        factoryTurno(JogadorQueComecaPartida);
        Baralho b = new Baralho();
        jogadores.get(JogadorQueComecaPartida).embaralhar(b);
        for (Jogador j : turno.getJogador())
            jogadores.get(JogadorQueComecaPartida).DarAsCartas(b, j, numerosCartas);

    }

    public Jogador nextPlayer() {
        if (turno.hasMoreTurns()) {
            return turno.nextPlayerTurn();
        } else {
            int todosOsPontos = 0;
            for (Jogador j : turno.getJogador())
                todosOsPontos += j.getPontosPendente();

            if (todosOsPontos == numerosCartas)
                return turno.getJogador()[turno.getJogador().length - 1];
            else
                return null;
        }
    }

    public Integer numberOfAlivePlayers() {
        return turno.getJogador().length;
    }

    public void vira(Carta c) {
        int valor = c.getValor();
        if (valor < 0 || valor > 9) throw new ArrayIndexOutOfBoundsException();

        if (valor == 9) valor = 0;
        else
            valor++;

        manilha = valor;

    }

    public void startRound() {
        forcaDasCartas = new ForcaDasCartas(mesa, manilha);
    }

    public String getManilha() {
        return Carta.Valor[manilha];
    }

    public void calcMaximoNumeroDeCartas() {
        maximoDeNumerosDeCarta = 40 / jogadores.size();
    }

    public boolean hasRodada() {

        if (rodada == 0) {
            turno = new Turno(factoryTurno(JogadorQueComecaPartida));
            rodada++;
            return true;
        } else if (rodada < rodadasPorPartidas) {

            Jogador j = forcaDasCartas.getJogadorFez();
            int posicaoDeQuemFez;
            if (j != null) {
                j.setPontos(j.getPontos() + 1);
                posicaoDeQuemFez = jogadores.indexOf(j);
            } else posicaoDeQuemFez = jogadores.indexOf(turno.getJogador()[turno.getJogador().length - 1]);

            if (posicaoDeQuemFez == 0) posicaoDeQuemFez = jogadores.size() - 1;
            else posicaoDeQuemFez--;
            turno = new Turno(factoryTurno(posicaoDeQuemFez));
            rodada++;

            return true;
        } else {
            partidas++;
            numerosCartas++;
            rodada = 0;
            rodadasPorPartidas++;
            Jogador j = forcaDasCartas.getJogadorFez();
            if (j != null) j.setPontos(j.getPontos() + 1);
            ContagemDeHit();
            zeraPontos();
            OlharSeAlgumjogadorCaiu();
            calcMaximoNumeroDeCartas();
            if (numerosCartas > maximoDeNumerosDeCarta) {
                numerosCartas = 2;
                rodadasPorPartidas = 2;
            }
            JogadorQueComecaPartida++;
            if (JogadorQueComecaPartida == jogadores.size()) JogadorQueComecaPartida = 0;
            turno = new Turno(factoryTurno(JogadorQueComecaPartida));
            return false;
        }
    }


    public int getPartidas() {
        return partidas;
    }

    public boolean isFirstRound() {
        return partidas == 0;
    }

    private void zeraPontos() {
        for (Jogador j : jogadores) j.setPontos(0);
    }

    private void ContagemDeHit() {
        for (Jogador j : jogadores) {
            int pontos = j.getPontos();
            int pontosPendentes = j.getPontosPendente();

            if (pontos == pontosPendentes) continue;
            else if (pontos > pontosPendentes) {
                int hit = pontos - pontosPendentes;
                j.setVidas(j.getVidas() - hit);
            } else {
                int hit = pontosPendentes - pontos;
                j.setVidas(j.getVidas() - hit);
            }
        }
    }

    private void OlharSeAlgumjogadorCaiu() {
        ArrayList<Jogador> remover = new ArrayList<Jogador>();
        for (Jogador j : jogadores) {
            if (j.getVidas() <= 0) remover.add(j);
        }
        for (Jogador j : remover) {
            int posicao = jogadores.indexOf(j);
            if (posicao < JogadorQueComecaPartida) JogadorQueComecaPartida--;
            if (JogadorQueComecaPartida == jogadores.size()) JogadorQueComecaPartida = 0;
            jogadores.remove(j);
        }
    }

    public boolean hasGameFinished() {
        return jogadores.size() <= 1;
    }

    public Jogador getGanhador() {
        if (jogadores.size() == 1) return jogadores.get(0);
        else return null;
    }

    public int getRodadasPorPartidas() {
        return rodadasPorPartidas;
    }

    public int getRodada() {
        return rodada;
    }

    public int getNumerosCartas() {
        return numerosCartas;
    }

    private Jogador[] factoryTurno(int posicao) {
        Jogador[] j = new Jogador[jogadores.size()];
        if (jogadores.size() <= 0) return null;
        int x = posicao + 1;
        int index = 0;
        while (index < jogadores.size()) {
            if (x == jogadores.size()) {
                x = 0;
                j[index] = jogadores.get(x);
                index++;
                x++;
                continue;
            }
            j[index] = jogadores.get(x);
            x++;
            index++;
        }
        return j;
    }

    public Turno getTurno() {
        return turno;
    }

    public ForcaDasCartas getForcaDasCartas() {
        return forcaDasCartas;
    }

    public void prepareRound() {
        System.out.println("------------------------------------------");
        System.out.println("Partida " + (getPartidas() + 1));

        Baralho b = new Baralho();
        getJogadorDaVez().embaralhar(b);

        System.out.println("Jogador " + getJogadorDaVez().getNome() + " vai dar as cartas!");
        DarAsCartas();

        b = new Baralho();
        getJogadorDaVez().embaralhar(b);
        vira(b.DarCarta());

        System.out.println("Manilha: " + getManilha());
    }
}
