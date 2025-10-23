package domain;

import java.util.List;
import java.util.Scanner;

public class RegistroJogada { // Nome da classe corrigido para corresponder ao arquivo RegistrarJogada.java

    public static void imprimirSituacaoAtual(Tabuleiro tabuleiro, List<Personagem> time1, List<Personagem> time2, String nomeJogadorAtivo) {

        System.out.println("\n=========================================");
        System.out.println("        REGISTRO DE TURNO: " + nomeJogadorAtivo);
        System.out.println("=========================================");

        tabuleiro.ImprimeTabuleiro();

        System.out.println("\n--- STATUS DOS TIMES ---");
        imprimirStatusTime("TIME 1", time1);
        imprimirStatusTime("TIME 2", time2);

        System.out.println("-----------------------------------------");
    }

    private static void imprimirStatusTime(String nomeTime, List<Personagem> time) {
        System.out.println(nomeTime + " (" + time.size() + " Vivos):");
        if (time.isEmpty()) {
            System.out.println("  - DERROTADO!");
            return;
        }
        for (Personagem p : time) {
            System.out.printf("  - %s (%s) [Vida: %d/%d] (Pos: %d,%d)\n",
                    p.getNomePersonagem(),
                    p.getCasaPersonagem().getNome(),
                    p.getVidaAtual(),
                    p.getCasaPersonagem().getVidaMaxima(),
                    p.getLinha(), p.getColuna());
        }
    }

    public static void solicitarProximaJogada(Scanner scanner) {
        System.out.print("\nPressione ENTER para continuar para o próximo turno...");
        scanner.nextLine();
    }
}