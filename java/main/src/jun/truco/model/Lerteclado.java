package jun.truco.model;
import java.util.Scanner;

public class Lerteclado {
    public void primeira_rodada(Jogador j, Scanner scanner){
        
        Boolean validador = false;
        String resposta = "";						        
        System.out.println("Deseja ir? ('sim' ou 'nao')");
        while(!validador){ //faz while ate que resposta informada seja uma das 2 seguintes "sim" ou "nao"
            while(!scanner.hasNextLine()) scanner.next(); //so ira fazer as demais validacoes caso seja um string
            resposta = scanner.nextLine();
            if((resposta.equals("sim") || resposta.equals("nao"))){
                validador = true;
                j.setPontosPendente(resposta.equals("sim") ? 1 : 0); //caso faz entao pendente ira receber +1
            }else{
                System.out.println("Resposta invalida, Deseja ir? ('sim' ou 'nao')");
            }
        } 
    }

    public void fazquantos(Jogador jogador, Scanner scanner){
        Boolean validador = false;
        System.out.println("\nFaz quanto?");
        while(!validador){
            int qtd = jogador.getMao().size();
            while(!scanner.hasNextInt()) scanner.next();
            Integer resposta = scanner.nextInt();
            if(resposta <= qtd){
                validador = true;
                jogador.setPontosPendente(resposta);
            }else{
                System.out.println("Resposta invalida informe um numero de 0 a "+ qtd);
            }
        }
    }

    public void escolher_cartajogar(Mesa m, Scanner scanner, Jogador jogador){
        Humano hum = (Humano)jogador;		

        Boolean validador = false;
        System.out.println("qual carta jogar?");
        while(!validador){ //faz while ate que resposta informada seja uma das 2 seguintes "sim" ou "nao"
            int qtd = jogador.getMao().size();
            while(!scanner.hasNextInt()) scanner.next(); //so ira fazer as demais validacoes caso seja um string
            Integer resposta = scanner.nextInt();
            if(resposta <= qtd){
                validador = true;
                m.getForcaDasCartas().CartaJogada(hum, hum.jogar(resposta-1));
            }else{
                System.out.println("Resposta invalida informe um numero de 0 a "+ qtd);
            }
        }
    }
}
