package jun.truco.service;
import java.util.Scanner;
import jun.truco.model.Jogador;

public class Partida {
    Scanner scanner = new Scanner(System.in);

    public void primeira_rodada(Jogador j){
        Boolean validador = false;
        String resposta = "";						
        System.out.println("Deseja ir? ('sim' ou 'nao')");
        while(!validador){ //faz while ate que resposta informada seja uma das 2 seguintes "sim" ou "nao"
            while(!scanner.hasNextLine()) scanner.next(); //so ira fazer as demais validacoes caso seja um string
            resposta = scanner.nextLine();
            if((resposta.equals("sim") || resposta.equals("nao"))){
                validador = true;
                j.setPontosPendente((resposta == "sim") ? 1 : 0); //caso faz entao pendente ira receber +1
            }else{
                System.out.println("Resposta invalida, Deseja ir? ('sim' ou 'nao')");
            }
        } 
    }
}
