package jun.truco.model;
import jun.truco.model.CPUNormal;
import java.util.ArrayList;
import java.util.Scanner;

public class Outrosjogadores {
    public void primeira_rodada(Mesa m, Scanner scanner){    

        
		System.out.print("Digite a quantindade de jogadores de 4 a 8");
		int qtdJogadores = 0;
		do{
			while(!scanner.hasNextInt()) scanner.next();
			qtdJogadores = scanner.nextInt();
		}while(qtdJogadores < 4 || qtdJogadores > 8);

        ArrayList<String> jogadores = new ArrayList<String>();
		jogadores.add("Osvaldo");
		jogadores.add("Mario");
		jogadores.add("Alberto");
		jogadores.add("Renato");
		jogadores.add("Leticia");
		jogadores.add("Samuel");
		jogadores.add("Lorenzo");

		for(int x = 0; x < qtdJogadores-1; x++){
		        m.addJogador(new CPUNormal(jogadores.get(x),m));
		}

        scanner.nextLine();
    }
}
