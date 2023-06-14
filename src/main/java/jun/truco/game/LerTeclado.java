package jun.truco.game;

import jun.truco.Main;
import jun.truco.model.Humano;
import jun.truco.model.Jogador;
import jun.truco.model.Mesa;

import java.util.Scanner;

public class LerTeclado {
    public void primeira_rodada(Jogador j, Scanner scanner) {
        Boolean isValid = false;
        String resposta = "";
        System.out.println("Deseja ir? ('sim' ou 'nao')");
        while (!isValid) { //faz while ate que resposta informada seja uma das 2 seguintes "sim" ou "nao"
            while (!scanner.hasNextLine()) {  //printa aviso mesmo informe um numerico
                if (!scanner.hasNextLine()) System.out.println("Resposta invalida, Deseja ir? ('sim' ou 'nao')");
                scanner.next();
            }

            resposta = scanner.nextLine();
            if ((resposta.equals("sim") || resposta.equals("nao"))) {
                isValid = true;
                j.setPontosPendente(resposta.equals("sim") ? 1 : 0); //caso faz entao pendente ira receber +1
            } else {
                System.out.println("Resposta invalida, Deseja ir? ('sim' ou 'nao')");
            }
        }
    }

    public void fazquantos(Jogador jogador, Scanner scanner) {
        Boolean isValid = false;
        int qtd = jogador.getMao().size();
        System.out.println("\nFaz quanto? (informe um numero de 0 a " + qtd + ")");
        while (!isValid) {
            while (!scanner.hasNextInt()) { //printa aviso mesmo informe uma letra
                if (!scanner.hasNextInt()) System.out.println("Resposta invalida. Informe um numero de 0 a " + qtd);
                scanner.next();
            }
            Integer resposta = scanner.nextInt();
            if (resposta <= qtd) {
                isValid = true;
                jogador.setPontosPendente(resposta);
            } else {
                System.out.println("Resposta invalida. Informe um numero de 0 a " + qtd);
            }
        }
    }

    public void chooseCardToPlay(Mesa m, Scanner scanner, Jogador jogador) {
        Humano hum = (Humano) jogador;
        Boolean isValid = false;
        int qtd = jogador.getMao().size();

        System.out.println("Qual carta jogar? (informe um numero de 1 a " + qtd + ")");
        System.out.println("Para desistir e comecar uma nova partida digite 'R'.");
        while (!isValid) { //faz while ate que resposta informada seja uma das 2 seguintes "sim" ou "nao"
            while (!scanner.hasNextInt()) { //printa aviso mesmo informe uma letra
                if (scanner.next().equalsIgnoreCase("R")) Main.restart(); //reinicia a partida invocando a main()
                if (!scanner.hasNextInt()) System.out.println("Resposta invalida. Informe um numero de 1 a " + qtd);
                scanner.next();
            }
            Integer resposta = scanner.nextInt();
            if ((resposta <= qtd) && (resposta != 0)) {
                isValid = true;
                m.getForcaDasCartas().playCard(hum, hum.jogar(resposta - 1));
            } else {
                System.out.println("Resposta invalida. Informe um numero de 1 a " + qtd);
            }
        }
    }
}
